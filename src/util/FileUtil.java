package util;

import java.util.List;

public abstract class FileUtil<T>{

    public abstract List<T> readFile(String filePath);

    public abstract void writeFile(String filePath, List<T> items);

    public abstract void clearFile(String filePath);
}
