package edu.umd.cs.findbugs.detect;

import edu.umd.cs.findbugs.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

class Issue2985Test extends AbstractIntegrationTest {

    @Test
    void testIssue() {
        performAnalysis("ghIssues/Issue2985.class");

        assertBugTypeCount("SING_SINGLETON_IMPLEMENTS_SERIALIZABLE", 0);
    }
}
