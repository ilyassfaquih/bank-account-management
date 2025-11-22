package com.example.bankingapp.web;

import com.example.bankingapp.domain.ports.in.GenerateStatementUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statements")
@RequiredArgsConstructor
public class StatementController {

    private final GenerateStatementUseCase generateStatementUseCase;

    @GetMapping("/{accountNumber}/pdf")
    public ResponseEntity<byte[]> getStatementPdf(@PathVariable String accountNumber) {
        byte[] pdf = generateStatementUseCase.generateStatement(accountNumber);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=statement-" + accountNumber + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
