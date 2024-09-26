package controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Endereco;
import modelo.Medico;
import modelo.Paciente;
import service.EnderecoService;
import service.PacienteService;

@ViewScoped
@ManagedBean
public class PacienteBean {
	@EJB
	private PacienteService pacienteService;
	@EJB
	private EnderecoService enderecoService;
	
	private Paciente paciente;
	private List<Paciente> pacientes = new ArrayList<Paciente>();
	
	@PostConstruct
	public void iniciar() {
		paciente = new Paciente();
		paciente.setEndereco(new Endereco());
		atualizarListaPaciente();
	}
	
	public void atualizarListaPaciente() {
		pacientes = pacienteService.listAll();
	}
	
	public void gravar() {
		String msg = "";

		if (paciente.getId() == null) {
			pacienteService.create(paciente);
			msg = "Medico cadastrado";
		} else {
			pacienteService.merge(paciente);
			msg = "Medico editado";
		}

		FacesContext.getCurrentInstance().addMessage("", new FacesMessage(msg));
		paciente = new Paciente();
		paciente.setEndereco(new Endereco());
		atualizarListaPaciente();

	}

	public PacienteService getPacienteService() {
		return pacienteService;
	}

	public void setPacienteService(PacienteService pacienteService) {
		this.pacienteService = pacienteService;
	}

	public EnderecoService getEnderecoService() {
		return enderecoService;
	}

	public void setEnderecoService(EnderecoService enderecoService) {
		this.enderecoService = enderecoService;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}
	
	
}
