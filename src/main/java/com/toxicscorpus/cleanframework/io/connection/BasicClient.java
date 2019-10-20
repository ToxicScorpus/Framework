package com.toxicscorpus.cleanframework.io.connection;

public class BasicClient {
    
    private Client client;
    private String lastMessage;
    private boolean awaitsRespone;
    private MessageHandler asyncMessageHandler;

    public BasicClient(String host, int port, MessageHandler asyncMessageHandler) {
        this.asyncMessageHandler = asyncMessageHandler;
        client = new Client();
        connect(host, port);
    }

    public void connect(String host, int port) {
        client.connect(host, port);
        new Thread(this::asyncReceiver).start();
    }

    public void disconnect() {
        client.disconnect();
    }

    public void send(String text) {
        client.sendLine(text);
    }

    public String sendWithResponse(String text) {
        awaitsRespone = true;
        send(text);
        if (waitForResponse(50)) {
            return lastMessage;
        }
        return null;
    }

    private String receive() {
        String line = client.receiveLine();
        return line;
    }

    private boolean waitForResponse(int timeout) {
        int fails = 0;
        while (awaitsRespone) {
            fails++;
            if (fails > timeout) {
                return false;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }
        }
        return true;
    }

    private void asyncReceiver() {
        while (client.isConnected()) {
            String text = receive();
            if (awaitsRespone) {
                lastMessage = text;
                awaitsRespone = false;
            } else {
                if (text == null) {
                    client.disconnect();
                    continue;
                }
                if (client.isConnected()) {
                    asyncMessageHandler.handle(new ClientMessage(client, text));
                }
            }
        }
    }
}
