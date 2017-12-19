package com.gopiandcode.entity;

import org.joml.Vector3f;
import org.lwjgl.input.Keyboard;

import java.security.Key;

public class Camera {
    private Vector3f position = new Vector3f(0,5,0);
    private float pitch;
    private float yaw;
    private float roll;


    public void move() {
        if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.z -= 0.02;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.z += 0.02;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.x += 0.02f;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            position.x -= 0.02f;
        }
    }

    public Camera() {
        this.pitch = 0;
        this.yaw = 0;
        this.roll = 0;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
