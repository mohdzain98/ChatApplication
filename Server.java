/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chatapplication;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.net.*;
import javax.imageio.ImageIO;

/**
 *
 * @author HTC
 */
public class Server  implements ActionListener{
    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    Server(){
        f.setLayout(null);
        JPanel p1 = new JPanel();
        p1.setBounds(0,0,500,70);
        f.add(p1);
        p1.setLayout(null);
        p1.setBackground(new Color(7,94,84));
        f.setSize(450,700);
        f.setLocation(200, 10);
        f.getContentPane().setBackground(Color.WHITE);
        
        //back image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);
        
        //profile
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/blank.jpg"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);
        
        JLabel name = new JLabel("nawaz");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(name);
        
       
        JLabel status = new JLabel("Active Now");
        status.setBounds(110,35,100,18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        p1.add(status);
        
        a1 = new JPanel();
        a1.setBounds(5,75,440,570);
        f.add(a1);
        
        f.setUndecorated(true);
        
        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("SAN+SERIF",Font.PLAIN,16));
        f.add(text);
        
        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.setFont(new Font("SAN+SERIF",Font.PLAIN,16));
        send.addActionListener(this);
        f.add(send);
        back.addMouseListener(new MouseAdapter(){
            @Override
                 public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });
        f.setVisible(true);
        
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       try{
           String out = text.getText();
                JPanel p2 = formatLabel(out);

                a1.setLayout(new BorderLayout());
                JPanel right = new JPanel(new BorderLayout());
                right.add(p2,BorderLayout.LINE_END);
                vertical.add(right);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical,BorderLayout.PAGE_START);
                dout.writeUTF(out);
                text.setText(" ");
                f.repaint();
                f.invalidate();
                f.validate();
                
       }catch(IOException ex){
           ex.printStackTrace();
       }
    }
    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style=\"width:150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        
        panel.add(output);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;
    }
    public static void main(String[] args) {
        // TODO code application logic here
        new Server();
        try{
           ServerSocket skt = new ServerSocket(6001);
           while(true){
               Socket s = skt.accept(); 
               DataInputStream din = new DataInputStream(s.getInputStream());
               dout = new DataOutputStream(s.getOutputStream());
               while(true){
                   String msg = din.readUTF();
                   JPanel panel =formatLabel(msg);
                   JPanel left = new JPanel(new BorderLayout());
                   left.add(panel,BorderLayout.LINE_START);
                   vertical.add(left);
                   f.validate();
               }
           
           }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    
    
}
