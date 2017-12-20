package com.gopiandcode.entity;

import com.gopiandcode.models.TexturedModel;
import com.gopiandcode.render.DisplayManager;
import org.joml.Vector3f;
import org.lwjgl.input.Keyboard;

import javax.swing.*;

import static org.lwjgl.input.Keyboard.KEYBOARD_SIZE;
import static org.lwjgl.input.Keyboard.KEY_D;

public class Player extends Entity {
    private static final float RUN_SPEED = 20f;
    private static final float TURN_SPEED = 160f;
    private static final float GRAVITY = -50f;
    private static final float JUMP_POWER = 30f;
    private static final float TERRAIN_HEIGHT = 0;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;
    private boolean isInAir = false;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move() {
        checkInputs();
        float frameTimeSeconds = DisplayManager.getFrameTimeSeconds();
        System.out.println("drotation: " + frameTimeSeconds + " * " + currentTurnSpeed);
        float ry = currentTurnSpeed * frameTimeSeconds;
        System.out.println("drotation: " + ry);
        this.increaseRotation(0, ry, 0);
        float distance = currentSpeed * frameTimeSeconds;

        upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);

        if(super.getPosition().y < TERRAIN_HEIGHT) {
            super.getPosition().y = TERRAIN_HEIGHT;
            upwardsSpeed = 0;
            isInAir = false;
        }

        float rotY = (float) Math.toRadians(this.getRotY());
        float dx = (float) (Math.sin(rotY) * distance);
        float dz = (float) (Math.cos(rotY) * distance);
        super.increasePosition(dx, 0, dz);
    }

    private void jump() {
        if(!isInAir) {
            this.upwardsSpeed = JUMP_POWER;
            isInAir = true;
        }
    }

    private void checkInputs() {
        if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
            this.currentSpeed = RUN_SPEED;
        } else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
            this.currentSpeed = -RUN_SPEED;
        } else {
            this.currentSpeed = 0;
        }

        if (Keyboard.isKeyDown(KEY_D)) {
           this.currentTurnSpeed = -TURN_SPEED;
        } else if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
            this.currentTurnSpeed = TURN_SPEED;
        } else {
            this.currentTurnSpeed = 0;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            jump();
        }
    }
}
