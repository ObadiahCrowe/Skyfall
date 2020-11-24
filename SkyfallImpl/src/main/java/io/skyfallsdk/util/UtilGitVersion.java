package io.skyfallsdk.util;

import io.skyfallsdk.SkyfallMain;
import io.skyfallsdk.expansion.Expansion;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UtilGitVersion {

    public static GitInfo getFromSkyfall() {
        return getFromStream(SkyfallMain.class.getClassLoader().getResourceAsStream("git.properties"));
    }

    public static GitInfo getFromExpansion(Expansion expansion) {
        return getFromStream(expansion.getClass().getClassLoader().getResourceAsStream("git.properties"));
    }

    private static GitInfo getFromStream(InputStream stream) {
        Properties properties = new Properties();

        try {
            properties.load(stream);
        } catch (IOException | NullPointerException e) {
            return null;
        }

        try {
            return new GitInfo(
              properties.getProperty("git.branch"),
              properties.getProperty("git.build.time"),
              properties.getProperty("git.commit.id.full"),
              properties.getProperty("git.commit.message.full")
            );
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static class GitInfo {

        private final String branch;
        private final String buildTime;
        private final String gitCommitId;
        private final String commitMessage;

        public GitInfo(String branch, String buildTime, String gitCommitId, String commitMessage) {
            this.branch = branch;
            this.buildTime = buildTime;
            this.gitCommitId = gitCommitId;
            this.commitMessage = commitMessage;
        }

        public String getBranch() {
            return this.branch;
        }

        public String getBuildTime() {
            return this.buildTime;
        }

        public String getGitCommitId() {
            return this.gitCommitId;
        }

        public String getCommitMessage() {
            return this.commitMessage;
        }

        public String getPretty() {
            return this.branch + '-' + this.gitCommitId + " (Built on: " + this.getBuildTime() + ')';
        }

        @Override
        public String toString() {
            return "GitInfo{" +
              "branch='" + branch + '\'' +
              ", buildTime='" + buildTime + '\'' +
              ", gitCommitId='" + gitCommitId + '\'' +
              ", commitMessage='" + commitMessage + '\'' +
              '}';
        }
    }
}

