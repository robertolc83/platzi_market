package com.platzi.market.web.security.filter;

import com.platzi.market.domain.service.PlatziUserDetailsService;
import com.platzi.market.web.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Descrpción: Filtro que se encarga de atrapar todas las peticiones que recibe mi aplicacion
 * y verifique si el JWT es correcto
 *
 * OncePerRequestFilter nos ayuda a que se ejecute cada que existe una petición
 */
@Component
public class JwtFilterRequest extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PlatziUserDetailsService platziUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Verificamos si lo que viene en el encabezado de la petición es un token y si es correcto
        String authorizationHeader = request.getHeader("Authorization");

        //Validamos que el token no sea nulo y que comience con Bearer
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {

            //Extraemos el token usamos substring para quitar los primeros 7 caracteres que son "Bearer "
            String jwt = authorizationHeader.substring(7);

            //Extraemos el usuario con el metodo que creamos en JWTUtil
            String username = jwtUtil.extractUsername(jwt);

            //Validamos que el user no sea nulo y QUE NO HAYA ENTRADO A LA APLICACION ANTERIORMENTE (QUE EL USUARIO NO SE HAYA AUTENTICADO ANTES)
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                //Vamos a obtener el User Detail para validarlo
                UserDetails userDetails = platziUserDetailsService.loadUserByUsername(username);

                //Vamos a validar si el jwt es correcto
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    //Le asignamos los detalles de la conexión y en request se informan datos como el navegador que se esta usando a que horario se conectó, SO, etc
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        //El filtro es evaluado
        filterChain.doFilter(request, response);
    }
}
