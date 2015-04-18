package be.cegeka.configurator.serverRegistery;

import be.cegeka.configurator.server.Server;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.*;

class Multicaster {
    private Runner runner;
    private MulticasterListener multicasterListener;

    public Multicaster(Server thisServer) {
        this.runner = new Runner(thisServer);
    }

    public void setMulticasterListener(MulticasterListener multicasterListener) {
        this.multicasterListener = multicasterListener;
    }

    public void start() {
        runner.start();
    }

    public void stop() {
        runner.interrupt();
    }

    private class Runner extends Thread {
        private InetAddress multiCastAddress;
        private int PORT = 6548;
        private MulticastSocket multicastSocket;
        private ObjectMapper objectMapper = new ObjectMapper();
        private Server thisServer;

        private Runner(Server thisServer) {
            this.thisServer = thisServer;
        }

        @Override
        public void run() {
            try {
                init();
                sendHello();
                while (true) {
                    if (isInterrupted()) {
                        break;
                    }
                    listen();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeConnection();
            }
        }

        @Override
        public void interrupt() {
            closeConnection();
            super.interrupt();

        }

        private void closeConnection() {
            if (multicastSocket != null) {
                if (!multicastSocket.isClosed()) {
                    multicastSocket.close();
                }
            }
        }

        private void init() throws IOException {
            multiCastAddress = InetAddress.getByName("ff05::dace");
            multicastSocket = new MulticastSocket(PORT);
            multicastSocket.joinGroup(multiCastAddress);
        }

        private void sendHello() throws IOException {
            ServerInfoMessage serverInfoMessage = new ServerInfoMessage(thisServer);
            sendObject(serverInfoMessage);
        }

        private void sendObject(Object object) throws IOException {
            byte[] json = objectMapper.writeValueAsBytes(object);

            DatagramPacket datagramPacket = new DatagramPacket(json, json.length, multiCastAddress, PORT);
            multicastSocket.send(datagramPacket);
        }

        private void listen() throws IOException {
            byte[] buffer = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            multicastSocket.receive(datagramPacket);

            ServerInfoMessage serverInfoMessage;
            try {
                serverInfoMessage = objectMapper.readValue(buffer, 0, datagramPacket.getLength(), ServerInfoMessage.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return;
            }

            if (multicasterListener != null) {
                multicasterListener.messageArrived(datagramPacket.getAddress(), serverInfoMessage);
            }
        }
    }
}
