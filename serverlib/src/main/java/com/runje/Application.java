package com.runje;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import games.runje.dicymodel.communication.ConnectionToServer;

public class Application
{
    public static void main(String[] args)
    {

       new Server(ConnectionToServer.port).start();
    }

}
