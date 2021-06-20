import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class MultiplayerController {

    Socket socket = new Socket();
    Boolean isHost;
    Boolean canMove;
    public MultiplayerController(boolean host, Inet4Address inet4Address, Integer port) throws IOException {
        if (host){
            Socket matchingSocket = new Socket(inet4Address, port);
            NetworkHelper.write("HOST",matchingSocket.getOutputStream());
            String msg = NetworkHelper.msgRead(matchingSocket.getInputStream());
            String[] args = msg.split(" ");
            if (args[0].equals("ROOM")){
                Integer roomPort = Integer.valueOf(args[1]);
                this.socket = new Socket(inet4Address, roomPort);
                String replyMsg = NetworkHelper.msgRead(this.socket.getInputStream());
                if (replyMsg.equals("HOST")){
                    isHost = true;
                    canMove = true;
                }
                else {
                    isHost = false;
                    canMove = false;
                }
            }
        }
        else{
            Socket matchingSocket = new Socket(inet4Address, port);
            NetworkHelper.write("CLIENT", matchingSocket.getOutputStream());
            String msg = NetworkHelper.msgRead(matchingSocket.getInputStream());
            String[] args = msg.split(" ");
            System.out.println(args[0]);
            if (args[0].equals("ROOMS")){
                Integer roomPort = getPort(Arrays.copyOfRange(args, 1, args.length));
                this.socket = new Socket(inet4Address, roomPort);
                System.out.println(roomPort.intValue());
                String replyMsg = NetworkHelper.msgRead(socket.getInputStream());
                if (replyMsg.equals("HOST")){
                    isHost = true;
                    canMove = true;
                }
                else {
                    isHost = false;
                    canMove = false;
                }
            }
        }
    }
    public Integer getPort(String[] args){
        return Integer.valueOf(args[0]);
    }

}
