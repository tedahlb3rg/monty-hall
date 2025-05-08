package se.tedahlberg.monty_hall.service;

public class TreasureService {
  private static final int NUMBER_OF_ISLANDS = 3;

  private int getRandomIsland() {
    return (int) Math.floor(Math.random() * NUMBER_OF_ISLANDS);
  }

  private Boolean[] removeIsland(Boolean[] islands, int index) {
    Boolean[] newIslands = new Boolean[islands.length - 1];

    for (int i = 0, j = 0; i < islands.length; i++) {
      if (i != index) {
        newIslands[j++] = islands[i];
      }
    }

    return newIslands;
  }

  public Boolean tryFindingTreasure(Boolean trustJack) {
    Boolean foundTreasure = false;
    Boolean[] islands = new Boolean[NUMBER_OF_ISLANDS];
    int treasureIsland = getRandomIsland();
    int currentIsland = getRandomIsland();

    for (int i = 0; i < NUMBER_OF_ISLANDS; i++) {
      if (i == treasureIsland) {
        islands[i] = true;
      } else {
        islands[i] = false;
      }
    }

    if (trustJack) {
      for (int i = 0; i < NUMBER_OF_ISLANDS; i++) {
        if (i != treasureIsland && i != currentIsland) {
          islands = removeIsland(islands, i);
          break;
        }
      }

      for (int i = 0; i < islands.length; i++) {
        if (i != currentIsland) {
          currentIsland = i;
          break;
        }
      }
    }

    foundTreasure = islands[currentIsland];
    return foundTreasure;
  }
}
