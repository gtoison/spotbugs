package edu.umd.cs.findbugs.detect;

import edu.umd.cs.findbugs.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;

public class Issue1707Test extends AbstractIntegrationTest {

    @Test
    void testIssue() {
        performAnalysis("nonAscii/éèùàçこんにちはöñ/ŢəßƗ.class");
    }
}
