package resources;

public class ResourceTest {
    private String name;
    private int age;

    public ResourceTest(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public ResourceTest() {
        this.name = "";
        this.age = 0;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}