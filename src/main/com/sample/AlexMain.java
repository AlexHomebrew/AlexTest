package main.com.sample;

import java.io.File;

public class AlexMain
{
    public AlexMain()
    {
        System.out.println("Started Alex Test");
    }

    public static void main(String[] args)
    {
        Poker poker = new Poker();
        File f = new File("poker.txt");
        System.out.println("Player 1 has = " + poker.solution(f) + " wins.");
    }
}
