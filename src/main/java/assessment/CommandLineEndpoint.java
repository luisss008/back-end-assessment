package assessment;


import assessment.adapter.CommandLineTransactionAdapter;
import assessment.adapter.impl.CommandLineTransactionStorageAdapter;
import assessment.command.CommandFactory;
import assessment.command.domain.Command;
import assessment.command.exception.ParseCommandException;
import assessment.data.TransactionDao;
import assessment.data.impl.CSVTransactionDao;

import java.io.IOException;

public class CommandLineEndpoint {

    public String handle(String [] args, String storageFileName){
        TransactionDao<String,Integer> transactionDao = null;
        try {
            transactionDao = new CSVTransactionDao(storageFileName);
        } catch (IOException e) {
            System.out.println("Error occurred while creating file storage : " + e.getCause());
            System.exit(1);
        }

        CommandLineTransactionAdapter service = new CommandLineTransactionStorageAdapter(transactionDao);
        Command command = null;
        try {
            command = CommandFactory.getCommand(args, service);
        } catch (ParseCommandException e) {
            System.out.println("Error occurred while parsing command: " + e.getCause());
        }

        return command.execute();
    }


}
