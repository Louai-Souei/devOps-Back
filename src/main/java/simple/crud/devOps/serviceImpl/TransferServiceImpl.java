package simple.crud.devOps.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import simple.crud.devOps.dto.TransferDto;
import simple.crud.devOps.entity.Transfer;
import simple.crud.devOps.repository.TransferRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl {

    private final TransferRepository repository;

    public TransferDto save(TransferDto transferDto) {
        Transfer transfer = transferDto.DtoToTransfer();
        Transfer savedTransfer = repository.save(transfer);
        return new TransferDto(savedTransfer);
    }

    public List<TransferDto> findAll() {
        List<Transfer> transfers = repository.findAll();
        return transfers.stream()
                .map(TransferDto::new)
                .collect(Collectors.toList());
    }

    public Optional<TransferDto> findById(Long id) {
        Optional<Transfer> transfer = repository.findById(id);
        return transfer.map(TransferDto::new);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public TransferDto update(Long id, TransferDto transferDto) {
        Optional<Transfer> existingTransferOpt = repository.findById(id);

        if (existingTransferOpt.isPresent()) {
            Transfer existingTransfer = existingTransferOpt.get();

            existingTransfer.setName(transferDto.getName());
            existingTransfer.setAmount(transferDto.getAmount());
            existingTransfer.setTransferDate(transferDto.getTransferDate());

            Transfer updatedTransfer = repository.save(existingTransfer);
            return new TransferDto(updatedTransfer);
        } else {
            throw new RuntimeException("Transfer not found with id " + id);
        }
    }
}
