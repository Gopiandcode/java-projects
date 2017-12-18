package com.gopiandcode.render;

import com.gopiandcode.entity.Entity;
import com.gopiandcode.models.RawModel;
import com.gopiandcode.models.TexturedModel;
import com.gopiandcode.shaders.StaticShader;
import com.gopiandcode.textures.ModelTexture;
import com.gopiandcode.toolbox.Maths;
import org.joml.Matrix4f;
import org.lwjgl.opengl.*;

import java.util.List;
import java.util.Map;

public class EntityRenderer {

    StaticShader shader;

    public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(Map<TexturedModel, List<Entity>> entities) {

        for (TexturedModel model : entities.keySet()) {
            prepareTexturedModel(model);

            List<Entity> batch = entities.get(model);
            for (Entity entity : batch) {
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModel();
        }
    }


    private void prepareInstance(Entity entity) {

        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
    }

    private void prepareTexturedModel(TexturedModel texturedModel) {
        RawModel model = texturedModel.getRawModel();
        ModelTexture modelTexture = texturedModel.getModelTexture();
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        shader.loadShineVariables(modelTexture.getShineDamper(), modelTexture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, modelTexture.getTextureId());
    }

    private void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(2);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }


}
