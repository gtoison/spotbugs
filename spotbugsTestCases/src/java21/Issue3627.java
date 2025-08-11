import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.XMLStreamConstants;

public class Issue3617 {

	public int process(Object event) {
		switch (event) {
		case XMLEvent xmlEvent when xmlEvent.getEventType() == XMLStreamConstants.START_ELEMENT -> {
			return 1;
		}
		case XMLEvent xmlEvent when xmlEvent.getEventType() == XMLStreamConstants.SPACE -> {
			// do nothing - skip whitespace
		}
		default -> {
			return 2;
		}
		}

		return 3;
	}
}
