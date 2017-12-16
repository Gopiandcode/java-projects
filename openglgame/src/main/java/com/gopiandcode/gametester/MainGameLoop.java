package com.gopiandcode.gametester;

import com.gopiandcode.render.DisplayManager;
import com.gopiandcode.render.Loader;
import com.gopiandcode.render.RawModel;
import com.gopiandcode.render.Renderer;
import org.lwjgl.opengl.Display;

public class MainGameLoop {
    public static void main(String[] args) {
        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

         float[] vertices = { -0.5f, 0.5f, 0, -0.5f, -0.5f, 0, 0.5f, -0.5f, 0, 0.5f, 0.5f, 0f }; int[] indices = { 0,1,3, 3,1,2 };

        RawModel model = loader.loadToVao(vertices, indices);

        while(!Display.isCloseRequested()) {

            renderer.prepare();
            // game logic

            renderer.render(model);
            // game rendering


            DisplayManager.updateDisplay();
        }

        DisplayManager.closeDisplay();
    }
    
    
}
