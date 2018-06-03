package com.battleship.networking;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.battleship.Battleship;
import com.battleship.controller.BoardController;
import com.battleship.controller.GameController;
import com.battleship.model.Coord;
import com.battleship.model.Move;
import com.battleship.model.Turn;
import com.battleship.model.aux.CellType;
import com.battleship.networking.msg.AcceptMessage;
import com.battleship.networking.msg.HitMessage;
import com.battleship.networking.msg.MsgType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Server implements Runnable{
    private Battleship game;

    public Server(Battleship game){
        this.game = game;
    }

    @Override
    public void run() {
        boolean listen = true;

        ServerSocketHints serverSocketHint = new ServerSocketHints();
        // 0 means no timeout.  Probably not the greatest idea in production!
        serverSocketHint.acceptTimeout = 0;

        // Create the socket server using TCP protocol and listening on game.defaultPort
        ServerSocket serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, game.defaultPort, serverSocketHint);

        while(listen){
            // Create a socket
            Socket socket = serverSocket.accept(null);

            // Read data from the socket into a BufferedReader
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send data
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            try {
                String response = inFromClient.readLine();
                System.out.println(response);

                String[] msgArgs = response.split(" ");

                if(msgArgs[0].equals("JOIN")){
                    //accept
                    out.write(new AcceptMessage().toString());
                    //dispose room view
                    game.startPlacingView(Turn.Blue);
                }else if(msgArgs[0].equals("MOVE")){
                    Coord target = new Coord(Integer.parseInt(msgArgs[1]), Integer.parseInt(msgArgs[2]));
                    Move move = new Move(target, Turn.Red);
//                      process move
                    BoardController.getInstance().setBoard(game.getGameModel().getPlayerBlueBoard());
                    CellType hit = BoardController.getInstance().doMove(move);
//
                    out.write(new HitMessage(hit).toString());
                }
                out.flush();

//                out.close();
//                inFromClient.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeServer(){

    }
}
