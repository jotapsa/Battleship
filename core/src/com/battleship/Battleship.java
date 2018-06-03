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
import com.battleship.model.aux.Turn;
import com.battleship.networking.Server;
import com.battleship.view.GameView;
import com.battleship.view.MainMenuView;
import com.battleship.view.PlacingView;
import com.battleship.view.MultiplayerView;
import com.battleship.view.RoomView;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Class representing Battleship Game.
 */
public class Battleship extends Game {
    private String ipEnemy;
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

    private RoomView roomView;

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

	/**
	 * Gets Battleship game model.
	 *
	 * @return the game model
	 */
	public GameModel getGameModel(){
	    return this.gameModel;
    }

    /**
     * Show Battleship menu.
     */
    public void showMenu() {
        setScreen(new MainMenuView(this));
    }


    /**
     * Show Create Room for Multiplayer Game.
     */
    public void showRoom() {
        this.roomView = new RoomView(this);
        setScreen(roomView);
    }


    /**
     * Start Singleplayer Game.
     */
    public void startSingleplayerGame() {
        this.gameModel = new GameModel(GameType.SinglePlayer);
        this.gameController = new GameController(gameModel, this);
        setScreen(new PlacingView(this, Turn.Blue));
    }

    /**
     * Start Local Multiplayer Game.
     */
    public void startMultiplayerLocal() {
        this.gameModel = new GameModel(GameType.Multiplayer_local);
        this.gameController = new GameController(gameModel, this);
        setScreen(new PlacingView(this, Turn.Blue));
    }

    /**
     * Start Multiplayer Game.
     */
    public void startMultiplayer() {
        this.gameModel = new GameModel(GameType.Multiplayer);
        this.gameController = new GameController(gameModel, this);
        setScreen(new MultiplayerView(this));
    }

    /**
     * Start Placing View.
     *
     * @param turn Turn
     */
    public void startPlacingView(Turn turn){
        setScreen(new PlacingView(this, turn));
    }

    /**
     * Start Battleship game server.
     */
    public void startGameServer(){
        this.gameServer = new Server(this);
        this.gameServerThread = new Thread(gameServer);
        this.gameServerThread.start();
    }

    /**
     * Stop Battleship game server.
     */
    public void stopGameServer(){
        if(this.gameServer != null){
            this.gameServer.closeServer();
            this.gameServer = null;
        }
    }


    /**
     * Render.
     */
    @Override
	public void render () {
		super.render();
	}
	
	/**
	 * Dispose.
	 */
	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}

    /**
     * Returns the asset manager used to load all textures and sounds.
     *
     * @return AssetManager asset manager
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * Returns the sprite batch used to improve drawing performance.
     *
     * @return SpriteBatch sprite batch
     */
    public SpriteBatch getBatch() {
        return batch;
    }

    /**
     * Load Ship Textures.
     */
    public void loadAssets(){
        this.carrier = new Texture("carrier.png");
        this.battleship = new Texture("battleship_ship.png");
        this.cruiser = new Texture("cruiser.png");
        this.submarine = new Texture("submarine.png");

        this.freeHit = new Texture("freeHit.png");
        this.shipHit = new Texture("shipHit.png");
    }

    /**
     * Gets Texture of ShipType.
     *
     * @param shipType ShipType
     * @return Texture ship texture
     */
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

    /**
     * Gets Texture of CellType.
     *
     * @param cellType CellType
     * @return Texture cell texture
     */
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

    /**
     * Start Game View.
     *
     * @param playerTurn Turn
     */
    public void startGameView(Turn playerTurn){
        this.gameModel.setPlayerTurn(playerTurn);
        setScreen(new GameView(this, this.gameController));
    }

    /**
     * Gets Room view.
     *
     * @return room view
     */
    public RoomView getRoomView() {
        return roomView;
    }

    /**
     * Gets IP enemy.
     *
     * @return String IP enemy
     */
    public String getIpEnemy() {
        return ipEnemy;
    }

    /**
     * Sets IP enemy.
     *
     * @param ipEnemy String
     */
    public void setIpEnemy(String ipEnemy) {
        this.ipEnemy = ipEnemy;
    }

    /**
     * Gets User IP.
     *
     * @return List IP
     */
    public List<String> getIP(){
        // The following code loops through the available network interfaces
        // Keep in mind, there can be multiple interfaces per device, for example
        // one per NIC, one per active wireless and the loopback
        // In this case we only care about IPv4 address ( x.x.x.x format )
        List<String> addresses = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for(NetworkInterface ni : Collections.list(interfaces)){
                for(InetAddress address : Collections.list(ni.getInetAddresses()))
                {
                    if(address instanceof Inet4Address){
                        addresses.add(address.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return addresses;
    }
}
