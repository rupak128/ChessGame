package chess;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Rupak on 03-Jun-16.
 */
public class Client {
    BoardNController boardNController;
    Socket socket;
    SocWriter socWriter;
    SocReader socReader;
    public Client(BoardNController boardNController) throws IOException {
        this.boardNController = boardNController;
        socket=new Socket("localhost",44444);
        socWriter=new SocWriter(socket);
        socReader=new SocReader(socket,boardNController);
    }
}
