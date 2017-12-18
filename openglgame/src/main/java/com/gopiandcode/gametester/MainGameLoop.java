package com.gopiandcode.gametester;

import com.gopiandcode.entity.Camera;
import com.gopiandcode.entity.Entity;
import com.gopiandcode.entity.Light;
import com.gopiandcode.models.TexturedModel;
import com.gopiandcode.render.*;
import com.gopiandcode.models.RawModel;
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

        RawModel model = OBJLoader.loadObjModel("stall", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));

        TexturedModel texturedModel = new TexturedModel(model, texture);

        Entity entity = new Entity(texturedModel, new Vector3f(0,0,-25), 0,0,0,1);
        Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));
        Camera camera = new Camera();

        texture.setReflectivity(1);
        texture.setShineDamper(10);

        MasterRenderer renderer = new MasterRenderer();

        while(!Display.isCloseRequested()) {

//            entity.increasePosition(0.0f,0,-0.01f);
            entity.increaseRotation(0f,0.2f,0.0f);

            // game logic
            camera.move();

            renderer.processEntity(entity);


            // game rendering
            renderer.render(light, camera);


            DisplayManager.updateDisplay();
        }
        shader.cleanUp();
        loader.cleanUp();

        DisplayManager.closeDisplay();
    }


}
