import java.util.*;

/**
 * Classe utilizada para registar utilizadores
 * @author Dárcio Guerra e Rodrigo Pereira
 */
public class Register {

    /**
     * Metodo para criar um gestor
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public void createManager(String type, User aUser) {
        
        Inserts insert = new Inserts();
        Manager newManager = new Manager(type);

        newManager.setEmail(aUser.getEmail());
        newManager.setName(aUser.getName());
        newManager.setLogin(aUser.getLogin());
        newManager.setPassword(aUser.getPassword());
        newManager.setType(type);

        insert.insertUser(aUser, null, null, null);
        
    }  

    /**
     * Metodo para criar um autor
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public void registerAuthor(String type, User aUser, Client aClient, Author aAuthor) {

        Inserts insert = new Inserts();

        insert.insertUser(aUser, aClient, aAuthor, null);
        Notification n = new Notification();
        n = n.informationNewUserN(aUser);
        if (insert.insertNotification(n.getTitle(), n.getContent(), n.getType(), false)) {

            Searches s = new Searches();
            int id = s.searchNotificationById();

            ArrayList<User> userList = s.searchUsersByType("Manager");

            Iterator<User> userIterator = userList.iterator();
            while (userIterator.hasNext()) {
                System.out.println(userIterator.next().getLogin());
            }

            insert.assignUsersNotification(id, userList);
        }
    }

    /**
     * Metodo para criar um revisor
     * @author Dárcio Guerra e Rodrigo Pereira
     */
    public void registerReviewer(String type, User aUser, Client aClient, Reviewer aReviewer) {

        Inserts insert = new Inserts();
        
        insert.insertUser(aUser, aClient, null, aReviewer);
        Notification n = new Notification();
        n = n.informationNewUserN(aUser);
        if (insert.insertNotification(n.getTitle(), n.getContent(), n.getType(), false)) {

            Searches s = new Searches();
            int id = s.searchNotificationById();

            ArrayList<User> userList = s.searchUsersByType("Manager");

            Iterator<User> userIterator = userList.iterator();
            while (userIterator.hasNext()) {
                System.out.println(userIterator.next().getLogin());
            }

            insert.assignUsersNotification(id, userList);
        }
    }
}
