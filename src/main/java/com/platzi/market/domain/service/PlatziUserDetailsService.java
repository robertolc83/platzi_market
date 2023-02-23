package com.platzi.market.domain.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
//SERVICIO PARA SPRINNG SECURITY DONDE DEFINIMOS UN USUARIO Y CONTRASEÑA
public class PlatziUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("roberto","{noop}platzi", new ArrayList<>()); //En el arrayList son los tipos de errores del usuario
    }
}
