package model;

import java.util.Locale;

public class RailFenceEncryption {

    private String sourceText;
    private String encryptedText;
    private String decryptedText;

    public RailFenceEncryption(String text) {
        this.sourceText = text.toUpperCase(Locale.ROOT);
    }

    public String getEncryptedText() {
        return encryptedText;
    }

    public String getDecryptedText() {
        return decryptedText;
    }

    private char[][] makeRailFenceMap(int key) {
        char[][] map = new char[key][sourceText.length()];

        int period = 2 * (key - 1);

        int j = 0;
        for (int i = 0; i < sourceText.length(); i++) {
            int rest = i % period;
            int pos = key - 1 - Math.abs(key - 1 - rest);
            map[pos][j++] = sourceText.charAt(i);
        }

        return map;
    }

    public void encryptRailFence(int key) {
        if (key == 1) {
            this.encryptedText = sourceText;
            return;
        }
        char[][] map = makeRailFenceMap(key);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < key; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] != 0)
                    sb.append(map[i][j]);
            }
        }
        this.encryptedText = sb.toString().replaceAll("\\s+", "");
    }

    public void decryptRailFence(int key) {
        if (key == 1) {
            this.decryptedText = this.sourceText;
            return;
        }
        StringBuilder result = new StringBuilder();
        char[][] matrix = new char[key][sourceText.length()];
        for (int i = 0; i < key; i++) {
            for (int j = 0; j < sourceText.length(); j++) {
                matrix[i][j] = '\n';
            }
        }
        boolean down = true;
        int row = 0, col = 0;
        for (int i = 0; i < sourceText.length(); i++) {
            if (row == 0)
                down = true;
            if (row == key - 1)
                down = false;
            matrix[row][col++] = '*';
            if (down) row++;
            else row--;
        }
        int index = 0;
        for (int i = 0; i < key; i++)
            for (int j = 0; j < sourceText.length(); j++)
                if ((matrix[i][j] == '*') && (index < sourceText.length()))
                    matrix[i][j] = sourceText.charAt(index++);

        row = 0;
        col = 0;
        for (int i = 0; i < sourceText.length(); i++) {
            if (row == 0)
                down = true;
            if (row == key - 1)
                down = false;
            if (matrix[row][col] != '*')
                result.append(matrix[row][col++]);
            if (down) row++;
            else row--;
        }
        this.decryptedText = result.toString();
    }
}
