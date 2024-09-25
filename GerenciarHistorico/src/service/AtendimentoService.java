package service;

import javax.ejb.Stateless;

import modelo.Atendimento;

@Stateless
public class AtendimentoService extends GenericService<Atendimento>{

	public AtendimentoService() {
		super(Atendimento.class);
		
	}

}
