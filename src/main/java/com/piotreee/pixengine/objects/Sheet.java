package com.piotreee.pixengine.objects;

import org.joml.Vector2i;

import java.util.Arrays;

public class Sheet<E> {
    private Object[][] elements;

    public Sheet(int width, int height) {
        this.elements = new Object[width][height];
    }

    public Sheet(E[][] elements) {
        this.elements = elements;
    }

    public void setElement(E element, int x, int y) {
        this.elements[x][y] = element;
    }

    public void setRow(int index, E[] elements) {
        this.elements[index] = elements;
    }

    public E getElement(int x, int y) {
        return (E) elements[x][y];
    }

    public E getElement(Vector2i position) {
        return getElement(position.x, position.y);
    }

    public E[][] getElements() {
        return (E[][]) elements;
    }

    public void setElements(E[][] elements) {
        this.elements = elements;
    }

    public int getWidth() {
        return elements.length;
    }

    public int getHeight() {
        return elements[0].length;
    }

    public int getSize() {
        return getWidth() * getHeight();
    }

    @Override
    public String toString() {
        return Arrays.deepToString(elements);
    }
}
