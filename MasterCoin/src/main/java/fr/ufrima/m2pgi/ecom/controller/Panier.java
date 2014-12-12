package fr.ufrima.m2pgi.ecom.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import fr.ufrima.m2pgi.ecom.exception.SameMoneyException;
import fr.ufrima.m2pgi.ecom.model.Transaction;
import fr.ufrima.m2pgi.ecom.service.EchangeTxService;
import fr.ufrima.m2pgi.ecom.util.Util;

@SessionScoped
@ManagedBean
public class Panier {

	private ArrayList<Transaction> articles;

	@ManagedProperty(value = "#{login}")
	private Login login;

	@Inject
	private EchangeTxService echangeTxFacade;

	@Inject
	private FacesContext facesContext;

	@PostConstruct
	private void initNewMember() {
		articles = new ArrayList<Transaction>();
	}

	public void addArticles(Transaction article) throws SameMoneyException {
		if (article.getMonnaieAchat().equals(article.getMonnaieVendre())) {
			throw new SameMoneyException();
		}
		Transaction t = article.clone();
		this.articles.add(t);
	}

	public void valider() {
		login.forwardToLoginIfNotLoggedIn();
		try {
			echangeTxFacade.validerPanniers(articles, login.getCurrentUser());
			Util.DisplaySucces(facesContext);
			initNewMember();
		} catch (Exception e) {
			Util.DisplayError(e, facesContext);
		}
	}

	// on ne peut supprimer qu'un article a la fois et la page doit
	// obligatoirement être regénérer
	// car la position des élélemnts dans la liste change
	public void supprimer(int i) {
		articles.remove(i);
	}

	public double montantVendre(int i) {
		return echangeTxFacade.calculerMontantVendre(articles.get(i), login.getCurrentUser());
	}

	public void clean() {
		articles.clear();
	}

	public List<Transaction> getArticles() {
		return articles;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

}
