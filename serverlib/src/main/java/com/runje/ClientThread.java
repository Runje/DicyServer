package com.runje;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;

import games.runje.dicymodel.Gamemaster;
import games.runje.dicymodel.Rules;
import games.runje.dicymodel.communication.Message;
import games.runje.dicymodel.communication.MessageParser;
import games.runje.dicymodel.communication.MessageReader;
import games.runje.dicymodel.communication.StartGameMessage;
import games.runje.dicymodel.data.Board;

/**
 * Created by Thomas on 12.02.2015.
 */
public class ClientThread extends Thread
{

    private Socket clientSocket;

    public ClientThread(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

     @Override
    public void run()
     {
         try
         {
             String inputLine, outputLine;
             OutputStream outStream = clientSocket.getOutputStream();
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(clientSocket.getInputStream()));
             // Start Game
             Rules r = new Rules();
             //Board b = Board.createBoardNoPoints(5,5, r);
             Board b = Board.createElementsBoard(new int[]{1, 2, 3, 4, 5,
                     2, 2, 4, 3, 3,
                     1, 2, 2, 5, 3,
                     6, 5, 4, 4, 5,
                     5, 4, 5, 6, 5});
             String[] player = new String[] { "Thomas", "Milena"};
             StartGameMessage msg = new StartGameMessage(b, r, player);
             Gamemaster gamemaster = new Gamemaster(b, r, player, clientSocket);
             byte[] bytes = msg.toByte();
             outStream.write(bytes);
             System.out.println(msg.getBoard().toString());
             System.out.println("sent bytes:" + bytes.length);
             BufferedReader stdIn =
                     new BufferedReader(new InputStreamReader(System.in));

             while (true)
             {
                 ByteBuffer buffer = ByteBuffer.allocate(2000);
                 int length = MessageReader.readMessage(clientSocket.getInputStream(), buffer);
                 System.out.println("Message read from client, length: " + length);
                 Message message = MessageParser.parse(buffer, length);
                 System.out.println("Messagename: " + message.getName());
                 message.execute(gamemaster);
                 System.out.println("Board after switch: " + gamemaster.getBoard().toString());
             }
         }
         catch (IOException e)
         {
             e.printStackTrace();
         }

         System.out.println("Client Thread ends");
     }
}
