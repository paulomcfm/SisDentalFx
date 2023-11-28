package br.fipp.sisdentalfx;

import br.fipp.sisdentalfx.db.dals.ConsultaDAL;
import br.fipp.sisdentalfx.db.dals.MaterialDAL;
import br.fipp.sisdentalfx.db.dals.PessoaDAL;
import br.fipp.sisdentalfx.db.dals.ProcedimentoDAL;
import br.fipp.sisdentalfx.db.entidades.*;
import br.fipp.sisdentalfx.util.UIControl;
import javafx.beans.property.SimpleIntegerProperty;
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
    public Spinner<Integer> sQtdMaterial;
    public TableView<Mat> tvMaterial;
    public TableColumn<Mat, Mat> colMaterial;
    public TableColumn<Mat, Integer> colQtdMat;
    public ComboBox<Procedimento> cbProcedimento;
    public Spinner<Integer>  sQtdProcedimento;
    public TableView<Proc> tvProcedimento;
    public TableColumn<Proc, Proc> colProcedimento;
    public TableColumn<Proc, Integer> colQtdProc;
    public ComboBox<Dentista> cbDentista;

    private List<Mat> materiais = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dpDiaConsulta.setValue(LocalDate.now());
        List<Pessoa> dentistas = new PessoaDAL().get("", new Dentista());
        for(Pessoa dentista:dentistas){
            cbDentista.getItems().add((Dentista)dentista);
        }
        List<Material> materiais = new MaterialDAL().get("");
        for(Material material:materiais){
            cbMaterial.getItems().add(material);
        }
        List<Procedimento> procedimentos = new ProcedimentoDAL().get("");
        for(Procedimento procedimento:procedimentos){
            cbProcedimento.getItems().add(procedimento);
        }
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
        List<Consulta> consultas = new ConsultaDAL().get(cbDentista.getSelectionModel().getSelectedItem(),dpDiaConsulta.getValue());
        List<Horario> horarios = new ArrayList<>();
        for(int hora=8;hora<18;hora++){
            horarios.add(new Horario(hora, new Paciente()));
        }
        for(Consulta con : consultas){
            horarios.set(con.getHorario()-8,new Horario(con.getHorario(),con.getPaciente()));
        }
        tvHorario.setItems(FXCollections.observableArrayList(horarios));
    }
    public void onTrocouData(ActionEvent actionEvent) {
        preencherHorarios();
    }
    public void onTrocouDentista(ActionEvent actionEvent) {
        preencherHorarios();
    }

    public void onSelecionarAtendimento(MouseEvent mouseEvent) {
        tvHorario.getSelectionModel().getSelectedItem().getPaciente();
    }
    public class Mat{
        private String descricao;
        private int qtd;

        public Mat(String descricao, int qtd) {
            this.descricao=descricao;
            this.qtd=qtd;
        }
        public String getDescricao() {
            return descricao;
        }

        public int getQtd() {
            return qtd;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public void setQtd(int qtd) {
            this.qtd = qtd;
        }
    }
    public void onMaisMat(ActionEvent actionEvent) {
        colMaterial.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colQtdMat.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQtd()).asObject());
        Material material = cbMaterial.getValue();
        Mat mat = new Mat(material.getDescricao(), sQtdMaterial.getValue());
        materiais.add(mat);
        tvMaterial.setItems(FXCollections.observableArrayList(materiais));
    }

    public void onMenosMat(ActionEvent actionEvent) {
    }
    public class Proc{
        private String descricao;
        private int qtd;

        public Proc(String descricao, int qtd) {
            this.descricao=descricao;
            this.qtd=qtd;
        }
        public String getDescricao() {
            return descricao;
        }

        public int getQtd() {
            return qtd;
        }
    }
    public void onMaisProc(ActionEvent actionEvent) {

    }
    public void onMenosProc(ActionEvent actionEvent) {
    }

    public void onConfirmar(ActionEvent actionEvent) {
    }

    public void onCancelar(ActionEvent actionEvent) {
    }
    public void onTrocouMaterial(ActionEvent actionEvent) {
        sQtdMaterial.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
    }
    public void onTrocouProcedimento(ActionEvent actionEvent) {
        sQtdProcedimento.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
    }


}
