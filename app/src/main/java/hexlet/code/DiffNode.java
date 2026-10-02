package hexlet.code;

/** Описание одного ключа во внутреннем представлении дифа. */
public record DiffNode(String key, Status status, Object oldValue, Object newValue) {

    public enum Status {
        ADDED,
        REMOVED,
        UNCHANGED,
        CHANGED
    }
}
