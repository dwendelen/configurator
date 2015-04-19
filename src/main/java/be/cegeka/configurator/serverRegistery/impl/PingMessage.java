package be.cegeka.configurator.serverRegistery.impl;

import be.cegeka.configurator.message.Message;

public class PingMessage implements Message {
    @Override
    public String getType() {
        return "ping";
    }

    @Override
    public void setType(String messageType) {

    }
}
