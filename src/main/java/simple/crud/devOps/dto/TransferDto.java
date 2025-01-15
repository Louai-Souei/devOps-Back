package simple.crud.devOps.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import simple.crud.devOps.entity.Transfer;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TransferDto {
    private Long id;
    private String name;
    private Double amount;
    private Date transferDate;

    public TransferDto(Transfer transfer) {
        this.id = transfer.getId();
        this.name = transfer.getName();
        this.amount = transfer.getAmount();
        this.transferDate = transfer.getTransferDate();
    }

    public Transfer DtoToTransfer() {
        Transfer transfer = new Transfer();
        transfer.setId(this.id);
        transfer.setName(this.name);
        transfer.setAmount(this.amount);
        transfer.setTransferDate(this.transferDate);
        return transfer;
    }

}