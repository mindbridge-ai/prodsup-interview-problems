package ai.mindbridge.interview.prodsup.simulator;

import ai.mindbridge.interview.prodsup.exceptions.CsvProcessingException;
import ai.mindbridge.interview.prodsup.steps.cleaning.CleaningService;
import ai.mindbridge.interview.prodsup.steps.processing.ProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.TaskScheduler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/** Simulated user session */
public class UserSession {
    private final TaskScheduler scheduler;

    private final String userId;
    private final String tenantId;
    private final String baseFilename;

    private final Random random = new Random();

    private final Logger loginLogger = LoggerFactory.getLogger("ai.mindbridge.interview.prodsup.login.LoginController");
    private final Logger uploadLogger = LoggerFactory.getLogger("ai.mindbridge.interview.prodsup.steps.upload.UploadService");
    private final Logger cleaningLogger = LoggerFactory.getLogger(CleaningService.class);
    private final Logger processingLogger = LoggerFactory.getLogger(ProcessingService.class);

    public UserSession(TaskScheduler scheduler) {
        this.scheduler = scheduler;

        var tenantList = new ArrayList<>(SampleData.TENANT_USERS.keySet());
        this.tenantId = tenantList.get(random.nextInt(tenantList.size()));

        var userList = SampleData.TENANT_USERS.get(tenantId);
        this.userId = userList.get(random.nextInt(userList.size()));

        this.baseFilename = SampleData.FILENAMES.get(random.nextInt(SampleData.FILENAMES.size()));
    }

    public void session() {
        setUpLoggingContext();
        loginLogger.info("User logged in");
        scheduler.schedule(this::upload, delayUpTo(5000));
    }

    private void upload() {
        setUpLoggingContext();
        uploadLogger.info("Uploaded file {}.csv", baseFilename);
        if (random.nextInt(3) < 1) {
            scheduler.schedule(this::clean, delayUpTo(10000));
        }
        else {
            scheduler.schedule(() -> process(baseFilename), delayUpTo(10000));
        }
    }

    private void clean() {
        setUpLoggingContext();
        cleaningLogger.info("Processing file {}.csv", baseFilename);

        // 1/5 chance of failure
        if (random.nextInt(5) < 1) {
            throw new CsvProcessingException(random.nextInt(100), "Failed to process file", new NullPointerException("bug"));
        }
        var cleanedFilename = baseFilename + "-cleaned";
        cleaningLogger.info("Processed file {}.csv: {} rows, and created output file {}.csv", baseFilename, 103, cleanedFilename);
        scheduler.schedule(() -> process(cleanedFilename), delayUpTo(10000));
    }

    private void process(String filename) {
        setUpLoggingContext();
        processingLogger.info("Processing file {}.csv", filename);

        // 1/5 chance of failure
        if (random.nextInt(5) < 1) {
            throw new CsvProcessingException(random.nextInt(100), "Failed to process file", new NullPointerException("bug2"));
        }
        processingLogger.info("Processed file {}.csv: {} rows", filename, 103);
    }

    private Instant delayUpTo(long millis) {
        return Instant.now().plusMillis(random.nextLong(millis));
    }

    private void setUpLoggingContext() {
        MDC.put("user_id", userId);
        MDC.put("tenant_id", tenantId);
        MDC.put("request_id", UUID.randomUUID().toString());
    }
}
