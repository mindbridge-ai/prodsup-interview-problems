package ai.mindbridge.interview.prodsup.simulator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SimulatorThreadPoolConfig {
    // Background task scheduler for fake activity
    @Bean
    public ThreadPoolTaskScheduler getScheduler(){
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(3);
        scheduler.setThreadNamePrefix("http");
        return scheduler;
    }
}
