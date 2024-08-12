package dev.bazarski.clashqualifiers.services;

import dev.bazarski.clashqualifiers.errors.exceptions.AccountNotFoundException;
import dev.bazarski.clashqualifiers.errors.exceptions.TooManyRequestsException;
import dev.bazarski.clashqualifiers.props.RiotApiProperties;
import dev.bazarski.clashqualifiers.props.SearchProps.SearchProperties;
import dev.bazarski.clashqualifiers.records.*;
import dev.bazarski.clashqualifiers.records.match.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.StringTemplate.STR;


@Service
public class ClashQualifiersService {

    private static final Logger log = LoggerFactory.getLogger(ClashQualifiersService.class);
    private final WebClient client;
    private final RiotApiProperties props;
    private final SearchProperties searchProps;
    private final List<Account> accounts;


    public ClashQualifiersService(WebClient client, RiotApiProperties props, SearchProperties searchProps) {
        this.client = client;
        this.props = props;
        this.searchProps = searchProps;
        this.accounts = searchProps.getAccounts();
    }

    Flux<String> getListOfMatchIdsByPuuid(String puuid, MultiValueMap<String, String> params) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(STR."/lol/match/v5/matches/by-puuid/\{puuid}/ids")
                        .queryParams(params)
                        .build()
                )
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    throw new TooManyRequestsException(response.statusCode());
                })
                .bodyToMono(String[].class)
                .flatMapMany(Flux::fromArray);
    }

    Mono<Match> getMatch(String matchId) {
        return client.get()
                .uri(STR."/lol/match/v5/matches/\{matchId}")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    throw new TooManyRequestsException(response.statusCode());
                })
                .bodyToMono(Match.class);
    }

    public Flux<Standing> getMatchesAndCountPoints() {
        Flux<Match> matchIds = getDistinctMatchIdsForAccountsFromProps()
                .index()
                .flatMapSequential(indexedMatchId -> getMatch(indexedMatchId.getT2())
                        .delaySubscription(Duration.ofMillis(indexedMatchId.getT1() * 50))
                );

        return matchIds
                .flatMapSequential(this::filterOccurrences)
                .flatMapSequential(this::mapPoints)
                .groupBy(Standing::puuid)
                .flatMap(this::sumPoints)
                .flatMap(this::applyInitialPoints)
                .sort((standing1, standing2) -> Double.compare(standing2.points(), standing1.points()));
    }

    public Flux<String> getDistinctMatchIdsForAccountsFromProps() {
        Flux<Account> accs = Flux.fromIterable(accounts);
        Flux<String> matchIds = accs.flatMapSequential(account -> getListOfMatchIdsByPuuid(
                account.puuid(),
                searchProps.getParams())
        );

        return matchIds
                .distinct()
                .index()
                .flatMap(indexedMatchId -> Mono.just(indexedMatchId.getT2())
                        .delaySubscription(Duration.ofMillis(indexedMatchId.getT1() * 50))
                )
                .doOnNext(log::info);
    }

    Mono<GameDetails> filterOccurrences(Match match) {
        Set<String> accountPuuids = accounts.stream()
                .map(Account::puuid)
                .collect(Collectors.toSet());

        return Mono.just(new GameDetails(
                match.metadata().matchId(),
                match.info().participants().stream().filter(participant -> accountPuuids.contains(participant.puuid())).toList()
        ));
    }

    Flux<Standing> mapPoints(GameDetails gameDetails) {
        List<Participant> participants = gameDetails.participants();
        Stream<Standing> standings = participants.stream().map(participant -> {
            double points = switch (participants.size()) {
                default -> 0.0;
                case 2 -> 0.5;
                case 3, 6, 8, 10 -> 1.0;
                case 4 -> 2.0;
                case 5 -> 3.0;
            };
            return new Standing(participant.puuid(), participant.gameName(), points);
        });

        return Flux.fromStream(standings);
    }

    Mono<Standing> sumPoints(Flux<Standing> standings) {
        return standings.reduce((standing1, standing2) -> new Standing(
                standing1.puuid(),
                standing1.gameName(),
                standing2.points() + standing1.points())
        );
    }

    private Account findAccountByPuuid(String puuid) {
        return accounts.stream()
                .filter(account -> account.puuid().equals(puuid))
                .findFirst()
                .orElseThrow(AccountNotFoundException::new);
    }

    Mono<Standing> applyInitialPoints(Standing standing) {
        Account account = findAccountByPuuid(standing.puuid());
        return Mono.just(new Standing(standing.puuid(), standing.gameName(), account.initialPoints() + standing.points()));
    }
}
