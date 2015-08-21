package com.swcguild.contactlistmvc.dao;

import com.swcguild.contactlistmvc.model.Contact;
import com.swcguild.contactlistmvc.model.SearchTerm;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class ContactListDaoDbImpl implements ContactListDAO {
    
    private static final String SQL_INSERT_CONTACT = "INSERT INTO contacts (first_name, last_name, company, phone, email) "
                                                   + "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_CONTACT = "DELETE FROM contacts WHERE contact_id = ?"; //the '?' stands for params to be passed in
    private static final String SQL_UPDATE_CONTACT = "UPDATE contacts SET first_name=?, last_name=?, company=?, phone=?, email=?"
                                                   + "WHERE contact_id=?";
    private static final String SQL_SELECT_ALL_CONTACTS = "SELECT * FROM contacts"; //read from database
    private static final String SQL_SELECT_CONTACT = "SELECT * FROM contacts WHERE contact_id=?";
    
    private JdbcTemplate jdbcTemplate; //import from Spring
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) { //use Spring to do setter injection
        this.jdbcTemplate = jdbcTemplate;
    }
        
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false) //this tells Spring to tell the JDBC to tell SQL that this whole
                                                                        //transaction needs to take place inside of one transaction
    public Contact addContact(Contact contact) {
        jdbcTemplate.update(SQL_INSERT_CONTACT, contact.getFirstName(),
                                                contact.getLastName(),
                                                contact.getCompany(),
                                                contact.getPhone(),
                                                contact.getEmail());
        //this all has to happen inside of one transaction because we need to be able to select the last ID that we inserted.
        contact.setContactId(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
        return contact;
    }

    @Override
    public void removeContact(int contactId) {
        jdbcTemplate.update(SQL_DELETE_CONTACT, contactId); //use .update to change data
    }

    @Override
    public void updateContact(Contact contact) {
        jdbcTemplate.update(SQL_UPDATE_CONTACT, 
                contact.getFirstName(),
                contact.getLastName(),
                contact.getCompany(),
                contact.getPhone(),
                contact.getEmail(),
                contact.getContactId());               
    }

    @Override
    public List<Contact> getAllContacts() { //take result set from query and convert to Contact objects
        return jdbcTemplate.query(SQL_SELECT_ALL_CONTACTS, new ContactMapper()); //use .query to get data, then map data to Contacts with ContactMapper
        //.query method functions as a for each loop
    }

    @Override
    public Contact getContactById(int contactId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_CONTACT, new ContactMapper(), contactId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Contact> searchContacts(Map<SearchTerm, String> criteria) {
        if(criteria == null || criteria.size() == 0) return getAllContacts();
        StringBuilder query = new StringBuilder("SELECT * FROM contacts WHERE ");
        int numParams = criteria.size();
        int paramPosition = 0;
        String[] paramVals = new String[numParams];
        Set<SearchTerm> keyset = criteria.keySet();
        Iterator<SearchTerm> iter = keyset.iterator();
        while(iter.hasNext()) {
            SearchTerm currentKey = iter.next();
            String currentValue = criteria .get(currentKey);
            if(paramPosition > 0) {
                query.append(" and ");
            }
            query.append(currentKey);
            query.append(" = ? ");
            paramVals[paramPosition] = currentValue;
            paramPosition++;
        }
        return jdbcTemplate.query(query.toString(), new ContactMapper(), paramVals);
//        criteria.forEach((k,v) -> { //k is seach term, v is String
//            if(paramPosition > 0) {
//                query.append(" and ");
//            }
//            query.append(k);
//            query.append(" = ? ");
//            paramVals[paramPosition] = v;
//            paramPosition++;
//        });
    }
    
    private static final class ContactMapper implements ParameterizedRowMapper<Contact> {

        @Override
        public Contact mapRow(ResultSet rs, int i) throws SQLException { //create a result set from an SQL row
            Contact contact = new Contact();
            contact.setContactId(rs.getInt("contact_id"));
            contact.setFirstName(rs.getString("first_name"));
            contact.setLastName(rs.getString("last_name"));
            contact.setCompany(rs.getString("company"));
            contact.setPhone(rs.getString("phone"));
            contact.setEmail(rs.getString("email"));
            return contact;
        }
        
    }
    
}
