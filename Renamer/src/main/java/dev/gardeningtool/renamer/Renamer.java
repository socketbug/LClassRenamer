package dev.gardeningtool.renamer;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Renamer {

    private static final List<File> fileList = new ArrayList<>();

    public static void main(String[] args) {
        PrintStream printStream = System.out;
        File directory = new File("lunar" + File.separator);
        if (!directory.exists()) {
            printStream.println("Unable to find a directory to rename files from! Exiting...");
            directory.mkdir();
            System.exit(-1);
        }
        getFiles(Objects.requireNonNull(directory.listFiles()));
        fileList.stream().filter(file -> file.getName().endsWith(".lclass")).forEach(file -> {
            if (!file.renameTo(new File(file.getPath().replace(file.getName(), "") + file.getName().replace(".lclass", ".class")))) {
                System.err.println("Failed renaming for " + file.getName());
            }
        });
    }

    private static void getFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                getFiles(Objects.requireNonNull(file.listFiles()));
            } else {
                fileList.add(file);
                System.out.println("Found file " + file.getName());
            }
        }
    }
}
