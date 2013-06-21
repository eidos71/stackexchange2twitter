package org.common.classification;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.mahout.math.Vector;
import org.apache.mahout.utils.vectors.io.VectorWriter;
import org.junit.Test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class ClassificationUnitTest {
    private static final String FILE_ON_DISK = "file:/tmp/spammery.seq";

    private static final String COMMERCIAL = "commercial";
    private static final String NONCOMMERCIAL = "noncommercial";

    // tests

    @Test
    public final void whenTextIsEncodedAsVector1_thenNoExceptions() {
        ClassificationUtil.encodeIncomplete(randomAlphabetic(4) + " " + randomAlphabetic(5));
    }

    @Test
    public final void whenTextIsEncodedAsVector2_thenNoExceptions() throws IOException {
        ClassificationUtil.encode(randomAlphabetic(4), Lists.newArrayList(randomAlphabetic(5), randomAlphabetic(4)));
    }

    @Test
    public final void whenLoadingClassificationData_thenNoExceptions() throws IOException {
        ClassificationUtil.loadData(FILE_ON_DISK);
    }

    @Test
    public final void whenLoadingClassificationData_thenOperationCorrect() throws IOException {
        assertNotNull(ClassificationUtil.loadData(FILE_ON_DISK));
    }

    @Test
    public final void givenDataIsLoaded_whenWriterIsUsed_thenNoExceptions() throws IOException {
        final VectorWriter vectorWriter = ClassificationUtil.loadData(FILE_ON_DISK);
        final Vector noncommercial1 = ClassificationUtil.encode(NONCOMMERCIAL, Splitter.on(CharMatcher.anyOf(" ")).split("How to travel around the world for a year http://blog.alexmaccaw.com/how-to-travel-around-the-world-for-a-year/"));
        final Vector noncommercial2 = ClassificationUtil.encode(NONCOMMERCIAL, Splitter.on(CharMatcher.anyOf(" ")).split("What is nux and what's it used for? - http://askubuntu.com/questions/18413/what-is-nux-and-whats-it-used-for"));
        final Vector commercial1 = ClassificationUtil.encode(COMMERCIAL, Splitter.on(CharMatcher.anyOf(" ")).split("We're looking to #hire a Front End Developer/Creative Designer to join our team in Leeds. Get in touch for more information."));
        final Vector commercial2 = ClassificationUtil.encode(COMMERCIAL, Splitter.on(CharMatcher.anyOf(" ")).split("New job Nurse Practitioners & Physician Assistants - New Jersey http://goo.gl/fb/K8jZP  #hire #jobs"));

        vectorWriter.write(Lists.newArrayList(noncommercial1, commercial1, noncommercial2, commercial2));
        vectorWriter.close();
    }

    @Test
    public final void givenDataWasWritten_whenDataIsReadBack_thenNoExceptions() throws IOException {
        ClassificationUtil.readBackData(FILE_ON_DISK);
    }

}
