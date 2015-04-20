package be.cegeka.configurator.socket.impl;

import be.cegeka.configurator.socket.ListeningContext;
import be.cegeka.configurator.socket.Session;
import be.cegeka.configurator.socket.Socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;

public class TCPSocket implements Socket {
    @Override
    public ListeningContext listen(int port) throws IOException {
        return new TCPListeningContext(port);
    }

    @Override
    public Session open(String inetAddress, int port) throws IOException {
        return new TCPSession(inetAddress, port);
    }

    private class TCPListeningContext implements ListeningContext {
        private ServerSocket serverSocket;

        public TCPListeningContext(int port) throws IOException {
            serverSocket = new ServerSocket(port);
        }

        @Override
        public Session waitForSession() throws IOException {
            return new TCPSession(serverSocket.accept());
        }

        @Override
        public void close() throws IOException {
            serverSocket.close();
        }

        @Override
        public int getPort() {
            return serverSocket.getLocalPort();
        }
    }

    private class TCPSession implements Session {
        private java.net.Socket socket;

        public TCPSession(java.net.Socket socket) {
            this.socket = socket;
        }

        public TCPSession(String server, int port) throws IOException {
            socket = new java.net.Socket(server, port);
        }


        @Override
        public String getAddress() {
            return socket.getInetAddress().getHostAddress();
        }

        @Override
        public InputStream read() throws IOException {
            return new InputStream() {
                private InputStream inputStream = socket.getInputStream();
                @Override
                public int read() throws IOException {
                    int read = inputStream.read();
                    if(read == 0) {
                        return -1;
                    }

                    return read;
                }

                @Override
                public void close() throws IOException {
                }
            };
        }

        @Override
        public OutputStream write() throws IOException {
            return new OutputStream() {
                private OutputStream outputStream = socket.getOutputStream();

                @Override
                public void write(int i) throws IOException {
                    outputStream.write(i);
                }

                @Override
                public void close() throws IOException {
                    outputStream.write(0);
                }

                @Override
                public void flush() throws IOException {
                    outputStream.flush();
                }
            };
        }

        @Override
        public void close() throws IOException {
            socket.close();
        }
    }

}
