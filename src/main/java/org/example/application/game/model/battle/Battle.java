package org.example.application.game.model.battle;

import lombok.Builder;
import org.example.application.game.model.card.Card;
import org.example.application.game.model.user.User;

import java.util.List;

@Builder
public class Battle {
    private int id;
    private User playerA;
    private User playerB;
    private User winner;
    private List<Battle> battleRound;
    private Card card_a;
    private Card card_b;
    private Card winnerCard;

    public Battle() {
    }

    public Battle(int id, User playerA, User playerB, User winner, List<Battle> battleRound, Card card_a, Card card_b, Card winnerCard) {
        this.id = id;
        this.playerA = playerA;
        this.playerB = playerB;
        this.winner = winner;
        this.battleRound = battleRound;
        this.card_a = card_a;
        this.card_b = card_b;
        this.winnerCard = winnerCard;
    }

    public Card getCard_a() {
        return card_a;
    }

    public void setCard_a(Card card_a) {
        this.card_a = card_a;
    }

    public Card getCard_b() {
        return card_b;
    }

    public void setCard_b(Card card_b) {
        this.card_b = card_b;
    }

    public Card getWinnerCard() {
        return winnerCard;
    }

    public void setWinnerCard(Card winnerCard) {
        this.winnerCard = winnerCard;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getPlayerA() {
        return playerA;
    }

    public void setPlayerA(User playerA) {
        this.playerA = playerA;
    }

    public User getPlayerB() {
        return playerB;
    }

    public void setPlayerB(User playerB) {
        this.playerB = playerB;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public List<Battle> getBattleRound() {
        return battleRound;
    }

    public void setBattleRound(List<Battle> battleRound) {
        this.battleRound = battleRound;
    }
}
