package com.example.bankingapp.application.service;

import com.example.bankingapp.domain.model.TransactionLog;
import com.example.bankingapp.domain.ports.in.GenerateStatementUseCase;
import com.example.bankingapp.domain.ports.out.TransactionLogPort;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatementService implements GenerateStatementUseCase {

    private final TransactionLogPort transactionLogPort;

    @Override
    public byte[] generateStatement(String accountNumber) {
        List<TransactionLog> logs = transactionLogPort.findByAccount(accountNumber);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph("Bank Statement for account: " + accountNumber));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);

            addHeaderCell(table, "Date");
            addHeaderCell(table, "From");
            addHeaderCell(table, "To");
            addHeaderCell(table, "Type");
            addHeaderCell(table, "Amount");
            addHeaderCell(table, "Currencies");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    .withZone(ZoneId.systemDefault());

            for (TransactionLog log : logs) {
                table.addCell(formatter.format(log.getTimestamp()));
                table.addCell(log.getFromAccount());
                table.addCell(log.getToAccount());
                table.addCell(log.getType().name());
                table.addCell(String.valueOf(log.getAmount()));
                table.addCell(log.getCurrencyFrom() + " -> " + log.getCurrencyTo());
            }

            document.add(table);
            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF statement", e);
        }
    }

    private void addHeaderCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setGrayFill(0.9f);
        table.addCell(cell);
    }
}
