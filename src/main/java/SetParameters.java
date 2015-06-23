import DB.DaoImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Single on 23.06.2015.
 */
public class SetParameters extends JFrame {
    public SetParameters() {
        createView();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        pack();
    }

    private void createView() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel level1 = new JPanel();
        level1.setLayout(new BoxLayout(level1, BoxLayout.Y_AXIS));
        level1.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        JLabel nameDBLabel = new JLabel("Name DB");
        JTextField nameDBField = new JTextField(15);
        level1.add(nameDBLabel);
        level1.add(nameDBField);

        JLabel loginDBLabel = new JLabel("Login DB");
        JTextField loginDBField = new JTextField(15);
        level1.add(loginDBLabel);
        level1.add(loginDBField);

        JLabel passwordDBLabel = new JLabel("Password DB");
        JTextField passwordDBField = new JTextField(15);
        level1.add(passwordDBLabel);
        level1.add(passwordDBField);

        JLabel ipDBLabel = new JLabel("IP address");
        JTextField ipDBField = new JTextField(15);
        level1.add(ipDBLabel);
        level1.add(ipDBField);

        JLabel portDBLabel = new JLabel("Port");
        JTextField portDBField = new JTextField(15);
        level1.add(portDBLabel);
        level1.add(portDBField);

        level1.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(level1, BorderLayout.CENTER);

        JButton button = new JButton("Set values");
        panel.add(button, BorderLayout.SOUTH);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameDb = nameDBField.getText();
                String loginDB = loginDBField.getText();
                String passwordDB = passwordDBField.getText();
                String ipAddress = ipDBField.getText();
                String port = portDBField.getText();

                if (!(nameDb.isEmpty() || loginDB.isEmpty()
                        || passwordDB.isEmpty() || port.isEmpty()
                        || ipAddress.isEmpty())) {
                    UI.setStartDownloadEnable(true);

                    DaoImpl.getInstance().setParameters(nameDb, loginDB, passwordDB, ipAddress, port);
                } else JOptionPane.showMessageDialog(
                        null,
                        "Необходимо заполнить все поля!",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        setContentPane(panel);
    }
}
