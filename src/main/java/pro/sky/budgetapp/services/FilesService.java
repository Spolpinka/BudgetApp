package pro.sky.budgetapp.services;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface FilesService {
    boolean saveToFile(String json);

    String readFromFile();

    File getDataFile();
}
