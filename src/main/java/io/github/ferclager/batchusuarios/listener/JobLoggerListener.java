package io.github.ferclager.batchusuarios.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;

@Component
@Slf4j
public class JobLoggerListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("... INICIO job {} con parametros {}",
                jobExecution.getJobInstance().getJobName(),
                jobExecution.getJobParameters());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Duration duration = Duration.between(Objects.requireNonNull(jobExecution.getStartTime()),
                jobExecution.getEndTime());
        log.info("... FIN job {} estado {} en {} ms",
                jobExecution.getJobInstance().getJobName(),
                jobExecution.getStatus(),
                duration.toMillis());

        for (StepExecution step : jobExecution.getStepExecutions()) {
            log.info("... Step {}: leidos={}, escritos={}, filtrados={} saltados={}",
                    step.getStepName(),
                    step.getReadCount(),
                    step.getWriteCount(),
                    step.getFilterCount(),
                    step.getSkipCount());
        }
    }
}
