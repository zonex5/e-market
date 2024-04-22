package xyz.toway.emarket.security;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import xyz.toway.emarket.repository.UserDetailsRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserDetailsCashService {
    private final UserDetailsRepository userDetailsRepository;

    private final Cache<String, JwtUserDetails> cache;

    public UserDetailsCashService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;

        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();
    }

    public JwtUserDetails getUser(String username) {
        var item = cache.getIfPresent(username);
        if (item == null) {
            log.warn("*** load user from DB **");
            item = userDetailsRepository.getByUsername(username)
                    .map(dbUser -> {
                        List<GrantedAuthority> authorities = dbUser.getRoles().stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());
                        return JwtUserDetails.builder()
                                .username(dbUser.getUsername())
                                .firstName(dbUser.getFirstName())
                                .lastName(dbUser.getLastName())
                                .enabled(true)
                                .authorities(authorities)
                                .build();
                    }).block();
            if (item != null) {
                cache.put(username, item);
            }
        }
        return item;
    }
}
