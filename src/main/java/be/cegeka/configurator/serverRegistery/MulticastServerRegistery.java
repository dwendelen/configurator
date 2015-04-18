package be.cegeka.configurator.serverRegistery;

import be.cegeka.configurator.messageProcessor.MessageHandler;
import be.cegeka.configurator.server.Server;
import be.cegeka.configurator.server.ServerFactory;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

class MulticastServerRegistery implements JoinProtocolListener, ServerRegistery {
    private JoinProtocol joinProtocol;
    private Repository repository;
    private ServerFactory serverFactory;
    private ServerInfoHandler serverInfoHandler = new ServerInfoHandler();

    public MulticastServerRegistery(JoinProtocol joinProtocol, Repository repository, ServerFactory serverFactory) {
        this.joinProtocol = joinProtocol;
        this.repository = repository;
        this.serverFactory = serverFactory;
        joinProtocol.setJoinProtocolListener(this);
    }

    public void start() throws IOException {
        joinProtocol.start();
    }

    public void stop() {
        joinProtocol.stop();
    }

    @Override
    public List<? extends MessageHandler> getMessageHandlers() {
        return newArrayList(serverInfoHandler);
    }


    @Override
    public void newSereverArrived(InetAddress inetAddress, ServerInfoMessage serverInfoMessage) {
        System.out.println("MULTICAST");
        serverInfoHandler.handle(serverInfoMessage, inetAddress);

        Socket socket = null;
        try {
            socket = new Socket(inetAddress, serverInfoMessage.getPort());
            ObjectMapper objectMapper = new ObjectMapper();
            ServerInfoMessage serverInfoMessage1 = new ServerInfoMessage(repository.getThisServer());
            objectMapper.writeValue(socket.getOutputStream(), serverInfoMessage1);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket != null) {
                if(!socket.isClosed()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
             }
        }
    }

    private class ServerInfoHandler implements MessageHandler<ServerInfoMessage> {
        @Override
        public Class<ServerInfoMessage> getMessageClass() {
            return ServerInfoMessage.class;
        }

        @Override
        public String getMessageType() {
            return ServerInfoMessage.MESSAGE_TYPE;
        }

        @Override
        public void handle(ServerInfoMessage message, InetAddress inetAddress) {
            if(message.getUuid().equals(repository.getThisServer().getUuid())) {
                return;
            }

            Server newServer = serverFactory.createNewServer(message.getUuid(), inetAddress, message.getPort(), message.getHostname());
            repository.addServer(newServer);

            System.out.println(newServer.getUuid().toString());
            System.out.println(newServer.getHostname());
            System.out.println(newServer.getInetAddress().toString());
            System.out.println(newServer.getPort());
        }
    };
}
