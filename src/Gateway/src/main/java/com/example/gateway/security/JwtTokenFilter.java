//import com.example.IdentityProvider.security.TokenAuthenticationFilter;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class JwtTokenFilter extends OncePerRequestFilter {
//
//    private final TokenAuthenticationFilter tokenAuthenticationFilter;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        String token = extractTokenFromRequest(request); // Реализуйте метод для извлечения токена из запроса
//        if (token != null) {
//            UserDetails userDetails = extractUserDetailsFromToken(token); // Реализуйте метод для извлечения информации о пользователе из токена
//            if (userDetails != null) {
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//    private String extractTokenFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7); // Remove "Bearer " prefix
//        }
//        return null;
//    }
//
//    private UserDetails extractUserDetailsFromToken(String token) {
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey("v9y$B&E)H@MbQeThWmZq4t7w!z%C*F-JaNdRfUjXn2r5u8x/A?D(G+KbPeShVkYp") // Replace with your actual secret key
//                    .parseClaimsJws(token)
//                    .getBody();
//
//            // Extract user information from claims
//            String username = claims.getSubject(); // Assuming username is stored in the subject claim
//
//            if (username != null) {
//                return new CustomUserDetails(username); // CustomUserDetails is your UserDetails implementation
//            }
//        } catch (Exception e) {
//            // Handle exceptions (e.g., token expired, invalid token, etc.) as needed
//        }
//        return null;
//    }
//}