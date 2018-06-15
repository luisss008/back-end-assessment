package assessment.command.domain;


import assessment.adapter.CommandLineTransactionAdapter;

import java.util.Optional;

public class GetCommand implements Command{

    private static String NOT_FOUND = "Transaction not found";

    private CommandLineTransactionAdapter service;
    private int userId;
    private String transactionId;

    public GetCommand(CommandLineTransactionAdapter service, int userId, String transactionId) {
        this.service = service;
        this.userId = userId;
        this.transactionId = transactionId;
    }

    @Override
    public String execute() {
        Optional<String> response = service.get(userId, transactionId);
        if (response.isPresent()){
            return response.get();
        }
        return NOT_FOUND;
    }
}