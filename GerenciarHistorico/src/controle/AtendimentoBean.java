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
	private boolean exibirCamposCadastro;
	private Paciente pacienteParaEncontrar = new Paciente();

	@PostConstruct
	public void init() {
		medicos = medicoService.listAll();
		atendimento = new Atendimento();
		paciente = new Paciente();
		exibirCamposCadastro = false;
		paciente.setEndereco(new Endereco());
		medicosSelecionados = new ArrayList<Medico>();
	}

	public void buscarPaciente() {
		String msg = "";
		Paciente reserva;
		Atendimento ultimoAtendimento;
		if (pacienteParaEncontrar.getCpf() != null && !pacienteParaEncontrar.getCpf().trim().isEmpty()) {
			try {
				reserva = pacienteParaEncontrar;
				pacienteParaEncontrar = pacienteService.findByCpf(pacienteParaEncontrar.getCpf());
				if (pacienteParaEncontrar != null) {
					paciente = pacienteParaEncontrar;
					ultimoAtendimento = atendimentoService.buscarAtendimentoPaciente(paciente.getId());

					/*
					 * descomentar esse pedaco caso queira trazer os dados do ultimo atendimento
					 */
					// atendimento = ultimoAtendimento;
					exibirCamposCadastro = true;
					medicosSelecionados = atendimentoService.buscarMedicos(atendimento.getId());
					msg = "Paciente encontrado: " + paciente.getNomeCompleto();
					pacienteParaEncontrar = new Paciente();
					FacesContext.getCurrentInstance().addMessage("", new FacesMessage(msg));

				} else {
					paciente = new Paciente();
					paciente.setCpf(reserva.getCpf());
					paciente.setEndereco(new Endereco());
					pacienteParaEncontrar = new Paciente();
					exibirCamposCadastro = true;
					msg = "Paciente nao encontrado, um novo paciente sera criado com o CPF fornecido.";
					FacesContext.getCurrentInstance().addMessage("", new FacesMessage(msg));
				}
			} catch (Exception e) {
				msg = "Ocorreu um erro ao buscar o paciente. ERRO: " + e.getMessage();
				FacesMessage message = new FacesMessage();
				FacesContext.getCurrentInstance().addMessage("", new FacesMessage(msg));

			}
		} else {
			msg = "Erro: O CPF fornecido e invalido.";
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

					msg = "Atendimento gravado com sucesso. Numero: " + atendimento.getNumero();
					FacesContext.getCurrentInstance().addMessage("", new FacesMessage(msg));

					paciente = new Paciente();
					paciente.setEndereco(new Endereco());
					medicosSelecionados = new ArrayList<Medico>();
					atendimento = new Atendimento();
				} else {
					List<Integer> numerosExistentes = atendimentoService.buscarNumerosExistentes();
					atendimento.gerarNumeroAtendimento(numerosExistentes); // Gera um n�mero �nico
					atendimento.setPaciente(paciente);
					atendimento.setMedicos(medicosSelecionados);
					atendimento.setSituacao(Situacao.EM_ABERTO);

					atendimentoService.merge(atendimento);
					paciente = new Paciente();
					atendimento = new Atendimento();
					medicosSelecionados = new ArrayList<Medico>();
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

	public boolean isExibirCamposCadastro() {
		return exibirCamposCadastro;
	}

	public void setExibirCamposCadastro(boolean exibirCamposCadastro) {
		this.exibirCamposCadastro = exibirCamposCadastro;
	}

	public Paciente getPacienteParaEncontrar() {
		return pacienteParaEncontrar;
	}

	public void setPacienteParaEncontrar(Paciente pacienteParaEncontrar) {
		this.pacienteParaEncontrar = pacienteParaEncontrar;
	}

	public List<Medico> getMedicosSelecionados() {
		return medicosSelecionados;
	}

	public void setMedicosSelecionados(List<Medico> medicosSelecionados) {
		this.medicosSelecionados = medicosSelecionados;
	}

}
