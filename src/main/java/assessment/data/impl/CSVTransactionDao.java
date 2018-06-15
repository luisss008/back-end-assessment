package assessment.data.impl;


import assessment.data.TransactionDao;
import assessment.data.domain.Transaction;
import assessment.data.exception.DataAccessException;
import assessment.data.exception.RecordNotFoundException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CSVTransactionDao<T extends Serializable, E extends Serializable> implements TransactionDao<T,E> {

    private CSVPrinter printer;
    private CSVParser parser;

    public CSVTransactionDao(String fileName) throws IOException {

        File file = new File(fileName);
        file.createNewFile();

        FileWriter out = new FileWriter(file, true);
        this.printer = new CSVPrinter(out, CSVFormat.DEFAULT );

        Reader reader = Files.newBufferedReader(Paths.get(fileName));
        this.parser = new CSVParser(reader, CSVFormat.DEFAULT);
    }

    public Transaction add(Transaction transaction) {
        UUID id = UUID.randomUUID();
        transaction.setTransactionId(id.toString());
        try {
            printer.printRecord(transaction.getTransactionId(), (T) transaction.getUserId(), transaction.getAmount(),
                    transaction.getDescription(), transaction.getDate());
            printer.close();
        } catch (IOException e) {
            throw new DataAccessException("Error while accessing data file", e);
        }
        return transaction;
    }

    public Transaction<T,E> getByUserIdAndTransactionId(E userId, T transactionId) throws RecordNotFoundException {

        CSVRecord record = findOnFile( transactionId, 0);
        String recordUserId = record.get(1);
        if (!recordUserId.equals( String.valueOf(userId))){
            throw new RecordNotFoundException("No record found");
        }

        Transaction<T,E> transaction = mapTransaction(record, userId);
        return transaction;
    }

    public List<Transaction<T,E>> list(E userId) {

        List<CSVRecord> records;
        try {
            records = parser.getRecords();
        } catch (IOException e) {
            throw new DataAccessException("Error while accessing data file", e);
        }

        return records.stream()
                .filter( e -> String.valueOf(userId).equals(e.get(1)))
                .map( e -> mapTransaction(e, userId))
                .collect(Collectors.toList());
    }

    private CSVRecord findOnFile(T toFind, int index) throws RecordNotFoundException {
        List<CSVRecord> records;
        try {
            records = parser.getRecords();
        } catch (IOException e) {
            throw new DataAccessException("Error while accessing data file", e);
        }

        for (CSVRecord record: records) {
            if ( String.valueOf(toFind).equals(record.get(index)) ){
                return record;
            }
        }
        throw new RecordNotFoundException("No record found");
    }

    private Transaction<T,E> mapTransaction(CSVRecord record, E userId) {
        Transaction<T,E> transaction = new Transaction<>();
        transaction.setTransactionId((T)record.get(0));
        transaction.setUserId(userId);
        transaction.setAmount(Double.valueOf(record.get(2)));
        transaction.setDescription(record.get(3));
        transaction.setDate(new Date(record.get(4)));
        return transaction;
    }
}
