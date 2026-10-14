import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;

/**
 * Classe onde esta o codigo relacionado com a GUI do autor
 * @author Dárcio Guerra
 */
public class MainAuthorWindow extends JFrame implements WindowListener, ActionListener {
    private JFrame window;
    private Author newAuthor = new Author("Author");
    private Searches newSearch = new Searches();
    
    //paineis e objetos genericos
    private JPanel cardPanel, mainPanel;
    private JPanel welcomePanel, notificationPanel, auxcnPanel, cnPanel;
    private JMenuItem exitItem;
    private JButton checkNotifications;
    private JButton mainBack;

    //Paneis e objetos do autor
    private JPanel payReviewPanel, auxPayReviewPanel, auxTablePayReviewPanel;
    private JPanel checkReviewStatePanel, auxcheckReviewStatePanel, auxTablecheckReviewStatePanel;
    private JButton payReview, payButton;
    private JButton checkReviewState;
    private JMenuItem addBookItem, startReviewItem, requestAccountRemovalItem;
    private JTable payTable, checkReviewTable;
    private String[] mode;
    /**
     * Metodo construtor da classe
     * @author Dárcio Guerra
     */
    public MainAuthorWindow(String[] aMode) {
        mode = aMode;
        window = new JFrame();

        window.setTitle("Author Menu");
        window.setSize(700, 500); 
        window.setLocationRelativeTo(null);

        initAuthor(aMode);
        createAuthorWindow(aMode);
        
        window.addWindowListener(this);
    }

    /**
     * Metodo para tornar a janela visivel
     * @author Dárcio Guerra
     */
    public void show() {
        window.setVisible(true);
    }

    /**
     * Metodo para inicializar um autor e ir buscar os seus dados a base de dados
     * @author Dárcio Guerra
     */
    public void initAuthor(String[] aData) {
        newAuthor = new Author(aData[0]);
        newAuthor = newSearch.searchAuthor(aData[1]);
    }

    /**
     * Metodo para a janela principal da interface do autor
     * @author Dárcio Guerra
     */
    public void createAuthorWindow(String[] aMode) {

        if (cardPanel == null) {
            cardPanel = new JPanel(new CardLayout());
            window.add(cardPanel);
        }

        CardLayout cardLayout = (CardLayout)(cardPanel.getLayout()) ;

        if (mainPanel == null) {

            mainPanel = new JPanel(new BorderLayout(5, 5));

            JMenuBar authorMenuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("File");
            fileMenu.setToolTipText("General options");
            fileMenu.setMnemonic(KeyEvent.VK_F);

            requestAccountRemovalItem = new JMenuItem("Request Account Removal");
            requestAccountRemovalItem.setToolTipText("Sent a request to a manager to remove your account");
            requestAccountRemovalItem.setMnemonic(KeyEvent.VK_A);
            requestAccountRemovalItem.addActionListener(this);

            exitItem = new JMenuItem("Exit");
            exitItem.setToolTipText("Log out of your account");
            exitItem.setMnemonic(KeyEvent.VK_X);
            exitItem.addActionListener(this);
            
            fileMenu.add(requestAccountRemovalItem);
            fileMenu.add(exitItem);

            JMenu addMenu = new JMenu("Add");
            addMenu.setToolTipText("Add information to the system");
            addMenu.setMnemonic(KeyEvent.VK_A);

            addBookItem = new JMenuItem("Book...");
            addBookItem.setToolTipText("Add a book to the system");
            addBookItem.addActionListener(this);
            addBookItem.setMnemonic(KeyEvent.VK_U);
            startReviewItem = new JMenuItem("Review...");
            startReviewItem.setToolTipText("Add review to the system");
            startReviewItem.addActionListener(this);
            startReviewItem.setMnemonic(KeyEvent.VK_U);

            addMenu.add(addBookItem);
            addMenu.add(startReviewItem);

            authorMenuBar.add(fileMenu);
            authorMenuBar.add(addMenu);

            window.setJMenuBar(authorMenuBar);

            JLabel welcomeText = new JLabel("Welcome " + aMode[1]);
            welcomeText.setFont(new Font(null, Font.PLAIN, 24));
            welcomePanel = new JPanel(new FlowLayout());
            welcomePanel.add(welcomeText);

            checkNotifications = new JButton("Notifications");
            checkNotifications.setToolTipText("Click to see your notifications");
            checkNotifications.addActionListener(this);

            payReview = new JButton("Pay Review");
            payReview.setToolTipText("Click to pay a review of your choice");
            payReview.addActionListener(this);

            checkReviewState = new JButton("Check review state");
            checkReviewState.setToolTipText("Click to verify the state of your reviews");
            checkReviewState.addActionListener(this);

            JPanel newPanel = new JPanel(new FlowLayout());
            newPanel.add(checkNotifications);
            newPanel.add(payReview);
            newPanel.add(checkReviewState);
            

            mainPanel.add(welcomePanel, BorderLayout.NORTH);
            mainPanel.add(newPanel, BorderLayout.CENTER);

            cardPanel.add(mainPanel, "Welcome");
        }

        cardLayout.show(cardPanel, "Welcome");
    }

