package dev.bazarski.clashqualifiers.records;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record Standing(@JsonIgnore String puuid,
                       String gameName,
                       Double points
) {}
