package be.cegeka.configurator.controller;

import be.cegeka.configurator.controller.impl.JSONMessage;
import be.cegeka.configurator.server.Server;

public interface MessageController<T extends JSONMessage> {
    Class<T> getMessageClass();
    String getMessageType();
    void messageArrived(Server server, JSONMessage JSONMessage);
}
