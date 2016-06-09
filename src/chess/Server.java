package chess;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Rupak on 03-Jun-16.
 */
public class Server implements Runnable {
    ServerSocket serverSocket;
    BoardNController boardNController;
    Socket client;
    Thread t;
    SocReader socReader;
    SocWriter socWriter;

    public Server(BoardNController boardNController) throws IOException {
        this.boardNController = boardNController;
        serverSocket=new ServerSocket(44444);
        t=new Thread(this);
        t.start();



    }

    @Override
    public void run() {
        try {
            client=serverSocket.accept();
            socReader=new SocReader(client,boardNController);
            socWriter=new SocWriter(client);
            boardNController.setSocWriter(socWriter);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
