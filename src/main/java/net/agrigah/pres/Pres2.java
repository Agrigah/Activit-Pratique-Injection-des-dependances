package net.agrigah.pres;

import net.agrigah.dao.IDao;
import net.agrigah.metier.IMetier;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Pres2 {
    public static void main(String[] args)
            throws Exception {

        Scanner scanner = new Scanner(new File("config.txt"));

        // DAO
        String daoClassName = scanner.nextLine();
        Class<?> cDao = Class.forName(daoClassName);
        IDao dao = (IDao) cDao.getDeclaredConstructor().newInstance();

        // METIER
        String metierClassName = scanner.nextLine();
        Class<?> cMetier = Class.forName(metierClassName);

         IMetier metier = (IMetier) cMetier.getConstructor(IDao.class).newInstance(dao);

       // IMetier metier = (IMetier) cMetier.getDeclaredConstructor().newInstance();

       // Method setDao = cMetier.getDeclaredMethod("setDao", IDao.class);
       // setDao.invoke(metier, dao);

        System.out.println("RES = " + metier.calcul());
    }
}