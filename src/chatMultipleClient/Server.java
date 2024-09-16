/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatMultipleClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Buyam
 * @file: Server.java
 * @date May 28, 2023
 * @version 1.0
 */
public class Server {

    static ArrayList<Chatter> list;

    public static void main(String[] args) {
        try {
            list = new ArrayList<>();
            ServerSocket ss = new ServerSocket(1309);
            System.out.println("Server is ready....");

            while (true) {
                Socket socket = ss.accept();
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                
                

// Có nghĩa là chèn lại những thằng client đã tham gia trước đó rồi cho thằng cuối cùng đc thấy tất cả những th client trước đó
                out.writeUTF("insert");
                int n = list.size();
                out.writeInt(n);
                for (int i = 0; i < n; i++) {
                    out.writeUTF(list.get(i).getName());
                }
                System.err.println("OKKKKKKKKK");
                
                
                String name = in.readUTF();
                if (!name.equals("Cancel")) {
                    Chatter chatter = new Chatter(socket, name);
                    list.add(chatter);
                    for (Chatter c : list) {
                        DataOutputStream out1 = c.getOut();
                        out1.writeUTF("add");
                        out1.writeUTF(name);
                    }

                    Processing pr = new Processing(chatter);
                    pr.start();
                }
            }
        } catch (IOException ex) {
            System.out.println("Loi");
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
