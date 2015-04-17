package be.cegeka.configurator.serverRegistery;

import be.cegeka.configurator.server.Server;
import be.cegeka.configurator.server.ServerFactory;

class MulticastServerRegistery implements MulticasterListener, ServerRegistery {
    private Multicaster multicaster;
    private Repository repository;
    private ServerFactory serverFactory;

    public MulticastServerRegistery(Multicaster multicaster, Repository repository, ServerFactory serverFactory) {
        this.multicaster = multicaster;
        this.repository = repository;
        this.serverFactory = serverFactory;
        multicaster.setMulticasterListener(this);
    }

    @Override
    public void start() {
        multicaster.start();
    }

    @Override
    public void stop() {
        multicaster.stop();
    }

    @Override
    public void messageArrived(MulticastMessage multicastMessage) {
        if(multicastMessage.getUuid().equals(repository.getThisServer().getUuid())) {
            return;
        }

        Server newServer = serverFactory.createNewServer(multicastMessage.getUuid(), 0, multicastMessage.getHostname());
        repository.addServer(newServer);


        System.out.println(multicastMessage.getAddress());
        System.out.println(multicastMessage.getUuid().toString());
        System.out.println(multicastMessage.getHostname());
    }
}
