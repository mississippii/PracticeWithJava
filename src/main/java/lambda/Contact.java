package lambda;

import java.io.Serializable;

public class Contact implements Serializable {
    enum Sex{
        MALE,FEMALE
    }
    private String name;
    private int age;
    private Sex sex;
    public Contact(ContactBuilder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.sex = builder.sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
