package com.gopiandcode.shaders;

import com.gopiandcode.entity.Camera;
import com.gopiandcode.entity.Light;
import com.gopiandcode.toolbox.Maths;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class TerrainShader extends  ShaderProgram{
     private static final String VERTEX_FILE = "src/main/java/com/gopiandcode/shaders/terrainVertexShader";
    private static final String FRAGMENT_FILE = "src/main/java/com/gopiandcode/shaders/terrainFragmentShader";
    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColour;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_skyColour;

    public TerrainShader()
    {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");

    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColour = super.getUniformLocation("lightColour");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_skyColour = super.getUniformLocation("skyColour");
    }


    public void loadSkyColour(float r, float g, float b) {
        super.loadVector(location_skyColour, new Vector3f(r, g, b));
    }
    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void  loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadLight(Light light) {
        super.loadVector(location_lightPosition, light.getPosition());
        super.loadVector(location_lightColour, light.getColour());
    }
    public void loadShineVariables(float damer, float reflectivity) {
        super.loadFloat(location_shineDamper, damer);
        super.loadFloat(location_reflectivity, reflectivity);
    }

}
