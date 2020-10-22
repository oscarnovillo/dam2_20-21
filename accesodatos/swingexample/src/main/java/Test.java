import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test {
    private JTextField textField1;
    private JButton button1;
    private JPanel panelMain;


    public Test() {
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"HELLO");
            }
        });
    }

    public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.setContentPane(new  Test().panelMain);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
    }
}
