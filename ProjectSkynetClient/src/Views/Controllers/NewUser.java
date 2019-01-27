package Views.Controllers;

import Handlers.Commands;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class NewUser {

    Commands cmd = new Commands();

    @FXML
    private TextField tf_Mail;
    @FXML
    private TextField tf_Username;
    @FXML
    private PasswordField pf_Password;
    @FXML
    private PasswordField pf_cPassword;
    @FXML
    private Button btn_Create;
    @FXML
    private Button btn_Cancel;

    public void Clicked_Create(ActionEvent actionEvent) {
        if (pf_Password.getText().equals(pf_cPassword.getText())) {
            Boolean respone = cmd.NewUserRequest(tf_Username.getText(), pf_Password.getText(), tf_Mail.getText());
            if (!respone) {
                Messagebox("Fail", "Fail to create new user", "Username taken");
            } else {
                Messagebox("New User Created", "New user is now created plaese login", "");
                Stage NewUser = (Stage) btn_Create.getScene().getWindow();
                NewUser.close();
            }

        } else {
            Messagebox("Fail", "Password does not match", "");
        }

    }

    public void Clicked_Cancel(ActionEvent actionEvent) {
        Stage NewUser = (Stage) btn_Cancel.getScene().getWindow();
        NewUser.close();
    }

    private void Messagebox(String Title, String HanderText, String Content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Title);
        alert.setHeaderText(HanderText);
        alert.setContentText(Content);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
            }
        });
    }
}
