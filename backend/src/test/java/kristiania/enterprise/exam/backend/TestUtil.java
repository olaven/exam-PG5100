package kristiania.enterprise.exam.backend;

import kristiania.enterprise.exam.backend.entity.PlaceholderEntity;

public class TestUtil {

    public static PlaceholderEntity getValidEntity() {

        PlaceholderEntity entity = new PlaceholderEntity();
        entity.setA(1);
        entity.setB(2);
        entity.setC(3);
        entity.setCounter(0);

        return entity;
    }
}
