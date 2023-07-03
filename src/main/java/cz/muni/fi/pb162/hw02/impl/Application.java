package cz.muni.fi.pb162.hw02.impl;

import com.beust.jcommander.Parameter;
import cz.muni.fi.pb162.hw02.Messages;
import cz.muni.fi.pb162.hw02.TerminalOperation;
import cz.muni.fi.pb162.hw02.cmd.CommandLine;
import cz.muni.fi.pb162.hw02.cmd.TerminalOperationConverter;

import java.io.IOException;

/**
 * Application class represents the command line interface of this application.
 *
 * @author Petr Valik
 */
public class Application {

    @Parameter(names = {"-u"})
    private boolean unique;

    @Parameter(names = {"-s"})
    private boolean sort;

    @Parameter(names = {"-d"})
    private boolean duplicate;

    @Parameter(names = "--help", help = true)
    private boolean showUsage = false;

    @Parameter(converter = TerminalOperationConverter.class)
    private TerminalOperation terminalOperation = TerminalOperation.LINES;

    @Parameter(names = {"--file"}, required = true)
    private String path;

    /**
     * Application entry point
     *
     * @param args command line arguments of the application
     */
    public static void main(String[] args) {
        Application app = new Application();

        CommandLine cli = new CommandLine(app);
        cli.parseArguments(args);

        if (app.showUsage) {
            cli.showUsage();
        } else {
            app.run(cli);
        }
    }

    /**
     * Application runtime logic
     *
     * @param cli command line interface
     */
    private void run(CommandLine cli) {
        try {
            Operations linesList = new Operations(path);
            if (unique && duplicate) {
                System.err.println(Messages.INVALID_OPTION_COMBINATION);
                cli.showUsage();
            }
            if (unique) {
                linesList.unique();
            }
            if (sort) {
                linesList.sort();
            }
            if (duplicate) {
                linesList.duplicates();
            }
            if (TerminalOperation.COUNT != terminalOperation &&
                    TerminalOperation.SIZES != terminalOperation &&
                    TerminalOperation.SIMILAR != terminalOperation &&
                    TerminalOperation.LINES != terminalOperation) {
                linesList.print();
            }
            if (TerminalOperation.COUNT == terminalOperation) {
                linesList.count();
            }
            if (TerminalOperation.SIZES == terminalOperation) {
                linesList.sizes();
            }
            if (TerminalOperation.SIMILAR == terminalOperation) {
                linesList.similar();
            }
            if (TerminalOperation.LINES == terminalOperation){
                linesList.print();
            }
        } catch (IllegalArgumentException | IOException ex) {
            System.err.printf(Messages.IO_ERROR, path);
            System.err.println();
            cli.showUsage();
        }

    }
}
