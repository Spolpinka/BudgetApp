package pro.sky.budgetapp.services;

import org.springframework.stereotype.Service;

@Service
public interface FilesService {
    boolean saveToFile(String json);

    String readFromFile();
}
