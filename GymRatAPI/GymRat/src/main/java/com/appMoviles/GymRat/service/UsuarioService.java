package com.appMoviles.GymRat.service;

import com.appMoviles.GymRat.dto.ActualizarMedidasRequest;
import com.appMoviles.GymRat.entities.Usuario;
import com.appMoviles.GymRat.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con ese email.");
        }

        if (usuario.getRol() == null || usuario.getRol().isBlank()) {
            usuario.setRol("USER");
        }

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    public Usuario actualizarMedidas(Long id, ActualizarMedidasRequest request) {
        Usuario usuario = obtenerUsuarioPorId(id);

        usuario.setPeso(request.getPeso());
        usuario.setEstatura(request.getEstatura());
        usuario.setEdad(request.getEdad());

        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuarioComoAdmin(Long id, Usuario datos) {
        Usuario usuario = obtenerUsuarioPorId(id);

        if (usuarioRepository.existsByEmailAndIdNot(datos.getEmail(), id)) {
            throw new RuntimeException("Ese email ya está siendo usado por otro usuario.");
        }

        usuario.setNombre(datos.getNombre());
        usuario.setEmail(datos.getEmail());
        usuario.setPeso(datos.getPeso());
        usuario.setEstatura(datos.getEstatura());
        usuario.setEdad(datos.getEdad());

        if (datos.getPassword() != null && !datos.getPassword().isBlank()) {
            usuario.setPassword(datos.getPassword());
        }

        if (datos.getRol() != null && !datos.getRol().isBlank()) {
            usuario.setRol(datos.getRol());
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario crearUsuarioComoAdmin(Usuario usuario) {
        return registrarUsuario(usuario);
    }

    public void eliminarUsuario(Long id) {
        Usuario usuario = obtenerUsuarioPorId(id);
        usuarioRepository.delete(usuario);
    }
}