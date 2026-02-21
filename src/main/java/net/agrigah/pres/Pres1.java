package net.agrigah.pres;

import net.agrigah.framework.context.ApplicationContextAnnotation;
import net.agrigah.metier.MetierImpl;

public class Pres1 {
    public static void main(String[] args) {

        // --- Crée le contexte annotation ---
        ApplicationContextAnnotation context = new ApplicationContextAnnotation("net.agrigah");

        // --- Récupère le bean MetierImpl ---
        MetierImpl metier = context.getBean(MetierImpl.class);

        // --- Test de chaque type d'injection ---
        System.out.println("Résultat Field Injection = " + metier.calculField());
        System.out.println("Résultat Constructor Injection = " + metier.calculConstructor());
        System.out.println("Résultat Setter Injection = " + metier.calculSetter());
    }
}