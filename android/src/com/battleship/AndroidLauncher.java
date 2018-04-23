package com.battleship;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.battleship.Battleship;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        //Saving battery
        config.useAccelerometer = false;
        config.useGyroscope = false;
        config.useCompass = false;
        config.useRotationVectorSensor = false;

        //Hide the status bar
        config.hideStatusBar = true;

        initialize(new Battleship(), config);
    }
}
