package team.item.purchaser.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import team.item.purchaser.forum.entity.Consumer;
import team.item.purchaser.forum.exception.DuplicateElementException;
import team.item.purchaser.forum.repository.ConsumerRepository;
import team.item.purchaser.forum.repository.PermissionRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

@org.springframework.stereotype.Service(value = "consumerService")
public class ConsumerService implements Service<Consumer, UUID>{

    @Autowired
    private ConsumerRepository repository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public JpaRepository<Consumer, UUID> repository() {
        return repository;
    }

    @Override
    public Consumer insert(Consumer entity) {
        entity.setId(UUID.randomUUID());
        while (existsById(entity.getId())){
            entity.setId(UUID.randomUUID());
        }
        if(repository.existsByPhone(entity.getPhone())){
            throw new DuplicateElementException("Duplicate element for field: \"phone\": "+entity.getPhone());
        }
        entity.setBan(LocalDateTime.now().minusHours(2));
        entity.setMute(LocalDateTime.now().minusHours(2));
        return repository.save(entity);
    }

    public Optional<Consumer> login(Consumer entity){
        List<Optional<Consumer>> list = List.of(
                loginByPhone(entity.getPhone(), entity.getPassword()),
                loginById(entity.getId(), entity.getPassword()),
                loginByEmail(entity.getEmail(), entity.getPassword()),
                loginByUsername(entity.getUsername(), entity.getPassword())
        );
        for(Optional<Consumer> optional:list){
            if(optional.isPresent()){
                return optional;
            }
        }
        return Optional.empty();
    }

    public Optional<Consumer> loginByPhone(String phone, String password){
        return repository.findByPhoneAndPassword(phone, password);
    }

    public Optional<Consumer> loginByUsername(String username, String password){
        return repository.findByUsernameAndPassword(username, password);
    }

    public Optional<Consumer> loginByEmail(String email, String password){
        return repository.findByEmailAndPassword(email, password);
    }

    public Optional<Consumer> loginById(UUID id, String password){
        return repository.findByIdAndPassword(id, password);
    }
}
