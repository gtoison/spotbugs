package edu.umd.cs.findbugs.detect;

import static edu.umd.cs.findbugs.test.CountMatcher.containsExactly;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import edu.umd.cs.findbugs.AbstractIntegrationTest;
import edu.umd.cs.findbugs.test.matcher.BugInstanceMatcher;
import edu.umd.cs.findbugs.test.matcher.BugInstanceMatcherBuilder;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.JRE;

class MultipleInstantiationsOfSingletonsTest extends AbstractIntegrationTest {
    @Test
    void abstractClassTest() {
        performAnalysis("singletons/AbstractClass.class",
                "singletons/AbstractClass$Nested.class");
        assertNoBugs();
    }

    @Test
    void innerChildInstanceTest() {
        performAnalysis("singletons/InnerChildInstance.class",
                "singletons/InnerChildInstance$Unknown.class",
                "singletons/InnerChildInstance$1.class");
        assertNoBugs();
    }

    @Test
    void innerChildAndMoreInstanceTest() {
        performAnalysis("singletons/InnerChildAndMoreInstance.class",
                "singletons/InnerChildAndMoreInstance$1.class",
                "singletons/InnerChildAndMoreInstance$Unknown.class");
        assertNumOfSINGBugs("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", 1);
        assertSINGBug("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", "InnerChildAndMoreInstance");

        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_INDIRECTLY_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONE_METHOD", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_SERIALIZABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_GETTER_NOT_SYNCHRONIZED", 0);
    }

    @Test
    void InnerChildAndMoreInstanceReorderedTest() {
        performAnalysis("singletons/InnerChildAndMoreInstanceReordered.class",
                "singletons/InnerChildAndMoreInstanceReordered$1.class",
                "singletons/InnerChildAndMoreInstanceReordered$Unknown.class");
        assertNumOfSINGBugs("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", 1);
        assertSINGBug("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", "InnerChildAndMoreInstanceReordered");

        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_INDIRECTLY_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONE_METHOD", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_SERIALIZABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_GETTER_NOT_SYNCHRONIZED", 0);
    }

    @Test
    void InnerChildAndMoreInstanceNoGetterTest() {
        performAnalysis("singletons/InnerChildAndMoreInstanceNoGetter.class",
                "singletons/InnerChildAndMoreInstanceNoGetter$1.class",
                "singletons/InnerChildAndMoreInstanceNoGetter$Unknown.class");
        assertNoBugs();
    }

    @Test
    void notSingletonWithFactoryMethodTest() {
        performAnalysis("singletons/NotSingletonWithFactoryMethod.class");
        assertNoBugs();
    }

    @Test
    void notLazyInitSingletonTest() {
        performAnalysis("singletons/NotLazyInitSingleton.class");
        assertNoBugs();
    }

    @Test
    void cloneableSingletonTest() {
        performAnalysis("singletons/CloneableSingleton.class");
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONEABLE", 1);
        assertSINGBug("SING_SINGLETON_IMPLEMENTS_CLONEABLE", "CloneableSingleton", "clone");

        assertNumOfSINGBugs("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", 0);
        assertNumOfSINGBugs("SING_SINGLETON_INDIRECTLY_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONE_METHOD", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_SERIALIZABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_GETTER_NOT_SYNCHRONIZED", 0);
    }

    @Test
    void indirectlyCloneableSingletonTest() {
        performAnalysis("singletons/IndirectlyCloneableSingleton.class", "singletons/CloneableClass.class");
        assertNumOfSINGBugs("SING_SINGLETON_INDIRECTLY_IMPLEMENTS_CLONEABLE", 1);
        assertSINGBug("SING_SINGLETON_INDIRECTLY_IMPLEMENTS_CLONEABLE", "IndirectlyCloneableSingleton");

        assertNumOfSINGBugs("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONE_METHOD", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_SERIALIZABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_GETTER_NOT_SYNCHRONIZED", 0);
    }

    @Test
    void defaultConstructorTest() {
        performAnalysis("singletons/DefaultConstructor.class");
        assertNumOfSINGBugs("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", 1);
        assertSINGBug("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", "DefaultConstructor");

        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_INDIRECTLY_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONE_METHOD", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_SERIALIZABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_GETTER_NOT_SYNCHRONIZED", 0);
    }

    @Test
    void protectedConstructorTest() {
        performAnalysis("singletons/ProtectedConstructor.class");
        assertNumOfSINGBugs("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", 1);
        assertSINGBug("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", "ProtectedConstructor");

        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_INDIRECTLY_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONE_METHOD", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_SERIALIZABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_GETTER_NOT_SYNCHRONIZED", 0);
    }

    @Test
    void protectedConstructorStaticInitTest() {
        performAnalysis("singletons/ProtectedConstructorStaticInit.class");
        assertNumOfSINGBugs("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", 1);
        assertSINGBug("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", "ProtectedConstructorStaticInit");

        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_INDIRECTLY_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONE_METHOD", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_SERIALIZABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_GETTER_NOT_SYNCHRONIZED", 0);
    }

    @Test
    void protectedConstructorStaticInitReorderedTest() {
        performAnalysis("singletons/ProtectedConstructorStaticInitReordered.class");
        assertNumOfSINGBugs("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", 1);
        assertSINGBug("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", "ProtectedConstructorStaticInitReordered");

        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_INDIRECTLY_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONE_METHOD", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_SERIALIZABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_GETTER_NOT_SYNCHRONIZED", 0);
    }

    @Test
    void notCloneableButHasCloneMethodTest() {
        performAnalysis("singletons/NotCloneableButHasCloneMethod.class");
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONE_METHOD", 1);
        assertSINGBug("SING_SINGLETON_IMPLEMENTS_CLONE_METHOD", "NotCloneableButHasCloneMethod", "clone");

        assertNumOfSINGBugs("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_INDIRECTLY_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_SERIALIZABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_GETTER_NOT_SYNCHRONIZED", 0);
    }

    @Test
    void serializableSingletonTest() {
        performAnalysis("singletons/SerializableSingleton.class");
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_SERIALIZABLE", 1);
        assertSINGBug("SING_SINGLETON_IMPLEMENTS_SERIALIZABLE", "SerializableSingleton");

        assertNumOfSINGBugs("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_INDIRECTLY_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONE_METHOD", 0);
        assertNumOfSINGBugs("SING_SINGLETON_GETTER_NOT_SYNCHRONIZED", 0);
    }

    @Test
    void notSynchronizedSingletonTest() {
        performAnalysis("singletons/NotSynchronizedSingleton.class");
        assertNumOfSINGBugs("SING_SINGLETON_GETTER_NOT_SYNCHRONIZED", 1);
        assertSINGBug("SING_SINGLETON_GETTER_NOT_SYNCHRONIZED", "NotSynchronizedSingleton", "getInstance");

        assertNumOfSINGBugs("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_INDIRECTLY_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONE_METHOD", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_SERIALIZABLE", 0);
    }

    @Test
    void inappropriateSynchronization() {
        performAnalysis("singletons/InappropriateSynchronization.class");
        // now cannot detect synchronization bug
        // because of the using of a monitor inside function
        assertNumOfSINGBugs("SING_SINGLETON_GETTER_NOT_SYNCHRONIZED", 0);
        //assertSINGBug("SING_SINGLETON_GETTER_NOT_SYNCHRONIZED", "InappropriateSynchronization", "getInstance");

        assertNumOfSINGBugs("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_INDIRECTLY_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONE_METHOD", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_SERIALIZABLE", 0);
    }

    @Test
    void appropriateSingletonTest() {
        performAnalysis("singletons/AppropriateSingleton.class");
        assertNoBugs();
    }

    @Test
    void initializeOnDemandHolderIdiomTest() {
        performAnalysis("singletons/InitializeOnDemandHolderIdiom.class",
                "singletons/InitializeOnDemandHolderIdiom$SingletonHolder.class");
        assertNoBugs();
    }

    @Test
    void enumSingletonTest() {
        performAnalysis("singletons/EnumSingleton.class");
        assertNoBugs();
    }

    @Test
    void oneEnumSingletonTest() {
        performAnalysis("singletons/OneEnumSingleton.class");
        assertNoBugs();
    }

    @Test
    void unconditionalThrowerCloneableTest() {
        performAnalysis("singletons/UnconditionalThrowerCloneable.class");
        assertNoBugs();
    }

    @Test
    void doubleCheckedLockingTest() {
        performAnalysis("singletons/DoubleCheckedLocking.class");
        assertNoBugs();
    }

    @Test
    void doubleCheckedLockingIndirectTest() {
        performAnalysis("singletons/DoubleCheckedLockingIndirect.class");
        assertNoBugs();
    }

    @Test
    void doubleCheckedLockingIndirect2Test() {
        performAnalysis("singletons/DoubleCheckedLockingIndirect2.class");
        assertNoBugs();
    }

    @Test
    void multiplePrivateConstructorsTest() {
        performAnalysis("singletons/MultiplePrivateConstructors.class");
        assertNoBugs();
    }

    @Test
    void multipleNonPrivateConstructorsTest() {
        performAnalysis("singletons/MultipleNonPrivateConstructors.class");
        assertNumOfSINGBugs("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", 1);
        assertSINGBug("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", "MultipleNonPrivateConstructors");

        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_INDIRECTLY_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONE_METHOD", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_SERIALIZABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_GETTER_NOT_SYNCHRONIZED", 0);
    }

    @Test
    void multipleNonPrivateConstructorsTest2() {
        performAnalysis("singletons/MultipleNonPrivateConstructors2.class");
        assertNumOfSINGBugs("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", 1);
        assertSINGBug("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", "MultipleNonPrivateConstructors2");

        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_INDIRECTLY_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONE_METHOD", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_SERIALIZABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_GETTER_NOT_SYNCHRONIZED", 0);
    }

    @Test
    @DisabledOnJre({ JRE.JAVA_8, JRE.JAVA_11 })
    void recordNotSingletonTest() {
        performAnalysis("../java17/Issue2981.class");
        assertNoBugs();
    }

    private void assertNoBugs() {
        assertNumOfSINGBugs("SING_SINGLETON_HAS_NONPRIVATE_CONSTRUCTOR", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_INDIRECTLY_IMPLEMENTS_CLONEABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_CLONE_METHOD", 0);
        assertNumOfSINGBugs("SING_SINGLETON_IMPLEMENTS_SERIALIZABLE", 0);
        assertNumOfSINGBugs("SING_SINGLETON_GETTER_NOT_SYNCHRONIZED", 0);
    }

    private void assertNumOfSINGBugs(String bugType, int num) {
        final BugInstanceMatcher bugTypeMatcher = new BugInstanceMatcherBuilder()
                .bugType(bugType)
                .build();
        assertThat(getBugCollection(), containsExactly(num, bugTypeMatcher));
    }

    private void assertSINGBug(String bugType, String className) {
        final BugInstanceMatcher bugInstanceMatcher = new BugInstanceMatcherBuilder()
                .bugType(bugType)
                .inClass(className)
                .build();
        assertThat(getBugCollection(), hasItem(bugInstanceMatcher));
    }

    private void assertSINGBug(String bugType, String className, String method) {
        final BugInstanceMatcher bugInstanceMatcher = new BugInstanceMatcherBuilder()
                .bugType(bugType)
                .inClass(className)
                .inMethod(method)
                .build();
        assertThat(getBugCollection(), hasItem(bugInstanceMatcher));
    }
}
