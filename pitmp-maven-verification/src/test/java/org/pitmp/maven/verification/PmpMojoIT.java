package org.pitmp.maven.verification;

import org.apache.maven.it.VerificationException;
import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.pitest.maven.PmpMojo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

@RunWith(Parameterized.class)
public class PmpMojoIT {

    private static final String PITMP_VERSION = getProperty("pitmp_version");
    private static final String PIT_VERSION = getProperty("pit_version");
    private static final String LOG_FILENAME = "log.out";
    private Verifier verifier;
    @ParameterizedTest
    @ArgumentsSource(PomPathsArgumentsProvider.class)
    public void testDefaultConfiguration(String projectPath, String pomPath, @TempDir Path testFolder) throws Exception {
        prepare(projectPath, pomPath, testFolder);
        // goals
        List<String> goals = new ArrayList<String>();
        goals.add("clean");
        goals.add("install");
        goals.add("pitmp:run");
        goals.add("pitmp:descartes");
        // client options
        List<String> cliOptions = new ArrayList<String>();
        cliOptions.add("-Dpitest-maven-version=" + PIT_VERSION);
        cliOptions.add("-Dpitmp-maven-plugin-version=" + PITMP_VERSION);
        cliOptions.add("-DoutputFormats=XML");
        verifier.setCliOptions(cliOptions);
        verifier.executeGoals(goals);
        verifier.verifyErrorFreeLog();
    }

    private void prepare(String testPath, String pomPath, Path testFolder) throws IOException, VerificationException {
        String path = ResourceExtractor.extractResourcePath(getClass(), testPath, testFolder.toFile(), true)
                .getAbsolutePath();
        verifier = new Verifier(path);
        verifier.setAutoclean(true);
        verifier.setDebug(true);
        verifier.setLogFileName(LOG_FILENAME);

        Files.move(Path.of(path + File.separator + pomPath), Path.of(path + File.separator + "pom.xml"));
        new File(testFolder.toFile().getAbsoluteFile() + testPath);
    }

    private static String getProperty(String propertyName) {
        String path = "/version.prop";
        InputStream stream = PmpMojo.class.getResourceAsStream(path);
        Properties props = new Properties();
        try {
            props.load(stream);
            if (stream != null) {
                stream.close();
            }
            return (String) props.get(propertyName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}