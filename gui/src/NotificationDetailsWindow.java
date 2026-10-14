import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Classe onde esta o codigo relacionado com a GUI das notificações
 * @author Rodrigo Pereira
 */
public class NotificationDetailsWindow extends JFrame implements ActionListener, WindowListener {
    private JFrame window;
    private JButton yesButton, noButton;

    private Object[] array;
    private User user;

    public NotificationDetailsWindow(Object[] aObjectArray, User aUser) {
        array = aObjectArray;
        user = aUser;
        window = new JFrame();

        window.setTitle("Details");
        window.setSize(500, 300); 
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel id = new JLabel("" + aObjectArray[0]);
        id.setToolTipText("Notification's identifier");
        JLabel title = new JLabel((String)aObjectArray[1]);
        title.setToolTipText("Notification's title");
        JTextArea content = new JTextArea((String)aObjectArray[2]);
        content.setToolTipText("Notification's content");
        content.setWrapStyleWord(true);
        content.setLineWrap(true);
        content.setEditable(false);
        JLabel type = new JLabel((String)aObjectArray[3]);
        type.setToolTipText("Notification's type (information or choice)");
        JLabel viewed;

        if (aObjectArray[4].equals(true)) {
            viewed = new JLabel("true");
        } else {
            viewed = new JLabel("false");
        }

        viewed.setToolTipText("Notification's viewed status");
    
        JLabel idLabel = new JLabel("ID: ");
        JLabel titleLabel = new JLabel("Title: ");
        JLabel contentLabel = new JLabel("Content: ");
        JLabel typeLabel = new JLabel("Type: ");
        JLabel viewedLabel = new JLabel("Viewed: ");

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel infoPanel = new JPanel(new GridLayout(5, 8));
        JPanel auxPanel = new JPanel(new FlowLayout());

        infoPanel.add(idLabel);
        infoPanel.add(id);

        infoPanel.add(titleLabel);
        infoPanel.add(title);

        infoPanel.add(contentLabel);
        infoPanel.add(content);

        infoPanel.add(typeLabel);
        infoPanel.add(type);
        
        infoPanel.add(viewedLabel);
        infoPanel.add(viewed);

        if (aObjectArray[3].equals("Choice")) {
            JLabel acceptLabel = new JLabel("Do you accept?");
            yesButton = new JButton("Yes");
            yesButton.setToolTipText("Accept the action");
            yesButton.addActionListener(this);
            noButton = new JButton("No");
            noButton.setToolTipText("Refuse the action");
            noButton.addActionListener(this);

            auxPanel.add(acceptLabel);
            auxPanel.add(yesButton);
            auxPanel.add(noButton);

            mainPanel.add(auxPanel, BorderLayout.SOUTH);
        }

        mainPanel.add(infoPanel, BorderLayout.CENTER);

        window.add(mainPanel);
        window.addWindowListener(this);
    }

    /**
     * Método para mostrar a janela
     * @author Rodrigo Pereira
     */

    public void show() {
        window.setVisible(true);
    }

    /**
     * Método para a lógica das notificações de escolha
     * @author Rodrigo Pereira
     */

    public void Choice(Object[] aObjectArray, User aUser, boolean aOption) {
        Searches newSearch = new Searches();
        Manager newManager = new Manager("Manager");
        Updates newUpdate = new Updates();

        if (aObjectArray[1].equals("Head reviewer proposal")) {
            if (aOption) {
                Long serialNumber = newSearch.searchNotificationBySN((int)aObjectArray[0]);
                Reviewer r = new Reviewer("Reviewer");
                if (r.addHeadReviewer(aUser.getLogin(), serialNumber)) {
                    JOptionPane.showMessageDialog(window, "Proposal accepted successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    newUpdate.updateNotificationViewed(null, (int)aObjectArray[0]);
                } else {
                    JOptionPane.showMessageDialog(window, "An error occurred while accepting the proposal.", "Error", JOptionPane.ERROR_MESSAGE);
                    newUpdate.updateNotificationViewed(null, (int)aObjectArray[0]);
                }
            } else {
                Long serialNumber = newSearch.searchNotificationBySN((int)aObjectArray[0]);
                DeclinedReviewers newDeclinedReviewer = new DeclinedReviewers();
                if (newDeclinedReviewer.addDeclinedReviewer(aUser.getLogin(), serialNumber)) {
                    Review review = newSearch.searchReviewBySN(serialNumber);
                    Inserts i = new Inserts();
                    Notification n2 = new Notification();
                    n2 = n2.informationReviewerRejectedProposalN(aUser, review);

                    if (i.insertNotification(n2.getTitle(), n2.getContent(), n2.getType(), false)) {
                        int id2 = newSearch.searchNotificationById();
                        i.assignUserNotification(id2, review.getManager().getLogin());
                        JOptionPane.showMessageDialog(window, "Proposal rejected successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
                        newUpdate.updateNotificationViewed(null, (int)aObjectArray[0]);
                    }
                }
            }
        } else if (aObjectArray[1].equals("Account data omission proposal")) {
            String description = (String)(aObjectArray[2]);

            int indexFirstQuote = description.indexOf('\"');
            int indexLastQuote = description.indexOf('\"', indexFirstQuote + 1);

            String login = description.substring(indexFirstQuote + 1, indexLastQuote);
            
            if (aOption) {
                if (newManager.inactivateAccount(login)) {
                    JOptionPane.showMessageDialog(window, "Proposal accepted successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    newUpdate.updateNotificationViewed(null, (int)aObjectArray[0]);
                } else {
                    JOptionPane.showMessageDialog(window, "An error occurred while accepting the omission proposal.", "Error", JOptionPane.ERROR_MESSAGE);
                    newUpdate.updateNotificationViewed(null, (int)aObjectArray[0]);
                }
            } else {
                Inserts i = new Inserts();
                Notification n = new Notification();
                n = n.informationAccountDataOmitProposalRejectedN();

                if (i.insertNotification(n.getTitle(), n.getContent(), n.getType(), false)) {
                    int id = newSearch.searchNotificationById();
                    i.assignUserNotification(id, login);
                    JOptionPane.showMessageDialog(window, "Proposal rejected successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    newUpdate.updateNotificationViewed(null, (int)aObjectArray[0]);
                } else {
                    JOptionPane.showMessageDialog(window, "An error ocurred while rejecting the omission proposal.", "Error", JOptionPane.ERROR_MESSAGE);
                    newUpdate.updateNotificationViewed(null, (int)aObjectArray[0]);
                }
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        if ((boolean)array[4] == false) {
            Updates newUpdate = new Updates();
            newUpdate.updateNotificationViewed(null, (int)array[0]);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(yesButton)) {
            Choice(array, user, true);
            window.dispose();
        } else if (e.getSource().equals(noButton)) {
            Choice(array, user, false);
            window.dispose();
        }
    }
}
