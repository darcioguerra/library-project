import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Classe onde serao efetuadas todas as pesquisas na base de dados
 * @author Dárcio Guerra e Rodrigo Pereira
 */
public class Searches {

    private Initialization in = new Initialization();
    private Connection newconn = null;
    private Statement st = null;
    private ResultSet rs = null;

    /**
     * Metodo para pesquisar utilizadores por nome na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public String searchName(String aLogin, String aPassword) {
        String name = "";
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT nome FROM utilizadores WHERE login = ? AND password = ?");

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            ps.setString(1, aLogin);
            ps.setString(2, aPassword);
            rs = ps.executeQuery(); 
            
            if (rs == null) {
                System.out.println("No records!");
            } else 
                while (rs.next())
                    name = rs.getString("nome");
                    
        
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
        }

        in.deJdbc(st, newconn);

        if (name != null)
            return name;
        else
            return null;
    }

    /**
     * Metodo para pesquisar um login (expecifico) de utilizador na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean searchLogin(String aLogin) {
        
        int count = 0;
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT COUNT(*) FROM utilizadores WHERE login = ?");

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            ps.setString(1, aLogin);
            rs = ps.executeQuery(); 
            
            if (rs == null) {
                System.out.println("No records!");
            } else 
                while (rs.next())
                    count = rs.getInt("COUNT(*)");
                    
        
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
        }

        in.deJdbc(st, newconn);

        if (count == 0)
            return true;
        else
            return false;
    }

    /**
     * Metodo para pesquisar um email (expecifico) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean searchEmails(String aEmail) {
        
        int count = 0;
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT COUNT(*) FROM utilizadores WHERE email = ?");

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            ps.setString(1, aEmail);
            rs = ps.executeQuery();
            
            if (rs == null) {
                System.out.println("No records!");
                
            } else {
                while (rs.next()) {
                    count = rs.getInt("COUNT(*)");
                }
            }        

        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
        }

        in.deJdbc(st, newconn);

        if (count == 0)
            return true;
        else
            return false;
    }

    /**
     * Metodo para pesquisar um utilizador (por login) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public User searchUser(String aLogin) {
        
        User u = new User(); 
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM utilizadores WHERE login = ?");

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            ps.setString(1, aLogin);
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                System.out.println("Não existem utilizadores!");
            } else {
                u.setLogin(rs.getString("login"));
                u.setName(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setState(rs.getString("estado"));
                u.setType(rs.getString("tipo"));;
            }    
        
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
        }

        in.deJdbc(st, newconn);

        return u;
    }  

    /**
     * Metodo para pesquisar utilizadores (por estado) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public ArrayList<User> searchUsersByState(String aState) {
        
        ArrayList<User> userList = new ArrayList<>(); 
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM utilizadores WHERE estado = ?");

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            ps.setString(1, aState);
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                System.out.println("Não existem utilizadores!");
            } else {
                do {
                    User u = new User();
                    u.setLogin(rs.getString("login"));
                    u.setName(rs.getString("nome"));
                    u.setEmail(rs.getString("email"));
                    u.setPassword(rs.getString("password"));
                    u.setState(rs.getString("estado"));
                    u.setType(rs.getString("tipo"));
    
                    userList.add(u);
                    
                } while (rs.next());
            }    
        
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
        }

        in.deJdbc(st, newconn);

        return userList;
    }    

    /**
     * Metodo para pesquisar utilizadores (por tipo) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public ArrayList<User> searchUsersByType(String aType) {
        
        ArrayList<User> userList = new ArrayList<>(); 
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM utilizadores WHERE tipo = ?");

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            ps.setString(1, aType);
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                System.out.println("Não existem utilizadores!");
            } else {
                do {
                    User u = new User();
                    u.setLogin(rs.getString("login"));
                    u.setName(rs.getString("nome"));
                    u.setEmail(rs.getString("email"));
                    u.setPassword(rs.getString("password"));
                    u.setState(rs.getString("estado"));
                    u.setType(rs.getString("tipo"));
    
                    userList.add(u);
                    
                } while (rs.next());
            }    
        
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
        }

        in.deJdbc(st, newconn);

        return userList;
    }   

    /**
     * Metodo para pesquisar os estilos na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public ArrayList<Style> searchStyles() {
        
        ArrayList<Style> styleList = new ArrayList<>(); 
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT nome FROM estilos");

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            
            if (rs == null) {
                System.out.println("No styles are available!");
            } else {
                while (rs.next()) {
                    Style newStyle = new Style();
                    newStyle.setName(rs.getString("nome"));
                    styleList.add(newStyle);
                }
            }    
        
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
        }

        in.deJdbc(st, newconn);

        return styleList;
    }
    
    /**
     * Metodo para pesquisar gestores na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public Manager searchManager(String aLogin) {
        Manager m = new Manager("Manager");
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM utilizadores WHERE login = ?");

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            ps.setString(1, aLogin);
            rs = ps.executeQuery();
            
            if (rs == null) {
                System.out.println("No managers found!");
            } else {
                while (rs.next()) {
                    m.setName(rs.getString("nome"));
                    m.setLogin(rs.getString("login"));
                    m.setPassword(rs.getString("password"));
                    m.setEmail(rs.getString("email"));
                    m.setState(rs.getString("estado"));
                }
            }    
        
        } catch (SQLException e) {
            System.err.println("Error accessing data base!");
            System.err.println(e);
        }

        in.deJdbc(st, newconn);

        return m;
    }   

    /**
     * Metodo para pesquisar autores na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public Author searchAuthor(String aLogin) {
        Author a = new Author("Author");
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM utilizadores LEFT JOIN clientes ON utilizadores.login = clientes.utilizadores_login LEFT JOIN autores ON clientes.utilizadores_login = autores.clientes_utilizadores_login WHERE login = ?");

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            ps.setString(1, aLogin);
            rs = ps.executeQuery();
            
            if (rs == null) {
                System.out.println("No authors found!");
            } else {
                while (rs.next()) {
                    a.setName(rs.getString("nome"));
                    a.setLogin(rs.getString("login"));
                    a.setPassword(rs.getString("password"));
                    a.setEmail(rs.getString("email"));
                    a.setState(rs.getString("estado"));
                    a.setAddress(rs.getString("morada"));
                    a.setNif(rs.getInt("nif"));
                    a.setTelephoneNumber(rs.getInt("telefone"));
                    
                    java.sql.Date sqlDate = rs.getDate("dataatividade");
                    if (sqlDate != null)
                        a.setInitialDate(sqlDate.toLocalDate());
                    
                    a.setLiteraryStyle(rs.getString("estilos_nome"));
                }
            }    
        
        } catch (SQLException e) {
            System.err.println("Error accessing data base!");
            System.err.println(e);
        }

        in.deJdbc(st, newconn);

        return a;
    }

    /**
     * Metodo para pesquisar revisores na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public Reviewer searchReviewer(String aLogin) {
        Reviewer r = new Reviewer("Reviewer");
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM utilizadores LEFT JOIN clientes ON utilizadores.login = clientes.utilizadores_login LEFT JOIN revisores ON clientes.utilizadores_login = revisores.clientes_utilizadores_login WHERE login = ?");

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            ps.setString(1, aLogin);
            rs = ps.executeQuery();
            
            if (rs == null) {
                System.out.println("No reviewers found!");
            } else {
                while (rs.next()) {
                    r.setName(rs.getString("nome"));
                    r.setLogin(rs.getString("login"));
                    r.setPassword(rs.getString("password"));
                    r.setEmail(rs.getString("email"));
                    r.setState(rs.getString("estado"));
                    r.setAddress(rs.getString("morada"));
                    r.setNif(rs.getInt("nif"));
                    r.setTelephoneNumber(rs.getInt("telefone"));

                    r.setAreaOfEspecialization(rs.getString("spec"));
                    r.setAcademics(rs.getString("form"));
                }
            }    
        
        } catch (SQLException e) {
            System.err.println("Error accessing data base!");
            System.err.println(e);
        }

        in.deJdbc(st, newconn);

        return r;
    }

    /**
     * Metodo para pesquisar livros (por titulo) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public Book searchBook(String aTitle) {
        newconn = in.inJdbc();
        Book newBook = new Book();
        Searches newSearch = new Searches();
        
        try (PreparedStatement ps = newconn.prepareStatement("SELECT * FROM livros WHERE titulo = ?")){
            ps.setString(1, aTitle);

            try (ResultSet newResult = ps.executeQuery()) {
            
                while (newResult.next()) {
                    newBook.setTitle(newResult.getString("titulo"));
                    newBook.setSubtitle(newResult.getString("subtitulo"));
                    newBook.setType(newResult.getString("tipo"));
                    newBook.setPages(newResult.getInt("paginas"));
                    newBook.setWords(newResult.getInt("palavras"));
                    newBook.setIsbn(newResult.getLong("isbn"));
                    newBook.setEdition(newResult.getInt("edicao"));
                    
                    java.sql.Date subDateSQL = newResult.getDate("datasub");
                    if (subDateSQL != null) {
                        LocalDate newSubDate = subDateSQL.toLocalDate();
                        newBook.setSubmissionDate(newSubDate);
                    }

                    java.sql.Date approveDateSQL = newResult.getDate("dataapr");
                    if (approveDateSQL != null) {
                        LocalDate newApproveDate = approveDateSQL.toLocalDate();
                        newBook.setAprovationDate(newApproveDate);
                    }

                    newBook.setStyle(newResult.getString("estilos_nome"));

                    Author newAuthor = newSearch.searchAuthor(newResult.getString("autores_clientes_utilizadores_login"));

                    newBook.setAuthor(newAuthor);
                    
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        in.deJdbc(st, newconn);

        return newBook;
    }

    /**
     * Metodo para pesquisar livros (por autor) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public ArrayList<String> searchBooksByAuthor(String aAuthorLogin) {
        
        ArrayList<String> bookList = new ArrayList<>();

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("SELECT titulo FROM livros LEFT JOIN autores ON livros.autores_clientes_utilizadores_login = autores.clientes_utilizadores_login WHERE autores.clientes_utilizadores_login = ?");
            ps.setString(1, aAuthorLogin);
            rs = ps.executeQuery();
            
            if (rs == null) {
                System.out.println("No books found!");
            } else {
                while (rs.next()) {
                    bookList.add(rs.getString("titulo"));
                }
            }    
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
        }

        in.deJdbc(st, newconn);

        return bookList;
    }

    /**
     * Método para pesquisar livros pertencentes a um autor, na base de dados
     * @author Dárcio Guerra
     */
    public ArrayList<Book> searchBookByAuthorSocket(String aLogin) {
        ArrayList<Book> bookList = new ArrayList<>();
        Book newBook = new Book();

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("SELECT * FROM livros LEFT JOIN autores ON livros.autores_clientes_utilizadores_login = autores.clientes_utilizadores_login WHERE autores.clientes_utilizadores_login = ?");
            ps.setString(1, aLogin);
            rs = ps.executeQuery();
            
            try (ResultSet newResult = ps.executeQuery()) {
            
                while (newResult.next()) {
                    newBook.setTitle(newResult.getString("titulo"));
                    newBook.setSubtitle(newResult.getString("subtitulo"));
                    newBook.setStyle(newResult.getString("estilos_nome"));
                    newBook.setType(newResult.getString("tipo"));
                    newBook.setPages(newResult.getInt("paginas"));
                    newBook.setWords(newResult.getInt("palavras"));
                    newBook.setIsbn(newResult.getLong("isbn"));
                    newBook.setEdition(newResult.getInt("edicao"));
                    java.sql.Date subDateSQL = newResult.getDate("datasub");
                    if (subDateSQL != null) {
                        LocalDate newSubDate = subDateSQL.toLocalDate();
                        newBook.setSubmissionDate(newSubDate);
                    }
                    
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e);
            }
   
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
        }

