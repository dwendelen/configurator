package be.cegeka.configurator.listener;

public class ListenerFactory {
    public Listener create() {
        return new TCPListener();
    }
}
