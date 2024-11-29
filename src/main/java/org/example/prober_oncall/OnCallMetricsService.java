package org.example.prober_oncall;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class OnCallMetricsService {
    private final Counter successCount;
    private final Counter failCount;

    public OnCallMetricsService(MeterRegistry meterRegistry, OncallRepository oncallRepository) {
        successCount = Counter.builder("success_on_call_counter")
                .register(meterRegistry);
        failCount = Counter.builder("fail_on_call_counter")
                .register(meterRegistry);
        Gauge.builder("year_sla_percentage", () -> oncallRepository.getSlaPercentage(31_536_000))
                //зависит от того какой год
                .register(meterRegistry);
        Gauge.builder("mounth_sla_percentage", () -> oncallRepository.getSlaPercentage(2_678_400))
                //зависит от того какой месяц
                .register(meterRegistry);
        Gauge.builder("day_sla_percentage", () -> oncallRepository.getSlaPercentage(86_400))
                .register(meterRegistry);
    }

    public void incrementSuccessCount() {
        successCount.increment();
    }

    public void incrementFailCount() {
        failCount.increment();
    }
}
