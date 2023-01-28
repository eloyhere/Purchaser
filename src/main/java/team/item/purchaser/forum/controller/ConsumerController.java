package team.item.purchaser.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.item.purchaser.forum.entity.Consumer;
import team.item.purchaser.forum.provider.UsernamePasswordAuthenticationProvider;
import team.item.purchaser.forum.service.*;

import java.util.*;

@RestController
@RequestMapping(value = "consumer")
public class ConsumerController implements Controller<Consumer, UUID>{

    @Autowired
    ConsumerService service;

    private UsernamePasswordAuthenticationProvider provider;

    /**
     * Service
     */
    @Override
    public Service<Consumer, UUID> service() {
        return service;
    }

    @Override
    @PutMapping(value = "insert")
    public Object insert(Consumer entity) {
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
    public Object update(Consumer entity) {
        return Controller.super.update(entity);
    }

    @Override
    @GetMapping(value = "list")
    public Object list(Consumer entity, @RequestParam Integer page, @RequestParam Integer size) {
        return Controller.super.list(entity, page, size);
    }
}
