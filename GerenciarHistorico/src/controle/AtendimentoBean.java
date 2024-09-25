package controle;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import modelo.Medico;
import service.EnderecoService;
import service.MedicoService;
import service.PacienteService;

@ViewScoped
@ManagedBean
public class AtendimentoBean {
	
	@EJB
	private PacienteService pacienteService;
	
	@EJB
	private MedicoService medicoService;
	
	@EJB
	private EnderecoService enderecoService;
	
	private List<Medico> medicos = new ArrayList<Medico>();
	

	public List<Medico> getMedicos() {
		return medicos;
	}

	public void setMedicos(List<Medico> medicos) {
		this.medicos = medicos;
	}
	
}
