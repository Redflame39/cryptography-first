package model;

public class Decryption {

    private String encryptedText;
    private String decryptedText;

    public Decryption(String text) {
        this.encryptedText = text;
    }

    public void decryptPleifeir(int key) {
        //DecryptPleifeirAlgorithm
    }

    public void decryptVijiner(int key) {
        //DecryptVijinerAlgorithm
    }


    public String getDecryptedText() {
        return decryptedText;
    }

    public void setDecryptedText(String decryptedText) {
        this.decryptedText = decryptedText;
    }
}
