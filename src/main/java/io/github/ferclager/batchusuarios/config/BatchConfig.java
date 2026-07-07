package io.github.ferclager.batchusuarios.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    @Bean
    public Step pasoSaludo(JobRepository jobRepository, PlatformTransactionManager ptx) {
        Tasklet tasklet = (contribution, chunkContext) -> {
            System.out.println(">>>Hola, Spring Batch!");
            return RepeatStatus.FINISHED;
        };
        return new StepBuilder("pasoSaludo", jobRepository)
                .tasklet(tasklet, ptx)
                .build();
    }

    @Bean
    public Job importarUsuariosJob(JobRepository jobRepository, Step pasoSaludo) {
        return new JobBuilder("importarUsuariosJob", jobRepository)
                .start(pasoSaludo)
                .build();
    }
}
