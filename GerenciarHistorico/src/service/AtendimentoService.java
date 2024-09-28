package service;

import modelo.Atendimento;
import modelo.Medico;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

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
}
