package org.example.application.game.respository;

import com.google.gson.Gson;
import org.example.application.game.Database.Database;
import org.example.application.game.model.battle.Battle;
import org.example.application.game.model.card.Card;
import org.example.application.game.model.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleMemoryRepository {
    private static BattleMemoryRepository instance;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final DeckMemoryRepository deckMemoryRepository;
    private final StatsMemoryRepository statsMemoryRepository;
    private Gson g;

    public BattleMemoryRepository() {
        g = new Gson();
        userRepository = UserMemoryRepository.getInstance();
        cardRepository = CardMemoryRepository.getInstance();
        statsMemoryRepository = StatsMemoryRepository.getInstance();
        deckMemoryRepository = DeckMemoryRepository.getInstance();
    }


    public static BattleMemoryRepository getInstance() {
        if (BattleMemoryRepository.instance == null) {
            BattleMemoryRepository.instance = new BattleMemoryRepository();
        }
        return BattleMemoryRepository.instance;
    }

    public Battle getBattle(int id) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id, player_a, player_b, winner FROM battles WHERE id=?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            List<Battle> battleRounds = new ArrayList<>();

            if (rs.next()) {

                int battleId = rs.getInt(1);

                User playerA = userRepository.getUserById(rs.getInt(2));
                User playerB = userRepository.getUserById(rs.getInt(3));
                User winner = userRepository.getUserById(rs.getInt(4));

                PreparedStatement ps2 = conn.prepareStatement("SELECT id, card_1, card_2, winner_card FROM battles WHERE id=?;");
                ps2.setInt(1, battleId);
                ResultSet rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    Card cardA = cardRepository.findByCardId(rs2.getString(2));
                    Card cardB = cardRepository.findByCardId(rs2.getString(3));
                    Card winnerCard = cardRepository.findByCardId(rs2.getString(4));
                    battleRounds.add(Battle.builder()
                            .id(rs2.getInt(1))
                            .card_a(cardA)
                            .card_b(cardB)
                            .winnerCard(winnerCard)
                            .build());
                }

                Battle battle = Battle.builder()
                        .id(battleId)
                        .playerA(playerA)
                        .playerB(playerB)
                        .winner(winner)
                        .battleRound(battleRounds)
                        .build();

                rs2.close();
                ps2.close();
                rs.close();
                ps.close();
                conn.close();

                return battle;
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public Battle addBattle(Battle battle) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO battles VALUES(DEFAULT);", Statement.RETURN_GENERATED_KEYS);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return null;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return this.getBattle(generatedKeys.getInt(1));
                }
            }
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Battle addUserToBattle(User user, Battle battle_) {

        Battle battle = (Battle) this.getBattle(battle_.getId());

        try {
            Connection conn = Database.getInstance().getConnection();
            int affectedRows;

            if (battle.getPlayerA() == null) {
                // Set user as playerA
                PreparedStatement ps = conn.prepareStatement("UPDATE battles SET player_a = ? WHERE id = ?;");
                ps.setInt(1, user.getId());
                ps.setInt(2, battle.getId());
                affectedRows = ps.executeUpdate();
                ps.close();
            } else if (battle.getPlayerB() == null) {
                // Set user as playerB
                PreparedStatement ps = conn.prepareStatement("UPDATE battles SET player_b = ? WHERE id = ?;");
                ps.setInt(1, user.getId());
                ps.setInt(2, battle.getId());
                affectedRows = ps.executeUpdate();
                ps.close();
            } else {
                conn.close();
                return null;
            }

            conn.close();

            if (affectedRows > 0) {
                battle = (Battle) this.getBattle(battle_.getId());
                // Check if the battle is full now
                if (battle.getPlayerA() != null && battle.getPlayerB() != null) {
                    // Now start the battle
                    this.battle(battle);
                }
                return battle;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean battle(Battle battle) {
        User playerA = (User) battle.getPlayerA();
        User playerB = (User) battle.getPlayerB();
        User winner = null;

        ArrayList<Card> deckA = (ArrayList<Card>) deckMemoryRepository.getDeck(playerA);
        ArrayList<Card> deckB = (ArrayList<Card>) deckMemoryRepository.getDeck(playerB);

        // Check if decks are complete
        if (deckA.size() != 4 || deckB.size() != 4) {
            return false;
        }

        Card cardA;
        Card cardB;
        Card winnerCard;

        System.out.println(playerA.getUsername() + " vs. " + playerB.getUsername());

        for (int i = 0; i < 100; i++) {
            // Check for winners
            if (deckA.size() == 0) {
                // Deck A is empty, therefore player B won.
                winner = playerB;
                // Update stats
                statsMemoryRepository.addStatForUser(playerB, 1);
                statsMemoryRepository.addStatForUser(playerA, -1);
                // Update elo
                statsMemoryRepository.updateEloForPlayers(playerA, playerB, -5, 3);
                System.out.println("Player B won.");
                break;
            } else if (deckB.size() == 0) {
                // Deck B is empty, therefore player A won.
                winner = playerA;
                // Update stats
                statsMemoryRepository.addStatForUser(playerA, 1);
                statsMemoryRepository.addStatForUser(playerB, -1);
                // Update elo
                statsMemoryRepository.updateEloForPlayers(playerA, playerB, 3, -5);
                System.out.println("Player A won.");
                break;
            }

            cardA = deckA.get(new Random().nextInt(deckA.size()));
            cardB = deckB.get(new Random().nextInt(deckB.size()));
            winnerCard = null;

            if (cardA.winsAgainst(cardB) || cardA.calculateDamage(cardB) > cardB.calculateDamage(cardA)) {
                // Player A wins this round, and gets cardB
                winnerCard = cardA;
                deckB.remove(cardB);
                deckA.add(cardB);
            } else if (cardB.getDamage() > cardA.getDamage()) {
                // Player B wins this round, and gets cardA
                winnerCard = cardB;
                deckA.remove(cardA);
                deckB.add(cardA);
            }

            if (winnerCard != null) {
                System.out.println("Winner: " + winnerCard.getName() + "(" + winnerCard.getDamage() + ")");
                System.out.println("DeckA: "+ g.toJson(deckA));
                System.out.println("DeckB: "+ g.toJson(deckB));

            }

            this.addBattleRound(battle, cardA, cardB, winnerCard);
        }

        // Transfer cards from current decks to users
        for (Card card : deckA) {
            cardRepository.addCardToUser(card, playerA);
        }
        for (Card card : deckB) {
            cardRepository.addCardToUser(card, playerB);
        }

        // Clear deck
        deckMemoryRepository.clearDeck(playerA);
        deckMemoryRepository.clearDeck(playerB);

        // Update stats, tie
        if (winner == null) {
            statsMemoryRepository.addStatForUser(playerA, 0);
            statsMemoryRepository.addStatForUser(playerB, 0);
            statsMemoryRepository.updateEloForPlayers(playerA, playerB, 0, 0);
            System.out.println("No one won.");
        }

        return this.setWinnerForBattle(winner, battle);
    }

    public boolean addBattleRound(Battle battle, Card cardA, Card cardB, Card winnerCard) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO battles(player_a, player_b, card_1, card_2, winner_card) VALUES(?, ?, ?,?,?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, battle.getPlayerA().getUsername());
            ps.setString(2, battle.getPlayerB().getUsername());
            ps.setString(3, cardA.getId());
            ps.setString(4, cardB.getId());

            if (winnerCard != null) {
                ps.setString(5, winnerCard.getId());
            } else {
                ps.setNull(5, java.sql.Types.NULL);
            }

            int affectedRows = ps.executeUpdate();

            ps.close();
            conn.close();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setWinnerForBattle(User winner, Battle battle) {
        try {
            Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE battles SET winner = ? WHERE id = ?;");
            ps.setInt(2, battle.getId());

            if (winner != null) {
                // Update winner
                ps.setInt(1, winner.getId());
            } else {
                // It's a draw.
                ps.setNull(1, java.sql.Types.NULL);
            }

            int affectedRows = ps.executeUpdate();

            ps.close();
            conn.close();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
