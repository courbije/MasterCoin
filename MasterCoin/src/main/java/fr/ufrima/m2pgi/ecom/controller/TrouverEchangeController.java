package fr.ufrima.m2pgi.ecom.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean
public class TrouverEchangeController {

	private String idV;

	private String idA;

	public String getIdV() {
		return idV;
	}

	public void setIdV(String idV) {
		this.idV = idV;
	}

	public String getIdA() {
		return idA;
	}

	public void setIdA(String idA) {
		this.idA = idA;
	}
}
