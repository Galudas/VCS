package vcs;

import filesystem.FileSystemOperation;
import filesystem.FileSystemSnapshot;
import utils.OutputWriter;
import utils.Visitor;

import java.util.ArrayList;
import java.util.Arrays;

public final class Vcs implements Visitor {
    //De unde incep stergerea in CheckOut
    private int indextodell;
    //Branchul pe care lucrez
    private String workingBranch;
    //Indexul Branchului pe care lucrezz
    private int workingBanchIndex;
    private final OutputWriter outputWriter;
    private FileSystemSnapshot activeSnapshot;
    private ArrayList<String> commands;
    //Aici am stagging
    private ArrayList<String> stagging;
    //Lista de branchuri
    private ArrayList<Branch> myBranches;
    //Mesajele ce trebuie parsate in commit
    private ArrayList<String> messagesTobeParsed;
    //Branchul ce trebuie creat in caz de checkout Branch
    private String branchNameToCreate;
    //Numele de branchuri deja adaugate
    private ArrayList<String> alreadyAddesd;
    //Branchul ce trebuie adaugat
    private String toSetWorkBanch;

    /**
     * Vcs constructor.
     *
     * @param outputWriter the output writer
     */
    public Vcs(OutputWriter outputWriter) {
        this.outputWriter = outputWriter;
        commands = new ArrayList<String>();
        this.myBranches = new ArrayList<Branch>();
        workingBranch = "master";
        messagesTobeParsed = new ArrayList<String>();
        branchNameToCreate = new String();
        alreadyAddesd = new ArrayList<String>();
        toSetWorkBanch = new String();
    }

    public OutputWriter getOutputWriter() {
        return outputWriter;
    }

    /**
     * Does initialisations.
     */
    public void init() {
        this.activeSnapshot = new FileSystemSnapshot(outputWriter);
        this.stagging = new ArrayList<String>();
        Commit firstCommit = new Commit(activeSnapshot);
        Branch firstBranch = new Branch("master");
        alreadyAddesd.add("master");
        firstBranch.getMyBranch().add(firstCommit);
        myBranches.add(firstBranch);
        workingBanchIndex = 0;
        toSetWorkBanch = "nothing";
        //TODO other initialisations
    }

    public String getToSetWorkBanch() {
        return toSetWorkBanch;
    }

    public void setToSetWorkBanch(String toSetWorkBanch) {
        this.toSetWorkBanch = toSetWorkBanch;
    }

    public void printCommands() {
        System.out.println(Arrays.toString(getCommands().toArray()));
    }

    public int getIndextodell() {
        return indextodell;
    }

    public void setIndextodell(int indextodell) {
        this.indextodell = indextodell;
    }

    public ArrayList<String> getAlreadyAddesd() {
        return alreadyAddesd;
    }

    public void setAlreadyAdded(ArrayList<String> alreadyAdded) {
        this.alreadyAddesd = alreadyAdded;
    }

    public String getBranchNames() {
        return branchNameToCreate;
    }

    public void setBranchNames(String branchNames) {
        this.branchNameToCreate = branchNames;
    }

    public FileSystemSnapshot getActiveSnapshot() {
        return activeSnapshot;
    }


    public ArrayList<Branch> getMyBranch() {
        return myBranches;
    }

    public ArrayList<String> getMessagesTobeParsed() {
        return messagesTobeParsed;
    }

    public void setMessagesTobeParsed(ArrayList<String> messagesTobeParsed) {
        this.messagesTobeParsed = messagesTobeParsed;
    }

    public String getWorkingBranch() {
        return workingBranch;
    }

    public void setWorkingBranch(String workingBranch) {
        this.workingBranch = workingBranch;
    }

    public void setMyBranch(ArrayList<Branch> myBranch) {
        this.myBranches = myBranch;
    }

    public int getWorkingBanchIndex() {
        return workingBanchIndex;
    }

    public void setWorkingBanchIndex(int workingBanchIndex) {
        this.workingBanchIndex = workingBanchIndex;
    }

    public void setActiveSnapshot(FileSystemSnapshot activeSnapshot) {
        this.activeSnapshot = activeSnapshot;
    }

    public ArrayList<String> getStagging() {
        return stagging;
    }

    public void setStagging(ArrayList<String> stagging) {
        this.stagging = stagging;
    }

    public ArrayList<String> getCommands() {
        return commands;
    }

    /**
     * Visits a file system operation.
     *
     * @param fileSystemOperation the file system operation
     * @return the return code
     */

    public int visit(FileSystemOperation fileSystemOperation) {
        return fileSystemOperation.execute(this.activeSnapshot);
    }

    /**
     * Visits a vcs operation.
     *
     * @param vcsOperation the vcs operation
     * @return return code
     */
    @Override
    public int visit(VcsOperation vcsOperation) {

        return vcsOperation.execute(this);
    }

    //TODO methods through which vcs operations interact with this
}
