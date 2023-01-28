package team.item.purchaser.forum.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "permission")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Permission implements GrantedAuthority {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(length = 20)
    @NotEmpty()
    @NotBlank()
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Permission that = (Permission) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}