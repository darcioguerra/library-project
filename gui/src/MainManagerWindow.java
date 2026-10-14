import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;

/**
 * Classe onde esta o codigo relacionado com a GUI do Gestor
 * @author Rodrigo Pereira e Dárcio Guerra
 */
public class MainManagerWindow extends JFrame implements ActionListener, WindowListener {
    private JFrame window;

    private Manager newManager;
    private Searches searchManager = new Searches();

    //paineis e objetos genericos
    private JPanel cardPanel, mainPanel;
    private JPanel welcomePanel, notificationPanel, cnauxPanel, cntablePanel;

    private JMenuItem exitItem;

    private JButton checkNotifications;
    private JButton mainBack;

    //paineis e objetos do gestor
    private JPanel approveUserPanel;
    private JPanel reproveUserPanel;
    private JPanel inactivateUserPanel;
    private JPanel selectReviewPanel, selectReviewerPanel;
    private JPanel approveReviewPanel;
    private JPanel reproveReviewPanel;
    private JPanel checkReviewPanel;
    
    private JButton approveUser, reproveUser, inactivateUser;
    private JButton approveButton, reproveButton, inactivateButton;

    private JButton selectReviewer, proceedButton, returnBack, finishButton;

    private JButton approveReview, reproveReview, checkReview;
    private JButton approveRButton, reproveRButton;

    private JMenuItem addUserItem, addLicenceItem;

    private JTable userTable, reviewTable, reviewerTable;

    private String[] mode;
    private String bookName;
    private Long sn;

    public MainManagerWindow(String[] aMode) {
        mode = aMode;
        window = new JFrame();

        window.setTitle("Manager Menu");
        window.setSize(700, 500); 
        window.setLocationRelativeTo(null);

        initManager(aMode);
        createManagerWindow(aMode);
        
        window.addWindowListener(this);
    }

    /**
     * Método para mostrar janela
     * @author Rodrigo Pereira
     */

    public void show() {
        window.setVisible(true);
    }

    /**
     * Método para inicializar um objeto da classe manager
     * @author Rodrigo Pereira
     */

    public void initManager(String[] aData) {
        newManager = new Manager(aData[0]);
        newManager = searchManager.searchManager(aData[1]);
    }

    /**
     * Método para criar a janela do gestor
     * @author Rodrigo Pereira
     */

    public void createManagerWindow(String[] aMode) {
        if (cardPanel == null) {
            cardPanel = new JPanel(new CardLayout());
            window.add(cardPanel);
        }
    
        CardLayout cl = (CardLayout)(cardPanel.getLayout());

        if (mainPanel == null) {
            JPanel mainPanel = new JPanel(new BorderLayout(5, 5));

            JMenuBar managerMenuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("File");
            fileMenu.setToolTipText("General options");
            fileMenu.setMnemonic(KeyEvent.VK_F);

            exitItem = new JMenuItem("Exit");
            exitItem.setToolTipText("Log out of your account");
            exitItem.setMnemonic(KeyEvent.VK_X);
            exitItem.addActionListener(this);

            fileMenu.add(exitItem);

            JMenu addMenu = new JMenu("Add");
            addMenu.setToolTipText("Add information to the system");
            addMenu.setMnemonic(KeyEvent.VK_A);

            addUserItem = new JMenuItem("User...");
            addUserItem.setToolTipText("Add user to the system");
            addUserItem.setMnemonic(KeyEvent.VK_U);
            addUserItem.addActionListener(this);
            addLicenceItem = new JMenuItem("Licence...");
            addLicenceItem.setToolTipText("Add licence to the system");
            addLicenceItem.setMnemonic(KeyEvent.VK_L);
            addLicenceItem.addActionListener(this);

            addMenu.add(addUserItem);
            addMenu.add(addLicenceItem);

            managerMenuBar.add(fileMenu);
            managerMenuBar.add(addMenu);

            window.setJMenuBar(managerMenuBar);

            JLabel welcometext = new JLabel("Welcome " + aMode[1]);
            welcometext.setFont(new Font(null, Font.PLAIN, 24));
            welcomePanel = new JPanel(new FlowLayout());
            welcomePanel.add(welcometext);

            checkNotifications = new JButton("Notifications");
            checkNotifications.setToolTipText("Click to see your notifications");
            checkNotifications.addActionListener(this);

            approveUser = new JButton("Approve User");
            approveUser.setToolTipText("Click to approve an user");
            approveUser.addActionListener(this);

            reproveUser = new JButton("Reprove User");
            reproveUser.setToolTipText("Click to reprove an user");
            reproveUser.addActionListener(this);

            inactivateUser = new JButton("Inactivate User");
            inactivateUser.setToolTipText("Click to inactivate an user");
            inactivateUser.addActionListener(this);

            selectReviewer = new JButton("Select head reviewer");
            selectReviewer.setToolTipText("Click to send a head reviewer proposal");
            selectReviewer.addActionListener(this);

            approveReview = new JButton("Approve Review");
            approveReview.setToolTipText("Click to approve review");
            approveReview.addActionListener(this);

            reproveReview = new JButton("Reprove Review");
            reproveReview.setToolTipText("Click to reprove review");
            reproveReview.addActionListener(this);

            checkReview = new JButton("Check Review");
            checkReview.setToolTipText("Click to check review's state");
            checkReview.addActionListener(this);

            JPanel auxPanel = new JPanel(new FlowLayout());
            auxPanel.add(approveUser);
            auxPanel.add(reproveUser);
            auxPanel.add(inactivateUser);
            auxPanel.add(selectReviewer);
            auxPanel.add(checkNotifications);
            auxPanel.add(approveReview);
            auxPanel.add(reproveReview);
            auxPanel.add(checkReview);

            mainPanel.add(welcomePanel, BorderLayout.NORTH);
            mainPanel.add(auxPanel, BorderLayout.CENTER);
            cardPanel.add(mainPanel, "Welcome");
        }
    
        cl.show(cardPanel, "Welcome");
    }

