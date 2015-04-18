package be.cegeka.configurator.listener;

import java.net.InetAddress;

public interface MessageHandler<T extends Message> {
    Class<T> getMessageClass();
    String getMessageType();
    void handle(T message, InetAddress inetAddress);
}
