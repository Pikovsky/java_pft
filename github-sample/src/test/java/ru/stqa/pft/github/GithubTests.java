package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {

  @Test
  public void GithubTests() throws IOException {
    Github github = new RtGithub("4df429aa45702e2518eb3a2b4896f1781f348697");
    RepoCommits commits = github.repos().get(new Coordinates.Simple("barancev", "java_pft")).commits();
    for (RepoCommit commit: commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
      System.out.println(new RepoCommit.Smart(commit).message());
    }
  }
}
