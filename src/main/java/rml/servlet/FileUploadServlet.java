package rml.servlet;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import rml.util.ImgFile;
import rml.util.MD5;
import rml.util.ReturnJson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@Component
public class FileUploadServlet extends HttpServlet
{

  private static final Logger logger = LoggerFactory.getLogger(FileUploadServlet.class);


  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    try
    {
        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
        String randomKey = "";
        String secretKey = "";
        for (FileItem item : items)
            if (item.isFormField())
            {
                String fieldName = item.getFieldName();
                if (fieldName.equals("randomKey")) {
                    randomKey = item.getString();
                }
                if (fieldName.equals("secretKey")) {
                    secretKey = item.getString();
                }
            }
      List<HashMap<String,String>> fileList = new ArrayList<HashMap<String,String>>();

      for (FileItem item : items){
          String fieldName = item.getFieldName();
          String fileName = FilenameUtils.getName(item.getName());
          String ext = fileName.substring(fileName.lastIndexOf("."), fileName.length());

          InputStream fileContent = item.getInputStream();

          File tmpDir = new File(System.getProperty("java.io.tmpdir") + "/tmp0" + ext);
          ClientGlobal.init("/fdfs_client.conf");
          TrackerClient tracker = new TrackerClient();
          TrackerServer trackerServer = tracker.getConnection();
          StorageServer storageServer = null;
          StorageClient1 client = new StorageClient1(trackerServer, storageServer);
          NameValuePair[] metaList = new NameValuePair[3];
          inputstreamtofile(fileContent, tmpDir);
          metaList[0] = new NameValuePair("fileName", tmpDir.getName());
          metaList[1] = new NameValuePair("fileExtName", ext);
          metaList[2] = new NameValuePair("fileLength", String.valueOf(tmpDir.length()));
          String uuid = UUID.randomUUID().toString();

          String[] result = client.upload_file(File2byte(tmpDir), ext, metaList);
          System.err.print(tmpDir.length());
          PrintWriter out = response.getWriter();
          ReturnJson value = new ReturnJson();
          value.setErrorCode(46000);
          if(fileList.size()==0){
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("pic","http://tp.tata168.com/" + result[0] + "/" + result[1]);
            fileList.add(map);
          }
          value.setReturnObject(fileList);
          value.setReturnMessage("调用成功");
          value.setServerStatus(0);
          value.setReturnValue("http://tp.tata168.com/" + result[0] + "/" + result[1]);
          logger.info("http://tp.tata168.com/" + result[0] + "/" + result[1]);
          out.append(JSONObject.toJSONString(value));
       return;
        }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }


    public static String fileUpload(File tmpDir)
            throws ServletException, IOException
    {
        try
        {
            String fileName = tmpDir.getName();
            String ext = fileName.substring(fileName.lastIndexOf("."), fileName.length());
             List<HashMap<String,String>> fileList = new ArrayList<HashMap<String,String>>();
            ClientGlobal.init("/fdfs_client.conf");
            TrackerClient tracker = new TrackerClient();
                TrackerServer trackerServer = tracker.getConnection();
                StorageServer storageServer = null;
                StorageClient1 client = new StorageClient1(trackerServer, storageServer);
                NameValuePair[] metaList = new NameValuePair[3];
                metaList[0] = new NameValuePair("fileName", tmpDir.getName());
                metaList[1] = new NameValuePair("fileExtName", ext);
                metaList[2] = new NameValuePair("fileLength", String.valueOf(tmpDir.length()));
                String uuid = UUID.randomUUID().toString();

                String[] result = client.upload_file(File2byte(tmpDir), ext, metaList);
                ReturnJson value = new ReturnJson();
                value.setErrorCode(46000);
                if(fileList.size()==0){
                    HashMap<String,String> map = new HashMap<String, String>();
                    map.put("pic","http://tp.tata168.com/" + result[0] + "/" + result[1]);
                    fileList.add(map);
                }
                value.setReturnObject(fileList);
                value.setReturnMessage("调用成功");
                value.setServerStatus(0);
                value.setReturnValue("http://tp.tata168.com/" + result[0] + "/" + result[1]);
                logger.info("http://tp.tata168.com/" + result[0] + "/" + result[1]);
                return "http://tp.tata168.com/" + result[0] + "/" + result[1];
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }



    public Long getFileSizes(File f) throws Exception{//取得文件大小
    long s=0;
    if (f.exists()) {
      FileInputStream fis = null;
      fis = new FileInputStream(f);
      s= fis.available();
    } else {
      f.createNewFile();
      System.out.println("文件不存在");
    }
    return s;
  }
  public static void inputstreamtofile(InputStream ins, File file) throws Exception {
    OutputStream os = new FileOutputStream(file);
    int bytesRead = 0;
    byte[] buffer = new byte[8192];
    while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
      os.write(buffer, 0, bytesRead);
    }
    os.close();
    ins.close();
  }
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    doGet(request, response);
  }


  public static byte[] File2byte(File file)
  {
    byte[] buffer = null;
    try
    {
      FileInputStream fis = new FileInputStream(file);
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      byte[] b = new byte[1024];
      int n;
      while ((n = fis.read(b)) != -1)
      {
        bos.write(b, 0, n);
      }
      fis.close();
      bos.close();
      buffer = bos.toByteArray();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return buffer;
  }

  public static void main(String[]args){
//        logger.info("before word pdf:" + new Date().getTime());
//        pdfToImg("C:\\365test.pdf");
//        logger.info("after word img:" + new Date().getTime());
    System.err.print(1452429143943L-1452429133226L);
  }
}