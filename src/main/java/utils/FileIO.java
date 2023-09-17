package utils;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

//文件读写的IO类
public class FileIO {
    //从指定路径的文件中读取文本内容并返回文本字符串
    public static String readFile(String path) throws IOException{
        StringBuilder builder = new StringBuilder();//用于构建最终的文本内容
        BufferedReader reader = new BufferedReader(new FileReader(path));//读取文件
        //开始逐行读取构建
        String line = reader.readLine();
        while (line != null){
            builder.append(line);
            line = reader.readLine();
        }
        return builder.toString();//转换为字符串返回
    }

    public static  void writeFile(String path, String text) throws IOException{
        //创建文件输出流，文件不存在则新建
        FileOutputStream fileOutputStream = new FileOutputStream(path, true);
        //将文本转换为字节数组,编码格式UTF-8
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        //写入文件
        fileOutputStream.write(bytes);
        //关闭文件流
        fileOutputStream.close();
    }
}
