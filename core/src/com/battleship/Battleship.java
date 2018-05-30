package com.battleship;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.battleship.controller.BoardController;
import com.battleship.model.GameModel;
import com.battleship.model.GameType;
import com.battleship.view.GameView;
import com.battleship.view.MainMenuView;
import com.battleship.view.PlacingView;

public class Battleship extends Game {
    private GameModel gameModel;

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

	public GameModel getGameModel(){
	    return this.gameModel;
    }

    /**
     * Show menu.
     */
    private void showMenu() {
        setScreen(new MainMenuView(this));
    }

    /**
     * Start GameModel.
     */
    public void startSingleplayerGame() {
        this.gameModel = new GameModel(GameType.SinglePlayer);
        setScreen(new PlacingView(this));
    }

    /**
     * Start GameModel.
     */
    public void startMultiplayerLocal() {
        this.gameModel = new GameModel(GameType.Multiplayer_local);
        setScreen(new GameView(this));
    }

    /**
     * Start GameModel.
     */
    public void startMultiplayer() {
        this.gameModel = new GameModel(GameType.Multiplayer);
        setScreen(new GameView(this));
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

    public void setGameView(){
        setScreen(new GameView(this));
    }
}
