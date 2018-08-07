package com.cg.dao;

import com.cg.bean.Account;
import com.cg.exception.WalletException;

public interface IWalletDao {
	String createAccount(Account acc) throws WalletException;

	double showBalance(String mobileNo) throws WalletException;

	Account deposit(String mobileNo,double depositAmt) throws WalletException;

	Account withdraw(String mobileNo,double withdrawAmt) throws WalletException;

	Account printTransactionDetails(String mobileNo) throws WalletException;
}
