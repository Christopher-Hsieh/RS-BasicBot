package scripts;
 
import java.awt.EventQueue;
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
 
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
 
public class GUI extends JFrame {
 
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
 
    /**
     * Launch the application.
     */
     public static void handleGui() {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        GUI gui = new GUI();
                        gui.setVisible(true);
                        gui.setLocationRelativeTo(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
 
    /**
     * Create the frame.
     */
    public GUI() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 183, 167);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
       
        final JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Lvl-1 Enchant", "Lvl-2 Enchant", "Lvl-3 Enchant", "Lvl-4 Enchant", "Lvl-5 Enchant", "Lvl-6 Enchant"}));
        comboBox.setBounds(10, 11, 155, 20);
        contentPane.add(comboBox);
       
        JButton btnStart = new JButton("Start");
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //main.alive = true;
                main.itemToEnchant = Integer.parseInt(textField.getText());
                main.spell = (String) comboBox.getSelectedItem();
                dispose();
            }
        });
        btnStart.setBounds(10, 102, 155, 23);
        contentPane.add(btnStart);
       
        JLabel lblCastOnId = new JLabel("Item ID to cast the spell on :");
        lblCastOnId.setBounds(10, 42, 155, 14);
        contentPane.add(lblCastOnId);
       
        textField = new JTextField();
        textField.setBounds(10, 67, 155, 20);
        contentPane.add(textField);
        textField.setColumns(10);
    }
}