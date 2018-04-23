package com.sloniec.road.shared.commons;

import java.io.File;

public class FileCommons {
    public static void checkIfIsFile(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            System.out.println("BLAD: Wartosc '" + path + "' nie wskazuje na plik!");
            System.exit(0);
        }
    }

    public static void checkIfIsFolder(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            System.out.println("BLAD: Wartosc '" + path + "' nie wskazuje na folder!");
            System.exit(0);
        }
    }

}
