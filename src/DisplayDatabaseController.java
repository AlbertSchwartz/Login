
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Albert
 */
public class DisplayDatabaseController implements Initializable {

    private LoginDatabase accountsDB;
    private final ObservableList<LoginProfile> accountsData = FXCollections.observableArrayList();
    
    @FXML
    private JFXButton closeBtn;
    @FXML
    private ImageView SSLogo;
    @FXML
    private TableView<LoginProfile> accountsTable;
    @FXML
    private TableColumn<LoginProfile, String> nameCol;
    @FXML
    private TableColumn<LoginProfile, String> emailCol;
    @FXML
    private TableColumn<LoginProfile, String> usernameCol;
    @FXML
    private TableColumn<LoginProfile, String> passwordCol;
    
    public void setDatabase(LoginDatabase dataBase) {

        accountsDB = dataBase;
        buildData();

    }
    
    public void close() {
    
    Stage stage = (Stage) closeBtn.getScene().getWindow();
    stage.close();
    
    }
    
    @FXML
    public void buildData() {
    
        try {
            accountsData.addAll(accountsDB.getDatabaseInfo());
        } catch (SQLException ex) {
            Logger.getLogger(DisplayDatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        SSLogo.setImage(new Image("file:src/SSLogoAgain.png"));
        
        
        nameCol.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        emailCol.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
        usernameCol.setCellValueFactory(cellData -> cellData.getValue().getUsernameProperty());
        passwordCol.setCellValueFactory(cellData -> cellData.getValue().getPasswordProperty());
        
        accountsTable.setItems(accountsData);
        
    }    
    
}
