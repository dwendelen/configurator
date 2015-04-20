package be.cegeka.configurator.messageProcessor.impl;

import be.cegeka.configurator.message.Daemon;
import be.cegeka.configurator.message.Message;
import be.cegeka.configurator.messageProcessor.MessageHandler;
import be.cegeka.configurator.socket.Session;
import be.cegeka.configurator.socket.Socket;
import com.google.common.base.Optional;
import org.codehaus.jackson.map.ObjectMapper;

public class ListenerDaemon extends Daemon<Message> {
    private MessageHandlerRepo messageHandlerRepo;

    public ListenerDaemon(Socket socket, ObjectMapper objectMapper, MessageHandlerRepo messageHandlerRepo) {
        super(socket, objectMapper, 0);
        this.messageHandlerRepo = messageHandlerRepo;
    }

    @Override
    protected Optional<? extends Class<? extends Message>> deriveMessageClass(String type) {
        Optional<MessageHandler<Message>> messageHandlerOptional = messageHandlerRepo.get(type);
        if(!messageHandlerOptional.isPresent()) {
            return Optional.absent();
        }

        MessageHandler<Message> messageHandler = messageHandlerOptional.get();
        return Optional.of(messageHandler.getMessageClass());
    }

    @Override
    protected void messageArrived(Message message, Session session) {
        Optional<MessageHandler<Message>> messageHandlerOptional = messageHandlerRepo.get(message.getType());
        if(!messageHandlerOptional.isPresent()) {
            return;
        }

        MessageHandler<Message> messageHandler = messageHandlerOptional.get();
        messageHandler.handle(message, session);
    }
}
