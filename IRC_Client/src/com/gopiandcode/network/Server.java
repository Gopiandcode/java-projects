package com.gopiandcode.network;

import java.io.Serializable;

/**
 * Created by gopia on 11/06/2017.
 */
public class Server implements Serializable {
    private String server_name;
    private String address;
    private String channel;
    private int port;
    private boolean SSL_enabled;

    public Server(String server_name, String address, String channel, int port, boolean SSL_enabled) {
        this.server_name = server_name;
        this.address = address;
        this.channel = channel;
        this.port = port;
        this.SSL_enabled = SSL_enabled;
    }


    public String getServer_name() {
        return server_name;
    }

    public void setServer_name(String server_name) {
        this.server_name = server_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isSSL_enabled() {
        return SSL_enabled;
    }

    public void setSSL_enabled(boolean SSL_enabled) {
        this.SSL_enabled = SSL_enabled;
    }

}
