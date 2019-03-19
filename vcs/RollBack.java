package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

public final class RollBack extends VcsOperation {
    public RollBack(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    @Override
    public int execute(Vcs vcs) {
        vcs.getStagging().clear();

        ArrayList<Branch> branch = vcs.getMyBranch();
        Branch workingBranch = branch.get(vcs.getWorkingBanchIndex());
        ArrayList<Commit> commitForm = workingBranch.getMyBranch();
        vcs.setActiveSnapshot(commitForm.get(commitForm.size() - 1).getCommitSnapshot());
        return ErrorCodeManager.OK;
    }
}
