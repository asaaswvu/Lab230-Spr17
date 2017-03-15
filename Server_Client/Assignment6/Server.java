import java.net.*;
import java.util.*;
import java.util.Hashtable;
import java.io.IOException;

class Server extends Thread{

    ServerSocket ss;


    Server(){
    }

    public void run(){
        try{
        ss = new ServerSocket(50000);   //high port numbers aren't normally dedicated
        System.out.println("Server Started");
        while(true){
            Socket client = ss.accept();
            new ClientHandler(client,this).start();
            System.out.println("Started ClientHandler");
        }
        }catch(SocketException f){
            System.out.println("null");
        
        }catch(IOException e){
            System.out.print("Server IOException");
            e.printStackTrace();
        }
        
        
    }
    public void die(){
        try{
            ss.close();
        }catch(IOException e){
            //don't care, shutting down
        }
        System.exit(0);
    }
    
    public static void main(String args[]){
        new Server().start();
    }

}
