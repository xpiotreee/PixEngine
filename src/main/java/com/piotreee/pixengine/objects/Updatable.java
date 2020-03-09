package com.piotreee.pixengine.objects;

public interface Updatable {
    default void update(float delta, boolean isServer) {
    }

}
