package main.com.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Poker
{
    //Special case for a wrap around straight
    private static final int[] ACE_STRAIGHT = {2, 3, 4, 5, 14};
    private static final String RANKS = "23456789TJQKA";
    private static final String SUITS = "HDCS";
    public static final long HIGH_CARD  = 1;                  // Highest value card.
    public static final long ONE_PAIR   = 100;                // Two cards of the same rank.
    public static final long TWO_PAIRS  = 10000;              // Two different pairs.
    public static final long THREE      = 1000000;            // Three cards of the same rank.
    public static final long STRAIGHT   = 100000000;          // All cards are consecutive rank.
    public static final long FLUSH      = 10000000000L;       // All cards of the same suit.
    public static final long FULL_HOUSE = 1000000000000L;     // Three of a kind and a pair.
    public static final long FOUR       = 100000000000000L;   // Four cards of the same rank.
    public static final long STR_FLUSH  = 10000000000000000L; // All cards are consecutive values of same suit

    public Poker()
    {}

    /**
     * Determines the number of Player 1 wins
     * @param sourceCards file containing the card list
     * @return the number of Player 1 wins
     */
    public int solution(File sourceCards)
    {
        int player1Wins = 0;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(sourceCards));
            String line = br.readLine();
            while (line != null)
            {
                String[] wholeLine = line.split(" ");
                //TODO: validate the input line.

                String[] player1 = Arrays.copyOfRange(wholeLine, 0, 5);
                String[] player2 = Arrays.copyOfRange(wholeLine, 5, 10);

                int[] player1Ranks = getRanks(player1);
                int[] player1Suits = getSuits(player1);

                int[] player2Ranks = getRanks(player2);
                int[] player2Suits = getSuits(player2);

                long player1Value = handValue(player1Ranks, player1Suits);
                long player2Value = handValue(player2Ranks, player2Suits);
                //Rank
                if (player1Value > player2Value)
                {
                    player1Wins++;
                }
                else if (player1Value == player2Value)
                {
                    //Compare each card in turn, true if player 1 is higher
                    if (compareAll(player1Ranks, player2Ranks)) player1Wins++;
                }
                line = br.readLine();
            }
        }
        catch (IOException | ArrayIndexOutOfBoundsException | IllegalArgumentException | NullPointerException ex)
        {
            //TODO: Add some better handling
            player1Wins = -1;
        }
        return player1Wins;
    }

    /**
     * Returns the value for the given hand.
     * The first parameter MUST be an array of 5 entries of the ranks of the cards
     * in the set  {2,3,4,5,6,7,8,9,T,J,Q,K,A}
     * and the second parameter MUST be an array of 5 entries of the suits of the cards
     * in the set  {D,C,H,S}
     * @param myRank the ranks of the 5 cards
     * @param mySuit the suits of the 5 cards
     * @return the value of that hand
     */
    public long handValue(int[] myRank, int[] mySuit)
    {
        //Check for flush
        boolean flush = true;
        for (int j=1; j < mySuit.length; j++)
        {
            if (mySuit[j] != mySuit[0])
            {
                flush = false;
                break;
            }
        }
        //Check for straight
        boolean straight = true;
        //Check for Ace wraparound
        if (!Arrays.equals(myRank, ACE_STRAIGHT))
        {
            for (int j = 1; j < myRank.length; j++)
            {
                if (myRank[j] != myRank[0] + j)
                {
                    straight = false;
                    break;
                }
            }
        }

        if (flush && straight) return STR_FLUSH * myRank[3];

        if (flush) return FLUSH;

        if (straight) return STRAIGHT * myRank[3];

        //Not a flush or straight
        //Check for three and four of a kind. If cards contain 3 or 4 of a kind
        //the middle entry will contain that rank.
        int rankCount = 1;
        if (myRank[0] == myRank[2]) rankCount++;
        if (myRank[1] == myRank[2]) rankCount++;
        if (myRank[3] == myRank[2]) rankCount++;
        if (myRank[4] == myRank[2]) rankCount++;

        if (rankCount == 4) return FOUR * myRank[2];

        if (rankCount == 3)  //either Full House or Three of a Kind
        {
            if (myRank[0] != myRank[2])
            {
                if (myRank[0] == myRank[1]) return FULL_HOUSE * myRank[2];
            }
            if (myRank[4] != myRank[2])
            {
                if (myRank[4] == myRank[3]) return FULL_HOUSE * myRank[2];
            }
            //Three of a kind
            return THREE * myRank[2];
        }
        //Check for pairs
        //Walk through the cards saving the index of the first of a pair.
        List<Integer> pairs = new ArrayList<>();
        for (int j=1; j < myRank.length; j++)
        {
            if (myRank[j] == myRank[j-1]) pairs.add(j-1);
        }
        if (pairs.size() == 2)
        {
            return TWO_PAIRS * myRank[pairs.get(1)];
        }
        if (pairs.size() == 1)
        {
            return ONE_PAIR * myRank[pairs.get(0)];
        }
        //It must be High Card
        return 1;
    }
    /**
     * Compares the cards to determine which has the highest rank
     * @param ranks1 player 1 ranks
     * @param ranks2 player 2 ranks
     * @return if player 1 has higher cards
     */
    public boolean compareAll(int[] ranks1, int[] ranks2)
    {
        for (int k = ranks1.length-1; k >=0 ; k--)
        {
            if (ranks1[k] < ranks2[k])
            {
                return false;
            }
            else if (ranks1[k] > ranks2[k])
            {
                return true;
            }
        }
        return false; //All ranks are equal
    }

    /**
     * Scan the cards to determine the ranks
     * @param cards which cards are held
     * @return the sorted rank values
     */
    public int[] getRanks(String[] cards)
    {
        int[] myRank = new int[5];
        for (int i=0; i<cards.length; i++)
        {
            char point = cards[i].charAt(0);

            myRank[i] = RANKS.indexOf(point) + 2;  //so rank will equal point value
        }
        //Sort the rank. Note that it doesn't matter that the suit for each
        //card won't be preserved as long as we preserve the set of suits
        Arrays.sort(myRank);
        return myRank;
    }

    /**
     * Scan the cards to determine the suits
     * @param cards which cards are held
     * @return the suit values
     */
    public int[] getSuits(String[] cards)
    {
        int[] mySuit = new int[5];
        for (int i=0; i<cards.length; i++)
        {
            char suit = cards[i].charAt(1);

            mySuit[i] = SUITS.indexOf(suit);
        }
        return mySuit;
    }
}
