package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

public final class CommitOperation extends BranchOperation {

    /**
     * Vcs operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public CommitOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);

    }


    @Override
    public int execute(Vcs vcs) {
        if (vcs.getStagging().isEmpty()) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;
        }
        Branch workingBranch = vcs.getMyBranch().get(vcs.getWorkingBanchIndex());
        Commit toAdd = new Commit(vcs.getActiveSnapshot());
        toAdd.setCommitSnapshot(vcs.getActiveSnapshot().cloneFileSystem());
        toAdd.setParrsedString(vcs.getMessagesTobeParsed().get(0));
        vcs.getMessagesTobeParsed().remove(0);
        workingBranch.getMyBranch().add(toAdd);
        vcs.getStagging().clear();
        return ErrorCodeManager.OK;
    }
}

