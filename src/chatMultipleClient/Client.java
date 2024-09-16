/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package chatMultipleClient;

import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import jnafilechooser.api.JnaFileChooser;

/**
 *
 * @author Admin
 */
public class Client extends javax.swing.JFrame implements Runnable {

    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    static DefaultListModel listModel;
    String name = "";
    boolean isSender = false;

    /**
     * Creates new form Client
     */
    public Client() throws IOException {
        socket = new Socket("192.168.18.130", 1309);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        initComponents();
//        out.writeUTF("ess");
        setLocationRelativeTo(null);
        listModel = new DefaultListModel<String>();
        listModel.addElement("Send to All");

        jList.setModel(listModel);
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (socket != null) {
                    String tt = in.readUTF();
                    switch (tt) {
                        case "add":
//                            Nhan ten
                            String name = in.readUTF();
                            txtArea.append("\n[" + name + "] is connected");
                            listModel.addElement(name);
                            break;
                        case "insert":
                            int size = in.readInt();
                            if (size > 0) {
                                for (int i = 0; i < size; i++) {
                                    listModel.addElement(in.readUTF());
                                }
                            }
                            break;
                        case "remove":
                            String name1 = in.readUTF();
                            listModel.removeElement(name1);
                            txtArea.append("\n[" + name1 + "] has left the room");
                            break;
                        case "Send":
                            String m = in.readUTF();
                            txtArea.append("\n" + m);
                            break;
                        case "SendPM":
                            String m1 = in.readUTF();
                            txtArea.append("\n" + m1);
                            break;
                        case "SendFileforAll":
                            System.out.println("Client nhan tu Processing");
                            String fileName = in.readUTF();
                            long fileSize = in.readLong();

                            // Tạo mảng byte để lưu dữ liệu tệp
                            byte[] fileData = new byte[(int) fileSize];
                            // Đọc toàn bộ dữ liệu tệp từ server
                            in.readFully(fileData);
                            File file = null;

//                            int j = in.readInt();
                            String name2 = in.readUTF();
                            txtArea.append("\n[" + name2 + "] sent a file " + fileName);
                            // Chỉ hỏi mở file nếu client không phải là người gửi
                            if (!isSender) {
                                // Lưu file vào hệ thống
                                file = new File("received_" + fileName);
                                try (FileOutputStream fos = new FileOutputStream(file)) {
                                    fos.write(fileData);
                                }
                                int response = JOptionPane.showConfirmDialog(null,
                                        "File " + fileName + " received. Do you want to open it?",
                                        "Open File",
                                        JOptionPane.YES_NO_OPTION);

                                if (response == JOptionPane.YES_OPTION) {
                                    // Mở file nếu người dùng chọn Yes
                                    if (Desktop.isDesktopSupported()) {
                                        Desktop.getDesktop().open(file);
                                    } else {
                                        txtArea.append("Cannot open file: Desktop is not supported\n");
                                    }
                                }

                            } else {
                                // Reset lại biến isSender sau khi gửi file
                                isSender = false;

                            }

                            break;

                        case "SendFileforOne":
                            System.out.println("Client nhan tu Processing");
                            String fileName1 = in.readUTF();
                            long fileSize1 = in.readLong();

                            // Tạo mảng byte để lưu dữ liệu tệp
                            byte[] fileData1 = new byte[(int) fileSize1];
                            // Đọc toàn bộ dữ liệu tệp từ server
                            in.readFully(fileData1);
                            File file1 = null;

                            String name3 = in.readUTF();
                            txtArea.append("\n[" + name3 + "] sent a file " + fileName1);
                            // Chỉ hỏi mở file nếu client không phải là người gửi
                            if (!isSender) {
                                // Lưu file vào hệ thống
                                file1 = new File("received_" + fileName1);
                                try (FileOutputStream fos = new FileOutputStream(file1)) {
                                    fos.write(fileData1);
                                }
                                int response = JOptionPane.showConfirmDialog(null,
                                        "File " + fileName1 + " received. Do you want to open it?",
                                        "Open File",
                                        JOptionPane.YES_NO_OPTION);

                                if (response == JOptionPane.YES_OPTION) {
                                    // Mở file nếu người dùng chọn Yes
                                    if (Desktop.isDesktopSupported()) {
                                        Desktop.getDesktop().open(file1);
                                    } else {
                                        txtArea.append("Cannot open file: Desktop is not supported\n");
                                    }
                                }

                            } else {
                                // Reset lại biến isSender sau khi gửi file
                                isSender = false;

                            }

                            break;
                    }
                }

            } catch (IOException ex) {
//                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList = new javax.swing.JList<>();
        btnStart = new javax.swing.JButton();
        txtText = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        btnSendFile = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        txtArea.setColumns(20);
        txtArea.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtArea.setRows(5);
        txtArea.setText("Welcome!\nEnter your nickname and ");
        txtArea.setEnabled(false);
        jScrollPane1.setViewportView(txtArea);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("List Chatter");

        jScrollPane2.setViewportView(jList);

        btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        txtText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTextKeyPressed(evt);
            }
        });

        btnSend.setText("Send");
        btnSend.setEnabled(false);
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        btnSendFile.setText("File");
        btnSendFile.setEnabled(false);
        btnSendFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtText))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSendFile)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnSend, btnSendFile});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(txtText)
                    .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSendFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            // TODO add your handling code here:
            out.writeUTF("Cancel");
            socket.close();
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed

        // TODO add your handling code here:
        name = txtText.getText();
