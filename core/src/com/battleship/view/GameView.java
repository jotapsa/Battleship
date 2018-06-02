package com.battleship.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.battleship.model.Orientation;
import com.battleship.model.Ship;
import com.battleship.model.ShipType;
import com.battleship.model.Turn;

import java.util.Map;

public class GameView extends ScreenAdapter {

    private Battleship game;
    private GameModel gameModel;
    private GameController gameController;

    private int DISPLAY_HEIGHT = Gdx.graphics.getHeight();
    private int DISPLAY_WIDTH = Gdx.graphics.getWidth();


    /**
     * The width of the viewport in cells.
     */
    private static final float VIEWPORT_WIDTH = 10;

    /**
     * The width of the viewport in cells.
     */
    private static final float VIEWPORT_HEIGHT = 21;

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
    public GameView(Battleship game, GameController gameController){
        this.game = game;
        this.gameModel = game.getGameModel();
        this.gameController = gameController;

        loadAssets();

        createCamera();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("boardGame.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
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

        if(this.gameModel.getGameType() != GameType.Multiplayer_local){
            camera.rotate(180);
        }
    }

    /**
     * Loads the assets needed by this screen.
     */
    private void loadAssets(){

        this.game.getAssetManager().finishLoading();
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

        game.getBatch().begin();

        printBoard();

        game.getBatch().end();
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
        if(Gdx.input.justTouched()){
            Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mousePos); // mousePos is now in world coordinates

            int x = (int)mousePos.x/32;
            int y = (int)mousePos.y/32;

            Gdx.app.log("Battleship", "x - " + x + " y - " + y);

            Coord coord = new Coord(x, y);

            if(BoardController.isValidCoord(this.gameModel.getPlayerBlueBoard(), coord)){
                //get reverse coordinates
                if(this.gameModel.getGameType() != GameType.Multiplayer_local){
                    coord.setX(Math.abs(x-9));
                    coord.setY(Math.abs(y-9));
                }
//                this.gameController.handleClick(coord);

                this.gameModel.nextTurn();

                if(this.gameModel.getGameType() == GameType.Multiplayer_local){
                    camera.rotate(180);
                }
            }
        }
    }

    @Override
    public void dispose(){
        super.dispose();
        map.dispose();
    }

    public void printBoard(){
        if(this.gameModel.getGameType() != GameType.Multiplayer_local){
            //blue board
            for(Map.Entry<Ship, Coord> shipBoard : this.gameModel.getPlayerBlueBoard().getPlacedShips().entrySet()){
                printShipBoard(shipBoard.getKey(), shipBoard.getValue(), false);
            }

            for(Map.Entry<Ship, Coord> shipBoard : this.gameModel.getPlayerRedBoard().getPlacedShips().entrySet()){
                printShipBoard(shipBoard.getKey(), shipBoard.getValue(), true);
            }


//            for(Coord cell : this.gameModel.getPlayerRed().getHitCells()){
//                printBoardCell
//            }

        }
        else{

            if(this.gameModel.getTurn() == Turn.Blue){
                //print reverse red board
                for(Map.Entry<Ship, Coord> shipBoard : this.gameModel.getPlayerRedBoard().getPlacedShips().entrySet()){
                    printShipBoard(shipBoard.getKey(), shipBoard.getValue(), true);
                }
            }
            else{
                // print blue board
                for(Map.Entry<Ship, Coord> shipBoard : this.gameModel.getPlayerBlueBoard().getPlacedShips().entrySet()){
                    printShipBoard(shipBoard.getKey(), shipBoard.getValue(), false);
                }
            }
        }
    }

    public void printShipBoard(Ship ship, Coord pos, boolean reverse){
        ShipType shipType = ship.getShipType();
        float rotation = (ship.getOrientation() == Orientation.Vertical) ? (reverse ? -270 : -90) : (reverse ? -180 : 0);

        Sprite sprite = new Sprite(game.getShipTexture(shipType));

        if(reverse){
             sprite.setOrigin(0 ,0);
             sprite.setRotation(rotation);
             if(ship.getOrientation() == Orientation.Vertical){
                sprite.setPosition((10 - pos.getX())*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (21 - pos.getY()-1 )*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
                 sprite.setSize((shipType.getSize())*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT), (DISPLAY_WIDTH/VIEWPORT_WIDTH));
             }
             else{
                sprite.setPosition((10 - pos.getX())*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (21 - pos.getY() )*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
                 sprite.setSize(shipType.getSize()*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
             }
        }
        else{
            if(ship.getOrientation() == Orientation.Vertical){
                sprite.setOrigin(0 ,0);
                sprite.setRotation(rotation);
                sprite.setPosition((pos.getX())*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (pos.getY()+1)*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
                sprite.setSize((shipType.getSize())*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT), (DISPLAY_WIDTH/VIEWPORT_WIDTH));
            }
            else{
                sprite.setPosition((pos.getX())*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (pos.getY())*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
                sprite.setSize(shipType.getSize()*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
            }
        }
        sprite.draw(game.getBatch());
    }
}