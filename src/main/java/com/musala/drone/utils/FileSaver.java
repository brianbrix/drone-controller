package com.musala.drone.utils;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileSaver {

    private static final String RESOURCES_DIR = Objects.requireNonNull(FileSaver.class.getResource("/"))
            .getPath();

    public static String save(MultipartFile file, String imageName) throws Exception {
        Path newFile = Paths.get(RESOURCES_DIR + System.currentTimeMillis() + "-" + imageName);
        Files.createDirectories(newFile.getParent());
        byte [] content=file.getBytes();
        Files.write(newFile, content);

        return newFile.toAbsolutePath()
                .toString();
    }

    public static FileSystemResource findInFileSystem(String location) throws FileNotFoundException {
        try {
            return new FileSystemResource(Paths.get(location));
        } catch (Exception e) {
            throw new FileNotFoundException();
        }
    }
}
