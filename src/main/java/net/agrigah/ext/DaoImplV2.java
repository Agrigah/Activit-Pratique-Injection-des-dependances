package net.agrigah.ext;

import net.agrigah.dao.IDao;

public class DaoImplV2  implements IDao {
    @Override
    public double getData() {
        System.out.println("VErsion capteurs....");
        double t= 12;
        return t;
    }


}
