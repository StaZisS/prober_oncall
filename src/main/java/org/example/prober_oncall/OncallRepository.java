package org.example.prober_oncall;

import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;

@Repository
public class OncallRepository {
    private final LocalDateTime startTime;
    private long uptimeInSeconds;

    public OncallRepository() {
        this.startTime = LocalDateTime.now();
        this.uptimeInSeconds = 0;
    }

    public double getSlaPercentage(long observationTime) {
        long downtime = calculateDowntime();

        long uptime = observationTime - downtime;
        return ((double) uptime / observationTime) * 100;
    }

    public void isAppAlive() {
        addUptime(1);
    }

    private void addUptime(long seconds) {
        if (seconds < 0) {
            throw new IllegalArgumentException("Время работы не может быть отрицательным.");
        }
        this.uptimeInSeconds += seconds;
    }

    private long calculateDowntime() {
        long totalRunningTimeInSeconds = Duration.between(startTime, LocalDateTime.now()).getSeconds();
        return Math.max(0, totalRunningTimeInSeconds - uptimeInSeconds);
    }
}
