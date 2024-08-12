package dev.bazarski.clashqualifiers.props.SearchProps;

import dev.bazarski.clashqualifiers.common.JsonHelper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@Profile("test")
public class TestSearchProperties extends SearchProperties {

    public TestSearchProperties() {
        this.accounts = JsonHelper.readAccounts();
        Long startTimeEpoch = LocalDateTime
                .of(2024, 8, 6, 16, 0)
                .atZone(ZoneId.of("Europe/Warsaw"))
                .toEpochSecond();
        Long endTimeEpoch = LocalDateTime
                .of(2024, 8, 6, 23, 59)
                .atZone(ZoneId.of("Europe/Warsaw"))
                .toEpochSecond();
        Integer queueType = 440;
        String type = "ranked";
        Integer startIndex = 0;
        Integer count = 100;
        params.add("startTime", startTimeEpoch.toString());
        params.add("endTime", endTimeEpoch.toString());
        params.add("queue", queueType.toString());
        params.add("type", type);
        params.add("start", startIndex.toString());
        params.add("count", count.toString());
    }
}
