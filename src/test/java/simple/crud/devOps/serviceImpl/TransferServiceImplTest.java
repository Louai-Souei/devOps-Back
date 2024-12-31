package simple.crud.devOps.serviceImpl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.junit.jupiter.MockitoExtension;
import simple.crud.devOps.dto.TransferDto;
import simple.crud.devOps.entity.Transfer;
import simple.crud.devOps.repository.TransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {

    @InjectMocks
    private TransferServiceImpl transferService;

    @Mock
    private TransferRepository transferRepository;

    private TransferDto transferDto;
    private Transfer transfer;

    @BeforeEach
    void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 31);
        Date mockDate = calendar.getTime();

        transfer = new Transfer();
        transfer.setId(1L);
        transfer.setName("Transfer1");
        transfer.setAmount(1000.0);
        transfer.setTransferDate(mockDate);

        transferDto = new TransferDto(transfer);
    }

    @Test
    void testSaveTransfer() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String expectedDate = dateFormat.format(transfer.getTransferDate());

        when(transferRepository.save(any(Transfer.class))).thenReturn(transfer);

        TransferDto savedTransfer = transferService.save(transferDto);

        assertNotNull(savedTransfer);
        assertEquals(1L, savedTransfer.getId());
        assertEquals("Transfer1", savedTransfer.getName());
        assertEquals(1000.0, savedTransfer.getAmount());

        assertEquals(expectedDate, dateFormat.format(savedTransfer.getTransferDate()));
        verify(transferRepository, times(1)).save(any(Transfer.class));
    }

    @Test
    void testFindAllTransfers() {
        when(transferRepository.findAll()).thenReturn(List.of(transfer));

        List<TransferDto> transferDtos = transferService.findAll();

        assertNotNull(transferDtos);
        assertEquals(1, transferDtos.size());
        assertEquals(1L, transferDtos.get(0).getId());
        assertEquals("Transfer1", transferDtos.get(0).getName());
        verify(transferRepository, times(1)).findAll();
    }

    @Test
    void testFindTransferById() {
        when(transferRepository.findById(1L)).thenReturn(Optional.of(transfer));

        Optional<TransferDto> foundTransfer = transferService.findById(1L);

        assertTrue(foundTransfer.isPresent());
        assertEquals(1L, foundTransfer.get().getId());
        assertEquals("Transfer1", foundTransfer.get().getName());
        verify(transferRepository, times(1)).findById(1L);
    }

    @Test
    void testFindTransferByIdNotFound() {
        when(transferRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<TransferDto> foundTransfer = transferService.findById(1L);

        assertFalse(foundTransfer.isPresent());
        verify(transferRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateTransfer() {
        Transfer updatedTransfer = new Transfer();
        updatedTransfer.setId(1L);
        updatedTransfer.setName("UpdatedTransfer");
        updatedTransfer.setAmount(2000.0);
        updatedTransfer.setTransferDate(new Date());

        TransferDto updatedTransferDto = new TransferDto(updatedTransfer);

        when(transferRepository.findById(1L)).thenReturn(Optional.of(transfer));
        when(transferRepository.save(any(Transfer.class))).thenReturn(updatedTransfer);

        TransferDto result = transferService.update(1L, updatedTransferDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("UpdatedTransfer", result.getName());
        assertEquals(2000.0, result.getAmount());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(result.getTransferDate());

        assertEquals("2024-12-31", formattedDate);

        verify(transferRepository, times(1)).findById(1L);
        verify(transferRepository, times(1)).save(any(Transfer.class));
    }

    @Test
    void testUpdateTransferNotFound() {
        TransferDto updatedTransferDto = new TransferDto();
        updatedTransferDto.setName("UpdatedTransfer");
        updatedTransferDto.setAmount(2000.0);
        updatedTransferDto.setTransferDate(new Date());

        when(transferRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            transferService.update(1L, updatedTransferDto);
        });

        assertEquals("Transfer not found with id 1", thrown.getMessage());
        verify(transferRepository, times(1)).findById(1L);
        verify(transferRepository, times(0)).save(any(Transfer.class));
    }

    @Test
    void testDeleteTransfer() {
        doNothing().when(transferRepository).deleteById(1L);

        transferService.deleteById(1L);

        verify(transferRepository, times(1)).deleteById(1L);
    }
}
