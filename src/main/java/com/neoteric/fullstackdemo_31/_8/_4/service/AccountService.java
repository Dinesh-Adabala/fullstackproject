package com.neoteric.fullstackdemo_31._8._4.service;

import com.neoteric.fullstackdemo_31._8._4.exception.AccountCreationFailedException;
import com.neoteric.fullstackdemo_31._8._4.hibernatte.HibernatteUtils;
import com.neoteric.fullstackdemo_31._8._4.mapper.AccountMapper;
import com.neoteric.fullstackdemo_31._8._4.model.Account;
import com.neoteric.fullstackdemo_31._8._4.model.AccountAddressEntity;
import com.neoteric.fullstackdemo_31._8._4.model.AccountEntity;
import com.neoteric.fullstackdemo_31._8._4.model.Address;

//import com.neoteric.fullstackdemo_31._8._4.repository.AccountRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AccountService {
   /* @Autowired
    AccountRepository accountRepository;
*/
    @Autowired
    AccountMapper accountMapper;

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

    /*public Account getAccount(String accountNumber)  {
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
    }*/


    public List<Account> getAccount(String accountNumber) {
        SessionFactory sessionFactory = HibernatteUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<AccountEntity> criteriaQuery = criteriaBuilder.createQuery(AccountEntity.class);
        Root<AccountEntity> root = criteriaQuery.from(AccountEntity.class);

        if (accountNumber != null && !accountNumber.isEmpty()) {
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("accountnumber"), accountNumber));
        } else {
            criteriaQuery.select(root);
        }

        Query<AccountEntity> query = session.createQuery(criteriaQuery);
        List<AccountEntity> accountEntities = query.getResultList();
        List<Account> accounts = new ArrayList<>();
        for (AccountEntity accountEntity : accountEntities) {

            Address address = null;
            if (!accountEntity.getAccountAddressEntityList().isEmpty()) {
                AccountAddressEntity addressEntity = accountEntity.getAccountAddressEntityList().get(0);
                address = Address.builder()
                        .add1(addressEntity.getAddress1())
                         .add2(addressEntity.getAddress2())
                        .pincode(addressEntity.getPincode())
                        .state(addressEntity.getState())
                        .city(addressEntity.getCity())
                        .build();
            }
 
            Account account = Account.builder()
                    .name(accountEntity.getName())
                    .pan(accountEntity.getPan())
                    .mobileNumber(accountEntity.getMobileNumber())
                    .balance(accountEntity.getBalance())
                    .accountnumber(accountEntity.getAccountnumber())
                    .address(address)
                    .build();

            accounts.add(account);
        }

        return accounts;




    }

/*
  public Account searchAccountByJPA (String accountNumber){
      EntityManagerFactory emf=Persistence.createEntityManagerFactory("select a FROM AccountEntity a WHERE a.accountnumber = :inputaccountNumber");
      EntityManager em=emf.createEntityManager();
      em.getTransaction().begin();
      jakarta.persistence.Query query = em.createQuery("select a FROM AccountEntity a WHERE a.accountnumber = :inputaccountNumber");
      query.setParameter("inputAccountNumber",accountNumber);

      List<AccountEntity> accountEntities = query.getResultList();
      AccountEntity accountEntity = accountEntities.get(0);
      Account account = Account.builder()
              .name(accountEntity.getName())
              .pan(accountEntity.getPan())
              .mobileNumber(accountEntity.getMobileNumber())
              .balance(accountEntity.getBalance())
              .accountnumber(accountEntity.getAccountnumber())
              .build();

      List<AccountAddressEntity > accountAddressEntityList = accountEntity.getAccountAddressEntityList();
      if (Objects.nonNull(accountAddressEntityList) && accountAddressEntityList.size()>0){
          AccountAddressEntity addressEntity = accountEntity.getAccountAddressEntityList().get(0);
        Address  address = Address.builder()
                  .add1(addressEntity.getAddress1())
                  .add2(addressEntity.getAddress2())
                  .pincode(addressEntity.getPincode())
                  .state(addressEntity.getState())
                  .city(addressEntity.getCity())
                  .build();
      }
      return account;
  }*/
/*public Account getAccountByDataJPA(String accountNumber)  {
    AccountEntity  accountEntity = accountRepository.findByAccountNumber(accountNumber);
    Account account = accountMapper.dtoToEntity(accountEntity);
    return account;
}*/



}
