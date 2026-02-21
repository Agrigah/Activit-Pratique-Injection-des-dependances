package net.agrigah.pres;

import net.agrigah.metier.IMetier;
import net.agrigah.framework.context.ApplicationContextXML;

public class PresTestXML {
    public static void main(String[] args) {
        ApplicationContextXML context = new ApplicationContextXML("config.xml");  // ← just the filename
        IMetier metier = (IMetier) context.getBean("metier");
        System.out.println("Résultat XML = " + metier.calcul());
    }
}