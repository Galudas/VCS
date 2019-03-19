package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

public final class LogOperation extends VcsOperation {
    /**
     * Vcs operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public LogOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    @Override
    public int execute(Vcs vcs) {
        Branch myBranch = vcs.getMyBranch().get(vcs.getWorkingBanchIndex());
        int sizeBranch = myBranch.getMyBranch().size();
        int counter = 1;
        for (Commit com : myBranch.getMyBranch()) {
            vcs.getOutputWriter().write("Commit id: " + com.getIdCommit() + "\n");
            vcs.getOutputWriter().write("Message:" + com.getParrsedString() + "\n");
            if (counter != sizeBranch) {
                vcs.getOutputWriter().write("\n");
            }
            counter++;
        }
        return ErrorCodeManager.OK;
    }
}
