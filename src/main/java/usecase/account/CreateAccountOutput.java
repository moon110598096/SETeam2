package usecase.account;

import domain.Account;

public interface CreateAccountOutput {
    void setId(String id);
    String getId();
    void setGithubId(String githubId);
    String getGithubId();
}
