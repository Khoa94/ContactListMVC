package com.swcguild.contactlistmvc.dao;

import com.swcguild.contactlistmvc.model.Contact;
import com.swcguild.contactlistmvc.model.SearchTerm;
import java.util.List;
import java.util.Map;

public interface ContactListDAO {
    public Contact addContact(Contact newContact);
    public void removeContact(int contactId);
    public void updateContact(Contact contact);
    public List<Contact> getAllContacts();
    public Contact getContactById(int contactId);
    public List<Contact> searchContacts(Map<SearchTerm, String> criteria);
}
