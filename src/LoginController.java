
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Albert Schwartz
 */
public class LoginController implements Initializable {

    private Connection conn;
    private LoginDatabase accountsDB;

    @FXML
    private JFXTextField usernameTxt;
    @FXML
    private JFXPasswordField passwordTxt;
    @FXML
    private ImageView logoImage;
    @FXML
    private ImageView closeImage;
    @FXML
    private ImageView usernameImage;
    @FXML
    private ImageView passwordImage;
    @FXML
    private JFXButton closeBtn;
    @FXML
    private JFXButton signUpBtn;
    @FXML
    private Label loginSuccessfulLbl;

    public void close() {

        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();

    }

    public void signUp() throws Exception {

        accountsDB = new LoginDatabase(false, conn);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
        Parent SignUp = (Parent) fxmlLoader.load();
        SignUpController newSignUpController = (SignUpController) fxmlLoader.getController();
        newSignUpController.setDatabase(accountsDB);

        Scene scene = new Scene(SignUp);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:src/SSLogo.png"));
        stage.setTitle("Sign Up");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }
    
        public void viewDatabase() throws IOException, SQLException {
    
        accountsDB = new LoginDatabase(false, conn);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DisplayDatabase.fxml"));
        Parent DisplayDatabase = (Parent) fxmlLoader.load();
        DisplayDatabaseController newDatabaseController = (DisplayDatabaseController) fxmlLoader.getController();
        newDatabaseController.setDatabase(accountsDB);

        Scene scene = new Scene(DisplayDatabase);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:src/SSLogo.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    
    }

    private void displayAlert(String msg) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Alert.fxml"));
            Parent ERROR = loader.load();
            AlertController controller = (AlertController) loader.getController();

            Scene scene = new Scene(ERROR);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.getIcons().add(new Image("file:src/SSLogo.png"));
            controller.setAlertText(msg);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException ex1) {

        }
    }

    public void login() throws SQLException, PasswordStorage.CannotPerformOperationException, PasswordStorage.InvalidHashException {

        String usernameInput = usernameTxt.getText();
        String passwordInput = passwordTxt.getText();
        boolean loginsuccessful = accountsDB.usernameAndPasswordCheck(usernameInput, passwordInput);

        if (loginsuccessful) {

            usernameTxt.setText("");
            passwordTxt.setText("");
            loginSuccessfulLbl.setText("");
            displayAlert("Login was successful.");

        } else {

            loginSuccessfulLbl.setText("*Username/password combination is invalid.*");

        }

    }

    public void createDB() {

        try {
            accountsDB = new LoginDatabase(true, conn);
            displayAlert("Accounts table has been created");

        } catch (SQLException ex) {
            displayAlert("ERROR: " + ex.getMessage());
        }

    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        usernameTxt.setStyle("-fx-prompt-text-fill: #a0a2ab");
        passwordTxt.setStyle("-fx-prompt-text-fill: #a0a2ab;");
        logoImage.setImage(new Image("file:src/SSLogo.png"));
        closeImage.setImage(new Image("file:src/close.png"));
        usernameImage.setImage(new Image("file:src/username.png"));
        passwordImage.setImage(new Image("file:src/password.png"));

        try {
            // Create a named constant for the URL
            // NOTE: This value is specific for Java DB
            String DB_URL = "jdbc:derby:LoginDB;create=true";
            // Create a connection to the database
            conn = DriverManager.getConnection(DB_URL);

        } catch (SQLException ex) {

            displayAlert(ex.getMessage());

        }

        //createDB();
        try {

            accountsDB = new LoginDatabase(false, conn);

        } catch (SQLException ex) {

            displayAlert(ex.getMessage());

        }
    }

}
