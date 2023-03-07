package com.platzi.market.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    private static final String KEY = "pl4tz1";

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, KEY).compact();
    }

    /**
     * Descripción: Método para validar si el token es correcto.
     *
     * Verificar que el token este creado para el usuario y que no haya expirado
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        //Validamos que el usuario de la peticion sea el mismo que el del token y que el token no haya expirado
        return userDetails.getUsername().equals(extractUsername(token)) && !isTokenExpired(token);
    }

    /**
     * Descripción: Método que se encarga de extraer el usuario
     */
    public String extractUsername(String token) {
        //Solicitamos el Subject ya que ahí se encuentra el usuario
        return getClaims(token).getSubject();
    }

    /**
     * Descripcoón: Método que indica si el token ya expiró.
     */
    public boolean isTokenExpired(String token) {
        //el metodo getExpiration no manda la fecha de expiración y validamos si es antes de la fecha actual
        return getClaims(token).getExpiration().before(new Date());
    }

    /**
     *Descripción: Método que retorna los Claims que son los objetos de JWT
     */
    private Claims getClaims(String token) {
        //le damos la KEY y el token para que nos mande los Claims de ese token
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
    }
}
