package net.agrigah.pres;

import net.agrigah.dao.DaoImpl;
import net.agrigah.ext.DaoImplV2.DaoImplV2;
import net.agrigah.metier.MetierImpl;

public class Pres1 {
    public static void main(String[] args) {
        DaoImplV2 d = new DaoImplV2();
        MetierImpl metier = new MetierImpl(d);
        //metier.setDao(d);//Injection des dépendances via le setter
        System.out.println("RES="+metier.calcul());
    }
}
