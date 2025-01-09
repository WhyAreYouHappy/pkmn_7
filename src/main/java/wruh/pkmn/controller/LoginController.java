package wruh.pkmn.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import wruh.pkmn.models.User;
import wruh.pkmn.services.JwtService;

import java.util.Base64;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class LoginController {

    private final JwtService jwtService;

    @PostMapping("/success")
    public String successPage(HttpServletResponse response,
                              Authentication authentication) {
        String token = jwtService.createToken(authentication.getName(), authentication.getAuthorities()
                .stream().collect(Collectors.toUnmodifiableList()));
        response.addCookie(new Cookie("JWT", Base64.getEncoder().encodeToString(token.getBytes())));
        return "Успешная авторизация";
    }

    @PostMapping("/reg")
    public String registration(@RequestBody User user) {
        if(user.getPassword() != null && user.getUsername() != null) {
            jwtService.registerUser(user.getUsername(), user.getPassword());
            return "201.";
        } else {
            return "400.";
        }
    }
}
