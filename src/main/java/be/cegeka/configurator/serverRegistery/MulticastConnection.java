package be.cegeka.configurator.serverRegistery;

import be.cegeka.configurator.connection.ListeningContext;
import be.cegeka.configurator.connection.Session;
import be.cegeka.configurator.connection.Socket;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastConnection implements Socket {
    private InetAddress multiCastAddress;

    public MulticastConnection(InetAddress multiCastAddress) {
        this.multiCastAddress = multiCastAddress;
    }

    @Override
    public ListeningContext listen(int port) throws IOException {
        return new MultiCastListeningContext(port);
    }

    @Override
    public Session open(InetAddress inetAddress, int port) throws IOException {
        return new Write(inetAddress, port);
    }

    private class MultiCastListeningContext implements ListeningContext {
        private MulticastSocket multicastSocket;

        public MultiCastListeningContext(int port) throws IOException {
            multicastSocket = new MulticastSocket(port);
            multicastSocket.joinGroup(multiCastAddress);
        }

        @Override
        public Session waitForSession() throws IOException {
            Read read = new Read();
            read.init(multicastSocket);
            return read;
        }

        @Override
        public void close() throws IOException {
            multicastSocket.close();
        }
    }


    private class Read implements Session {
        private byte[] readBuffer = new byte[1024];
        DatagramPacket datagramPacket;

        public void init(MulticastSocket multicastSocket) throws IOException {
            datagramPacket = new DatagramPacket(readBuffer, readBuffer.length);
            multicastSocket.receive(datagramPacket);
        }

        @Override
        public InetAddress getAddress() {
            return datagramPacket.getAddress();
        }

        @Override
        public int getLocalPort() {
            return -1;
        }

        @Override
        public InputStream read() throws IOException {
            return new ByteArrayInputStream(readBuffer, 0, datagramPacket.getLength());
        }

        @Override
        public OutputStream write() throws IOException {
            throw new IOException("not supported");
        }

        @Override
        public void close() throws IOException {
        }
    }

    private class Write implements Session {
        private InetAddress address;
        private int port;
        private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);

        public Write(InetAddress address, int port) {
            this.address = address;
            this.port = port;
        }

        @Override
        public InetAddress getAddress() {
            return address;
        }

        @Override
        public int getLocalPort() {
            return -1;
        }

        @Override
        public InputStream read() throws IOException {
            throw new IOException("unsupported");
        }

        @Override
        public OutputStream write() throws IOException {
            return new OutputStream() {
                @Override
                public void write(int i) throws IOException {
                    byteArrayOutputStream.write(i);
                }

                @Override
                public void close() throws IOException {
                    send();
                }

                @Override
                public void flush() throws IOException {
                    byteArrayOutputStream.flush();
                }
            };
        }

        private void send() throws IOException {
            byte[] buffer = byteArrayOutputStream.toByteArray();
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, multiCastAddress, port);
            new MulticastSocket().send(datagramPacket);
        }

        @Override
        public void close() throws IOException {
            byteArrayOutputStream.close();
        }
    }
}
