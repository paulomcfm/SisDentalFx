package br.fipp.sisdentalfx.db.dals;

import br.fipp.sisdentalfx.db.entidades.Consulta;
import br.fipp.sisdentalfx.db.entidades.Dentista;
import br.fipp.sisdentalfx.db.entidades.Material;
import br.fipp.sisdentalfx.db.entidades.Paciente;
import br.fipp.sisdentalfx.db.util.DB;

import java.io.Console;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAL implements IDAL<Consulta>{

    @Override
    public boolean gravar(Consulta entidade) {
        String sql="INSERT INTO public.consulta(con_id, con_relato, con_data, con_horario, pac_id, den_id, con_efetivado) VALUES (default, '#1', '#2', #3, #4, #5, #6);";
        sql=sql.replace("#1",entidade.getRelato());
        sql=sql.replace("#2",entidade.getData().toString());
        sql=sql.replace("#3",""+entidade.getHorario());
        sql=sql.replace("#4",""+entidade.getPaciente().getId());
        sql=sql.replace("#5",""+entidade.getDentista().getId());
        sql=sql.replace("#6",""+entidade.isEfetivado());
        return DB.getCon().manipular(sql);
    }

    @Override
    public boolean alterar(Consulta entidade) {
        String sql="UPDATE consulta set con_relato='#1', con_efetivado=true WHERE con_id ="+entidade.getId();
        sql=sql.replace("#1",entidade.getRelato());
        if(DB.getCon().manipular(sql)){
            for(Consulta.ItemMat item : entidade.getMateriais()){
                DB.getCon().manipular(String.format("insert into cons_mat (mat_id, con_id, cm_quant) values (%d, %d, %d)",item.material().getId(), entidade.getId(), item.quant()));
            }
            for(Consulta.ItemProc item : entidade.getProcedimentos()){
                DB.getCon().manipular(String.format("insert into cons_proc (pro_id, con_id, cp_quant) values (%d, %d, %d)",item.procedimento().getId(), entidade.getId(), item.quant()));
            }
        }
        return true;
    }

    @Override
    public boolean apagar(Consulta entidade) {
        String sql = "DELETE FROM consulta WHERE con_id = " + entidade.getId();
        if (DB.getCon().manipular(sql)) {

            DB.getCon().manipular("DELETE FROM cons_mat WHERE con_id = " + entidade.getId());

            DB.getCon().manipular("DELETE FROM cons_proc WHERE con_id = " + entidade.getId());

            return true;
        }
        return false;
    }

    @Override
    public Consulta get(int id) {
        Consulta consulta=null;
        ResultSet rs=DB.getCon().consultar("select * from consulta where con_id="+id);
        try {
            if (rs.next()){
                Dentista dentista = (Dentista) new PessoaDAL().get(rs.getInt("den_id"),new Dentista());
                Paciente paciente = (Paciente) new PessoaDAL().get(rs.getInt("pac_id"),new Paciente());
                consulta = new Consulta(rs.getInt("con_id"), rs.getDate("con_data").toLocalDate(), rs.getInt("con_horario"),dentista, paciente,rs.getString("con_relato"),rs.getBoolean("con_efetivado"));

            }
                }
        catch (Exception e){  }
        return consulta;
    }
    public List<Consulta> get(Dentista dentista, LocalDate data) {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "select * from consulta where den_id=#1 and con_data='#2'";
        sql=sql.replace("#1",""+dentista.getId());
        sql=sql.replace("#2",data.toString());
        ResultSet rs = DB.getCon().consultar(sql);
        try{
            while(rs.next()){
                Paciente paciente = (Paciente) new PessoaDAL().get(rs.getInt("pac_id"),new Paciente());
                Consulta consulta = new Consulta(rs.getInt("con_id"), rs.getDate("con_data").toLocalDate(), rs.getInt("con_horario"),dentista, paciente,rs.getString("con_relato"),rs.getBoolean("con_efetivado"));
                String sql2 = "select * from cons_mat where con_id =" +consulta.getId();
                ResultSet rs2 = DB.getCon().consultar(sql2);
                while(rs2.next()){
                    Consulta.ItemMat item = new Consulta.ItemMat(rs2.getInt("cm_quant"), new MaterialDAL().get(rs2.getInt("mat_id")));
                    consulta.addMaterial(item);
                }
                String sql3 = "select * from cons_proc where con_id =" +consulta.getId();
                ResultSet rs3 = DB.getCon().consultar(sql3);
                while(rs3.next()){
                    Consulta.ItemProc item = new Consulta.ItemProc(rs3.getInt("cp_quant"), new ProcedimentoDAL().get(rs3.getInt("pro_id")));
                    consulta.addProcedimento(item);
                }
                consultas.add(consulta);
            }
        }catch (Exception e){
            System.out.printf(e.getMessage());
        }
        return consultas;
    }
    @Override
    public List<Consulta> get(String filtro) {
        return null;
    }
}
