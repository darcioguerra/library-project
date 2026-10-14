import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;

/**
 * Classe onde esta o codigo relacionado com a GUI do Revisor
 * @author Rodrigo Pereira e Dárcio Guerra
 */
public class MainReviewerWindow extends JFrame implements ActionListener, WindowListener{
    private JFrame window;

    private Reviewer newReviewer;
    private Searches searchReviewer = new Searches();

    //paineis e objetos genericos
    private JPanel cardPanel, mainPanel;
    private JPanel welcomePanel, notificationPanel, cnauxPanel, cntablePanel;

    private JMenuItem exitItem;

    private JButton checkNotifications;
    private JButton mainBack;

    //paineis e objetos do revisor
    private JPanel startReviewPanel;
    private JPanel addLicenceReviewPanel;
    private JPanel selectLicencePanel;
    private JPanel selectReviewPanel;
    
    private JButton startReview, finishReview;
    private JButton startRButton, addLicenceRButton, finishRButton;

    private JButton addLicenceProceedButton, addAnnotationProceedButton, returnBack;

    private JMenuItem addLicenceReviewItem, addAnnotationReviewItem, requestAccountRemovalItem;

    private JTable reviewTable, licenceTable;

    //variáveis globais
    private String[] mode;
    private Long sn;
    private int id;