    /**
     * Metodo para criar e gerir a interface das notificacoes do autor
     * @author Dárcio Guerra
     */
    public void checkNotifications(CardLayout cardLayout) {

        notificationPanel = new JPanel(new BorderLayout());
        auxcnPanel = new JPanel();
        cnPanel = new JPanel(new BorderLayout());

        notificationPanel.setBorder(BorderFactory.createLineBorder(getBackground(), 10, false));
    
        JLabel windowtitle = new JLabel("Notifications");
        windowtitle.setFont(new Font(null, Font.PLAIN, 24));

        String col[] = {"Id","Title","Type","Viewed",""};

        DefaultTableModel tableModel = new DefaultTableModel(col, 0);

        ArrayList<Notification> notificationList = newAuthor.checkNotifications(newAuthor);

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

        cnPanel.add(scrollPane);

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
                                    
                NotificationDetailsWindow window = new NotificationDetailsWindow(objectArray, newAuthor);

                window.show();
            }
        };

        new ButtonColumn(table, detailswindow, 4, "Show the notification's details");
        
    
        mainBack = new JButton("< Back");
        mainBack.setToolTipText("Button to return to Author Menu");
        mainBack.addActionListener(this);

        auxcnPanel.add(mainBack);

        notificationPanel.add(windowtitle, BorderLayout.NORTH);
        notificationPanel.add(cnPanel);
        notificationPanel.add(auxcnPanel, BorderLayout.SOUTH);

        cardPanel.add(notificationPanel, "Notifications");
        
        
        cardLayout.show(cardPanel, "Notifications");
    }

    /**
     * Metodo para criar e gerir a interface dos pagamentos das revisoes do autor
     * @author Dárcio Guerra
     */
    public void payReview(CardLayout cardLayout) {

        payReviewPanel = new JPanel(new BorderLayout());
        auxPayReviewPanel = new JPanel();
        auxTablePayReviewPanel = new JPanel(new BorderLayout());

        payReviewPanel.setBorder(BorderFactory.createLineBorder(getBackground(), 10, false));

        JLabel windowtitle = new JLabel("Payment Window");
        windowtitle.setFont(new Font(null, Font.PLAIN, 24));

        String col[] = {"Author","Book title","Serial Number","Payment amount"};

        DefaultTableModel tableModel = new DefaultTableModel(col, 0);

        ArrayList<Review> reviewList = newSearch.searchReviewsByAuthor("Finished", mode[1]);

        for (int i = 0; i < reviewList.size(); i++){

            if (!reviewList.get(i).getPaid()) {
                String name = reviewList.get(i).getAuthor().getName();
                String bookTitle = reviewList.get(i).getBook().getTitle();
                Long serialNumber  = reviewList.get(i).getSerialNumber();
                float paymentAmount = reviewList.get(i).getPaymentAmount();
                
                Object[] data = {name, bookTitle, serialNumber, paymentAmount};
            
                tableModel.addRow(data);
            }
            
        }

        payTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(payTable);
        payTable.setAutoCreateRowSorter(true);
        payTable.setFillsViewportHeight(true);
        auxTablePayReviewPanel.add(scrollPane);

        mainBack = new JButton("< Back");
        mainBack.setToolTipText("Button to return to Author Menu");
        mainBack.addActionListener(this);

        payButton = new JButton("Pay");
        payButton.setToolTipText("Button to pay a review");
        payButton.addActionListener(this);

        auxPayReviewPanel.add(mainBack);
        auxPayReviewPanel.add(payButton);

        payReviewPanel.add(windowtitle, BorderLayout.NORTH);
        payReviewPanel.add(auxTablePayReviewPanel);
        payReviewPanel.add(auxPayReviewPanel, BorderLayout.SOUTH);

        cardPanel.add(payReviewPanel, "Payment Window");
        
        
        cardLayout.show(cardPanel, "Payment Window");
    }

    /**
     * Metodo para gerir as acoes que o butao para pagar as revisoes ira ter
     * @author Dárcio Guerra
     */
    public void payReviewButtonAction(CardLayout cardLayout) {
        
        Long reviewSerialNumber = (Long)(payTable.getValueAt(payTable.getSelectedRow(), 2));
        System.out.println(reviewSerialNumber);
        
        Updates newUpdate = new Updates();

        if (newUpdate.updateReviewPaid(reviewSerialNumber)) {
            JOptionPane.showMessageDialog(window, "Review paid successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(cardPanel, "Welcome");
        }
    }
    
    /**
     * Metodo para criar e gerir a interface onde o autor pode verificar o estado das revisoes que pediu
     * @author Dárcio Guerra
     */
    public void checkReviewState(CardLayout cardLayout) {

        if (checkReviewStatePanel == null) {
            checkReviewStatePanel = new JPanel(new BorderLayout());
            auxcheckReviewStatePanel = new JPanel();
            auxTablecheckReviewStatePanel= new JPanel(new BorderLayout());

            checkReviewStatePanel.setBorder(BorderFactory.createLineBorder(getBackground(), 10, false));

            JLabel windowTitle = new JLabel("Review state Window");
            windowTitle.setFont(new Font (null, Font.PLAIN, 24));

            String columns[] = {"Author", "Book title", "Serial Number", "State"};

            DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

            ArrayList<Review> reviewList = newSearch.searchReviewsByAuthor(mode[1]);

            for (int i = 0; i < reviewList.size(); i++) {
                String name = reviewList.get(i).getAuthor().getName();
                String bookTitle = reviewList.get(i).getBook().getTitle();
                Long serialNumber = reviewList.get(i).getSerialNumber();
                String state = reviewList.get(i).getState();

                Object[] data = {name, bookTitle, serialNumber, state};

                tableModel.addRow(data);
            }

            checkReviewTable = new JTable(tableModel);

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
            checkReviewTable.getColumnModel().getColumn(3).setCellRenderer(stateColumnRenderer);

            JScrollPane scrollPane = new JScrollPane(checkReviewTable);
            checkReviewTable.setAutoCreateRowSorter(true);
            checkReviewTable.setFillsViewportHeight(true);
            auxTablecheckReviewStatePanel.add(scrollPane);

            mainBack = new JButton("< Back");
            mainBack.setToolTipText("Button to return to Author Menu");
            mainBack.addActionListener(this);

            auxcheckReviewStatePanel.add(mainBack);

            checkReviewStatePanel.add(windowTitle, BorderLayout.NORTH);
            checkReviewStatePanel.add(auxTablecheckReviewStatePanel);
            checkReviewStatePanel.add(auxcheckReviewStatePanel, BorderLayout.SOUTH);

            cardPanel.add(checkReviewStatePanel, "Review state Window");
        }

        cardLayout.show(cardPanel, "Review state Window");
    }

    /**
     * Metodo para criar e gerir a interface onde o autor pode requisitar a remocao da sua conta
     * @author Dárcio Guerra
     */
    public void requestAccountRemoval(CardLayout cardLayout) {
        Client newClient = newSearch.searchAuthor(mode[1]);

        if (newClient.requestAccountRemoval(newClient) == true) {
            JOptionPane.showMessageDialog(window, "Request sent successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(window, "Request failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo para criar e gerir a interface onde o autor pode adicionar um livro no sistema
     * @author Dárcio Guerra
     */
    public void addBook(CardLayout cardLayout) {

        boolean validInput = false;

        while (!validInput) {
            JLabel titleLabel = new JLabel("Title:");
            JTextField title = new JTextField();
            title.setToolTipText("Field to insert book title");
            JLabel subtitleLabel = new JLabel("Subtitle:");
            JTextField subtitle = new JTextField();
            subtitle.setToolTipText("Field to insert book subtitle");

            JLabel styleLabel = new JLabel("Style:");
            String[] styles = {"Action", "Adventure", "Comedy", "Drama", "Fantasy", "Horror", "Mystery", "Romance", "Science Fiction", "Suspence"};
            SpinnerListModel sSpinner = new SpinnerListModel(styles);
            JSpinner styleSpinner = new JSpinner(sSpinner);
            styleSpinner.setToolTipText("Choose book's style");

            JLabel typeLabel = new JLabel("Cover type:");
            JTextField type = new JTextField();
            type.setToolTipText("Field to insert book cover type");
            JLabel pagesLabel = new JLabel("Pages:");
            JTextField pages = new JTextField();
            pages.setToolTipText("Field to insert number of pages of the book");
            JLabel wordsLabel = new JLabel("Words");
            JTextField words = new JTextField();
            words.setToolTipText("Field to insert number of words of the book");
            JLabel isbnLabel = new JLabel("ISBN:");
            JTextField isbn = new JTextField();
            isbn.setToolTipText("Field to insert book isbn");
            JLabel editionLabel = new JLabel("Edition:");
            JTextField edition = new JTextField();
            edition.setToolTipText("Field to insert book edition");

            String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
            SpinnerListModel monthModel = new SpinnerListModel( months );
            JSpinner monthSpinner = new JSpinner(monthModel);
            JLabel monthLabel = new JLabel("Month:");
            monthSpinner.setToolTipText("Choose book's month of creation");

            JLabel dayLabel = new JLabel("Day:");
            JTextField day = new JTextField();
            day.setToolTipText("Field to insert book's day of creation");
            JLabel yearLabel = new JLabel("Year:");
            JTextField year = new JTextField();
            year.setToolTipText("Field to insert book's year of creation");

            JComponent[] inputs = new JComponent[] {titleLabel, title, subtitleLabel, subtitle, styleLabel, styleSpinner, typeLabel, type, pagesLabel, pages, wordsLabel, words, isbnLabel, isbn, editionLabel, edition, dayLabel, day, monthLabel, monthSpinner, yearLabel, year};

            int value = JOptionPane.showConfirmDialog(null, inputs, "Book information", JOptionPane.OK_CANCEL_OPTION);

            if (value == JOptionPane.OK_OPTION) {
                Validation newValidation = new Validation();
                Book newBook = new Book();

                Long newIsbn = Long.parseLong(isbn.getText());
                String newTitle = title.getText();
                if (!newValidation.validateIsbn(newIsbn)) {
                    JOptionPane.showMessageDialog(window, "ISBN already in use. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if(newValidation.validateTitle(newTitle)) {
                    JOptionPane.showMessageDialog(window, "Title already in use. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    int indexMonths = 1;
                    for (String newMonth : months) {
                        if (newMonth.equals(monthSpinner.getValue())) {
                            break;
                        }
                        indexMonths++;
                    }
                    
                    LocalDate date = newValidation.requestDate(Integer.parseInt(year.getText()), indexMonths, Integer.parseInt(day.getText()));

                    if (date != null) {
                        newBook.setAuthor(newSearch.searchAuthor(mode[1]));
                        newBook.setTitle(title.getText());
                        newBook.setSubtitle(subtitle.getText());
                        newBook.setStyle((String) styleSpinner.getValue());
                        newBook.setType(type.getText());
                        newBook.setPages(Integer.parseInt(pages.getText()));
                        newBook.setWords(Integer.parseInt(words.getText()));
                        newBook.setIsbn(newIsbn);
                        newBook.setEdition(Integer.parseInt(edition.getText()));
                        newBook.setSubmissionDate(date);

                        Inserts newInsert = new Inserts();
                        newInsert.insertBook(newBook);

                        validInput = true;
                        JOptionPane.showMessageDialog(window, "Book added successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(window, "Invalid date. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                validInput = true;
            }
        }
    }

    /**
     * Metodo para criar e gerir a interface onde o autor pode adicionar uma revisao ao sistema
     * @author Dárcio Guerra
     */
    public void addReview(CardLayout cardLayout) {
        boolean validInput = false;

        while (!validInput) {
            JLabel bookTitleLabel = new JLabel("Book Title:");
            JTextField bookTitle = new JTextField();
            bookTitle.setToolTipText("Field to insert book's title you want to ve revised");

            JComponent[] inputs = new JComponent[] {bookTitleLabel, bookTitle};
            int value = JOptionPane.showConfirmDialog(null, inputs, "Review information", JOptionPane.OK_CANCEL_OPTION);
        
            if (value == JOptionPane.OK_OPTION) {
                Review newReview = new Review();

                Validation newValidation = new Validation();
                String title = bookTitle.getText();

                if (!newValidation.validateTitle(title)) {
                    JOptionPane.showMessageDialog(window, "Title not found. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    newReview.setAuthor(newSearch.searchAuthor(mode[1]));
                    newReview.setBook(newSearch.searchBook(title));

                    if (newValidation.validateReview(title)) {
                        if (newSearch.searchBook(title).getAuthor().getLogin().equals(mode[1])) {
                            Inserts newInsert = new Inserts();
                            if (newInsert.insertReview(newReview)) {
                                validInput = true;
                                JOptionPane.showMessageDialog(window, "Review added successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(window, "This book doesn't belong to this author. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(window, "There is already a review started for this book. Please try again!", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    
                }
            } else {
                validInput = true;
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
            MainAuthorWindow newMainAuthorWindow = new MainAuthorWindow(mode);
            newMainAuthorWindow.show(); 
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

        CardLayout cardLayout = (CardLayout)(cardPanel.getLayout());

        //File button actions
        if (e.getSource().equals(exitItem)) {
            windowClosing(null);
        } else if(e.getSource().equals(requestAccountRemovalItem)) {
            requestAccountRemoval(cardLayout);
        }

        //Check notification actions
        if(e.getSource().equals(checkNotifications)) {
            checkNotifications(cardLayout);
        } 
        
    
        //Check review state actions
        if(e.getSource().equals(checkReviewState)) {
            checkReviewState(cardLayout);
        } 


        //Pay review actions
        if (e.getSource().equals(payReview)) {
            payReview(cardLayout);
        } else if(e.getSource().equals(payButton)) {
            payReviewButtonAction(cardLayout);
        }

        //back button action
        if (e.getSource().equals(mainBack)) {
            cardLayout.show(cardPanel, "Welcome");
        }


        //add book actions
        if (e.getSource().equals(addBookItem)) {
            addBook(cardLayout);
        }

        //add review actions
        if (e.getSource().equals(startReviewItem)) {
            addReview(cardLayout);
        }
        
    }
}
