package ricksciascia.ow_5v5_build.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import ricksciascia.ow_5v5_build.entities.User;
import ricksciascia.ow_5v5_build.exceptions.UnauthorizedException;
import ricksciascia.ow_5v5_build.services.AuthService;
import ricksciascia.ow_5v5_build.services.UsersService;

import java.io.IOException;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UsersService usersService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

// ------------ AUTHENTICATION ------------

        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
//            throw new UnauthorizedException("Inserire il token nell'Authorization header nel formato corretto");
        filterChain.doFilter(request,response);
        return;
        }
        String accessToken = authHeader.replace("Bearer ","");
        jwtTools.verifyToken(accessToken);

// ------------ AUTHORIZATION ------------

        Long userId = jwtTools.extractIdFromToken(accessToken);
        User authenticatedUser = this.usersService.findById(userId);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                authenticatedUser,null,authenticatedUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request,response);
    }

}
