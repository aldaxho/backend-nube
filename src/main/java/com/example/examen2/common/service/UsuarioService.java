package com.example.examen2.common.service;

import com.example.examen2.common.model.Rol;
import com.example.examen2.common.model.Usuario;
import com.example.examen2.common.repository.RolRepository;
import com.example.examen2.common.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .filter(usuario -> !usuario.getRol().getNombre().equalsIgnoreCase("DOCENTE"))
                .collect(Collectors.toList());
    }

    public Usuario createUsuario(Usuario usuario) {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    public Usuario getUsuarioById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (usuario.getRol().getNombre().equalsIgnoreCase("DOCENTE")) {
            throw new RuntimeException("No se puede obtener un usuario con rol DOCENTE");
        }
        return usuario;
    }

    public Usuario updateUsuario(Long id, Usuario usuarioDetails) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(usuarioDetails.getNombre());
        usuario.setApellido(usuarioDetails.getApellido());
        usuario.setEmail(usuarioDetails.getEmail());
        usuario.setContrasena(passwordEncoder.encode(usuarioDetails.getContrasena()));

        // Actualizar el rol del usuario
        if (usuarioDetails.getRol() != null) {
            Optional<Rol> rolOptional = rolRepository.findById(usuarioDetails.getRol().getId());
            if (rolOptional.isPresent()) {
                usuario.setRol(rolOptional.get());
            } else {
                throw new RuntimeException("Rol no encontrado");
            }
        }

        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioRepository.delete(usuario);
    }
}
