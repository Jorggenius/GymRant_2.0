package com.appMoviles.GymRat.controller;

import com.appMoviles.GymRat.dto.ActualizarMedidasRequest;
import com.appMoviles.GymRat.entities.Usuario;
import com.appMoviles.GymRat.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public Usuario registrar(@RequestBody @Valid Usuario usuario) {
        return usuarioService.registrarUsuario(usuario);
    }

    @GetMapping("/{id}")
    public Usuario obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    @PatchMapping("/{id}/medidas")
    public Usuario actualizarMedidas(
            @PathVariable Long id,
            @RequestBody @Valid ActualizarMedidasRequest request
    ) {
        return usuarioService.actualizarMedidas(id, request);
    }
}