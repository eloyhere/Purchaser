package team.item.purchaser.forum.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "role")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Role {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST})
    @ToString.Exclude
    private ArrayList<Permission> permissions = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return id != null && Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}