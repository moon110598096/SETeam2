package usecase.oauth;

import domain.Account;
import usecase.account.AccountRepository;
import usecase.account.CreateAccountInput;
import usecase.account.CreateAccountOutput;
import usecase.account.CreateAccountUseCase;

public class OAuthLoginUseCase {

    private AccountRepository accountRepository;
    public OAuthLoginUseCase(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public void execute(CreateAccountInput input, CreateAccountOutput output) {
        Account admin = new Account();
        admin.setName(input.getName());
        admin.setGithubId(input.getGithubId());

        boolean githubAccountExist = accountRepository.verifyGithubAccount(admin);
        if (!githubAccountExist){
                CreateAccountUseCase createAccountUseCase = new CreateAccountUseCase(accountRepository);
                createAccountUseCase.executeOAuth(input, output);
        }
        else {
            Account account = accountRepository.getAccountByGithubId(admin);

            output.setId(account.getId());
            output.setName(account.getName());
            output.setGithubId(account.getGithubId());
        }

    }
}
