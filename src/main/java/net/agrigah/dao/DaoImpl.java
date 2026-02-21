package net.agrigah.dao;

import net.agrigah.framework.Component;

@Component("d")
public class DaoImpl implements IDao {
    @Override
    public double getData() {
        return 42;
    }
}