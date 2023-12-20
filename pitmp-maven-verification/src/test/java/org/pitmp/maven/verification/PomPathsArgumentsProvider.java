package org.pitmp.maven.verification;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class PomPathsArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of("/dhell", "pom.xml.pitmp.conf1.xml"),
                Arguments.of("/dhell", "pom.xml.pitmp.noconf.xml"),

                Arguments.of("/dhell5", "pom.xml.pitmp.conf1.xml"),
                Arguments.of("/dhell5", "pom.xml.pitmp.noconf.xml"),

                Arguments.of("/dnoo", "pom.xml.pitmp.conf1.xml"),
                Arguments.of("/dnoo", "pom.xml.pitmp.noconf.xml"),

                Arguments.of("/dnoo5", "pom.xml.pitmp.conf1.xml"),
                Arguments.of("/dnoo5", "pom.xml.pitmp.noconf.xml")
        );

    }
}
