package ai.mindbridge.interview.prodsup.simulator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Random log generator
 */
@SpringBootApplication
public class LogSimulator implements CommandLineRunner {
    private final UserSimulator simulator;

    public static void main(String[] args) {
        SpringApplication.run(LogSimulator.class, args);
    }

    public LogSimulator(UserSimulator simulator) {
        this.simulator = simulator;
    }

    @Override
    public void run(String... args) {
        simulator.simulateActivity();
    }
}
