package fr.ufrima.m2pgi.ecom.controller;

import java.awt.Event;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import fr.ufrima.m2pgi.ecom.model.Compte;
import fr.ufrima.m2pgi.ecom.service.CompteFacade;

@ManagedBean
@RequestScoped
public class Credentials {

	private String username;

	private String password;
	
	@Inject
	private CompteFacade compteFacade;
	
    @ManagedProperty(value="#{login}")
    private Login login;
    
	public void setLogin(Login login) {
		this.login = login;
	}

	@NotNull
	@Length(min = 6, max = 20)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotNull
	@Length(min = 6, max = 20)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
		
	public String login() {
		
		Compte result = compteFacade.find(this);
		if (result != null) {
			login.setCurrentUser(result);
			if(!login.getFromUrl().isEmpty())
			{
				String url = login.getFromUrl();
				login.setFromUrl("");
				return url+"/?faces-redirect=true";
			}
		} else {
			// perhaps add code here to report a failed login
		}
		return "";
	}
	
	public String login(String source) {
		
		Compte result = compteFacade.find(this);
		if (result != null) {
			login.setCurrentUser(result);
			if(!login.getFromUrl().isEmpty() && FacesContext.getCurrentInstance().getViewRoot().getViewId().matches("/login.xhtml"))
			{
				String url = login.getFromUrl();
				login.setFromUrl("");
				return url+"/?faces-redirect=true";
			}
			else
			{
				return "";
			}
		} else {
			// perhaps add code here to report a failed login
		}
		return "";
	}
	
	public void logout() {

		login.setCurrentUser(null);

	}

	


}