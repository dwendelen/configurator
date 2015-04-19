package be.cegeka.configurator.serverRegistery.impl;

import be.cegeka.configurator.messageProcessor.MessageHandler;
import be.cegeka.configurator.server.Server;
import be.cegeka.configurator.socket.Session;
import com.google.common.base.Optional;

import java.io.IOException;

public class UnreachableHandler implements MessageHandler<UnreachableMessage> {
    private Repository repository;

    public UnreachableHandler(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Class<UnreachableMessage> getMessageClass() {
        return UnreachableMessage.class;
    }

    @Override
    public String getMessageType() {
        return UnreachableMessage.TYPE;
    }

    @Override
    public void handle(UnreachableMessage message, Session session) {
        Optional<Server> server = repository.getServer(message.getUuid());
        if(!server.isPresent()) {
            return;
        }

        server.get().send(new PingMessage());
    }
}
