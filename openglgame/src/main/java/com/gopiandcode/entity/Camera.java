package com.gopiandcode.entity;

import org.joml.Vector3f;
import org.lwjgl.input.Mouse;

public class Camera {
    private Vector3f position = new Vector3f(0,5,0);
    private final Player player;
    private float pitch = 10;
    private float yaw;
    private float roll;

    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 0;

    public void move() {
        calculateZoom();
        calculateAngle();
        calculatePitch();

        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();

        calculateCameraPosition(horizontalDistance, verticalDistance);
        this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
    }

    public Camera(Player player) {
        this.player = player;
        this.pitch = 0;
        this.yaw = 0;
        this.roll = 0;
    }
    private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
        float theta = player.getRotY() + angleAroundPlayer;

        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
//        offsetX /= 1000;
//        offsetZ /= 1000;
//        verticalDistance /= 100;
        System.out.println("offsets: " +offsetX + ", " + verticalDistance + "," +  offsetZ );
        System.out.println("position: " +position.x+ ", " + position.y+ "," +  position.z);
        System.out.println("pitches: " +pitch+ ", " + roll+ "," +  yaw);
        position.x = player.getPosition().x - offsetX;
        position.z = player.getPosition().z - offsetZ;
        position.y = player.getPosition().y + verticalDistance;
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

    private float calculateHorizontalDistance() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }


    public float getDistanceFromPlayer() {
        return distanceFromPlayer;
    }

    public float getAngleAroundPlayer() {
        return angleAroundPlayer;
    }

    private void calculateZoom() {
        float zoomLevel = Mouse.getDWheel() * 0.1f;
        distanceFromPlayer += zoomLevel;
    }

    private void calculatePitch() {
        if((Mouse.isInsideWindow()) && Mouse.isButtonDown(0)) {
            float pitchChange = Mouse.getDY() * 0.1f;
            pitch -= pitchChange;
        }
    }

    private void calculateAngle() {
        if((Mouse.isInsideWindow()) && Mouse.isButtonDown(0)) {
            float angleAroundPlayerChange = Mouse.getDX() * 0.3f;
            angleAroundPlayer -= angleAroundPlayerChange;
        }
    }
}
