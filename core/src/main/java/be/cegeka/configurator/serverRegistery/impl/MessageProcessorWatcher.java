package be.cegeka.configurator.serverRegistery.impl;

import be.cegeka.configurator.messageProcessor.MessageProcessorListener;

public class MessageProcessorWatcher implements MessageProcessorListener {
    private MulticastServerRegistery multicastServerRegistery;

    public MessageProcessorWatcher(MulticastServerRegistery multicastServerRegistery) {
        this.multicastServerRegistery = multicastServerRegistery;
    }

    @Override
    public void stopped() {
        multicastServerRegistery.stop();
    }
}
