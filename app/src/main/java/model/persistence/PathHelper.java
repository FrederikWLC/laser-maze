package model.persistence;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathHelper {
    private static final String APPDATA_FOLDER_NAME = "LaserMaze";

    public PathHelper() { }


    public Path getResourcePath(String folderName) {
        URL url = this.getClass().getClassLoader().getResource(folderName);
        if (url == null) {
            throw new IllegalArgumentException("Resource folder not found on classpath: " + folderName);
        }
        try {
            return Paths.get(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URI for resource: " + folderName, e);
        }
    }

    public static Path getAppDataPath(String folderName) {
        Path base = getOSAppDataDir().resolve(folderName);
        try {
            return Files.createDirectories(base);
        } catch (IOException e) {
            throw new RuntimeException("Could not create AppData folder: " + base, e);
        }
    }

    private static Path getOSAppDataDir() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return Paths.get(System.getenv("APPDATA"), APPDATA_FOLDER_NAME);
        }
        if (os.contains("mac")) {
            return Paths.get(System.getProperty("user.home"),
                    "Library", "Application Support", APPDATA_FOLDER_NAME);
        }
        // Linux, BSD, etc.
        String xdg = System.getenv("XDG_DATA_HOME");
        Path base = xdg != null
                ? Paths.get(xdg)
                : Paths.get(System.getProperty("user.home"), ".local", "share");
        return base.resolve(APPDATA_FOLDER_NAME);
    }
}
