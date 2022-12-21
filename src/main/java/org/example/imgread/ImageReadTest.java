package org.example.imgread;

import cn.hutool.core.io.resource.ClassPathResource;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

/**
 * 图片识别
 *
 * @author yuct
 */
public class ImageReadTest {
    public static void main(String[] args) throws ImageProcessingException, IOException {
        URL resource = ImageReadTest.class.getClassLoader().getResource("img/1.jpg");
        if (resource == null) {
            System.out.println("图片不存在");
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(resource.getFile());
        Metadata metadata = ImageMetadataReader.readMetadata(fileInputStream);
        metadata.getDirectories().forEach(directory -> {
            directory.getTags().forEach(tag -> {
                System.out.println(tag.getTagName() + " : " + tag.getDescription());
            });
        });
    }
}
