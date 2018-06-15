package assessment.command.domain;


import assessment.adapter.CommandLineTransactionAdapter;

public class ListCommand implements Command{

    private CommandLineTransactionAdapter service;
    private int userId;

    public ListCommand(CommandLineTransactionAdapter service, int userId) {
        this.service = service;
        this.userId = userId;
    }

    @Override
    public String execute() {
        return service.list(userId);
    }

}