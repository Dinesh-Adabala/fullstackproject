package com.neoteric.fullstackdemo_31._8._4.service;

import com.neoteric.fullstackdemo_31._8._4.exception.AccountCreationFailedException;
import com.neoteric.fullstackdemo_31._8._4.hibernatte.HibernatteUtils;
import com.neoteric.fullstackdemo_31._8._4.model.Account;
import com.neoteric.fullstackdemo_31._8._4.model.AccountAddressEntity;
import com.neoteric.fullstackdemo_31._8._4.model.AccountEntity;
import com.neoteric.fullstackdemo_31._8._4.model.Address;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountService {
    public String createAccount(Account account) throws Exception{
        String accountNumber=null;
        try {
            Connection connection = DBCennection.getConnection();
            Statement statement = connection.createStatement();
            accountNumber= UUID.randomUUID().toString();
            String query = "insert into bank.account values(" + "'" + accountNumber + "'" + ","
                    + "'" + account.getName() + "'" + ","
                    + "'" + account.getPan() + "'" + ","
                    + "'" + account.getMobileNumber() + "'" + ","
                    + account.getBalance() + ")";
            int status = statement.executeUpdate(query);
            if (status==1) {
                System.out.println("Account is  created " + accountNumber);
            } else {
            throw new AccountCreationFailedException("Account creation is failed"+account.getPan());
            }
        }catch(Exception e){
            System.out.println("Execption occured "+e);
         throw e;
        }
        return accountNumber;
    }


    public String createAccountUsingHibernet(Account account){
        SessionFactory sessionFactory = HibernatteUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
       Transaction transaction= session.beginTransaction();
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountnumber(UUID.randomUUID().toString());
        accountEntity.setName(account.getName());
        accountEntity.setPan(account.getPan());
        accountEntity.setMobileNumber(account.getMobileNumber());
        accountEntity.setBalance(account.getBalance());

        session.persist(accountEntity);
        transaction.commit();
        return accountEntity.getAccountnumber();
    }


    public String oneToManyUsingHibernateFromUI(Account account){
       SessionFactory sessionFactory = HibernatteUtils.getSessionFactory();
       Session session = sessionFactory.openSession();
       Transaction transaction = session.beginTransaction();

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountnumber(UUID.randomUUID().toString());
        accountEntity.setName(account.getName());
        accountEntity.setPan(account.getPan());
        accountEntity.setMobileNumber(account.getMobileNumber());
        accountEntity.setBalance(account.getBalance());

        List<AccountAddressEntity> accountAddressEntityList = new ArrayList<>();
        AccountAddressEntity accountAddressEntity = new AccountAddressEntity();
        accountAddressEntity.setAddress1(account.getAddress().getAdd1());
        accountAddressEntity.setAddress2(account.getAddress().getAdd2());
        accountAddressEntity.setPincode(account.getAddress().getPincode());
        accountAddressEntity.setState(account.getAddress().getState());
        accountAddressEntity.setCity(account.getAddress().getCity());
        accountAddressEntity.setStatus(1);
        accountAddressEntity.setAccountEntity(accountEntity);
        accountAddressEntityList.add(accountAddressEntity);
        accountEntity.setAccountAddressEntityList(accountAddressEntityList);
        session.persist(accountEntity);
        transaction.commit();
        return accountEntity.getAccountnumber();

    }

    public Account getAccount(String accountNumber)  {
        SessionFactory sessionFactory = HibernatteUtils.getSessionFactory();
        Session session = sessionFactory.openSession();

          //  String hql = "FROM AccountEntity a WHERE a.accountnumber = :inputaccountNumber";
            Query<AccountEntity> query = session.createQuery("FROM AccountEntity a WHERE a.accountnumber = :inputaccountNumber");
            query.setParameter("inputaccountNumber", accountNumber);
            AccountEntity accountEntity = query.list().get(0);

                // Convert AccountEntity to Account
           Account account = Account.builder()
                        .name(accountEntity.getName())
                        .pan(accountEntity.getPan())
                        .mobileNumber(accountEntity.getMobileNumber())
                        .balance(accountEntity.getBalance())
                        .accountnumber(accountEntity.getAccountnumber())
                        .address(
                                Address.builder()
                                        .add1(accountEntity.getAccountAddressEntityList().get(0).getAddress1())
                                        .add2(accountEntity.getAccountAddressEntityList().get(0).getAddress2())
                                        .pincode(accountEntity.getAccountAddressEntityList().get(0).getPincode())
                                        .state(accountEntity.getAccountAddressEntityList().get(0).getState())
                                        .city(accountEntity.getAccountAddressEntityList().get(0).getCity())
                                        .build()
                                )
                   .build();


        return account;
    }


}
