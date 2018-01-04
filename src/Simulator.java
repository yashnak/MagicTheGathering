/**
 * Created by yashnak on 10/16/17.
 */

import java.util.List;
import java.util.ArrayList;

public class Simulator {

    public static  List<Double> probabilityTable = new ArrayList<Double>();

    public static void main(String[] args) {


        List<Card> deck1 = deckOneCreator();
        deck1 = shuffleDeck(deck1);
        System.out.println("7.5A");
        simulateA(deck1, 1);
        probabilityTable.clear();
        System.out.println();
        System.out.println("7.5B");
        simulateB(deck1,1);
        System.out.println();
        System.out.println("7.5C");
        System.out.println("X-Axis: Turns, Y-Axis: Spells");
        simulateC(deck1, 1);
        probabilityTable.clear();


        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        List<Card> deck2 = deckTwoCreator();
        deck2 = shuffleDeck(deck2);
        System.out.println("7.6A");
        simulateA(deck2, 2);
        probabilityTable.clear();
        System.out.println();
        System.out.println("7.6B");
        simulateB(deck2,2);
        System.out.println();
        System.out.println("7.6C");
        System.out.println("X-Axis: Turns, Y-Axis: Spells");
        simulateC(deck2, 2);



    }


    //first time you play spell is turn 4
    static void simulateA(List<Card> deck, int deckNum) {

        List<Integer> turn4Spells = new ArrayList<Integer>();

        //#experiments
        for (int i = 0; i < 10; i++) {

            //#simulations
            for (int j = 0; j < 1000; j++ ) {

                List<Card> hand = createHand(deck);
                int numSpellsSoFar = 0;
                List<Card> inPlayCards = new ArrayList<Card>();
                int countTurn = 1;

                while (countTurn < 5) {
                    boolean landCardPlayed = false;
                    hand.add(drawCard(deck, deck.size()));


                    List<Integer> landCardIndexes = getLandCardIndexes(hand);
                    if (landCardIndexes.size() > 0) {
                        inPlayCards.add(hand.get(landCardIndexes.get(0)));
                        hand.remove(hand.get(landCardIndexes.get(0)));
                        landCardPlayed = true;

                    }

                    if (landCardPlayed || inPlayCards.size() > 0) {
                        int maxCostIndex = getMaxSpellCostIndex(hand, inPlayCards);
                        if (maxCostIndex >= 0 && maxCostIndex <= inPlayCards.size()) {
                            hand.remove(maxCostIndex);
                            numSpellsSoFar++;

                            if (countTurn == 4 && numSpellsSoFar == 1) {
                                turn4Spells.add(1);
                            }
                        }


                    }
                    countTurn++;



                }
                if (deckNum == 1) deck = deckOneCreator();
                else deck = deckTwoCreator();



            }


            double turn4Prob = (turn4Spells.size())/1000.0000;
            probabilityTable.add(turn4Prob);




        }

        System.out.println("Probabilities: " +probabilityTable);
        double mean = 0;
        for (int k = 0; k < probabilityTable.size(); k++) {
            mean = mean + probabilityTable.get(k);

        }
        mean = (mean)/probabilityTable.size();
        System.out.println("Mean: " + mean);
        double variance = getVariance(mean, probabilityTable);
        double std = getStd(variance);
        System.out.println("Standard Deviation: " + std);



    }


    //prob that first spell is expense 4
    static void simulateB(List<Card> deck, int deckNum) {

        List<Integer> spellFours = new ArrayList<Integer>();

        //#experiments
        for (int i = 0; i < 10; i++) {

            //#simulations
            for (int j = 0; j < 1000; j++ ) {

                List<Card> hand = createHand(deck);
                int numSpellsSoFar = 0;
                List<Card> inPlayCards = new ArrayList<Card>();

                int countTurn = 1;
                boolean spell4 = false;

                while (spell4 == false) {
                    boolean landCardPlayed = false;
                    hand.add(drawCard(deck, deck.size()));


                    List<Integer> landCardIndexes = getLandCardIndexes(hand);
                    if (landCardIndexes.size() > 0) {
                        inPlayCards.add(hand.get(landCardIndexes.get(0)));
                        hand.remove(hand.get(landCardIndexes.get(0)));
                        landCardPlayed = true;


                    }

                    if (landCardPlayed || inPlayCards.size() > 0) {
                        int maxCostIndex = getMaxSpellCostIndex(hand, inPlayCards);
                        if (maxCostIndex >= 0 && maxCostIndex <= inPlayCards.size()) {
                            int spellNum = hand.get(maxCostIndex).cost;
                            hand.remove(maxCostIndex);
                            numSpellsSoFar++;

                            if (numSpellsSoFar == 1 && spellNum == 4) {
                                spellFours.add(1);
                                spell4 = true;
                            }else if (numSpellsSoFar ==1 && spellNum != 4){
                                break;
                            }
                        }

                    }
                    countTurn++;



                }

                if (deckNum == 1) deck = deckOneCreator();
                else deck = deckTwoCreator();



            }


            double turn4Prob = (spellFours.size())/1000.0000;
            probabilityTable.add(turn4Prob);

        }

        System.out.println("Probabilities: " +probabilityTable);
        double mean = 0;
        for (int k = 0; k < probabilityTable.size(); k++) {
            mean = mean + probabilityTable.get(k);

        }
        mean = (mean)/probabilityTable.size();
        System.out.println("Mean: " + mean);
        double variance = getVariance(mean, probabilityTable);
        double std = getStd(variance);
        System.out.println("Standard Deviation: " + std);



    }

