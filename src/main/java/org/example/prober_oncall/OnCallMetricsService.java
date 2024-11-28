package org.example.prober_oncall;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class OnCallMetricsService {
    private final Counter successCount;
    private final Counter failCount;

    public OnCallMetricsService(MeterRegistry meterRegistry) {
        successCount = Counter.builder("success_on_call_counter")
                .register(meterRegistry);
        failCount = Counter.builder("fail_on_call_counter")
                .register(meterRegistry);
    }

    public void incrementSuccessCount() {
        successCount.increment();
    }

    public void incrementFailCount() {
        failCount.increment();
    }
}
