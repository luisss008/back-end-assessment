package assessment.adapter.impl;


import assessment.data.TransactionDao;
import assessment.data.domain.Transaction;
import assessment.data.exception.RecordNotFoundException;
import assessment.adapter.CommandLineTransactionAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Optional;

public class CommandLineTransactionStorageAdapter implements CommandLineTransactionAdapter {

    private TransactionDao<String, Integer> transactionDao;

    public CommandLineTransactionStorageAdapter(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public String add(int userId, String jsonString) {
        Transaction<String, Integer> transaction = new Gson().fromJson(jsonString,  new TypeToken<Transaction<String,Integer>>(){}.getType());
        transaction.setUserId(userId);
        return transactionDao.add(transaction).getTransactionId();
    }

    public Optional<String> get(int userId, String transactionId) {
        Transaction<String, Integer> transaction = null;
        try {
            transaction = transactionDao.getByUserIdAndTransactionId(userId, transactionId);
        } catch (RecordNotFoundException e) {
            return Optional.ofNullable(null);
        }
        return Optional.of(new Gson().toJson(transaction));
    }

    public String list(int userId) {
        List<Transaction<String, Integer>> list = transactionDao.list(userId);
        return new Gson().toJson(list);
    }

    public String sum(int userId) {
        List<Transaction<String,Integer>> list = transactionDao.list(userId);
        double sum = 0;
        for (Transaction<String,Integer> transaction : list) {
            sum = sum + transaction.getAmount();
        }
        return String.valueOf(sum);
    }
}
