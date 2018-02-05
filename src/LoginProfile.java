
import javafx.beans.property.SimpleStringProperty;


/**
 *
 * @author Albert
 */
public class LoginProfile {

    SimpleStringProperty accountName;
    SimpleStringProperty accountEmail;
    SimpleStringProperty accountUsername;
    SimpleStringProperty accountPassword;
    Boolean isAdmin;

    public LoginProfile(String accountName, String accountEmail, String accountUsername, String accountPassword, Boolean isAdmin) {

        this.accountName = new SimpleStringProperty(accountName);
        this.accountEmail = new SimpleStringProperty(accountEmail);
        this.accountUsername = new SimpleStringProperty(accountUsername);
        this.accountPassword = new SimpleStringProperty(accountPassword);
        this.isAdmin = isAdmin;

    }

    public void setName(String name) {

        this.accountName = new SimpleStringProperty(name);

    }

    public void setEmail(String email) {

        this.accountEmail = new SimpleStringProperty(email);

    }

    public void setUsername(String username) {

        this.accountUsername = new SimpleStringProperty(username);

    }

    public void setPassword(String password) {

        this.accountPassword = new SimpleStringProperty(password);

    }

    public void setIsAdmin(Boolean isAdmin) {

        this.isAdmin = isAdmin;

    }

    public String getAccountName() {

        return accountName.get();

    }

    public String getAccountEmail() {

        return accountEmail.get();

    }

    public String getAccountUsername() {

        return accountUsername.get();

    }

    public Boolean getIsAdmin() {

        return isAdmin;

    }
    
    public SimpleStringProperty getNameProperty() {
    
    return accountName;
    
    }
    
    public SimpleStringProperty getEmailProperty() {
    
    return accountEmail;
    
    }
    
    public SimpleStringProperty getUsernameProperty() {
    
    return accountUsername;
    
    }
    
    public SimpleStringProperty getPasswordProperty() {
    
    return accountPassword;
    
    }

}
