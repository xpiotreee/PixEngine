package com.piotreee.pixengine.networking;

import com.google.gson.Gson;

import java.io.Serializable;

public class Packet implements Serializable {
    private transient final static Gson gson = new Gson();
    private String type;
    private String object;

    public Packet(Class type, Object object) {
        this.type = type.getName();
        this.object = gson.toJson(object, type);
    }

    public Class getType() {
        try {
            return Class.forName(type);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setType(Class type) {
        this.type = type.getName();
    }

    public Object getObject() {
        return gson.fromJson(object, getType());
    }

    public void setObject(Object object) {
        this.object = gson.toJson(object, getType());
    }
}
