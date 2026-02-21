package net.agrigah.ext;

import net.agrigah.dao.IDao;
import org.springframework.stereotype.Component;

@Component("d2")
public class DaoImplV2 implements IDao {
    @Override
    public double getData() {
        return 100;
    }
}