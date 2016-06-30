package com.framework.opengl;

import java.util.*;

public class OpenglShaderManager {
    private static OpenglShaderManager instance;
    private ArrayList<OpenglProgram> programs;

    private OpenglShaderManager(){
        programs = new ArrayList<OpenglProgram>();
    }

    static public OpenglShaderManager get() {
        if(instance == null) {
            instance = new OpenglShaderManager();
        }
        return instance;
    }

    public void clear() {
        programs.clear();
        programs = new ArrayList<OpenglProgram>();
    }

    public OpenglProgram getShader(String vs, String fs) {
        OpenglProgram shader = null;
        Boolean found = false;

        for(OpenglProgram program : programs) {
            if(program.getFragFilename().equalsIgnoreCase(fs) && program.getVertexFilename().equalsIgnoreCase(vs)) {
                shader = program;
                found = true;
                break;
            }
        }

        if(!found) {
            shader = new OpenglProgram();
            shader.pushShaders(vs, fs);
            programs.add(shader);
        }

        return shader;
    }
}
