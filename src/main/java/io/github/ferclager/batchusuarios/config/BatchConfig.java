package io.github.ferclager.batchusuarios.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
public class BatchConfig {

    // Lee de uno en uno. Aquí desde una lista; en el vídeo 04 será un CSV real.
    @Bean
    public ItemReader<String> reader() {
        return new ListItemReader<>(List.of("iris", "luis", "marta", "pedro", "sara"));
    }

    // Transforma cada item de uno en uno (aquí: a mayúsculas).
    @Bean
    public ItemProcessor<String, String> processor() {
        return nombre -> nombre.toUpperCase();
    }

    // Recibe el LOTE completo (Chunk) y lo escribe de una vez. Aquí por consola.
    @Bean
    public ItemWriter<String> writer() {
        return lote -> {
            System.out.println("--- escribiendo lote de " + lote.size() + " items ---");
            lote.forEach(System.out::println);
        };
    }

    // Step por chunks: junta 3 items y entonces llama al writer (1 commit por lote).
    @Bean
    public Step pasoImportar(JobRepository jobRepository,
                             PlatformTransactionManager tx,
                             ItemReader<String> reader,
                             ItemProcessor<String, String> processor,
                             ItemWriter<String> writer) {
        return new StepBuilder("pasoImportar", jobRepository)
                .<String, String>chunk(3, tx)   // tamaño del lote = 3
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job importarUsuariosJob(JobRepository jobRepository, Step pasoImportar) {
        return new JobBuilder("importarUsuariosJob", jobRepository)
                .start(pasoImportar)
                .build();
    }
}
