package com.toxicscorpus.cleanframework.io.connection;

public class ClientMessage {

    private final Client client;
    private final String text;

    public ClientMessage(Client client, String text) {
        this.client = client;
        this.text = text;
    }

    public Client getClient() {
        return client;
    }

    public String getText() {
        return text;
    }
}
