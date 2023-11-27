package br.fipp.sisdentalfx;

import br.fipp.sisdentalfx.db.dals.PessoaDAL;
import br.fipp.sisdentalfx.db.dals.ProcedimentoDAL;
import br.fipp.sisdentalfx.db.entidades.Paciente;
import br.fipp.sisdentalfx.db.entidades.Procedimento;
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

public class ProcedimentoViewController implements Initializable {

    public TextField tfID;
    @FXML
    private Button btCancelar;

    @FXML
    private Button btConfirmar;

    @FXML
    private TextField tfDescricao;

    @FXML
    private TextField tfTempo;

    @FXML
    private TextField tfValor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()-> tfDescricao.requestFocus()); // executa ao finalizar o carregamento do formulário
        btCancelar.setOnAction(e->{((Control)e.getSource()).getScene().getWindow().hide();});
        btConfirmar.setOnAction(e->gravarProcedimento(e));
        if(ProcedimentoTableController.procedimento!=null){
            Procedimento procedimento = ProcedimentoTableController.procedimento;
            tfID.setText(""+procedimento.getId());
            tfDescricao.setText(procedimento.getDescricao());
            tfTempo.setText(""+procedimento.getTempo());
            tfValor.setText(""+procedimento.getValor());
        }
     }

    private void gravarProcedimento(ActionEvent e) {
        Procedimento procedimento;
        //validar antes
        procedimento = new Procedimento(tfDescricao.getText(),Integer.parseInt(tfTempo.getText()),Double.parseDouble(tfValor.getText()));
        // gravar um novo procedimento
        if(ProcedimentoTableController.procedimento==null){
            if(new ProcedimentoDAL().gravar(procedimento)==false)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao gravar o procedimento "+ DB.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        else{
            procedimento.setId(Integer.parseInt(tfID.getText()));
            if(new ProcedimentoDAL().alterar(procedimento)==false)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao alterar o procedimento "+ DB.getCon().getMensagemErro());
                alert.showAndWait();
            }
        }
        ((Control)e.getSource()).getScene().getWindow().hide();
    }
}






