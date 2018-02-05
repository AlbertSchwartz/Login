
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Albert Schwartz
 */
public class SignUpController implements Initializable {

    private LoginDatabase accountsDB;

    @FXML
    private ImageView SSLogo;
    @FXML
    private ImageView backLogo;
    @FXML
    private JFXButton backBtn;
    @FXML
    private Label nameAlertLbl;
    @FXML
    private Label emailAlertLbl;
    @FXML
    private Label usernameAlertLbl;
    @FXML
    private Label passwordAlertLbl;
    @FXML
    private Label successLbl;
    @FXML
    private JFXTextField nameTxt;
    @FXML
    private JFXTextField emailTxt;
    @FXML
    private JFXTextField usernameTxt;
    @FXML
    private JFXPasswordField passwordTxt;
    @FXML
    private JFXCheckBox showPassCheckBox;
    @FXML
    private JFXTextField showPassTextTxt;

    public void setDatabase(LoginDatabase dataBase) {

        accountsDB = dataBase;

    }

    public void createAccount() throws SQLException, PasswordStorage.CannotPerformOperationException {

        if (showPassCheckBox.isSelected()) {

            passwordTxt.setText(showPassTextTxt.getText());

        }

        String nameText = nameTxt.getText();
        String emailText = emailTxt.getText().trim();
        String usernameInput = usernameTxt.getText();
        String passwordInput = passwordTxt.getText();
        boolean emailCharsThere = isEmailCharsThere(emailText);
        boolean usernameInputValid = validateUsernameInput(usernameInput);
        boolean passwordInputValid = validatePasswordInput(passwordInput);
        boolean nameOkay = false;
        boolean emailOkay = false;
        boolean usernameOkay = false;
        boolean passwordOkay = false;
        //boolean emailIsDuplicate = false;
        //boolean usernameIsDuplicate = false;
        boolean emailIsDuplicate = accountsDB.emailAlreadyInDB(emailText);
        boolean usernameIsDuplicate = accountsDB.usernameAlreadyInDB(usernameInput);

        if (nameTxt.getText().isEmpty()) {

            nameAlertLbl.setText("*You must enter a name.");

        } else if (nameTxt.getText().isEmpty() == false) {

            nameAlertLbl.setText("");
            nameOkay = true;

        }

        if ((emailTxt.getText().isEmpty()) || (emailCharsThere == false)) {

            emailAlertLbl.setText("*You must enter a valid email address.");

        } else if (emailIsDuplicate == true) {

            emailAlertLbl.setText("This email already has an associated account.");

        } else if ((emailTxt.getText().isEmpty() == false) && (emailCharsThere == true) && (emailIsDuplicate == false)) {

            emailAlertLbl.setText("");
            emailOkay = true;

        }

        if ((usernameTxt.getText().isEmpty()) || (usernameTxt.getText().length() < 7)) {

            usernameAlertLbl.setText("*Username must be atleast 7 characters long.");

        } else if (usernameTxt.getText().length() > 15) {

            usernameAlertLbl.setText("*Username cannot exceed 15 characters in length.");

        } else if (usernameInputValid == false) {

            usernameAlertLbl.setText("*Username cannot contain spaces or special characters (excluding ._-).");

        } else if (usernameIsDuplicate == true) {

            usernameAlertLbl.setText("*This username has already been taken.");

        } else if ((usernameTxt.getText().isEmpty() == false) && (usernameInputValid == true) && (usernameTxt.getText().length() >= 7) && (usernameTxt.getText().length() <= 15) && (usernameIsDuplicate == false)) {

            usernameAlertLbl.setText("");
            usernameOkay = true;

        }

        if ((passwordTxt.getText().isEmpty()) || (passwordTxt.getText().length() < 7)) {

            passwordAlertLbl.setText("*Password must be atleast 7 characters long.");

        } else if (passwordTxt.getText().length() > 15) {

            passwordAlertLbl.setText("*Password cannot exceed 15 characters in length.");

        } else if (passwordInputValid == false) {

            passwordAlertLbl.setText("*Password must contain atleast one upper and lowercase letter and one digit.");

        } else if ((passwordTxt.getText().isEmpty() == false) && (passwordInputValid == true) && (passwordTxt.getText().length() >= 7) && (passwordTxt.getText().length() <= 15)) {

            passwordAlertLbl.setText("");
            passwordOkay = true;

        }

        if ((nameOkay == true) && (emailOkay == true) && (usernameOkay == true) && (passwordOkay == true)) {

            String hashedPassword = PasswordStorage.createHash(passwordInput);

            accountsDB.addUser(nameText, emailText, usernameInput, hashedPassword);
            nameTxt.setText("");
            emailTxt.setText("");
            usernameTxt.setText("");
            passwordTxt.setText("");
            showPassTextTxt.setText("");
            successLbl.setText("*Account Successfully Created*");

        }

    }

    private boolean isEmailCharsThere(String input) {

        Pattern p = Pattern.compile("^[A-Za-z0-9._-]+(\\-[A-Za-z0-9_])*@" + "[A-Za-z0-9._-]+(\\.[A-Za-z0-9_])");
        Matcher m = p.matcher(input);

        return m.find();

    }

    private boolean validateUsernameInput(String input) {

        Pattern p = Pattern.compile("^[a-zA-Z0-9_.-]{6,16}$");
        Matcher m = p.matcher(input);

        return m.matches();

    }

    private boolean validatePasswordInput(String input) {

        Pattern p = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}$");
        Matcher m = p.matcher(input);

        return m.find();

    }

    public void close() {

        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();

    }

    public void showPassword() {

        if (showPassCheckBox.isSelected()) {

            passwordTxt.setVisible(false);
            showPassTextTxt.setText(passwordTxt.getText());
            showPassTextTxt.setVisible(true);

        } else {

            showPassTextTxt.setVisible(false);
            passwordTxt.setText(showPassTextTxt.getText());
            passwordTxt.setVisible(true);

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        SSLogo.setImage(new Image("file:src/SSLogoAgain.png"));
        backLogo.setImage(new Image("file:src/backarrow.png"));

    }

}
