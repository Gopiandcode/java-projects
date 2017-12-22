package com.gopiandcode.entity;

import com.gopiandcode.models.TexturedModel;
import org.joml.Vector3f;

public class Entity {
    private TexturedModel model;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;
    private int textureIndex = 0;

    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
       this(model,position, rotX,rotY,rotZ, scale, 0);
    }



    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, int textureIndex) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.textureIndex = textureIndex;
    }



    public void increasePosition(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public void increaseRotation(float rx,float ry,float rz){
        this.rotX += rx;
        this.rotY += ry;
        this.rotZ += rz;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public TexturedModel getModel() {
        return model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getRotX() {
        return rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public float getScale() {
        return scale;
    }

    public float getTextureXOffset() {
        int column = textureIndex % model.getModelTexture().getNumberOfRows();
        return (float)column/(float)model.getModelTexture().getNumberOfRows();
    }

    public float getTextureYOffset() {
        int row = textureIndex / model.getModelTexture().getNumberOfRows();
        return (float)row/(float)model.getModelTexture().getNumberOfRows();
    }

}
