import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.*;

public class Server implements ActionListener {
    ImageIcon i1, i3, i4, i6,i7,i9, i10, i12,i13, i15;
    Image i2, i5, i8, i11, i14;
    JLabel back, profile, video, phone,morevert, name,status;
    JTextField text;
    JButton btn;
    JPanel p1,a1;
    static  Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;

    Server() {
        f.setLayout(null);

        p1 = new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);

        i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        i3 = new ImageIcon(i2);
        back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        i6 = new ImageIcon(i5);
        profile = new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);

        i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        i9 = new ImageIcon(i8);
        video = new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);

        i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        i11 = i10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        i12 = new ImageIcon(i11);
        phone = new JLabel(i12);
        phone.setBounds(360,20,35,30);
        p1.add(phone);

        i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        i14 = i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        i15 = new ImageIcon(i14);
        morevert = new JLabel(i15);
        morevert.setBounds(420,20,10,25);
        p1.add(morevert);

        name = new JLabel("Gulshan");
        name.setBounds(110,15,100,25);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(name);

        status = new JLabel("Active Now");
        status.setBounds(110,35,100,25);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        p1.add(status);

        a1 = new JPanel();
        a1.setBounds(5,75,440,570);
        f.add(a1);

        text = new JTextField();
        text.setBounds(5, 645,310,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,14));
        f.add(text);

        btn = new JButton("Send");
        btn.setBounds(320,645,123,40);
        btn.setBackground(new Color(7, 94,84));
        btn.setFont(new Font("SAN_SERIF",Font.BOLD,14));
        btn.addActionListener(this);
        btn.setForeground(Color.WHITE);
        f.add(btn);

        f.setSize(450,700);
        f.setLocation(200,30);
        f.getContentPane().setBackground(Color.WHITE);
        f.setUndecorated(true);
        f.setVisible(true);

    }
    public  void actionPerformed(ActionEvent ae) {
        try {
            String out = text.getText();
//        JLabel output = new JLabel(out);

            JPanel p2 = formatLabel(out);


            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(8));

            a1.add(vertical,BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tohoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);


        return panel;
    }

    public static void main(String[] args) {

        new Server();
        try {
            ServerSocket skt = new ServerSocket(6001);
            while (true) {
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while (true) {
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}