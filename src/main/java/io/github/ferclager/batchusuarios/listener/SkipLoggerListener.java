package io.github.ferclager.batchusuarios.listener;

import io.github.ferclager.batchusuarios.model.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SkipLoggerListener implements SkipListener<Usuario, Usuario> {

    @Override
    public void onSkipInRead(Throwable t) {
        log.warn("SKIP en lectura: {}", t.getMessage());
    }

    @Override
    public void onSkipInWrite(Usuario u, Throwable t) {
        log.warn("SKIP en escritura: {} -? {}", u, t.getMessage());
    }

    @Override
    public void onSkipInProcess(Usuario u, Throwable t) {
        log.warn("SKIP en procesamiento: {} -? {}", u, t.getMessage());
    }
}
