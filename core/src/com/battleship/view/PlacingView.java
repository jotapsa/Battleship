package com.battleship.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.battleship.Battleship;
import com.battleship.controller.BoardController;
import com.battleship.model.Board;
import com.battleship.model.Coord;
import com.battleship.model.GameModel;
import com.battleship.model.aux.GameType;
import com.battleship.model.aux.Orientation;
import com.battleship.model.Player;
import com.battleship.model.Ship;
import com.battleship.model.aux.ShipType;
import com.battleship.model.aux.Turn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlacingView extends ScreenAdapter{


    /**
     * The game this screen belongs to.
     */
    private Battleship game;

    private BoardController boardController;
    private GameModel gameModel;
    private Player player;
    private Ship selectedShip;
    private HashMap<Ship, Coord> shipsPlaced;
    private ArrayList<Ship> ships;
    private Turn playerTurn;

    private boolean reverse;
    private boolean confirm;

    private int DISPLAY_HEIGHT = Gdx.graphics.getHeight();
    private int DISPLAY_WIDTH = Gdx.graphics.getWidth();


    /**
     * The width of the viewport in cells.
     */
    private static final float VIEWPORT_WIDTH = 12;

    /**
     * The width of the viewport in cells.
     */
    private static final float VIEWPORT_HEIGHT = 18;

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

    private BitmapFont font;

    private Texture arrow;
    private Texture selected;
    private Texture rotate;

    public PlacingView(Battleship game, Turn playerTurn){
        this.game = game;
        this.gameModel = game.getGameModel();
        this.boardController = BoardController.getInstance();
        this.playerTurn = playerTurn;

        createCamera();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("placingBoard.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        if(playerTurn == Turn.Blue){
            init(this.gameModel.getPlayerBlue(), this.gameModel.getPlayerBlueBoard());
        }
        else{
            init(this.gameModel.getPlayerRed(), this.gameModel.getPlayerRedBoard());
        }



        //FONTS
        this.font = new BitmapFont();
        this.font.setColor(Color.BLUE);
        this.arrow = new Texture("arrow.png");
        this.selected = new Texture("selected.png");
        this.rotate = new Texture("rotate.png");
    }

    /**
     * Creates the camera used to show the viewport.
     *
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

        game.getBatch().begin();

        printShips();
        printOrientation();
        printPlacedShips();

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
            int x = (int)mousePos.x/32 - 1;
            int y = (int)mousePos.y/32 - 1;
            Gdx.app.log("Battleship", "x - " + x + " y - " + y);

            if(boardController.placeShip(selectedShip, new Coord(x, y))){
                Gdx.app.log("Battleship", "Ship " + selectedShip.getShipType() + "Placed!");
                nextShip();
            }
            else if(removePlacedShip(new Coord(x, y)) || selectShip(new Coord(x+1 , y+1))){
//
            }
            else if(x == 10 && y == -1 && selectedShip != null){
                selectedShip.flipOrientation();
            }
            else if(!confirm && shipsPlaced.size() == ships.size() && selectedShip == null){
                confirm = true;
                nextShip();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.showMenu();
            dispose();
        }
    }

    @Override
    public void dispose(){
        super.dispose();
        map.dispose();
    }

    public void printOrientation(){
        if(selectedShip == null){
            return;
        }

        //arrow of selectedShip orientation to place
        Sprite arrowSprite = new Sprite(this.arrow);

        if(selectedShip.getOrientation() == Orientation.Horizontal){
            if(reverse){
                arrowSprite.setOrigin(0,0);
                arrowSprite.setRotation(180);
                arrowSprite.setPosition((9)*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (18)*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
            }
            else{
                arrowSprite.setPosition((3)*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (0)*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
            }
            arrowSprite.setSize(6*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
            arrowSprite.draw(game.getBatch());
        }
        else{
            arrowSprite.setOrigin(0,0);
            if(reverse){
                arrowSprite.setRotation(-270);
                arrowSprite.setPosition((1)*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (9)*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
            }
            else{
                arrowSprite.setRotation(-90);
                arrowSprite.setPosition((11)*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (9)*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
            }
            arrowSprite.setSize((6)*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT), (DISPLAY_WIDTH/VIEWPORT_WIDTH));
            arrowSprite.draw(game.getBatch());
        }

        //print Rotate Button
        Sprite rotateSprite = new Sprite(this.rotate);
        if(reverse){
            rotateSprite.setOrigin(0,0);
            rotateSprite.setRotation(180);
            rotateSprite.setPosition((1)*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (18)*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
        }
        else{
            rotateSprite.setPosition((11)*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (0)*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
        }
        rotateSprite.setSize((DISPLAY_WIDTH/VIEWPORT_WIDTH), (DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
        rotateSprite.draw(game.getBatch());
    }

    public void printPlacedShips(){
        //shipsPlaced placed
        for(Map.Entry<Ship, Coord> shipBoard : this.shipsPlaced.entrySet()){
            printShipBoard(shipBoard.getKey(), shipBoard.getValue(), reverse);
        }
    }


    public void printShips(){
        int i=0;
        int j = reverse ? 6 : 1;
        float rotation = reverse ? 180 : 0;

        Sprite selected = new Sprite(this.selected);
        for(Ship ship : this.ships){
            if(!shipsPlaced.containsKey(ship)){
                Sprite shipSprite = new Sprite(game.getShipTexture(ship.getShipType()));

                if(reverse){
                    shipSprite.setOrigin(0,0);
                    shipSprite.setRotation(rotation);
                    shipSprite.setPosition((j+5)*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (2+i)*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
                    shipSprite.setSize(ship.getShipType().getSize()*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
                }
                else{
                    shipSprite.setPosition((j)*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (16-i)*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
                    shipSprite.setSize(ship.getShipType().getSize()*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
                }
                shipSprite.draw(game.getBatch());

                if(ship == selectedShip){
                    if(reverse){
                        selected.setOrigin(0,0);
                        selected.setRotation(rotation);
                        selected.setPosition((j+5)*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (2+i)*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
                        selected.setSize(ship.getShipType().getSize()*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
                    }
                    else{
                        selected.setPosition((j)*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (16-i)*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
                        selected.setSize(ship.getShipType().getSize()*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
                    }
                    selected.draw(game.getBatch());
                }
            }

            if(reverse){
                if(j==6){
                    j=0;
                }
                else{
                    i+=2;
                    j=6;
                }
            }
            else{
                if(j==1){
                    j+=6;
                }
                else{
                    i+=2;
                    j=1;
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
                sprite.setPosition(( 11 - pos.getX())*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (17 - pos.getY()-1)*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
                sprite.setSize((shipType.getSize())*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT), (DISPLAY_WIDTH/VIEWPORT_WIDTH));
            }
            else{
                sprite.setPosition(( 11 - pos.getX())*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (17 - pos.getY())*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
                sprite.setSize(shipType.getSize()*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
            }
        }
        else{
            if(ship.getOrientation() == Orientation.Vertical){
                sprite.setOrigin(0 ,0);
                sprite.setRotation(rotation);
                sprite.setPosition((pos.getX()+1)*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (pos.getY()+2)*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
                sprite.setSize((shipType.getSize())*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT), (DISPLAY_WIDTH/VIEWPORT_WIDTH));
            }
            else{
                sprite.setPosition((pos.getX()+1)*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (pos.getY()+1)*(DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
                sprite.setSize(shipType.getSize()*(DISPLAY_WIDTH/VIEWPORT_WIDTH), (DISPLAY_HEIGHT/VIEWPORT_HEIGHT));
            }
        }
        sprite.draw(game.getBatch());
    }

    public boolean removePlacedShip(Coord pos){
        for(Map.Entry<Ship, Coord> shipBoard : this.shipsPlaced.entrySet()){
            Ship ship = shipBoard.getKey();
            Coord shipPos = shipBoard.getValue();

            for(int i=0; i<ship.getShipType().getSize(); i++){
                if( (ship.getOrientation() == Orientation.Vertical && pos.equals(new Coord( shipPos.getX(), shipPos.getY()-i)) )
                        || pos.equals(new Coord( shipPos.getX()+i, shipPos.getY()))){
                    boardController.removeShip(ship);
                    selectedShip = ship;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean selectShip(Coord pos){
        Ship ship;
        for(int i=0; i < ships.size(); i+=2){
            ship = ships.get(i);
            if( pos.getX() >= 1 && pos.getX() < 1 + ship.getShipType().getSize() && pos.getY() == 16-i && !shipsPlaced.containsKey(ship)){
                selectedShip = ship;
                return true;
            }

            ship = i+1 < ships.size() ? ships.get(i+1) : null;
            if( ship != null && pos.getX() >= 7 && pos.getX() < 7 + ship.getShipType().getSize() && pos.getY() == 16-i && !shipsPlaced.containsKey(ship)){
                selectedShip = ship;
                return true;
            }
        }
        return false;
    }

    public void init(Player player, Board board){
        this.confirm = false;
        this.reverse = false;

        this.player = player;
        boardController.setBoard(board);

        shipsPlaced = board.getPlacedShips();
        ships = board.getShips();

        selectedShip = ships.get(0);
    }

    public void nextShip(){
        for(Ship ship : this.ships){
            if(!shipsPlaced.containsKey(ship)){
                selectedShip = ship;
                return;
            }
        }

        selectedShip = null;

        if((player == this.gameModel.getPlayerRed()
                || this.gameModel.getGameType() != GameType.Multiplayer_local) && confirm){
            exit();
        }

        if(this.gameModel.getGameType() == GameType.Multiplayer_local && confirm){
            init(this.gameModel.getPlayerRed(), this.gameModel.getPlayerRedBoard());
            camera.rotate(180);
            reverse=true;
        }
    }

    public void exit(){
        this.game.startGameView(playerTurn);
        dispose();
    }
}
