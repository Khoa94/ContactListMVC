/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.contactlistmvc.dao;

import com.swcguild.contactlistmvc.model.Contact;
import com.swcguild.contactlistmvc.model.SearchTerm;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author apprentice
 */
public class ContactListDAOMemImplTest {
    
    private ContactListDAO dao;
    
    public ContactListDAOMemImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    //must remove the info that spring adds to the database after the test is done
    public void setUp() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        dao = ctx.getBean("contactListDAO", ContactListDAO.class);
        JdbcTemplate jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        jdbcTemplate.execute("DELETE FROM contacts");
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void addGetDeleteContact() {
        Contact nc = new Contact();
        nc.setFirstName("John");
        nc.setLastName("Doe");
        nc.setCompany("Oracle");
        nc.setEmail("john@doe.com");
        nc.setPhone("1234567890");
        
        dao.addContact(nc);
        
        Contact fromDb = dao.getContactById(nc.getContactId());
        
        assertEquals(fromDb, nc);
        
        dao.removeContact(nc.getContactId());
        
        assertNull(dao.getContactById(nc.getContactId()));
    }
    
    @Test
    public void addUpdateContact() {
        Contact nc = new Contact();
        nc.setFirstName("Jimmy");
        nc.setLastName("Smith");
        nc.setCompany("Sun");
        nc.setEmail("jimmy@sun.com");
        nc.setPhone("9876543210");
        
        dao.addContact(nc);
        
        nc.setPhone("9999999999");
        dao.updateContact(nc);
        
        Contact fromDb = dao.getContactById(nc.getContactId());
        assertEquals(fromDb, nc);
    }
    
    @Test
    public void getAllContacts() {
        Contact nc = new Contact();
        nc.setFirstName("Jimmy");
        nc.setLastName("Smith");
        nc.setCompany("Sun");
        nc.setEmail("jimmy@sun.com");
        nc.setPhone("9876543210");
        
        dao.addContact(nc);
        
        Contact nc2 = new Contact();
        nc2.setFirstName("John");
        nc2.setLastName("Jones");
        nc2.setCompany("Sun");
        nc2.setEmail("john@jones.com");
        nc2.setPhone("5556667777");
        
        dao.addContact(nc2);
        
        List<Contact> cList = dao.getAllContacts();
        
        assertEquals(2, cList.size());
    }
    
    @Test
    public void searchContacts() {
        Contact nc = new Contact();
        nc.setFirstName("Jimmy");
        nc.setLastName("Smith");
        nc.setCompany("Sun");
        nc.setEmail("jimmy@sun.com");
        nc.setPhone("9876543210");
        
        dao.addContact(nc);
        
        Contact nc2 = new Contact();
        nc2.setFirstName("John");
        nc2.setLastName("Jones");
        nc2.setCompany("Sun");
        nc2.setEmail("john@jones.com");
        nc2.setPhone("5556667777");
        
        dao.addContact(nc2);
        
        Contact nc3 = new Contact();
        nc3.setFirstName("Jimmy");
        nc3.setLastName("Smith");
        nc3.setCompany("Microsoft");
        nc3.setEmail("jimmy@sun.com");
        nc3.setPhone("9876543210");
        
        dao.addContact(nc3);
        
        Map<SearchTerm, String> criteria = new HashMap<>();
        criteria.put(SearchTerm.LAST_NAME, "Jones");
        List<Contact> cList = dao.searchContacts(criteria);
        
        assertEquals(1, cList.size());
        assertEquals(nc2, cList.get(0));
        
        criteria.put(SearchTerm.LAST_NAME, "Smith");
        cList = dao.searchContacts(criteria);
        assertEquals(2, cList.size());
        
        criteria.put(SearchTerm.COMPANY, "Microsoft");
        cList = dao.searchContacts(criteria);
        assertEquals(1, cList.size());
        assertEquals(nc3, cList.get(0));
        
        criteria.put(SearchTerm.COMPANY, "Sun");
        cList = dao.searchContacts(criteria);
        assertEquals(1, cList.size());
        assertEquals(nc, cList.get(0));
        
        criteria.put(SearchTerm.COMPANY, "Apple");
        cList = dao.searchContacts(criteria);
        assertEquals(0, cList.size());
    }

    /**
     * Test of addContact method, of class ContactListDAOMemImpl.
     */
//    @Test
//    public void testAddContact() {
//        System.out.println("addContact");
//        Contact newContact = null;
//        ContactListDAOMemImpl instance = new ContactListDAOMemImpl();
//        Contact expResult = null;
//        Contact result = instance.addContact(newContact);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of removeContact method, of class ContactListDAOMemImpl.
//     */
//    @Test
//    public void testRemoveContact() {
//        System.out.println("removeContact");
//        int contactId = 0;
//        ContactListDAOMemImpl instance = new ContactListDAOMemImpl();
//        instance.removeContact(contactId);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of updateContact method, of class ContactListDAOMemImpl.
//     */
//    @Test
//    public void testUpdateContact() {
//        System.out.println("updateContact");
//        Contact contact = null;
//        ContactListDAOMemImpl instance = new ContactListDAOMemImpl();
//        instance.updateContact(contact);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getAllContacts method, of class ContactListDAOMemImpl.
//     */
//    @Test
//    public void testGetAllContacts() {
//        System.out.println("getAllContacts");
//        ContactListDAOMemImpl instance = new ContactListDAOMemImpl();
//        List<Contact> expResult = null;
//        List<Contact> result = instance.getAllContacts();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getContactById method, of class ContactListDAOMemImpl.
//     */
//    @Test
//    public void testGetContactById() {
//        System.out.println("getContactById");
//        int contactId = 0;
//        ContactListDAOMemImpl instance = new ContactListDAOMemImpl();
//        Contact expResult = null;
//        Contact result = instance.getContactById(contactId);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of searchContacts method, of class ContactListDAOMemImpl.
//     */
//    @Test
//    public void testSearchContacts() {
//        System.out.println("searchContacts");
//        Map<SearchTerm, String> criteria = null;
//        ContactListDAOMemImpl instance = new ContactListDAOMemImpl();
//        List<Contact> expResult = null;
//        List<Contact> result = instance.searchContacts(criteria);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
