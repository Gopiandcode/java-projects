package com.gopiandcode.render;

import com.gopiandcode.entity.Camera;
import com.gopiandcode.entity.Entity;
import com.gopiandcode.entity.Light;
import com.gopiandcode.models.TexturedModel;
import com.gopiandcode.shaders.StaticShader;
import com.gopiandcode.shaders.TerrainShader;
import com.gopiandcode.terrains.Terrain;
import org.joml.Matrix4f;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer {
    public static final float FOV = 70;
    public static final float FAR_PLANE = 1000;
    public static final float NEAR_PLANE = 0.1f;
    private Matrix4f projectionMatrix;

    private StaticShader shader = new StaticShader();
    private EntityRenderer renderer;
    private TerrainRenderer terrainRenderer;
    private TerrainShader terrainShader = new TerrainShader();

    private Map<TexturedModel, List<Entity>> entities = new HashMap<>();
    private List<Terrain> terrains = new ArrayList<>();

    public MasterRenderer() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        renderer = new EntityRenderer(shader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
    }

    private void createProjectionMatrix() {
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) (1f / Math.tan(Math.toRadians(FOV / 2f)) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustrum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00(x_scale);
        projectionMatrix.m11(y_scale);
        projectionMatrix.m22(-((FAR_PLANE + NEAR_PLANE) / frustrum_length));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2 * NEAR_PLANE + FAR_PLANE) / frustrum_length));
        projectionMatrix.m33(0);

    }

    private void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClearColor(1, 0, 1, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void render(Light sun, Camera camera) {
        prepare();

        terrainShader.start();
        terrainShader.loadLight(sun);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();


        shader.start();
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);
        renderer.render(entities);
        shader.stop();


        entities.clear();
        terrains.clear();
    }
    public void cleanUp() {
        shader.cleanUp();
        terrainShader.cleanUp();
    }

    public void processEntity(Entity entity) {
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);

        if (batch != null) {
            batch.add(entity);
        } else {
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

    public void processTerrain(Terrain terrain) {
        terrains.add(terrain);
    }

}
