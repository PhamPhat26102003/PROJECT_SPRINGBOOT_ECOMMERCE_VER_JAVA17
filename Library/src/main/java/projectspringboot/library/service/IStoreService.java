package projectspringboot.library.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface IStoreService {
    void openFileStore();
    String storeFile(MultipartFile file);
    Path uploadFile(String filename);
    Resource loadAsResource(String filename);
    void deleteFile(String filename);
}
