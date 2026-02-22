📌 Mini Framework d’Injection de Dépendances (IOC)
🎯 Objectif du Projet

Ce projet consiste à développer un mini Framework d’Injection de Dépendances (IOC) similaire à Spring.

Le framework permet :

La création automatique des objets (Beans)

L’injection des dépendances entre composants

Le support de deux modes de configuration :

✅ Version XML (JAXB – OXM)

✅ Version Annotations

Le support de trois types d’injection :

✔ Injection par Constructeur

✔ Injection par Setter

✔ Injection par Attribut (Field)


⚙️ Partie 1 – Composants Métier
🔹 Interface DAO
public interface IDao {
    double getData();
}
🔹 Implémentation DAO
@Component("d")
public class DaoImpl implements IDao {
    public double getData() {
        return 42;
    }
}
🔹 Interface Métier
public interface IMetier {
    double calcul();
}
🔹 Implémentation Métier
@Component("metier")
public class MetierImpl implements IMetier {

    @Autowired
    @Qualifier("d")
    private IDao dao;

    public double calcul() {
        return dao.getData() * 2;
    }
}
🏷️ Annotations du Framework
🔸 @Component

Permet de déclarer une classe comme Bean géré par le framework.

🔸 @Autowired

Indique qu’une dépendance doit être injectée automatiquement.

🔸 @Qualifier

Permet de choisir l’implémentation à injecter lorsqu’il y a plusieurs beans du même type.

🧠 Fonctionnement du Framework
1️⃣ Version Annotation

Le framework :

Scanne les classes annotées @Component

Instancie les objets

Injecte automatiquement les dépendances :

Par constructeur

Par setter

Par attribut

▶ Test
ApplicationContextAnnotation context =
        new ApplicationContextAnnotation("net.agrigah");

IMetier metier = context.getBean(IMetier.class);

System.out.println("Résultat = " + metier.calcul());
2️⃣ Version XML
📄 config.xml
<beans>
    <bean id="d" class="net.agrigah.dao.DaoImpl"/>
    
    <bean id="metier" class="net.agrigah.metier.MetierImpl">
        <constructor-arg ref="d"/>
    </bean>
</beans>

Le framework :

Lit le fichier XML

Crée les objets via réflexion

Injecte les dépendances définies dans le XML

▶ Test
ApplicationContextXML context =
        new ApplicationContextXML("resources/config.xml");

IMetier metier = (IMetier) context.getBean("metier");

System.out.println("Résultat XML = " + metier.calcul());
🔬 Types d’Injection Supportés
✔ Injection par Constructeur
@Autowired
public MetierImpl(IDao dao) {
    this.dao = dao;
}
✔ Injection par Setter
@Autowired
public void setDao(IDao dao) {
    this.dao = dao;
}
✔ Injection par Attribut (Field)
@Autowired
private IDao dao;
🧩 Technologies Utilisées

Java Reflection API

Annotations personnalisées

JAXB (pour XML)

Programmation orientée objet

Principe du couplage faible

✅ Résultat Final

Ce mini framework permet :

La gestion automatique des dépendances

La réduction du couplage entre composants

Une architecture similaire à Spring IOC

Une compréhension approfondie du fonctionnement interne d’un conteneur IOC

👩‍💻 Auteur

Aya Agrigah
