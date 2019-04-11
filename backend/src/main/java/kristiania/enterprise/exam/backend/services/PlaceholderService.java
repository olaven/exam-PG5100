package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.PlaceholderEntity;
import kristiania.enterprise.exam.backend.repository.PlaceholderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class PlaceholderService {


    @Autowired
    private PlaceholderRepository crud;

    public List<PlaceholderEntity> getAll() {

        List<PlaceholderEntity> list = new ArrayList<>();
        crud.findAll().forEach(list::add);

        return list;
    }


    @Transactional
    @Async
    public void increment(Long id) {

        PlaceholderEntity placeholderEntity = crud.findById(id).get();

        Integer counter = placeholderEntity.getCounter();
        placeholderEntity.setCounter(counter + 1);

        crud.save(placeholderEntity);
    }

    @Transactional
    @Async
    public void incrementIflessThan(Integer limit, Long id) {

        incrementIf(counter -> counter < limit, id);
    }

    @Transactional
    @Async
    public void incrementIfGreatherThan(Integer limit, Long id) {

        incrementIf(counter -> counter > limit, id);
    }


    private void incrementIf(Predicate<Integer> predicate, Long id) {

        PlaceholderEntity placeholderEntity = crud.findById(id).get();
        Integer counter = placeholderEntity.getCounter();

        if (predicate.test(counter)) {

            counter++;
            placeholderEntity.setCounter(counter);
            crud.save(placeholderEntity);
        }
    }


    public PlaceholderEntity createNew() {

        PlaceholderEntity placeholderEntity = new PlaceholderEntity();
        placeholderEntity.setCounter(0);
        placeholderEntity.setA(0);
        placeholderEntity.setB(0);
        placeholderEntity.setC(0);

        PlaceholderEntity saved = crud.save(placeholderEntity);
        return saved;
    }

    public void delete(Long id) {

        crud.deleteById(id);
    }
}
