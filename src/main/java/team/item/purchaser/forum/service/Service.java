package team.item.purchaser.forum.service;

import java.util.*;
import java.util.concurrent.atomic.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Service<T, ID> {

    public default T insert(T entity){
        JpaRepository<T, ID> repository = repository();
        return repository.saveAndFlush(entity);
    }


    public default Integer insert(Iterable<T> entities){
        JpaRepository<T, ID> repository = repository();
        return repository.saveAllAndFlush(entities).size();
    }

    public default Boolean delete(T entity){
        JpaRepository<T, ID> repository = repository();
        boolean exist = repository().exists(Example.of(entity));
        if(exist){
            repository.delete(entity);
            return true;
        }
        return false;
    }

    public default Integer delete(Iterable<T> entities){
        JpaRepository<T, ID> repository = repository();
        AtomicInteger counter = new AtomicInteger(0);
        entities.forEach(t->{
            if(delete(t)){
                counter.incrementAndGet();
            }
        });
        return counter.get();
    }

    public default Boolean deleteById(ID id){
        JpaRepository<T, ID> repository = repository();
        boolean exist = repository().existsById(id);
        if(exist){
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public default Integer deleteById(Iterable<ID> ids){
        JpaRepository<T, ID> repository = repository();
        AtomicInteger counter = new AtomicInteger(0);
        ids.forEach(id->{
            if(deleteById(id)){
                counter.incrementAndGet();
            }
        });
        return counter.get();
    }

    public default T update(T entity){
        JpaRepository<T, ID> repository = repository();
        return repository.saveAndFlush(entity);
    }

    public default Page<T> list(T entity, Integer page, Integer size){
        JpaRepository<T, ID> repository = repository();
        return repository.findAll(Example.of(entity), PageRequest.of(page, size));
    }

    public default Page<T> list(Integer page, Integer size){
        JpaRepository<T, ID> repository = repository();
        return repository.findAll( PageRequest.of(page, size));
    }

    public default Optional<T> findOne(T entity){
        JpaRepository<T, ID> repository = repository();
        return repository.findOne(Example.of(entity));
    }

    public default Optional<T> findById(ID id){
        JpaRepository<T, ID> repository = repository();
        return repository.findById(id);
    }

    public default Boolean exists(T entity){
        JpaRepository<T, ID> repository = repository();
        return repository.exists(Example.of(entity));
    }

    public default Long count(T entity){
        JpaRepository<T, ID> repository = repository();
        return repository.count(Example.of(entity));
    }

    public default Boolean existsById(ID id){
        JpaRepository<T, ID> repository = repository();
        return repository.existsById(id);
    }

    public JpaRepository<T, ID> repository();
}
