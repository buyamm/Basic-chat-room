/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package chatMultipleClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Buyam
 * @file: Chatter.java
 * @date May 28, 2023
 * @version 1.0
 */
public class Chatter {
    Socket socket;
    String name;
//    Mỗi thằng sẽ có 2 luồng nhận và gửi để giao tiếp với thằng request của những thằng client khác
    DataInputStream in;
    DataOutputStream out;
    
//    Hàm dựng
    public Chatter(Socket socket, String name) throws IOException{
        this.socket = socket;
        this.name = name;
        in = new DataInputStream(socket.getInputStream()); 
        out = new DataOutputStream(socket.getOutputStream()); 
    }
    public Socket getSocket() {
        return socket;
    }

    public String getName() {
        return name;
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIn(DataInputStream in) {
        this.in = in;
    }

    public void setOut(DataOutputStream out) {
        this.out = out;
    }
    
    
}
