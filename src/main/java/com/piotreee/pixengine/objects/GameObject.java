package com.piotreee.pixengine.objects;

import com.piotreee.pixengine.math.Collider;

import java.util.Optional;

public interface GameObject extends Updatable {
    Transform getTransform();
    int getId();
    default Optional<Rigidbody> getRigidbody() {
        return Optional.empty();
    }

    default Optional<Collider> getCollider() {
        return Optional.empty();
    }
}
