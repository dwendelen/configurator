package be.cegeka.configurator.message;

import be.cegeka.configurator.socket.ListeningContext;
import be.cegeka.configurator.socket.NullSession;
import be.cegeka.configurator.socket.Session;
import be.cegeka.configurator.socket.Socket;
import com.google.common.base.Optional;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

public abstract class Daemon<T extends Message> {
    private Runner runner = new Runner();
    private Socket socket;
    private ObjectMapper objectMapper;
    private int port;

    protected Daemon(Socket socket, ObjectMapper objectMapper, int port) {
        this.socket = socket;
        this.objectMapper = objectMapper;
        this.port = port;
    }

    private String deriveName() {
        return this.getClass().getSimpleName();
    }

    public void start() {
        runner.start();
    }

    public void stop() {
        runner.interrupt();
    }

    public Optional<Integer> getPort() {
        return runner.getPort();
    }

    protected abstract Optional<? extends Class<? extends T>> deriveMessageClass(String type);

    protected abstract void messageArrived(T message, Session session);

    private class Runner extends Thread {
        public Runner() {
            super(deriveName());
        }
        private ListeningContext listeningContext;

        public void start() {
            try {
                listeningContext = socket.listen(port);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            super.start();
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if (isInterrupted()) {
                        break;
                    }
                    listen();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeConnection();
            }
        }

        @Override
        public void interrupt() {
            closeConnection();
            super.interrupt();
        }

        private void listen() {
            try {
                listenThrowingExceptions();
            } catch (IOException e) {
                if(!isInterrupted()) {
                    e.printStackTrace();
                }
                return;
            }
        }

        private void listenThrowingExceptions() throws IOException {
            Session session = listeningContext.waitForSession();
            try {
                handle(session);
            } finally {
                session.close();
            }
        }

        private void handle(Session session) throws IOException {
            JsonNode jsonNode = objectMapper.readTree(session.read());

            if(!jsonNode.has("type")) {
                return;
            }

            Optional<? extends Class<? extends T>> messageClass = deriveMessageClass(jsonNode.get("type").asText());

            if(!messageClass.isPresent()) {
                return;
            }

            T message = objectMapper.readValue(jsonNode, messageClass.get());
            messageArrived(message, session);
        }

        private void closeConnection() {
            if(listeningContext != null) {
                try {
                    listeningContext.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public Optional<Integer> getPort() {
            if(listeningContext == null) {
                return Optional.absent();
            }
            return Optional.of(listeningContext.getPort());
        }
    }
}
