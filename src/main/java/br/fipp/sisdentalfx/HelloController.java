package br.fipp.sisdentalfx;

import br.fipp.sisdentalfx.db.dals.PessoaDAL;
import br.fipp.sisdentalfx.db.entidades.Pessoa;
import br.fipp.sisdentalfx.db.entidades.Usuario;
import br.fipp.sisdentalfx.util.UIControl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public static BorderPane staticpainel;
    public BorderPane painel;
    public Button btHome;
    public Button btClose;
    public Label lbAcesso;
    public Button btHelp;
    public ImageView ivLogotipo;
    public TextField tf_usu;

    public TextField tf_senna;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staticpainel=painel;
        btHome.setTooltip(new Tooltip("Ir para o início"));
        btClose.setTooltip(new Tooltip("Fechar o sistema"));
        btHome.setDisable(true);
        Image img = new Image("file:src\\main\\resources\\br\\fipp\\sisdentalfx\\logotipo.png");
        ivLogotipo.setImage(img);

        lbAcesso.setOnMouseClicked(e->{
            try {
                btHome.setDisable(false);
                onHome(null);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    public void onHome(ActionEvent actionEvent) throws IOException {
        String usu = tf_usu.getText() ,senha=tf_senna.getText();
        List <Pessoa> usuarios = new PessoaDAL().get("uso_nome like '"+usu+"'", new Usuario());
        Usuario usuario;
        boolean logado=false;
        if(usuarios.size()>0){
            usuario=(Usuario)usuarios.get(0);
            if(usuario.getSenha().equals(senha)){
                UIControl.usuario=usuario.getNome();
                UIControl.nivel=usuario.getNivel();
                logado = true;
            }
        }
        if(logado){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
            fxmlLoader.load();
            staticpainel.setCenter(fxmlLoader.getRoot());
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login não efetuado.");
            alert.setHeaderText("Erro ao fazer login.");
            alert.setContentText("Usuário ou senha incorretos.");
            alert.showAndWait();
        }

    }
    public void onClose(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Finalizar o sistema?");
        if(alert.showAndWait().get() == ButtonType.OK)
            Platform.exit();
    }

    public void onHelp(ActionEvent actionEvent) throws IOException {
        File file= new File("help/exemplo.html");
        UIControl.abreHelp(file.toURI().toString());
        //Desktop.getDesktop().open(file);
    }
}