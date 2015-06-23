import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Single on 23.06.2015.
 */
public class UI extends JFrame {
    private JButton add;
    private JButton remove;
    private JList userList;
    private JFormattedTextField startDateTime;
    private JTextField endDateTime;
    private JButton createConnection;
    private static JButton startDownload;
    private final DefaultListModel listModel = new DefaultListModel();

    public static void setStartDownloadEnable(boolean enable) {
        startDownload.setEnabled(enable);
    }

    public UI() {
        setSize(400, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        createView();
    }

    private void createView() {
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout());
        userPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        userList = new JList(listModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.setBackground(Color.cyan);
        userList.setPreferredSize(new Dimension(150, 0));
        userPanel.add(userList, BorderLayout.CENTER);

        MyListener listener = new MyListener();

        JPanel panelButton = new JPanel();
        add = new JButton("Add");
        add.addActionListener(listener);
        remove = new JButton("Remove");
        remove.addActionListener(listener);
        panelButton.add(add);
        panelButton.add(remove);
        userPanel.add(panelButton, BorderLayout.SOUTH);

        add(userPanel, BorderLayout.WEST);


        DateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormatter formatter = new DateFormatter(date);
        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);

        JPanel rightPanel = new JPanel();
        JLabel labelTimeStart = new JLabel("Time start:");
        JLabel labelTimeEnd = new JLabel("Time end:  ");
        startDateTime = new JFormattedTextField(formatter);
        startDateTime.setColumns(15);
        startDateTime.setText("2015-06-22 15:50:55");
        endDateTime = new JTextField(15);
        endDateTime.setText("2015-06-23 00:00:55");
        rightPanel.add(labelTimeStart);
        rightPanel.add(startDateTime);
        rightPanel.add(labelTimeEnd);
        rightPanel.add(endDateTime);

        createConnection = new JButton("Create connection");
        createConnection.addActionListener(listener);
        rightPanel.add(createConnection);

        startDownload = new JButton("Start Download");
        startDownload.addActionListener(listener);
        startDownload.setEnabled(false);
        rightPanel.add(startDownload);
        getContentPane().add(rightPanel, BorderLayout.CENTER);
    }

    class MyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(add)) {
                String name = JOptionPane.showInputDialog(UI.this, "Введите имя!");
                listModel.addElement(name);
                userList.repaint();

            } else if (e.getSource().equals(remove)) {
                int selectIndex = userList.getSelectedIndex();
                if (selectIndex >= 0) {
                    listModel.remove(selectIndex);
                    userList.repaint();
                }
            } else if (e.getSource().equals(createConnection)) {
                new SetParameters();
            } else if (e.getSource().equals(startDownload)) {
                for (String st : getListComponents()) {
                    System.out.println(st);
                }
                Download download = new Download();
                download.downloading(getListComponents(), startDateTime.getText(), endDateTime.getText());
            }
        }

        public String[] getListComponents() {
            String[] listComponents = null;
            if (listModel.getSize() >= 0) {
                listComponents = new String[listModel.getSize()];
                for (int i = 0; i < listModel.getSize(); i++) {
                    listComponents[i] = (String) listModel.get(i);
                }
            }
            return listComponents;
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UI();
            }
        });
    }
}
