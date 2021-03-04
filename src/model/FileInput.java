package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileInput {

    private final String path;

    public FileInput(String path)
    {
        this.path = path;
    }

    public String read()
    {
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

}
