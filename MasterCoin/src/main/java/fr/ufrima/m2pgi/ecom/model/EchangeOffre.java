package fr.ufrima.m2pgi.ecom.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.AssertTrue;
import javax.validation.Valid;

import fr.ufrima.m2pgi.ecom.util.GtZero;

@SuppressWarnings("serial")
@Entity
public class EchangeOffre implements Serializable, Comparable<EchangeOffre> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "idc", nullable = false)
	private Compte compte;

	@ManyToOne
	@JoinColumn(name = "idmV", nullable = false)
	private Monnaie monnaieVendre;

	@ManyToOne
	@JoinColumn(name = "idmA", nullable = false)
	@Valid
	private Monnaie monnaieAchat;

	@Column(nullable = false)
	@GtZero
	private Double montantVendre;

	@Column(nullable = false)
	@GtZero
	private Double montantAchat;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreation;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof EchangeOffre)) {
			return false;
		}
		EchangeOffre other = (EchangeOffre) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public Compte getCompte() {
		return this.compte;
	}

	public void setCompte(final Compte compte) {
		this.compte = compte;
	}

	public Monnaie getMonnaieVendre() {
		return this.monnaieVendre;
	}

	public void setMonnaieVendre(final Monnaie monnaieVendre) {
		this.monnaieVendre = monnaieVendre;
	}

	public Monnaie getMonnaieAchat() {
		return this.monnaieAchat;
	}

	public void setMonnaieAchat(final Monnaie monnaieAchat) {
		this.monnaieAchat = monnaieAchat;
	}

	public Double getMontantVendre() {
		return montantVendre;
	}

	public void setMontantVendre(Double montantVendre) {
		this.montantVendre = montantVendre;
	}

	public Double getMontantAchat() {
		return montantAchat;
	}

	public void setMontantAchat(Double montantAchat) {
		this.montantAchat = montantAchat;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (montantVendre != null)
			result += "montantVendre: " + montantVendre;
		if (montantAchat != null)
			result += ", montantAchat: " + montantAchat;
		return result;
	}

	@Override
	public int compareTo(EchangeOffre arg0) {
		// TODO Auto-generated method stub
		double myTaux = (double) this.getMontantAchat() / (double) this.getMontantVendre();
		double Tauxarg0 = (double) arg0.getMontantAchat() / (double) arg0.getMontantVendre();
		if (myTaux > Tauxarg0) {
			return 1;
		} else if (myTaux < Tauxarg0) {
			return -1;
		} else {
			return 0;
		}
	}

}
