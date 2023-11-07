package br.fipp.sisdentalfx.db.entidades;

public class Paciente extends Pessoa{
    private String cpf, cep, rua, numero, bairro, cidade, uf, fone, email, histo;

    public Paciente(int id, String nome, String cpf, String cep, String rua, String numero, String bairro, String cidade, String uf, String fone, String email, String histo) {
        super(id, nome);
        this.cpf = cpf;
        this.cep = cep;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.fone = fone;
        this.email = email;
        this.histo = histo;
    }

    public Paciente() {
        this(0,"","","","","","","","","","","");
    }

    public Paciente(String nome, String cpf, String cep, String rua, String numero, String bairro, String cidade, String uf, String fone, String email, String histo) {
        this(0,nome,cpf,cep,rua,numero,bairro,cidade,uf,fone,email,histo);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHisto() {
        return histo;
    }

    public void setHisto(String histo) {
        this.histo = histo;
    }

    @Override
    public String toString() {
        return getNome();
    }
}
