package br.fipp.sisdentalfx;

import br.fipp.sisdentalfx.db.dals.MaterialDAL;
import br.fipp.sisdentalfx.db.entidades.Material;
import br.fipp.sisdentalfx.util.UIControl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MaterialTableController implements Initializable {
    public TextField tfPesquisa;
    public Button btNovo;
    public TableView <Material> tabela;
    public TableColumn <Material,Integer> colID;
    public TableColumn <Material,String> colDescricao;
    public TableColumn <Material,Double> colPreco;
    public Button btClose;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btClose.setOnAction(e->{((Button)e.getSource()).getScene().getWindow().hide();});
        preencherTabela("");

    }

    private void preencherTabela(String filtro) {
        List<Material> materiais = new MaterialDAL().get(filtro);
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        tabela.setItems(FXCollections.observableArrayList(materiais));
    }

    public void onFiltrar(KeyEvent keyEvent) {
        String filtro=tfPesquisa.getText().toUpperCase();
        preencherTabela("upper(mat_desc) like '%"+filtro+"%'");
    }

    public void onNovoMaterial(ActionEvent actionEvent) {
        UIControl.abreModal("material-view.fxml");
        preencherTabela("");
    }

    public void onAlterar(ActionEvent actionEvent) {
    }

    public void onApagar(ActionEvent actionEvent) {
        if(tabela.getSelectionModel().getSelectedItem()!=null)
        {
            Material material=tabela.getSelectionModel().getSelectedItem();
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Posso apagar definitivamente o material "+material.getDescricao());
            if(alert.showAndWait().get()==ButtonType.OK) {
                new MaterialDAL().apagar(material);
                preencherTabela("");
            }
        }
    }
}
