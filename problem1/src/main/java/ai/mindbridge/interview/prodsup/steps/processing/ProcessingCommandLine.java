package ai.mindbridge.interview.prodsup.steps.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.support.TaskUtils;

import java.util.UUID;

/**
 * Command-line driver for running the step locally
 */
@SpringBootApplication
public class ProcessingCommandLine implements CommandLineRunner {
    private final ProcessingService processingService;
    private final Logger logger = LoggerFactory.getLogger(ProcessingCommandLine.class);

    public static void main(String[] args) {
        SpringApplication.run(ProcessingCommandLine.class, args);
    }

    @Autowired
    public ProcessingCommandLine(ProcessingService processingService) {
        this.processingService = processingService;
    }

    @Override
    public void run(String... args) {
        Logger loginLogger = LoggerFactory.getLogger("ai.mindbridge.interview.prodsup.login.LoginController");
        Logger uploadLogger = LoggerFactory.getLogger("ai.mindbridge.interview.prodsup.steps.upload.UploadService");

        MDC.put("user_id", "627eb586c5a8ea0020b96e56");
        MDC.put("tenant_id", "example1");
        MDC.put("request_id", UUID.randomUUID().toString());

        // Use a similar thread name to make it look like the simulated tasks
        Thread.currentThread().setName("http-1");

        String filename = args[0];
        loginLogger.info("User logged in");
        uploadLogger.info("Uploaded file {}", filename);

        try {
            processingService.processFile(args[0]);
        }
        catch (Exception e) {
            // Make it look the same as the simulated errors (same logger)
            LoggerFactory.getLogger(TaskUtils.LOG_AND_SUPPRESS_ERROR_HANDLER.getClass())
                    .error("Unexpected error occurred in scheduled task", e);
        }
    }


}
