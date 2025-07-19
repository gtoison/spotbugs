package ghIssues;

import java.io.Serializable;

public class Issue2985 implements Serializable {
	private static final Issue2985 DEFAULT_INSTANCE;
	static {
		DEFAULT_INSTANCE = new Issue2985();
	}
	
	public static Issue2985 getDefaultInstance() {
		return DEFAULT_INSTANCE;
	}
	
	private Issue2985() {
	}

	// This method should be recognized as (non private) factory method, this is therefore not a singleton
	protected Object newInstance() {
		return new Issue2985();
	}
}
