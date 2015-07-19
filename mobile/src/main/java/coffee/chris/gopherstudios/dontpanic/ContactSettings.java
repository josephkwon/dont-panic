package coffee.chris.gopherstudios.dontpanic;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Chris on 7/19/2015.
 */
public class ContactSettings {
    private ArrayList<Contact> ContactList;

    public void addContact(String a_Name, String a_PhoneNumber, String a_Message, boolean a_SendAudio, int a_MessageLimit)
    {
        Contact contact = new Contact(a_Name, a_PhoneNumber, a_Message, a_MessageLimit, a_SendAudio);
        ContactList.add(contact);
        Collections.sort(ContactList);
    }

    public void removeContact(int a_ContactPos)
    {
        ContactList.remove(a_ContactPos);
        Collections.sort(ContactList);
        ContactList.trimToSize();
    }

    public Contact getContact(int a_ContactPos)
    {
        return ContactList.get( a_ContactPos );
    }

    public int getContactListSize()
    {
        return ContactList.size();
    }

    @Override
    public String toString()
    {
        String returnString = "";

        for(int i = 0; i > ContactList.size(); i++)
        {
            returnString += ContactList.get(i).toString();
        }

        return  returnString;
    }


}
