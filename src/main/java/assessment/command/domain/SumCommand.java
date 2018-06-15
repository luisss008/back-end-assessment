package assessment.command.domain;


import assessment.adapter.CommandLineTransactionAdapter;

public class SumCommand implements Command{

    private CommandLineTransactionAdapter service;
    private int userId;

    public SumCommand(CommandLineTransactionAdapter service, int userId) {
        this.service = service;
        this.userId = userId;
    }

    @Override
    public String execute() {
        return service.sum(userId);
    }
}