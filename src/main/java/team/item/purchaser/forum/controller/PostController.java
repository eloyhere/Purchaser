package team.item.purchaser.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.item.purchaser.forum.entity.Post;
import team.item.purchaser.forum.service.PostService;
import team.item.purchaser.forum.service.Service;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "post")
public class PostController implements Controller<Post, UUID> {

    @Autowired
    private PostService service;

    /**
     * Service
     */
    @Override
    public Service<Post, UUID> service() {
        return service;
    }

    @Override
    @PutMapping(value = "insert")
    public Object insert(Post entity) {
        return Controller.super.insert(entity);
    }

    @Override
    @DeleteMapping(value = "delete")
    public Object delete(List<UUID> uuids) {
        return Controller.super.delete(uuids);
    }

    @Override
    @PutMapping(value = "update")
    public Object update(Post entity) {
        return Controller.super.update(entity);
    }

    @Override
    @GetMapping(value = "list")
    public Object list(Post entity, @RequestParam Integer page, @RequestParam Integer size) {
        return Controller.super.list(entity, page, size);
    }
}
