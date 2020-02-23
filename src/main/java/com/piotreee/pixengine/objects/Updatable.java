package com.piotreee.pixengine.objects;

import com.piotreee.pixengine.io.Input;

public interface Updatable {
    void update(double delta, Input input);
}
