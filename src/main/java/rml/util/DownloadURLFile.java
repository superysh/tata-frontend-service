package rml.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;
import java.util.UUID;

/**
 * Created by edward-echo on 2016/4/8.
 */
public class DownloadURLFile {
    /**
     * @param args
     */
    public static void main(String[] args) {

        File file = downloadFromUrl("http://file.weiqu168.com/group1/M00/00/64/i8QzQVcDFAmANwqiAAG0Ar44OLA65..jpg","d:/");
        String dest_file = "D:/dest_pic/"+ UUID.randomUUID().toString().substring(0,9)+file.getName();
        try {
            Compresssion.transferImg1(file.getPath(), dest_file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        System.out.println(dest_file);
    }


    public static File downloadFromUrl(String url,String dir) {
        try {
            URL httpurl = new URL(url);
            String fileName = getFileNameFromUrl(url);
            System.out.println(fileName);
            File f = new File(dir + fileName);
            FileUtils.copyURLToFile(httpurl, f);
            return  f;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static String getFileNameFromUrl(String url){
        String name = new Long(System.currentTimeMillis()).toString() + "..X";
        int index = url.lastIndexOf("/");
        if(index > 0){
            name = url.substring(index + 1);
            if(name.trim().length()>0){
                return name;
            }
        }
        return name;
    }
}

