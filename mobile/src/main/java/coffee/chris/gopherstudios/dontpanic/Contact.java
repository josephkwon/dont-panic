package coffee.chris.gopherstudios.dontpanic;

/**
 * Created by Chris on 7/19/2015.
 */
public class Contact implements Comparable<Contact> {
    private String m_Name;
    private String m_PhoneNumber;
    private String m_Message;
    private int m_MessageLimit;
    private boolean m_SendAudioTranscript;

    public Contact(String a_Name, String a_PhoneNumber, String a_Message, int a_MessageLimit, boolean a_SendAudio)
    {
        m_Name = a_Name;
        m_PhoneNumber = a_PhoneNumber;
        m_Message = a_Message;
        m_MessageLimit = a_MessageLimit;
        m_SendAudioTranscript = a_SendAudio;
    }

    public int compareTo(Contact contact)
    {
        return m_Name.compareTo(contact.m_Name);
    }

    public String getName()
    {
        return m_Name;
    }

    public String getPhone()
    {
        return m_PhoneNumber;
    }

    public String getMessage()
    {
        return m_Message;
    }

    public int getMessageLimit()
    {
        return m_MessageLimit;
    }

    public boolean getSendAudio()
    {
        return m_SendAudioTranscript;
    }

    @Override
    public String toString()
    {
        return  m_Name + " " +
                m_PhoneNumber + " " +
                m_Message + " " +
                m_MessageLimit + " " +
                m_SendAudioTranscript + "\n";
    }
}
