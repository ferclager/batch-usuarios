package io.github.ferclager.batchusuarios.config;

import io.github.ferclager.batchusuarios.model.Usuario;
import io.github.ferclager.batchusuarios.processor.UsuarioProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.RecordFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    @Bean
    @StepScope
    public FlatFileItemReader<Usuario> reader(@Value("#{jobParameters['rutaFichero']}") String fichero) {
        return new FlatFileItemReaderBuilder<Usuario>()
                .name("usuarioReader")
                .resource(new FileSystemResource(fichero))
                .linesToSkip(1)
                .delimited().names("nombre", "email", "edad")
                .fieldSetMapper(new RecordFieldSetMapper<>(Usuario.class))
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Usuario> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Usuario>()
                .dataSource(dataSource)
                .sql("INSERT INTO usuario (nombre, email, edad) VALUES (:nombre, :email, :edad)")
                .beanMapped()
                .build();
    }

    @Bean
    public Step pasoImportar(JobRepository jobRepository,
                             PlatformTransactionManager tx,
                             FlatFileItemReader<Usuario> reader,
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
