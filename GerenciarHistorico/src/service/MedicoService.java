package service;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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

}
