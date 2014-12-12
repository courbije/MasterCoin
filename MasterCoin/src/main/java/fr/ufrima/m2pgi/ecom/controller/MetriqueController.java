package fr.ufrima.m2pgi.ecom.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.facade.MetriqueFacade;
import fr.ufrima.m2pgi.ecom.facade.MonnaieFacade;
import fr.ufrima.m2pgi.ecom.model.Metrique;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.util.Util;

@ViewScoped
@ManagedBean
public class MetriqueController {
	
	@Inject
	private MetriqueFacade metriqueFacade;
	
	@Inject
	private MonnaieFacade monnaieFacade;

	private Monnaie monnaie1;
	
	private Monnaie monnaie2;
	
	private String idV;
	
	private String idA;
	
	private List<Metrique> metriqueAllWhereMonnaie1Monnaie2;
	
	
	@PostConstruct
	public void init() {
		if (monnaie1 != null && monnaie2 != null) {
			metriqueAllWhereMonnaie1Monnaie2 = metriqueFacade.findAllByMonnaie(monnaie1, monnaie2);
		}
	}
	

	public Monnaie getMonnaie1() {
		return monnaie1;
	}

	public Monnaie getMonnaie2() {
		return monnaie2;
	}
	
	public String getIdV() {
		return idV;
	}

	public void setIdV(String idV) {
		if(idV!=null && !idV.equals("")) {
			this.monnaie2 = this.monnaieFacade.find(Long.parseLong(idV));
		}
		init();
		this.idV = idV;
	}

	public String getIdA() {
		return idA;
	}

	public void setIdA(String idA) {
		if(idA!=null && !idA.equals("")) {
			this.monnaie1 = this.monnaieFacade.find(Long.parseLong(idA));
		}
		init();
		this.idA = idA;
	}


	public List<Metrique> getMetriqueAllWhereMonnaie1Monnaie2(){
		return metriqueAllWhereMonnaie1Monnaie2;
	}
	
	public String donnees(){
		if (metriqueAllWhereMonnaie1Monnaie2==null)
			return "";
	 	int longueur = metriqueAllWhereMonnaie1Monnaie2.size();
	 	String s = "data: [";
	 	s +="{x: '" + metriqueAllWhereMonnaie1Monnaie2.get(0).getDate() + "', y: " + (metriqueAllWhereMonnaie1Monnaie2.get(0).getMontantMonnaie1()/metriqueAllWhereMonnaie1Monnaie2.get(0).getMontantMonnaie2()) ;
	 	for(int i=1; i < longueur; i++) 
	 	 { 
	 	 	s += "},\n{x: '" + metriqueAllWhereMonnaie1Monnaie2.get(i).getDate() + "', y: " + (metriqueAllWhereMonnaie1Monnaie2.get(i).getMontantMonnaie1()/metriqueAllWhereMonnaie1Monnaie2.get(i).getMontantMonnaie2()) ;
	 	 }
	 	 s+= "}\n]";
	 	return s;
	 }


	
}
