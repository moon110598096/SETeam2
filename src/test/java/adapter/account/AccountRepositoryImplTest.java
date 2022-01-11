package adapter.account;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import domain.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class AccountRepositoryImplTest {
    AccountRepositoryImpl repository;
    @Before
    public void setUp(){
        repository = new AccountRepositoryImpl();
    }

    @Test
    public void GetAccountByAccountAndPassword(){
        Account account = new Account("tt", "tt");
        Account query = repository.getAccountByAccountAndPassword(account);

        Assert.assertEquals("8df87ce5-ee74-4366-a539-82414e176a03", query.getId());

        account = new Account("ShouldNoFound", "ShouldNoFound");
        query = repository.getAccountByAccountAndPassword(account);

        Assert.assertEquals(null, query);
    }

    @Test
    public void GetAccountById(){
        String id = "8df87ce5-ee74-4366-a539-82414e176a03";
        Account query = repository.getAccountById(id);

        Assert.assertEquals("tt", query.getName());

        query = repository.getAccountById("");

        Assert.assertEquals(null, query);
    }

    @Test
    public void VerifyAccountTest() {
        Account account = new Account("ShouldNoFound", "ShouldNoFound");
        Assert.assertFalse( repository.verifyAccount(account) );

        account = new Account("tt", "tt");
        Assert.assertTrue( repository.verifyAccount(account) );

    }

    @Test
    public void DeleteProjectRelationsTest() {
        String id = "4d0f5ebc-a68f-4581-9527-406176dc581e";
        Account account = new Account(id, null, null, null);
        account.addProject("1");
        repository.updateAccountOwnProject(account);

        Assert.assertTrue((repository.deleteProjectRelations(id, "1")));
    }
}
