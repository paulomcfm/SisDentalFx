package br.fipp.sisdentalfx;

import br.fipp.sisdentalfx.db.dals.PessoaDAL;
import br.fipp.sisdentalfx.db.entidades.Dentista;
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
        btConfirmar.setOnAction(e->gravarPaciente(e));
        MaskFieldUtil.foneField(tfFone);
     }

    private void gravarPaciente(ActionEvent e) {
        Dentista dentista;
        //validar antes
        dentista = new Dentista(tfNome.getText(),Integer.parseInt(tfCRO.getText()),tfFone.getText(),tfEmail.getText());
        // gravar um novo dentista
        if(new PessoaDAL().gravar(dentista)==false)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao gravar o dentista "+ DB.getCon().getMensagemErro());
            alert.showAndWait();
        }
        ((Control)e.getSource()).getScene().getWindow().hide();
    }
}






