package dev.gardeningtool.renamer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Renamer {

    private final File directory = new File("lunar" + File.separator);
    private final Set<File> files = new HashSet<>();

    public static void main(String[] args) {
        Renamer renamer = new Renamer();
        renamer.init();
        renamer.rename();
    }

    private void init() {
        if (!directory.isDirectory()) {
            directory.mkdir();
            throw new RuntimeException("Unable to find a directory to rename files from! Exiting...");
        }
        getFiles(directory.listFiles());
    }

    private void getFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                getFiles(file.listFiles());
            } else {
                if (file.getName().endsWith(".lclass")) {
                    this.files.add(file);
                    System.out.println("Found file " + file.getPath());
                }
            }
        }
    }

    private void rename() {
        List<File> files = new ArrayList<>();
        this.files.forEach(file -> {
            if (!file.renameTo(new File(file.getPath().replace(file.getName(), "") + file.getName().replace(".lclass", ".class")))) {
                files.add(file);
            } else {
                System.out.println("Successfully renamed " + file.getPath());
            }
        });
        files.forEach(file -> System.out.println("Failed renaming for " + file.getPath()));
        System.out.println("Successfully renamed " + (this.files.size() - files.size()) + " .lclass files!");
        System.out.println("Failed renaming for " + files.size() + " .lclass files");
    }

}
