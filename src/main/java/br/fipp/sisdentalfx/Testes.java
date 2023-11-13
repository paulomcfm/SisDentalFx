package br.fipp.sisdentalfx;

import br.fipp.sisdentalfx.db.dals.ConsultaDAL;
import br.fipp.sisdentalfx.db.dals.MaterialDAL;
import br.fipp.sisdentalfx.db.dals.PessoaDAL;
import br.fipp.sisdentalfx.db.dals.ProcedimentoDAL;
import br.fipp.sisdentalfx.db.entidades.*;
import br.fipp.sisdentalfx.db.util.DB;

import java.time.LocalDate;
import java.util.List;

public class Testes {
    public static void main(String[] args) {
        /* conexão com o banco*/
        if(DB.conectar()) {
//            MaterialDAL dal = new MaterialDAL();
//            dal.gravar(new Material("mascara",5.50));
//            List<Material> lista = dal.get("");
//            for (Material material : lista)
//                System.out.println(material);
           //PessoaDAL dal=new PessoaDAL();
           //if(!dal.gravar(new Dentista("Teodoro",321,"18333999","teo@email.com")))
           //    DB.getCon().getMensagemErro();
           //if(!dal.gravar(new Paciente("Gustavo Lima","123254","","","","","","","","","")))
           //    DB.getCon().getMensagemErro();]
            //if(!dal.gravar(new Usuario("Syrley",1,"123")))
            //    DB.getCon().getMensagemErro();

            // System.out.println(dal.get(2,new Paciente()).getNome());
            //List<Pessoa> lista = dal.get("",new Paciente());
            //for (Pessoa p : lista)
             //   System.out.println(p.getNome());
            //new ProcedimentoDAL().gravar(new Procedimento("teste",10,10.3));
            //new ConsultaDAL().gravar(new Consulta(1,LocalDate.now(),9,
              //      new Dentista(1,"",0,"",""),
                //    (Paciente) new PessoaDAL().get(1,new Paciente()),"",false));/
            Dentista dentista = (Dentista) new PessoaDAL().get(1, new Dentista());
            List<Consulta> consulta = new ConsultaDAL().get(dentista, LocalDate.now());
            System.out.println(consulta.get(0).getPaciente().getNome());
            List <Consulta.ItemProc> procedimentos = consulta.get(0).getProcedimentos(); //DEU ERRADO
            //List <Consulta.ItemMat> materiais = consulta.get(0).getMateriais();
            for(Consulta.ItemProc item : procedimentos){
                System.out.println(item.quant()+" "+item.procedimento().getDescricao());
            }
            //for(Consulta.ItemMat item : materiais){
              //  System.out.println(item.quant()+" "+item.material().getDescricao());
            //}
        }
        else
            System.out.println(DB.getCon().getMensagemErro());
    }

}
