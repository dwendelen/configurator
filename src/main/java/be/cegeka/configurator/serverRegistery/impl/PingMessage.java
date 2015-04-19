package be.cegeka.configurator.serverRegistery.impl;

import be.cegeka.configurator.message.Message;

public class PingMessage implements Message {

    public static final String TYPE = "ping";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void setType(String messageType) {

    }
}
