package io.github.ferclager.batchusuarios.config;

import io.github.ferclager.batchusuarios.model.Usuario;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.RecordFieldSetMapper;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
public class BatchConfig {

    // Lee usuarios.csv (en src/main/resources/data/) y mapea cada línea a un Usuario.
    @Bean
    public ItemReader<Usuario> reader() {
        return new FlatFileItemReaderBuilder<Usuario>()
                .name("usuarioReader")
                .resource(new ClassPathResource("data/usuarios.csv"))
                .linesToSkip(1)
                .delimited().names("nombre", "email", "edad")
                .fieldSetMapper(new RecordFieldSetMapper<>(Usuario.class))
                .build();
    }

    // De momento devuelve el usuario tal cual
    @Bean
    public ItemProcessor<Usuario, Usuario> processor() {
        return usuario -> usuario;
    }

    // Writer temporal por consola para comprobar que el mapeo funciona.
    @Bean
    public ItemWriter<Usuario> writer() {
        return lote -> {
            lote.forEach(System.out::println);
        };
    }

    @Bean
    public Step pasoImportar(JobRepository jobRepository,
                             PlatformTransactionManager tx,
                             ItemReader<Usuario> reader,
                             ItemProcessor<Usuario, Usuario> processor,
                             ItemWriter<Usuario> writer) {
        return new StepBuilder("pasoImportar", jobRepository)
                .<Usuario, Usuario>chunk(3, tx)   // tamaño del lote = 3
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
