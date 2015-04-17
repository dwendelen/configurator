package be.cegeka.configurator.serverRegistery;

import java.util.UUID;

class MulticastMessage {
    private String magicString = "be.cegeka.configurator.magicString";
    private UUID uuid;
    private String hostname;
    private String address;

    public UUID getUuid() {
        return uuid;
    }

    public String getHostname() {
        return hostname;
    }

    public String getAddress() {
        return address;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMagicString() {
        return magicString;
    }

    public void setMagicString(String magicString) {
        this.magicString = magicString;
    }
}
