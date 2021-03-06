package com.gopiandcode.gametester;

import com.gopiandcode.entity.Camera;
import com.gopiandcode.entity.Entity;
import com.gopiandcode.entity.Light;
import com.gopiandcode.entity.Player;
import com.gopiandcode.models.TexturedModel;
import com.gopiandcode.render.*;
import com.gopiandcode.models.RawModel;
import com.gopiandcode.shaders.StaticShader;
import com.gopiandcode.terrains.Terrain;
import com.gopiandcode.textures.ModelTexture;
import com.gopiandcode.textures.TerrainTexture;
import com.gopiandcode.textures.TerrainTexturePack;
import org.joml.Vector3f;
import org.lwjgl.opengl.Display;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainGameLoop {
    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", new File("C:\\Users\\gopia\\Downloads\\extracted\\lwjgl-2.9.3\\native\\windows").getAbsolutePath());
        DisplayManager.createDisplay();

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();


        List<Entity> entities = new ArrayList<>();
        entities.add(createEntityEntity(loader, "tree", "tree", new Vector3f(0, 0, -25), 1, 10));
        entities.add(createEntityEntity(loader, "tree", "tree", new Vector3f(3, 0, -25), 1, 10));

        List<Light> light = new ArrayList<>();
        light.add(new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1), new Vector3f(1.0f,0.01f,0.002f)));
        light.add(new Light(new Vector3f(10, 10, -70), new Vector3f(0.8f, 0.8f, 0.8f)));
        light.add(new Light(new Vector3f(30, 40, -50), new Vector3f(1, 1, 1),new Vector3f(1.0f,0.01f,0.002f)));

        //******************************TERRAIN STUFF ************************************
        List<Terrain> terrains = new ArrayList<>();
        terrains.add(createTerrain(loader, 0, -1, "heightmap"));
//        terrains.add(createTerrain(loader, -1, -1, "heightmap"));

        //********************************************************************************


        MasterRenderer renderer = new MasterRenderer();

        RawModel bunnyModel = OBJLoader.loadObjModel("bunny", loader);
        TexturedModel stanfordBunny = new TexturedModel(bunnyModel, new ModelTexture(loader.loadTexture("white")));

        Player player = new Player(stanfordBunny, new Vector3f(5, 0, -25), 0, 0, 0, 1);
        entities.add(player);

        Camera camera = new Camera(player);

        while (!Display.isCloseRequested()) {

//            entity.increasePosition(0.0f,0,-0.01f);
            for(Entity entity : entities)
                if(entity != player)
                    entity.increaseRotation(0f, 0.2f, 0.0f);

            // game logic
            player.move(terrains.get(0));
            camera.move(terrains.get(0));

            for(Terrain terrain : terrains)
                renderer.processTerrain(terrain);

            for(Entity entity: entities)
                renderer.processEntity(entity);


            // game rendering
            renderer.render(light, camera);


            DisplayManager.updateDisplay();
        }
        shader.cleanUp();
        loader.cleanUp();

        DisplayManager.closeDisplay();
    }

    private static Terrain createTerrain(Loader loader, int gridX, int gridZ, String heightmap) {
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));


        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

        return new Terrain(gridX, gridZ, loader, texturePack, blendMap, heightmap);
    }

    private static Entity createEntityEntity(Loader loader, String tree, String stallTexture, Vector3f position, int reflectivity, int shineDamper) {
        ModelTexture texture = new ModelTexture(loader.loadTexture(stallTexture));
        TexturedModel texturedModel = new TexturedModel(OBJLoader.loadObjModel(tree, loader), texture);
        texture.setReflectivity(reflectivity);
        texture.setShineDamper(shineDamper);
        return new Entity(texturedModel, position, 0, 0, 0, 1);
    }


}
