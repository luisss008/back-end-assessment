package assessment;

import com.google.gson.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@RunWith(JUnit4.class)
public class CommandTests {

    public static final String TEST_TRANSACTION_FILE_NAME = "test.csv";

    @Before
    public void setUp() throws IOException {
        File file = new File(TEST_TRANSACTION_FILE_NAME);
        FileWriter fileWriter = new FileWriter(file, false);
        fileWriter.write("");
    }

    @Test
    public void testAddCommandResponse(){
        String [] args = new String []{"3452", "add",
                "{\"amount\": 1.23, \"description\": \"cafe cucurucho\", \"date\":\"2018-12-30\", \"user_id\": 3452 }"};

        CommandLineEndpoint endpoint = new CommandLineEndpoint();
        JsonObject jsonObject = addTransaction(args, endpoint);
        Assert.assertNotNull(jsonObject.get("transaction_id"));
    }

    @Test
    public void testGetCommandResponse(){
        String [] args = new String []{"3452", "add",
                "{\"amount\": 1.23, \"description\": \"cafe cucurucho\", \"date\":\"2018-12-30\", \"user_id\": 3452 }"};

        CommandLineEndpoint endpoint = new CommandLineEndpoint();
        String response = endpoint.handle(args, TEST_TRANSACTION_FILE_NAME);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject =  jsonParser.parse(response).getAsJsonObject();

        args = new String []{"3452", jsonObject.get("transaction_id").getAsString()};
        String getResponse = endpoint.handle(args, TEST_TRANSACTION_FILE_NAME);
        Assert.assertEquals(response, getResponse);
    }

    @Test
    public void testGetCommandResponseRecordNotFound(){
        String [] args = new String []{"3452", "add",
                "{\"amount\": 1.23, \"description\": \"cafe cucurucho\", \"date\":\"2018-12-30\", \"user_id\": 3452 }"};

        CommandLineEndpoint endpoint = new CommandLineEndpoint();
        JsonObject jsonObject = addTransaction(args, endpoint);

        args = new String []{"1234", jsonObject.get("transaction_id").getAsString()};
        String getResponse = endpoint.handle(args, TEST_TRANSACTION_FILE_NAME);
        Assert.assertEquals("Transaction not found", getResponse);
    }

    @Test
    public void testListCommandResponse(){
        String [] args = new String []{"3452", "add",
                "{\"amount\": 1.23, \"description\": \"cafe cucurucho\", \"date\":\"2018-12-30\", \"user_id\": 3452 }"};

        CommandLineEndpoint endpoint = new CommandLineEndpoint();
        addTransaction(args, endpoint);

        args = new String []{"3452", "list"};
        String response = endpoint.handle(args, TEST_TRANSACTION_FILE_NAME);
        JsonArray array = new JsonParser().parse(response).getAsJsonArray();
        Assert.assertTrue(array.size() > 0);
    }

    @Test
    public void testSumCommandResponse(){
        String [] args = new String []{"3452", "add",
                "{\"amount\": 23.2, \"description\": \"cafe cucurucho\", \"date\":\"2018-12-30\", \"user_id\": 3452 }"};

        CommandLineEndpoint endpoint = new CommandLineEndpoint();
        addTransaction(args, endpoint);
        addTransaction(args, endpoint);

        args = new String []{"3452", "sum"};
        String response = endpoint.handle(args, TEST_TRANSACTION_FILE_NAME);
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        Assert.assertEquals(jsonObject.get("user_id").toString(), "3452");
        Assert.assertEquals("46.4", jsonObject.get("sum").getAsString());
    }

    private JsonObject addTransaction(String[] args, CommandLineEndpoint endpoint) {
        String response = endpoint.handle(args, TEST_TRANSACTION_FILE_NAME);
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(response).getAsJsonObject();
    }
}
