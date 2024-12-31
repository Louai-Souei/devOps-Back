package simple.crud.devOps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import simple.crud.devOps.entity.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

}

