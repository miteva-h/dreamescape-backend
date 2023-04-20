package com.project.config.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.config.JwtAuthConstants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserDetailsService userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JwtAuthConstants.HEADER_STRING);
        if (header == null || !header.startsWith(JwtAuthConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken token = getToken(header);
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }

    public UsernamePasswordAuthenticationToken getToken(String header) throws JsonProcessingException {
//        // parse the token.
//        String user = JWT.require(Algorithm.HMAC256(JwtAuthConstants.SECRET.getBytes()))
//                .build()
//                .verify(header.replace(JwtAuthConstants.TOKEN_PREFIX, ""))
//                .getSubject();
//        if (user == null) {
//            return null;
//        }
//        UserDetailsDto userDetails = new ObjectMapper().readValue(user, UserDetailsDto.class);
//        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", Collections.singleton(userDetails.getRole()));
//    }

        String token = header.substring(7);
        DecodedJWT jwt = JWT.decode(token);
        Map<String, Claim> claim = jwt.getClaims();
        String username = claim.get("username").asString();
        String role = claim.get("role").asString();
        return new UsernamePasswordAuthenticationToken(username, "", Collections.singleton(new SimpleGrantedAuthority(role)));
    }

    public String getUsername(String header) throws JsonProcessingException {
        UsernamePasswordAuthenticationToken token = getToken(header);
        return token.getPrincipal().toString();
    }
}


