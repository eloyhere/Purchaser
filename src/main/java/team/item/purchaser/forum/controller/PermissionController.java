package team.item.purchaser.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.item.purchaser.forum.entity.Permission;
import team.item.purchaser.forum.service.PermissionService;
import team.item.purchaser.forum.service.Service;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "permission")
public class PermissionController implements Controller<Permission, UUID> {

    @Autowired
    private PermissionService service;

    @Override
    public Service<Permission, UUID> service() {
        return service;
    }

    @Override
    @PreAuthorize("hasRole('admin')")
    @PutMapping(value = "insert")
    public Object insert(Permission entity) {
        return Controller.super.insert(entity);
    }

    @Override
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping(value = "delete")
    public Object delete(List<UUID> uuids) {
        return Controller.super.delete(uuids);
    }

    @Override
    @PreAuthorize("hasRole('admin')")
    @PutMapping(value = "update")
    public Object update(Permission entity) {
        return Controller.super.update(entity);
    }

    @Override
    @GetMapping(value = "list")
    public Object list(Permission entity, @RequestParam Integer page, @RequestParam Integer size) {
        return Controller.super.list(entity, page, size);
    }
}
