package be.cegeka.configurator.messageProcessor;

import be.cegeka.configurator.message.Message;
import be.cegeka.configurator.socket.Session;

import java.net.InetAddress;

public interface MessageHandler<T extends Message> {
    Class<T> getMessageClass();
    String getMessageType();
    void handle(T message, Session session);
}
