package com.framework.opengl;

import static android.opengl.GLES20.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;

import com.framework.io.ResourceManager;

public class OpenglProgram {
    private Integer vertexShader;
    private Integer fragShader;
    private Integer program;

    private String vertexFilename;
    private String fragFilename;

    public OpenglProgram() {
        vertexShader = glCreateShader(GL_VERTEX_SHADER);
        fragShader = glCreateShader(GL_FRAGMENT_SHADER);
        program = glCreateProgram();
    }

    public void pushShaders(String VertexID, String FragID) {
        vertexFilename = VertexID;
        fragFilename = FragID;

        glShaderSource(vertexShader, loadShaderSource(VertexID));
        glShaderSource(fragShader, loadShaderSource(FragID));

        glCompileShader(vertexShader);
        glCompileShader(fragShader);

        debugInfo(vertexShader);
        debugInfo(fragShader);

        glAttachShader(program, vertexShader);
        glAttachShader(program, fragShader);
        glLinkProgram(program);
    }

    public int getUniform(String name) {
        return glGetUniformLocation(program, name);
    }

    public int getAttribute(String name) {
        return glGetAttribLocation(program, name);
    }

    public void startProgram() {
        glUseProgram(program);
    }

    public void endProgram() {
        glUseProgram(0);
    }

    public String getVertexFilename() {
        return vertexFilename;
    }


    public String getFragFilename() {
        return fragFilename;
    }

    private void debugInfo(int shader) {
        int[] compiled = new int[1];
        glGetShaderiv(shader, GL_COMPILE_STATUS, compiled, 0);

        if(compiled[0] == 0) {
            Log.e("Shader Error", glGetShaderInfoLog(shader));
        }
    }

    private String loadShaderSource(String filename) {
        InputStream inputStream = ResourceManager.get().getResource(filename);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder stringBuilder = new StringBuilder();
        String string;

        try	{
            while((string = bufferedReader.readLine()) != null)	{
                stringBuilder.append(string);
                stringBuilder.append('\n');
            }
        }
        catch(IOException e) {
            return null;
        }

        return stringBuilder.toString();
    }
}