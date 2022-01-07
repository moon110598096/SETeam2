package usecase.account;

import adapter.account.CreateAccountInputImpl;
import adapter.account.CreateAccountOutputImpl;
import adapter.account.AccountRepositoryImpl;
import domain.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OAuthUseCaseTest {
    private AccountRepository accountRepository;
    @Before
    public void setUp(){
        accountRepository = new AccountRepositoryImpl();
    }

    @Test
    public void OAuth_Create_Account_Should_Success_Test(){
        CreateAccountInput input = new CreateAccountInputImpl();
        input.setGithubId("testGithubId");
        input.setName("testGithubName");

        CreateAccountOutput output = new CreateAccountOutputImpl();
        CreateAccountUseCase createAccountUseCase = new CreateAccountUseCase(accountRepository);
        createAccountUseCase.execute(input, output);
        Account account = accountRepository.getAccountById(output.getId());
        Assert.assertEquals("bigMoney", account.getAccount());
        accountRepository.deleteAccount(account.getId());
    }
}
