import assessment.command.CommandFactory;
import assessment.command.domain.Command;
import assessment.command.exception.ParseCommandException;
import assessment.data.TransactionDao;
import assessment.data.impl.CSVTransactionDao;
import assessment.adapter.CommandLineTransactionAdapter;
import assessment.adapter.impl.CommandLineTransactionStorageAdapter;

import java.io.IOException;

public class Main {

    public static void main(String [] args) throws IOException {

        TransactionDao<String,Integer> transactionDao = new CSVTransactionDao();
        CommandLineTransactionAdapter service = new CommandLineTransactionStorageAdapter(transactionDao);

        Command command = null;
        try {
            command = CommandFactory.getCommand(args, service);
        } catch (ParseCommandException e) {
            System.out.println(e.getCause());
        }

        System.out.println(command.execute());
    }
}
