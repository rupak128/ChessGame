package chess;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Suvo on 03-Jun-16.
 */
public class SocWriter {
    Socket socket;
    DataOutputStream dout;

    public SocWriter(Socket socket) {
        this.socket = socket;
        try {
            dout=new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String str){
        try {
            dout.writeUTF(str);
            dout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
