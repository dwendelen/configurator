package be.cegeka.configurator.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NullSession implements Session {
    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public InputStream read() throws IOException {
        return null;
    }

    @Override
    public OutputStream write() throws IOException {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
