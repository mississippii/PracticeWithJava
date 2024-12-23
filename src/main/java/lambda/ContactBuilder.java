package lambda;

public class ContactBuilder {
    String name;
    int age;
    Contact.Sex sex;
    public ContactBuilder setName(String name) {
        this.name = name;
        return this;
    }
    public ContactBuilder setAge(int age) {
        this.age = age;
        return this;
    }
    public ContactBuilder setSex(Contact.Sex sex) {
        this.sex = sex;
        return this;
    }

    public Contact build() {
        return new Contact(this);
    }
}
