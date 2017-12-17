package com.gopiandcode.gametester;

import com.gopiandcode.models.TexturedModel;
import com.gopiandcode.render.DisplayManager;
import com.gopiandcode.render.Loader;
import com.gopiandcode.models.RawModel;
import com.gopiandcode.render.Renderer;
import com.gopiandcode.shaders.StaticShader;
import com.gopiandcode.textures.ModelTexture;
import org.lwjgl.opengl.Display;

public class MainGameLoop {
    public static void main(String[] args) {
        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

         float[] vertices = {
                 -0.5f, 0.5f,
                 0, -0.5f,
                 -0.5f, 0,
                 0.5f, -0.5f,
                 0, 0.5f,
                 0.5f, 0f };


        float[] textureCoords = {
                 0, 0,
                 0, 1,
                 1, 1,
                 1, 0
         };
        int[] indices = {
                0,
                1,
                3,
                3,
                1,
                2
        };

        RawModel model = loader.loadToVao(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("image"));

        TexturedModel texturedModel = new TexturedModel(model, texture);
        while(!Display.isCloseRequested()) {

            renderer.prepare();
            // game logic

            shader.start();
            renderer.render(texturedModel);
            shader.stop();
            // game rendering


            DisplayManager.updateDisplay();
        }
        shader.cleanUp();

        DisplayManager.closeDisplay();
    }
    
    
}
