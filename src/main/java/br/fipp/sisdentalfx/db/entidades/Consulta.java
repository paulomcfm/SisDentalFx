package br.fipp.sisdentalfx.db.entidades;

import java.time.LocalDate;

public class Consulta {
    private int id;
    private LocalDate data;
    private int horario;
    private Dentista dentista;
    private Paciente paciente;
    private String relato;
    private boolean efetivado;
}
