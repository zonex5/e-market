package xyz.toway.emarket.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import xyz.toway.emarket.entity.CustomerEntity;
import xyz.toway.emarket.entity.UserDetailsEntity;
import xyz.toway.emarket.entity.UserEntity;
import xyz.toway.emarket.model.LoginResultModel;
import xyz.toway.emarket.repository.CustomerRepository;
import xyz.toway.emarket.repository.UserDetailsRepository;
import xyz.toway.emarket.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {

    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${application.jwt.secret}")
    private String secretKey;

    @Value("${application.jwt.expire}")
    private Duration expireTime;

    private final UserDetailsRepository userDetailsRepository;
    private final UserDetailsCashService cashService;

    private final PasswordEncoder passwordEncoder;

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    @PostConstruct
    private void init() {

    }

    public Authentication getAuthentication(String token) {
        var user = loadUserByUsername(getUsername(token));
        return user != null ? new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()) : null;
    }

    public String getUsername(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String token = Objects.requireNonNullElse(request.getHeader("Authorization"), "");
        return token.startsWith(BEARER_PREFIX) ? token.replace(BEARER_PREFIX, "") : token;
    }

    public boolean validateExpirationToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .after(new Date());
    }

    @Deprecated
    public String createToken(Authentication authentication) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        String user = (String) authentication.getPrincipal();
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        var token = Jwts.builder()
                .expiration(new Date(System.currentTimeMillis() + expireTime.toMillis()))
                .issuedAt(new Date())
                .subject(user)
                //.issuer(appName)
                .claim("scopes", roles)
                .signWith(key)
                .compact();
        return BEARER_PREFIX + token;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.warn("**** load user by username **");
        return cashService.getUser(username);
    }

    public String createToken(String username, List<String> roles) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        var token = Jwts.builder()
                .expiration(new Date(System.currentTimeMillis() + expireTime.toMillis()))
                .issuedAt(new Date())
                .subject(username)
                .claim("scopes", roles)
                .signWith(key)
                .compact();
        return BEARER_PREFIX + token;
    }

    public LoginResultModel login(JwtUserModel user) {

        if (user == null || user.getPassword() == null || user.getUsername() == null) {
            return new LoginResultModel(LoginStatusEnum.OTHER);
        }

        UserDetailsEntity dbUser = userDetailsRepository.getByUsername(user.getUsername()).block();  //todo

        if (dbUser == null) {
            return new LoginResultModel(LoginStatusEnum.USER_NOT_FOUND);
        }

        if (passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            String token = createToken(dbUser.getUsername(), dbUser.getRoles());
            return new LoginResultModel(token, dbUser.getFirstName(), dbUser.getLastName(), dbUser.getUuid(), dbUser.isAdmin(), LoginStatusEnum.SUCCESS);
        } else {
            return new LoginResultModel(LoginStatusEnum.INVALID_PASSWORD);
        }
    }

    public Mono<LoginResultModel> register(JwtUserModel user) {

        if (user == null || user.getPassword() == null || user.getUsername() == null) {
            return Mono.just(new LoginResultModel(LoginStatusEnum.OTHER));
        }

        boolean userExists = Boolean.TRUE.equals(userRepository.existsByUsername(user.getUsername()).block()); //todo
        if (userExists) {
            return Mono.just(new LoginResultModel(LoginStatusEnum.USER_ALREADY_EXISTS));
        }

        // create new user
        String password = passwordEncoder.encode(user.getPassword());
        UserEntity userEntity = new UserEntity(user.getUsername(), password, true);

        return userRepository.save(userEntity)
                .flatMap(usr -> {
                    String token = createToken(usr.getUsername(), List.of("ROLE_CUSTOMER"));
                    CustomerEntity customerEntity = new CustomerEntity(usr.getId(), UUID.randomUUID().toString(), "", "", "", user.getUsername());
                    return customerRepository.save(customerEntity)
                            .map(customer -> new LoginResultModel(token, customer.getFirstName(), customer.getLastName(), customer.getUuid(), false, LoginStatusEnum.SUCCESS));
                });
    }
}
