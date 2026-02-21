package net.agrigah.metier;

import net.agrigah.dao.IDao;
import net.agrigah.framework.Autowired;
import net.agrigah.framework.Component;
import net.agrigah.framework.Qualifier;

@Component("metier")
public class MetierImpl implements IMetier {

    // --- Field Injection ---
    @Autowired
    @Qualifier("d")
    private IDao daoField;

    // --- Constructor Injection ---
    private IDao daoConstructor;

    @Autowired
    public MetierImpl(IDao dao) {  // ← removed @Qualifier here
        this.daoConstructor = dao;
    }

    // needed for reflection-based instantiation
    public MetierImpl() {}

    // --- Setter Injection ---
    private IDao daoSetter;

    @Autowired
    @Qualifier("d")
    public void setDao(IDao dao) {
        this.daoSetter = dao;
    }

    public double calcul() {
        if (daoField != null) return daoField.getData() * 2;
        if (daoSetter != null) return daoSetter.getData() * 2;
        if (daoConstructor != null) return daoConstructor.getData() * 2;
        throw new RuntimeException("No dao injected!");
    }

    public double calculField() {
        return daoField.getData() * 2;
    }

    public double calculConstructor() {
        return daoConstructor.getData() * 2;
    }

    public double calculSetter() {
        return daoSetter.getData() * 2;
    }
}