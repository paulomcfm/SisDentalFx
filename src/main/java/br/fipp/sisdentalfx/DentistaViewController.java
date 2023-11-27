package br.fipp.sisdentalfx;

import br.fipp.sisdentalfx.db.dals.PessoaDAL;
import br.fipp.sisdentalfx.db.entidades.Dentista;
import br.fipp.sisdentalfx.db.entidades.Paciente;
import br.fipp.sisdentalfx.db.util.DB;
import br.fipp.sisdentalfx.util.MaskFieldUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class DentistaViewController implements Initializable {

    @FXML
    private TextField tfCRO;

    @FXML
    private Button btCancelar;

    @FXML
    private Button btConfirmar;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfFone;

    @FXML
    private TextField tfID;

    @FXML
    private TextField tfNome;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()-> tfNome.requestFocus()); // executa ao finalizar o carregamento do formulário
        btCancelar.setOnAction(e->{((Control)e.getSource()).getScene().getWindow().hide();});
        btConfirmar.setOnAction(e->gravarDentista(e));
        MaskFieldUtil.foneField(tfFone);
        if(DentistaTableController.pessoa!=null){
            Dentista dentista = (Dentista) DentistaTableController.pessoa;
            tfID.setText(""+dentista.getId());
            tfNome.setText(dentista.getNome());
            tfCRO.setText(""+dentista.getCro());
            tfFone.setText(dentista.getFone());
            tfEmail.setText(dentista.getEmail());
        }
     }

    private void gravarDentista(ActionEvent e) {
        Dentista dentista;
        //validar antes
        dentista = new Dentista(tfNome.getText(),Integer.parseInt(tfCRO.getText()),tfFone.getText(),tfEmail.getText());
        // gravar um novo dentista
        if(DentistaTableController.pessoa==null) {
            if (new PessoaDAL().gravar(dentista) == false) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao gravar o dentista " + DB.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else {
            dentista.setId(Integer.parseInt(tfID.getText()));
            if (new PessoaDAL().alterar(dentista) == false) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao alterar o dentista " + DB.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        ((Control)e.getSource()).getScene().getWindow().hide();
    }
}






