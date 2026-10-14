import java.time.*;
import java.util.regex.*;
import java.sql.*;

/**
 * Classe para gerir validacoes
 * @author Dárcio Guerra e Rodrigo Pereira
 */
public class Validation {

    /**
     * Metodo para validar o login
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean validateLogin(String aLogin) {
        Searches search = new Searches();
        return search.searchLogin(aLogin);
    }

    /**
     * Metodo para validar as informacoes de login
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public String validateLogon(String aUsername, String aPassword) {

        Initialization in = new Initialization();
        Connection newconn = null;
        Statement st = null;
        ResultSet rs = null;

        String type = null;
        String active = null;
        StringBuffer sb = new StringBuffer();


        sb.append("SELECT tipo, estado FROM utilizadores WHERE login = ? AND password = ?");
        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            ps.setString(1, aUsername);
            ps.setString(2, aPassword);
            rs = ps.executeQuery();

            if (rs == null) {
                System.out.println("No records!");
            } else {
                while (rs.next()) {
                    type = rs.getString("tipo");
                    active = rs.getString("estado");
                }
            }
        
        } catch (SQLException e) {
            System.err.println("Error accessing database!!");
            System.out.println(e);
        }

        in.deJdbc(st, newconn);

        if (active == null) {
            return "";
        } else if (active.equals("Active")) {
            return type;
        } else if (active.equals("Inactive")) {
            return "Unauthorized";
        } else if (active.equals("Reproved")) {
            return "Forbidden";
        } else if (active.equals("Omitted")) {
            return "Deleted";
        } else {
            return null;
        }
            
    }
    
    /**
     * Metodo para validar o email
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean validateEmail(String aEmail) {
     
        Searches search = new Searches();

        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(aEmail);

        //^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$ regex antigo

        if (matcher.matches())
            return search.searchEmails(aEmail);
        else
            return false;
    }

    /**
     * metodo para validar o nif
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean validateNif(int aNif) {
       
        boolean validator = true;

        if (aNif > 100000000 && aNif <= 999999999) {
    
            return validator;
        } else {
            validator = false;
            return validator;
        }
    }

    /**
     * Metodo para validar o numero de telefone
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean validateTelephone(int aTelephoneNumber) {

        String regex = "[239]\\d{8}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(Integer.toString(aTelephoneNumber));

        boolean validator = true;

        if (matcher.matches()) {
           
            return validator;
        } else {
            validator = false;
            return validator;
        }
    }

    /**
     * Metodo para validar o isbn
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public boolean validateIsbn(Long aIsbn) {

        Initialization in = new Initialization();
        Connection newconn = null;
        Statement st = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT isbn FROM livros WHERE isbn = ?");

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement(sb.toString());
            ps.setLong(1, aIsbn);

            rs = ps.executeQuery();

            if (rs.next()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }

        in.deJdbc(st, newconn);

        return false;
    }

    /**
     * Metodo para pedir e validar uma data
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public LocalDate requestDate(int newYear, int newMonth, int newDay) {

        LocalDate currentDate = LocalDate.now();
        int maxDayOfMonth = LocalDate.of(newYear, newMonth, 1).lengthOfMonth();

        if (newYear > currentDate.getYear()) {
            return null;
        } else if ((newYear == currentDate.getYear() && newMonth > currentDate.getMonthValue()) || newMonth < 1 || newMonth > 12) {
            return null;
        } else if ((newYear == currentDate.getYear() && newMonth == currentDate.getMonthValue() && newDay > currentDate.getDayOfMonth()) || newDay < 1 || newDay > maxDayOfMonth) {
            return null;
        } else {
            LocalDate selectedDate = LocalDate.of(newYear, newMonth, newDay);
            return selectedDate; 
        }
    }

    /**
     * Metodo para verificar se ja existe alguma revisao na base de dados com o nome do livro passado como argumento
     * @author Dárcio Guerra
     */
    public boolean validateReview(String aTitle) {

        Initialization in = new Initialization();
        Connection newconn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            newconn = in.inJdbc();

            PreparedStatement ps = newconn.prepareStatement("SELECT COUNT(*) FROM revisoes WHERE livros_titulo = ?");
            ps.setString(1, aTitle);

            rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

        in.deJdbc(st, newconn);

        return true;
    }

    /**
     * Metodo para verificar se o titulo de um livro esta a ser usado
     * @author Dárcio Guerra
     */
    public boolean validateTitle(String aTitle){ 

        Initialization in = new Initialization();
        Connection newconn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            newconn = in.inJdbc();
            PreparedStatement ps = newconn.prepareStatement("SELECT COUNT(*) FROM livros WHERE titulo = ?");
            ps.setString(1, aTitle);

            rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

        in.deJdbc(st, newconn);

        return false;
    }
}

//https://www.javatpoint.com/java-email-validation - Validação de emails
