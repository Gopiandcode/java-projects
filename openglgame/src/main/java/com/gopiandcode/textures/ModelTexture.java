package com.gopiandcode.textures;

public class ModelTexture {
    private final int textureId;
    private float shineDamper = 1;
    private boolean hasTrasnparency = false;
    private boolean useFakeLighting = false;

    public boolean shouldUseFakeLighting() {
        return useFakeLighting;
    }

    public void setUseFakeLighting(boolean useFakeLighting) {
        this.useFakeLighting = useFakeLighting;
    }

    public boolean isTransparent() {
        return hasTrasnparency;
    }

    public void setTrasnparency(boolean hasTrasnparency) {
        this.hasTrasnparency = hasTrasnparency;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    private float reflectivity = 0;

    public ModelTexture(int textureId) {
        this.textureId = textureId;
    }

    public int getTextureId() {
        return textureId;
    }
}
