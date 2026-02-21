package net.agrigah.metier;

import net.agrigah.dao.IDao;

public class MetierImpl implements IMetier {
    private IDao dao;// Couplage faible
    @Override
    public double calcul() {
        double t = dao.gatData();
        double res =t * 12 *Math.PI/2 *Math.cos(t);

        return res;
    }

}
