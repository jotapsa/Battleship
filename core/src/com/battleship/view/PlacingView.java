package com.battleship.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.battleship.Battleship;
import com.battleship.controller.BoardController;
import com.battleship.controller.GameController;
import com.battleship.model.Coord;
import com.battleship.model.GameModel;
import com.battleship.model.GameType;
import com.battleship.model.Player;
import com.battleship.model.Ship;

public class PlacingView extends ScreenAdapter{


    /**
     * The game this screen belongs to.
     */
    private Battleship game;

    private BoardController boardController;
    private GameModel gameModel;
    private Player player;


    /**
     * The width of the viewport in cells.
     */
    private static final float VIEWPORT_WIDTH = 14;

    /**
     * The width of the viewport in cells.
     */
    private static final float VIEWPORT_HEIGHT = 14;

    /**
     * Pixels per cell (square cell)
     */
    private static final float PPC = 32;


    /**
     * The camera used to show the viewport.
     */
    private OrthographicCamera camera;
    private ExtendViewport gamePort;
    //TODO: Hud

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    /**
     * Creates this screen.
     */
    public PlacingView(Battleship game){
        this.game = game;
        this.gameModel = game.getGameModel();

        createCamera();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("placing_board.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

//        this.placingShips();
    }

    /**
     * Creates the camera used to show the viewport.
     *
     * @return the camera
     */
    private void createCamera() {
        this.camera = new OrthographicCamera(VIEWPORT_WIDTH * PPC, VIEWPORT_HEIGHT*PPC);

        this.gamePort = new ExtendViewport(VIEWPORT_WIDTH * PPC, VIEWPORT_HEIGHT*PPC, camera);

//        camera.position.set(gamePort.getWorldWidth() / 2f, gamePort.getWorldHeight() / 2f, 0);
        camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);
    }



    /**
     * Renders this screen.
     *
     * @param delta time since last renders in seconds.
     */
    public void render(float delta){
        super.render(delta);
        update(delta);

        Gdx.gl.glClearColor( 1f, 1f, 1f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        renderer.render();
    }

    private void update(float delta) {
        handleInputs(delta);

        camera.update();
        renderer.setView(camera);
    }

    /**
     * Handles any inputs and passes them to the controller.
     *
     * @param delta time since last time inputs where handled in seconds
     */
    private void handleInputs(float delta) {
        if(Gdx.input.isTouched()){
            Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mousePos); // mousePos is now in world coordinates
            Gdx.app.log("Battleship", "x - " + (int)mousePos.x/32 + " y - " + (int)mousePos.y/32);

//            GameController.getInstance().handleClick((int)mousePos.x/32, (int)mousePos.y/32);
            if(boardController.canPlaceShip(null, new Coord((int)mousePos.x/32, (int)mousePos.y/32) )){

            }
        }
    }

    @Override
    public void dispose(){
        super.dispose();
        map.dispose();
    }

    public void placingShips(){
        this.player = this.gameModel.getPlayerBlue();
        boardController = new BoardController(this.gameModel.getPlayerBlueBoard());

        for(Ship ship : this.player.getShips()){
            // Print ship size ( ship.getShipType().getSize() ) ABOVE TILEMAP

            // Print PLAYER 1 UNDER TILEMAP

            //After User Input
//            while(!boardController.canPlaceShip(ship, new Coord(x, y))){
//                boardController.placeShip(ship, new Coord(x, y));
//            }
        }

        if(this.gameModel.getGameType() == GameType.SinglePlayer
                || this.gameModel.getGameType() == GameType.Multiplayer){
//            this.game.setGameView();
            return;
        }

        this.player = this.gameModel.getPlayerRed();
        boardController = new BoardController(this.gameModel.getPlayerRedBoard());

        for(Ship ship : this.player.getShips()){
            // Print ship size ( ship.getShipType().getSize() ) ABOVE TILEMAP

            // Print PLAYER 2 UNDER TILEMAP

            //After User Input
//            while(!boardController.canPlaceShip(ship, new Coord(x, y))){
//                boardController.placeShip(ship, new Coord(x, y));
//            }
        }

    }
}
