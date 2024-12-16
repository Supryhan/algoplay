package problemslab;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ReadWriteFile {
    public static void main(String[] args) {
        ReadWriteFile readWriteFile = new ReadWriteFile();
        try {
            readWriteFile.storeMessage("my message - 42");
        } catch (IOException ioe) {
            System.out.println("IO Error!");
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input your name:");
        String inputName = scanner.nextLine();
        System.out.println("Hello " + inputName);
    }


    private void storeMessage(String message) throws IOException {
        String name = "inputdata.txt";
        File file = new File(name);
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(message);
        writer.flush();
        writer.close();
        System.out.println("Saved");
    }
}
