package com.piotreee.game.packets;

import com.piotreee.game.objects.TestGameObject;
import com.piotreee.pixengine.objects.GameObject;

import java.io.Serializable;
import java.util.List;

public class UpdatePacket implements Serializable {
    private String date;
    private String connected;
    private List<TestGameObject> gameObjects;

    public UpdatePacket(String date, String connected, List<TestGameObject> gameObjects) {
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

    public List<TestGameObject> getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(List<TestGameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }
}
