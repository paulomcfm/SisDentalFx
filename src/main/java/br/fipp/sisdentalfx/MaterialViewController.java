package br.fipp.sisdentalfx;

import br.fipp.sisdentalfx.db.dals.MaterialDAL;
import br.fipp.sisdentalfx.db.dals.PessoaDAL;
import br.fipp.sisdentalfx.db.entidades.Material;
import br.fipp.sisdentalfx.db.entidades.Paciente;
import br.fipp.sisdentalfx.db.util.DB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MaterialViewController implements Initializable {

    public TextField tfID;
    @FXML
    private Button btCancelar;

    @FXML
    private Button btConfirmar;

    @FXML
    private TextField tfDescricao;

    @FXML
    private TextField tfPreco;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()-> tfDescricao.requestFocus()); // executa ao finalizar o carregamento do formulário
        btCancelar.setOnAction(e->{((Control)e.getSource()).getScene().getWindow().hide();});
        btConfirmar.setOnAction(e->gravarMaterial(e));
        if(MaterialTableController.material!=null){
            Material material = MaterialTableController.material;
            tfID.setText(""+material.getId());
            tfDescricao.setText(material.getDescricao());
            tfPreco.setText(String.valueOf(material.getPreco()));
        }
     }

    private void gravarMaterial(ActionEvent e) {
        Material material;
        //validar antes
        material = new Material(tfDescricao.getText(),Double.parseDouble(tfPreco.getText()));
        // gravar um novo material
        if(MaterialTableController.material==null){
            if(new MaterialDAL().gravar(material)==false)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao gravar o material "+ DB.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else{
            material.setId(Integer.parseInt(tfID.getText()));
            if(new MaterialDAL().alterar(material)==false)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao alterar o material "+ DB.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        ((Control)e.getSource()).getScene().getWindow().hide();
    }
}






