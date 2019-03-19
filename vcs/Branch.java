package vcs;

import java.util.ArrayList;

public class Branch {
    private ArrayList<Commit> myBranch;
    private String branchName;
    private int commitDeleted = 0;

    public Branch() {

    }

    public Branch(String branchName) {
        this.myBranch = new ArrayList<Commit>();
        this.branchName = branchName;
    }

    /**/
    public int getCommitDeleted() {
        return commitDeleted;
    }

    /**/
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**/
    public ArrayList<Commit> getMyBranch() {
        return myBranch;
    }

    /**/
    public void setMyBranch(ArrayList<Commit> myBranch) {
        this.myBranch = myBranch;
    }

    /**/
    public void copyBack(ArrayList<Commit> toCopy) {
        myBranch.addAll(toCopy);
    }
}
