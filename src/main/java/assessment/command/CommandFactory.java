package assessment.command;

import assessment.command.domain.*;
import assessment.command.exception.ParseCommandException;
import assessment.adapter.CommandLineTransactionAdapter;
import com.google.gson.Gson;

public class CommandFactory {

    public static final String ADD_COMMAND = "add";
    public static final String LIST_COMMAND = "list";
    public static final String SUM_COMMAND = "sum";

    public static Command getCommand(String [] args, CommandLineTransactionAdapter service) throws ParseCommandException {

        validateInput(args);
        String applicationCommand = args[1];

        switch ( applicationCommand ){
            case ADD_COMMAND:
                return createAddCommand(args, service);
            case LIST_COMMAND:
                return createListCommand(args, service);
            case SUM_COMMAND:
                return createSumCommand(args, service);
            default:
                return createGetCommand(args, service);
        }
    }

    private static void validateInput(String[] args) throws ParseCommandException {
        if (args.length == 0 || args.length == 1 ){
            throw new ParseCommandException("Missing arguments");
        }
    }

    private static Command createGetCommand(String [] args, CommandLineTransactionAdapter service) throws ParseCommandException {
        int userId = getInt(args, 0);
        String transactionId = args[1];
        return new GetCommand(service, userId, transactionId);
    }

    private static Command createSumCommand(String [] args, CommandLineTransactionAdapter service) throws ParseCommandException {
        int userId = getInt(args, 0);
        return new SumCommand(service, userId);
    }

    private static Command createListCommand(String [] args, CommandLineTransactionAdapter service) throws ParseCommandException {
        int userId = getInt(args, 0);
        return new ListCommand(service, userId);
    }

    private static Command createAddCommand(String [] args, CommandLineTransactionAdapter service) throws ParseCommandException {
        int userId = getInt(args, 0);
        String transactionJson = args[2];
        isValidJson(transactionJson);
        return new AddCommand(service, userId, transactionJson);
    }

    private static int getInt(String[] args, int index) throws ParseCommandException {
        try {
            return Integer.valueOf(args[index]);
        }catch ( NumberFormatException ex ){
            throw new ParseCommandException("Invalid command format", ex);
        }
    }

    public static boolean isValidJson(String Json) throws ParseCommandException {
        Gson gson = new Gson();
        try {
            gson.fromJson(Json, Object.class);
            return true;
        } catch (com.google.gson.JsonSyntaxException ex) {
            throw new ParseCommandException("Invalid json format", ex);
        }
    }
}
