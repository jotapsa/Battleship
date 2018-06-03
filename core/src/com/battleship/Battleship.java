package com.battleship;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.battleship.controller.GameController;
import com.battleship.model.aux.CellType;
import com.battleship.model.GameModel;
import com.battleship.model.aux.GameType;
import com.battleship.model.aux.ShipType;
import com.battleship.model.Turn;
import com.battleship.networking.Server;
import com.battleship.view.GameView;
import com.battleship.view.MainMenuView;
import com.battleship.view.PlacingView;
import com.battleship.view.MultiplayerView;
import com.battleship.view.RoomView;

public class Battleship extends Game {
    public String ipEnemy;
    public int defaultPort = 5000;

    private Server gameServer;
    private Thread gameServerThread;

    private GameModel gameModel;
    private GameController gameController;

	private SpriteBatch batch;
	private AssetManager assetManager;

    private Texture carrier;
    private Texture battleship;
    private Texture cruiser;
    private Texture submarine;

    private Texture freeHit;
    private Texture shipHit;

    /**
     * Creates the game. Initializes the sprite batch and asset manager.
     * Also shows the menu.
     */
	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();

		loadAssets();

		showMenu();
	}

	public GameModel getGameModel(){
	    return this.gameModel;
    }

    /**
     * Show menu.
     */
    public void showMenu() {
        setScreen(new MainMenuView(this));
    }


    /**
     * Show Room.
     */
    public void showRoom() {
        setScreen(new RoomView(this));
    }


    /**
     * Start GameModel.
     */
    public void startSingleplayerGame() {
        this.gameModel = new GameModel(GameType.SinglePlayer);
        this.gameController = new GameController(gameModel);
        setScreen(new PlacingView(this, Turn.Blue));
    }

    /**
     * Start GameModel.
     */
    public void startMultiplayerLocal() {
        this.gameModel = new GameModel(GameType.Multiplayer_local);
        this.gameController = new GameController(gameModel);
        setScreen(new PlacingView(this, Turn.Blue));
    }

    /**
     * Start GameModel.
     */
    public void startMultiplayer() {
        this.gameModel = new GameModel(GameType.Multiplayer);
        this.gameController = new GameController(gameModel);
        startGameServer();
        setScreen(new MultiplayerView(this));
    }

    public void startPlacingView(Turn turn){
        setScreen(new PlacingView(this, turn));
    }

    private void startGameServer(){
        this.gameServer = new Server(this);
        this.gameServerThread = new Thread(gameServer);
        this.gameServerThread.start();
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

    public void loadAssets(){
        this.carrier = new Texture("carrier.png");
        this.battleship = new Texture("battleship_ship.png");
        this.cruiser = new Texture("cruiser.png");
        this.submarine = new Texture("submarine.png");

        this.freeHit = new Texture("freeHit.png");
        this.shipHit = new Texture("shipHit.png");
    }

    public Texture getShipTexture(ShipType shipType){
        switch(shipType){
            case Carrier:
                return this.carrier;
            case Battleship:
                return this.battleship;
            case Cruiser:
                return this.cruiser;
            case Submarine:
                return this.submarine;
            default:
               return this.battleship;
        }
    }

    public Texture getCellTexture(CellType cellType){
        switch(cellType){
            case FreeHit:
                return this.freeHit;
            case ShipHit:
                return this.shipHit;
            default:
                return this.freeHit;
        }
    }

    public void startGameView(Turn playerTurn){
        this.gameModel.setPlayerTurn(playerTurn);
        setScreen(new GameView(this, this.gameController));
    }
}
