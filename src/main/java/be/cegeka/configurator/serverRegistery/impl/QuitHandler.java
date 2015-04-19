package be.cegeka.configurator.serverRegistery.impl;

import be.cegeka.configurator.messageProcessor.MessageHandler;
import be.cegeka.configurator.server.Server;
import be.cegeka.configurator.socket.Session;

import java.io.IOException;

public class QuitHandler implements MessageHandler<QuitMessage> {
    private Repository repository;

    public QuitHandler(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Class<QuitMessage> getMessageClass() {
        return QuitMessage.class;
    }

    @Override
    public String getMessageType() {
        return QuitMessage.TYPE;
    }

    @Override
    public void handle(QuitMessage message, Session session) {
        System.out.println("QUIT");
        if(!repository.hasServer(message.getUuid())) {
            System.out.println("UNKNOWN");
            return;
        }

        System.out.println("DELETE AND SPREAD");
        repository.removeServerByUuid(message.getUuid());
        new Broadcaster(repository).broadcast(message);
    }
}
