package com.mill.garantias.config;

import com.mill.garantias.model.Usuario;
import com.mill.garantias.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Inicializa os usuários padrão se não existirem.
 * Isso garante que as senhas sejam corretamente codificadas com BCrypt.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Cria usuário admin se não existir
        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setNomeCompleto("Administrador");
            admin.setPerfil("ADMIN");
            usuarioRepository.save(admin);
        }

        // Cria usuário qualidade se não existir
        if (usuarioRepository.findByUsername("qualidade").isEmpty()) {
            Usuario qualidade = new Usuario();
            qualidade.setUsername("qualidade");
            qualidade.setPassword(passwordEncoder.encode("admin123"));
            qualidade.setNomeCompleto("Analista de Qualidade");
            qualidade.setPerfil("QUALIDADE");
            usuarioRepository.save(qualidade);
        }
    }
}
