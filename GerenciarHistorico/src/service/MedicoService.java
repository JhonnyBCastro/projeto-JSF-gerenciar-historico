package service;

import javax.ejb.Stateless;

import modelo.Medico;

@Stateless
public class MedicoService extends GenericService<Medico>{

	public MedicoService() {
		super(Medico.class);
		
	}

}
