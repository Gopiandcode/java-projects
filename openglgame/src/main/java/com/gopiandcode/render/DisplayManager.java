package com.gopiandcode.render;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;


import static java.awt.DisplayMode.BIT_DEPTH_MULTI;
import static java.awt.DisplayMode.REFRESH_RATE_UNKNOWN;

public class DisplayManager {

    private static final int HEIGHT = 720;
    private static final int WIDTH = 1280;
    private static final int FPSCAP = 120;

    private static long lastFrameTime;
    private static float delta;
    public static void createDisplay() {
        ContextAttribs attribs = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), attribs);
            Display.setTitle("An example display");
        } catch (LWJGLException e) {
            e.printStackTrace();
        }


        // Tell OPENGL where to draw
        GL11.glViewport(0,0, WIDTH, HEIGHT);
        lastFrameTime = 0;
        delta = 0.0f;
    }
    public static void updateDisplay() {
       long currentFrameTime = getCurrentTime();
        System.out.println("Current frame time: " + currentFrameTime +  ", last frame time: " + lastFrameTime);
       delta = (currentFrameTime - lastFrameTime)/(float)Sys.getTimerResolution();
       lastFrameTime = currentFrameTime;

       Display.sync(FPSCAP);
       Display.update();
    }

    public static float getFrameTimeSeconds() {
        return delta;
    }

    public static void closeDisplay() {
        Display.destroy();
    }

    private static long getCurrentTime() {
        return (Sys.getTime());
    }
}
