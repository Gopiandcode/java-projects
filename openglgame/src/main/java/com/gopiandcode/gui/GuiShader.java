package com.gopiandcode.gui;

import com.gopiandcode.shaders.ShaderProgram;

public class GuiShader extends ShaderProgram {
    private static final String FRAGMENT_FILE = "src/main/java/com/gopiandcode/shaders/guiFragmentShader";
    private static final String VERTEX_FILE = "src/main/java/com/gopiandcode/shaders/guiVertexShader";
    private int location_transformationMatrix;

    public GuiShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}
