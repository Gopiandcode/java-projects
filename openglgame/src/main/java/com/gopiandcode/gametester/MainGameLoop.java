package com.gopiandcode.gametester;

import com.gopiandcode.entity.Entity;
import com.gopiandcode.models.TexturedModel;
import com.gopiandcode.render.DisplayManager;
import com.gopiandcode.render.Loader;
import com.gopiandcode.models.RawModel;
import com.gopiandcode.render.Renderer;
import com.gopiandcode.shaders.StaticShader;
import com.gopiandcode.textures.ModelTexture;
import com.gopiandcode.toolbox.Maths;
import org.joml.Vector3f;
import org.lwjgl.opengl.Display;

import java.io.File;

public class MainGameLoop {
    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", new File("C:\\Users\\gopia\\Downloads\\extracted\\lwjgl-2.9.3\\native\\windows").getAbsolutePath());
        DisplayManager.createDisplay();

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

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

        Entity entity = new Entity(texturedModel, new Vector3f(0,0,-2), 0,0,0,1);
        while(!Display.isCloseRequested()) {
            entity.increasePosition(0.0f,0,-0.01f);
//            entity.increaseRotation(0.2f,0,0);

            renderer.prepare();
            // game logic

            shader.start();
            renderer.render(entity, shader);
            shader.stop();
            // game rendering


            DisplayManager.updateDisplay();
        }
        shader.cleanUp();
        loader.cleanUp();

        DisplayManager.closeDisplay();
    }


}
