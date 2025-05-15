package org.example.granturismo.control;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.granturismo.dtos.UsuarioDTO;
import org.example.granturismo.dtos.UsuarioLoginRespuestaDTO;
import org.example.granturismo.dtos.VerificarCuentaDTO;
import org.example.granturismo.modelo.Usuario;
import org.example.granturismo.security.JwtTokenUtil;
import org.example.granturismo.security.JwtUserDetailsService;
import org.example.granturismo.security.PermitRoles;
import org.example.granturismo.servicio.IUsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
@Slf4j
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
        UsuarioDTO createdUser = userService.registerUser(user); // Esto funciona

        try {
            final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user.user());
            String token = jwtTokenUtil.generateToken(userDetails);

            UsuarioLoginRespuestaDTO respuesta = new UsuarioLoginRespuestaDTO(createdUser.getIdUsuario(), createdUser.getUser(), token);
            return ResponseEntity.created(URI.create("/users/" + createdUser.getUser())).body(respuesta);

        } catch (Exception e) {
            // *** AÑADE ESTO TEMPORALMENTE PARA DEPURAR ***
            log.error("Ocurrió un error inesperado después del registro exitoso del usuario", e);
            // Opcional: relanzar la excepción para ver si aparece en otros logs
            throw new RuntimeException("Error al finalizar el registro", e);
            // *******************************************
        }
    }


    //@PermitRoles("ADMIN")
    @PostMapping("/create")
    public ResponseEntity<UsuarioDTO> createUserByAdmin(@RequestBody @Valid UsuarioDTO.UsuarioCrearConRolDto user) {
        UsuarioDTO createdUser = userService.registerByAdmin(user);
        return ResponseEntity.created(URI.create("/users/" + createdUser.getUser())).body(createdUser);
    }

    @PostMapping("/verificar")
    public ResponseEntity<String> verificarCuenta(@RequestBody VerificarCuentaDTO dto) {
        userService.verificarCuenta(dto.token());
        return ResponseEntity.ok("Cuenta verificada correctamente");
    }

}

