package net.agrigah.pres;

import net.agrigah.metier.MetierImpl;
import net.agrigah.framework.context.ApplicationContextAnnotation;

public class PresTestAnnotation {
    public static void main(String[] args) {
        ApplicationContextAnnotation context = new ApplicationContextAnnotation("net.agrigah");
        MetierImpl metier = context.getBean(MetierImpl.class);
        System.out.println("Résultat Annotation = " + metier.calcul());
    }
}