package team.item.purchaser.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import team.item.purchaser.forum.entity.Post;
import team.item.purchaser.forum.repository.PostRepository;

import java.util.UUID;

@org.springframework.stereotype.Service(value = "postService")
public class PostService implements Service<Post, UUID> {

    @Autowired
    private PostRepository repository;

    @Override
    public JpaRepository<Post, UUID> repository() {
        return repository;
    }
}
