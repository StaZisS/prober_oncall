package org.example.prober_oncall;

import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class OnCallService {
    private static final Logger LOG = LoggerFactory.getLogger(OnCallService.class);
    private final OnCallWebClient onCallWebClient;
    private final OnCallMetricsService onCallMetricsService;
    private final OncallRepository oncallRepository;

    public OnCallService(
            OnCallWebClient onCallWebClient,
            OnCallMetricsService onCallMetricsService,
            OncallRepository oncallRepository
    ) {
        this.onCallWebClient = onCallWebClient;
        this.onCallMetricsService = onCallMetricsService;
        this.oncallRepository = oncallRepository;
    }

    @Scheduled(fixedRate = 2000)
    @Timed(value = "on_call_spent_time", percentiles = {0.5, 0.95, 0.99})
    public void schedule() {
        try {
            var response = onCallWebClient.login();
            onCallMetricsService.incrementSuccessCount();
            oncallRepository.isAppAlive();
            LOG.info("nice: {}", response);
        } catch (Exception e) {
            onCallMetricsService.incrementFailCount();
            LOG.info("ne nice: {}", e, e);
        }
    }
}
