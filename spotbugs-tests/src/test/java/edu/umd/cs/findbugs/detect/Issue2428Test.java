package edu.umd.cs.findbugs.detect;

import org.junit.Test;

import edu.umd.cs.findbugs.AbstractIntegrationTest;
import edu.umd.cs.findbugs.SystemProperties;
import edu.umd.cs.findbugs.test.matcher.BugInstanceMatcher;
import edu.umd.cs.findbugs.test.matcher.BugInstanceMatcherBuilder;

import static edu.umd.cs.findbugs.test.CountMatcher.containsExactly;
import static org.hamcrest.MatcherAssert.assertThat;

public class Issue2428Test extends AbstractIntegrationTest {
    @Test
    public void test() {
        SystemProperties.setProperty("unreadfields.debug", "true");

        performAnalysis("ghIssues/Issue2428.class",
                "ghIssues/Issue2428$1.class");

        BugInstanceMatcher matcher = new BugInstanceMatcherBuilder()
                .bugType("SIC_INNER_SHOULD_BE_STATIC_ANON").build();

        assertThat(getBugCollection(), containsExactly(0, matcher));
    }
}
