package service;

import modelo.Atendimento;
import modelo.Medico;
import modelo.Situacao;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
public class AtendimentoService extends GenericService<Atendimento> {

    public AtendimentoService() {
        super(Atendimento.class);
    }

    public List<Integer> buscarNumerosExistentes() {
    	final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
    	final CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
    	final Root<Atendimento> atendimentoRoot = criteriaQuery.from(Atendimento.class);
    	
    	criteriaQuery.select(atendimentoRoot.get("numero"));
    	    	
    	List<Integer> resultado = getEntityManager().createQuery(criteriaQuery).getResultList();
    	
        
        return resultado; 
    }

	public Atendimento buscarAtendimentoPaciente(Long idAtend) {
		final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Atendimento> criteriaQuery = criteriaBuilder.createQuery(Atendimento.class);
		final Root<Atendimento> atendRoot = criteriaQuery.from(Atendimento.class);
		
		criteriaQuery.select(atendRoot);
		criteriaQuery.where(criteriaBuilder.equal(atendRoot.get("paciente").get("id"), idAtend))
		.orderBy(criteriaBuilder.desc(atendRoot.get("dataEntrada")));
		
		Atendimento resultado = getEntityManager().createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
			
		return resultado;
	}

	public List<Medico> buscarMedicos(Long idAtendimento) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
	    CriteriaQuery<Medico> criteriaQuery = criteriaBuilder.createQuery(Medico.class);
	    Root<Atendimento> atendRoot = criteriaQuery.from(Atendimento.class);
	    Join<Atendimento, Medico> medicosJoin = atendRoot.join("medicos");

	    criteriaQuery.select(medicosJoin)
	                 .where(criteriaBuilder.equal(atendRoot.get("id"), idAtendimento));

	    List<Medico> resultado = getEntityManager().createQuery(criteriaQuery).getResultList();
	    
	    return resultado;
		
	}
	
	/* Relatorio */
	public List<Atendimento> listarTodosAtendimentos() {
	    final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
	    final CriteriaQuery<Atendimento> cQuery = cBuilder.createQuery(Atendimento.class);
	    final Root<Atendimento> atendimentoRoot = cQuery.from(Atendimento.class);

	    // Seleciona todos os atendimentos sem aplicar filtros
	    cQuery.select(atendimentoRoot);

	    return getEntityManager().createQuery(cQuery).getResultList();
	}
	
	public List<Atendimento> filtrarAtendimentos(Date inicio, Date fim, Long idMedico) {
	    CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
	    CriteriaQuery<Atendimento> query = cb.createQuery(Atendimento.class);
	    Root<Atendimento> root = query.from(Atendimento.class);

	    List<Predicate> predicates = new ArrayList<>();

	    // Filtros de data
	    if (inicio != null && fim != null) {
	        predicates.add(cb.between(root.get("dataEntrada"), inicio, fim));
	    } else if (inicio != null) {
	        predicates.add(cb.greaterThanOrEqualTo(root.get("dataEntrada"), inicio));
	    } else if (fim != null) {
	        predicates.add(cb.lessThanOrEqualTo(root.get("dataEntrada"), fim));
	    }

	    // Filtro por médico
	    if (idMedico != null && idMedico != 0L) {
	        Join<Atendimento, Medico> medicoJoin = root.join("medicos");
	        predicates.add(cb.equal(medicoJoin.get("id"), idMedico));
	    }

	    // Aplicar os predicados acumulados
	    query.where(cb.and(predicates.toArray(new Predicate[0])));

	    // Ordenar por data de entrada, do mais recente para o mais antigo
	    query.orderBy(cb.desc(root.get("dataEntrada")));

	    return getEntityManager().createQuery(query).getResultList();
	}

    public void finalizarAtendimento(Long atendimentoId, String parecer) {
        Atendimento atendimento = getEntityManager().find(Atendimento.class, atendimentoId);
        if (atendimento == null) {
            throw new IllegalArgumentException("Atendimento não encontrado");
        }
        if (parecer == null || parecer.trim().isEmpty()) {
            throw new IllegalArgumentException("Parecer é obrigatório");
        }
        atendimento.setSituacao(Situacao.FINALIZADO);
        atendimento.setParecer(parecer);
        getEntityManager().merge(atendimento);
    }

    public void excluirAtendimento(Long atendimentoId) {
        Atendimento atendimento = getEntityManager().find(Atendimento.class, atendimentoId);
        if (atendimento == null) {
            throw new IllegalArgumentException("Atendimento não encontrado");
        }
        if (!"FINALIZADO".equals(atendimento.getSituacao())) {
            getEntityManager().remove(atendimento);
        } else {
            throw new IllegalStateException("Atendimento finalizado não pode ser excluído");
        }
    }

    public Atendimento buscarAtendimentoPorId(Long id) {
        return getEntityManager().find(Atendimento.class, id);
    }
}
