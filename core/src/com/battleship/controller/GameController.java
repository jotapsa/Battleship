package com.battleship.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.battleship.model.Computer;
import com.battleship.model.Coord;
import com.battleship.model.GameModel;
import com.battleship.model.Human;
import com.battleship.model.Move;
import com.battleship.model.Turn;
import com.battleship.model.aux.GameType;
import com.battleship.networking.msg.JoinMessage;
import com.battleship.view.GameView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;

public class GameController {
    private static GameController instance;

    private GameModel gameModel;
    private BoardController boardController;
    private boolean isGameOver;

    /**
     * Creates a new GameController of a certain GameModel.
     *
     */
    public GameController(GameModel gameModel) {

        this.gameModel = gameModel;
        this.boardController = BoardController.getInstance();
        isGameOver = false;

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

    public BoardController getBoardController() {
        return boardController;
    }

    public void update(float delta){
        if(gameModel.getPlayerBlue() instanceof Computer || gameModel.getPlayerRed() instanceof Computer){
            computerPlay();
        }

        isGameOver = boardController.allSank();
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

        if(gameModel.getTurn() == Turn.Blue && gameModel.getPlayerBlue() instanceof Human && !((Human) gameModel.getPlayerBlue()).isOnlinePlayer()){
            move = new Move(target, gameModel.getTurn());
//            if(gameModel.getGameType() == GameType.Multiplayer){
//                game.ipEnemy;
//                Socket socket;
//
//                SocketHints socketHints = new SocketHints();
//                socketHints.connectTimeout = 0;
//
//                try {
//                    socket = Gdx.net.newClientSocket(Net.Protocol.TCP, ip, game.defaultPort, socketHints);
//                    PrintWriter out = new PrintWriter(socket.getOutputStream());
//                    out.write(msg.toString());
//                    out.flush();
//
//                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//                    String response = inFromServer.readLine();
//                    System.out.println(response);
//
//                    String[] msgArgs = response.split(" ");
//
//                    if(msgArgs[0].equals("ACCEPT")){
//                        dispose();
//                        game.startPlacingView(Turn.Red);
//                    }
//
////                    inFromServer.close();
////                    out.close();
//
//                }
//                catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//                catch(GdxRuntimeException ex){
//                    System.out.println("Fail to open " + ip);
//                    stage.setKeyboardFocus(ipInput);
//                }
//
//                //send move.
//            }
        }
        else if (gameModel.getTurn() == Turn.Red && gameModel.getPlayerRed() instanceof Human && !((Human) gameModel.getPlayerBlue()).isOnlinePlayer()){
            move = new Move(target, gameModel.getTurn());
            if(gameModel.getGameType() == GameType.Multiplayer){
                //send move.
                //store received hit in board.
            }
        }

        if(move != null){
            boardController.doMove(move);
            if(!move.getHitShip()){
                gameModel.nextTurn();
            }
        }

        isGameOver = boardController.allSank();
    }

    public void setPlayerOnline(Turn turn){
        switch (turn){
            case Blue:
                if(gameModel.getPlayerBlue() instanceof Human){
                    ((Human) gameModel.getPlayerBlue()).setOnlinePlayer(true);
                }
                break;
            case Red:
                if(gameModel.getPlayerBlue() instanceof Human){
                    ((Human) gameModel.getPlayerRed()).setOnlinePlayer(true);
                }
                break;
            default:
                break;
        }
    }

    public boolean isGameOver(){
        return isGameOver;
    }
}
