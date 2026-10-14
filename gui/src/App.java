/**
 * Classe app -> Arranque da aplicação
 * Inicia com o menu principal para registar ou fazer login
 * @author Dárcio Guerra e Rodrigo Pereira
 */

public class App {

    /**
     * Método inicia o arranque da aplicação
     * @author Rodrigo Pereira
     */

    public static void main(String[] args) {
        LoginWindow newLoginWindow = new LoginWindow();
        newLoginWindow.show();
    }
}
