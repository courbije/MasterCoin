package fr.ufrima.m2pgi.ecom.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import fr.ufrima.m2pgi.ecom.service.NotEnoughtMoneyException;

public class Util {
	
	public static void DisplayError(Exception e, FacesContext facesContext) {
		String errorMessage = getRootErrorMessage(e);
		FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
		facesContext.addMessage(null, m);
	}
	
	public static void DisplayError(String errorMessage, FacesContext facesContext) {
		FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
		facesContext.addMessage(null, m);
	}
	
	public static void DisplaySucces(FacesContext facesContext) {
		FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
		facesContext.addMessage(null, m);
	}
	
	public static void DisplaySucces(String succesMessage, FacesContext facesContext) {
		FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, succesMessage, "Registration successful");
		facesContext.addMessage(null, m);
	}
	
	private static String getRootErrorMessage(Exception e) {
		// Default to general error message that registration failed.
		String errorMessage = "Registration failed. See server log for more information";
		if (e == null) {
			// This shouldn't happen, but return the default messages
			return errorMessage;
		}

		if (e.getClass().equals(NotEnoughtMoneyException.class)){
			return "pas assez d'argent";
		}
		
		// Start with the exception and recurse to find the root cause
		Throwable t = e;
		while (t != null) {
			// Get the message from the Throwable class instance
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		// This is the root cause message
		return errorMessage;
	}

}
