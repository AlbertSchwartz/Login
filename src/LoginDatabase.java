
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Albert Schwartz
 */
public class LoginDatabase {

    Connection DBConn;

    public LoginDatabase(boolean resetDatabase, Connection connection) throws SQLException {

        DBConn = connection;

        if (resetDatabase) {

            Statement stmt = DBConn.createStatement();

            try {
                // Remove tables if database tables have been created.      
                stmt.execute("DROP TABLE Accounts");
                // then do finally
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
                // do finally to create it
            } finally {
                // Create the database
                stmt.execute("CREATE TABLE Accounts ("
                        + "Name VARCHAR(50) NOT NULL, "
                        + "Email VARCHAR(50) NOT NULL, "
                        + "Username VARCHAR(15) NOT NULL, "
                        + "Password VARCHAR(100) NOT NULL"
                        + ")");

            }
        }
    }

    public boolean emailAlreadyInDB(String email) throws SQLException {

        Statement stmt = DBConn.createStatement();
        String sqlStatement = "SELECT * FROM Accounts WHERE Email = '" + email + "'";
        ResultSet result = stmt.executeQuery(sqlStatement);

        return result.next();

    }

    public boolean usernameAlreadyInDB(String username) throws SQLException {

        Statement stmt = DBConn.createStatement();
        String sqlStatement = "SELECT * FROM Accounts WHERE Username = '" + username + "'";
        ResultSet result = stmt.executeQuery(sqlStatement);

        return result.next();

    }

    public void addUser(String name, String email, String username, String password) throws SQLException {

        Statement stmt = DBConn.createStatement();
        String sqlStatement = "INSERT INTO Accounts VALUES ('" + name + "', '" + email + "', '" + username + "', '" + password + "')";
        stmt.execute(sqlStatement);

    }

    public boolean usernameAndPasswordCheck(String username, String password) throws SQLException, PasswordStorage.CannotPerformOperationException, PasswordStorage.InvalidHashException {

        Statement stmt = DBConn.createStatement();
        String sqlStatement = "SELECT * FROM Accounts WHERE Username = '" + username + "'";
        ResultSet result = stmt.executeQuery(sqlStatement);

        if (result.next() == true) {

            String passwordFromUsername = result.getString("Password");

            return PasswordStorage.verifyPassword(password, passwordFromUsername);

        } else {

            return false;

        }

    }
    
    public ObservableList<LoginProfile> getDatabaseInfo() throws SQLException {
    
        ObservableList<LoginProfile> accountsList = FXCollections.observableArrayList();
    
        Statement stmt = DBConn.createStatement();
        String sqlStatement = "SELECT * FROM Accounts";
        ResultSet results = stmt.executeQuery(sqlStatement);
        
        String name;
        String email;
        String username;
        String password;
        Boolean isAdmin = false;
        
        while (results.next()) {
        
        name = results.getString("Name");
        email = results.getString("Email");
        username = results.getString("Username");
        password = results.getString("Password");
        
        LoginProfile tempProfile = new LoginProfile(name, email, username, password, isAdmin);
        accountsList.add(tempProfile);
        
        }
    
    
        return accountsList;
        
    }
    
}
