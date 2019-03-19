package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;

import java.util.ArrayList;

public class BranchOperation extends VcsOperation {

    public BranchOperation(OperationType type, ArrayList<String> operationArgs) {

        super(type, operationArgs);
    }

    /**/
    @Override
    public int execute(Vcs vcs) {
        String branchName = vcs.getBranchNames();
        ArrayList<String> aux = vcs.getAlreadyAddesd();
        if (aux.contains(branchName)) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;

        } // Cazul in care branchul deja exista
        aux.add(branchName);
        vcs.setAlreadyAdded(aux);
        Branch newBranch = new Branch(branchName); // Creez Noul Branch
        Commit newCoomit = new Commit(vcs.getActiveSnapshot());
        // Creez un commit cu snaphotul curent; ^
        newBranch.getMyBranch().add(newCoomit);
        vcs.getMyBranch().add(newBranch); // Se adauga noul Branch
        vcs.getStagging().clear();
        return ErrorCodeManager.OK;
    }
}
