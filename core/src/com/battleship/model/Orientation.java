package com.battleship.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Orientation {
    Horizontal, Vertical;

    private static final List<Orientation> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Orientation randomOrientation()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
