package com.battleship;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.battleship.view.MainMenuView;

public class Battleship extends Game {
	private SpriteBatch batch;
	private AssetManager assetManager;

    /**
     * Creates the game. Initializes the sprite batch and asset manager.
     * Also shows the menu.
     */
	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		
		showMenu();
	}

    /**
     * Show menu.
     */
    private void showMenu() {
        setScreen(new MainMenuView());
    }

    @Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}

    /**
     * Returns the asset manager used to load all textures and sounds.
     *
     * @return the asset manager
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * Returns the sprite batch used to improve drawing performance.
     *
     * @return the sprite batch
     */
    public SpriteBatch getBatch() {
        return batch;
    }
}
