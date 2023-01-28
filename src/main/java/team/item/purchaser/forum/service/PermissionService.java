package team.item.purchaser.forum.service;

import org.springframework.data.jpa.repository.JpaRepository;
import team.item.purchaser.forum.entity.Permission;
import team.item.purchaser.forum.repository.PermissionRepository;

import java.util.*;

@org.springframework.stereotype.Service(value = "permissionService")
public class PermissionService implements Service<Permission, UUID> {

    PermissionRepository repository;

    @Override
    public JpaRepository<Permission, UUID> repository() {
        return repository;
    }

    @Override
    public Permission insert(Permission entity) {
        entity.setId(UUID.randomUUID());
        while (existsById(entity.getId())){
            entity.setId(UUID.randomUUID());
        }
        return repository.saveAndFlush(entity);
    }
}
