package com.gopiandcode.shaders;

import com.gopiandcode.entity.Camera;
import com.gopiandcode.entity.Light;
import com.gopiandcode.toolbox.Maths;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.List;

public class TerrainShader extends  ShaderProgram{
     private static final String VERTEX_FILE = "src/main/java/com/gopiandcode/shaders/terrainVertexShader";
    private static final String FRAGMENT_FILE = "src/main/java/com/gopiandcode/shaders/terrainFragmentShader";
    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition[];
    private int location_lightColour[];
    private int location_attenuation[];
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_skyColour;
    private int location_backgroundTexture;
    private int location_rTexture;
    private int location_gTexture;
    private int location_bTexture;
    private int location_blendMap;

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
        location_lightColour = new int[4];
        location_lightPosition = new int[4];
        location_attenuation = new int[4];
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_skyColour = super.getUniformLocation("skyColour");

        location_backgroundTexture = super.getUniformLocation("backgroundTexture");
        location_rTexture = super.getUniformLocation("rTexture");
        location_gTexture = super.getUniformLocation("gTexture");
        location_bTexture = super.getUniformLocation("bTexture");
        location_blendMap = super.getUniformLocation("blendMap");

        for(int i = 0; i < 4; i++) {
            location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i +"]");
            location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
            location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
        }
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

    public void loadLight(List<Light> light) {
        for(int i = 0; i < 4; i++) {
            if(i < light.size()) {
                super.loadVector(location_lightPosition[i], light.get(i).getPosition());
                super.loadVector(location_lightColour[i], light.get(i).getColour());
                super.loadVector(location_attenuation[i], light.get(i).getAttenuation());
            } else {
                super.loadVector(location_lightPosition[i], new Vector3f());
                super.loadVector(location_lightColour[i], new Vector3f());
                super.loadVector(location_attenuation[i], new Vector3f(1,0,0));
            }
        }
    }
    public void loadShineVariables(float damer, float reflectivity) {
        super.loadFloat(location_shineDamper, damer);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    public void connectTextureUnits() {
        super.loadInt(location_backgroundTexture, 0);
        super.loadInt(location_rTexture, 1);
        super.loadInt(location_gTexture, 2);
        super.loadInt(location_bTexture, 3);
        super.loadInt(location_blendMap, 4);

    }

}
