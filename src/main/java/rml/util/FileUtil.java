package rml.util;

import java.io.File;
import java.util.UUID;

/**
 * Created by edward-echo on 2016/4/8.
 */
public class FileUtil {

    public static String transferImg7(String source,String dest){
        File file = DownloadURLFile.downloadFromUrl(source,dest);
        String dest_file = System.getProperty("java.io.tmpdir")+ UUID.randomUUID().toString().substring(0,9)+file.getName();
        try {
            Compresssion.transferImg7(file.getPath(), dest_file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return dest_file;
    }


    public static String transferImg1(String source,String dest){
        File file = DownloadURLFile.downloadFromUrl(source,dest);
        String dest_file = System.getProperty("java.io.tmpdir")+ UUID.randomUUID().toString().substring(0,9)+file.getName();
        try {
            Compresssion.transferImg1(file.getPath(), dest_file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return dest_file;
    }
}
