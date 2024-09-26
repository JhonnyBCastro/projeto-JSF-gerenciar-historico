package controle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Endereco;
import modelo.Medico;
import service.EnderecoService;
import service.MedicoService;

@ViewScoped
@ManagedBean
public class MedicoBean {

	@EJB
	private MedicoService medicoService;

	@EJB
	private EnderecoService enderecoService;
	
	private Medico medico;
	private List<Medico> medicos = new ArrayList<Medico>();
	

	@PostConstruct
	public void iniciar() {
		medico = new Medico();
        medico.setEndereco(new Endereco());
		atualizarListaMedico();
	}

	public void atualizarListaMedico() {
		medicos = medicoService.listAll();
		Collections.sort(medicos, new Comparator<Medico>() {
			@Override
			public int compare(Medico m1, Medico m2) {
				return m1.getPrimeiroNome().compareToIgnoreCase(m2.getPrimeiroNome());
			}
		});
	}
	
	public void carregarMaisInfo(Medico m) {
		medico = medicoService.buscarMedico(m.getId());
	}

	public void gravar() {
		String msg = "";

		if (medico.getId() == null) {
			medicoService.create(medico);
			msg = "Medico cadastrado";
		} else {
			medicoService.merge(medico);
			msg = "Medico editado";
		}

		FacesContext.getCurrentInstance().addMessage("", new FacesMessage(msg));
		medico = new Medico();
		medico.setEndereco(new Endereco());
		atualizarListaMedico();

	}

	public void editarMedico(Medico editM) {
		medico = medicoService.buscarMedico(editM.getId());
	}

	public void deletarMedico(Medico delM) {
		String msg = "";
		try {
			msg = "Medico deletado com sucesso";
			medicoService.remove(delM);
			atualizarListaMedico();
		}catch (Exception e) {
			msg = "Nao foi possivel deletar o medico";			
		}
		FacesContext.getCurrentInstance().addMessage("", new FacesMessage(msg));
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public List<Medico> getMedicos() {
		return medicos;
	}

	public void setMedicos(List<Medico> medicos) {
		this.medicos = medicos;
	}

}
