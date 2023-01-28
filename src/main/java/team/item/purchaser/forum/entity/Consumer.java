package team.item.purchaser.forum.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import team.item.purchaser.forum.converter.Picture;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "consumer")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Consumer implements UserDetails {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true, length = 20)
    @NotEmpty()
    @NotBlank()
    private String username;

    @Column(nullable = false, length = 20)
    @NotEmpty()
    @NotBlank()
    private String nickname;

    @Column(nullable = false, length = 20)
    @NotEmpty()
    @NotBlank()
    private String password;

    @Convert(converter = Picture.class)
    @Column(columnDefinition = "mediumblob")
    @NotEmpty()
    @NotBlank()
    private String avatar;

    @Column(unique = true, length = 20)
    @NotEmpty()
    @NotBlank()
    private String phone;

    @Column(unique = true, length = 32)
    @NotEmpty()
    @NotBlank()
    private String email;

    private LocalDateTime ban;

    private LocalDateTime mute;

    private LocalDateTime expire;

    @OneToMany(cascade = {CascadeType.PERSIST})
    @ToString.Exclude
    private List<Role> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles().stream().flatMap((role)-> role.getPermissions().stream()).distinct().toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return Objects.nonNull(expire) && LocalDateTime.now().isAfter(expire);
    }

    @Override
    public boolean isAccountNonLocked() {
        return Objects.nonNull(ban) && LocalDateTime.now().isAfter(ban);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isAccountNonExpired() && this.isAccountNonLocked();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Consumer consumer = (Consumer) o;
        return id != null && Objects.equals(id, consumer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}