    public MainReviewerWindow(String[] aMode) {
        mode = aMode;
        window = new JFrame();

        window.setTitle("Reviewer Menu");
        window.setSize(700, 500); 
        window.setLocationRelativeTo(null);

        initReviewer(aMode);
        createReviewerWindow(aMode);
        
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
     * Método para inicializar um objeto reviewer
     * @author Rodrigo Pereira
     */

    public void initReviewer(String[] aData) {
        newReviewer = new Reviewer(aData[0]);
        newReviewer = searchReviewer.searchReviewer(aData[1]);
    }

    /**
     * Método para criar a janela do revisor
     * @author Rodrigo Pereira
     */

    public void createReviewerWindow(String[] aMode) {
        if (cardPanel == null) {
            cardPanel = new JPanel(new CardLayout());
            window.add(cardPanel);
        }
    
        CardLayout cl = (CardLayout)(cardPanel.getLayout());

        if (mainPanel == null) {
            JPanel mainPanel = new JPanel(new BorderLayout(5, 5));

            JMenuBar reviewerMenuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("File");
            fileMenu.setToolTipText("General options");
            fileMenu.setMnemonic(KeyEvent.VK_F);

            exitItem = new JMenuItem("Exit");
            exitItem.setToolTipText("Log out of your account");
            exitItem.setMnemonic(KeyEvent.VK_X);
            exitItem.addActionListener(this);

            requestAccountRemovalItem = new JMenuItem("Request Account Removal");
            requestAccountRemovalItem.setToolTipText("Sent a request to a manager to remove your account");
            requestAccountRemovalItem.setMnemonic(KeyEvent.VK_C);
            requestAccountRemovalItem.addActionListener(this);

            fileMenu.add(requestAccountRemovalItem);
            fileMenu.add(exitItem);

            JMenu addMenu = new JMenu("Add");
            addMenu.setToolTipText("Add information to the system");
            addMenu.setMnemonic(KeyEvent.VK_A);

            addLicenceReviewItem = new JMenuItem("Licence to Review...");
            addLicenceReviewItem.setToolTipText("Add a licence to a review");
            addLicenceReviewItem.setMnemonic(KeyEvent.VK_L);
            addLicenceReviewItem.addActionListener(this);
            addAnnotationReviewItem = new JMenuItem("Annotation to Review...");
            addAnnotationReviewItem.setToolTipText("Add an annotation to a review");
            addAnnotationReviewItem.setMnemonic(KeyEvent.VK_A);
            addAnnotationReviewItem.addActionListener(this);

            addMenu.add(addLicenceReviewItem);
            addMenu.add(addAnnotationReviewItem);

            reviewerMenuBar.add(fileMenu);
            reviewerMenuBar.add(addMenu);

            window.setJMenuBar(reviewerMenuBar);

            JLabel welcometext = new JLabel("Welcome " + aMode[1]);
            welcometext.setFont(new Font(null, Font.PLAIN, 24));
            welcomePanel = new JPanel(new FlowLayout());
            welcomePanel.add(welcometext);

            checkNotifications = new JButton("Notifications");
            checkNotifications.setToolTipText("Click to see your notifications");
            checkNotifications.addActionListener(this);

            startReview = new JButton("Start Review");
            startReview.setToolTipText("Click to start a review");
            startReview.addActionListener(this);

            finishReview = new JButton("Finish Review");
            finishReview.setToolTipText("Click to finish a review");
            finishReview.addActionListener(this);

            JPanel auxPanel = new JPanel(new FlowLayout());
            auxPanel.add(checkNotifications);
            auxPanel.add(startReview);
            auxPanel.add(finishReview);

            mainPanel.add(welcomePanel, BorderLayout.NORTH);
            mainPanel.add(auxPanel, BorderLayout.CENTER);
            cardPanel.add(mainPanel, "Welcome");
        }
    
        cl.show(cardPanel, "Welcome");
    }

    /**
     * Método para enviar uma notificação de término de conta
     * @author Rodrigo Pereira 
     */

    public void requestAccountRemoval(CardLayout aCl) {
        Searches newSearch = new Searches();
        Client newClient = newSearch.searchReviewer(mode[1]);

        if (newClient.requestAccountRemoval(newClient) == true) {
            JOptionPane.showMessageDialog(window, "Request sent successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(window, "Request failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para criar o painel para iniciar revisões
     * @author Rodrigo Pereira
     */

    public void startReviewPanel(CardLayout aCl) {
        String col[] = {"Title","Name","Serial Number","State"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        newReviewer.listReviews(tableModel, "Approved");

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(getBackground(), 10, false));

        startReviewPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Start Review");
        title.setFont(new Font(null, Font.PLAIN, 24));

        reviewTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(reviewTable);
        reviewTable.setAutoCreateRowSorter(true);
        reviewTable.setFillsViewportHeight(true);

        startReviewPanel.add(scrollPane);

        JPanel startReviewAuxPanel = new JPanel(new FlowLayout());

        mainBack = new JButton("< Back");
        mainBack.setToolTipText("Button to return to Reviewer Menu");
        mainBack.addActionListener(this);
        
        startRButton = new JButton("Start");
        startRButton.setToolTipText("Button to star review");
        startRButton.addActionListener(this);
        
        startReviewAuxPanel.add(mainBack);
        startReviewAuxPanel.add(startRButton);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(startReviewPanel);
        mainPanel.add(startReviewAuxPanel, BorderLayout.SOUTH);
        cardPanel.add(mainPanel, "StartReview");
        
        aCl.show(cardPanel, "StartReview");
    }

    /**
     * Método para a lógica do botão de iniciar revisões
     * @author Rodrigo Pereira
     */

    public void startRButton(CardLayout aCl) {
        sn = (Long)reviewTable.getValueAt(reviewTable.getSelectedRow(), 2);
        
        if (sn == null) {
            JOptionPane.showMessageDialog(window, "Error retrieving serial number. \nHave you tried selecting a row from the table?", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (newReviewer.startReview(sn)) {
                JOptionPane.showMessageDialog(window, "Review started successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
                aCl.show(cardPanel, "Welcome");
            } else {
                JOptionPane.showMessageDialog(window, "An error occurred while starting the specified review.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Método para criar o painel para adicionar licenças
     * @author Rodrigo Pereira
     */
    public void addLicenceReviewPanel(CardLayout aCl) {
        String col[] = {"Title","Name","Serial Number","State"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        newReviewer.listReviews(tableModel, "Ongoing");

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(getBackground(), 10, false));

        addLicenceReviewPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Add Licence to Review");
        title.setFont(new Font(null, Font.PLAIN, 24));

        reviewTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(reviewTable);
        reviewTable.setAutoCreateRowSorter(true);
        reviewTable.setFillsViewportHeight(true);

        addLicenceReviewPanel.add(scrollPane);

        JPanel addLicenceReviewAuxPanel = new JPanel(new FlowLayout());

        mainBack = new JButton("< Back");
        mainBack.setToolTipText("Button to return to Author Menu");
        mainBack.addActionListener(this);
        
        addLicenceProceedButton = new JButton("Continue >");
        addLicenceProceedButton.setToolTipText("Button to select review and proceed to next screen");
        addLicenceProceedButton.addActionListener(this);
        
        addLicenceReviewAuxPanel.add(mainBack);
        addLicenceReviewAuxPanel.add(addLicenceProceedButton);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(addLicenceReviewPanel);
        mainPanel.add(addLicenceReviewAuxPanel, BorderLayout.SOUTH);
        cardPanel.add(mainPanel, "AddLicenceReview");
        
        aCl.show(cardPanel, "AddLicenceReview");
    }

    /**
     * Método para a lógica do botão de proceder
     * @author Rodrigo Pereira
     */

    public void proceedButton(CardLayout aCl) {
        sn = (Long)reviewTable.getValueAt(reviewTable.getSelectedRow(), 2);
        if (sn == null)
            JOptionPane.showMessageDialog(window, "Error retrieving serial number. \nHave you tried selecting a row from the table?", "Error", JOptionPane.ERROR_MESSAGE);
        else 
            selectLicencePanel(aCl);
    }

    /**
     * Método para criar o painel de seleção de licenças
     * @author Rodrigo Pereira
     */

    public void selectLicencePanel(CardLayout aCl) {
        Licence newLicence = new Licence();
        String col[] = {"ID","Name","Description","Date", "Valid?"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        newLicence.listLicences(tableModel);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(getBackground(), 10, false));

        selectLicencePanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Select Licence");
        title.setFont(new Font(null, Font.PLAIN, 24));

        licenceTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(licenceTable);
        licenceTable.setAutoCreateRowSorter(true);
        licenceTable.setFillsViewportHeight(true);

        selectLicencePanel.add(scrollPane);

        JPanel selectLicenceAuxPanel = new JPanel(new FlowLayout());

        returnBack = new JButton("< Back");
        mainBack.setToolTipText("Button to return to Select Review screen");
        returnBack.addActionListener(this);
        
        addLicenceRButton = new JButton("Confirm");
        addLicenceProceedButton.setToolTipText("Button to select licence and add it to review");
        addLicenceRButton.addActionListener(this);
        
        selectLicenceAuxPanel.add(returnBack);
        selectLicenceAuxPanel.add(addLicenceRButton);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(selectLicencePanel);
        mainPanel.add(selectLicenceAuxPanel, BorderLayout.SOUTH);
        cardPanel.add(mainPanel, "SelectLicence");
        
        aCl.show(cardPanel, "SelectLicence");
    }

    /**
     * Método para a lógica do botão de adicionar licença
     * @author Rodrigo Pereira
     */

    public void addLicenceRButton(CardLayout aCl) {
        id = (int)licenceTable.getValueAt(licenceTable.getSelectedRow(), 0);
        if (id == 0)
            JOptionPane.showMessageDialog(window, "Error retrieving serial number. \nHave you tried selecting a row from the table?", "Error", JOptionPane.ERROR_MESSAGE);
        else {
            if (newReviewer.addLicences(sn, id)) {
                JOptionPane.showMessageDialog(null, "Licence added successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "An error occured while adding the licence to the specified review.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }      
    }

    /**
     * Método para criar o painel de adicionar anotações
     * @author Rodrigo Pereira
     */

    public void addAnnotationReviewPanel(CardLayout aCl) {
        String col[] = {"Title","Name","Serial Number","State"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        newReviewer.listReviews(tableModel, "Ongoing");

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(getBackground(), 10, false));

        selectReviewPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Add Annotation to Review");
        title.setFont(new Font(null, Font.PLAIN, 24));

        reviewTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(reviewTable);
        reviewTable.setAutoCreateRowSorter(true);
        reviewTable.setFillsViewportHeight(true);

        selectReviewPanel.add(scrollPane);

        JPanel selectReviewAuxPanel = new JPanel(new FlowLayout());

        mainBack = new JButton("< Back");
        mainBack.setToolTipText("Button to return to Select Review screen");
        mainBack.addActionListener(this);
        
        addAnnotationProceedButton = new JButton("Continue >");
        addAnnotationProceedButton.setToolTipText("Button to select review and proceed to next screen");
        addAnnotationProceedButton.addActionListener(this);
        
        selectReviewAuxPanel.add(mainBack);
        selectReviewAuxPanel.add(addAnnotationProceedButton);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(selectReviewPanel);
        mainPanel.add(selectReviewAuxPanel, BorderLayout.SOUTH);
        cardPanel.add(mainPanel, "AddAnnotationReview");
        
        aCl.show(cardPanel, "AddAnnotationReview");
    }

    /**
     * Método para criar o diálogo de adicionar anotações
     * @author Rodrigo Pereira
     */

    public void addAnnotationDialog(Long aSerialNumber) {

        JLabel descriptionLabel = new JLabel("Description:");
        JTextField description = new JTextField();
        description.setToolTipText("Field to insert annotation's description");
        JLabel pageLabel = new JLabel("Page:");
        JTextField page = new JTextField();
        page.setToolTipText("Field to insert annotation's page reference");
        JLabel paragraphLabel = new JLabel("Paragraph:");
        JTextField paragraph = new JTextField();
        paragraph.setToolTipText("Field to insert annotation's paragraph reference");
        JComponent[] inputs = new JComponent[] 
        {descriptionLabel, description, pageLabel, page, paragraphLabel, paragraph};
        int value = JOptionPane.showConfirmDialog(
        null,
        inputs,
        "Annotation's information",
        JOptionPane.OK_CANCEL_OPTION);

        if (value == JOptionPane.OK_OPTION) {
            Annotation newAnnotation = new Annotation();
            newAnnotation.setDescription(description.getText());
            newAnnotation.setPage(Integer.parseInt(page.getText()));
            newAnnotation.setDate(LocalDate.now());
            newAnnotation.setParagraph(Integer.parseInt(paragraph.getText()));

            if (newReviewer.addAnnotations(aSerialNumber, newAnnotation)) {
                JOptionPane.showMessageDialog(window, "Annotation added successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(window, "An error occurred while adding the new annotation.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Método para criar o painel para finalizar revisões
     * @author Rodrigo Pereira
     */

    public void finishReviewPanel(CardLayout aCl) {
        String col[] = {"Title","Name","Serial Number","State"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        newReviewer.listReviews(tableModel, "Ongoing");

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(getBackground(), 10, false));

        selectReviewPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Finish Review");
        title.setFont(new Font(null, Font.PLAIN, 24));

        reviewTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(reviewTable);
        reviewTable.setAutoCreateRowSorter(true);
        reviewTable.setFillsViewportHeight(true);

        selectReviewPanel.add(scrollPane);

        JPanel finishReviewAuxPanel = new JPanel(new FlowLayout());

        mainBack = new JButton("< Back");
        mainBack.setToolTipText("Button to return to Select Review screen");
        mainBack.addActionListener(this);
        
        finishRButton = new JButton("Finish");
        finishRButton.setToolTipText("Button to finish a review");
        finishRButton.addActionListener(this);
        
        finishReviewAuxPanel.add(mainBack);
        finishReviewAuxPanel.add(finishRButton);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(selectReviewPanel);
        mainPanel.add(finishReviewAuxPanel, BorderLayout.SOUTH);
        cardPanel.add(mainPanel, "FinishReview");
        
        aCl.show(cardPanel, "FinishReview");
    }

    /**
     * Método para a lógica do botão de finalizar revisões
     * @author Rodrigo Pereira
     */

    public void finishRButton(CardLayout aCl) {
        sn = (Long)reviewTable.getValueAt(reviewTable.getSelectedRow(), 2);
        
        if (sn == null) {
            JOptionPane.showMessageDialog(window, "Error retrieving serial number. \nHave you tried selecting a row from the table?", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (newReviewer.finishReview(sn)) {
                JOptionPane.showMessageDialog(window, "Review finished successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
                aCl.show(cardPanel, "Welcome");
            } else {
                JOptionPane.showMessageDialog(window, "An error occurred while finishing the specified review.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        int option = JOptionPane.showConfirmDialog(this, "Do you wish to logout?", "Info", JOptionPane.YES_NO_OPTION);

        System.out.println(option);

        if (option == JOptionPane.YES_OPTION) {
            window.setVisible(false);
            dispose();

            LoginWindow newLoginWindow = new LoginWindow();
            newLoginWindow.show();
        } else if (option == JOptionPane.NO_OPTION) {
            window.setVisible(false);
            dispose();
            MainReviewerWindow newMainReviewerWindow = new MainReviewerWindow(mode);
            newMainReviewerWindow.show(); 
        }

    }

    @Override
    public void windowClosed(WindowEvent e) {
        LoginWindow newLoginWindow = new LoginWindow();
        newLoginWindow.show();
    }

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
        CardLayout cl = (CardLayout)(cardPanel.getLayout());

        //revisor
        // | básicos
        if (e.getSource().equals(exitItem)) {
            windowClosing(null);
        } else if (e.getSource().equals(requestAccountRemovalItem)) {
            requestAccountRemoval(cl);
        }

        // | gestão de revisão
        if (e.getSource().equals(startReview)) {
            startReviewPanel(cl);
        } else if (e.getSource().equals(startRButton)) {
            startRButton(cl);
        } else if (e.getSource().equals(addLicenceReviewItem)) {
            addLicenceReviewPanel(cl);
        } else if (e.getSource().equals(addLicenceProceedButton)) {
            proceedButton(cl);
        } else if (e.getSource().equals(addLicenceRButton)) {
            addLicenceRButton(cl);
        } else if (e.getSource().equals(returnBack)) {
            addLicenceReviewPanel(cl);
        } else if (e.getSource().equals(finishReview)) {
            finishReviewPanel(cl);
        } else if (e.getSource().equals(finishRButton)) {
            finishRButton(cl);
        }

        // | gestão de anotações
        if (e.getSource().equals(addAnnotationReviewItem)) {
            addAnnotationReviewPanel(cl);
        } else if (e.getSource().equals(addAnnotationProceedButton)) {
            sn = (Long)reviewTable.getValueAt(reviewTable.getSelectedRow(), 2);
            if (sn == null) {
                JOptionPane.showMessageDialog(window, "Error retrieving serial number. \nHave you tried selecting a row from the table?", "Error", JOptionPane.ERROR_MESSAGE);
            } else 
                addAnnotationDialog(sn);
        }

        // | geral
        if (e.getSource().equals(checkNotifications)) {

            notificationPanel = new JPanel(new BorderLayout());
            cnauxPanel = new JPanel();
            cntablePanel = new JPanel(new BorderLayout());

            notificationPanel.setBorder(BorderFactory.createLineBorder(getBackground(), 10, false));
        
            JLabel windowtitle = new JLabel("Notifications");
            windowtitle.setFont(new Font(null, Font.PLAIN, 24));

            String col[] = {"Id","Title","Type","Viewed",""};

            DefaultTableModel tableModel = new DefaultTableModel(col, 0);

            ArrayList<Notification> notificationList = newReviewer.checkNotifications(newReviewer);

            for (int i = 0; i < notificationList.size(); i++){
                int id = notificationList.get(i).getId();
                String title = notificationList.get(i).getTitle();
                String type  = notificationList.get(i).getType();
                boolean viewed = notificationList.get(i).getViewed();
                
                Object[] data = {id, title, type, viewed, "Details"};
            
                tableModel.addRow(data);
            }
            
            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            table.setAutoCreateRowSorter(true);
            table.setFillsViewportHeight(true);

            cntablePanel.add(scrollPane);

            Action detailswindow = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    JTable table = (JTable)e.getSource();
                    int modelRow = Integer.valueOf( e.getActionCommand() );

                    Object[] objectArray = new Object[table.getModel().getColumnCount()];

                    objectArray[0] = notificationList.get(modelRow).getId();
                    objectArray[1] = notificationList.get(modelRow).getTitle();
                    objectArray[2] = notificationList.get(modelRow).getContent();
                    objectArray[3] = notificationList.get(modelRow).getType();
                    objectArray[4] = notificationList.get(modelRow).getViewed();
                                        
                    NotificationDetailsWindow window = new NotificationDetailsWindow(objectArray, newReviewer);
                    window.show();
                }
            };

            new ButtonColumn(table, detailswindow, 4, "Show the notification's details");
        
            mainBack = new JButton("< Back");
            mainBack.setToolTipText("Button to return to Reviewer Menu");
            mainBack.addActionListener(this);

            cnauxPanel.add(mainBack);

            notificationPanel.add(windowtitle, BorderLayout.NORTH);
            notificationPanel.add(cntablePanel);
            notificationPanel.add(cnauxPanel, BorderLayout.SOUTH);

            cardPanel.add(notificationPanel, "Notifications");
            
            
            cl.show(cardPanel, "Notifications");
            
        } else if (e.getSource().equals(mainBack)) {
            cl.show(cardPanel, "Welcome");
        }

    }

}
