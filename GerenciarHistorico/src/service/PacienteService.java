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
public class PacienteService extends GenericService<Paciente>{

	public PacienteService() {
		super(Paciente.class);
		
	}

	public Paciente findByCpf(String cpf) {
	    try {
	        TypedQuery<Paciente> query = getEntityManager().createQuery(
	                "SELECT p FROM Paciente p WHERE p.cpf = :cpf", Paciente.class);
	        query.setParameter("cpf", cpf);
	        return query.getSingleResult();
	    } catch (Exception e) {
	        return null;
	    }
	}
}
