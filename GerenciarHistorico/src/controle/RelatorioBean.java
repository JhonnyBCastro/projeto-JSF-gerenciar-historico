package controle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Atendimento;
import modelo.Medico;
import service.AtendimentoService;
import service.MedicoService;

@ManagedBean
@ViewScoped
public class RelatorioBean {

    @EJB
    private AtendimentoService atendimentoService;
    
    @EJB
    private MedicoService medicoService;
    
    private List<Medico> medicos;
    private List<Atendimento> atendimentos;
    private Atendimento medicoAtendimento = new Atendimento();
    private Date inicio;
    private Date fim;
    private Long idMedico;
    private String parecer;
    private Atendimento atendimentoSelecionado;
    private Boolean todosMedicos;
    
    @PostConstruct
    public void init() {
        medicos = new ArrayList<>();
        atendimentos = new ArrayList<>();
        idMedico = 0L;
        todosMedicos = false;
        carregarAtendimentos();
        carregarMedicos();
    }
    
    private void carregarAtendimentos() {
        try {
            atendimentos = atendimentoService.listarTodosAtendimentos();
        } catch (Exception e) {
            addErrorMessage("Erro ao carregar atendimentos: " + e.getMessage());
        }
    }
    
    public void carregarMedico(Atendimento a) {
        try {
            medicoAtendimento = medicoService.buscarAtendComMedico(a.getId());
        } catch (Exception e) {
            addErrorMessage("Erro ao carregar médicos do atendimento: " + e.getMessage());
        }
    }
    
    private void carregarMedicos() {
        try {
            medicos = medicoService.listarTodosOsMedicos();
        } catch (Exception e) {
            addErrorMessage("Erro ao carregar médicos: " + e.getMessage());
        }
    }
    
    public void filtrar() {
        try {
            if (inicio != null && fim != null && inicio.after(fim)) {
                addErrorMessage("Data de início não pode ser posterior à data de fim.");
                return;
            }

            if (inicio == null && fim == null && (todosMedicos || idMedico == null || idMedico == 0)) {
                atendimentos = atendimentoService.listarTodosAtendimentos();
            } else if (todosMedicos || idMedico == null || idMedico == 0) {
                atendimentos = atendimentoService.filtrarAtendimentos(inicio, fim, null);
            } else {
                atendimentos = atendimentoService.filtrarAtendimentos(inicio, fim, idMedico);
            }

            if (atendimentos.isEmpty()) {
                addInfoMessage("Nenhum atendimento encontrado!");
            }
        } catch (Exception e) {
            addErrorMessage("Erro ao filtrar atendimentos: " + e.getMessage());
        }
    }

    private void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    private void addInfoMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }

    public void excluir(Atendimento atendimento) {
        this.atendimentoSelecionado = atendimento;
    }

    public void confirmarExclusao() {
        try {
            atendimentoService.excluirAtendimento(atendimentoSelecionado.getId());
            atendimentos.remove(atendimentoSelecionado);
            addInfoMessage("Atendimento excluído com sucesso!");
        } catch (IllegalStateException e) {
            addErrorMessage(e.getMessage());
        } catch (Exception e) {
            addErrorMessage("Erro ao excluir atendimento!");
        }
    }
    
    public void finalizar(Atendimento atendimento) {
        this.atendimentoSelecionado = atendimento;
        this.parecer = "";
    }

    public void confirmarFinalizacao() {
        if (parecer == null || parecer.trim().isEmpty()) {
            addErrorMessage("O parecer é obrigatório!");
            return;
        }
        try {
            atendimentoService.finalizarAtendimento(atendimentoSelecionado.getId(), parecer);
            addInfoMessage("Atendimento finalizado com sucesso!");
            filtrar();
            parecer = null;
        } catch (IllegalArgumentException e) {
            addErrorMessage(e.getMessage());
        }
    }

	public AtendimentoService getAtendimentoService() {
		return atendimentoService;
	}

	public void setAtendimentoService(AtendimentoService atendimentoService) {
		this.atendimentoService = atendimentoService;
	}

	public MedicoService getMedicoService() {
		return medicoService;
	}

	public void setMedicoService(MedicoService medicoService) {
		this.medicoService = medicoService;
	}

	public List<Medico> getMedicos() {
		return medicos;
	}

	public void setMedicos(List<Medico> medicos) {
		this.medicos = medicos;
	}

	public List<Atendimento> getAtendimentos() {
		return atendimentos;
	}

	public void setAtendimentos(List<Atendimento> atendimentos) {
		this.atendimentos = atendimentos;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public Long getIdMedico() {
		return idMedico;
	}

	public void setIdMedico(Long idMedico) {
		this.idMedico = idMedico;
	}

	public String getParecer() {
		return parecer;
	}

	public void setParecer(String parecer) {
		this.parecer = parecer;
	}

	public Atendimento getAtendimentoSelecionado() {
		return atendimentoSelecionado;
	}

	public void setAtendimentoSelecionado(Atendimento atendimentoSelecionado) {
		this.atendimentoSelecionado = atendimentoSelecionado;
	}

	public Boolean getTodosMedicos() {
		return todosMedicos;
	}

	public void setTodosMedicos(Boolean todosMedicos) {
		this.todosMedicos = todosMedicos;
	}

	public Atendimento getMedicoAtendimento() {
		return medicoAtendimento;
	}

	public void setMedicoAtendimento(Atendimento medicoAtendimento) {
		this.medicoAtendimento = medicoAtendimento;
	}
    
	
}
