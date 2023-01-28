package team.item.purchaser.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team.item.purchaser.forum.entity.Role;
import team.item.purchaser.forum.service.RoleService;
import team.item.purchaser.forum.service.Service;

import java.util.List;
import java.util.UUID;

public class RoleController implements Controller<Role, UUID> {

    @Autowired
    private RoleService service;

    @Override
    public Service<Role, UUID> service() {
        return service;
    }

    @Override
    @PutMapping(value = "insert")
    public Object insert(Role entity) {
        return Controller.super.insert(entity);
    }

    @Override
    @DeleteMapping(value = "delete")
    public Object delete(List<UUID> uuids) {
        return Controller.super.delete(uuids);
    }

    @Override
    @PutMapping(value = "update")
    public Object update(Role entity) {
        return Controller.super.update(entity);
    }

    @Override
    public Object list(Role entity, @RequestParam Integer page, @RequestParam Integer size) {
        return Controller.super.list(entity, page, size);
    }
}
