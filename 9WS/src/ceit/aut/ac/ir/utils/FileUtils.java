package ceit.aut.ac.ir.utils;

import ceit.aut.ac.ir.model.Note;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {

    private static final String NOTES_PATH = "./notes/";

    //It's a static initializer. It's executed when the class is loaded.
    //It's similar to constructor
    static {
        boolean isSuccessful = new File(NOTES_PATH).mkdirs();
        System.out.println("Creating " + NOTES_PATH + " directory is successful: " + isSuccessful);
    }

    public static File[] getFilesInDirectory() {
        return new File(NOTES_PATH).listFiles();
    }


    public static String fileReader(File file) {
        try (FileInputStream in = new FileInputStream(file)) {
            ObjectInputStream ois = new ObjectInputStream(in);
            Note selected = (Note) ois.readObject();
            ois.close();
            return selected.getContent();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, file.getName() + " NOT FOUND!!", "WARNING", JOptionPane.WARNING_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static void fileWriter(String content) {
        String fileName = getProperFileName(content);
        try (FileOutputStream toWrite = new FileOutputStream(NOTES_PATH + fileName)) {
            ObjectOutputStream oos = new ObjectOutputStream(toWrite);
            Note note = new Note(fileName, content, "1399/2/31");
            oos.writeObject(note);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //TODO: Phase1: define method here for reading file with InputStream
    //TODO: Phase1: define method here for writing file with OutputStream

    //TODO: Phase2: proper methods for handling serialization

    private static String getProperFileName(String content) {
        int loc = content.indexOf("\n");
        String fileSuffix = new SimpleDateFormat("yyyyMMdd").format(new Date()) + " ";
        if (loc != -1) {
            return fileSuffix + content.substring(0, loc) + ".bin";
        }
        if (!content.isEmpty()) {
            return fileSuffix + content + ".bin";
        }
        return fileSuffix + "_new file.BIN";

    }
}
