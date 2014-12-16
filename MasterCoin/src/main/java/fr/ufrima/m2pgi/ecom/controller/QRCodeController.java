package fr.ufrima.m2pgi.ecom.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.EchangeOffreFacade;
import fr.ufrima.m2pgi.ecom.model.EchangeOffre;
import fr.ufrima.m2pgi.ecom.service.EchangeTxService;
import fr.ufrima.m2pgi.ecom.util.Util;

@RequestScoped
@ManagedBean
public class QRCodeController {
	
	@ManagedProperty(value = "#{login}")
	private Login login;

	public void setLogin(Login login) {
		this.login = login;
	}

	@Inject
	private FacesContext facesContext;
	
	@Inject
	private EchangeTxService echangeTxService;
	
	@Inject
	private EchangeOffreFacade echangeOffreFacade;
	
	private String scanned_qrcode;
	
	public String getScanned_qrcode() {
		return scanned_qrcode;
	}

	public void setScanned_qrcode(String scanned_qrcode) {
		this.scanned_qrcode = scanned_qrcode;
	}

	public void makeEchange() throws Exception {
		try {
			EchangeOffre echange = this.echangeOffreFacade.find(Long.parseLong(scanned_qrcode));
			echangeTxService.validerOffre(echange ,login.getCurrentUser());
			
		} catch (Exception e) {
				Util.DisplayError(e,facesContext);
		}
	}
	
}
