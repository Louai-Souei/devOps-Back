package simple.crud.devOps.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import simple.crud.devOps.dto.TransferDto;
import simple.crud.devOps.repository.TransferRepository;
import simple.crud.devOps.serviceImpl.TransferServiceImpl;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TransferIntegrationTest {

    @Autowired
    private TransferServiceImpl transferService;

    @Autowired
    private TransferRepository transferRepository;

    private TransferDto transferDto;

    @BeforeEach
    void setUp() {
        transferDto = new TransferDto(null, "Transfer1", 1000.0, new Date());
    }

    @Test
    void testSaveTransfer() {
        TransferDto savedTransfer = transferService.save(transferDto);

        assertNotNull(savedTransfer.getId());
        assertEquals("Transfer1", savedTransfer.getName());
        assertEquals(1000.0, savedTransfer.getAmount());
    }

    @Test
    void testFindAllTransfers() {
        transferService.save(transferDto);

        List<TransferDto> transfers = transferService.findAll();

        assertNotNull(transfers);
        assertEquals(6, transfers.size());
        assertEquals("Transfer1", transfers.get(5).getName());
    }

    @Test
    void testUpdateTransfer() {
        TransferDto savedTransfer = transferService.save(transferDto);

        savedTransfer.setName("UpdatedTransfer");
        savedTransfer.setAmount(2000.0);

        TransferDto updatedTransfer = transferService.update(savedTransfer.getId(), savedTransfer);

        assertEquals("UpdatedTransfer", updatedTransfer.getName());
        assertEquals(2000.0, updatedTransfer.getAmount());
    }

    @Test
    void testDeleteTransfer() {
        TransferDto savedTransfer = transferService.save(transferDto);

        transferService.deleteById(savedTransfer.getId());

        List<TransferDto> transfers = transferService.findAll();
        assertEquals(5, transfers.size());
    }
}
