package dev.bazarski.clashqualifiers.props.SearchProps;

import dev.bazarski.clashqualifiers.common.JsonHelper;
import dev.bazarski.clashqualifiers.records.Account;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/*
Pierwsza iteracja clasha timestamp:
this.startTimeEpoch = LocalDateTime
                .of(2024, 7, 22, 19, 0)
                .atZone(ZoneId.of("Europe/Warsaw"))
                .toEpochSecond();
 */

@Component
@Profile("default")
public class DefaultSearchProperties extends SearchProperties {

    private final List<Account> accounts;
    private final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    public DefaultSearchProperties() {
        this.accounts = JsonHelper.readAccounts();
        Long startTimeEpoch = LocalDateTime
                .of(2024, 8, 11, 20, 0)
                .atZone(ZoneId.of("Europe/Warsaw"))
                .toEpochSecond();
        Long endTimeEpoch = LocalDateTime.now()
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

    public List<Account> getAccounts() {
        return accounts;
    }

    public MultiValueMap<String, String> getParams() {
        return params;
    }
}
