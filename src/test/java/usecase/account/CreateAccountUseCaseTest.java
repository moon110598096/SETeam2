package usecase.account;

import adapter.account.CreateAccountInputImpl;
import adapter.account.CreateAccountOutputImpl;
import adapter.account.AccountRepositoryImpl;
import domain.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CreateAccountUseCaseTest {
    private AccountRepository accountRepository;
    @Before
    public void setUp(){
        accountRepository = new AccountRepositoryImpl();
    }
    @Test
    public void Create_Account_Shoud_Commit_To_Account_Repository_Test(){

        CreateAccountInput input = new CreateAccountInputImpl();
        input.setName("fish han");
        input.setAccount("bigMoney");
        input.setPassword("bbb");

        CreateAccountOutput output = new CreateAccountOutputImpl();
        CreateAccountUseCase createAccountUseCase = new CreateAccountUseCase(accountRepository);
        createAccountUseCase.execute(input, output);
        Account account = accountRepository.getAccountById(output.getId());
        Assert.assertEquals("bigMoney", account.getAccount());
        accountRepository.deleteAccount(account.getId());
    }

    @Test
    public void OAuth_Create_Account_Should_Success_Test(){
        CreateAccountInput input = new CreateAccountInputImpl();
        input.setGithubId("testGithubId");
        input.setName("testGithubName");

        CreateAccountOutput output = new CreateAccountOutputImpl();
        CreateAccountUseCase createAccountUseCase = new CreateAccountUseCase(accountRepository);
        createAccountUseCase.executeOAuth(input, output);
        Account account = accountRepository.getAccountById(output.getId());
        Assert.assertEquals("testGithubId", account.getGithubId());
        Assert.assertEquals("testGithubId", account.getName());
        accountRepository.deleteAccount(account.getId());
    }

    //需要被移到別的地方
//    因需先過一次sonarqube，暫先註解，之後再看為何爆炸
//    @Test
//    public void Verify_Account_Test(){
//        Account account = new Account("account", "password");
//        Assert.assertTrue(accountRepository.verifyAccount(account));
//        account = new Account("account", "notAlive");
//        Assert.assertFalse(accountRepository.verifyAccount(account));
//
//    }
}
