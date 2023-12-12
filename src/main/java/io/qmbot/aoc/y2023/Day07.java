package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day07 implements Puzzle {
    @Override
    public Object part1(String input) {
        List<Player> players = players(input, 1);
        Collections.sort(players);
        return IntStream.range(0, players.size()).map(i -> players.get(i).bid * (i + 1)).sum();
    }

    @Override
    public Object part2(String input) {
        List<Player> players = players(input, 2);
        Hand hand = Hand.card('J');
        for (Player player : players) {
            if (player.hands.contains(hand)) {
                player.change();
                player.value = Player.value(player.type);
            }
        }
        Collections.sort(players);
        return IntStream.range(0, players.size()).map(i -> players.get(i).bid * (i + 1)).sum();
    }

    private List<Player> players(String input, int part) {
        return Arrays.stream(input.split(REGEX_NEW_LINE)).map(str -> Player.player(str, part)).collect(Collectors.toList());
    }

    static class Player implements Comparable {
        List<Hand> hands;
        int bid;
        String type;
        int value;


        public Player(List<Hand> hands, int bid, String type, int value) {
            this.hands = hands;
            this.bid = bid;
            this.type = type;
            this.value = value;
        }

        static public Player player(String string, int part) {
            String[] strings = string.split(" ");
            String handString = strings[0];
            List<Hand> hands = new ArrayList<>();
            for (int i = 0; i < handString.length(); i++) {
                if (part == 1) {
                    hands.add(Hand.card(handString.charAt(i)));
                } else hands.add(Hand.card2(handString.charAt(i)));
            }
            String type = type(hands);
            return new Player(hands, Integer.parseInt(strings[1]), type, value(type));
        }


        static int value(String string) {
            switch (string) {
                case "Five of a kind" -> {
                    return 6;
                }
                case "Four of a kind" -> {
                    return 5;
                }
                case "Full house" -> {
                    return 4;
                }
                case "Three of a kind" -> {
                    return 3;
                }
                case "Two pair" -> {
                    return 2;
                }
                case "One pair" -> {
                    return 1;
                }
                case "High card" -> {
                    return 0;
                }
                default -> throw new IllegalStateException("Unexpected value: " + string);
            }
        }

        static String type(List<Hand> hands) {
            Set<Hand> handSet = new HashSet<>(hands);
            Map<Hand, Long> countCharacters = countCharacters(hands);
            if (handSet.size() == 5) return "High card";
            if (handSet.size() == 4) return "One pair";
            if (handSet.size() == 3) {
                for (Hand hand : countCharacters.keySet()) {
                    if (countCharacters.get(hand) == 3) return "Three of a kind";
                }
                return "Two pair";
            }
            if (handSet.size() == 2) {
                for (Hand hand : countCharacters.keySet()) {
                    if (countCharacters.get(hand) == 4) return "Four of a kind";
                }
                return "Full house";
            }
            if (handSet.size() == 1) return "Five of a kind";
            throw new IllegalArgumentException();
        }

        void change() {
            switch (type) {
                case "Five of a kind" -> {
                }
                case "Four of a kind", "Full house" -> {
                    type = "Five of a kind";
                }
                case "Three of a kind" -> {
                    type = "Four of a kind";
                }
                case "Two pair" -> {
                    if (countCharacters(hands).get(Hand.card('J')) == 2) {
                        type = "Four of a kind";
                    } else {
                        type = "Full house";
                    }
                }
                case "One pair" -> {
                    type = "Three of a kind";
                }
                case "High card" -> {
                    type = "One pair";
                }
            }
        }

        private static Map<Hand, Long> countCharacters(List<Hand> hands) {
            return hands.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        }

        @Override
        public int compareTo(@NotNull Object o) {
            Player p = (Player) o;
            int result = Integer.compare(value, p.value);
            int i = 0;
            while (result == 0) {
                result = hands.get(i).compareTo(p.hands.get(i));
                i++;
            }
            return result;
        }
    }

    static class Hand implements Comparable {
        char card;
        int value;

        public Hand(char card, int value) {
            this.card = card;
            this.value = value;
        }

        static public Hand card(char card) {
            int value;
            switch (card) {
                case 'A' -> value = 14;
                case 'K' -> value = 13;
                case 'Q' -> value = 12;
                case 'J' -> value = 11;
                case 'T' -> value = 10;
                case '9' -> value = 9;
                case '8' -> value = 8;
                case '7' -> value = 7;
                case '6' -> value = 6;
                case '5' -> value = 5;
                case '4' -> value = 4;
                case '3' -> value = 3;
                case '2' -> value = 2;
                default -> throw new IllegalArgumentException();
            }
            return new Hand(card, value);
        }

        static public Hand card2(char card) {
            int value;
            switch (card) {
                case 'A' -> value = 14;
                case 'K' -> value = 13;
                case 'Q' -> value = 12;
                case 'T' -> value = 10;
                case '9' -> value = 9;
                case '8' -> value = 8;
                case '7' -> value = 7;
                case '6' -> value = 6;
                case '5' -> value = 5;
                case '4' -> value = 4;
                case '3' -> value = 3;
                case '2' -> value = 2;
                case 'J' -> value = 1;
                default -> throw new IllegalArgumentException();
            }
            return new Hand(card, value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Hand hand = (Hand) o;

            return card == hand.card;
        }

        @Override
        public int hashCode() {
            return card;
        }

        @Override
        public int compareTo(@NotNull Object o) {
            Hand h = (Hand) o;
            return Integer.compare(value, h.value);
        }
    }
}
