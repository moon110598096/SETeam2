package adapter.account;

import usecase.account.CreateAccountInput;

public class CreateAccountInputImpl implements CreateAccountInput {
    private String name;
    private String account;
    private String password;
    private String githubId;

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setGithubId(String githubId) {
        this.githubId = githubId;
    }

    public String getGithubId() {
        return this.githubId;
    }
}
