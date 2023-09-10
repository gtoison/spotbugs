package ghIssues;

import java.util.function.Supplier;

import org.mockito.Mockito;

public class Issue2428 {
	final Issue2428 value = Mockito.mock(Issue2428.class);
	
	final Supplier<Issue2428> valueSupplier = new Supplier<Issue2428>() {
		@Override
		public Issue2428 get() {
			return value;
		}
	};
}
