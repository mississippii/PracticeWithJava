package lambda;

import java.util.ArrayList;
import java.util.List;

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
        ans.forEach(System.out::println);
    }
}
