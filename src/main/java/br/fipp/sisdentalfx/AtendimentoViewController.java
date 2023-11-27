package br.fipp.sisdentalfx;

import br.fipp.sisdentalfx.db.dals.ConsultaDAL;
import br.fipp.sisdentalfx.db.dals.PessoaDAL;
import br.fipp.sisdentalfx.db.entidades.*;
import br.fipp.sisdentalfx.util.UIControl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AtendimentoViewController implements Initializable {
    public DatePicker dpDiaConsulta;
    public TableView<Horario> tvHorario;
    public TableColumn<Horario,Integer> colHora;
    public TableColumn<Horario, Paciente>  colPaciente;
    public TextArea taObservacoes;
    public ComboBox<Material> cbMaterial;
    public Spinner sQtdMaterial;
    public TableView<Material> tvMaterial;
    public TableColumn<Material, Material> colMaterial;
    public TableColumn<Material, Integer> colQtdMat;
    public ComboBox<Procedimento> cbProcedimento;
    public Spinner sQtdProcedimento;
    public TableView<Procedimento> tvProcedimento;
    public TableColumn<Procedimento, Procedimento> colProcedimento;
    public TableColumn<Procedimento, Integer> colQtdProc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dpDiaConsulta.setValue(LocalDate.now());
        preencherHorarios();
    }
    public void onPaciente(ActionEvent actionEvent) throws IOException {
        BoxBlur bb = new BoxBlur(15,15,10);
        dpDiaConsulta.getScene().getRoot().setEffect(bb); // aplicando efeito borrado no painel
        UIControl.abreModal("paciente-table-view.fxml");
        dpDiaConsulta.getScene().getRoot().setEffect(null);
    }

    public void onDentista(ActionEvent actionEvent) {
        BoxBlur bb = new BoxBlur(15,15,10);
        dpDiaConsulta.getScene().getRoot().setEffect(bb); // aplicando efeito borrado no painel
        UIControl.abreModal("dentista-table-view.fxml");
        dpDiaConsulta.getScene().getRoot().setEffect(null);
    }

    public void onMaterial(ActionEvent actionEvent) {
        BoxBlur bb = new BoxBlur(15,15,10);
        dpDiaConsulta.getScene().getRoot().setEffect(bb); // aplicando efeito borrado no painel
        UIControl.abreModal("material-table-view.fxml");
        dpDiaConsulta.getScene().getRoot().setEffect(null);
    }

    public void onProcedimento(ActionEvent actionEvent) {
        BoxBlur bb = new BoxBlur(15,15,10);
        dpDiaConsulta.getScene().getRoot().setEffect(bb); // aplicando efeito borrado no painel
        UIControl.abreModal("procedimento-table-view.fxml");
        dpDiaConsulta.getScene().getRoot().setEffect(null);
    }
    public void preencherHorarios(){

        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));
        Dentista dentista = (Dentista) new PessoaDAL().get("den_nome like %"+UIControl.usuario+"%",new Dentista());
        List<Consulta> consultas = new ConsultaDAL().get(dentista,dpDiaConsulta.getValue());
        List<Horario> horarios = new ArrayList<>();
        for(int hora=8;hora<18;hora++){
            horarios.add(new Horario(hora, new Paciente()));
        }
        for(Consulta con : consultas){
            horarios.set(con.getHorario()-8,new Horario(con.getHorario(),con.getPaciente()));
        }
        tvHorario.setItems(FXCollections.observableArrayList(horarios));
    }
    public void onSelecionarProcedimento(MouseEvent mouseEvent) {
    }

    public void onSelecionarMaterial(MouseEvent mouseEvent) {
    }

    public void onSelecionarDentista(MouseEvent mouseEvent) {
    }

    public void onTrocouData(ActionEvent actionEvent) {
        preencherHorarios();
    }

    public void onMaisMat(ActionEvent actionEvent) {
    }

    public void onMenosMat(ActionEvent actionEvent) {
    }

    public void onMaisProc(ActionEvent actionEvent) {
    }
    public void onMenosProc(ActionEvent actionEvent) {
    }

    public void onConfirmar(ActionEvent actionEvent) {
    }

    public void onCancelar(ActionEvent actionEvent) {
    }
}
