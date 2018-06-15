package assessment.command.domain;


import assessment.adapter.CommandLineTransactionAdapter;

public class AddCommand implements Command{

    private CommandLineTransactionAdapter service;
    private String transactionJson;
    private int userId;

    public AddCommand(CommandLineTransactionAdapter service, int userId, String transactionJson) {
        this.service = service;
        this.transactionJson = transactionJson;
        this.userId = userId;
    }

    public String execute() {
        return service.add(userId, transactionJson);
    }
}
