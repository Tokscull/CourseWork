package client.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileWork {
    public static String[] getIpAndPortFromFile(String filename){
        String[] info = new String[2];
        File file = new File(filename);
        if(file.exists()) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()));
                String[] line = content.split("\r\n", 2);
                info[0] = line[0];
                info[1] = line[1];
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        else{
            info[0] = "empty";
        }
        return info;
    }
}
