package be.cegeka.configurator.listener;

public interface Listener {
    void start();
    int getPort();
    void addMessageHandler(MessageHandler messageHandler);
}
