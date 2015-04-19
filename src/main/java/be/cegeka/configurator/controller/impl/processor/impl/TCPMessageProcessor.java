package be.cegeka.configurator.controller.impl.processor.impl;

import be.cegeka.configurator.controller.impl.processor.JSONMessageHandler;
import be.cegeka.configurator.controller.impl.processor.MessageProcessor;
import com.google.common.base.Optional;

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
    public void addMessageHandler(JSONMessageHandler JSONMessageHandler) {
        messageHandlerRepo.add(JSONMessageHandler);
    }

    public void stop() {
        listenerDaemon.stop();
    }
}
