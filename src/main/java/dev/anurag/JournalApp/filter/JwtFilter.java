package dev.anurag.JournalApp.filter;



import dev.anurag.JournalApp.service.UserService;
import dev.anurag.JournalApp.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtFilter  extends OncePerRequestFilter{
    @Autowired
    private UserDetailsService userDetailsService;  //we have implemented this interface in userdetailsimpl class but then also autowiring the interface. Why? Beacuse of polymorphism and best practice.

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }
        if (username != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
         // UserDetails userDetails1 = userService.findByUsername1(username);
            //I changed the return type from Users to UserDetails in service layer but still the above line is wrong because loadUserByUsername return more things then only username like password and other things.
            if (jwtUtil.validateToken(jwt)) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        response.addHeader("admin", "anurag");//this will add in the final response also because it is linked with the HttpServletResponse. So it will bind with the ResponseEntity we are using in the controller.
        // So as we know that Response Entity is used to customize the information with the response, so admin will also get added in the response header.
        response.getHeaderNames();
        chain.doFilter(request, response);
    }
}








































/*
User logs in → send email + password

Authenticate using Spring AuthenticationManager

Generate JWT with email + custom fields

Client sends request with Bearer token

JwtFilter extracts email from token

Load user using loadUserByUsername(email)

Set authentication in SecurityContextHolder

Controller gets logged in user using:
* */