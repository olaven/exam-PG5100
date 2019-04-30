package kristiania.enterprise.exam.backend.services;

import jdk.nashorn.internal.ir.annotations.Ignore;
import kristiania.enterprise.exam.backend.ResetService;
import kristiania.enterprise.exam.backend.StubApplication;
import kristiania.enterprise.exam.backend.entity.PlaceholderEntity;
import kristiania.enterprise.exam.backend.repository.PlaceholderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static kristiania.enterprise.exam.backend.TestUtil.getValidEntity;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {StubApplication.class})
@ExtendWith(SpringExtension.class)
class PlaceholderServiceTest {

    @Autowired
    private PlaceholderService service;
    @Autowired
    PlaceholderRepository repository;
    @Autowired
    ResetService resetService;

    @BeforeEach
    public void init() {

        resetService.resetDatabase();
    }

    @Test
    public void databaseIsEmpty() {

        long count = repository.count();
        assertEquals(count, 0);
    }

    @Test
    public void canIncrementIfBelow10() throws InterruptedException {

        PlaceholderEntity entity = repository.save(getValidEntity());
        entity.setCounter(9);
        repository.save(entity);

        service.incrementIflessThan(10, entity.getId());

        Thread.sleep(200);

        PlaceholderEntity allowedAfter = repository
                .findById(entity.getId())
                .get();


        int expected = entity.getCounter() + 1;
        int actual = allowedAfter.getCounter();

        assertEquals(expected, actual);
    }

    @Test
    public void canIncrementIfGreaterThan10() throws InterruptedException {

        PlaceholderEntity entity = repository.save(getValidEntity());
        entity.setCounter(11);
        repository.save(entity);

        service.incrementIfGreatherThan(10, entity.getId());

        Thread.sleep(200);

        PlaceholderEntity allowedAfter = repository
                .findById(entity.getId())
                .get();


        int expected = entity.getCounter() + 1;
        int actual = allowedAfter.getCounter();

        assertEquals(expected, actual);
    }

    @Test
    public void canCreateNew() {

        long before = repository.count();
        service.createNew();
        long after = repository.count();

        assertEquals(before + 1, after);
    }

    @Test
    public void canDelete() {

        Long id = service.createNew().getId();
        assertTrue(repository.findById(id).isPresent());

        service.delete(id);
        assertFalse(repository.findById(id).isPresent());
    }

    @Test
    public void canGetAll() {

        int n = 5;
        List<PlaceholderEntity> persistedEntities = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            PlaceholderEntity entity = service.createNew();
            persistedEntities.add(entity);
        }

        List<PlaceholderEntity> retrievedEntities = service.getAll();
        assertEquals(persistedEntities.size(), retrievedEntities.size());

        for (int i = 0; i < persistedEntities.size(); i++) {

            PlaceholderEntity persisted = persistedEntities.get(i);
            PlaceholderEntity retrieved = persistedEntities.get(i);

            assertEquals(persisted, retrieved);
        }

    }
}