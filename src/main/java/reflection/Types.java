package reflection;

public enum Types {
    BYTE,
    BOOLEAN,
    SHORT,
    CHAR,
    INT,
    FLOAT,
    LONG,
    DOUBLE,
    STRING;

    public static Types getType(Class<?> Class) {
        String className = Class.getSimpleName().toUpperCase();
        return Types.valueOf(className);
    }
}