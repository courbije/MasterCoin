package fr.ufrima.m2pgi.ecom.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("serial")
public class MonnaieDTO implements Serializable {
	
	public MonnaieDTO() {
		this.image=null;
	}

	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	@NotEmpty
	private String nom;

	@NotNull
	@NotEmpty
	private String acroyme;

	private UploadedFile image;
	
    public UploadedFile getImage() {
        return this.image;
    }

    public void setImage(UploadedFile image) {
        this.image = image;
    }

	public String getNom() {
		return nom;
	}

	public void setNom(String Nom) {
		this.nom = Nom;
	}

	public String getAcroyme() {
		return acroyme;
	}

	public void setAcroyme(String acroyme) {
		this.acroyme = acroyme;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof MonnaieDTO)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (nom != null && !nom.trim().isEmpty())
			result += ", Nom: " + nom;
		if (acroyme != null && !acroyme.trim().isEmpty())
			result += ", acroyme: " + acroyme;
		return result;
	}
}