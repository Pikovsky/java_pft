package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {

  @Test
  public void GithubTests() throws IOException {
    Github github = new RtGithub("03fd2e52648d1bedcafb68dc0c8b02ddd49c7698");
    RepoCommits commits = github.repos().get(new Coordinates.Simple("Pikovsky", "java_pft")).commits();
    for (RepoCommit commit: commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
      System.out.println(new RepoCommit.Smart(commit).message());
    }
  }
}
