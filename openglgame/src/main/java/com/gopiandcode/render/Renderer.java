package com.gopiandcode.render;

import com.gopiandcode.entity.Entity;
import com.gopiandcode.models.RawModel;
import com.gopiandcode.models.TexturedModel;
import com.gopiandcode.shaders.StaticShader;
import com.gopiandcode.textures.ModelTexture;
import com.gopiandcode.toolbox.Maths;
import org.joml.Matrix4f;
import org.lwjgl.opengl.*;

import javax.xml.soap.Text;

public class Renderer {

    public static final float FOV = 70;
    public static final float FAR_PLANE = 1000;
    public static final float NEAR_PLANE = 0.1f;
    private Matrix4f projectionMatrix;

    public Renderer(StaticShader shader) {
        createProjectionMatrix();
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void prepare(){
        GL11.glClearColor(1, 0, 1, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    public void render(RawModel model) {
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    public void render(TexturedModel texturedModel) {
        RawModel model = texturedModel.getRawModel();
        ModelTexture modelTexture = texturedModel.getModelTexture();

         GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, modelTexture.getTextureId());
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);

   }

   public void render(Entity entity, StaticShader shader) {
       TexturedModel texturedModel= entity.getModel();
       RawModel model = texturedModel.getRawModel();
       ModelTexture modelTexture = texturedModel.getModelTexture();
       Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());

       GL30.glBindVertexArray(model.getVaoID());
       GL20.glEnableVertexAttribArray(0);
       GL20.glEnableVertexAttribArray(1);


       shader.loadTransformationMatrix(transformationMatrix);

       GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
       GL13.glActiveTexture(GL13.GL_TEXTURE0);
       GL11.glBindTexture(GL11.GL_TEXTURE_2D, modelTexture.getTextureId());
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());


        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);

   }
    private  void createProjectionMatrix() {
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) (1f / Math.tan(Math.toRadians(FOV/2f)) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustrum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00(x_scale);
        projectionMatrix.m11(y_scale);
        projectionMatrix.m22(-((FAR_PLANE + NEAR_PLANE) /frustrum_length));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2 * NEAR_PLANE + FAR_PLANE) / frustrum_length));
        projectionMatrix.m33(0);
    }


}
