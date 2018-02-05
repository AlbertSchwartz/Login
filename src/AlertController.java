
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Albert Schwartz
 */
public class AlertController implements Initializable {

    @FXML
    private ImageView logoImage;
    @FXML
    public Label error;

    public void setAlertText(String text) {
        
        error.setText(text);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        logoImage.setImage(new Image("file:src/SSLogo.png"));

    }

}
