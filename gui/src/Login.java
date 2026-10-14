
/**
 * Classe que gere os logins da aplicacao, bem como os menus associados à mesma
 * @author Dárcio Guerra e Rodrigo Pereira
 */
public class Login {

    /**
     * Metodo para pedir as credenciais ao utilizador
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public String[] getInfoNew(String aUsername, String aPassword) {
        String data[] = new String[3];

        data[0] = logon(aUsername, aPassword);
        data[1] = aUsername;
        data[2] = aPassword;

        return data;   
    }

    /**
     * Metodo para verificar as credencias do login
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public String logon(String aUsername, String aPassword) {
        Validation validate = new Validation();
        return validate.validateLogon(aUsername, aPassword);
    }

}
