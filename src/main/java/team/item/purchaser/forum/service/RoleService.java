package team.item.purchaser.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import team.item.purchaser.forum.entity.Role;
import team.item.purchaser.forum.repository.PermissionRepository;
import team.item.purchaser.forum.repository.RoleRepository;

import java.util.UUID;

@org.springframework.stereotype.Service(value = "roleService")
public class RoleService implements Service<Role, UUID> {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public JpaRepository<Role, UUID> repository() {
        return repository;
    }

}
