package vcs;

import filesystem.FileSystemSnapshot;
import utils.IDGenerator;

public class Commit extends Branch {
    private final int idCommit;
    private String parrsedString;
    private FileSystemSnapshot commitSnapshot;


    public Commit(FileSystemSnapshot currentFile) {
        if (getCommitDeleted() > 0) {
            this.idCommit = getCommitDeleted() + IDGenerator.generateCommitID() - 1;
            System.out.println("ceva");
        } else {
            this.idCommit = IDGenerator.generateCommitID();
        }
        this.parrsedString = " First commit";
        this.commitSnapshot = currentFile.cloneFileSystem();
    }

    /**/
    public int getIdCommit() {
        return idCommit;
    }
    /**/
    public String getParrsedString() {
        return parrsedString;
    }
    /**/
    public FileSystemSnapshot getCommitSnapshot() {
        return commitSnapshot;
    }
    /**/
    public void setCommitSnapshot(FileSystemSnapshot commitSnapshot) {
        this.commitSnapshot = commitSnapshot;
    }
    /**/
    public void setParrsedString(String parrsedString) {
        this.parrsedString = parrsedString;
    }
}
