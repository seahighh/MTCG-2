package org.example.application.game.model.stats;

import lombok.Builder;

@Builder
public class Stats {

    private int battles;
    private int numbersOfWin;
    private int numbersOfLost;
    private int elo;

    public int getBattles() {
        return battles;
    }

    public void setBattles(int battles) {
        this.battles = battles;
    }

    public int getNumbersOfWin() {
        return numbersOfWin;
    }

    public void setNumbersOfWin(int numbersOfWin) {
        this.numbersOfWin = numbersOfWin;
    }

    public int getNumbersOfLost() {
        return numbersOfLost;
    }

    public void setNumbersOfLost(int numbersOfLost) {
        this.numbersOfLost = numbersOfLost;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }
}
