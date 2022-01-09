package usecase.account;

import adapter.account.CreateAccountInputImpl;
import adapter.account.CreateAccountOutputImpl;
import adapter.account.AccountRepositoryImpl;
import domain.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import usecase.oauth.OAuthLoginUseCase;

public class OAuthUseCaseTest {
    private AccountRepository accountRepository;
    @Before
    public void setUp(){
        accountRepository = new AccountRepositoryImpl();
    }

    @Test
    public void OAuth_Login_Check_If_Account_Not_Exist_Should_Create_Test(){
        CreateAccountInput input = new CreateAccountInputImpl();
        input.setGithubId("testGithubId");
        input.setName("testGithubName");

        CreateAccountOutput output = new CreateAccountOutputImpl();
        OAuthLoginUseCase oAuthUseCaseTest = new OAuthLoginUseCase(accountRepository);
        oAuthUseCaseTest.execute(input, output);

        Account account = accountRepository.getAccountById(output.getId());
        Assert.assertEquals("testGithubId", account.getGithubId());
        Assert.assertEquals("testGithubName", account.getName());

        //clean test data
        accountRepository.deleteAccount(account.getId());
    }

    @Test
    public void OAuth_Login_Check_If_Account_Exist_Should_Search_Test(){
        CreateAccountInput input = new CreateAccountInputImpl();
        input.setGithubId("89350545");
        input.setName("AccountShouldNotCreate");

        CreateAccountOutput output = new CreateAccountOutputImpl();
        OAuthLoginUseCase oAuthUseCaseTest = new OAuthLoginUseCase(accountRepository);
        oAuthUseCaseTest.execute(input, output);

        Account account = accountRepository.getAccountById(output.getId());
        Assert.assertEquals("89350545", account.getGithubId());
        Assert.assertEquals("davidcao6426", account.getName());
    }
}