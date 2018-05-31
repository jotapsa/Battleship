package com.battleship.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private Ship ship;
    private int shipIndex;


    /**
     * The width of the viewport in cells.
     */
    private static final float VIEWPORT_WIDTH = 12;

    /**
     * The width of the viewport in cells.
     */
    private static final float VIEWPORT_HEIGHT = 16;

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

    private SpriteBatch batch;
    private BitmapFont font;

    /**
     * Creates this screen.
     */
    public PlacingView(Battleship game){
        this.game = game;
        this.gameModel = game.getGameModel();
        this.boardController = BoardController.getInstance();

        createCamera();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("placingBoard.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        init();


        //FONTS
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.font.setColor(Color.BLUE);
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

        this.batch.begin();
        // Print ship size ( ship.getShipType().getSize() ) ABOVE TILEMAP
        //        this.font.draw( this.batch, "Hello World!", 250, 1900);

        // Print PLAYER UNDER TILEMAP
        //        this.font.draw( this.batch, "Hello World!", 250, 1900);

        this.batch.end();
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
            int x = (int)mousePos.x/32 - 1;
            int y = (int)mousePos.y/32 - 2;
            Gdx.app.log("Battleship", "x - " + x + " y - " + y);

            if(boardController.placeShip(ship, new Coord(x, y ))){
                Gdx.app.log("Battleship", "Ship " + ship.getShipType() + "Placed!");
                nextShip();
            }

        }
    }

    @Override
    public void dispose(){
        super.dispose();
        map.dispose();
    }

    public void init(){
        this.player = this.gameModel.getPlayerBlue();
        boardController.setBoard(this.gameModel.getPlayerBlueBoard());
        shipIndex = 0;

        ship = this.player.getShips().get(shipIndex);
    }

    public void nextShip(){
        if(shipIndex < player.getShips().size()-1){
            shipIndex++;
            ship = this.player.getShips().get(shipIndex);
            return;
        }

        if(player == this.gameModel.getPlayerRed()
                || this.gameModel.getGameType() != GameType.Multiplayer_local){
            exit();
        }

        if(this.gameModel.getGameType() == GameType.Multiplayer_local){
          this.player = this.gameModel.getPlayerRed();
          boardController.setBoard(this.gameModel.getPlayerRedBoard());
        }
    }

    public void exit(){
        this.game.showMenu();
        dispose();
    }
}
