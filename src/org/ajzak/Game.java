package org.ajzak;

import org.ajzak.games.Human;
import org.ajzak.games.Komputer;
import org.ajzak.games.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game {

    private final Map<String, String> WINNING_HANDS= new HashMap<String, String>() {{
        put("kamien", "nozyczki");
        put("nozyczki", "papier");
        put("papier", "kamien");
    }};
    private ArrayList<String> history = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private boolean isPlaying = true;

    private Player player1;
    private Player player2;

    void start() {
        String lowercaseInput;

        System.out.println("Witaj w ajzakRPS!\n");

        while (isPlaying) {
            displayMenu();

            try {
                lowercaseInput = scanner.nextLine().toLowerCase();

                switch(lowercaseInput) {
                    case "graj":
                        play();
                        break;
                    case "historia":
                        showHistory();
                        break;
                    case "quit":
                        quit();
                        break;
                    default:
                        System.out.println("Nie ma takiej opcji!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        System.out.println("Dziekuję za granie ~ajzak");
    }

    private void displayMenu() {
        System.out.println(
                        "====[MENU]====\n" +
                        "1. Wpisz 'graj' zeby grac\n" +
                        "2. Wpisz 'historia' zeby zobaczyc historie\n" +
                        "3. Wpisz 'quit' zeby wyjsc"
        );
    }

    private void play() {
        setupPlayers();
        playRound();
        askToPlayAgain();
    }

    private void setupPlayers() {
        player1 = requestPlayer(1);
        player2 = requestPlayer(2);
    }

    private void playRound() {
        while(true) {
            String firstPlayerInput = player1.requestPlayerInput();
            String secondPlayerInput = player2.requestPlayerInput();
            int compareInput = determineWinningPlayer(firstPlayerInput, secondPlayerInput);

            switch (compareInput) {
                case 1:
                    addToHistory(player1.getName(), firstPlayerInput, player2.getName(), secondPlayerInput);
                    return;
                case 2:
                    addToHistory(player2.getName(), secondPlayerInput, player1.getName(), firstPlayerInput);
                    return;
                default:
                    System.out.println("Remis! Zagraj ponownie!");
            }
        }
    }

    private int determineWinningPlayer(String input1, String input2) {
        if (input1.equals(input2)) {
            return 3;
        }

        String losingHand = WINNING_HANDS.get(input1);

        return input2.equals(losingHand) ? 1 : 2;
    }

    private void quit() {
        isPlaying = false;
    }

    private void showHistory() {
        history.forEach(System.out::println);
        System.out.println("Nascisnij Enter/Return zeby kontynuowac.");
        try {
            scanner.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addToHistory(String winner, String winningMove, String loser, String losingMove) {
        String dateNow = createDateNow();
        String roundHistory = String.format("ZWYCIESCA: %s (%s)  |  PRZEGRANY: %s (%s)", winner, winningMove, loser, losingMove);
        String log = String.format("[%s] %s", dateNow, roundHistory);
        System.out.println(roundHistory);
        history.add(log);
    }

    private Player requestPlayer(int playerNum) {
        String playerInput;

        while (true) {
            try {
                System.out.printf("Wybierz 'czlowiek' lub 'komputer' dla Gracza %s: ", playerNum);
                playerInput = scanner.nextLine().toLowerCase();

                switch(playerInput) {
                    case "czlowiek":
                        System.out.println("Jakie jest twoje imie?");
                        String playerName;
                        while (true) {
                            playerName = scanner.nextLine().trim();

                            try {
                                validateUserName(playerName);
                                break;
                            } catch(NullPointerException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        return new Human(playerName);
                    case "komputer":
                        return new Komputer(String.format("Player %s (KOMPUTER)", playerNum));
                    default:
                        System.out.println("Wybierz typ uzytkownika 'czlowiek' or 'komputer'");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void validateUserName(String name) throws NullPointerException {
        if (name.isEmpty()) {
            throw new NullPointerException("Nazwa nie moze byc pusta!");
        }
    }

    private void askToPlayAgain() {
        String userInput;
        System.out.println("Chcesz grac jeszcze raz? (y/n)");

        userInput = scanner.nextLine().toLowerCase();

        if (!userInput.equals("y")) {
            isPlaying = false;
        }
    }

    static String createDateNow() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
