package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import modelo.Atendimento;
import modelo.Medico;

@Stateless
public class MedicoService extends GenericService<Medico> {

	public MedicoService() {
		super(Medico.class);

	}

	public Medico buscarMedico(Long idMedico) {
		final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Medico> criteriaQuery = criteriaBuilder.createQuery(Medico.class);
		final Root<Medico> medicoRoot = criteriaQuery.from(Medico.class);
		
		criteriaQuery.select(medicoRoot);
		
		criteriaQuery.where(criteriaBuilder.equal(medicoRoot.get("id"), idMedico));
		
		Medico resultado = getEntityManager().createQuery(criteriaQuery).getSingleResult();
		
		return resultado;
	}

	public List<Medico> listarPorMedico(String nomeMedico) {
        final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Medico> cQuery = cBuilder.createQuery(Medico.class);
        final Root<Medico> medicoRoot = cQuery.from(Medico.class);

        String[] nomes = nomeMedico.toLowerCase().split("\\s+", 2);

        if (nomes.length > 1) {
            cQuery.select(medicoRoot)
                  .where(cBuilder.and(
                      cBuilder.like(cBuilder.lower(medicoRoot.get("primeiroNome")), "%" + nomes[0] + "%"),
                      cBuilder.like(cBuilder.lower(medicoRoot.get("sobrenome")), "%" + nomes[1] + "%")
                  ))
                  .orderBy(cBuilder.asc(medicoRoot.get("primeiroNome")), cBuilder.asc(medicoRoot.get("sobrenome")));
        } else {
            cQuery.select(medicoRoot)
                  .where(cBuilder.or(
                      cBuilder.like(cBuilder.lower(medicoRoot.get("primeiroNome")), "%" + nomes[0] + "%"),
                      cBuilder.like(cBuilder.lower(medicoRoot.get("sobrenome")), "%" + nomes[0] + "%")
                  ))
                  .orderBy(cBuilder.asc(medicoRoot.get("primeiroNome")), cBuilder.asc(medicoRoot.get("sobrenome")));
        }

        return getEntityManager().createQuery(cQuery).getResultList();
    }

    public List<Medico> listarTodosOsMedicos() {
        final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<Medico> cQuery = cBuilder.createQuery(Medico.class);
        final Root<Medico> medicoRoot = cQuery.from(Medico.class);

        cQuery.select(medicoRoot)
              .orderBy(cBuilder.asc(medicoRoot.get("primeiroNome")), cBuilder.asc(medicoRoot.get("sobrenome")));

        return getEntityManager().createQuery(cQuery).getResultList();
    }
    
    public Atendimento buscarAtendComMedico(Long atendimentoId) {
    	final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Atendimento> criteriaQuery = criteriaBuilder.createQuery(Atendimento.class);
		final Root<Atendimento> atendRoot = criteriaQuery.from(Atendimento.class);
		
		
		atendRoot.fetch("medicos", JoinType.INNER);
		criteriaQuery.select(atendRoot);
		criteriaQuery.where(criteriaBuilder.equal(atendRoot.get("id"), atendimentoId));
		
		criteriaQuery.orderBy(criteriaBuilder.asc(atendRoot.get("numero")));
		
		Atendimento resultado = getEntityManager().createQuery(criteriaQuery).getSingleResult();
		
		return resultado;
    }
}
