package io.muic.ooc;

import io.muic.ooc.Walker;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Walker w = new Walker();
        File path = new File("/Users/poon./prg/ooc/hw/a1/Prob4/docs/");
        w.start(path);

        System.out.println("number of directory: "+ w.getDirNumber());
        System.out.println("number of file: "+ w.getFileNumber());
        System.out.println("number of extensions: "+w.getExtensionNumber());
        w.listAllUnique();
        w.listTotalForEachExtension();


    }
}
