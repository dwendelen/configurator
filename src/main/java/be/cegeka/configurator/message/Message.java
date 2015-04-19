package be.cegeka.configurator.message;

public interface Message {
    public String getType();
    public void setType(String messageType);
    public String getUuid();
    public void setUuid(String uuid);
}
