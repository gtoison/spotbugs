package edu.umd.cs.findbugs.detect;

import edu.umd.cs.findbugs.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.JRE;

class Issue3196Test extends AbstractIntegrationTest {

    @Test
    @DisabledOnJre({ JRE.JAVA_8, JRE.JAVA_11, JRE.JAVA_17 })
    void testIssue() {
        performAnalysis(
                "../java21/Issue3196.class",
                "../java21/Issue3196$One.class",
                "../java21/Issue3196$Two.class");

        assertBugTypeCount("BC_VACUOUS_INSTANCEOF", 0);
    }
}