    //table of turn x spell prob
    static void simulateC(List<Card> deck, int deckNum) {

        double [][] table = new double[6][6];
        //#experiments
        for (int i = 0; i < 1; i++) {

            //#simulations
            for (int j = 0; j < 10000; j++ ) {

                List<Card> hand = createHand(deck);
                List<Card> inPlayCards = new ArrayList<Card>();

                int countTurn = 1;

                while (countTurn < 7) {
                    boolean landCardPlayed = false;
                    hand.add(drawCard(deck, deck.size()));


                    List<Integer> landCardIndexes = getLandCardIndexes(hand);
                    if (landCardIndexes.size() > 0) {
                        inPlayCards.add(hand.get(landCardIndexes.get(0)));
                        hand.remove(hand.get(landCardIndexes.get(0)));
                        landCardPlayed = true;


                    }

                    if (landCardPlayed || inPlayCards.size() > 0) {
                        int maxCostIndex = getMaxSpellCostIndex(hand, inPlayCards);
                        if (maxCostIndex >= 0 && maxCostIndex <= inPlayCards.size()) {
                            int spellNum = hand.get(maxCostIndex).cost;
                            hand.remove(maxCostIndex);

                            if (countTurn == 1) {
                                table[countTurn-1][spellNum-1] +=1;
                            }
                            if (countTurn == 2) {
                                table[countTurn-1][spellNum-1] +=1;
                            }
                            if (countTurn == 3) {
                                table[countTurn-1][spellNum-1] +=1;
                            }
                            if (countTurn == 4) {
                                table[countTurn-1][spellNum-1] +=1;
                            }
                            if (countTurn == 5) {
                                table[countTurn-1][spellNum-1] +=1;
                            }
                            if (countTurn == 6) {
                                table[countTurn-1][spellNum-1] +=1;
                            }

                        }
                        countTurn++;

                    }



                }

                if (deckNum == 1) deck = deckOneCreator();
                else deck = deckTwoCreator();



            }


        }

        printTable(table);




    }

    static void printTable(double [][] table) {

        for (int i = 0; i < 6; i++) {
            System.out.println();
            for (int j = 0; j < 6; j++) {

                table[i][j] /= 10000;
                System.out.print(table[i][j] + "           ");

            }
        }

    }



    static List<Integer> getLandCardIndexes(List<Card> hand) {
        List<Integer> landCardIndexes = new ArrayList<Integer>();

        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).type == 'L') {
                landCardIndexes.add(i);

            }
        }

        return landCardIndexes;
    }

    static double getVariance(double mean, List<Double> prob) {
        double var = 0.0;
        for (int i = 0; i < prob.size(); i++) {
            var = (prob.get(i) - mean) * (prob.get(i) - mean);
        }
        return (var/(prob.size()-1));
    }
    static double getStd(double variance) {
        return Math.sqrt(variance);
    }


   static int getMaxSpellCostIndex(List<Card> hand, List<Card> inPlayCards) {
        int maxCost = 0;
        int index = -1;
        int tappedCards = inPlayCards.size();

        for (int i = 0; i < hand.size(); i++) {

            int tempCost = hand.get(i).cost;
            if (tempCost > maxCost && tempCost <= tappedCards) {
                maxCost = tempCost;
                index = i;
            }

        }

        return index;

    }


    static List<Card> deckOneCreator() {

        List<Card> deck = new ArrayList<Card>();

        for (int i = 0; i < 24; i++) {
            Card card = new Card('L', 0);
            deck.add(card);
        }

        for (int i = 0; i < 36; i++) {
            Card card;
            if (i < 10) {
                card = new Card('S', 1);
            }

            else if (i >= 10 && i < 20) {
                card = new Card('S', 2);
            }

            else if (i >= 20 && i < 30) {
                card = new Card('S', 3);
            }

            else if (i >= 30 && i < 32) {
                card = new Card('S', 4);
            }

            else if (i >= 32 && i <34) {
                card = new Card('S', 5);
            }
            else {
                card = new Card('S', 6);
            }

            deck.add(card);

        }

        return deck;

    }

    static List<Card> deckTwoCreator() {

        List<Card> deck = new ArrayList<Card>();

        for (int i = 0; i < 10; i++) {
            Card card = new Card('L', 0);
            deck.add(card);
        }

        for (int i = 0; i < 50; i++) {
            Card card;
            if (i < 10) {
                card = new Card('S', 1);
            }

            else if (i >= 10 && i < 20) {
                card = new Card('S', 2);
            }

            else if (i >= 20 && i < 30) {
                card = new Card('S', 3);
            }

            else if (i >= 30 && i < 32) {
                card = new Card('S', 4);
            }

            else if (i >= 32 && i <34) {
                card = new Card('S', 5);
            }
            else {
                card = new Card('S', 6);
            }

            deck.add(card);

        }

        return deck;

    }


    static List<Card> createHand(List<Card> deck) {
        List<Card> hand  = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            int randIndex = (int) (Math.random() * deck.size());
            Card card = new Card(deck.get(randIndex).type, deck.get(randIndex).cost);
            hand.add(card);
            deck.remove(randIndex);

        }

        return hand;

    }

    static Card drawCard(List<Card> deck, int deckSize) {

        int randIndex = (int) (Math.random() * deck.size());
        Card card = deck.get(randIndex);
        deck.remove(randIndex);
        return card;
    }


    static List<Card> shuffleDeck(List<Card> deck) {

        List<Card> shuffledDeck = new ArrayList<Card>();

        for (int i = 0; i < 60; i++) {

            int randIndex = (int) (Math.random() * deck.size());
            shuffledDeck.add(deck.get(randIndex));
            deck.remove(randIndex);

        }

        return shuffledDeck;

    }



}
