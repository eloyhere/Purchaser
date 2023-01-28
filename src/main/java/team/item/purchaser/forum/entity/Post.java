package team.item.purchaser.forum.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import team.item.purchaser.forum.converter.Pictures;
import team.item.purchaser.forum.converter.Videos;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "post")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Post {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "author")
    private Consumer author;

    private String content;

    @Convert(converter = Pictures.class)
    @Column(columnDefinition = "mediumblob")
    private ArrayList<String> pictures;

    @Convert(converter = Videos.class)
    @Column(columnDefinition = "longblob")
    private ArrayList<String> videos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return id != null && Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}