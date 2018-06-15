package assessment.adapter;


import java.util.Optional;

public interface CommandLineTransactionAdapter {
    String add(int userId, String jsonString);
    Optional<String> get(int userId, String transactionId);
    String list(int userId);
    String sum(int userId);
}
