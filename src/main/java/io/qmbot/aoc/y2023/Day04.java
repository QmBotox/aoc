package io.qmbot.aoc.y2023;

import io.qmbot.aoc.Puzzle;

import java.util.*;
import java.util.stream.Collectors;

public class Day04 implements Puzzle {
    @Override
    public Object part1(String input) {
        return parse(input).values().stream().mapToInt(card -> (int) Math.pow(2, card.points() - 1)).sum();
    }

    @Override
    public Object part2(String input) {
        Map<Integer, Card> cards = parse(input);
        int result = 0;
        for (int cardNumber : cards.keySet()) {
            Card card = cards.get(cardNumber);
            result += card.countOfCopies;
            for (int c = 0; c < card.countOfCopies; c++) {
                for (int i = cardNumber + 1; i < cardNumber + 1 + card.points();  i++) {
                    cards.get(i).countOfCopies++;
                }
            }
        }
        return result;
    }

    private Map<Integer, Card> parse(String input) {
        return Arrays.stream(input.split(REGEX_NEW_LINE)).map(string -> string.split(":"))
                .collect(Collectors.toMap(parse -> Integer.parseInt(parse[0].split("\\D+")[1]),
                        parse -> card(parse[1].split("\\|"))));
    }

    private Card card(String[] allNumbers) {
        return new Card(parseNumbers(allNumbers[0]), parseNumbers(allNumbers[1]));
    }

    private static List<Integer> parseNumbers(String input) {
        return Arrays.stream(input.split(" ")).filter(numberAsString -> !numberAsString.isEmpty())
                .map(Integer::parseInt).toList();
    }


    static class Card{
        List<Integer> have;
        List<Integer> winning;
        int countOfCopies = 1;

        public Card(List<Integer> have, List<Integer> winning) {
            this.have = have;
            this.winning = winning;
        }

        public int points() {
            return (int) winning.stream().filter(have::contains).count();
        }
    }
}
