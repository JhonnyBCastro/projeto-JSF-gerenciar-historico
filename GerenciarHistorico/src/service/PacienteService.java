package service;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import modelo.Paciente;

@Stateless
public class PacienteService extends GenericService<Paciente> {

	public PacienteService() {
		super(Paciente.class);

	}

	public Paciente findByCpf(String cpf) {
		try {
			final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
			final CriteriaQuery<Paciente> criteriaQuery = criteriaBuilder.createQuery(Paciente.class);
			final Root<Paciente> pacienteRoot = criteriaQuery.from(Paciente.class);

			criteriaQuery.select(pacienteRoot);

			criteriaQuery.where(criteriaBuilder.equal(pacienteRoot.get("cpf"), cpf));

			Paciente resultado = getEntityManager().createQuery(criteriaQuery).getSingleResult();

			return resultado;

		} catch (Exception e) {
			return null;
		}
   
	}
}
