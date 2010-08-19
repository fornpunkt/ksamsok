package se.raa.ksamsok.harvest.validation;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Catches exceptions thrown by the validator when validating
 * @author Martin Duveborg
 */
public class ErrorHandlerImpl implements ErrorHandler{

	private List<Message> messages = new ArrayList<Message>();

	public void error(SAXParseException exception) throws SAXException {
		addMessage("Fel: ", exception);
	}

    public void fatalError(SAXParseException exception) throws SAXException{
    	addMessage("Fatalt fel: ", exception);
    }

    public void warning(SAXParseException exception) throws SAXException{
    	addMessage("Varning: ", exception);
    }

    public List<Message> getReport(){
    	return messages;
    }  
    
    /**
     * Adds a message which will be retrieved by getReport()
     * @param msgStart b�rjan av meddelandet, tex "Fel: "
     * @param e vilket exception som helst. �r det ett
     * SAXParseException s� l�ggs rad&kolumn till i meddelandet
     */
    public void addMessage(String msgStart, Exception e){
    	Message m = new Message();
    	m.messageText = msgStart + (e == null ? "" : e.getMessage());
    	if(!messages.contains(m)){
    		// adds a new message
    		if(e != null && e instanceof SAXParseException){
	        	m.firstOccuranceCol = ((SAXParseException)e).getColumnNumber();
	        	m.firstOccuranceRow = ((SAXParseException)e).getLineNumber();
    		}
        	messages.add(m);	
    	}
    	else {
    		// counting up the number of times this message has occured
    		Message m2 = messages.get(messages.indexOf(m));
    		m2.totalOccurances++;
    	}
    }
    
    /**
     * @see addMessage(String msgStart, Exception e)
     */
    public void addMessage(String msg){
    	addMessage(msg, null);
    }
}
