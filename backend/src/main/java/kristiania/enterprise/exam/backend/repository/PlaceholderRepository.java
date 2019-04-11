package kristiania.enterprise.exam.backend.repository;


import kristiania.enterprise.exam.backend.entity.PlaceholderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceholderRepository extends CrudRepository<PlaceholderEntity, Long> {

    List<PlaceholderEntity> getByCounterLessThan(int limit);
    List<PlaceholderEntity> getByCounterGreaterThan(int limit);
    List<PlaceholderEntity> getByAAndBAndC(int a, int b, int c);
}
