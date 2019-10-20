package com.toxicscorpus.cleanframework.io.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private boolean open;

    private ServerSocket server;

    private List<ServerClient> clients;

    public Server() {
        clients = new ArrayList<>();
    }

    public boolean openServer(int port) {
        if (open) {
            return false;
        }
        try {
            server = new ServerSocket(port);
            open = true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("[Server] Failed opening server on port: " + port);
            return false;
        }
        return true;
    }

    public boolean waitForConnection() {
        if (!open) {
            return false;
        }
        try {
            Socket socket = server.accept();
            ServerClient client = new ServerClient(socket);
            client.setServer(this);
            clients.add(client);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("[Server] Failed waiting for connection!");
            return false;
        }
        return true;
    }

    public void closeServer() {
        if (!open) {
            return;
        }
        try {
            closeAllClients();
            server.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("[Server] Failed closing server!");
        }
    }

    private void closeAllClients() {
        while (clients.size() > 0) {
            clients.get(0).disconnect();
        }
    }

    public void sendLineToAll(String text) {
        for (int i = 0; i < clients.size(); i++) {
            clients.get(i).sendLine(text);
        }
    }

    public void removeClient(ServerClient client) {
        try {
            clients.remove(client);
        } catch (Exception e) {
        }
    }

    public ServerClient getClient(int index) {
        return clients.get(index);
    }

    public ServerClient getLastClient() {
        return clients.get(clients.size() - 1);
    }

}
