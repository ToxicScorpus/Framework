package com.toxicscorpus.cleanframework.io.connection;

public class BasicServer {

    private final MessageHandler handler;
    private final Server server;

    public BasicServer(int port, MessageHandler handler) {
        server = new Server();
        server.openServer(port);
        this.handler = handler;
        new Thread(this::receiver).start();
    }

    public void stop() {
        server.closeServer();
    }

    public void broadcast(String text) {
        server.sendLineToAll(text);
    }

    private void clientListener(Client client) {
        while (client.isConnected()) {
            String line = client.receiveLine();
            if (line == null) {
                client.disconnect();
                continue;
            }
            handler.handle(new ClientMessage(client, line));
        }
    }

    private void receiver() {
        while (server.waitForConnection()) {
            new Thread(() -> clientListener(server.getLastClient())).start();
        }
    }

}
