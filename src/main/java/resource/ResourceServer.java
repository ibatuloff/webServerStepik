package resource;

public interface ResourceServer {
    String getName();
    int getAge();
    String getNameClass();
    void readResource(String path);
}