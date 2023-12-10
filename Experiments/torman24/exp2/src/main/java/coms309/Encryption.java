package coms309;

public class Encryption {

    static char[] base = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    static char[] key = {'Q','W','E','R','T','Y','U','I','O','P','A','S','D','F','G','H','J','K','L','Z','X','C','V','B','N','M'};
    public static String Encrypt(String plainText){

        StringBuilder cipherText = new StringBuilder();
        for(int i = 0; i < plainText.length(); i++) {
            char letter = plainText.charAt(i);
            for (int j = 0; j < 26; j++) {
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
