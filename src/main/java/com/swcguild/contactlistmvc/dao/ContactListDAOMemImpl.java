package com.swcguild.contactlistmvc.dao;

import com.swcguild.contactlistmvc.model.Contact;
import com.swcguild.contactlistmvc.model.SearchTerm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ContactListDAOMemImpl implements ContactListDAO {
    
    private Map<Integer, Contact>  contactMap = new HashMap<>();
    private static int contactIdCounter = 0;
    
    @Override
    public Contact addContact(Contact newContact) {
        newContact.setContactId(contactIdCounter++);
        contactMap.put(newContact.getContactId(), newContact);
        return newContact;
    }

    @Override
    public void removeContact(int contactId) {
        contactMap.remove(contactId);
    }

    @Override
    public void updateContact(Contact contact) {
        contactMap.put(contact.getContactId(), contact);
    }

    @Override
    public List<Contact> getAllContacts() {
        List<Contact> c = contactMap.values().stream().collect(Collectors.toList());
        return new ArrayList(c);
    }

    @Override
    public Contact getContactById(int contactId) {
        return contactMap.get(contactId);
    }

    @Override
    public List<Contact> searchContacts(Map<SearchTerm, String> criteria) {
        String firstNameCriteria = criteria.get(SearchTerm.FIRST_NAME);
        String lastNameCriteria = criteria.get(SearchTerm.LAST_NAME);
        String companyCriteria = criteria.get(SearchTerm.COMPANY);
        String phoneCriteria = criteria.get(SearchTerm.PHONE);
        String emailCriteria = criteria.get(SearchTerm.EMAIL);
        //building a predicate
        Predicate<Contact> firstNameMatches;
        Predicate<Contact> lastNameMatches;
        Predicate<Contact> companyMatches;
        Predicate<Contact> phoneMatches;
        Predicate<Contact> emailMatches;
        
        Predicate<Contact> truePredicate = (c) -> {return true;};

        firstNameMatches = (firstNameCriteria == null || firstNameCriteria.isEmpty())
                ? truePredicate : (c) -> c.getFirstName().equalsIgnoreCase(firstNameCriteria);
        
        lastNameMatches = (lastNameCriteria == null || lastNameCriteria.isEmpty())
                ? truePredicate : (c) -> c.getLastName().equalsIgnoreCase(lastNameCriteria);
        
        companyMatches = (companyCriteria == null || companyCriteria.isEmpty())
                ? truePredicate : (c) -> c.getCompany().equalsIgnoreCase(companyCriteria);
        
        phoneMatches = (phoneCriteria == null || phoneCriteria.isEmpty())
                ? truePredicate : (c) -> c.getPhone().equalsIgnoreCase(phoneCriteria);
        
        emailMatches = (emailCriteria == null || emailCriteria.isEmpty())
                ? truePredicate : (c) -> c.getEmail().equalsIgnoreCase(emailCriteria);
        
        return contactMap.values().stream()
            .filter(firstNameMatches
               .and(lastNameMatches)
               .and(companyMatches)
               .and(phoneMatches)
               .and(emailMatches))
            .collect(Collectors.toList());
    }
    
}
