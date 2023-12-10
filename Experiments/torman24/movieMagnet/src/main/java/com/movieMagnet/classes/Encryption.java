package com.movieMagnet.classes;

public class Encryption {

    static char[] base = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'};
    static char[] key = {'c','5','e','Q','W','n','p','E','s','R','4','T','j','k','Y','0','b','U','a','I','o','O','x','P','f','h','A','w','y','8','S','D','F','l','i','q','r','7','G','t','H','J','K','1','z','L','Z','d','X','g','3','C','u','V','B','m','2','6','N','M','v','9'};
    public static String Encrypt(String plainText){

        StringBuilder cipherText = new StringBuilder();
        for(int i = 0; i < plainText.length(); i++) {
            char letter = plainText.charAt(i);
            for (int j = 0; j < base.length; j++) {
                if (letter == base[j]) {
                    cipherText.append(key[j]);
                }
            }
        }
        return cipherText.toString();
    }

    public static String Decrypt(String plainText){

        StringBuilder cipherText = new StringBuilder();
        for(int i = 0; i < plainText.length(); i++) {
            char letter = plainText.charAt(i);
            for (int j = 0; j < 26; j++) {
                if (letter == key[j]) {
                    cipherText.append(base[j]);
                }
            }
        }
        return cipherText.toString();
    }
}
