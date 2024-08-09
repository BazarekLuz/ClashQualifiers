package dev.bazarski.clashqualifiers.props;

import dev.bazarski.clashqualifiers.common.JsonHelper;
import dev.bazarski.clashqualifiers.records.Account;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class SearchProperties {

    private final List<Account> accounts;
    private final Long startTimeEpoch;
    private final Long endTimeEpoch;
    private final Integer queueType;
    private final String type;
    private final Integer startIndex;
    private final Integer count;

    private final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();


    public SearchProperties() {
        this.accounts = JsonHelper.readAccounts();
        this.startTimeEpoch = LocalDateTime
                .of(2024, 7, 22, 19, 0)
                .atZone(ZoneId.of("Europe/Warsaw"))
                .toEpochSecond();
        this.endTimeEpoch = LocalDateTime.now()
                .atZone(ZoneId.of("Europe/Warsaw"))
                .toEpochSecond();
        this.queueType = 440;
        this.type = "ranked";
        this.startIndex = 0;
        this.count = 100;
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

    public Long getStartTimeEpoch() {
        return startTimeEpoch;
    }

    public Long getEndTimeEpoch() {
        return endTimeEpoch;
    }

    public Integer getQueueType() {
        return queueType;
    }

    public String getType() {
        return type;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public Integer getCount() {
        return count;
    }

    public MultiValueMap<String, String> getParams() {
        return params;
    }
}
