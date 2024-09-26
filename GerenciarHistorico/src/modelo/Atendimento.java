package modelo;

import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Atendimento {

    @Id
    @GeneratedValue
    private Long id;

    private Integer numero;
    private Date dataEntrada;
    private Situacao situacao;
    private String parecer;
    
    @OneToOne
    private Paciente paciente;
    
    @ManyToMany
    private List<Medico> medicos;

    public Atendimento() {
        // Construtor sem l�gica de gera��o de n�mero
    }

    public void gerarNumeroAtendimento(List<Integer> numerosExistentes) {
        Random numAtendimento = new Random();
        do {
            numero = numAtendimento.nextInt(10000);
        } while (numerosExistentes.contains(numero)); // Verifica se o n�mero j� existe
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public String getParecer() {
        return parecer;
    }

    public void setParecer(String parecer) {
        this.parecer = parecer;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public void setMedicos(List<Medico> medicos) {
        this.medicos = medicos;
    }
}
