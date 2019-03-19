package utils;

import vcs.Vcs;

import java.util.ArrayList;
import java.util.Arrays;

public final class Context {
    private Vcs vcs;
    private static Context instance = null;
    private InputReader inputReader;
    private OutputWriter outputWriter;

    /**
     * Context constructor.
     */
    private Context() {
    }

    /**
     * Gets the instance.
     *
     * @return the instance
     */
    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }

        return instance;
    }

    /**
     * Initialise the vcs.
     *
     * @param input  the input file
     * @param output the output file
     */
    public void init(String input, String output) {
        inputReader = new InputReader(input);
        outputWriter = new OutputWriter(output);
        vcs = new Vcs(outputWriter);
    }

    /**
     * Runs the context.
     */
    public void run() {
        String operationString = "";
        AbstractOperation operation;
        OperationParser parser = new OperationParser();
        int exitCode;
        boolean wasError;
        final int commandType = 0;
        final int file = 1;
        final int textAdded = 2;
        final int three = 3;
        this.vcs.init();

        while (true) {
            operationString = this.inputReader.readLine();
            if (operationString == null || operationString.isEmpty()) {
                continue;
            }
            if (operationString.equals("exit")) {
                return;
            }

            operation = parser.parseOperation(operationString);
            //Eu parses totul in vcs
            if (String.valueOf(operation.getType()).contains("BRANCH")) {
                ArrayList<String> operationArgs
                        = new ArrayList<>(Arrays.asList(operationString.split("\\s+")));
                vcs.setBranchNames(operationArgs.get(2)); //Numele Brancului ce trebuie creat

            } else if (String.valueOf(operation.getType()).contains("COMMIT")) {
                ArrayList<String> operationArgs
                        = new ArrayList<>(Arrays.asList(operationString.split("-m")));
                vcs.getMessagesTobeParsed().add(operationArgs.get(1));
                // Mesajul ce trebuie pus in fiecare Commit
            } else if (String.valueOf(operation.getType()).contains("CHECKOUT")) {
                ArrayList<String> operationArgs
                        = new ArrayList<>(Arrays.asList(operationString.split("\\s+")));
                if (operationArgs.contains("-c")) {
                    vcs.setIndextodell(Integer.valueOf(operationArgs.get(three)));
                    // Cazul in care se sterg commituri
                } else {
                    vcs.setToSetWorkBanch(String.valueOf(operationArgs.get(2)));
                    //Se schimba workingBranch
                }
            }

            exitCode = operation.accept(vcs);
            wasError = ErrorCodeManager.getInstance().checkExitCode(outputWriter, exitCode);

            if (!wasError && this.isTrackable(operation)) {
                String toSend = new String();
                toSend += String.valueOf(operation.getType());
                toSend += " ";
                for (String arg : operation.getOperationArgs()) {
                    toSend += arg;
                    toSend += " ";
                }
                String toPrintOut = new String();
                String[] commandTokenized = toSend.split("\\s+");
                // In continuare, creez stringul ce trebuie parsat in vcsStagged
                if (commandTokenized[commandType].equals("TOUCH")) {
                    toPrintOut += "\tCreated file ";
                    toPrintOut += commandTokenized[file];
                    toPrintOut += "\n";
                }
                if (commandTokenized[commandType].equals("WRITETOFILE")) {
                    toPrintOut += "\tAdded " + "\"";
                    toPrintOut += commandTokenized[textAdded];
                    toPrintOut += "\"";
                    toPrintOut += " to file ";
                    toPrintOut += commandTokenized[file];
                    toPrintOut += "\n";
                }
                if (commandTokenized[commandType].equals("MAKEDIR")) {
                    toPrintOut += "\tCreated directory ";
                    toPrintOut += commandTokenized[file];
                    toPrintOut += "\n";
                }
                if (commandTokenized[commandType].equals("CHANGEDIR")) {
                    toPrintOut += "\tCreated directory to ";
                    toPrintOut += commandTokenized[file];
                    toPrintOut += "\n";
                }
                if (commandTokenized[commandType].equals("REMOVE")) {
                    toPrintOut += "\tRemoved ";
                    toPrintOut += commandTokenized[file];
                    toPrintOut += "\n";
                }

                vcs.getStagging().add(toPrintOut);

            }

        }

    }

    /**
     * Specifies if an operation is vcs trackable or not.
     * You can use it when you implement rollback/checkout -c functionalities.
     *
     * @param abstractOperation the operation
     * @return whether it's trackable or not
     */
    private boolean isTrackable(AbstractOperation abstractOperation) {
        boolean result;

        switch (abstractOperation.type) {
            case CHANGEDIR:
            case MAKEDIR:
            case REMOVE:
            case TOUCH:
            case WRITETOFILE:
                result = true;
                break;
            default:
                result = false;
        }

        return result;
    }
}
