package lambda;

import others.collections.Calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class LambdaCalculas {
    public static void main(String[] args) {
        List<Contact> contacts = new ArrayList<>();
        contacts.add( new ContactBuilder()
                .setAge(26)
                .setName("Tanvir")
                .setSex(Contact.Sex.MALE)
                .build());
        contacts.add(new ContactBuilder()
                .setAge(22)
                .setName("Jara")
                .setSex(Contact.Sex.FEMALE)
                .build());
        contacts.add(new ContactBuilder()
                .setAge(25)
                .setName("Afreen")
                .setSex(Contact.Sex.FEMALE)
                .build());
        ContactService service = new ContactService();
        List<Contact> ans =service.getContacts(contacts,contact->{
            return contact.getAge()>=18 && contact.getAge()<=25 && contact.getSex()== Contact.Sex.FEMALE;
        });
        Calculator.serialozeToDisk("serialize.txt",contacts);
        List<Contact> xy = Calculator.deserialozeFromDisk("serialize.txt");
        ans.forEach(System.out::println);
        Calculator calculator = new Calculator();
        Function g = x -> calculator.fun1((Double) x);
        Function h = y -> calculator.fun2((Double) y);
        Function hog = h.compose(g);
        System.out.println(hog.apply(1.0));
    }
}
