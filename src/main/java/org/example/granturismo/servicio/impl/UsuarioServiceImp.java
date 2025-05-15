package org.example.granturismo.servicio.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.granturismo.dtos.UsuarioDTO;
import org.example.granturismo.excepciones.ModelNotFoundException;
// Puedes considerar una excepción más específica si quieres,
// por ahora usaremos ModelNotFoundException con un mensaje claro
// import org.example.granturismo.excepciones.AccountNotVerifiedException; // Ejemplo de excepción personalizada
import org.example.granturismo.mappers.UsuarioMapper; // Asumo que tienes un mapper
import org.example.granturismo.modelo.Rol;
import org.example.granturismo.modelo.Usuario;
import org.example.granturismo.modelo.UsuarioRol;
import org.example.granturismo.repositorio.ICrudGenericoRepository;
import org.example.granturismo.repositorio.IUsuarioRepository;
import org.example.granturismo.servicio.IEmailService;
import org.example.granturismo.servicio.IRolService;
import org.example.granturismo.servicio.IUsuarioRolService;
import org.example.granturismo.servicio.IUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import java.nio.CharBuffer;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServiceImp extends CrudGenericoServiceImp<Usuario, Long> implements IUsuarioService {
    private final IUsuarioRepository repo;

    private final IRolService rolService;
    private final IUsuarioRolService iurService;

    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper userMapper; // Asumo que tienes un mapper
    private final IEmailService emailService;

    @Override
    protected ICrudGenericoRepository<Usuario, Long> getRepo() {
        return repo;
    }



    @Override
    public UsuarioDTO login(UsuarioDTO.CredencialesDto credentialsDto) {
        Usuario user = repo.findOneByUser(credentialsDto.user())
                .orElseThrow(() -> new ModelNotFoundException("Unknown user", HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(CharBuffer.wrap(credentialsDto.clave()), user.getClave())) {
            throw new ModelNotFoundException("Invalid password", HttpStatus.BAD_REQUEST); // O una excepción más específica de contraseña
        }

        // *** MODIFICACIÓN AQUÍ: Verificar si la cuenta está verificada ***
        if (!user.isVerificado()) {
            // Puedes usar una excepción personalizada como AccountNotVerifiedException
            // throw new AccountNotVerifiedException("Account not verified. Please check your email for verification link.");
            // O reutilizar ModelNotFoundException con un estado HTTP diferente (ej. UNAUTHORIZED)
            throw new ModelNotFoundException("Account not verified. Please check your email for verification link.", HttpStatus.UNAUTHORIZED);
        }
        // *** FIN MODIFICACIÓN ***


        // Si llegamos aquí, las credenciales son correctas Y la cuenta está verificada.
        return userMapper.toDTO(user);
    }


    @Override
    public UsuarioDTO registerUser(UsuarioDTO.UsuarioCrearDto userDto) {
        log.info("Registrando usuario: {}", userDto.user());

        if (repo.findOneByUser(userDto.user()).isPresent()) {
            throw new ModelNotFoundException("Usuario ya existe", HttpStatus.BAD_REQUEST);
        }

        Usuario user = userMapper.toEntityFromCADTO(userDto);
        user.setClave(passwordEncoder.encode(CharBuffer.wrap(userDto.clave())));
        user.setTokenVerificacion(UUID.randomUUID().toString());
        user.setVerificado(false);

        Usuario savedUser = repo.save(user);
        user.setTokenVerificacion(UUID.randomUUID().toString());
        // aquí mismo:
        user.setFechaExpiracionToken(LocalDateTime.now().plusHours(24));
        user.setVerificado(false);

        // Asignar rol USER
        Rol rol = rolService.getByNombre(Rol.RolNombre.USER)
                .orElseThrow(() -> new ModelNotFoundException("Rol USER no encontrado", HttpStatus.NOT_FOUND));
        iurService.save(UsuarioRol.builder().usuario(savedUser).rol(rol).build());

        // Enviar correo
        emailService.enviarCorreoVerificacion(savedUser.getUser(), savedUser.getTokenVerificacion());

        return userMapper.toDTO(savedUser);
    }

    @Override
    public UsuarioDTO registerByAdmin(UsuarioDTO.UsuarioCrearConRolDto userDto) {
        log.info("ADMIN attempting to register user: {} with role: {}", userDto.user(), userDto.rol());

        Optional<Usuario> optionalUser = repo.findOneByUser(userDto.user());
        if (optionalUser.isPresent()) {
            log.warn("Username already exists: {}", userDto.user());
            throw new ModelNotFoundException("The username is already in use", HttpStatus.BAD_REQUEST);
        }

        Usuario user = userMapper.toEntityFromAdminDTO(userDto); // Asumiendo que el mapper maneja user y clave
        user.setClave(passwordEncoder.encode(CharBuffer.wrap(userDto.clave())));

        // Los usuarios creados por admin usualmente se consideran verificados de inmediato,
        // o el admin se encarga de la verificación fuera de este flujo.
        // Aquí, asumimos que un admin crea cuentas ya verificadas por defecto.
        user.setVerificado(true);
        user.setTokenVerificacion(null); // No necesita token si ya está verificado
        user.setFechaExpiracionToken(null); // No necesita fecha de expiración

        Usuario savedUser = repo.save(user);

        Rol.RolNombre rolNombre;
        try {
            rolNombre = Rol.RolNombre.valueOf(userDto.rol().toUpperCase());
        } catch (IllegalArgumentException ex) {
            log.error("Invalid role provided: {}", userDto.rol());
            throw new ModelNotFoundException("Invalid role", HttpStatus.BAD_REQUEST);
        }

        Rol rol = rolService.getByNombre(rolNombre)
                .orElseThrow(() -> new ModelNotFoundException("Role not found", HttpStatus.NOT_FOUND));

        iurService.save(UsuarioRol.builder()
                .usuario(savedUser)
                .rol(rol)
                .build());

        log.info("User created successfully by ADMIN: {} with role: {}", savedUser.getUser(), rol.getNombre());
        return userMapper.toDTO(savedUser);
    }

    @Override
    public Usuario verificarCuenta(String token) {
        Usuario user = repo.findByTokenVerificacion(token)
                .orElseThrow(() -> new ModelNotFoundException("Invalid or expired token", HttpStatus.NOT_FOUND)); // O una excepción más específica de token

        if (user.isVerificado()) {
            throw new ModelNotFoundException("Account already verified", HttpStatus.BAD_REQUEST); // O una excepción más específica
        }

        // *** LA LÓGICA DE EXPIRACIÓN YA ESTABA AQUÍ, AHORA ES FUNCIONAL ***
        if (user.getFechaExpiracionToken() != null && LocalDateTime.now().isAfter(user.getFechaExpiracionToken())) {
            // Limpiar información del token expirado para evitar reintentos
            user.setTokenVerificacion(null);
            user.setFechaExpiracionToken(null);
            repo.save(user); // Guardar el estado después de la expiración
            throw new ModelNotFoundException("Token has expired. Please request a new one.", HttpStatus.BAD_REQUEST); // O una excepción más específica
        }
        // *** FIN LÓGICA EXPIRACIÓN ***


        user.setVerificado(true);
        user.setTokenVerificacion(null); // ya no es necesario
        user.setFechaExpiracionToken(null); // ya no es necesario
        repo.save(user);

        log.info("User {} verified successfully", user.getUser());
        return user;
    }

    @Override
    public Optional<UsuarioDTO> findByUser(String username) {
        return repo.findOneByUser(username).map(userMapper::toDTO);
    }

}