package be.cegeka.configurator.serverRegistery.impl;

import be.cegeka.configurator.message.MessageSender;
import be.cegeka.configurator.messageProcessor.MessageHandler;
import be.cegeka.configurator.socket.Session;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class PingHandler implements MessageHandler<PingMessage> {
    private String uuid;
    private MessageSender messageSender;

    public PingHandler(MessageSender messageSender, String uuid) {
        this.uuid = uuid;
        this.messageSender = messageSender;
    }

    @Override
    public Class<PingMessage> getMessageClass() {
        return PingMessage.class;
    }

    @Override
    public String getMessageType() {
        return PingMessage.TYPE;
    }

    @Override
    public void handle(PingMessage message, Session session) {
        try {
            messageSender.send(session, uuid);
        } catch (IOException e) {
        }
    }
}
