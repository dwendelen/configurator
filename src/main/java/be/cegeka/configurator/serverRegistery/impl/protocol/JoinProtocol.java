package be.cegeka.configurator.serverRegistery.impl.protocol;

import be.cegeka.configurator.server.ServerInformation;
import be.cegeka.configurator.serverRegistery.impl.Repository;
import be.cegeka.configurator.serverRegistery.impl.message.ServerInfoMessage;
import be.cegeka.configurator.socket.*;
import be.cegeka.configurator.message.Daemon;
import be.cegeka.configurator.message.MessageSender;
import be.cegeka.configurator.server.Server;
import com.google.common.base.Optional;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.InetAddress;

public class JoinProtocol implements NewServerListener {
    public static final int PORT = 6548;
    public static final String MULTICAST = "ff05::dace";

    private MessageSender messageSender;
    private NewServerDaemon multicasterDaemon;
    private ServerInfoMessage serverInfoMessage;

    public JoinProtocol(Server thisServer, NewServerDaemon newServerDaemon, MessageSender messageSender) throws IOException {
        serverInfoMessage = new ServerInfoMessage(thisServer);
        this.multicasterDaemon = newServerDaemon;
        this.messageSender =  messageSender;
    }

    public void start() throws IOException {
        multicasterDaemon.start();
        messageSender.send(MULTICAST, PORT, serverInfoMessage);
    }

    public void stop() {
        multicasterDaemon.stop();
    }

    @Override
    public void newServerArrived(ServerInformation serverInformation) {
        Socket socket = new SocketFactory().createTCPSocket();
        MessageSender messageSender = new MessageSender(socket, new ObjectMapper());

        try {
            messageSender.send(serverInformation.getInetAddress(), serverInformation.getPort(), serverInfoMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
