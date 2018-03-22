package io.muic.ooc;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Walker extends DirectoryWalker {
    private int fileNumber = 0;
    private int dirNumber = 0;
    private Map<String, Integer> extensionsCounterStorage = new HashMap<>();

    @Override
    protected boolean handleDirectory(File directory, int depth, Collection results) throws IOException {
        dirNumber++;
        return true;
    }

    @Override
    protected void handleFile(File file, int depth, Collection results) throws IOException {

        fileNumber++;
        String extension = FilenameUtils.getExtension(file.toString());

        if (extension.equals("")) {
            extension = "No extension";
        }
        if (extensionsCounterStorage.containsKey(extension)) {
            extensionsCounterStorage.put(extension, extensionsCounterStorage.get(extension) + 1);
        } else {
            extensionsCounterStorage.put(extension, 1);
        }
    }

    public void start(File path) throws IOException {
        if (path.isDirectory())
            walk(path, null);
        else
            System.out.println("not at directory");
    }


    public int getFileNumber() {
        return fileNumber;
    }

    public int getDirNumber() {
        return dirNumber;
    }

    public int getExtensionNumber() {
        return extensionsCounterStorage.keySet().size();
    }

    public void listAllUnique() {
        for (String s : extensionsCounterStorage.keySet()) {
            if (s.equals("No extension"))
                continue;
            else
                System.out.println(s);
        }
    }

    public void listTotalForEachExtension() {
        for (String s : extensionsCounterStorage.keySet()) {
            System.out.printf("%s %d \n", s, extensionsCounterStorage.get(s));
        }
    }
}