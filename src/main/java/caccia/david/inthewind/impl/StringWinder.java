package caccia.david.inthewind.impl;

import caccia.david.inthewind.api.Winder;

import java.util.LinkedList;
import java.util.List;

// For POC, we are not validating inputs
public class StringWinder implements Winder<String,String>
{
    public int csize = 255; // for POC we are using a restricted character set, not the full utf-16 size of 65535;

    @Override
    public List<String> unwind(String s, int count) // count must be at least two
    {
        List<int[]> ciphers = new LinkedList<>();
        int[] message = makeMessage(s);
        for(int i = 0; i < count - 1; i++)
        {
            ciphers.addAll(doUnwind(message));
            if(i < count - 2)
            {
                message = ciphers.remove(ciphers.size() - 1);
            }
        }
        return convertToString(ciphers); // todo consider if we even care to convert back to text here.
    }

    private List<String> convertToString(List<int[]> ciphers)
    {
        List<String> codedText = new LinkedList<>();
        for(int[] cipher: ciphers)
        {
            char[] chars = convertIntToChar(cipher);
            String text = String.valueOf(chars);
            codedText.add(text);
        }

        return codedText;
    }

    private char[] convertIntToChar(int[] cipher)
    {
        char[] chars = new char[cipher.length];
        for(int i = 0; i < chars.length; i++)
        {
            chars[i] = (char)cipher[i];
        }
        return chars;
    }

    private int[] convertCharToInt(char[] chars)
    {
        int[] cipher = new int[chars.length];
        for(int i = 0; i < chars.length; i++)
        {
            cipher[i] = chars[i];
        }
        return cipher;
    }


    public List<int[]> doUnwind(int[] message)
    {
        List<int[]> lists = new LinkedList<>();
        int[] key = makeKey(message.length);
        int[] cipher = makeCipher(message, key);
        lists.add(key);
        lists.add(cipher);

        return lists;
    }

    private int[] makeCipher(int[] message, int[] key)
    {
        int[] cipher = new int[key.length];
        for(int i = 0; i < key.length; i++)
        {
            cipher[i] = (csize + message[i] - key[i])%csize;
        }
        return cipher;
    }

    @Deprecated
    private int[] makeKey(int length)
    {
        int[] key = new int[length];
        for(int i = 0; i < length; i++)
        {
            key[i] = (int) (csize * Math.random()); // This is just for demo purposes;
            // in production, we would want stronger randomization.
        }
        return key;
    }

    private int[] makeMessage(String s)
    {
        char[] chars = s.toCharArray();
        int[] message = new int[chars.length];
        for (int i = 0; i < chars.length; i++)
        {
            message[i] = chars[i];
        }
        return message;
    }

    @Override
    public String wind(List<String> t)
    {
        List<int[]> ciphers = extractCiphers(t);


        return decode(ciphers);
    }

    private String decode(List<int[]> ciphers)
    {
        int[] charvalues = new int[ciphers.get(0).length];
        for(int i = 0; i < charvalues.length; i++)
        {
            for(int[] cipher: ciphers)
            {
                charvalues[i] += cipher[i];
            }
            charvalues[i] = charvalues[i]%csize;
        }
        return new String(convertIntToChar(charvalues));
    }

    private List<int[]> extractCiphers(List<String> t)
    {
        List<int[]> ciphers = new LinkedList<>();
        for(String s : t)
        {
            char[] chars = s.toCharArray();
            ciphers.add(convertCharToInt(chars));
        }
        return ciphers;
    }
}
