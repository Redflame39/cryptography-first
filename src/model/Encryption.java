package model;

public class Encryption {

    private String sourceText;
    private String encryptedText;

    public Encryption(String text) {
        this.sourceText = text;
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

    public void encryptPleifeir(int key) {
        //EncryptPleifeirAlgorithm
    }

    public void encryptVijiner(int key) {
        //EncryptVijinerAlgorithm
    }

    public String getEncryptedText() {
        return encryptedText;
    }

    public void setEncryptedText(String encryptedText) {
        this.encryptedText = encryptedText;
    }
}
