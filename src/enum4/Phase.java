/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package enum4;

import java.util.EnumMap;
import java.util.Map;

/**
 *
 */
public enum Phase {

    SOLID, LIQUID, GAS;

    public enum Transition {
        MELT(SOLID, LIQUID), FREEZE(LIQUID, SOLID), BOIL(LIQUID, GAS), CONDENSE(GAS, LIQUID), SUBLIME(SOLID, GAS), DEPOSIT(GAS, SOLID);

        private final Phase src;
        private final Phase dst;

        Transition(Phase src, Phase dst) {
            this.src = src;
            this.dst = dst;
        }

        private static final Map<Phase, Map<Phase, Transition>> MATCH = new EnumMap<Phase, Map<Phase, Transition>>(Phase.class);

        static {
            for (Phase src : Phase.values()) {
                MATCH.put(src, new EnumMap<Phase, Transition>(Phase.class));
            }
            for (Transition dst : Transition.values()) {
                MATCH.get(dst.src).put(dst.dst, dst);
            }
        }

        public static Transition getTransitionFrom(Phase src, Phase dst) {
            return MATCH.get(src).get(dst);
        }

        @Override
        public String toString() {
            return this.src.name() + " to " + this.dst.name() + " called " + this.name();
        }
    }
}
