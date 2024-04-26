package xyz.toway.emarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.toway.emarket.security.SecurityService;
import xyz.toway.emarket.security.JwtUserModel;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/security")
public class SecurityController {

    private final SecurityService securityService;

    @GetMapping("/open")
    private String testOpen() {
        return "Open!!!";
    }

    @GetMapping("/closed")
    private String testClosed() {
        return "Closed!!!";
    }

    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody JwtUserModel user) {
        return ResponseEntity.ok(securityService.login(user));
    }

    @PostMapping("/register")
    private ResponseEntity<?> register(@RequestBody JwtUserModel user) {
        return ResponseEntity.ok(securityService.register(user).block());  //todo
    }
}
