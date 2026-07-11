package io.github.ferclager.batchusuarios.config;

import io.github.ferclager.batchusuarios.model.Usuario;
import io.github.ferclager.batchusuarios.processor.UsuarioProcessor;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

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
                             UsuarioProcessor processor,
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
