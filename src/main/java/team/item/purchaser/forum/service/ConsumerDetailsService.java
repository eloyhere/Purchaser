package team.item.purchaser.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import team.item.purchaser.forum.entity.Consumer;
import team.item.purchaser.forum.entity.Permission;
import team.item.purchaser.forum.entity.Role;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@org.springframework.stereotype.Service(value = "consumerDetailsService")
public class ConsumerDetailsService implements UserDetailsService, UserDetailsPasswordService{

    @Autowired
    private ConsumerService service;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private static Consumer root(){
        Consumer consumer = new Consumer();
        consumer.setId(UUID.randomUUID());
        LocalDateTime now = LocalDateTime.now();
        consumer.setMute(now.minusHours(2));
        consumer.setBan(now.minusHours(2));
        consumer.setExpire(now.minusHours(2));
        consumer.setEmail("root@forum.com");
        consumer.setPhone("123");
        consumer.setUsername("root");
        consumer.setPassword(encoder.encode("1024.."));

        Role role = new Role();
        role.setId(UUID.randomUUID());
        role.setName("admin");

        ArrayList<Permission> permissions = new ArrayList<>(Stream.of("insert", "delete", "update", "list").map((authority) -> {
            Permission permission = new Permission();
            permission.setId(UUID.randomUUID());
            permission.setAuthority(authority);
            return permission;
        }).toList());
        role.setPermissions(permissions);
        consumer.setRoles(List.of(role));
        return consumer;
    }

    public static Consumer anonymous(){
        Consumer consumer = new Consumer();
        UUID id = UUID.randomUUID();
        consumer.setId(id);
        LocalDateTime now = LocalDateTime.now();
        consumer.setMute(now.minusHours(2));
        consumer.setBan(now.minusHours(2));
        consumer.setEmail("anonymous"+id+"@forum.com");
        consumer.setPhone("1234");
        consumer.setUsername("anonymous"+id);
        consumer.setPassword(encoder.encode(String.valueOf(id)));

        Role role = new Role();
        role.setId(UUID.randomUUID());
        role.setName("anonymous");

        ArrayList<Permission> permissions = new ArrayList<>(Stream.of("list").map((authority) -> {
            Permission permission = new Permission();
            permission.setId(UUID.randomUUID());
            permission.setAuthority(authority);
            return permission;
        }).toList());
        role.setPermissions(permissions);
        consumer.setRoles(List.of(role));
        return consumer;
    }
    
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

    public ConsumerDetailsService(){
        inMemoryUserDetailsManager.createUser(root());
    }

    @Override
    public Consumer updatePassword(UserDetails user, String newPassword) {
        inMemoryUserDetailsManager.updatePassword(user, newPassword);
        Optional<Consumer> optional = service.login((Consumer)user);
        Consumer consumer = optional.orElseThrow(()-> new NoSuchElementException("User for \""+user.getUsername()+"\" could not be found."));
        consumer.setPassword(encoder.encode(newPassword));
        return service.update(consumer);
    }

    public boolean existsById(UUID id){
        return service.existsById(id);
    }

    public boolean existsByUsername(String username){
        if(Objects.equals(username, "root")){
            return true;
        }
        Consumer example = new Consumer();
        example.setUsername(username);
        return service.exists(example);
    }

    public boolean existsByEmail(String email){
        Consumer example = new Consumer();
        example.setEmail(email);
        return service.exists(example);
    }

    public boolean existsByPhone(String phone){
        Consumer example = new Consumer();
        example.setPhone(phone);
        return service.exists(example);
    }

    public boolean login(Consumer consumer){
        Consumer root = root();
        if(Objects.equals(root.getUsername(), consumer.getUsername()) && Objects.equals(root.getPassword(), consumer.getPassword()) ){
            return true;
        }
        return service.login(consumer).isPresent();
    }

    @Override
    public Consumer loadUserByUsername(String username) throws UsernameNotFoundException {
        if(Objects.equals("root", username)){
            return root();
        }
        Consumer example = new Consumer();
        example.setUsername(username);
        Optional<Consumer> optional = service.findOne(example);
        Consumer consumer = optional.orElseThrow(()-> new UsernameNotFoundException("Username for \""+example.getUsername()+"\" could not be found."));
        consumer.setPassword(encoder.encode(consumer.getPassword()));
        return consumer;
    }
}
