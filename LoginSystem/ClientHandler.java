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
				case "<login>":
					loginUser(data);
					break;
				case "<addElection>":
					server.addElection(data[1], data[2]);
					pwOut.println("<AddedElection>,"+data[1]+","+data[2]);
					break;
        case "<removeElection>":
  				if(server.removeElection(data[1])){
  				      pwOut.println("<removedElection>,"+data[1]);
          }
          else{
              pwOut.println("<removedElection>,"+data[1]+",fail");
          }
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
			server.die();
			die();
		}
	}

	private void loginUser(String [] data){
		if (data.length == 3){
			if(server.loginUser(data[1],data[2])){
				String userType = server.getUserType(data[1]);
				if(userType.equals("Student")){
					pwOut.println("<loggedS>");
				}
				else if(userType.equals("Admin")){
					pwOut.println("<loggedA>");
				}
				else{
					pwOut.println("<error>");
				}
			}else{
				pwOut.println("Invalid Credentials");
			}
		}else{
			pwOut.println("<error>");
		}
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
