package com.piotreee.game.packets;

import com.piotreee.game.objects.server.ServerGameObject;

import java.io.Serializable;
import java.util.List;

public class UpdatePacket implements Serializable {
    private String date;
    private String connected;
    private List<ServerGameObject> gameObjects;

    public UpdatePacket(String date, String connected, List<ServerGameObject> gameObjects) {
        this.date = date;
        this.connected = connected;
        this.gameObjects = gameObjects;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getConnected() {
        return connected;
    }

    public void setConnected(String connected) {
        this.connected = connected;
    }

    public List<ServerGameObject> getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(List<ServerGameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }
}
