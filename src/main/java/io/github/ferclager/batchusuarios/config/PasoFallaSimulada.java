package io.github.ferclager.batchusuarios.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PasoFallaSimulada implements Tasklet {

    @Nullable
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext ctx = chunkContext.getStepContext()
                .getStepExecution()
                .getExecutionContext();

        int intentos = ctx.getInt("intentos", 0);
        ctx.putInt("intentos", intentos + 1);   // se persiste aunque el step falle

        if (intentos == 0) {
            // Primer intento: simulamos un fallo (p.ej. se cayó un sistema externo).
            throw new IllegalStateException("Fallo simulado en el primer intento");
        }

        // Reanudación: ya es el segundo intento -> seguimos adelante.
        log.info(">>> Reanudado correctamente en el intento {}", intentos + 1);
        return RepeatStatus.FINISHED;

    }
}
