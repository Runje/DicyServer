package com.runje;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Thomas on 12.02.2015.
 */
public class Server
{
    private final int portNumber;

    public Server(int port)
    {
        portNumber = port;
    }
    public void start()
    {

        System.out.println("Server will start now");
        ServerSocket serverSocket = null;
        try
        {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        while (true)
        {
            System.out.println("Waiting for Client...");
            Socket clientSocket = null;
            try
            {
                clientSocket = serverSocket.accept();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            new ClientThread(clientSocket).start();
        }
    }
}