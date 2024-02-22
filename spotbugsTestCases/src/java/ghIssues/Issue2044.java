package ghIssues;

import edu.umd.cs.findbugs.annotations.NonNull;

public final class Issue2044 {
	@NonNull
	private static final Object INITIALIZED_OBJECT = new Object();

	@NonNull
	private static Object alsoInitialized = INITIALIZED_OBJECT;
}