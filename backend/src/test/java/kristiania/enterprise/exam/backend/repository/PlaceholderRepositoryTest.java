package kristiania.enterprise.exam.backend.repository;

import kristiania.enterprise.exam.backend.ResetService;
import kristiania.enterprise.exam.backend.StubApplication;
import kristiania.enterprise.exam.backend.entity.PlaceholderEntity;
import kristiania.enterprise.exam.backend.services.PlaceholderService;
import kristiania.enterprise.exam.backend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static kristiania.enterprise.exam.backend.TestUtil.getValidEntity;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {StubApplication.class})
class PlaceholderRepositoryTest {

    @Autowired
    private PlaceholderRepository repository;

    @Autowired
    private ResetService resetService;

    @Autowired
    private PlaceholderService placeholderService;

    @BeforeEach
    public void init() {

        resetService.resetDatabase();
    }

    @Test
    public void testDatbaseShouldBeEmpty() {

        Iterable<PlaceholderEntity> entities = repository.findAll();
        assertEquals(0, entities.spliterator().estimateSize());
    }

    @Test
    public void testCanSave() {

        PlaceholderEntity entity = new PlaceholderEntity();

        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer counter = 1;

        entity.setCounter(counter);
        entity.setA(a);
        entity.setB(b);
        entity.setC(c);

        PlaceholderEntity persisted = repository.save(entity);

        assertEquals(a, persisted.getA());
        assertEquals(b, persisted.getB());
        assertEquals(c, persisted.getC());
        assertEquals(counter, persisted.getCounter());
    }

    @Test
    public void testCounterCannotBeAbove20() {

        PlaceholderEntity entity = getValidEntity();
        entity.setCounter(21);

        assertThrows(Exception.class, () -> {
            repository.save(entity);
        });
    }

    @Test
    public void testCounterCannotBeBelow0() {

        PlaceholderEntity entity = getValidEntity();
        entity.setCounter(-1);

        assertThrows(Exception.class, () -> {
            repository.save(entity);
        });
    }

    @Test
    public void canGetByCounterLessThan() {

        int n = 15;
        PlaceholderEntity entity = placeholderService.createNew();
        entity.setCounter(n);
        repository.save(entity);

        assertTrue(repository.getByCounterLessThan(n).isEmpty());
        assertFalse(repository.getByCounterLessThan(n + 1).isEmpty());

    }

    @Test
    public void canGetByCounterGreaterThan() {

        int n = 15;
        PlaceholderEntity entity = placeholderService.createNew();
        entity.setCounter(n);
        repository.save(entity);

        assertTrue(repository.getByCounterGreaterThan(n).isEmpty());
        assertFalse(repository.getByCounterGreaterThan(n - 1).isEmpty());
    }

    @Test
    public void getByAAndBAndC() {

        int a = 1;
        int b = 2;
        int c = 3;

        PlaceholderEntity entity = placeholderService.createNew();

        entity.setA(a);
        entity.setB(b);
        entity.setC(c);

        repository.save(entity);

        PlaceholderEntity retrieved = repository.getByAAndBAndC(a, b, c).get(0);

        assertEquals(entity, retrieved);

    }

}