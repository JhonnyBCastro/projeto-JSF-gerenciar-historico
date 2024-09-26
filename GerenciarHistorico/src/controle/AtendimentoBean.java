package controle;

import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import modelo.Atendimento;
import modelo.Endereco;
import modelo.Medico;
import modelo.Paciente;
import modelo.Situacao;
import service.AtendimentoService;
import service.MedicoService;
import service.PacienteService;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@ViewScoped
@ManagedBean
public class AtendimentoBean {

    @EJB
    private PacienteService pacienteService;

    @EJB
    private MedicoService medicoService;

    @EJB
    private AtendimentoService atendimentoService;

    private List<Medico> medicos;
    private List<Medico> medicosSelecionados;
    private Paciente paciente = new Paciente();
    private Atendimento atendimento;
    private boolean pacienteEncontrado;
    private Endereco endereco = new Endereco();

    @PostConstruct
    public void init() {
        medicos = medicoService.listAll();
        atendimento = new Atendimento();
        paciente = new Paciente();
        pacienteEncontrado = false;
        medicosSelecionados = new ArrayList<Medico>();
    }

    public void buscarPaciente() {
        if (paciente.getCpf() != null && !paciente.getCpf().trim().isEmpty()) {
            try {
                Paciente pacienteEncontrado = pacienteService.findByCpf(paciente.getCpf());
                if (pacienteEncontrado != null) {
                    this.paciente = pacienteEncontrado;
                    this.pacienteEncontrado = true;
                    FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Sucesso", "Paciente encontrado: " + paciente.getNomeCompleto()));
                } else {
                    this.paciente = new Paciente();
                    this.paciente.setCpf(paciente.getCpf());
                    this.pacienteEncontrado = false;
                    FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN, 
                        "Paciente não encontrado", 
                        "Um novo paciente será criado com o CPF fornecido."));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Erro", "Ocorreu um erro ao buscar o paciente: " + e.getMessage()));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Erro", "O CPF fornecido é inválido."));
        }
    }

    public void salvar() {
        try {
            List<Integer> numerosExistentes = atendimentoService.buscarNumerosExistentes();
            atendimento.gerarNumeroAtendimento(numerosExistentes); // Gera um número único
            atendimento.setPaciente(paciente);
            atendimento.setMedicos(medicosSelecionados);
            atendimento.setSituacao(Situacao.EM_ABERTO); // Define a situação como "Em Aberto"

            // Salva o atendimento
            atendimentoService.create(atendimento);

            // Mensagem de sucesso
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Atendimento gravado com sucesso. Número: " + atendimento.getNumero(), ""));
            if (medicosSelecionados == null || medicosSelecionados.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro", "É necessário selecionar pelo menos um médico."));
                return; // Sai do método se não houver médicos selecionados
            }
            limparFormulario();
        } catch (Exception e) {
            // Mensagem de erro
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Erro", "Ocorreu um erro ao gravar o atendimento: " + e.getMessage()));
        }
    }

    private void limparFormulario() {
        paciente = new Paciente();
        atendimento = new Atendimento();
        medicosSelecionados = null;
        pacienteEncontrado = false;
    }

	public PacienteService getPacienteService() {
		return pacienteService;
	}

	public void setPacienteService(PacienteService pacienteService) {
		this.pacienteService = pacienteService;
	}

	public MedicoService getMedicoService() {
		return medicoService;
	}

	public void setMedicoService(MedicoService medicoService) {
		this.medicoService = medicoService;
	}

	public AtendimentoService getAtendimentoService() {
		return atendimentoService;
	}

	public void setAtendimentoService(AtendimentoService atendimentoService) {
		this.atendimentoService = atendimentoService;
	}

	public List<Medico> getMedicos() {
		return medicos;
	}

	public void setMedicos(List<Medico> medicos) {
		this.medicos = medicos;
	}

	public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

	public boolean isPacienteEncontrado() {
		return pacienteEncontrado;
	}

	public void setPacienteEncontrado(boolean pacienteEncontrado) {
		this.pacienteEncontrado = pacienteEncontrado;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Medico> getMedicosSelecionados() {
		return medicosSelecionados;
	}

	public void setMedicosSelecionados(List<Medico> medicosSelecionados) {
		this.medicosSelecionados = medicosSelecionados;
	}
	
	
}
