package fr.ufrima.m2pgi.ecom.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import fr.ufrima.m2pgi.ecom.facade.MonnaieFacade;
import fr.ufrima.m2pgi.ecom.model.Monnaie;

@FacesConverter("fr.ufrima.m2pgi.MonnaieConverter")
public class MonnaieConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		try {
			Context ctx = new InitialContext();
			MonnaieFacade monnaieFacade = (MonnaieFacade) ctx.lookup("java:module/MonnaieFacade");
			return monnaieFacade.find(Long.valueOf(arg2));
		} catch (NamingException e) {
			throw new ConverterException("Impossible de récuperer MonnaieFacade");
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return ((Monnaie) arg2).getId().toString();
	}

}
