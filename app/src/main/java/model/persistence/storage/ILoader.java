package model.persistence.storage;

import java.nio.file.Path;

public interface ILoader {
    String getFolderName();

    Path getPath();
}
