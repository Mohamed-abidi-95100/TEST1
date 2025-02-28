package com.example.stage.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

    private static final String UPLOAD_DIR = "uploads/";

    // Créer le répertoire d'upload s'il n'existe pas
    static {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    // Enregistrer un fichier dans le répertoire d'upload
    public static String saveFile(String fileName, byte[] content) {
        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);

            // Vérifier si le fichier existe déjà
            if (Files.exists(path)) {
                throw new IOException("Le fichier existe déjà : " + fileName);
            }

            // Écrire le contenu du fichier
            Files.write(path, content);
            return path.toString(); // Retourner le chemin du fichier
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Échec de l'enregistrement
        }
    }

    // Supprimer un fichier
    public static boolean deleteFile(String filePath) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}