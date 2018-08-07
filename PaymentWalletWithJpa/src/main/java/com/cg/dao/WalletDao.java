package com.cg.dao;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.cg.bean.Account;
import com.cg.exception.WalletException;

public class WalletDao implements IWalletDao{
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
	
	EntityManager em = emf.createEntityManager();
	@Override
	public String createAccount(Account acc) throws WalletException {
		// TODO Auto-generated method stub
		em.getTransaction().begin();
		em.persist(acc);
		em.getTransaction().commit();
		return acc.getMobileno();
	}
	@Override
	public double showBalance(String mobileNo) throws WalletException {
		
		String str="select a from Account a where a.mobileno=?";
		TypedQuery<Account> query=em.createQuery(str,Account.class);
		query.setParameter(1,mobileNo);
		Account ac=query.getSingleResult();
		if(ac!=null) {
			return ac.getBalance();
		}else {
			throw new WalletException("No entity found for query");
		}
		
		
	}
	@Override
	public Account deposit(String mobileNo, double depositAmt) throws WalletException {
		// TODO Auto-generated method stub
		em.getTransaction().begin();
		String str="select a from Account a where a.mobileno=?";
		TypedQuery<Account> query=em.createQuery(str,Account.class);
		query.setParameter(1,mobileNo);
		Account ac=query.getSingleResult();
		if(ac==null) {
			throw new WalletException("does not exists");
		}
		double d=ac.getBalance()+depositAmt;
		ac.setBalance(d);
		ac.setDate(Date.valueOf(LocalDate.now()));
		em.merge(ac);
		
		
		em.getTransaction().commit();
		return ac;
	
	}
	@Override
	public Account withdraw(String mobileNo, double withdrawAmt) throws WalletException {
		em.getTransaction().begin();
		String str="select a from Account a where a.mobileno=?";
		TypedQuery<Account> query=em.createQuery(str,Account.class);
		query.setParameter(1,mobileNo);
		Account ac=query.getSingleResult();
		if(ac==null) {
			throw new WalletException("does not exists");
		}
		double d=ac.getBalance()-withdrawAmt;
		ac.setBalance(d);
		em.merge(ac);
		
		
		em.getTransaction().commit();
		return ac;
	}
	@Override
	public Account printTransactionDetails(String mobileNo) throws WalletException {
		String str="select a from Account a where a.mobileno=?";
		TypedQuery<Account> query=em.createQuery(str,Account.class);
		query.setParameter(1,mobileNo);
		Account ac=query.getSingleResult();
		if(ac==null) {
			return ac;
		}else {
			throw new WalletException("number doesnot exists");
		}
	}

}
