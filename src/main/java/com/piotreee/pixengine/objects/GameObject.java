package com.piotreee.pixengine.objects;

public interface GameObject extends Updatable {
    Transform getTransform();

    int getId();
}
