package com.toxicscorpus.cleanframework.io.connection;

import java.net.Socket;

public class ServerClient extends Client{

    private Server server;

    public ServerClient(Socket socket) {
        init(socket);
    }
    
    public void setServer(Server server) {
        this.server = server;
    }
    
    @Override
    public boolean disconnect() {
        if(super.disconnect()) {
            server.removeClient(this);
            setServer(null);
            return true;
        }
        return false;
    }
    
}
