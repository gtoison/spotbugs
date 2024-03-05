package ghIssues;

import java.util.function.Predicate;

public enum Issue740 {
	ONE("test"::equals),
	TWO(s -> "test".equals(s)),
	THREE(Issue740::test);

	private final Predicate<String> predicate;
	
	private Issue740(Predicate<String> predicate) {
		this.predicate = predicate;
	}
	
	public static boolean test(String s) {
		return "test".equals(s);
	}
}
