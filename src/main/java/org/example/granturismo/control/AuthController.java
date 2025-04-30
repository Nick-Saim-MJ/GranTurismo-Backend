package org.example.granturismo.control;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.UsuarioDTO;
import org.example.granturismo.dtos.UsuarioLoginRespuestaDTO;
import org.example.granturismo.security.JwtTokenUtil;
import org.example.granturismo.security.JwtUserDetailsService;
import org.example.granturismo.servicio.IUsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class AuthController {
    private final IUsuarioService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;

    @PostMapping("/login")
    public ResponseEntity<UsuarioLoginRespuestaDTO> login(@RequestBody @Valid UsuarioDTO.CredencialesDto credentialsDto, HttpServletRequest request) {
        UsuarioDTO userDto = userService.login(credentialsDto);
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(credentialsDto.user());
        String token = jwtTokenUtil.generateToken(userDetails);
        request.getSession().setAttribute("USER_SESSION", userDto.getUser());

        UsuarioLoginRespuestaDTO respuesta = new UsuarioLoginRespuestaDTO(userDto.getIdUsuario(), userDto.getUser(), token);
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioLoginRespuestaDTO> register(@RequestBody @Valid UsuarioDTO.UsuarioCrearDto user) {
        UsuarioDTO createdUser = userService.register(user);
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user.user());
        String token = jwtTokenUtil.generateToken(userDetails);

        UsuarioLoginRespuestaDTO respuesta = new UsuarioLoginRespuestaDTO(createdUser.getIdUsuario(), createdUser.getUser(), token);
        return ResponseEntity.created(URI.create("/users/" + createdUser.getUser())).body(respuesta);
    }

}

