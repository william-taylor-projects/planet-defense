package com.framework.graphics;

import java.util.ArrayList;

import com.framework.IRenderable;

public class RenderQueue {
    private static final int RESERVE_SIZE = 100;
    private ArrayList<IRenderable> renderables;

    public RenderQueue() {
        renderables = new ArrayList<IRenderable>(RESERVE_SIZE);
    }

    public void put(IRenderable renderable) {
        if(renderable != null) {
            renderables.add(renderable);
        }
    }
    public void renderObjects() {
        for(IRenderable object : renderables) {
            object.render();
        }

        renderables.clear();
    }
}