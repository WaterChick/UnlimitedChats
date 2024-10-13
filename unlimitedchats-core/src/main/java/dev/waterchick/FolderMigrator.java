package dev.waterchick;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FolderMigrator {

    private final String pluginName;
    private final String newFolderPath;

    public FolderMigrator(String pluginName, String newFolderPath) {
        this.pluginName = pluginName;
        this.newFolderPath = newFolderPath;
    }

    public void migrate() {
        // Cesta k původní složce
        File originalFolder = new File("plugins/" + pluginName);

        // Cesta k nové složce
        File newFolder = new File(newFolderPath);

        // Zkopíruj a smaž složku v jedné metodě
        if (originalFolder.exists()) {
            try {
                File licenseFile = new File(originalFolder, "license.txt");
                if (licenseFile.exists()) {
                    licenseFile.delete();
                }
                // Zkopíruj soubory ze staré složky do nové
                copyFiles(originalFolder.toPath(), newFolder.toPath());

                // Po úspěšném kopírování smaž původní složku
                deleteFolder(originalFolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Metoda pro kopírování souborů ze složky
    private void copyFiles(Path sourceFolder, Path destinationFolder) throws IOException {
        // Zajištění, že cílová složka existuje
        if (!Files.exists(destinationFolder)) {
            Files.createDirectories(destinationFolder);
        }

        Files.list(sourceFolder)
                .forEach(sourcePath -> {
                    Path destPath = destinationFolder.resolve(sourcePath.getFileName());
                    try {
                        if (Files.isDirectory(sourcePath)) {
                            // Pokud je to adresář, zkopírujte i jeho obsah
                            copyFiles(sourcePath, destPath);
                        } else {
                            // Kopírujte soubory, nahraďte existující
                            Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    // Pomocná metoda pro mazání složky
    private void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                }
                file.delete();
            }
        }
        folder.delete();
    }
}
