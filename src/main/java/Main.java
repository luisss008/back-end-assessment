import assessment.CommandLineEndpoint;

public class Main {

    public static final String TRANSACTION_FILE_NAME = "transactions.csv";

    public static void main(String [] args) {
        System.out.println(new CommandLineEndpoint().handle(args,TRANSACTION_FILE_NAME));
    }
}