/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatMultipleClient;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Buyam
 * @file: Processing.java
 * @date May 28, 2023
 * @version 1.0
 */
public class Processing extends Thread {

    Chatter chatter;

    public Processing(Chatter chatter) {
        this.chatter = chatter;
    }

    @Override
    public void run() {
        String action;
        while (true) {
            try {
                action = chatter.getIn().readUTF();
                if (action.equals("Cancel")) {
                    chatter.getSocket().close();
                    Server.list.remove(chatter);
                    for (Chatter c : Server.list) {
                        c.getOut().writeUTF("remove");
                        c.getOut().writeUTF(chatter.getName());
                    }
                    break;
                } else {
                    switch (action) {
                        case "Send":
                            String mess = chatter.getIn().readUTF();

                            String name = chatter.getIn().readUTF();

                            for (Chatter c : Server.list) {
                                c.getOut().writeUTF("Send");
                                c.getOut().writeUTF("[" + name + "] " + mess);
                            }
                            break;
                        case "SendPM":
                            String mess1 = chatter.getIn().readUTF();
                            int j = chatter.getIn().readInt();
                            String name1 = chatter.getIn().readUTF();

                            Server.list.get(j).getOut().writeUTF("SendPM");
                            Server.list.get(j).getOut().writeUTF("[PM from " + name1 + "] " + mess1);

                            break;
                        case "SendFileforAll":
                            String fileName = chatter.getIn().readUTF();
                            long fileSize = chatter.getIn().readLong();

                            // Tạo một mảng byte để lưu dữ liệu tệp
                            byte[] fileData = new byte[(int) fileSize];
                            chatter.getIn().readFully(fileData);
                            String name2 = chatter.getIn().readUTF();

                            for (Chatter c : Server.list) {
                                c.getOut().writeUTF("SendFileforAll");
                                c.getOut().writeUTF(fileName);
                                c.getOut().writeLong(fileData.length);
                                c.getOut().write(fileData);
                                c.getOut().writeUTF(name2);
                            }


                            break;
                        case "SendFileforOne":
                            String fileName1 = chatter.getIn().readUTF();
                            long fileSize1 = chatter.getIn().readLong();

                            // Tạo một mảng byte để lưu dữ liệu tệp
                            byte[] fileData1 = new byte[(int) fileSize1];
                            chatter.getIn().readFully(fileData1);
                            int j2 = chatter.getIn().readInt();
                            String name3 = chatter.getIn().readUTF();

                            for (Chatter c : Server.list) {
                                c.getOut().writeUTF("SendFileforOne");
                                c.getOut().writeUTF(fileName1);
                                c.getOut().writeLong(fileData1.length);
                                c.getOut().write(fileData1);
                                c.getOut().writeInt(j2);
                                c.getOut().writeUTF(name3);
                            }

                            break;
                        default:
                            System.out.println("action: "+action);
                            throw new AssertionError();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Processing.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
