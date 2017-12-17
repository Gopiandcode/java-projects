package com.gopiandcode.toolbox;

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
}
