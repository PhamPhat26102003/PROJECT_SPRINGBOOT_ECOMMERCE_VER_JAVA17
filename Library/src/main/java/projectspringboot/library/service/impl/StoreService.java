package projectspringboot.library.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import projectspringboot.library.exception.FileNotFoundException;
import projectspringboot.library.exception.StoreException;
import projectspringboot.library.service.IStoreService;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StoreService implements IStoreService {
    
    @Value("${storage.location}")
    private String storageLocation;
    @Override
    public void openFileStore() {
        try{
            Files.createDirectories(Paths.get(storageLocation));
        }catch(IOException e){
            throw new StoreException("Error with file!!");
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if(file.isEmpty()){
            throw new StoreException("Can't store an empty file!!");
        }

        try{
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, Paths.get(storageLocation).resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new StoreException("Error storing file!!" + filename, e);
        }
        return filename;
    }

    @Override
    public Path uploadFile(String filename) {
        return Paths.get(storageLocation).resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try{
            Path file = uploadFile(filename);
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()){
                return resource;
            }else{
                throw new FileNotFoundException("Could not find the file" + filename);
            }
        }catch(MalformedURLException malformedURLException){
            throw new FileNotFoundException("Could not find the file" + filename, malformedURLException);
        }
    }

    @Override
    public void deleteFile(String filename) {
        Path file = uploadFile(filename);
        try{
            FileSystemUtils.deleteRecursively(file);
        }catch(IOException e){
            System.out.println(e);
        }
    }
}
