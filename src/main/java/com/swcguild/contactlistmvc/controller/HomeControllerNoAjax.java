package com.swcguild.contactlistmvc.controller;

import com.swcguild.contactlistmvc.dao.ContactListDAO;
import com.swcguild.contactlistmvc.model.Contact;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeControllerNoAjax {
    private ContactListDAO dao;
    
    @Inject //Spring goes thru and finds the bean that implements the interface that matches the interface below
    public HomeControllerNoAjax(ContactListDAO dao) {
        this.dao = dao;
    }
    
    @RequestMapping(value="/displayContactListNoAjax", method=RequestMethod.GET)
    public String displayContactListNoAjax(Model model) {
        List<Contact> cList = dao.getAllContacts();
        model.addAttribute("contactList", cList); //this is how we return input values back to the View
        return "displayContactListNoAjax";
    }
    
    @RequestMapping(value="/displayNewContactFormNoAjax", method=RequestMethod.GET)
    public String displayNewContactFormNoAjax() {
        return "newContactFormNoAjax";
    }
    
    @RequestMapping(value="/addNewContactNoAjax", method=RequestMethod.POST)
    public String addNewContactNoAjax(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String company = request.getParameter("company");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        
        Contact contact = new Contact();
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setCompany(company);
        contact.setEmail(email);
        contact.setPhone(phone);
        
        dao.addContact(contact);
        
        return "redirect:displayContactListNoAjax";
    }
    
    @RequestMapping(value="/deleteContactNoAjax", method=RequestMethod.GET)
    public String deleteContactNoAjax(HttpServletRequest request) {
        int contactId = Integer.parseInt(request.getParameter("contactId"));
        dao.removeContact(contactId);
        return "redirect:displayContactListNoAjax";
    }
    
    @RequestMapping(value="/displayEditContactFormNoAjax", method=RequestMethod.GET)
    public String displayEditContactFormNoAjax(HttpServletRequest request, Model model) {
        int contactId = Integer.parseInt(request.getParameter("contactId"));
        Contact contact = dao.getContactById(contactId);
        model.addAttribute("contact", contact);
        return "editContactFormNoAjax";
    }
    
    @RequestMapping(value="/editContactNoAjax", method=RequestMethod.POST)
    public String editContactNoAjax(@Valid @ModelAttribute("wigwam") Contact wigwam, BindingResult result) {
        //BindingResult checks for validation errors
        if(result.hasErrors()) {
            return "editContactFormNoAjax";
        }
        dao.updateContact(wigwam);
        return "redirect:displayContactListNoAjax";
    }
}
