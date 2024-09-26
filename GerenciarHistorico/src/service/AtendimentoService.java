package service;

import modelo.Atendimento;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class AtendimentoService extends GenericService<Atendimento> {

    public AtendimentoService() {
        super(Atendimento.class);
    }

    public List<Integer> buscarNumerosExistentes() {
        TypedQuery<Integer> query = getEntityManager().createQuery(
                "SELECT a.numero FROM Atendimento a", Integer.class);
        return query.getResultList(); // Retorna todos os números existentes
    }
}
