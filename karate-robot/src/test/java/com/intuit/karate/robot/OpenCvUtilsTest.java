package com.intuit.karate.robot;

import java.io.File;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pthomas3
 */
class OpenCvUtilsTest {

    private static final Logger logger = LoggerFactory.getLogger(OpenCvUtilsTest.class);

    // @Test // TODO jdk 17 apple silicon
    void testOpenCv() {
        // System.setProperty("org.bytedeco.javacpp.logger.debug", "true");
        File target = new File("src/test/java/search.png");
        File source = new File("src/test/java/desktop01.png");
        Region region = OpenCvUtils.find(1, null, OpenCvUtils.read(source), OpenCvUtils.read(target), false);
        assertEquals(1605, region.x);
        assertEquals(1, region.y);
        target = new File("src/test/java/search-1_5.png");
        region = OpenCvUtils.find(10, null, OpenCvUtils.read(source), OpenCvUtils.read(target), true);
        assertEquals(1604, region.x);
        assertEquals(0, region.y);
        region = OpenCvUtils.find(2, null, OpenCvUtils.read(source), OpenCvUtils.read(target), true);
        assertEquals(1605, region.x);
        assertEquals(1, region.y);        
    }

}
