package be.cegeka.configurator.serverRegistery;

interface MulticasterListener {
    void messageArrived(MulticastMessage multicastMessage);
}
