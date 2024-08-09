package dev.bazarski.clashqualifiers.records;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Participant(String puuid,
                          @JsonProperty("riotIdGameName") String gameName) {}
