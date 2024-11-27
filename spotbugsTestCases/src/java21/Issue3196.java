
public class Issue3196 {
    public record One(String value) {
        // Nothing else
    }

    public record Two(One one) {
        // Nothing else
    }

    public String deconstruct(final Object obj) {
        if (obj instanceof Two(One x)) {
            return x.toString();
        }

        return null;
    }

    public String patternMatchingCast(final Object obj) {
        if (obj instanceof Two two && two.one() != null) {
            return two.one().value();
        }

        return null;
    }

    public String standardCast(final Object obj) {
        if (obj instanceof Two) {
            Two two = (Two) obj;
            if (two.one() != null) {
                return two.one().value();
            }
        }

        return null;
    }

    public String vacuousInstanceOfTruePosition(final Object obj) {
        if (obj instanceof Two) {
            Two two = (Two) obj;
            One one = two.one();
            if (one instanceof One) {
                return one.value();
            }
        }

        return null;
    }

    public String extract(final Object obj) {
        return switch (obj) {
            case One(var value) -> value;
            case Two(One(var value)) -> value;
            case null, default -> null;
        };
    }
}
