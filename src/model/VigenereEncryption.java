package model;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class VigenereEncryption {

    private String sourceText;
    private String encryptedText;
    private String decryptedText;

    public VigenereEncryption(String text) {
        this.sourceText = text.toUpperCase(Locale.ROOT);
    }

    public String getEncryptedText() {
        return encryptedText;
    }

    public String getDecryptedText() {
        return decryptedText;
    }

    private final int ALPHABET_LENGTH = 33;

    private final List<Character> alphabet = Arrays.asList(
            'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П',
            'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'
    );

    private String getProgressiveKey(String key) {
        StringBuilder refactoredKey = new StringBuilder(key);
        for (int i = 0; i < sourceText.length() - key.length(); i++) {
            int index = (alphabet.indexOf(refactoredKey.charAt(i)) + 1) % ALPHABET_LENGTH;
            refactoredKey.append(alphabet.get(index));
        }
        return refactoredKey.toString();
    }

    public void encryptVigenereProgressive(String key) {
        String progressiveKey = getProgressiveKey(key.toUpperCase(Locale.ROOT));
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < sourceText.length(); i++) {
            result.append(alphabet.get((alphabet.indexOf(sourceText.charAt(i)) + alphabet.indexOf(progressiveKey.charAt(i))) % ALPHABET_LENGTH ));
        }
        this.encryptedText = result.toString();
    }

    public void decryptVigenereProgressive(String key) {
        StringBuilder result = new StringBuilder();
        String progressiveKey = getProgressiveKey(key.toUpperCase(Locale.ROOT));
        for (int i = 0; i < sourceText.length(); i++) {
            result.append(alphabet.get((alphabet.indexOf(sourceText.charAt(i)) - alphabet.indexOf(progressiveKey.charAt(i)) + ALPHABET_LENGTH) % ALPHABET_LENGTH));
        }
        this.decryptedText = result.toString();
    }
}