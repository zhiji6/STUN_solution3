//package window_server_client;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class Dialog extends JDialog implements ActionListener {
    
    public Dialog(JFrame parent, String title, String message) {
        super(parent, title, true);
        if (parent != null) {
            Dimension parentSize = parent.getSize(); 
            Point p = parent.getLocation(); 
            setLocation(p.x, p.y);
            this.setSize(parentSize);
        }
        JTextArea jtxt_port = new JTextArea();
        jtxt_port.setBounds(50, 75, 150, 35);
        this.add(jtxt_port);
        JTextField jtxt_port1 = new JTextField();
        jtxt_port1.setBounds(50, 160, 150, 35);
        this.add(jtxt_port1);
        JPanel messagePane = new JPanel();
        messagePane.add(new JLabel(message));
        getContentPane().add(messagePane);
        JPanel buttonPane = new JPanel();
        JButton button = new JButton("OK"); 
        buttonPane.add(button); 
        button.addActionListener(this);
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //pack(); 
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        setVisible(false); 
        dispose(); 
    }
  
}
