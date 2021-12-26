package usecase.account;

import adapter.account.CreateAccountInputImpl;
import adapter.account.CreateAccountOutputImpl;
import adapter.account.AccountRepositoryImpl;
import domain.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OAuthUseCaseTest {
//    private AccountRepository accountRepository;
//    @Before
//    public void setUp(){
//        accountRepository = new AccountRepositoryImpl();
//    }
//
//    @Test
//    public void OAuth_Account_Should_Success_Test(){
//        OAuthServlet oAuth = new OAuthServlet();
//
//        CreateAccountInput input = new CreateAccountInputImpl();
//        input.setName(oAuth.getName());
//        input.setAccount(oAuth.getAccount());
//        input.setPassword(oAuth.getPassword());
//
//        CreateAccountOutput output = new CreateAccountOutputImpl();
//        CreateAccountUseCase createAccountUseCase = new CreateAccountUseCase(accountRepository);
//        createAccountUseCase.execute(input, output);
//        Account account = accountRepository.getAccountById(output.getId());
//        Assert.assertEquals(oAuth.getAccount(), account.getAccount());
//        accountRepository.deleteAccount(account.getId());
//
//    }
}
