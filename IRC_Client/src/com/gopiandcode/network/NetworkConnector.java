package com.gopiandcode.network;

import com.sun.istack.internal.NotNull;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by gopia on 11/06/2017.
 */
public class NetworkConnector extends Observable {
    private String username;
    private String password;
    private Socket server_socket;
    private BufferedReader server_reader;
    private BufferedWriter server_writer;
    private StringBuilder server_in_buffer = new StringBuilder();
    private StringBuilder server_out_buffer = new StringBuilder();
    private ReentrantLock server_in_lock = new ReentrantLock();
    private ReentrantLock server_out_lock = new ReentrantLock();
    volatile private boolean connected;
    private boolean initialized;
    private Server server;

    public boolean isConnected() {
        return connected;
    }

    public void disconnect() {
        connected = false;
        initialized = false;
        setChanged();
        notifyObservers();
    }

    public NetworkConnector() {

    }

    public boolean checkUsername() {
        return username != null && !username.equals("");
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public boolean initialize(@NotNull Server s) throws IOException {
        if(connected) return false;

        server = s;

        if(server.isSSL_enabled()) {
            server_socket = SSLSocketFactory.getDefault().createSocket(server.getAddress(), server.getPort());
        } else {
            server_socket = new Socket(server.getAddress(), server.getPort());
        }
        server_writer = new BufferedWriter(new OutputStreamWriter(server_socket.getOutputStream()));
        server_reader = new BufferedReader(new InputStreamReader(server_socket.getInputStream()));

        initialized = true;
        return true;
    }

    public boolean connect() throws IOException {

        if(!initialized) return false;
        if(password != null && !password.equalsIgnoreCase(""))
            server_writer.write("PASS " + password + "\r\n");

        server_writer.write("NICK " + username + "\r\n");
        server_writer.write("USER " + username + " 0 * : " + username + " Gopiandcode's Java IRC Client\r\n");
        server_writer.flush();

        String line;

        while((line = server_reader.readLine()) !=null) {
            if(line.toLowerCase().startsWith("ping ")) {
                server_out_lock.lock();

                server_writer.write("PONG "  + line.substring(5) + "\r\n");
                server_writer.flush();

                server_out_lock.unlock();
            } else {
                server_in_lock.lock();
                server_in_buffer.append(line + "\r\n");
                server_in_lock.unlock();
                setChanged();
                notifyObservers();
                if (line.contains("004"))
                    break;
                else if (line.contains("433"))
                    return false;
            }
        }

        server_writer.write("JOIN :" + server.getChannel() + "\r\n");
        server_writer.flush();

        connected = true;

        new Thread(new InputConnector()).start();
        new Thread(new OutputConnector()).start();
        return true;
    }

    public String getInput() {
        server_in_lock.lock();

        String s = server_in_buffer.toString();
        server_in_buffer.setLength(0);

        server_in_lock.unlock();
        return s;
    }

    public void sendString(String s) {
        server_out_lock.lock();

        server_out_buffer.append(s.replace("\r"," ").replace("\n"," "));

        server_out_lock.unlock();
    }

    private class InputConnector implements Runnable {
        @Override
        public void run() {
            String line;
            try {
                while(connected && !server_socket.isClosed() &&(line = server_reader.readLine()) != null) {
                    if(line.toLowerCase().startsWith("ping ")) {
                        server_out_lock.lock();

                        server_writer.write("PONG "  + line.substring(5) + "\r\n");
                        server_writer.flush();

                        server_out_lock.unlock();
                    } else {
                        server_in_lock.lock();

                        server_in_buffer.append(line + "\r\n");

                        server_in_lock.unlock();

                        setChanged();
                        notifyObservers();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                disconnect();
                if(server_in_lock.isHeldByCurrentThread()) server_in_lock.unlock();
                if(server_out_lock.isHeldByCurrentThread()) server_out_lock.unlock();
                try {
                    server_writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class OutputConnector implements Runnable {

        @Override
        public void run() {

            try {

                while(connected && !server_socket.isClosed()) {
                    server_out_lock.lock();

                    String s = server_out_buffer.toString();
                    if(s.length() != 0) {
                        server_out_buffer.setLength(0);

                        server_writer.write("PRIVMSG " + server.getChannel() + " :" + s + "\r\n");
                    }
                    server_out_lock.unlock();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                disconnect();
                if(server_out_lock.isHeldByCurrentThread())
                    server_out_lock.unlock();
                try {
                    server_writer.write("QUIT\r\n");
                    server_writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
