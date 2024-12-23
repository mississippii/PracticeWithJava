package lambda;

import java.util.ArrayList;
import java.util.List;

public class ContactService {
    public List<Contact> getContacts(List<Contact> contacts,FilterCriteria criteria) {
        List<Contact> filteredContacts = new ArrayList<>();
        for (Contact contact : contacts) {
            if(criteria.matches(contact)) {
                filteredContacts.add(contact);
            }
        }
        return filteredContacts;
    }
}
