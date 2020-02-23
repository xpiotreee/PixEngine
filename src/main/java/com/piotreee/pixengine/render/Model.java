package com.piotreee.pixengine.render;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Model implements Renderable {
    public static final Model RECTANGLE = new Model(
            new float[]{
                    -0.5f, +0.5f, //+0.0f,
                    +0.5f, +0.5f, //+0.0f,
                    +0.5f, -0.5f, //+0.0f,
                    -0.5f, -0.5f, //+0.0f,
            },
            new float[]{
                    0, 0,
                    1, 0,
                    1, 1,
                    0, 1,
            },
            new int[]{
                    0, 1, 2,
                    2, 3, 0,
            }
    );
    private int drawCount;
    private int vertexObject;
    private int textureCoordObject;
    private int indexObject;

    public Model(float[] vertices, float[] texCoords, int[] indices) {
        drawCount = indices.length;

        vertexObject = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexObject);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);

        textureCoordObject = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, textureCoordObject);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(texCoords), GL_STATIC_DRAW);

        indexObject = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexObject);

        IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
        buffer.put(indices);
        buffer.flip();

        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    @Override
    protected void finalize() throws Throwable {
        glDeleteBuffers(vertexObject);
        glDeleteBuffers(textureCoordObject);
        glDeleteBuffers(indexObject);
        super.finalize();
    }

    private FloatBuffer createBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    @Override
    public void render(Shader shader, Camera camera, Matrix4f world) {
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, vertexObject);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, textureCoordObject);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexObject);
        glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }
}
