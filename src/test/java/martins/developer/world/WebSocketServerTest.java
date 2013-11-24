package martins.developer.world;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketClient;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class WebSocketServerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServerTest.class);
    private WebSocketServer subject;

    @Before
    public void before() {
        subject = new WebSocketServer();
    }

    @Test
    public void testStartAndBuild() throws Exception {
        subject.buildAndStartServer(8080, "127.0.0.1");
        WebSocketClient client = new WebSocketClient();
        Future<WebSocket.Connection> connectionFuture = client.open(new URI("ws://localhost:8080/websocket"), new WebSocket() {
            @Override
            public void onOpen(Connection connection) {
                LOGGER.info("onOpen");
                try {
                    connection.sendMessage("TestMessage");
                } catch (IOException e) {
                    LOGGER.error("Failed to send message: "+e.getMessage(), e);
                }
            }
            @Override
            public void onClose(int i, String s) {
                LOGGER.info("onClose");
            }
        });
        WebSocket.Connection connection = connectionFuture.get(2, TimeUnit.SECONDS);
        assertThat(connection, is(notNullValue()));
        connection.close();
        subject.stopServer();
        Thread.sleep(1000);
        assertThat(subject.lastReceivedMessage, is("TestMessage"));
    }
}
