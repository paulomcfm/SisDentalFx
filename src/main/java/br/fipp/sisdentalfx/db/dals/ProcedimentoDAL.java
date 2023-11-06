package br.fipp.sisdentalfx.db.dals;

import br.fipp.sisdentalfx.db.entidades.Material;
import br.fipp.sisdentalfx.db.entidades.Procedimento;
import br.fipp.sisdentalfx.db.util.DB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProcedimentoDAL implements IDAL<Procedimento>{
    @Override
    public boolean gravar(Procedimento procedimento) {
        String sql="insert into procedimento values (default,'#1',#2, #3)";
        sql=sql.replace("#1",procedimento.getDescricao());
        sql=sql.replace("#2",""+procedimento.getTempo());
        sql=sql.replace("#3",""+procedimento.getValor());
        return DB.getCon().manipular(sql);
    }

    @Override
    public boolean alterar(Procedimento procedimento) {
        String sql="update procedimento set pro_desc='#1', pro_tempo=#2, pro_valor=#3 where pro_id="+procedimento.getId();
        sql=sql.replace("#1",procedimento.getDescricao());
        sql=sql.replace("#2",""+procedimento.getTempo());
        sql=sql.replace("#3",""+procedimento.getValor());
        return DB.getCon().manipular(sql);
    }

    @Override
    public boolean apagar(Procedimento procedimento) {
        return DB.getCon().manipular("delete from procedimento where pro_id="+procedimento.getId());
    }

    @Override
    public Procedimento get(int id) {
        Procedimento procedimento=null;
        ResultSet rs=DB.getCon().consultar("select * from procedimento where pro_id="+id);
        try {
            if (rs.next())
                procedimento = new Procedimento(rs.getInt("pro_id"), rs.getString("pro_desc"),
                                                rs.getInt("pro_tempo"), rs.getDouble("pro_valor"));
        }
        catch (Exception e){  }
        return procedimento;
    }

    @Override
    public List<Procedimento> get(String filtro) {
        List <Procedimento> procedimentos=new ArrayList<>();
        String sql="select * from procedimento";
        if(!filtro.isEmpty())
            sql+=" where "+filtro;
        ResultSet rs=DB.getCon().consultar(sql);
        try {
            while (rs.next())
                procedimentos.add(new Procedimento(rs.getInt("pro_id"), rs.getString("pro_desc"),
                                                   rs.getInt("pro_tempo"), rs.getDouble("pro_valor")));
        }
        catch (Exception e){  }
        return procedimentos;
    }
}
