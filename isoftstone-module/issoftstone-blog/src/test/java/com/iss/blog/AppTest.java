package com.iss.blog;

import junit.framework.TestCase;

import java.io.*;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

    public static void main(String[] args) {
        File file = new File("D:\\blog-text\\jvm优化必知系列——监控工具.txt");
        System.out.println("文件路径===>>>" + file.getAbsolutePath());
        StringBuffer buffer = new StringBuffer();
        try {
            FileInputStream stream = new FileInputStream(file);
            if (stream != null) {
                InputStreamReader inputreader = new InputStreamReader(stream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                //分行读取
                while ((line = buffreader.readLine()) != null) {
                    buffer.append(line);
                }
                stream.close();
                String content = buffer.toString().replaceAll("<div class=\"ad-wrap\"(([\\s\\S])*?)<\\/div>", "");
                System.out.println(content);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
