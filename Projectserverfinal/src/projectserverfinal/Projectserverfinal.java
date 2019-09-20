/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectserverfinal;

/**
 *
 * @author Hridoy Saha
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
 
public class Projectserverfinal
{
 
    private static Socket socket;
 
    public static void main(String[] args)
    {
        try
        {
 
            int port = 25000;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server Started and listening to the port 25000");
 
            //Server is running always as while loop is always true and never false it is essential for multipletimes connectino... :) 
            while(true)
            {
                
                socket = serverSocket.accept();//accepting clients connection
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String inputmessage = br.readLine(); //Reading the message from the client

                GoriberELMS elms = new GoriberELMS(inputmessage); // the obj of Goriber elms is created here.. khella hobe :3
                
                String returnMessage;
                try
                {
                    returnMessage = elms.start(); // message is sent to the goribs for process ... khella hocche 

                }
                catch(NumberFormatException e)
                {
                    
                    returnMessage = "error in return message\n";
                }
 
                //Sending the response back to the client.
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(returnMessage);
                System.out.println("Message sent to the client is "+returnMessage);// checking what is sent to client
                bw.flush();// theflush >.<
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e){}
        }
    }
}
