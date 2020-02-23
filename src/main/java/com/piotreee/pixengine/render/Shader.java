package com.piotreee.pixengine.render;

import com.piotreee.pixengine.util.FileUtil;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private int programObject;
    private int vertexShaderObject;
    private int fragmentShaderObject;

    public Shader(String name) {
        programObject = glCreateProgram();

        vertexShaderObject = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShaderObject, FileUtil.getShader(name + "vs.glsl"));
        glCompileShader(vertexShaderObject);
        if (glGetShaderi(vertexShaderObject, GL_COMPILE_STATUS) != 1) {
            System.err.println(glGetShaderInfoLog(vertexShaderObject));
            System.exit(1);
        }

        fragmentShaderObject = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShaderObject, FileUtil.getShader(name + "fs.glsl"));
        glCompileShader(fragmentShaderObject);
        if (glGetShaderi(fragmentShaderObject, GL_COMPILE_STATUS) != 1) {
            System.err.println(glGetShaderInfoLog(fragmentShaderObject));
            System.exit(1);
        }

        glAttachShader(programObject, vertexShaderObject);
        glAttachShader(programObject, fragmentShaderObject);

        glBindAttribLocation(programObject, 0, "vertices");
        glBindAttribLocation(programObject, 1, "textures");

        glLinkProgram(programObject);
        if (glGetProgrami(programObject, GL_LINK_STATUS) != 1) {
            System.err.println(glGetProgramInfoLog(programObject));
            System.exit(1);
        }

        glValidateProgram(programObject);
        if (glGetProgrami(programObject, GL_VALIDATE_STATUS) != 1) {
            System.err.println(glGetProgramInfoLog(programObject));
            System.exit(1);
        }
    }

    public void setUniform(String uniformName, int value) {
        int location = glGetUniformLocation(programObject, uniformName);
        if (location != -1) glUniform1i(location, value);
    }

    public void setUniform(String uniformName, Vector4f value) {
        int location = glGetUniformLocation(programObject, uniformName);
        if (location != -1) glUniform4f(location, value.x, value.y, value.z, value.w);
    }

    public void setUniform(String uniformName, Matrix4f value) {
        int location = glGetUniformLocation(programObject, uniformName);
        FloatBuffer matrixData = BufferUtils.createFloatBuffer(16);
        value.get(matrixData);
        if (location != 1) glUniformMatrix4fv(location, false, matrixData);
    }

    public void bind() {
        glUseProgram(programObject);
    }

    @Override
    protected void finalize() throws Throwable {
        glDetachShader(programObject, vertexShaderObject);
        glDetachShader(programObject, fragmentShaderObject);
        glDeleteShader(vertexShaderObject);
        glDeleteShader(fragmentShaderObject);
        glDeleteProgram(programObject);
        super.finalize();
    }
}
