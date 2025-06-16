package com.example.demo.controller;

import com.example.demo.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.List;

@Controller
public class UsuarioController implements UserDetails {

    private Usuario usuario;

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@ModelAttribute Usuario usuario, Model model) {
        if(usuarioRepository.findByUsername(usuario.getUsername()).isEmpty()) {
            Usuario user = new Usuario();
            // Guarda el usuario
            user.setUsername(usuario.getUsername());
            user.setPassword(usuario.getPassword());
            user.setRol(usuario.getRol());
            usuarioRepository.save(user);
            // Volvemos a home
            return "redirect:/";
        } else {
            model.addAttribute("error", "usuario ya existe");
            return "altaUsuario";
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
