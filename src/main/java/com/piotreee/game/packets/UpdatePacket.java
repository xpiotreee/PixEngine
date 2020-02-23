package com.piotreee.game.packets;

import com.piotreee.pixengine.objects.GameObject;

import java.io.Serializable;

public class UpdatePacket implements Serializable {
    private String date;
    private String connected;
    private GameObject[] gameObjects;

    public UpdatePacket(String date, String connected, GameObject[] gameObjects) {
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

    public GameObject[] getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(GameObject[] gameObjects) {
        this.gameObjects = gameObjects;
    }
}
