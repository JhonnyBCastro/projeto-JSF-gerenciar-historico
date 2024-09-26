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

	@PostConstruct
	public void init() {
		medicos = medicoService.listAll();
		atendimento = new Atendimento();
		paciente = new Paciente();
		pacienteEncontrado = false;
		paciente.setEndereco(new Endereco());
		medicosSelecionados = new ArrayList<Medico>();
	}

	public void buscarPaciente() {
		String msg = "";
		if (paciente.getCpf() != null && !paciente.getCpf().trim().isEmpty()) {
			try {

				Paciente pacienteEncontrado = pacienteService.findByCpf(paciente.getCpf());
				if (pacienteEncontrado != null) {
					this.paciente = pacienteEncontrado;
					this.pacienteEncontrado = true;
					msg = "Paciente encontrado" + paciente.getNomeCompleto();
					FacesContext.getCurrentInstance().addMessage("", new FacesMessage(msg));

				} else {
					this.paciente = new Paciente();
					this.paciente.setCpf(paciente.getCpf());
					paciente.setEndereco(new Endereco());
					this.pacienteEncontrado = true;
					msg = "Paciente nao encontrado, um novo paciente sera criado com o CPF fornecido.";
					FacesContext.getCurrentInstance().addMessage("", new FacesMessage(msg));
				}
			} catch (Exception e) {
				msg = "Ocorreu um erro ao buscar o paciente. ERRO: " + e.getMessage();
				FacesContext.getCurrentInstance().addMessage("", new FacesMessage(msg));

			}
		} else {
			msg = "Erro: O CPF fornecido � inv�lido.";
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(msg));
		}
	}

	public void salvar() {
		String msg = "";
		try {

			if (medicosSelecionados == null || medicosSelecionados.isEmpty()) {
				msg = "E necesserio selecionar pelo menos um medico.";
				FacesContext.getCurrentInstance().addMessage("", new FacesMessage(msg));
				return;
			} else {
				if (paciente.getId() == null) {
					List<Integer> numerosExistentes = atendimentoService.buscarNumerosExistentes();
					atendimento.gerarNumeroAtendimento(numerosExistentes); // Gera um n�mero �nico
					atendimento.setPaciente(paciente);
					atendimento.setMedicos(medicosSelecionados);
					atendimento.setSituacao(Situacao.EM_ABERTO); // Define a situa��o como "Em Aberto"

					atendimentoService.create(atendimento);
					paciente = new Paciente();
					paciente.setEndereco(new Endereco());

					msg = "Atendimento gravado com sucesso. Numero: " + atendimento.getNumero();
					FacesContext.getCurrentInstance().addMessage("", new FacesMessage(msg));
				} else {
					List<Integer> numerosExistentes = atendimentoService.buscarNumerosExistentes();
					atendimento.gerarNumeroAtendimento(numerosExistentes); // Gera um n�mero �nico
					atendimento.setPaciente(paciente);
					atendimento.setMedicos(medicosSelecionados);
					atendimento.setSituacao(Situacao.EM_ABERTO);
					
					atendimentoService.merge(atendimento);
					msg = "Atendimento atualizado com sucesso. Numero: " + atendimento.getNumero();
					FacesContext.getCurrentInstance().addMessage("", new FacesMessage(msg));
				}
			}

		} catch (Exception e) {
			msg = "Ocorreu um erro ao gravar o atendimento: " + e.getMessage();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(msg));
		}

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

	public List<Medico> getMedicosSelecionados() {
		return medicosSelecionados;
	}

	public void setMedicosSelecionados(List<Medico> medicosSelecionados) {
		this.medicosSelecionados = medicosSelecionados;
	}

}
