package be.cegeka.configurator.serverRegistery.impl;

import be.cegeka.configurator.message.Message;

public class UnreachableMessage implements Message {
    public static final String TYPE = "unreachable";
    private String uuid;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void setType(String messageType) {

    }


    public String getUuid() {
        return uuid;
    }


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
