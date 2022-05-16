package ai.mindbridge.interview.prodsup.simulator;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Random;

@Component
public class UserSimulator {
    private final TaskScheduler scheduler;
    private final Random random = new Random();

    public UserSimulator(TaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void simulateActivity() {
        new UserSession(scheduler).session();

        // A new user logging in every 10 seconds
        scheduler.schedule(this::simulateActivity, Instant.now().plusMillis(random.nextLong(10000)));
    }
}
