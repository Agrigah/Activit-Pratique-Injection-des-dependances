package net.agrigah.metier;

import net.agrigah.dao.IDao;

public class MetierImpl implements IMetier {
    private IDao dao  ;// Couplage faible


    /**
     * Pour injecter dans l'attribut dao
     * un objet d'une classe qui implémente l'interface IDAO
     * au moment de l'instantiation
     */
    public MetierImpl(IDao dao) {
        this.dao = dao;
    }

    public MetierImpl() {
    }

    @Override
    public double calcul() {
        double t = dao.getData();
        double res = t * 2;
        return res;
    }


    /**
     * Pour injecter dans l'attribut dao
     * un objet d'une classe qui implémente l'interface IDAO
     * aprés instantiation
     */
     public void setDao(IDao dao) {
        this.dao = dao;
     }
}
