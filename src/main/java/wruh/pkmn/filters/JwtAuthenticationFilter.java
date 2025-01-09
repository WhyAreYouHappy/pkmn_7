package wruh.pkmn.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import wruh.pkmn.services.JwtService;

import java.io.IOException;
import java.util.Base64;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = null;

            String authHeader = request.getHeader("Authorization");
            if(authHeader != null && authHeader.startsWith("Morpeko")) {
                jwt = authHeader.substring(7);
            } else if(authHeader != null && authHeader.startsWith("Basic")) {
                return ;
            }

            if(request.getCookies() != null) {
                for(Cookie cookie : request.getCookies()) {
                    if("JWT".equals(cookie.getName())) {
                        jwt = new String(Base64.getDecoder().decode(cookie.getValue()));
                    }
                }
            }
            if(jwt == null) {
                filterChain.doFilter(request, response);
                return;
            }

            UserDetails user = jwtService.verify(jwt);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch(Exception ex) {
            SecurityContextHolder.clearContext();
            throw new ServletException(ex);
        }
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromHeaderOrCookie(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Morpeko")) {
            return authHeader.substring(7);
        } else if(authHeader != null && authHeader.startsWith("Basic")) {
            return null;
        }

        if(request.getCookies() != null) {
            for(Cookie cookie : request.getCookies()) {
                if("JWT".equals(cookie.getName())) {
                    return new String(Base64.getDecoder().decode(cookie.getValue()));
                }
            }
        }
        return null;
    }
}
