package io.github.ferclager.batchusuarios.processor;

import io.github.ferclager.batchusuarios.model.Usuario;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class UsuarioProcessor implements ItemProcessor<Usuario, Usuario> {
    @Nullable
    @Override
    public Usuario process(@NonNull Usuario item) throws Exception {
        if (item.edad() < 18) {
            return null;
        }
        if (item.email() == null || !item.email().contains("@")) {
            throw new IllegalArgumentException("Email inválido: " + item.email());
        }
        String nombre = item.nombre().toUpperCase(Locale.ROOT);
        String email = item.email().toLowerCase();
        return new Usuario(nombre, email, item.edad());
    }
}
