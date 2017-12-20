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
