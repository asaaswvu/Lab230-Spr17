import java.io.*;
import java.net.*;


class ClientHandler extends Thread{

    BufferedReader brIn;
    PrintWriter pwOut;
    Server server;
    Socket socket;
    
    ClientHandler(Socket sock, Server serv){
        try{
            brIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            pwOut = new PrintWriter(sock.getOutputStream(),true);
            server = serv;
            socket = sock;
        }catch(Exception e){
            System.out.println("Error initiating ClientHandler");
        }
    }
    
    public void run(){
        String line = new String();
        try{
            while (true){
                line = brIn.readLine();
                System.out.println(line);
                String [] data = line.split(",");
                switch (data[0]){
                    case "<add>":
                        addUser(data);
						displayUsers();
                    break;
                    case "<die>" :
                        die();
                    default:
                        pwOut.println("<error>");
                }         
                
            }
        }catch(SocketException x){
            System.out.println("socket disconnected");
        }catch(Exception e){
            System.out.print("Error: " + line);
        } 
    }
    private void addUser(String [] data){
            if(server.addUser(data)){
                pwOut.println("<added>" + server.users.toString());
            }else{
                pwOut.println("<error>");
            }
        
    }

    private void displayUsers(){
	server.displayUsers();

    }


    private void die(){
        try{
            socket.close();
            server.die();
        }catch(Exception e){
            //shutting down
        }
        System.exit(0);
    }
}