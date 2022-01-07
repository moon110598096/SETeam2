package usecase;

import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import adapter.gitrepository.GitRepositoryRepositoryImpl;
import com.mysql.cj.xdevapi.JsonArray;
import domain.GitRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import usecase.GithubRepositoryAccessor;
import usecase.gitrepository.GitRepositoryRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GithubOAuthTest {

    @Test
    public void CheckOAuthPageAlive() throws IOException {
        String apiUrl = "https://github.com/login/oauth/authorize?client_id=2ba7827da6b98900790e";
        GithubRepositoryAccessor accessor = new GithubRepositoryAccessor();
        JSONObject jsonObject = (JSONObject) accessor.httpsGet(apiUrl).get(0);
        System.out.println(jsonObject);
//        JSONObject commitStatsJsonObject = jsonObject.getJSONObject("stats");
//        int totalChanges = commitStatsJsonObject.getInt("total");
//        int additions = commitStatsJsonObject.getInt("additions");
//        int deletions = commitStatsJsonObject.getInt("deletions");
//        Assert.assertEquals(100, totalChanges);
//        Assert.assertEquals(59, additions);
//        Assert.assertEquals(41, deletions);
    }
}
