package org.example;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

    }

    ArrayList<Card> cardsOnTable = null;

    public ArrayList<Card> getSetCards(ArrayList<Card> cards, int count) {
        var cardCounts = new ArrayList<Integer>();
        for(int i = 0; i < 13; i++) {
            cardCounts.add(0);
        }
        for(Card card : cards) {
            int ordinal = card.GetName().ordinal();
            if(cardCounts.get(ordinal) == null)
                cardCounts.set(ordinal, 0);
            cardCounts.set(ordinal, cardCounts.get(ordinal)+1);
        }
        int maxPairOrdinal = -1;
        for(int i = 0; i < 13; i++) {
            if (cardCounts.get(i) == count)
                maxPairOrdinal = i;
        }
        var res = new ArrayList<Card>();
        if(maxPairOrdinal > -1) {
            for(var card : cards) {
                if(maxPairOrdinal == card.GetName().ordinal()) {
                    res.add(card);
                }
            }
        }
        return res;
    }
    public ArrayList<Card> getTwoPairsCards(ArrayList<Card> cards) {
        ArrayList<Card> cards2 = (ArrayList<Card>)cards.clone();
        var res = getSetCards(cards2, 2);
        if(res.size() > 0) {
            for(int i = 0; i < cards2.size(); i++) {
                if(cards2.get(i).equals(res.get(0))) {
                    cards2.remove(cards2.get(i));
                }
                if(cards2.get(i).equals(res.get(1))) {
                    cards2.remove(cards2.get(i));
                }
            }
        }
        var res2 = getSetCards(cards2, 2);
        if(res2.size() > 0) {
            for(var card : res) {
                res2.add(card);
            }
            return res2;
        }
        return null;
    }
    public ArrayList<Card> getFullHouse(ArrayList<Card> cards) {
        var pair = getSetCards(cards, 2);
        var set = getSetCards(cards, 3);
        if(pair.size() > 0 && set.size() > 0) {
            var res = new ArrayList<Card>();
            for(var card : pair)
                res.add(card);
            for(var card : set)
                res.add(card);
            return res;
        }
        return null;
    }

    public ArrayList<Card> getStreet(ArrayList<Card> cards) {
        var res = new ArrayList<Card>();
        var cardCounts = new ArrayList<Integer>();
        for(int i = 0; i < 13; i++) {
            cardCounts.add(0);
        }
        for(Card card : cards) {
            int ordinal = card.GetName().ordinal();
            if(cardCounts.get(ordinal) == null)
                cardCounts.set(ordinal, 0);
            cardCounts.set(ordinal, cardCounts.get(ordinal)+1);
        }
        int count = 0;
        int maxCount = 0;
        int firstOrdinal = -1;
        for(int i = 12; i >= 0; i--) {
            if(cardCounts.get(i) > 0) {
                count++;
                if(count == 5) {
                    firstOrdinal = i;
                    break;
                }
                if(count > maxCount) {
                    maxCount = count;
                }
            } else {
                count = 0;
            }
        }
        if(count == 4 && cardCounts.get(12) > 0) {
            firstOrdinal = 12;
        }
        if(firstOrdinal >= 0) {
            for(int i = 0; i < 5; i++) {
                for (var card : cards) {
                    if (card.GetName().ordinal() == ((firstOrdinal + i)%13))
                        res.add(card);
                }
            }
        }
        return res;
    }
    public ArrayList<Card> getSortedCards(ArrayList<Card> cards) {
        var res = new ArrayList<Card>();
        for(int i = 0; i < 13; i++)
            for(var card : cards) {
                if(card.GetName().ordinal() == i) {
                    res.add(card);
                }
            }
        return res;
    }
    public ArrayList<Card> getFlush(ArrayList<Card> cards) {
        var sortedCards = getSortedCards(cards);
        var colorCounts = new ArrayList<Integer>();
        for(int i = 0; i < 4; i++) {
            colorCounts.add(0);
        }
        for(Card card : sortedCards) {
            int ordinal = card.GetColor().ordinal();
            colorCounts.set(ordinal, colorCounts.get(ordinal)+1);
        }
        var res = new ArrayList<Card>();
        for(int i = 0; i < 4; i++) {
            if(colorCounts.get(i) >= 5) {
                for(var card : sortedCards) {
                    if(card.GetColor().ordinal() == i) {
                        res.add(card);
                    }
                }
                return res;
            }
        }
        return null;
    }
    public ArrayList<Card> getFlushStreet(ArrayList<Card> cards) {
        return getFlush(getStreet(cards));
    }

    public void setPlayerScore(Player player) {
        var playerCards = player.hand;
        var allCards = new ArrayList<Card>();
        for(Card c : playerCards) {
            allCards.add(c);
        }
        for(Card c : cardsOnTable) {
            allCards.add(c);
        }
        var combinations = new ArrayList<ArrayList<Card>>();
        combinations.add(getSetCards(allCards, 2));
        combinations.add(getTwoPairsCards(allCards));
        combinations.add(getSetCards(allCards, 3));
        combinations.add(getStreet(allCards));
        combinations.add(getFlush(allCards));
        combinations.add(getFullHouse(allCards));
        combinations.add(getSetCards(allCards, 4));
        combinations.add(getFlushStreet(allCards));
        for(int i = combinations.size()-1; i >= 0; i--) {
            if(combinations.get(i) != null)
                if(combinations.get(i).size() > 0) {
                    System.out.println(player.nickName);
                    for(var card : combinations.get(i)) {
                        System.out.println("    " + card.GetName() + " " + card.GetColor());
                    }
                    break;
                }
        }
        for(int i = 0; i < combinations.size(); i++) {
            var cards = combinations.get(i);
            if(cards == null)
                continue;
            if(cards.size() == 0)
                continue;

            var kicker = player.hand.get(0);
            for(var card : cards) {
                if(card.equals(kicker))
                    kicker = player.hand.get(1);
            }
            player.score = i*1000 + cards.get(cards.size()-1).GetName().ordinal()*10 + kicker.GetName().ordinal();
        }
    }
}