        in.deJdbc(st, newconn);

        return bookList;
    }

    /**
     * Metodo para pesquisar revisoes na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public ArrayList<Review> searchReviews(String aState) {

        ArrayList<Review> reviewList = new ArrayList<>();

        try {
        	if (aState.equals("SearchAll")) {
        		newconn = in.inJdbc();
                PreparedStatement ps = newconn.prepareStatement("SELECT * FROM revisoes");
                rs = ps.executeQuery();

                if (rs == null) {
                    System.out.println("No reviews found! do if do reviews");
                } else {
                    while (rs.next()) {

                        Review auxReview = new Review();
                        auxReview.setSerialNumber(rs.getLong("numeroseq"));

                        java.sql.Date subDateSQL = rs.getDate("datareal");
                        if (subDateSQL != null) {
                            LocalDate newSubDate = subDateSQL.toLocalDate();
                            auxReview.setRealizationDate(newSubDate);
                        }

                        java.sql.Date subDateSQL2 = rs.getDate("tempodec");
                        if (subDateSQL2 != null) {
                            LocalDate newSubDate = subDateSQL2.toLocalDate();
                            auxReview.setElapsedTime(newSubDate);
                        }
                        auxReview.setPaid(rs.getBoolean("pago"));
                        auxReview.setState(rs.getString("estado"));
                        auxReview.setPaymentAmount(rs.getFloat("despesa"));

                        Searches s = new Searches();

                        String bookTitle = rs.getString("livros_titulo");
                        String authorLogin = rs.getString("autores_clientes_utilizadores_login");
                        Author author = s.searchAuthor(authorLogin);
                        Book book = s.searchBook(bookTitle);
                        auxReview.setAuthor(author);
                        auxReview.setBook(book);

                        reviewList.add(auxReview);
                        
                    }
                }    
        	} else {
        		newconn = in.inJdbc();
                PreparedStatement ps = newconn.prepareStatement("SELECT * FROM revisoes WHERE estado = ? ");
                ps.setString(1, aState);
                rs = ps.executeQuery();

                if (rs == null) {
                    System.out.println("No reviews found! do if do reviews");
                } else {
                    while (rs.next()) {

                        Review auxReview = new Review();
                        auxReview.setSerialNumber(rs.getLong("numeroseq"));

                        java.sql.Date subDateSQL = rs.getDate("datareal");
                        if (subDateSQL != null) {
                            LocalDate newSubDate = subDateSQL.toLocalDate();
                            auxReview.setRealizationDate(newSubDate);
                        }

                        java.sql.Date subDateSQL2 = rs.getDate("tempodec");
                        if (subDateSQL2 != null) {
                            LocalDate newSubDate = subDateSQL2.toLocalDate();
                            auxReview.setElapsedTime(newSubDate);
                        }
                        auxReview.setPaid(rs.getBoolean("pago"));
                        auxReview.setState(rs.getString("estado"));
                        auxReview.setPaymentAmount(rs.getFloat("despesa"));
                        auxReview.setHeadReviewer(rs.getString("revisores_clientes_utilizadores_login"));

                        Searches s = new Searches();

                        String bookTitle = rs.getString("livros_titulo");
                        String authorLogin = rs.getString("autores_clientes_utilizadores_login");
                        Author author = s.searchAuthor(authorLogin);
                        Book book = s.searchBook(bookTitle);
                        auxReview.setAuthor(author);
                        auxReview.setBook(book);

                        reviewList.add(auxReview); 
                    }
                }    
        	}
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
            e.printStackTrace();
        }

        in.deJdbc(st, newconn);

        return reviewList;
    }

    /**
     * Metodo para pesquisar todas as pesquisas de um autor na base de dados
     * @author Dárcio Guerra
     */
    public ArrayList<Review> searchReviewsByAuthor(String aLogin) {
        ArrayList<Review> reviewList = new ArrayList<>();

        try {
            
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("SELECT * FROM revisoes WHERE autores_clientes_utilizadores_login = ?");
        
            ps.setString(1, aLogin);
            rs = ps.executeQuery();

            if (rs == null) {
                System.out.println("o reviews found! do if do reviews");
            } else {
                while (rs.next()) {
                    
                    Review auxReview = new Review();
                    auxReview.setSerialNumber(rs.getLong("numeroseq"));

                    java.sql.Date subDateSQL = rs.getDate("datareal");
                    if (subDateSQL != null) {
                        LocalDate newSubDate = subDateSQL.toLocalDate();
                        auxReview.setRealizationDate(newSubDate);
                    }

                    java.sql.Date subDateSQL2 = rs.getDate("tempodec");
                    if (subDateSQL2 != null) {
                        LocalDate newSubDate = subDateSQL2.toLocalDate();
                        auxReview.setElapsedTime(newSubDate);
                    }
                    auxReview.setPaid(rs.getBoolean("pago"));
                    auxReview.setState(rs.getString("estado"));
                    auxReview.setPaymentAmount(rs.getFloat("despesa"));
                    /*if (aState != "Started")*/

                    Searches s = new Searches();

                    String bookTitle = rs.getString("livros_titulo");
                    String authorLogin = rs.getString("autores_clientes_utilizadores_login");

                    Author author = s.searchAuthor(authorLogin);
                    Book book = s.searchBook(bookTitle);
                    auxReview.setAuthor(author);
                    auxReview.setBook(book);

                    reviewList.add(auxReview);
                }
            }
        } catch (Exception e) {
            
            System.err.println("Error accessing database!!");
            System.err.println(e);
            e.printStackTrace();
        }

        in.deJdbc(st, newconn);

        return reviewList;
    }

    /**
     * Metodo para pesquisar revisoes (por autor) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public ArrayList<Review> searchReviewsByAuthor(String aState, String aLogin) {

        ArrayList<Review> reviewList = new ArrayList<>();

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("SELECT * FROM revisoes WHERE estado = ? AND autores_clientes_utilizadores_login = ?");
            ps.setString(1, aState);
            ps.setString(2, aLogin);
            rs = ps.executeQuery();

            if (rs == null) {
                System.out.println("No reviews found! do if do reviews");
            } else {
                while (rs.next()) {

                    Review auxReview = new Review();
                    auxReview.setSerialNumber(rs.getLong("numeroseq"));
                    System.err.println(auxReview.getSerialNumber());

                    java.sql.Date subDateSQL = rs.getDate("datareal");
                    if (subDateSQL != null) {
                        LocalDate newSubDate = subDateSQL.toLocalDate();
                        auxReview.setRealizationDate(newSubDate);
                    }

                    java.sql.Date subDateSQL2 = rs.getDate("tempodec");
                    if (subDateSQL2 != null) {
                        LocalDate newSubDate = subDateSQL2.toLocalDate();
                        auxReview.setElapsedTime(newSubDate);
                    }
                    auxReview.setPaid(rs.getBoolean("pago"));
                    auxReview.setState(rs.getString("estado"));
                    auxReview.setPaymentAmount(rs.getFloat("despesa"));
                    /*if (aState != "Started")*/

                    Searches s = new Searches();

                    String bookTitle = rs.getString("livros_titulo");
                    String authorLogin = rs.getString("autores_clientes_utilizadores_login");
                    Author author = s.searchAuthor(authorLogin);
                    Book book = s.searchBook(bookTitle);
                    auxReview.setAuthor(author);
                    auxReview.setBook(book);

                    reviewList.add(auxReview);
                    
                }
            }    
    
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
            e.printStackTrace();
        }

        in.deJdbc(st, newconn);

        return reviewList;
    }

    /**searchReviewerByTerm
     * Metodo para pesquisar o numero de sequencia de uma revisao (por titulo) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public Long searchReviewSN(String aBookName) {

        Long serialNumber = null;

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("SELECT numeroseq FROM revisoes WHERE livros_titulo = ? ");
            ps.setString(1, aBookName);
            rs = ps.executeQuery();
            
            if (!rs.next())
                System.out.println("No review S/N found!");
            else
                serialNumber = rs.getLong("numeroseq");
        
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
        }

        in.deJdbc(st, newconn);

        return serialNumber;
    }

    /**
     * Metodo para pesquisar revisoes (por nome de livro) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public Review searchReviewByBook(String aBookName) {

        Review r = new Review();

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("SELECT * FROM revisoes WHERE livros_titulo = ? ");
            ps.setString(1, aBookName);
            rs = ps.executeQuery();
            
            if (!rs.next())
                System.out.println("No review found!");
            else {
                r.setSerialNumber(rs.getLong("numeroseq"));

                java.sql.Date subDateSQL = rs.getDate("datareal");
                if (subDateSQL != null) {
                    LocalDate newSubDate = subDateSQL.toLocalDate();
                    r.setRealizationDate(newSubDate);
                }

                java.sql.Date subDateSQL2 = rs.getDate("tempodec");
                if (subDateSQL2 != null) {
                    LocalDate newSubDate = subDateSQL2.toLocalDate();
                    r.setElapsedTime(newSubDate);
                }
                r.setPaid(rs.getBoolean("pago"));
                r.setState(rs.getString("estado"));
                r.setPaymentAmount(rs.getFloat("despesa"));

                Searches s = new Searches();

                String bookTitle = rs.getString("livros_titulo");
                String authorLogin = rs.getString("autores_clientes_utilizadores_login");
                Author author = s.searchAuthor(authorLogin);
                Book book = s.searchBook(bookTitle);
                r.setAuthor(author);
                r.setBook(book);
            }
        
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
        }

        in.deJdbc(st, newconn);

        return r;
    }

    /**
     * Metodo para pesquisar revisoes (por numero de sequencia) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public Review searchReviewBySN(Long aSerialNumber) {

        Review r = new Review();

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("SELECT * FROM revisoes WHERE numeroseq = ? ");
            ps.setLong(1, aSerialNumber);
            rs = ps.executeQuery();
            
            if (!rs.next())
                System.out.println("No review found!");
            else {
                r.setSerialNumber(rs.getLong("numeroseq"));

                java.sql.Date subDateSQL = rs.getDate("datareal");
                if (subDateSQL != null) {
                    LocalDate newSubDate = subDateSQL.toLocalDate();
                    r.setRealizationDate(newSubDate);
                }

                java.sql.Date subDateSQL2 = rs.getDate("tempodec");
                if (subDateSQL2 != null) {
                    LocalDate newSubDate = subDateSQL2.toLocalDate();
                    r.setElapsedTime(newSubDate);
                }
                r.setPaid(rs.getBoolean("pago"));
                r.setState(rs.getString("estado"));
                r.setPaymentAmount(rs.getFloat("despesa"));
                r.setHeadReviewer(rs.getString("revisores_clientes_utilizadores_login"));

                Searches s = new Searches();

                String bookTitle = rs.getString("livros_titulo");
                String authorLogin = rs.getString("autores_clientes_utilizadores_login");
                String managerLogin = rs.getString("gestores_utilizadores_login");
                Manager manager = s.searchManager(managerLogin);
                Author author = s.searchAuthor(authorLogin);
                Book book = s.searchBook(bookTitle);
                r.setManager(manager);
                r.setAuthor(author);
                r.setBook(book);
            }
        
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
        }

        in.deJdbc(st, newconn);

        return r;
    }

    /**
     * Metodo para pesquisar revisoes (por estado e login de gestor) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public ArrayList<Review> searchManagersReviews(String aState, Manager aManager) {

        ArrayList<Review> reviewList = new ArrayList<>();

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("SELECT * FROM revisoes WHERE gestores_utilizadores_login = ? AND estado = ?");
            ps.setString(1, aManager.getLogin());
            ps.setString(2, aState);
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                System.out.println("No reviews found!");
            } else {
                while (rs.next()) {

                    Review auxReview = new Review();
                    auxReview.setSerialNumber(rs.getInt("numeroseq"));

                    java.sql.Date subDateSQL = rs.getDate("datareal");
                    if (subDateSQL != null) {
                        LocalDate newSubDate = subDateSQL.toLocalDate();
                        auxReview.setRealizationDate(newSubDate);
                    }

                    java.sql.Date subDateSQL2 = rs.getDate("tempodec");
                    if (subDateSQL2 != null) {
                        LocalDate newSubDate = subDateSQL2.toLocalDate();
                        auxReview.setElapsedTime(newSubDate);
                    }
                    auxReview.setPaid(rs.getBoolean("pago"));
                    auxReview.setState(rs.getString("estado"));
                    auxReview.setPaymentAmount(rs.getFloat("despesa"));

                    reviewList.add(auxReview);
                    
                }
            }    
        
        } catch (SQLException e) {
            System.err.println(e);
        }

        in.deJdbc(st, newconn);

        return reviewList;
    }

    /**
     * Metodo para pesquisar o login e o nome de um revisor que nao tenha negado o pedido de se tornar revisor chefe de uma determinada revisao na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public ArrayList<Reviewer> searchReviewers(Long aSerialNumber) { //procura os reviewers, consoante cada pedido de revisão, nos quais ainda não rejeitaram a escolha

        ArrayList<Reviewer> reviewersList = new ArrayList<>();

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("SELECT login, nome, email, telefone FROM revisores JOIN utilizadores ON revisores.clientes_utilizadores_login = utilizadores.login JOIN clientes ON revisores.clientes_utilizadores_login = clientes.utilizadores_login LEFT JOIN revisores_declinados ON revisores.clientes_utilizadores_login = revisores_declinados.revisores_clientes_utilizadores_login WHERE revisores.clientes_utilizadores_login NOT IN (SELECT revisores_clientes_utilizadores_login FROM revisores_declinados WHERE revisoes_numeroseq = ?)");
            ps.setLong(1, aSerialNumber);
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                System.out.println("No eligible reviewers found!");
                return null;
            } else {
                do {
                    Reviewer auxReviewer = new Reviewer("Reviewer");
                    auxReviewer.setLogin(rs.getString("login"));
                    auxReviewer.setName(rs.getString("nome"));
                    auxReviewer.setEmail(rs.getString("email"));
                    auxReviewer.setTelephoneNumber(rs.getInt("telefone"));
    
                    reviewersList.add(auxReviewer);
                } while (rs.next());
            }    
        
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
            e.printStackTrace();
        }

        in.deJdbc(st, newconn);

        return reviewersList;
    }


    /**
     * Metodo para pesquisar uma notificacao na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public ArrayList<Notification> searchNotifications(String aLogin) {
        ArrayList<Notification> notificationList = new ArrayList<>();

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("SELECT n.*, nu.utilizadores_login \n" + //
                                "FROM notificacoes n\n" + //
                                "LEFT JOIN notificacoes_utilizadores nu ON n.id = nu.notificacoes_id \n" + //
                                "WHERE nu.utilizadores_login = ? AND n.visto = ?");
            ps.setString(1, aLogin);
            ps.setBoolean(2, false);
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                System.out.println("No notifications found!");
            } else {
                do {
                    Notification auxNotification = new Notification();
                    auxNotification.setId(rs.getInt("id"));
                    auxNotification.setTitle(rs.getString("titulo"));
                    auxNotification.setContent(rs.getString("conteudo"));
                    auxNotification.setType(rs.getString("tipo"));
                    auxNotification.setViewed(rs.getBoolean("visto"));
    
                    notificationList.add(auxNotification);
                } while (rs.next());
            }    
        
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
            e.printStackTrace();
        }

        in.deJdbc(st, newconn);

        return notificationList;
    }

    /**
     * Metodo para pesquisar uma notificacao (por id) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public Notification searchNotification(int aId) {
        Notification n = new Notification();

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("SELECT * FROM notificacoes WHERE id = ?");
            ps.setInt(1, aId);
            rs = ps.executeQuery();
            
            if (!rs.next()) {
                System.out.println("No notifications found!");
            } else {
                n.setId(rs.getInt("id"));
                n.setTitle(rs.getString("titulo"));
                n.setContent(rs.getString("conteudo"));
                n.setType(rs.getString("tipo"));
                n.setViewed(rs.getBoolean("visto"));
            }    
        
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
            e.printStackTrace();
        }

        in.deJdbc(st, newconn);

        return n;
    }

    /**
     * Metodo para pesquisar uma notificacao (por id maximo) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public int searchNotificationById() {
        int value = 0;

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("SELECT MAX(id) FROM notificacoes");
            rs = ps.executeQuery();
            
            if (!rs.next())
                System.out.println("No notifications found!");
            else
                value = rs.getInt("MAX(id)"); 
        
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
            e.printStackTrace();
        }

        in.deJdbc(st, newconn);

        return value;
    }

    /**
     * Metodo para pesquisar o numero de sequencia de uma revisao (por id de uma notificacao) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public Long searchNotificationBySN(int aId) {
        Long value = null;

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("SELECT revisoes_numeroseq FROM notificacoes_revisoes WHERE notificacoes_id = ?");
            ps.setInt(1, aId);
            rs = ps.executeQuery();
            
            if (!rs.next())
                System.out.println("No notifications found!");
            else
                value = rs.getLong("revisoes_numeroseq"); 
        
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
            e.printStackTrace();
        }

        in.deJdbc(st, newconn);

        return value;
    }

    /**
     * Metodo para pesquisar o login de um utilizador (por id de notificacao) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public String searchOmitNotification(int aId) {
        String login = null;

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("SELECT utilizadores_login FROM utilizadores_p_omitir WHERE notificacoes_id = ?");
            ps.setInt(1, aId);
            rs = ps.executeQuery();
            
            if (!rs.next())
                System.out.println("No notifications found!");
            else
                login = rs.getString("utilizadores_login"); 
        
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
            e.printStackTrace();
        }

        in.deJdbc(st, newconn);

        return login;
    }
    
    /**
     * Metodo para pesquisar o maior id de uma licenca na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public int searchLicenceID() {
    	int value = 0;
    	
    	try {
    		newconn = in.inJdbc();
    		PreparedStatement ps = newconn.prepareStatement("SELECT id FROM licencas ORDER BY id DESC LIMIT 1");
    		rs = ps.executeQuery();
    		
    		if(rs.next()) {
    			value = rs.getInt("id");
    		}
    	} catch(SQLException e) {
    		System.err.println(e);
    	}
    	
    	return value;
    }

    /**
     * Metodo para pesquisar uma licenca na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public ArrayList<Licence> searchLicences() {
        ArrayList<Licence> licences = new ArrayList<>();

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("SELECT * FROM licencas");
            rs = ps.executeQuery();

            while (rs.next()) {
                Licence newLicence = new Licence();
                newLicence.setName(rs.getString("nome"));
                newLicence.setDescription(rs.getString("descricao"));

                java.sql.Date subDateSQL = rs.getDate("data");
                if (subDateSQL != null) {
                    LocalDate newSubDate = subDateSQL.toLocalDate();
                    newLicence.setDate(newSubDate);
                }
                
                newLicence.setId(rs.getInt("id"));

                licences.add(newLicence);
            }
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            // Feche os recursos JDBC adequadamente
            try {
                if (rs != null) {
                    rs.close();
                }
                if (newconn != null) {
                    newconn.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos JDBC: " + e);
            }
        }

        return licences;
    }

    /**
     * Metodo para pesquisar o maior id de uma anotacao na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public int searchAnotationID() {

        int value = 0;

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("SELECT id FROM anotacoes ORDER BY id DESC LIMIT 1");

            rs = ps.executeQuery();

            if (rs.next()) {
                value = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

        return value;
    }

    /**
     * Metodo para pesquisar o maior contador das revisoes na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public int searchLastCount() {
        int count = 0;

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("SELECT contador FROM revisoes ORDER BY contador DESC LIMIT 1");
            rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt("contador");
            }
        } catch(SQLException e) {
            System.err.println(e);
        }

        return count;
    }


}