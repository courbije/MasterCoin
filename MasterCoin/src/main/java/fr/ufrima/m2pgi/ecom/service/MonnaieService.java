package fr.ufrima.m2pgi.ecom.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import fr.ufrima.m2pgi.ecom.facade.MonnaieFacade;
import fr.ufrima.m2pgi.ecom.model.Monnaie;
import fr.ufrima.m2pgi.ecom.model.MonnaieDTO;

@Stateless
public class MonnaieService {

	@Resource
	private EJBContext context;

	@Inject
	private MonnaieFacade monnaieFacade;

	public void registerMoney(MonnaieDTO monnaieDTO) {
		monnaieFacade.create(MonnaieDTO2Monnaie(monnaieDTO));
	}

	public void registerImage(MonnaieDTO monnaieDTO, String id) throws IOException {
		Monnaie monnaie = monnaieFacade.find(Long.valueOf(id));
		monnaie.setImage(monnaieDTO.getImage().getBytes());
		monnaie.setType(monnaieDTO.getImage().getContentType());
		monnaieFacade.edit(monnaie);
	}

	public boolean hasImage(String id) {
		return monnaieFacade.find(Long.valueOf(id)).getImage() != null;
	}

	public StreamedContent getImage(String id) {
		Monnaie monnaie = monnaieFacade.find(Long.valueOf(id));
		if (monnaie.getImage() != null) {
			return new DefaultStreamedContent(new ByteArrayInputStream(monnaie.getImage()), monnaie.getType(), "image." + monnaie.getType());
		} else {
			return new DefaultStreamedContent();
		}
	}

	private Monnaie MonnaieDTO2Monnaie(MonnaieDTO monnaieDTO) {
		// SI PAS D'IMAGE ALORS EN METTRE UNE PAR DEFAUT
		Monnaie monnaie = new Monnaie();
		monnaie.setNom(monnaieDTO.getNom());
		monnaie.setAcroyme(monnaieDTO.getAcroyme());
		if (monnaieDTO.getImage() != null) {
			monnaie.setType(monnaieDTO.getImage().getContentType());
			try {
				monnaie.setImage(monnaieDTO.getImage().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return monnaie;
	}
}
