package usecase.account;


import domain.Account;

public class CreateAccountUseCase {

    private AccountRepository accountRepository;
    public CreateAccountUseCase(AccountRepository accountRepository){
        this.accountRepository = accountRepository;

    }
    public void execute(CreateAccountInput input, CreateAccountOutput output) {

        Account admin = new Account(
                input.getName(),
                input.getAccount(),
                input.getPassword()
        );
        output.setId(admin.getId());
        output.setAccount(admin);
        accountRepository.createAccount(admin);
    }

    public void executeOAuth(CreateAccountInput input, CreateAccountOutput output) {

        Account admin = new Account();
        admin.setName(input.getName());
        admin.setGithubId(input.getGithubId());

        output.setId(admin.getId());
        output.setName(admin.getName());
        output.setGithubId(admin.getGithubId());
        accountRepository.createAccount(admin);
    }
}
