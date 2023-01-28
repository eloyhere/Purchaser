package team.item.purchaser.forum.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import team.item.purchaser.forum.entity.Consumer;

import java.util.Objects;

@Service(value = "authorize")
public class Authorize {

    public boolean hasAuthority(String authority){
        if(Objects.equals(authority, "anonymous")){
            return true;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Consumer consumer = (Consumer) authentication.getPrincipal();
        return consumer.getAuthorities().contains(authority);
    }
}
