package com.gasoiltracker.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileObjectsLoader{

    public static final String SUBPATH = System.getenv("APPDATA")
+"/gasoiltracker/";

    public static void objToFile(Object obj, String fileName) {
        File initialFolder = new File(SUBPATH);
        if (!initialFolder.exists()){
            initialFolder.mkdir();
        }
        try {   
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SUBPATH + fileName + ".obj"));
            oos.writeObject(obj);
            oos.close();
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + e.getMessage());
        }
    }

    public static Object objFromFile(String fileName){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SUBPATH + fileName))) {
            Object obj = ois.readObject();
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer el objeto del archivo: " + e.getMessage());
        }
        return null;
    }
}
