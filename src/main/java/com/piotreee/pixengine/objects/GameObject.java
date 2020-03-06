package com.piotreee.pixengine.objects;

import java.util.Optional;

public interface GameObject extends Updatable {
    Transform getTransform();
    int getId();

    default Optional<Rigidbody> getRigidbody() {
        return Optional.empty();
    }

//    default void setRigidbody(Rigidbody rigidbody) {
//
//    }
}
