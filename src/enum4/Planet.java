/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package enum4;

/**
 *
 * @author chentao
 * @version $Id: Planet.java, v 0.1 2016年4月26日 下午2:35:22 chentao Exp $
 */
public enum Planet {
    //水星
    MERCURY(3.302e+23, 2.439e6),
    //金星
    VENUS(4.869e+24, 6.052e6),
    //地球
    EARTH(5.975e+24, 6.378e6),
    // 火星
    MARS(6.419e+23, 3.393e6),
    //木星
    JUPITER(1.899e+27, 7.149e7),
    //土星
    SATURN(5.685e+26, 6.027e7),
    //天王星
    URANUS(8.683e+25, 2.556e7),
    //海王星
    NEPTUNE(1.024e+26, 2.477e7);
    //星球质量
    private final double mass;
    //星球半径
    private final double radius;
    //表面重力
    private final double surfaceGravity;

    private static final double G = 6.67300E-11;

    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
        surfaceGravity = G * mass / (radius * radius);
    }

    public double mass() {
        return mass;
    }

    public double radius() {
        return radius;
    }

    public double surfaceGravity() {
        return surfaceGravity;
    }

    //F=ma
    public double surfaceWeight(double mass) {
        return mass * surfaceGravity;
    }
}
