package com.gopiandcode.render;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;


import static java.awt.DisplayMode.BIT_DEPTH_MULTI;
import static java.awt.DisplayMode.REFRESH_RATE_UNKNOWN;

public class DisplayManager {

    private static final int HEIGHT = 720;
    private static final int WIDTH = 1280;
    private static final int FPSCAP = 120;

    public static void createDisplay() {
        ContextAttribs attribs = new ContextAttribs(3, 2);
        attribs.withForwardCompatible(true);
        attribs.withProfileCore(true);
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), attribs);
            Display.setTitle("An example display");
        } catch (LWJGLException e) {
            e.printStackTrace();
        }


        // Tell OPENGL where to draw
        GL11.glViewport(0,0, WIDTH, HEIGHT);



    }
    public static void updateDisplay() {
       Display.sync(FPSCAP);
       Display.update();
    }

    public static void closeDisplay() {
        Display.destroy();
    }
}
