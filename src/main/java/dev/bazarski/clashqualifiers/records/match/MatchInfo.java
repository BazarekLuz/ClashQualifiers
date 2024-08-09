package dev.bazarski.clashqualifiers.records.match;

import dev.bazarski.clashqualifiers.records.Participant;

import java.util.List;

public record MatchInfo(String endOfGameResult,
                        List<Participant> participants
) {}
