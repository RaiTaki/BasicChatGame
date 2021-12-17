package xyz.raitaki.vantachatgames;

import org.bukkit.entity.Player;

import java.util.Random;

public class methods {

    public static String shuffleletters(Random random, String inputString)
    {
        // Convert your string into a simple char array:
        char a[] = inputString.toCharArray();
        // Scramble the letters using the standard Fisher-Yates shuffle,
        for( int i=0 ; i<a.length ; i++ )
        {
            int j = random.nextInt(a.length);
            // Swap letters
            char temp = a[i]; a[i] = a[j];  a[j] = temp;
        }
        return new String(a);
    }
}
