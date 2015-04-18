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

class TCPMessageProcessor implements MessageProcessor {
    private MessageHandlerRepo messageHandlerRepo;
    private ListenerDaemon listenerDaemon;

    public TCPMessageProcessor(MessageHandlerRepo messageHandlerRepo) {
        ObjectMapper objectMapper = new ObjectMapper();
        this.messageHandlerRepo = messageHandlerRepo;
        Socket tcpSocket = new SocketFactory().createTCPSocket();
        listenerDaemon = new ListenerDaemon(tcpSocket, objectMapper);
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

    private class ListenerDaemon extends Daemon<Message> {

        protected ListenerDaemon(be.cegeka.configurator.socket.Socket socket, ObjectMapper objectMapper) {
            super(socket, objectMapper, 0);
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
        protected void messageArrived(Message message, InetAddress inetAddress) {
            Optional<MessageHandler<Message>> messageHandlerOptional = messageHandlerRepo.get(message.getType());
            if(!messageHandlerOptional.isPresent()) {
                return;
            }

            MessageHandler<Message> messageHandler = messageHandlerOptional.get();
            messageHandler.handle(message, inetAddress);
        }
    }


}
