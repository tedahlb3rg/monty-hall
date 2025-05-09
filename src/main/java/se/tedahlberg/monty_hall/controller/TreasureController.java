package se.tedahlberg.monty_hall.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.tedahlberg.monty_hall.service.TreasureService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/treasure")
public class TreasureController {
  private final TreasureService treasureService = new TreasureService();

  @GetMapping("/find")
  public ResponseEntity<Boolean> findTreasure(
      @RequestParam(required = false, defaultValue = "true") Boolean trustJack) {
    Boolean foundTreasure = treasureService.tryFindingTreasure(trustJack);

    return ResponseEntity.ok(foundTreasure);
  }
}
