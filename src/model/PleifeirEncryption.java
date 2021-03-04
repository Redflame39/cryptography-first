package model;

import java.util.Arrays;
import java.util.Locale;
import java.util.Stack;

public class PleifeirEncryption {

    private String sourceText;
    private String encryptedText;
    private String decryptedText;
    private char[][] matrixKey;

    private char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public PleifeirEncryption(String text) {
        this.sourceText = text.toUpperCase(Locale.ROOT);
    }

    public String getEncryptedText() {
        return encryptedText;
    }

    public String getDecryptedText() {
        return decryptedText;
    }

    private String updateMessage() {
        String message;

        message = (sourceText.length() % 2 == 0) ? sourceText :
                (sourceText.charAt(sourceText.length() - 1) != 'X') ? sourceText.concat("X") : sourceText.concat("Y");

        message = message.replaceAll("I", "J");
        StringBuilder messageFix = new StringBuilder();
        for (int i = 0; i < message.length(); i += 2) {
            char c1 = message.charAt(i), c2 = message.charAt(i + 1);
            messageFix.append(c1);
            if (c1 == c2) {
                messageFix.append(c1 != 'X' ? 'X' : 'Y');
            }
            messageFix.append(c2);
        }
        message = messageFix.toString();
        if (message.length() % 2 != 0)
            message = (message.charAt(message.length() - 1) != 'X') ? message.concat("X") : message.concat("Y");
        return message;
    }

    private void buildKey(String key) {
        StringBuilder sb = new StringBuilder(key);

        for (int i = 0; i < alphabet.length; i++) {
            if (!sb.toString().contains(Character.toString(alphabet[i])))
                sb.append(alphabet[i]);
        }

        char[][] matrixKey = new char[5][5];

        int ptr = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrixKey[i][j] = sb.toString().charAt(ptr++);
            }
        }

        this.matrixKey = matrixKey;
    }

    private char[] encryptBigram(char c1, char c2) {
        int row1 = 0, column1 = 0, row2 = 0, column2 = 0;
        for (int j = 0; j < matrixKey.length; j++) {
            for (int k = 0; k < matrixKey[j].length; k++) {
                if (c1 == matrixKey[j][k]) {
                    row1 = j;
                    column1 = k;
                }
                if (c2 == matrixKey[j][k]) {
                    row2 = j;
                    column2 = k;
                }
            }
        }

        if (row1 == row2) {
            c1 = (column1 != 4) ? matrixKey[row1][column1 + 1] : matrixKey[row1][0];
            c2 = (column2 != 4) ? matrixKey[row2][column2 + 1] : matrixKey[row2][0];
        } else if (column1 == column2) {
            c1 = (row1 != 4) ? matrixKey[row1 + 1][column1] : matrixKey[0][column1];
            c2 = (row2 != 4) ? matrixKey[row2 + 1][column2] : matrixKey[0][column2];
        } else {
            c1 = matrixKey[row1][column2];
            c2 = matrixKey[row2][column1];
        }

        return new char[]{c1, c2};
    }

    public void encryptPleifeir(String key) {
        String message = updateMessage();
        buildKey(key.toUpperCase(Locale.ROOT));

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < message.length(); i += 2) {
            char c1 = message.charAt(i), c2 = message.charAt(i + 1);
            result.append(encryptBigram(c1, c2));
        }

        this.encryptedText = result.toString();

    }

    private char[] decryptBigram(char c1, char c2) {
        int row1 = 0, column1 = 0, row2 = 0, column2 = 0;
        for (int j = 0; j < matrixKey.length; j++) {
            for (int k = 0; k < matrixKey[j].length; k++) {
                if (c1 == matrixKey[j][k]) {
                    row1 = j;
                    column1 = k;
                }
                if (c2 == matrixKey[j][k]) {
                    row2 = j;
                    column2 = k;
                }
            }
        }

        if (row1 == row2) {
            c1 = (column1 != 0) ? matrixKey[row1][column1 - 1] : matrixKey[row1][4];
            c2 = (column2 != 0) ? matrixKey[row2][column2 - 1] : matrixKey[row2][4];
        } else if (column1 == column2) {
            c1 = (row1 != 0) ? matrixKey[row1 - 1][column1] : matrixKey[4][column1];
            c2 = (row2 != 0) ? matrixKey[row2 - 1][column2] : matrixKey[4][column2];
        } else {
            c1 = matrixKey[row1][column2];
            c2 = matrixKey[row2][column1];
        }

        return new char[]{c1, c2};
    }

    public void decryptPleifeir(String key) {
        buildKey(key);

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < sourceText.length(); i += 2) {
            char c1 = sourceText.charAt(i), c2 = sourceText.charAt(i + 1);
            result.append(decryptBigram(c1, c2));
        }

        this.decryptedText = result.toString();
    }
}
