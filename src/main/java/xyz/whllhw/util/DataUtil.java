package xyz.whllhw.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataUtil {
    public static final String UPLOAD_FOLDER = "upload/";

    public static String saveFile(MultipartFile file, Long taskId, String user, String dataType) throws IOException {
        Path path = Paths.get(UPLOAD_FOLDER, String.format("%d-%s-%d-%s", taskId, user, System.currentTimeMillis(), dataType));
        File dir = new File(UPLOAD_FOLDER);
        dir.mkdir();
        Files.copy(file.getInputStream(), path);
        return path.getFileName().toString();
    }

    public static InputStream getFile(String fileName) throws FileNotFoundException {
        return new FileInputStream(Paths.get(UPLOAD_FOLDER, fileName).toFile());
    }
}
