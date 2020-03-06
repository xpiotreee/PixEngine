package com.piotreee.pixengine.objects;

import java.util.HashMap;

public class Registry<T> {
    private HashMap<Integer, Class<? extends T>> objects = new HashMap<>();

    public void register(int id, Class<? extends T> objectClass) {
        objects.put(id, objectClass);
    }

    public Class<? extends T> get(int id) {
        return objects.get(id);
    }
}
