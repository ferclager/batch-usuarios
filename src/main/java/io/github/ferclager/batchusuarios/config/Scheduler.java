package io.github.ferclager.batchusuarios.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@EnableScheduling
@Component
public class Scheduler {
    private final JobLauncher jobLauncher;
    private final Job importarUsuarios;

    public Scheduler(JobLauncher jobLauncher, Job importarUsuarios) {
        this.jobLauncher = jobLauncher;
        this.importarUsuarios = importarUsuarios;
    }

    @Scheduled(cron = "0 53 12 * * *", zone = "Europe/Madrid")
    public void lanzar() throws Exception{
        JobParameters parameters = new JobParametersBuilder()
                .addString("rutaFichero", "./b-usuarios.csv")
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
        log.info("Ejecutando tarea programada importarUsuarios");
        jobLauncher.run(importarUsuarios, parameters);

    }
}
