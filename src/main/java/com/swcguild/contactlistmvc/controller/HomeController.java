package com.swcguild.contactlistmvc.controller;

import com.swcguild.contactlistmvc.dao.ContactListDAO;
import com.swcguild.contactlistmvc.model.Contact;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HomeController {

    private ContactListDAO dao;

    @Inject
    public HomeController(ContactListDAO dao) {
        this.dao = dao;
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET) // the root "/" will be 
    //the fallback assuming that nothing defined in web.xml is accessible.
    //RequestMapping tells the "home" return value below to resolve to home.jsp
    public String displayHomePage() {
        return "home"; //response will be home.jsp
    }

    // This method will be invoked by Spring MVC when it sees a request for
    // ContactListMVC/mainAjaxPage.
    @RequestMapping(value = {"/mainAjaxPage"}, method = RequestMethod.GET)
    public String displayMainAjaxPage() {
        // This method does nothing except return the logical name of the 
        // view component (/jsp/home.jsp) that should be invoked in response
        // to this request.
        return "mainAjaxPage";
    }

    @RequestMapping(value = "/contact/{contactId}", method = RequestMethod.GET)   //PathVariable takes whatever var it sits next to, 
    @ResponseBody //tells spring to return the contact as a json object       //finds the PathVar that matches whatever is in its quotes
    public Contact getContact(@PathVariable("contactId") int contactId) {     //and then feeds that value into RequestMapping
        return dao.getContactById(contactId);
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED) //like the 200 or 404 page status
    @ResponseBody //ResponseBody is only necessary if you have a return type that you specifically want to turn into json
    public Contact createContact(@Valid @RequestBody Contact contact) { //requestBody takes json and turns it into a java object
        dao.addContact(contact); //the @Valid causes validation on Contact when Json is turned into a Contact byt going thru the Contact class and looking for all the @'s used for validtionat
        // it will then bundle those errors into a BindingResult on the backend, then it will throw MethodArgumentNotValidException with BindingResult inside it
        return contact;
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT) //another response status like 200 ok
    public void deleteContact(@PathVariable("id") int id) {
        dao.removeContact(id);
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putContact(@PathVariable("id") int id, @RequestBody Contact contact) {
        contact.setContactId(id);
        dao.updateContact(contact);
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    @ResponseBody
    public List<Contact> getAllContacts() {
        return dao.getAllContacts();
    }
}
