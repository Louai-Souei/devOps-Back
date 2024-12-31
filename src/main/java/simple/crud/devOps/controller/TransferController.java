package simple.crud.devOps.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import simple.crud.devOps.dto.TransferDto;
import simple.crud.devOps.serviceImpl.TransferServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferServiceImpl service;

    @PostMapping("new-transfer")
    public TransferDto create(@RequestBody TransferDto transferDto) {
        return service.save(transferDto);
    }

    @GetMapping("/get-all")
    public List<TransferDto> getAll() {
        return service.findAll();
    }

    @GetMapping("/get/{id}")
    public TransferDto getById(@PathVariable Long id) {
        return service.findById(id).orElseThrow();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public TransferDto update(@PathVariable Long id, @RequestBody TransferDto transferDto) {
        return service.update(id, transferDto);
    }
}