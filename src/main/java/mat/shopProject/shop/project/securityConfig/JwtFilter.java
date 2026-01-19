package mat.shopProject.shop.project.securityConfig;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mat.shopProject.shop.project.exception.ErrorResponse;
import mat.shopProject.shop.project.models.AuthenticatedUser;
import mat.shopProject.shop.project.models.UserModel;
import mat.shopProject.shop.project.repositories.UserRepo;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    private final ApplicationContext context;

    private final UserRepo userRepo;

    private final UserDetailsService userDetailsService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(7);

        try {
            String username = jwtService.extractUserName(token);


            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtService.validateToken(token, userDetails)) {
                    UserModel userFromDb = userRepo.findByEmail(username)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));


                    if (userFromDb == null) {
                        throw new UsernameNotFoundException("User not found");
                    }


                    AuthenticatedUser myUser = new AuthenticatedUser(
                            userFromDb.getId(),
                            userFromDb.getUsername(),
                            userFromDb.getRole(),
                            userFromDb.getPassword()
                    );

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(myUser, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            sendError(response, HttpStatus.UNAUTHORIZED, "AUTH_001", "Invalid or expired token");
            return;
        }
    }

    private void sendError(HttpServletResponse response, HttpStatus status, String code, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ErrorResponse error = new ErrorResponse(code, message);
        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}
