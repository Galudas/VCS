package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;
import java.util.ArrayList;

public final class Status extends VcsOperation {
    public Status(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    @Override
    public int execute(Vcs vcs) {
        vcs.getOutputWriter().write("On branch: " + vcs.getWorkingBranch() + "\n");
        vcs.getOutputWriter().write("Staged changes:\n");
        for (int i = 0; i < vcs.getStagging().size(); i++) {
            vcs.getOutputWriter().write(vcs.getStagging().get(i));
        }
        return ErrorCodeManager.OK;
    }
}
