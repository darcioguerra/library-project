import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * Classe com o proposito de atualizar as os dados da base de dados
 * @author Dárcio Guerra e Rodrigo Pereira
 */
public class Updates {
    
    private Initialization in = new Initialization();
    private Connection newconn = null;

    /**
     * Metodo para atualizar o estado de um utilizador (por login) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean updateUserState(String aState, String aLogin) {
        if (aLogin == null)
            return false;
        
        int count = 0;
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE utilizadores SET estado = ? WHERE login = ?");

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            ps.setString(1, aState);
            ps.setString(2, aLogin);
            count = ps.executeUpdate();
            
            if (count == 0) {
                System.out.println("No records!");
                ps.close();
            } else {
                System.out.println("Record updated successfully!");
                ps.close();
            }
        
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
        }

        in.deJdbc(null, newconn);

        if (count != 0)
            return true;
        else
            return false;
    }

    /**
     * Metodo para atualizar o login de um revisor associado a uma revisao (por titulo de livro) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean updateReviewManager(Manager aManager, String aBookName) {
        
        int count = 0;
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE revisoes SET gestores_utilizadores_login = ? WHERE livros_titulo = ?");

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            ps.setString(1, aManager.getLogin());
            ps.setString(2, aBookName);
            count = ps.executeUpdate();
            
            if (count == 0) {
                System.out.println("No records!");
                ps.close();
            } else {
                System.out.println("Record updated successfully!");
                ps.close();
            }
        
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
        }

        in.deJdbc(null, newconn);

        if (count != 0)
            return true;
        else
            return false;
    }

    /**
     * Metodo para atualizar o estado de uma revisao (por numero de sequencia) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean updateReviewState(String aState, Long aSerialNumber) {
        int count = 0;
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE revisoes SET estado = ? WHERE numeroseq = ?");

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            ps.setString(1, aState);
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
            System.err.println("Error accessing database!!");
            System.err.println(e);
        }

        in.deJdbc(null, newconn);

        if (count != 0)
            return true;
        else
            return false;
    }

    /**
     * Metodo para atualizar o revisor associado a uma revisao (por numero de revisao) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean updateHeadReviewer(String aLogin, Long aSerialNumber) {
        
        int count = 0;
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE revisoes SET revisores_clientes_utilizadores_login = ?, estado = ? WHERE numeroseq = ?");

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            ps.setString(1, aLogin);
            ps.setString(2, "Approved");
            ps.setLong(3, aSerialNumber);
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
     * Metodo para atualizar o preco da revisao (por numero de sequencia) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean updateReviewPaymentAmount(float aCost, Long aSerialNumber) {

        int count = 0;

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("UPDATE revisoes SET despesa = ? WHERE numeroseq = ?");
            ps.setFloat(1, aCost);
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
            System.err.println("Error accessing database!!");
            System.err.println(e);
        }

        in.deJdbc(null, newconn);

        if (count != 0)
            return true;
        else
            return false;
    }

    /**
     * Metodo para atualizar o estado de uma notificacao (por id) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean updateNotificationViewed(ArrayList <Notification> aNotificationList, int aId) {
        
        int count = 0;
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE notificacoes SET visto = ? WHERE id = ?");

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            ps.setBoolean(1, true);
            ps.setLong(2, aId);
            count = ps.executeUpdate();
            
            if (count == 0) {
                System.out.println("No records!");
                ps.close();
            } else {
                System.out.println("Record updated successfully!");
                ps.close();
            }
        
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.err.println(e);
        }
        
        in.deJdbc(null, newconn);

        if (count != 0)
            return true;
        else
            return false;
    }

    /**
     * Metodo para omitir as informacoes de um utilizador (por login) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean omitInfo(String aLogin) {

        if (aLogin == null)
            return false;

        try {
			PreparedStatement ps = newconn.prepareStatement("UPDATE utilizadores SET nome = NULL, login = NULL, password = NULL, email = NULL WHERE login = ?");
			ps.setString(1, aLogin);
			ps.executeUpdate();

            PreparedStatement ps2 = newconn.prepareStatement("UPDATE clientes SET morada = NULL, nif = NULL, telefone = NULL WHERE login = ?");
            ps2.setString(1, aLogin);
			ps2.executeUpdate();

            PreparedStatement ps3 = newconn.prepareStatement("UPDATE autores SET estilos_nome = NULL, data_atividade = 0 WHERE login = ?");
            ps3.setString(1, aLogin);
			ps3.executeUpdate();

            PreparedStatement ps4 = newconn.prepareStatement("UPDATE revisores SET spec = NULL, form = NULL WHERE login = ?");
            ps4.setString(1, aLogin);
			ps4.executeUpdate();
			
            System.out.println("Informações específicas da conta omitidas com sucesso para o usuário com login: " + aLogin);
		} catch (SQLException e) {
			System.err.println("Error accessing database!!");
		}
        
        in.deJdbc(null, newconn);

        return false;
    }

    /**
     * Metodo para atualizar o estado de pagamento de uma revisao (por numero de sequencia) na base de dados
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean updateReviewPaid(Long aSerialNumber) {

        int count = 0;

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("UPDATE revisoes SET pago = ? WHERE numeroseq = ?");
            ps.setBoolean(1, true);
            ps.setLong(2, aSerialNumber);
            count = ps.executeUpdate();

            if (count == 0) {
                System.out.println("No records!");
                ps.close();
            } 
        } catch (Exception e) {
            System.err.println(e);
        }

        if (count != 0)
            return true;
        else
            return false;
    }

    /**
     * Método para atualizar os dados do autor
     * @author Rodrigo Pereira
     */

    public boolean updateAuthorData(String aLogin, String[] aData) {
        int count = 0;

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("UPDATE utilizadores SET password = ?, nome = ?, email = ? WHERE login = ?");
            ps.setString(1, aData[0]);
            ps.setString(2, aData[1]);
            ps.setString(3, aData[2]);
            ps.setString(4, aLogin);
            count = ps.executeUpdate();

            if (count == 0) {
                System.out.println("No records!");
                ps.close();
            } 
        } catch (Exception e) {
            System.err.println(e);
        }

        if (count != 0) {
            count = 0;

            try {
                newconn = in.inJdbc();
                PreparedStatement ps = newconn.prepareStatement("UPDATE clientes SET nif = ?, telefone = ?, morada = ? WHERE utilizadores_login = ?");
                ps.setString(1, aData[3]);
                ps.setString(2, aData[4]);
                ps.setString(3, aData[5]);
                ps.setString(4, aLogin);
                count = ps.executeUpdate();
    
                if (count == 0) {
                    System.out.println("No records!");
                    ps.close();
                } 
            } catch (Exception e) {
                System.err.println(e);
            }

            if (count != 0)
                return true;
            else
                return false;
        }
            
        else
            return false;
    }
}
