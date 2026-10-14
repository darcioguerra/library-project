import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.*;

/**
 * Classe onde esta o código relacionado com a GUI de login
 * @author Rodrigo Pereira
 */
public class LoginWindow extends JFrame implements ActionListener {
    private JFrame window;
    private JTextField username;
    private JPasswordField password;
    private JButton loginbutton;
    private JButton registerbutton;

    private JPanel externalPanel, mainPanel;
    private JPanel registerPanel, loginPanel;

    public LoginWindow() {
        window = new JFrame();

        window.setTitle("Authentication");
        window.setSize(500, 400); 
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        externalPanel = new JPanel(new GridLayout(3, 1));
        mainPanel = new JPanel(new GridLayout(3, 1));
        registerPanel = new JPanel(new FlowLayout());
        loginPanel = new JPanel(new FlowLayout());

        JLabel welcome = new JLabel("Welcome", (int)JLabel.CENTER_ALIGNMENT);
        welcome.setFont(new Font(null, Font.PLAIN, 24));
        externalPanel.add(welcome);

        JPanel userpanel = new JPanel(new FlowLayout());
        JLabel usernamelabel = new JLabel("Username:");
        userpanel.add(usernamelabel);
        username = new JTextField(null, null, 10);
        username.setToolTipText("Field to insert user name (login)");
        userpanel.add(username);
        mainPanel.add(userpanel);

        JPanel passwordpanel = new JPanel(new FlowLayout());
        JLabel passwordlabel = new JLabel("Password:");
        passwordpanel.add(passwordlabel);
        password = new JPasswordField(null, null, 10);
        password.setToolTipText("Field to insert account password");
        passwordpanel.add(password);
        mainPanel.add(passwordpanel);

        externalPanel.add(mainPanel);
        
        loginbutton = new JButton("Login");
        loginbutton.setToolTipText("Button to log in");
        loginPanel.add(loginbutton);
        mainPanel.add(loginPanel);

        JLabel noaccountlabel = new JLabel("No account?");
        registerPanel.add(noaccountlabel);
        registerbutton = new JButton("Register");
        registerbutton.setToolTipText("Button to register new account");
        registerPanel.add(registerbutton);

        externalPanel.add(registerPanel);
        
        loginbutton.addActionListener(this);
        registerbutton.addActionListener(this);

        window.add(externalPanel);
    }

    /**
     * Método para mostrar janela
     * @author Rodrigo Pereira
     */

    public void show() {
        window.setVisible(true);
    }

    /**
     * Método para fechar janela
     * @author Rodrigo Pereira
     */

    public void close() {
        window.setVisible(false);
        window.dispose();
    }

    /**
     * Método para registar utilizadores
     * @author Dárcio Guerra e Rodrigo Pereira
     */

    public void register() {
        ButtonGroup type = new ButtonGroup();
        JRadioButton author = new JRadioButton("Author", false);
        author.setToolTipText("Set account type as Author");
        JRadioButton reviewer = new JRadioButton("Reviewer", false);
        reviewer.setToolTipText("Set account type as Reviewer");

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
            {author, reviewer, usernameLabel, username, nameLabel, name, passwordLabel, password, emailLabel, email};
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
                    if (author.isSelected()) {
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
                            telephone.setToolTipText("Field to insert reviewer's telephone number");
                            
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(loginbutton)) {
            String user = username.getText();
            String pass = new String(password.getPassword());

            if (!user.isEmpty() && !pass.isEmpty()) {
                Login newLogin = new Login();

                String[] data = newLogin.getInfoNew(user, pass);

                System.out.println(data[0]);

                if (data[0].isEmpty()) {
                    JOptionPane.showMessageDialog(window, "Invalid login or password!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (data[0].equals("Unauthorized")) {
                    JOptionPane.showMessageDialog(window, "Your account was inactived.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (data[0].equals("Forbidden")) {
                    JOptionPane.showMessageDialog(window, "Your account was reproved.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (data[0].equals("Deleted")) {
                    JOptionPane.showMessageDialog(window, "This account no longer exists.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    window.setVisible(false);
                    window.dispose();

                    if (data[0].equals("Manager")) {
                        MainManagerWindow newMainWindow = new MainManagerWindow(data);
                        newMainWindow.show();
                    } else if (data[0].equals("Author")) {
                        MainAuthorWindow newMainWindow = new MainAuthorWindow(data);
                        newMainWindow.show();
                    } else if (data[0].equals("Reviewer")) {
                        MainReviewerWindow newMainWindow = new MainReviewerWindow(data);
                        newMainWindow.show();
                    }
                }

            } else {
                JOptionPane.showMessageDialog(null, "Missing information!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource().equals(registerbutton)) {
            register();
        }
    }
}
