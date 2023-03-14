package pro.sky.budgetapp.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.budgetapp.services.FilesService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {
    @Value("${path.to.data.file}")
    private String dataFilePath;

    private Path dataPath = Path.of(dataFilePath);

    @Value("${name.of.data.file}")
    private String dataFileName;

    public boolean saveToFile(String json) {
        try {
            Files.writeString(dataPath, json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String readFromFile() {
        try {
            return Files.readString(dataPath);
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    private boolean cleanDataFile() {
        try {
            Files.deleteIfExists(dataPath);
            Files.createFile(Path.of(dataPath.toUri()));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
