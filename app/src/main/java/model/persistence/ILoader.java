package model.persistence;

import java.nio.file.Path;

public interface ILoader {
    String getFolderName();

    Path getPath();
}
