package vcs;

import filesystem.FileSystemSnapshot;
import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;
import java.util.Iterator;

public final class CheckOutOperation extends VcsOperation {
    /**
     * Vcs operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public CheckOutOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    @Override
    public int execute(Vcs vcs) {
        int maxDeletedId = -1;
        int toCheck = 0;
        if (!vcs.getToSetWorkBanch().equals("nothing")) {
            // Operatia de checkout este de pentru a muta working Branchul
            if (!vcs.getStagging().isEmpty()) {
                vcs.setToSetWorkBanch("nothing");
                return ErrorCodeManager.VCS_STAGED_OP_CODE;
            }
            if (vcs.getAlreadyAddesd().contains(vcs.getToSetWorkBanch())) {
                vcs.setWorkingBranch(vcs.getToSetWorkBanch());
                vcs.setToSetWorkBanch("nothing");
                return ErrorCodeManager.OK;
            } else {
                vcs.setToSetWorkBanch("nothing");
                return ErrorCodeManager.VCS_BAD_CMD_CODE;
            }

        }
        if (!vcs.getStagging().isEmpty()) {
            return ErrorCodeManager.VCS_STAGED_OP_CODE;
        }

        ArrayList<Branch> fromVcs = (ArrayList<Branch>) vcs.getMyBranch().clone();
        Branch workingBranch = fromVcs.get(vcs.getWorkingBanchIndex());
        ArrayList<Commit> workingBranchCommits =
                (ArrayList<Commit>) workingBranch.getMyBranch().clone();
        for (Commit co : workingBranchCommits) {
            if (co.getIdCommit() == vcs.getIndextodell()) {
                toCheck = 1;
            }
        }
        if (toCheck == 0) {
            return ErrorCodeManager.VCS_BAD_PATH_CODE;
        }
        Iterator<Commit> it = workingBranch.getMyBranch().iterator();
        while (it.hasNext()) {
            Commit com = it.next();
            if (com.getIdCommit() > vcs.getIndextodell()) {
                workingBranchCommits.remove(com);
                if (workingBranchCommits.size() == 1) {
                    // Am un singur commit si nu vreau sa obtin size -1 = -1
                    FileSystemSnapshot c =
                            workingBranchCommits.get(0).getCommitSnapshot().cloneFileSystem();
                    vcs.setActiveSnapshot(c);
                } else {
                    int previousBranch = workingBranchCommits.size() - 1;
                    Commit commit = workingBranchCommits.get(previousBranch);
                    FileSystemSnapshot toSet = commit.getCommitSnapshot();
                    vcs.setActiveSnapshot(toSet);
                }
            }

        }

        vcs.getMyBranch().get(vcs.getWorkingBanchIndex()).setMyBranch(workingBranchCommits);
        return ErrorCodeManager.OK;
    }

    public Commit getCommit(ArrayList<Commit> branches) {
        return branches.get(0);
    }
}
