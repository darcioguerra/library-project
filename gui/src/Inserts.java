import java.sql.*;
import java.util.*;

/**
 * Classe para inserir informacoes na base de dados
 * @author Dárcio Guerra e Rodrigo Pereira
 */
public class Inserts {

    private Initialization in = new Initialization();
    private Connection newconn = null;
    private Statement st = null;

    /**
     * Metodo para inserir um utilizador na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean insertUser(User aUser, Client aClient, Author aAuthor, Reviewer aReviewer) {

        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO utilizadores (nome, login, password, email, tipo, estado) VALUES( ?, ?, ?, ?, ?, ?)");
        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            ps.setString(1, aUser.getName());
            ps.setString(2, aUser.getLogin());
            ps.setString(3, aUser.getPassword());
            ps.setString(4, aUser.getEmail());
            ps.setString(5, aUser.getType());
            ps.setString(6, aUser.getState());
            int count = ps.executeUpdate();
            ps.close();

            if (count == 0) {
                System.out.println("");
            }

            if (aUser.getType().equals("Manager")) {
                sb = new StringBuffer();
                sb.append("INSERT INTO gestores (utilizadores_login) VALUES( ? )");
                ps = newconn.prepareStatement(sb.toString());
                ps.setString(1, aUser.getLogin());
                count = ps.executeUpdate();
                ps.close();
                    
            } else {
                sb = new StringBuffer();
                sb.append("INSERT INTO clientes (morada, nif, telefone, utilizadores_login) VALUES( ?, ?, ?, ? )"); 
                ps = newconn.prepareStatement(sb.toString());
                ps.setString(1, aClient.getAddress());
                ps.setInt(2, aClient.getNif());
                ps.setInt(3, aClient.getTelephoneNumber());
                ps.setString(4, aUser.getLogin());
                count = ps.executeUpdate();
                ps.close();

                if (aUser.getType().equals("Author")) {

                    java.sql.Date sqlDate = java.sql.Date.valueOf(aAuthor.getInitialDate());

                    sb = new StringBuffer();
                    sb.append("INSERT INTO autores (dataatividade, estilos_nome, clientes_utilizadores_login) VALUES( ?, ?, ?)"); 
                    ps = newconn.prepareStatement(sb.toString());
                    ps.setDate(1, sqlDate);
                    ps.setString(2, aAuthor.getLiteraryStyle());
                    ps.setString(3, aUser.getLogin());
                    count = ps.executeUpdate();
                    ps.close();
                    
                } else {
                    sb = new StringBuffer();
                    sb.append("INSERT INTO revisores (spec, form, clientes_utilizadores_login) VALUES( ?, ?, ?)");
                    ps = newconn.prepareStatement(sb.toString());
                    ps.setString(1, aReviewer.getAreaOfEspecialization());
                    ps.setString(2, aReviewer.getAcademics());
                    ps.setString(3, aUser.getLogin());
                    count = ps.executeUpdate();
                    ps.close();
                }


            } 
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            e.printStackTrace();
        }

        in.deJdbc(st, newconn);
        return true;
        
    }

    /**
     * Metodo para inserir estilos na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public void insertStyles() {
        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("INSERT INTO estilos (nome) VALUES(?)");

            String[] values = {"Action", "Adventure", "Comedy", "Drama", "Romance", "Mystery", "Fantasy", "Science Fiction", "Horror", "Suspense"};
            
            for (String value : values) {
                ps.setString(1, value);
                ps.executeUpdate();
            }
           
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            e.printStackTrace();
        }
    }

    /**
     * Metodo para inserir um livro na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean insertBook(Book aBook) {
     
        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("INSERT INTO livros (titulo, subtitulo, tipo, paginas, palavras, isbn, edicao, datasub, dataapr, estilos_nome, autores_clientes_utilizadores_login) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, aBook.getTitle());
            ps.setString(2, aBook.getSubtitle());
            ps.setString(3, aBook.getType());
            ps.setInt(4, aBook.getPages());
            ps.setInt(5, aBook.getWords());
            ps.setLong(6, aBook.getIsbn());
            ps.setInt(7, aBook.getEdition());
            ps.setDate(8, java.sql.Date.valueOf(aBook.getSubmissionDate()));
            ps.setDate(9, java.sql.Date.valueOf(aBook.getAprovationDate()));
            ps.setString(10, aBook.getStyle());
            ps.setString(11, aBook.getAuthor().getLogin());

            ps.executeUpdate();
            ps.close();

            return true;
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metodo para inserir uma revisao na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean insertReview(Review aReview) {

        Validation v = new Validation();
        if (!v.validateIsbn(aReview.getBook().getIsbn())) {
            try {
                newconn = in.inJdbc();
                PreparedStatement ps = newconn.prepareStatement("INSERT INTO revisoes (numeroseq, datareal, tempodec, pago, estado, despesa, isbn, contador, gestores_utilizadores_login, revisores_clientes_utilizadores_login, autores_clientes_utilizadores_login, livros_titulo) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                ps.setLong(1, aReview.getSerialNumber());
                ps.setDate(2, java.sql.Date.valueOf(aReview.getRealizationDate())); 
                ps.setDate(3, java.sql.Date.valueOf(aReview.getElapsedTime()));
                ps.setBoolean(4, aReview.getPaid());
                ps.setString(5, aReview.getState());
                ps.setFloat(6, aReview.getPaymentAmount());
                ps.setLong(7, aReview.getBook().getIsbn());
                ps.setInt(8, aReview.getCount());
                ps.setString(9, null);
                ps.setString(10, null);
                ps.setString(11, aReview.getAuthor().getLogin());
                ps.setString(12, aReview.getBook().getTitle());
    
                ps.executeUpdate();
    
                return true;
            } catch (SQLException e) {
                System.err.println("Error adding review to database!");
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }   
    }

    /**
     * Metodo para inserir uma notificacao na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean insertNotification(String aTitle, String aContent, String aType, boolean aViewed) {

        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO notificacoes (titulo, conteudo, tipo, visto) VALUES( ?, ?, ?, ?)");
        
        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            ps.setString(1, aTitle);
            ps.setString(2, aContent);
            ps.setString(3, aType);
            ps.setBoolean(4, aViewed);

            ps.executeUpdate();
            ps.close();

            return true;
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Metodo para inserir uma notificacao de uma revisao na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean assignReviewNotification(int aId, Long aSerialNumber) {
        
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO notificacoes_revisoes (notificacoes_id, revisoes_numeroseq) VALUES( ?, ?)");
        
        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            ps.setInt(1, aId);
            ps.setLong(2, aSerialNumber);

            ps.executeUpdate();
            ps.close();

            return true;
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metodo para inserir uma notificacao de utilizador na base de dados (manda notificao apenas para 1 utilizador)
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean assignUserNotification(int aId, String aLogin) {
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO notificacoes_utilizadores (notificacoes_id, utilizadores_login) VALUES( ?, ?)");
        
        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            ps.setInt(1, aId);
            ps.setString(2, aLogin);

            ps.executeUpdate();
            ps.close();

            return true;
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metodo para inserir uma notificacao de utilizador na base de dados (manda notificacao para varios utilizadores)
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean assignUsersNotification(int aId, ArrayList<User> aUserList) {
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO notificacoes_utilizadores (notificacoes_id, utilizadores_login) VALUES( ?, ?)");
        
        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            
            Iterator<User> iteratorUserList = aUserList.iterator();

            while (iteratorUserList.hasNext()) {
                ps.setInt(1, aId);
                ps.setString(2, iteratorUserList.next().getLogin());

                ps.executeUpdate();
            }
            ps.close();

            return true;
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metodo para inserir uma licenca na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean insertLicence(Licence aLicence) {
        int count = 0;

    	try {
    		newconn = in.inJdbc();
    		PreparedStatement ps = newconn.prepareStatement("INSERT INTO licencas (nome, descricao, data, id, estado) VALUES (?, ?, ?, ?, ?)");
    		ps.setString(1, aLicence.getName());
    		ps.setString(2, aLicence.getDescription());
    		ps.setDate(3, java.sql.Date.valueOf(aLicence.getDate()));
    		ps.setInt(4, aLicence.getId());
    		ps.setString(5, "Valid");
            count = ps.executeUpdate();

            if (count == 0) {
                System.out.println("No records!");
                ps.close();
            } else {
                System.out.println("Record updated successfully!");
                ps.close();
            }
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}

        in.deJdbc(null, newconn);
    	
    	if (count != 0)
            return true;
        else
            return false;
    }

    /**
     * Metodo para inserir uma revisor, que rejeitou um pedido de atribuicao a uma revisao, na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean insertDeclinedReviewers(String aLogin, Long aSerialNumber) {
    	int count = 0;
    	
    	try {
    		newconn = in.inJdbc();
    		PreparedStatement ps = newconn.prepareStatement("INSERT INTO revisores_declinados (revisores_clientes_utilizadores_login, revisoes_numeroseq) VALUES (?, ?)");
    		ps.setString(1, aLogin);
    		ps.setLong(2, aSerialNumber);
    		count = ps.executeUpdate();
    		
    		if (count == 0) {
                System.out.println("No records!");
                ps.close();
            } else {
                System.out.println("Record updated successfully!");
                ps.close();
            }
    	} catch (SQLException e) {
            System.err.println(e);
        }
    	
    	in.deJdbc(null, newconn);
    	
    	if (count != 0)
            return true;
        else
            return false;
    }

    /**
     * Metodo para inserir uma licenca relacionada a uma revisao na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean insertReviewLicence(Long aSerialNumber, int aId) {

        int count = 0;

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("INSERT INTO licencas_revisoes (licencas_id, revisoes_numeroseq) VALUES (?, ?)");
            ps.setInt(1, aId);
            ps.setLong(2, aSerialNumber);

            count = ps.executeUpdate();

            if (count == 0) {
                System.out.println("No records!");
                ps.close();
            } else {
                System.out.println("Record updated successfully!");
                ps.close();
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

        in.deJdbc(null, newconn);

        if (count != 0)
            return true;
        else
            return false;
    }

    /**
     * Metodo para inserir uma anotacao na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean insertAnnotation(Long aSerialNumber, Annotation newAnnotation) {

        Searches newSearch = new Searches();
        Review newReview = newSearch.searchReviewBySN(aSerialNumber);

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("INSERT INTO anotacoes (descricao, pagina, paragrafo, data, id, revisores_clientes_utilizadores_login, revisoes_numeroseq) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, newAnnotation.getDescription());
            ps.setInt(2, newAnnotation.getPage());
            ps.setInt(3, newAnnotation.getParagraph());
            ps.setDate(4, java.sql.Date.valueOf(newAnnotation.getDate()));
            ps.setInt(5, newAnnotation.getId());
            ps.setString(6, newReview.getHeadReviewer());
            ps.setLong(7, newReview.getSerialNumber());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
