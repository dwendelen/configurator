package be.cegeka.configurator.controller.impl.processor.impl;

import be.cegeka.configurator.controller.impl.Daemon;
import be.cegeka.configurator.controller.impl.JSONMessage;
import be.cegeka.configurator.controller.impl.processor.JSONMessageHandler;
import be.cegeka.configurator.socket.Socket;
import com.google.common.base.Optional;
import org.codehaus.jackson.map.ObjectMapper;

public class ListenerDaemon extends Daemon<JSONMessage> {
    private MessageHandlerRepo messageHandlerRepo;

    public ListenerDaemon(Socket socket, ObjectMapper objectMapper, MessageHandlerRepo messageHandlerRepo) {
        super(socket, objectMapper, 0);
        this.messageHandlerRepo = messageHandlerRepo;
    }

    @Override
    protected Optional<? extends Class<? extends JSONMessage>> deriveMessageClass(String type) {
        Optional<JSONMessageHandler<JSONMessage>> messageHandlerOptional = messageHandlerRepo.get(type);
        if(!messageHandlerOptional.isPresent()) {
            return Optional.absent();
        }

        JSONMessageHandler<JSONMessage> JSONMessageHandler = messageHandlerOptional.get();
        return Optional.of(JSONMessageHandler.getMessageClass());
    }

    @Override
    protected void messageArrived(JSONMessage JSONMessage, String inetAddress) {
        Optional<JSONMessageHandler<JSONMessage>> messageHandlerOptional = messageHandlerRepo.get(JSONMessage.getType());
        if(!messageHandlerOptional.isPresent()) {
            return;
        }

        JSONMessageHandler<JSONMessage> JSONMessageHandler = messageHandlerOptional.get();
        JSONMessageHandler.handle(JSONMessage, inetAddress);
    }
}
