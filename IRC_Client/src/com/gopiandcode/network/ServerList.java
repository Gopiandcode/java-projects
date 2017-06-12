package com.gopiandcode.network;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.Serializable;
import java.util.*;

/**
 * Created by gopia on 11/06/2017.
 */
public class ServerList extends Observable implements Serializable, Cloneable {
    private static ServerList server_list;
    private List<Server> list;
    private List<Observer> observers = new ArrayList<>();

    public ServerList getSerializable() {
        try {
            return (ServerList) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ServerList get() {
        if(server_list == null) {
            server_list = new ServerList();
        }
        return server_list;
    }

    public static void SetServers(List<Server> servers) {
        ServerList list = get();
        list.setList(servers);
    }

    private void setList(List<Server> servers){ list = servers;}

    private ServerList() {
        list = new ArrayList<>();
    }

    public void add(Server s) {
        list.add(s);
        setChanged();
        notifyObservers();
    }

    public int getIndex(Server s) {
        return list.indexOf(s);
    }

    public void remove(int index) {
        list.remove(index);
        setChanged();
        notifyObservers();
    }

    public List<Server> getList() {
        return new ArrayList<>(list);
    }

    public static void setList(ServerList list) {

        if(server_list != null) {
            list.observers = server_list.observers;
        } else {
            list.observers = new ArrayList<>();
        }

        server_list = list;

    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        observers.add(o);
    }
}
