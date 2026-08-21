package io.github.ferclager.batchusuarios;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SpringBatchTest
class BatchUsuariosApplicationTests {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private JobRepositoryTestUtils jobRepositoryTestUtils;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@AfterEach
	void cleanUp() {
		jobRepositoryTestUtils.removeJobExecutions();
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "usuario");
	}

	@Test
	void shouldImportUsersWhenValidFile() throws Exception {
		JobParameters parameters = new JobParametersBuilder()
				.addString("rutaFichero", "src/test/resources/data/usuarios-test.csv")
				.addLong("timestamp", System.currentTimeMillis()).toJobParameters();

		JobExecution execution = jobLauncherTestUtils.launchJob(parameters);

		assertThat(execution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

		int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "usuario");
		assertThat(rows).isEqualTo(5);
	}

	@Test
	void shouldFailWhenNotExistingFile() throws Exception {
		JobParameters parameters = new JobParametersBuilder()
				.addString("rutaFichero", "src/test/resources/data/non-existing-file-test.csv")
				.addLong("timestamp", System.currentTimeMillis()).toJobParameters();

		JobExecution execution = jobLauncherTestUtils.launchJob(parameters);

		assertThat(execution.getStatus()).isEqualTo(BatchStatus.FAILED);

		int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "usuario");
		assertThat(rows).isEqualTo(0);
	}

	@Test
	void contextLoads() {
	}

}
