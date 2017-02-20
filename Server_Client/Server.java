import java.net.*;
import java.util.Hashtable;
import java.io.IOException;

class Server extends Thread{

    Hashtable<String,String> users;
    ServerSocket ss;


    Server(){
        users = new Hashtable<String,String>();   
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
    public boolean addUser(String strUser, String strPass){
        if(users.containsKey(strUser)){
            return false;
        }else{
            users.put(strUser,strPass);
            return true;
        }
    }
    public boolean loginUser(String strUser, String strPass){
        String tempPass = users.get(strUser);
        if (tempPass != null && tempPass.equals(strPass)){
            return true;
        }
        return false;
    }

    public static void main(String args[]){
        new Server().start();
    }

}