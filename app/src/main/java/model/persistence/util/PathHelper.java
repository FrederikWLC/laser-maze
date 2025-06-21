package model.persistence.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.Collections;

public class PathHelper {

    public PathHelper() { }


    public Path getResourcePath(String subFolderName) {
        URL url = getClass().getClassLoader().getResource(subFolderName);
        if (url == null) {
            throw new IllegalArgumentException(
                    "Resource folder not found on classpath: " + subFolderName);
        }

        try {
            URI uri = url.toURI();
            String scheme = uri.getScheme();

            if ("jar".equalsIgnoreCase(scheme)) {
                // e.g. uri = jar:file:/.../app.jar!/levels
                // Try to get existing zipfs, else mount it
                FileSystem fs;
                try {
                    fs = FileSystems.getFileSystem(uri);
                } catch (FileSystemNotFoundException ex) {
                    fs = FileSystems.newFileSystem(uri, Collections.emptyMap());
                }
                // The path inside the JAR is the part after the "!"
                String entry = uri.getSchemeSpecificPart()
                        .substring(uri.getSchemeSpecificPart().indexOf("!") + 1);
                return fs.getPath(entry);
            } else if ("file".equalsIgnoreCase(scheme)) {
                // running from exploded classes on disk
                return Paths.get(uri);
            } else {
                throw new UnsupportedOperationException(
                        "Cannot handle URI scheme: " + scheme);
            }
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(
                    "Unable to resolve resource path for: " + subFolderName, e);
        }
    }

    public static Path getAppDataPath(String subFolderName) {
        Path base = getOSAppDataDir().resolve(subFolderName);
        try {
            return Files.createDirectories(base);
        } catch (IOException e) {
            throw new RuntimeException("Could not create AppData folder: " + base, e);
        }
    }

    private static Path getOSAppDataDir() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return Paths.get(System.getenv("APPDATA"), FolderNameRegistry.APPDATA_FOLDER_NAME);
        }
        if (os.contains("mac")) {
            return Paths.get(System.getProperty("user.home"),
                    "Library", "Application Support", FolderNameRegistry.APPDATA_FOLDER_NAME);
        }
        // Linux, BSD, etc.
        String xdg = System.getenv("XDG_DATA_HOME");
        Path base = xdg != null
                ? Paths.get(xdg)
                : Paths.get(System.getProperty("user.home"), ".local", "share");
        return base.resolve(FolderNameRegistry.APPDATA_FOLDER_NAME);
    }
}
