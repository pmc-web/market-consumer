package com.pmc.market.service;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class GCSServiceTest {

    @Test
    void openFile() throws FileNotFoundException {
        String file = "download/rm363-b07-google-mockup.jpg";

        File f = new File(file);
        System.out.println(f.getAbsolutePath());
//        System.out.println(new File("download").getAbsoluteFile());
        FileInputStream fileInputStream = new FileInputStream(f);

        System.out.println(fileInputStream instanceof InputStream);

    }

    @Test
    void download() throws IOException {
        Path path = Paths.get("download/rm363-b07-google-mockup.jpg");
        OutputStream outputStream = Files.newOutputStream(path);
    }
}