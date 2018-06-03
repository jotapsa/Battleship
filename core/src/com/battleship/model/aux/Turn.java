package com.battleship.model.aux;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Turn {
    Blue, Red;

    private static final List<Turn> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Turn randomTurn()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