    /**
     * Método para criar o painel das revisões
     * @author Rodrigo Pereira
     */

    public void selectReviewPanel(CardLayout aCl) {
        String col[] = {"Title","Author","Serial Number","State"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        newManager.reviewsList(tableModel, "Approved");

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(getBackground(), 10, false));
            
        selectReviewPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Select review");
        title.setFont(new Font(null, Font.PLAIN, 24));

        reviewTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(reviewTable);
        reviewTable.setAutoCreateRowSorter(true);
        reviewTable.setFillsViewportHeight(true);

        selectReviewPanel.add(scrollPane);

        JPanel selectReviewAuxPanel = new JPanel(new FlowLayout());

        mainBack = new JButton("< Back");
        mainBack.setToolTipText("Button to return to Manager Menu");
        mainBack.addActionListener(this);
        
        proceedButton = new JButton("Continue >");
        proceedButton.setToolTipText("Button to select review and proceed to next screen");
        proceedButton.addActionListener(this);
        
        selectReviewAuxPanel.add(mainBack);
        selectReviewAuxPanel.add(proceedButton);
        
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(selectReviewPanel);
        mainPanel.add(selectReviewAuxPanel, BorderLayout.SOUTH);
        cardPanel.add(mainPanel, "SelectReview");

        aCl.show(cardPanel, "SelectReview");
    }

    /**
     * Método para criar o painel dos revisores
     * @author Rodrigo Pereira
     */

    public void selectReviewerPanel(CardLayout aCl) {
        String col[] = {"Name","Login","Email","Telephone"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        newManager.reviewersList(tableModel, sn);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(getBackground(), 10, false));

        selectReviewerPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Select reviewer");
        title.setFont(new Font(null, Font.PLAIN, 24));

        reviewerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(reviewerTable);
        reviewerTable.setAutoCreateRowSorter(true);
        reviewerTable.setFillsViewportHeight(true);

        selectReviewerPanel.add(scrollPane);

        JPanel selectReviewerAuxPanel = new JPanel(new FlowLayout());

        returnBack = new JButton("< Back");
        returnBack.setToolTipText("Button to return to previous screen");
        returnBack.addActionListener(this);
        
        finishButton = new JButton("Select");
        finishButton.setToolTipText("Button to select reviewer and finish action");
        finishButton.addActionListener(this);
        
        selectReviewerAuxPanel.add(returnBack);
        selectReviewerAuxPanel.add(finishButton);
        
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(selectReviewerPanel);
        mainPanel.add(selectReviewerAuxPanel, BorderLayout.SOUTH);
        cardPanel.add(mainPanel, "SelectReviewer");

        aCl.show(cardPanel, "SelectReviewer");
    }

    /**
     * Método para criar o diálogo de adicionar utilizadores
     * @author Rodrigo Pereira
     */

