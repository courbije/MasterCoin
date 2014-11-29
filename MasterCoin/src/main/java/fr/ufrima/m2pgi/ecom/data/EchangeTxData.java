package fr.ufrima.m2pgi.ecom.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import fr.ufrima.m2pgi.ecom.facade.TransactionFacade;
import fr.ufrima.m2pgi.ecom.model.Transaction;

@RequestScoped
public class EchangeTxData {

    @Inject
    private TransactionFacade transactionFacade;
	private List<Transaction> transactions;

    @Produces
    @Named
    public List<Transaction> getTransaction() {
    	return transactions;
    }

    @PostConstruct
    public void init() {
    	transactions = transactionFacade.findAll();
    }
}
