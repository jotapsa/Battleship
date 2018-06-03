package com.battleship.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.battleship.Battleship;
import com.battleship.model.Board;
import com.battleship.model.Computer;
import com.battleship.model.Coord;
import com.battleship.model.GameModel;
import com.battleship.model.Human;
import com.battleship.model.Move;
import com.battleship.model.aux.GameType;
import com.battleship.model.aux.Turn;
import com.battleship.model.aux.CellType;
import com.battleship.networking.msg.MoveMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class GameController {
    private static GameController instance;

    private Battleship game;

    private GameModel gameModel;
    private BoardController boardController;
    private boolean isGameOver;

    /**
     * Creates a new GameController of a certain GameModel.
     *
     */
    public GameController(GameModel gameModel, Battleship game) {

        this.gameModel = gameModel;
        this.boardController = BoardController.getInstance();
        isGameOver = false;

        this.game = game;

        instance = this;
    }

    /**
     * Returns a singleton instance of a board controller
     *
     * @return the singleton instance
     */
    public static GameController getInstance() {
        return instance;
    }

    public void update(float delta){
        if(gameModel.getPlayerBlue() instanceof Computer || gameModel.getPlayerRed() instanceof Computer){
            computerPlay();
        }

        if(gameModel.getGameType() != GameType.Multiplayer){
            isGameOver = boardController.allSank();
        }
    }

    private void computerPlay() {
        Move move = null;

        if(gameModel.getTurn() == Turn.Blue && gameModel.getPlayerBlue() instanceof Computer){
            move = ((Computer) gameModel.getPlayerBlue()).createMove(gameModel.getPlayerRedBoard());
            boardController.setBoard(gameModel.getPlayerRedBoard());
        }

        if(gameModel.getTurn() == Turn.Red && gameModel.getPlayerRed() instanceof Computer){
            move = ((Computer) gameModel.getPlayerRed()).createMove(gameModel.getPlayerBlueBoard());
            boardController.setBoard(gameModel.getPlayerBlueBoard());
        }

        if(move != null){
            boardController.doMove(move);
            if(!move.getHitShip()){
                gameModel.nextTurn();
            }
        }
    }

    public boolean isValidTarget(Coord target){
        if(gameModel.getTurn() == Turn.Blue && gameModel.getPlayerBlue() instanceof Human){
            boardController.setBoard(gameModel.getPlayerRedBoard());
        }
        else if (gameModel.getTurn() == Turn.Red && gameModel.getPlayerRed() instanceof Human){
            boardController.setBoard(gameModel.getPlayerBlueBoard());
        }

        return boardController.isValidTarget(target);
    }


    public void handleClick(Coord target){
        Move move = null;

        if(!isValidTarget(target)){
            return;
        }

        if(gameModel.getTurn() == Turn.Blue && gameModel.getPlayerBlue() instanceof Human && gameModel.getPlayerTurn() == Turn.Blue){
            move = new Move(target, gameModel.getTurn());
        }
        else if (gameModel.getTurn() == Turn.Red && gameModel.getPlayerRed() instanceof Human && gameModel.getPlayerTurn() == Turn.Red){
            move = new Move(target, gameModel.getTurn());
        }

        if(move != null){
            boardController.doMove(move);
            if(!move.getHitShip()){
                gameModel.nextTurn();
            }
        }

        isGameOver = boardController.allSank();
    }

    public void handleMultiplayerClick(Coord target){
        if(!isValidTarget(target)){
            return;
        }

        Move move = null;
        CellType cellType = null;

        if(gameModel.getTurn() == Turn.Blue && gameModel.getPlayerBlue() instanceof Human && gameModel.getPlayerTurn() == Turn.Blue){
            move = new Move(target, gameModel.getTurn());
            cellType = sendMove(gameModel.getPlayerRedBoard(), target);
        }
        else if (gameModel.getTurn() == Turn.Red && gameModel.getPlayerRed() instanceof Human && gameModel.getPlayerTurn() == Turn.Red){
            move = new Move(target, gameModel.getTurn());
            cellType = sendMove(gameModel.getPlayerBlueBoard(), target);
        }

        if(move != null){
            move.setHitShip(cellType == CellType.ShipHit);
            if(!move.getHitShip()){
                gameModel.nextTurn();
            }
        }
    }

    public CellType sendMove(Board board, Coord target){
        Socket socket;

        SocketHints socketHints = new SocketHints();
        socketHints.connectTimeout = 0;

        try {
            socket = Gdx.net.newClientSocket(Net.Protocol.TCP, game.getIpEnemy(), game.defaultPort, socketHints);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.write(new MoveMessage(target).toString());
            out.flush();

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String response = inFromServer.readLine();
            if(response == null){
                //CONNECTION FAILED
                game.showMenu();
            }
            System.out.println(response);

            String[] msgArgs = response.split(" ");

            if(msgArgs[0].equals("HIT")){
                if(msgArgs[1].equals("FREEHIT")){
                    board.setCell(target, CellType.FreeHit);
                    return CellType.FreeHit;
                }else if(msgArgs[1].equals("SHIPHIT")){
                    board.setCell(target, CellType.ShipHit);
                    return CellType.ShipHit;
                }
            }
            else if(msgArgs[0].equals("GAMEOVER")){
                //GAMEOVER
                board.setCell(target, CellType.ShipHit);
                setGameOver(true);
                return CellType.ShipHit;
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        catch(GdxRuntimeException ex){
            game.showMenu();
        }

        return CellType.Free;
    }

    public void setGameOver(boolean gameOver){
        isGameOver = gameOver;
    }

    public boolean isGameOver(){
        return isGameOver;
    }
}
