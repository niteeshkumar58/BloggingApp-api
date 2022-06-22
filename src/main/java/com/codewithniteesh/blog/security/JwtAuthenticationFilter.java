package com.codewithniteesh.blog.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        step 1. get token
        String requestToken = request.getHeader("Authorization");
        // requestToken will be in form of Bearer 456asd1325d....

        System.out.println(requestToken);
        String userName = null;
        String token = null;

        if(requestToken != null && requestToken.startsWith("Bearer")) {
            token = requestToken.substring(7); // actual token start at index 7 after "Bearer"

            try {
                userName = jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            }catch (ExpiredJwtException e){
                System.out.println(e);
            }catch (MalformedJwtException e){
                System.out.println(e);
            }
        }
        else {
            System.out.println("Jwt Token does not start with \"Bearer\"");
        }

        //step 2. Once we get the token we need to validate it
        System.out.println("userName: "+userName);
        System.out.println("context: "+SecurityContextHolder.getContext().getAuthentication());
        if(userName != null && SecurityContextHolder.getContext().getAuthentication()==null){

            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            if(jwtTokenHelper.validateToken(token, userDetails)){
                //sahi chal rha hai
                // ab authentication krna hai
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
            else{
                System.out.println("Jwt token is not valid");
            }

        }
        else{
            System.out.println("either username is null or security contex is not null");
        }

        filterChain.doFilter(request, response);

    }
}
