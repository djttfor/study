package com.ex.test;

import com.ex.lucene.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class FileUtilsTest {
    @Test
    public void test1() throws IOException {
        System.out.println(FileUtils.readFileContentToString(new File("F:\\工作\\spring.txt")));
    }
}
