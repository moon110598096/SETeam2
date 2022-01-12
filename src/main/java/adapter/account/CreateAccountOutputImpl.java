package adapter.account;

import domain.Account;
import usecase.account.CreateAccountOutput;

public class CreateAccountOutputImpl implements CreateAccountOutput {
    private String id;
    private String githubId;

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setGithubId(String githubId){ this.githubId = githubId; }
    @Override
    public String getGithubId(){ return this.githubId; }
}
