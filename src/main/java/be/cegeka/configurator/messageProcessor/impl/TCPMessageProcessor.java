package be.cegeka.configurator.messageProcessor.impl;

import be.cegeka.configurator.message.Daemon;
import be.cegeka.configurator.message.Message;
import be.cegeka.configurator.messageProcessor.MessageHandler;
import be.cegeka.configurator.messageProcessor.MessageProcessor;
import be.cegeka.configurator.socket.Socket;
import be.cegeka.configurator.socket.SocketFactory;
import com.google.common.base.Optional;
import org.codehaus.jackson.map.ObjectMapper;

import java.net.InetAddress;

public class TCPMessageProcessor implements MessageProcessor {
    private MessageHandlerRepo messageHandlerRepo;
    private ListenerDaemon listenerDaemon;

    public TCPMessageProcessor(ListenerDaemon listenerDaemon, MessageHandlerRepo messageHandlerRepo) {
        this.messageHandlerRepo = messageHandlerRepo;
        this.listenerDaemon = listenerDaemon;
    }

    public void start() {
        listenerDaemon.start();
    }

    @Override
    public Optional<Integer> getPort() {
        return listenerDaemon.getPort();
    }

    @Override
    public void addMessageHandler(MessageHandler messageHandler) {
        messageHandlerRepo.add(messageHandler);
    }

    public void stop() {
        listenerDaemon.stop();
    }
}
