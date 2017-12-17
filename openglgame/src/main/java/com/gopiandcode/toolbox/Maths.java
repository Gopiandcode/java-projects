package com.gopiandcode.toolbox;

import com.gopiandcode.entity.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Maths {
    public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {
        return new Matrix4f()
                .identity()
                .translate(translation)
                .rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0))
                .rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0))
                .rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1))
                .scale(scale);
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f()
                .identity()
                .rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1,0,0))
                .rotation((float) Math.toRadians(camera.getYaw()), new Vector3f(0,1,0))
                .translate(
                        -1 * camera.getPosition().x,
                        -1 * camera.getPosition().y,
                        -1 * camera.getPosition().z
                );
        return viewMatrix;
    }

}
