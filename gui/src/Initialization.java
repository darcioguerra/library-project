import java.sql.*;
import java.time.*;


/**
 * Classe onde irao ser realizadas todas as inicializacoes do sistema
 * @author Dárcio Guerra e Rodrigo Pereira
 */
public class Initialization {
    
    private static Connection conn = null;

    /**
     * Método para inicializar a conexão com base de dados
     * @author Rodrigo Pereira
     */
    public Connection inJdbc() {
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pa", "root", "1234");
        } catch (SQLException e) {
            System.err.println("Error accessing database!");
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found!");
            System.out.println(e);
        }

        return conn;
    }

    /**
     * Método para encerrar a conexão com a base de dados
     * @author Rodrigo Pereira
     */
    public void deJdbc(Statement st, Connection conn) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection!");
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection!");
            }
        }    
    }

    /**
     * Metodo para pedir a data ao sistema (horas, minutos, segundos)
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public int[] getTime() {
        LocalTime atualTime = LocalTime.now();

        int hours = atualTime.getHour();
        int minutes = atualTime.getMinute();
        int seconds = atualTime.getSecond();

        int[] timeNow = {hours, minutes, seconds};

        return timeNow;
     }
}