    public void addUserDialog() {
        ButtonGroup type = new ButtonGroup();
        JRadioButton manager = new JRadioButton("Manager", true);
        manager.setToolTipText("Set account type as Manager");
        JRadioButton author = new JRadioButton("Author", false);
        author.setToolTipText("Set account type as Author");
        JRadioButton reviewer = new JRadioButton("Reviewer", false);
        reviewer.setToolTipText("Set account type as Reviewer");

        type.add(manager);
        type.add(author);
        type.add(reviewer);

        boolean validInput = false;

        while (!validInput) {
            
            JLabel usernameLabel = new JLabel("Username:");
            JTextField username = new JTextField();
            username.setToolTipText("Field to insert user name (login)");
            JLabel nameLabel = new JLabel("Name:");
            JTextField name = new JTextField();
            name.setToolTipText("Field to insert name");
            JLabel passwordLabel = new JLabel("Password:");
            JTextField password = new JPasswordField();
            password.setToolTipText("Field to insert account password");
            JLabel emailLabel = new JLabel("Email:");
            JTextField email = new JTextField();
            email.setToolTipText("Field to insert account email");
            JComponent[] inputs = new JComponent[] 
            {manager, author, reviewer, usernameLabel, username, nameLabel, name, passwordLabel, password, emailLabel, email};
            int value1 = JOptionPane.showConfirmDialog(
            null,
            inputs,
            "Basic information",
            JOptionPane.OK_CANCEL_OPTION);

            if (value1 == JOptionPane.OK_OPTION) {
                Register newRegister = new Register();
                Validation newValidation = new Validation();
                if (!newValidation.validateLogin(username.getText())) {
                    JOptionPane.showMessageDialog(window, "Username already used by another user. Please try a different one.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!newValidation.validateEmail(email.getText())) {
                    JOptionPane.showMessageDialog(window, "Invalid email address. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (manager.isSelected()) {
                        User newUser = new User("Manager");

                        newUser.setName(name.getText());
                        newUser.setLogin(username.getText());
                        newUser.setPassword(password.getText());
                        newUser.setEmail(email.getText());

                        newRegister.createManager("Manager", newUser);
                        validInput = true;
                        JOptionPane.showMessageDialog(window, "User added successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    } else if (author.isSelected()) {
                        User newUser = new User("Author");
                        Client newClient = new Client("Author");
                        Author newAuthor = new Author("Author");
                    
                        newUser.setName(name.getText());
                        newUser.setLogin(username.getText());
                        newUser.setPassword(password.getText());
                        newUser.setEmail(email.getText());

                        boolean authorValidInput = false;

                        while (!authorValidInput) {

                            JLabel addressLabel = new JLabel("Address:");
                            JTextField address = new JTextField();
                            address.setToolTipText("Field to insert author's address");
                            JLabel nifLabel = new JLabel("NIF:");
                            JTextField nif = new JTextField(9);
                            nif.setToolTipText("Field to insert author's NIF");
                            JLabel telephoneLabel = new JLabel("Telephone:");
                            JTextField telephone = new JTextField(9);
                            telephone.setToolTipText("Field to insert author's telephone number");

                            JLabel styleLabel = new JLabel("Style:");
                            JTextField style = new JTextField();
                            style.setToolTipText("Field to insert author's writing style");

                            String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

                            JLabel dayLabel = new JLabel("Day:");
                            JTextField day = new JTextField();
                            day.setToolTipText("Field to insert author's day of activity");
                            SpinnerListModel monthModel = new SpinnerListModel( months );
                            JLabel spinnerLabel = new JLabel("Month:");
                            JSpinner spinner = new JSpinner(monthModel);
                            spinner.setToolTipText("Choose author's month of activity");
                            JLabel yearLabel = new JLabel("Year:");
                            JTextField year = new JTextField();
                            year.setToolTipText("Field to insert author's year of activity");
                        
                            JComponent[] inputs2 = new JComponent[] 
                            {addressLabel, address, nifLabel, nif, telephoneLabel, telephone, styleLabel, style, dayLabel, day, spinnerLabel, spinner, yearLabel, year};
                            int value2 = JOptionPane.showConfirmDialog(
                            null,
                            inputs2,
                            "Author's information",
                            JOptionPane.OK_CANCEL_OPTION);

                            if (value2 == JOptionPane.OK_OPTION) {

                                if (!newValidation.validateNif(Integer.parseInt(nif.getText()))) {
                                    JOptionPane.showMessageDialog(window, "Invalid NIF. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
                                } else if (!newValidation.validateTelephone(Integer.parseInt(telephone.getText()))) {
                                    JOptionPane.showMessageDialog(window, "Invalid telephone number. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    int i = 1;
                                    for (String month : months) {
                                        if (month.equals(spinner.getValue()))
                                            break;
                                        i++;
                                    }
                                    LocalDate date = newValidation.requestDate(Integer.parseInt(year.getText()), i, Integer.parseInt(day.getText()));      
                                    
                                    if (date != null) {
                                        newClient.setAddress(address.getText());
                                        newClient.setNif(Integer.parseInt(nif.getText()));
                                        newClient.setTelephoneNumber(Integer.parseInt(telephone.getText()));
                                        newAuthor.setInitialDate(date);
                                        newAuthor.setLiteraryStyle(style.getText());

                                        newRegister.registerAuthor("Author", newUser, newClient, newAuthor);
                                        authorValidInput = true;
                                        validInput = true;
                                        JOptionPane.showMessageDialog(window, "User added successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(window, "Invalid date. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                
                            } else {
                                JOptionPane.showMessageDialog(window, "Error!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                    } else if (reviewer.isSelected()) {
                        User newUser = new User("Reviewer");
                        Client newClient = new Client("Author");
                        Reviewer newReviewer = new Reviewer("Reviewer");
                    
                        newUser.setName(name.getText());
                        newUser.setLogin(username.getText());
                        newUser.setPassword(password.getText());
                        newUser.setEmail(email.getText());

                        boolean reviewerValidInput = false;

                        while (!reviewerValidInput) {

                            JLabel addressLabel = new JLabel("Address:");
                            JTextField address = new JTextField();
                            address.setToolTipText("Field to insert reviewer's address");
                            JLabel nifLabel = new JLabel("NIF:");
                            JTextField nif = new JTextField(9);
                            nif.setToolTipText("Field to insert reviewer's NIF");
                            JLabel telephoneLabel = new JLabel("Telephone:");
                            JTextField telephone = new JTextField(9);
                            nif.setToolTipText("Field to insert reviewer's telephone number");

                            JLabel specializationLabel = new JLabel("Specialization:");
                            JTextField specialization = new JTextField();
                            specialization.setToolTipText("Field to insert reviewer's specialization");
                            JLabel academicsLabel = new JLabel("Academics:");
                            JTextField academics = new JTextField();
                            academics.setToolTipText("Field to insert reviewer's academics");
                        
                            JComponent[] inputs2 = new JComponent[] 
                            {addressLabel, address, nifLabel, nif, telephoneLabel, telephone, specializationLabel, specialization, academicsLabel, academics};
                            int value2 = JOptionPane.showConfirmDialog(
                            null,
                            inputs2,
                            "Reviewer's information",
                            JOptionPane.DEFAULT_OPTION);

                            if (value2 == JOptionPane.OK_OPTION) {
                                if (address.getText().isEmpty() || nif.getText().isEmpty() || telephone.getText().isEmpty() || specialization.getText().isEmpty() || academics.getText().isEmpty()) {
                                    JOptionPane.showMessageDialog(window, "One or more text fields are empty. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
                                } else if (!newValidation.validateNif(Integer.parseInt(nif.getText()))) {
                                    JOptionPane.showMessageDialog(window, "Invalid NIF. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
                                } else if (!newValidation.validateTelephone(Integer.parseInt(telephone.getText()))) {
                                    JOptionPane.showMessageDialog(window, "Invalid telephone number. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
                                } else {

                                    newClient.setAddress(address.getText());
                                    newClient.setNif(Integer.parseInt(nif.getText()));
                                    newClient.setTelephoneNumber(Integer.parseInt(telephone.getText()));
                                    newReviewer.setAreaOfEspecialization(specialization.getText());
                                    newReviewer.setAcademics(academics.getText());

                                    newRegister.registerReviewer("Reviewer", newUser, newClient, newReviewer);
                                    reviewerValidInput = true;
                                    validInput = true;
                                    JOptionPane.showMessageDialog(window, "User added successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        }
                    }
                }
            } else {
                //caso o utilizador cancele a operação
                validInput = true;
            }
        }
    }

    /**
     * Método para criar o painel de aprovar utilizadores
     * @author Rodrigo Pereira
     */

    public void approveUserPanel(CardLayout aCl) {
        String col[] = {"Name","Login","Email","Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        newManager.requestList(tableModel);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(getBackground(), 10, false));

        if (approveUserPanel == null) {
            approveUserPanel = new JPanel(new BorderLayout());

            JLabel title = new JLabel("Approve User");
            title.setFont(new Font(null, Font.PLAIN, 24));

            userTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(userTable);
            userTable.setAutoCreateRowSorter(true);
            userTable.setFillsViewportHeight(true);
    
            approveUserPanel.add(scrollPane);
    
            JPanel approveUserAuxPanel = new JPanel(new FlowLayout());
    
            mainBack = new JButton("< Back");
            mainBack.setToolTipText("Button to return to Manager Menu");
            mainBack.addActionListener(this);
            
            approveButton = new JButton("Approve");
            approveButton.setToolTipText("Button to select and approve user");
            approveButton.addActionListener(this);
            
            approveUserAuxPanel.add(mainBack);
            approveUserAuxPanel.add(approveButton);
    
            mainPanel.add(title, BorderLayout.NORTH);
            mainPanel.add(approveUserPanel);
            mainPanel.add(approveUserAuxPanel, BorderLayout.SOUTH);
            cardPanel.add(mainPanel, "ApproveUser");
        }
        
        aCl.show(cardPanel, "ApproveUser");
    }

    /** 
     * Método para a lógica do botão de aprovar utilizadores
     * @author Rodrigo Pereira
     */

    public void approveButton() {
        if (userTable.getValueAt(userTable.getSelectedRow(), 1) == null) {
            JOptionPane.showMessageDialog(window, "Error retrieving username. \nHave you tried selecting a row from the table?", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (newManager.approveRequest((String)userTable.getValueAt(userTable.getSelectedRow(), 1))) {
                JOptionPane.showMessageDialog(window, "User approved successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(window, "An error occurred while approving the user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } 
    }

    /**
     * Método para criar o painel de reprovar utilizadores
     * @author Rodrigo Pereira
     */

    public void reproveUserPanel(CardLayout aCl) {
        String col[] = {"Name","Login","Email","Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        newManager.requestList(tableModel);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(getBackground(), 10, false));

        if (reproveUserPanel == null) {
            reproveUserPanel = new JPanel(new BorderLayout());

            JLabel title = new JLabel("Reprove User");
            title.setFont(new Font(null, Font.PLAIN, 24));

            userTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(userTable);
            userTable.setAutoCreateRowSorter(true);
            userTable.setFillsViewportHeight(true);
    
            reproveUserPanel.add(scrollPane);
    
            JPanel reproveUserAuxPanel = new JPanel(new FlowLayout());
    
            mainBack = new JButton("< Back");
            mainBack.setToolTipText("Button to return to Manager Menu");
            mainBack.addActionListener(this);
            
            reproveButton = new JButton("Reprove");
            reproveButton.setToolTipText("Button to select and reprove user");
            reproveButton.addActionListener(this);
            
            reproveUserAuxPanel.add(mainBack);
            reproveUserAuxPanel.add(reproveButton);
            
            mainPanel.add(title, BorderLayout.NORTH);
            mainPanel.add(reproveUserPanel);
            mainPanel.add(reproveUserAuxPanel, BorderLayout.SOUTH);
            cardPanel.add(mainPanel, "ReproveUser");
        }
        
        aCl.show(cardPanel, "ReproveUser");
    }

    /**
     * Método para a lógica do botão de reprovar utilizadores
     * @author Rodrigo Pereira
     */

    public void reproveButton() {
        if (userTable.getValueAt(userTable.getSelectedRow(), 1) == null) {
            JOptionPane.showMessageDialog(window, "Error retrieving username. \nHave you tried selecting a row from the table?", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (newManager.reproveRequest((String)userTable.getValueAt(userTable.getSelectedRow(), 1))) {
                JOptionPane.showMessageDialog(window, "User reproved successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(window, "An error occurred while reproving the user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Método para criar o painel de inativar utilizadores
     * @author Rodrigo Pereira
     */

    public void inactivateUserPanel(CardLayout aCl) {
        String col[] = {"Name","Login","Email","Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        newManager.activeUserList(tableModel);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(getBackground(), 10, false));

        if (inactivateUserPanel == null) {
            inactivateUserPanel = new JPanel(new BorderLayout());

            JLabel title = new JLabel("Inactivate User");
            title.setFont(new Font(null, Font.PLAIN, 24));

            userTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(userTable);
            userTable.setAutoCreateRowSorter(true);
            userTable.setFillsViewportHeight(true);
    
            inactivateUserPanel.add(scrollPane);
    
            JPanel inactivateUserAuxPanel = new JPanel(new FlowLayout());
    
            mainBack = new JButton("< Back");
            mainBack.setToolTipText("Button to return to Manager Menu");
            mainBack.addActionListener(this);
            
            inactivateButton = new JButton("Inactivate");
            inactivateButton.setToolTipText("Button to select and inactivate user");
            inactivateButton.addActionListener(this);
            
            inactivateUserAuxPanel.add(mainBack);
            inactivateUserAuxPanel.add(inactivateButton);
            
            mainPanel.add(title, BorderLayout.NORTH);
            mainPanel.add(inactivateUserPanel);
            mainPanel.add(inactivateUserAuxPanel, BorderLayout.SOUTH);
            cardPanel.add(mainPanel, "InactivateUser");
        }
        
        aCl.show(cardPanel, "InactivateUser");
    }

    /**
     * Método para a lógica do botão de inativar utilizadores
     * @author Rodrigo Pereira
     */

    public void inactivateButton() {
        if (userTable.getValueAt(userTable.getSelectedRow(), 1) == null) {
            JOptionPane.showMessageDialog(window, "Error retrieving username. \nHave you tried selecting a row from the table?", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (newManager.inactivateAccount((String)userTable.getValueAt(userTable.getSelectedRow(), 1))) {
                JOptionPane.showMessageDialog(window, "User inactivated successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(window, "An error occurred while inactivating the user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Método para criar o painel de aprovar revisões
     * @author Rodrigo Pereira
     */

    public void approveReviewPanel(CardLayout aCl) {
        String col[] = {"Title","Author","Serial Number","State"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        newManager.reviewsList(tableModel, "Started");

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(getBackground(), 10, false));

        approveReviewPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Approve Review");
        title.setFont(new Font(null, Font.PLAIN, 24));

        reviewTable = new JTable(tableModel);

        DefaultTableCellRenderer stateColumnRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String state = (String) value;
                if (state != null) {
                    switch (state) {
                        case "Approved":
                            cell.setBackground(new Color(126, 249, 142));
                            break;
                        case "Reproved":
                            cell.setBackground(new Color(255, 132, 132));
                            break;
                        case "Started":
                            cell.setBackground(new Color(192, 192, 192));    
                            break;
                        case "Ongoing":
                            cell.setBackground(new Color(253, 253, 131));
                            break;
                        case "Finished":
                            cell.setBackground(new Color(131, 241, 253));
                            break;
                        default:
                            cell.setBackground(Color.YELLOW);
                            break;
                    }
                }
                return cell;
            }
        };
        reviewTable.getColumnModel().getColumn(3).setCellRenderer(stateColumnRenderer);

        JScrollPane scrollPane = new JScrollPane(reviewTable);
        reviewTable.setAutoCreateRowSorter(true);
        reviewTable.setFillsViewportHeight(true);

        approveReviewPanel.add(scrollPane);

        JPanel approveReviewAuxPanel = new JPanel(new FlowLayout());

        mainBack = new JButton("< Back");
        mainBack.setToolTipText("Button to return to Manager Menu");
        mainBack.addActionListener(this);
        
        approveRButton = new JButton("Approve");
        approveRButton.setToolTipText("Button to select and approve review");
        approveRButton.addActionListener(this);
        
        approveReviewAuxPanel.add(mainBack);
        approveReviewAuxPanel.add(approveRButton);
        
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(approveReviewPanel);
        mainPanel.add(approveReviewAuxPanel, BorderLayout.SOUTH);
        cardPanel.add(mainPanel, "ApproveReview");
        
        aCl.show(cardPanel, "ApproveReview");
    }

    /**
     * Método para a lógica do botão de aprovar revisões
     * @author Rodrigo Pereira
     */

    public void approveRButton(CardLayout aCl) {
        bookName = (String)reviewTable.getValueAt(reviewTable.getSelectedRow(), 0);
        if (bookName == null)
            JOptionPane.showMessageDialog(window, "Error retrieving book name. \nHave you tried selecting a row from the table?", "Error", JOptionPane.ERROR_MESSAGE);
        else {
            if (newManager.approveReview(newManager, bookName)) {
                JOptionPane.showMessageDialog(window, "Review approved successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
                aCl.show(cardPanel, "Welcome");
            } else {
                JOptionPane.showMessageDialog(window, "An error occurred while approving the specified review.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /** 
     * Método para criar o painel de reprovar revisões
     * @author Rodrigo Pereira
     */

    public void reproveReviewPanel(CardLayout aCl) {
        String col[] = {"Title","Author","Serial Number","State"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        newManager.reviewsList(tableModel, "Started");

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(getBackground(), 10, false));

        reproveReviewPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Reprove Review");
        title.setFont(new Font(null, Font.PLAIN, 24));

        reviewTable = new JTable(tableModel);

        DefaultTableCellRenderer stateColumnRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String state = (String) value;
                if (state != null) {
                    switch (state) {
                        case "Approved":
                            cell.setBackground(new Color(126, 249, 142));
                            break;
                        case "Reproved":
                            cell.setBackground(new Color(255, 132, 132));
                            break;
                        case "Started":
                            cell.setBackground(new Color(192, 192, 192));    
                            break;
                        case "Ongoing":
                            cell.setBackground(new Color(253, 253, 131));
                            break;
                        case "Finished":
                            cell.setBackground(new Color(131, 241, 253));
                            break;
                        default:
                            cell.setBackground(Color.YELLOW);
                            break;
                    }
                }
                return cell;
            }
        };
        reviewTable.getColumnModel().getColumn(3).setCellRenderer(stateColumnRenderer);

        JScrollPane scrollPane = new JScrollPane(reviewTable);
        reviewTable.setAutoCreateRowSorter(true);
        reviewTable.setFillsViewportHeight(true);

        reproveReviewPanel.add(scrollPane);

        JPanel reproveReviewAuxPanel = new JPanel(new FlowLayout());

        mainBack = new JButton("< Back");
        mainBack.setToolTipText("Button to return to Manager Menu");
        mainBack.addActionListener(this);
        
        reproveRButton = new JButton("Reprove");
        reproveRButton.setToolTipText("Button to select and reprove review");
        reproveRButton.addActionListener(this);
        
        reproveReviewAuxPanel.add(mainBack);
        reproveReviewAuxPanel.add(reproveRButton);
        
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(reproveReviewPanel);
        mainPanel.add(reproveReviewAuxPanel, BorderLayout.SOUTH);
        cardPanel.add(mainPanel, "ReproveReview");
        
        aCl.show(cardPanel, "ReproveReview");
    }

    /**
     * Método para a lógica do botão de reprovar revisões
     * @author Rodrigo Pereira
     */

    public void reproveRButton(CardLayout aCl) {
        bookName = (String)reviewTable.getValueAt(reviewTable.getSelectedRow(), 0);
        if (bookName == null)
            JOptionPane.showMessageDialog(window, "Error retrieving book name. \nHave you tried selecting a row from the table?", "Error", JOptionPane.ERROR_MESSAGE);
        else {
            if (newManager.reproveReview(newManager, bookName)) {
                JOptionPane.showMessageDialog(window, "Review reproved successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
                aCl.show(cardPanel, "Welcome");
            } else {
                JOptionPane.showMessageDialog(window, "An error occurred while reproving the specified review.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Método para criar o painel de verificar revisões
     * @author Rodrigo Pereira
     */

    public void checkReviewPanel(CardLayout aCl) {
        String col[] = {"Title","Author","Serial Number","State"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        newManager.allReviewsList(tableModel);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(getBackground(), 10, false));

        checkReviewPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Check Review");
        title.setFont(new Font(null, Font.PLAIN, 24));

        reviewTable = new JTable(tableModel);

        DefaultTableCellRenderer stateColumnRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String state = (String) value;
                if (state != null) {
                    switch (state) {
                        case "Approved":
                            cell.setBackground(new Color(126, 249, 142));
                            break;
                        case "Reproved":
                            cell.setBackground(new Color(255, 132, 132));
                            break;
                        case "Started":
                            cell.setBackground(new Color(192, 192, 192));    
                            break;
                        case "Ongoing":
                            cell.setBackground(new Color(253, 253, 131));
                            break;
                        case "Finished":
                            cell.setBackground(new Color(131, 241, 253));
                            break;
                        default:
                            cell.setBackground(Color.YELLOW);
                            break;
                    }
                }
                return cell;
            }
        };

        reviewTable.getColumnModel().getColumn(3).setCellRenderer(stateColumnRenderer);

        JScrollPane scrollPane = new JScrollPane(reviewTable);
        reviewTable.setAutoCreateRowSorter(true);
        reviewTable.setFillsViewportHeight(true);

        checkReviewPanel.add(scrollPane);

        JPanel checkReviewAuxPanel = new JPanel(new FlowLayout());

        mainBack = new JButton("< Back");
        mainBack.setToolTipText("Button to return to Manager Menu");
        mainBack.addActionListener(this);
        
        checkReviewAuxPanel.add(mainBack);
        
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(checkReviewPanel);
        mainPanel.add(checkReviewAuxPanel, BorderLayout.SOUTH);
        cardPanel.add(mainPanel, "CheckReview");
            
        aCl.show(cardPanel, "CheckReview");
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
            selectReviewerPanel(aCl);
    }

    /**
     * Método para a lógica do botão de finalizar
     * @author Rodrigo Pereira
     */

    public void finishButton(CardLayout aCl) {
        if (newManager.notifyHeadReviewer(sn, (String)reviewerTable.getValueAt(reviewerTable.getSelectedRow(), 1))) {
            JOptionPane.showMessageDialog(window, "Reviewer notified successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
            aCl.show(cardPanel, "Welcome");
        } else {
            JOptionPane.showMessageDialog(window, "An error occurred while notifying the specified reviewer.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para a criação do diálogo de adicionar licenças
     * @author Rodrigo Pereira
     */

    public void addLicenceDialog() {
        JLabel nameLabel = new JLabel("Name:");
        JTextField name = new JTextField();
        name.setToolTipText("Field to insert licence's name");
        JLabel descriptionLabel = new JLabel("Description:");
        JTextField description = new JTextField();
        name.setToolTipText("Field to insert licence's description");
        JComponent[] inputs = new JComponent[] 
        {nameLabel, name, descriptionLabel, description};
        int value = JOptionPane.showConfirmDialog(
        null,
        inputs,
        "Licence's information",
        JOptionPane.OK_CANCEL_OPTION);

        if (value == JOptionPane.OK_OPTION) {
            Licence newLicence = new Licence();
            newLicence.setName(name.getText());
            newLicence.setDescription(description.getText());
            if (newManager.createLicence(newLicence)) {
                JOptionPane.showMessageDialog(window, "Licence created successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(window, "An error occurred while creating the new licence.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Método para verificar notificações
     * @author Rodrigo Pereira
     */

    public void checkNotifications(CardLayout aCl) {

        notificationPanel = new JPanel(new BorderLayout());
        cnauxPanel = new JPanel();
        cntablePanel = new JPanel(new BorderLayout());

        notificationPanel.setBorder(BorderFactory.createLineBorder(getBackground(), 10, false));
    
        JLabel windowtitle = new JLabel("Notifications");
        windowtitle.setFont(new Font(null, Font.PLAIN, 24));

        String col[] = {"Id","Title","Type","Viewed",""};

        DefaultTableModel tableModel = new DefaultTableModel(col, 0);

        ArrayList<Notification> notificationList = newManager.checkNotifications(newManager);

        for (int i = 0; i < notificationList.size(); i++){
            int id = notificationList.get(i).getId();
            String title = notificationList.get(i).getTitle();
            String type  = notificationList.get(i).getType();
            boolean viewed = notificationList.get(i).getViewed();
            
            Object[] data = {id, title, type, viewed, "Details"};
        
            tableModel.addRow(data);
        }
        
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane( table );
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);

        cntablePanel.add(scrollPane);

        /*cria-se uma ação abstrata onde se cria um array de objetos 
        (com os valores da tabela) e onde em seguida se chama a janela de detalhes
        de notificações*/
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
                                    
                NotificationDetailsWindow window = new NotificationDetailsWindow(objectArray, newManager);
                window.show();
            }
        };

        /*cria-se uma coluna com botão, associada à ação feita anteriormente, contendo a tabela, a ação,
        a coluna onde estará o botão e o tooltip do mesmo*/
        new ButtonColumn(table, detailswindow, 4, "Show the notification's details");
    
        mainBack = new JButton("< Back");
        mainBack.setToolTipText("Button to return to Manager Menu");
        mainBack.addActionListener(this);

        cnauxPanel.add(mainBack);

        notificationPanel.add(windowtitle, BorderLayout.NORTH);
        notificationPanel.add(cntablePanel);
        notificationPanel.add(cnauxPanel, BorderLayout.SOUTH);

        cardPanel.add(notificationPanel, "Notifications");
        
        
        aCl.show(cardPanel, "Notifications");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout)(cardPanel.getLayout());

        if (e.getSource().equals(exitItem)) {
            windowClosing(null);
        }

        //manager 
        // | gestão de utilizadores
        if (e.getSource().equals(addUserItem)) {
            addUserDialog();
        } else if (e.getSource().equals(approveUser)) {
            approveUserPanel(cl);
        } else if (e.getSource().equals(approveButton)) {
            approveButton();
        } else if (e.getSource().equals(reproveUser)) {
            reproveUserPanel(cl);
        } else if (e.getSource().equals(reproveButton)) {
            reproveButton();
        } else if (e.getSource().equals(inactivateUser)) {
            inactivateUserPanel(cl);
        } else if (e.getSource().equals(inactivateButton)) {
            inactivateButton();
        } 

        // | gestão de revisões
        if (e.getSource().equals(approveReview)) {
            approveReviewPanel(cl);
        } else if (e.getSource().equals(approveRButton)) {
            approveRButton(cl);
        } else if (e.getSource().equals(reproveReview)) {
            reproveReviewPanel(cl);
        } else if (e.getSource().equals(reproveRButton)) {
            reproveRButton(cl);
        } else if (e.getSource().equals(checkReview)) {
            checkReviewPanel(cl);
        }

        // | selecionar head reviewer
        if (e.getSource().equals(selectReviewer)) {
            selectReviewPanel(cl);
        } else if (e.getSource().equals(proceedButton)) {
            proceedButton(cl);
        } else if (e.getSource().equals(returnBack)) {
            selectReviewPanel(cl);
        } else if (e.getSource().equals(finishButton)) {
            finishButton(cl);
        }

        // | adicionar licença
        if (e.getSource().equals(addLicenceItem)) {
            addLicenceDialog();
        }

        // | geral
        if (e.getSource().equals(checkNotifications)) {
            checkNotifications(cl);
        } else if (e.getSource().equals(mainBack)) {
            cl.show(cardPanel, "Welcome");
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        LoginWindow newLoginWindow = new LoginWindow();
        newLoginWindow.show();
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
            MainManagerWindow newMainManagerWindow = new MainManagerWindow(mode);
            newMainManagerWindow.show(); 
        }

    }

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}

}