//            
        boolean ok = true;
        for (int i = 0; i < listModel.size(); i++) {
            if (name.equals(listModel.getElementAt(i))) {
                JOptionPane.showConfirmDialog(this, "Tên đã tồn tại. \nBạn hãy thử tên khác");
                ok = false;
                name = "";
                break;
            }
        }

        if (ok == true) {

            try {
                btnStart.setEnabled(false);
                btnSend.setEnabled(true);
                btnSendFile.setEnabled(true);

                out.writeUTF(name);
                System.out.println("" + name);
                txtText.setText("");
                this.setTitle(name);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }//GEN-LAST:event_btnStartActionPerformed

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        // TODO add your handling code here:
        String mess = txtText.getText();
        int i = jList.getSelectedIndex() - 1;

        if (i < 0) {
            try {
                txtText.setText("");
                out.writeUTF("Send");
                out.writeUTF(mess);
                out.writeUTF(name);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                txtText.setText("");
                out.writeUTF("SendPM");
                out.writeUTF(mess);
                out.writeInt(i);
                out.writeUTF(name);
                txtArea.append("\n[PM to " + jList.getSelectedValue() + "] " + mess);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnSendActionPerformed

    private void txtTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTextKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (name.equals("")) {
                btnStart.doClick();
            } else {
                btnSend.doClick();
            }
        }
    }//GEN-LAST:event_txtTextKeyPressed

    private void btnSendFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendFileActionPerformed
        // TODO add your handling code here:
        int i = jList.getSelectedIndex() - 1;
//        gửi all
        if (i < 0) {
            try {
                // TODO add your handling code here:
                out.writeUTF("SendFileforAll");
                jnafilechooser.api.JnaFileChooser fileChooser = new JnaFileChooser();
                boolean result = fileChooser.showOpenDialog(this);
                if (result) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        byte[] fileData = Files.readAllBytes(Paths.get(file.getAbsolutePath()));

//                    out.writeUTF("FILE");
                        out.writeUTF(file.getName());
                        out.writeLong(fileData.length);
                        out.write(fileData);
                        out.writeUTF(name);
//                    txtArea.append("File sent: " + file.getName() + "\n");
                        System.out.println("Client -> Processing");

                        // Đánh dấu client này là người gửi
                        isSender = true;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                // TODO add your handling code here:
                out.writeUTF("SendFileforOne");
                jnafilechooser.api.JnaFileChooser fileChooser = new JnaFileChooser();
                boolean result = fileChooser.showOpenDialog(this);
                if (result) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        byte[] fileData = Files.readAllBytes(Paths.get(file.getAbsolutePath()));

//                    out.writeUTF("FILE");
                        out.writeUTF(file.getName());
                        out.writeLong(fileData.length);
                        out.write(fileData);
                        out.writeInt(i);
                        out.writeUTF(name);
//                  

                        // Đánh dấu client này là người gửi
                        isSender = true;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_btnSendFileActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    new Client().setVisible(true);
//                } catch (IOException ex) {
//                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
        Client c = new Client();
        c.setVisible(true);
        Thread t = new Thread(c);
        t.start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSend;
    private javax.swing.JButton btnSendFile;
    private javax.swing.JButton btnStart;
    private javax.swing.JLabel jLabel1;
    static javax.swing.JList<String> jList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private static javax.swing.JTextArea txtArea;
    private javax.swing.JTextField txtText;
    // End of variables declaration//GEN-END:variables

}
