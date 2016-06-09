package chess;

import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Rupak on 03-Jun-16.
 */
public class SocReader implements Runnable{
    Socket socket;
    DataInputStream din;
    BoardNController controller;
    Thread t;

    public SocReader(Socket socket, BoardNController controller) throws IOException {
        this.socket = socket;
        this.controller = controller;
        din=new DataInputStream(socket.getInputStream());
        t=new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while (true){
            try {
                String str=din.readUTF();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        if(str.startsWith("$"))controller.check();
                        else if(str.equals("#check_mate")) controller.opCheckMate();
                        else if(str.startsWith("*")) controller.addCapure(str);
                        else controller.updateOtherPlayer(str);
                    }
                });
            } catch (IOException e) {
                System.out.println("Opponent connection disrupted");
                break;
            }

        }

    }
}
