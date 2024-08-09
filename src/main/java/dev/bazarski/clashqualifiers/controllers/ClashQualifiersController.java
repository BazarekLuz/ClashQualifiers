package dev.bazarski.clashqualifiers.controllers;

import dev.bazarski.clashqualifiers.records.Standing;
import dev.bazarski.clashqualifiers.services.ClashQualifiersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("api/qualifiers")
public class ClashQualifiersController {
    private final ClashQualifiersService service;

    public ClashQualifiersController(ClashQualifiersService service) {
        this.service = service;
    }

    @GetMapping("matchIds")
    ResponseEntity<Flux<Standing>> fetchMatchIds() {
        return ResponseEntity.ok(service.getMatchesAndCountPoints());
    }
}
