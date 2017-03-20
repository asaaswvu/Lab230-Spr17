import java.net.*;
import java.util.Hashtable;
import java.io.IOException;

class Server extends Thread{

    Hashtable<String, String[]> users;
    ServerSocket ss;


    Server(){
        users = new Hashtable<String,String[]>();
        String[] user1 = {"password","Student"};
        String[] user2 = {"password","Admin"};
        users.put("student", user1);
        users.put("admin", user2);
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

    public boolean loginUser(String strUser, String strPass){
        String tempPass = users.get(strUser)[0];
        if (tempPass != null && tempPass.equals(strPass)){
            return true;
        }
        return false;
    }

    public String getUserType(String strUser){
      log(strUser);
      return users.get(strUser)[1];
    }

    public void log(String strUser){
      System.out.println(strUser +", "+users.get(strUser)[1]+" has logged in.");
    }

    public static void main(String args[]){
        new Server().start();
    }
}
