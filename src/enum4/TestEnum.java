/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package enum4;

import enum4.Phase.Transition;

public class TestEnum {

    public static void main(String[] args) {
        testPhase();
    }

    /**
     *testOperation
     */
    private static void testOperation() {
        double earthWeight = 2.232;
        double mass = earthWeight / Planet.EARTH.surfaceGravity();
        for (Planet p : Planet.values()) {
            System.out.printf("Weight on %s is %f%n", p, p.surfaceWeight(mass));
        }

        double x = 1.2;
        double y = 2.4;
        for (Operation op : Operation.values()) {
            System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
        }

        double z = Operation.DIVIDE.apply(x, y);
    }

    private static void testPhase() {
        System.out.println(Transition.getTransitionFrom(Phase.SOLID, Phase.LIQUID));
    }
}
