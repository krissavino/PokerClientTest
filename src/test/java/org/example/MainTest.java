package org.example;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MainTest {

    @org.junit.Test
    public void main() {
        Main main = new Main();
        main.cardsOnTable = new ArrayList<>();
        Player player1 = new Player(0);
        player1.nickName = "Player1";
        player1.AddCard(new Card(CardColor.Spades, CardName.Card_3));
        player1.AddCard(new Card(CardColor.Diamonds, CardName.Card_9));
        Player player2 = new Player(1);
        player2.nickName = "Player2";
        player2.AddCard(new Card(CardColor.Clubs, CardName.Card_A));
        player2.AddCard(new Card(CardColor.Spades, CardName.Card_K));
        Player player3 = new Player(2);
        player3.nickName = "Player3";
        player3.AddCard(new Card(CardColor.Spades, CardName.Card_4));
        player3.AddCard(new Card(CardColor.Hearts, CardName.Card_6));
        main.cardsOnTable.add(new Card(CardColor.Spades, CardName.Card_6));
        main.cardsOnTable.add(new Card(CardColor.Clubs, CardName.Card_6));
        main.cardsOnTable.add(new Card(CardColor.Diamonds, CardName.Card_3));
        main.cardsOnTable.add(new Card(CardColor.Diamonds, CardName.Card_5));
        main.cardsOnTable.add(new Card(CardColor.Hearts, CardName.Card_10));

        main.setPlayerScore(player1);
        main.setPlayerScore(player2);
        main.setPlayerScore(player3);
        System.out.println("Player1: " + player1.score);
        System.out.println("Player2: " + player2.score);
        System.out.println("Player3: " + player3.score);
    }
}