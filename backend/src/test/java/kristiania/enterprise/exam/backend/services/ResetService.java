package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.entity.Comment;
import kristiania.enterprise.exam.backend.entity.Item;
import kristiania.enterprise.exam.backend.entity.Rank;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

/*
    This file is partially copied from:
    * https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/734b4a660d8e1d815e2600c0e84cea5920bc5572/intro/exercise-solutions/quiz-game/part-11/backend/src/test/java/org/tsdes/intro/exercises/quizgame/backend/service/ResetService.java
 */
@Service
@Transactional
public class ResetService {

    @PersistenceContext
    private EntityManager em;

    public void resetDatabase(){

        //Have to use native SQL for ElementCollection
        Query query = em.createNativeQuery("delete from user_entity_roles");
        query.executeUpdate();

        deleteEntities(Comment.class);
        deleteEntities(Rank.class);
        deleteEntities(UserEntity.class);
        deleteEntities(Item.class);
    }

    private void deleteEntities(Class<?> entity){

        if(entity == null || entity.getAnnotation(Entity.class) == null){
            throw new IllegalArgumentException("Invalid non-entity class");
        }

        String name = entity.getSimpleName();

        /*
            Note: we passed as input a Class<?> instead of a String to
            avoid SQL injection. However, being here just test code, it should
            not be a problem. But, as a good habit, always be paranoiac about
            security, above all when you have code that can delete the whole
            database...
         */

        Query query = em.createQuery("delete from " + name);
        query.executeUpdate();
    }
}
