package test.com.sample;

import main.com.sample.Poker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokerTest
{
    Poker poker = new Poker();

    @Test
    void compareAllTest()
    {
        int[] tru1 = {4, 6, 8, 10, 12};
        int[] tru2 = {3, 6, 8, 10, 12};
        assertTrue(poker.compareAll(tru1, tru2), "Failed comparing all");
        int[] tru1a = {4, 6, 8, 10, 13};
        int[] tru2a = {3, 6, 8, 10, 12};
        assertTrue(poker.compareAll(tru1a, tru2a), "Failed comparing all");
        int[] tru1b = {4, 6, 9, 10, 12};
        int[] tru2b = {4, 6, 8, 10, 12};
        assertTrue(poker.compareAll(tru1b, tru2b), "Failed comparing all");
        int[] fal1 = {4, 6, 7, 10, 12};
        int[] fal2 = {3, 6, 8, 10, 12};
        assertFalse(poker.compareAll(fal1, fal2), "Failed comparing all");
    }

    @Test
    void handValueStraights()
    {
        String[] straightK = {"KS", "QC", "TD", "JH", "9H"};
        int[] straightKR = poker.getRanks(straightK);
        int[] straightKS = poker.getSuits(straightK);
        assertEquals( Poker.STRAIGHT * 12, poker.handValue(straightKR, straightKS), "Wrong Rank for STRAIGHT");

        String[] straightA = {"KS", "QC", "TD", "JH", "AH"};
        int[] straightAR = poker.getRanks(straightA);
        int[] straightAS = poker.getSuits(straightA);
        assertEquals( Poker.STRAIGHT * 13, poker.handValue(straightAR, straightAS), "Wrong Rank for STRAIGHT");

        String[] straight5 = {"AH", "5C", "2D", "4H", "3H"};
        int[] straight5R = poker.getRanks(straight5);
        int[] straight5S = poker.getSuits(straight5);
        assertEquals( Poker.STRAIGHT * 5, poker.handValue(straight5R, straight5S), "Wrong Rank for STRAIGHT");
    }

    @Test
    void handValueFlushs()
    {
        String[] flush1 = {"KS", "QS", "6S", "JS", "9S"};
        int[] flush1R = poker.getRanks(flush1);
        int[] flush1S = poker.getSuits(flush1);
        assertEquals( Poker.FLUSH, poker.handValue(flush1R, flush1S), "Wrong Rank for FLUSH");

        String[] straightFlush = {"8C", "QC", "TC", "JC", "9C"};
        int[] sFlushR = poker.getRanks(straightFlush);
        int[] sFlushS = poker.getSuits(straightFlush);
        assertEquals( Poker.STR_FLUSH * 11, poker.handValue(sFlushR, sFlushS), "Wrong Rank for STRAIGHT FLUSH");

        String[] flush2 = {"9H", "5H", "TH", "4H", "3H"};
        int[] flush2R = poker.getRanks(flush2);
        int[] flush2S = poker.getSuits(flush2);
        assertEquals( Poker.FLUSH, poker.handValue(flush2R, flush2S), "Wrong Rank for FLUSH");
    }

    @Test
    void handValueFullHouse()
    {
        String[] fullHouseK = {"KS", "KH", "6S", "6H", "KD"};
        int[] fullHouseKR = poker.getRanks(fullHouseK);
        int[] fullHouseKS = poker.getSuits(fullHouseK);
        assertEquals( Poker.FULL_HOUSE * 13, poker.handValue(fullHouseKR, fullHouseKS), "Wrong Rank for FULL_HOUSE");

        String[] fullHouse2 = {"8C", "2C", "8H", "2D", "2S"};
        int[] fullHouse2R = poker.getRanks(fullHouse2);
        int[] fullHouse2S = poker.getSuits(fullHouse2);
        assertEquals( Poker.FULL_HOUSE * 2, poker.handValue(fullHouse2R, fullHouse2S), "Wrong Rank for FULL HOUSE");
    }

    @Test
    void handValueThreeOfAKind()
    {
        String[] three7 = {"KS", "7S", "6S", "7D", "7H"};
        int[] three7R = poker.getRanks(three7);
        int[] three7S = poker.getSuits(three7);
        assertEquals( Poker.THREE * 7, poker.handValue(three7R, three7S), "Wrong Rank for Three Of A Kind");

        String[] three2 = {"2C", "QC", "2D", "2H", "9C"};
        int[] three2R = poker.getRanks(three2);
        int[] three2S = poker.getSuits(three2);
        assertEquals( Poker.THREE * 2, poker.handValue(three2R, three2S), "Wrong Rank for Three Of A Kind");

        String[] threeA = {"9H", "5H", "AH", "AD", "AS"};
        int[] threeAR = poker.getRanks(threeA);
        int[] threeAS = poker.getSuits(threeA);
        assertEquals( Poker.THREE * 14, poker.handValue(threeAR, threeAS), "Wrong Rank for Three Of A Kind");
    }

    @Test
    void handValueTwoPairs()
    {
        String[] two79 = {"9H", "9S", "6S", "7D", "7H"};
        int[] two79R = poker.getRanks(two79);
        int[] two79S = poker.getSuits(two79);
        assertEquals( Poker.TWO_PAIRS * 9, poker.handValue(two79R, two79S), "Wrong Rank for Two Pairs");

        String[] two2A = {"2C", "AC", "2D", "AH", "9C"};
        int[] two2AR = poker.getRanks(two2A);
        int[] two2AS = poker.getSuits(two2A);
        assertEquals( Poker.TWO_PAIRS * 14, poker.handValue(two2AR, two2AS), "Wrong Rank for Two Pairs");
    }
    @Test
    void handValueOnePair()
    {
        String[] one7 = {"KS", "7S", "6S", "7D", "AH"};
        int[] one7R = poker.getRanks(one7);
        int[] one7S = poker.getSuits(one7);
        assertEquals( Poker.ONE_PAIR * 7, poker.handValue(one7R, one7S), "Wrong Rank for One Pair");

        String[] one2 = {"2C", "QC", "8D", "2H", "9C"};
        int[] one2R = poker.getRanks(one2);
        int[] one2S = poker.getSuits(one2);
        assertEquals( Poker.ONE_PAIR * 2, poker.handValue(one2R, one2S), "Wrong Rank for One Pair");

        String[] oneA = {"9H", "5H", "AH", "3D", "AS"};
        int[] oneAR = poker.getRanks(oneA);
        int[] oneAS = poker.getSuits(oneA);
        assertEquals( Poker.ONE_PAIR * 14, poker.handValue(oneAR, oneAS), "Wrong Rank for One Pair");
    }
    @Test
    void handValueHighCard()
    {
        String[] highK = {"KS", "9S", "6S", "3D", "4H"};
        int[] highKR = poker.getRanks(highK);
        int[] highKS = poker.getSuits(highK);
        assertEquals( Poker.HIGH_CARD, poker.handValue(highKR, highKS), "Wrong Rank for High Card");
    }
}