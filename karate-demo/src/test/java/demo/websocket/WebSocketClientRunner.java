package demo.websocket;

import com.intuit.karate.Logger;
import com.intuit.karate.http.WebSocketClient;
import com.intuit.karate.http.WebSocketOptions;
import demo.TestBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author pthomas3
 */
class WebSocketClientRunner {

    private static final Logger logger = new Logger();

    private WebSocketClient client;
    private String result;

    @BeforeAll
    static void beforeAll() {
        TestBase.beforeAll();
    }

    @Test
    void testWebSocketClient() throws Exception {
        String port = System.getProperty("demo.server.port");
        WebSocketOptions options = new WebSocketOptions("ws://localhost:" + port + "/websocket");
        options.setTextConsumer(text -> {
            logger.debug("websocket listener text: {}", text);
            synchronized (this) {
                result = text;
                notify();
            }
        });
        client = new WebSocketClient(options, logger);
        client.send("Billie");
        synchronized (this) {
            wait();
        }
        assertEquals("hello Billie !", result);
    }

}
