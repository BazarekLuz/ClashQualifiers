package dev.bazarski.clashqualifiers.records;

import java.util.List;

public record GameDetails(String matchId,
                          List<Participant> participants
) {}
