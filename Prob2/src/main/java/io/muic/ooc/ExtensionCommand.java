package io.muic.ooc;


import org.apache.commons.cli.*;
import java.io.File;
import java.io.IOException;


public class ExtensionCommand {
    public void run(String[] args) throws IOException {

        // create the parser
        CommandLineParser parser = new DefaultParser();
        Options options = makeOpts();
        HelpFormatter formatter = new HelpFormatter();

            try {
                // parse the command line arguments
                CommandLine cmdline = parser.parse(options, args);

                Walker w = new Walker();
                if(!cmdline.hasOption('f') || cmdline.hasOption("help")){
                    formatter.printHelp( "ant", options );
                    return;
                }
                else{
                    String pathName = cmdline.getOptionValue('f');
                    File path = new File(pathName);// /Users/poon./prg/ooc/hw/a1/prob1
                    w.start(path);
                }

                if (cmdline.hasOption('a')) {
                    System.out.println("Number of files: " + w.getFileNumber());
                }
                if (cmdline.hasOption('b')) {
                    System.out.println("Number of directories: " + w.getDirNumber());
                }
                if (cmdline.hasOption('c')) {
                    System.out.println("Number of unique file extensions: " + w.getExtensionNumber());
                }
                if (cmdline.hasOption('d')) {
                    w.listAllUnique();
                }
                if (cmdline.hasOption("num-ext")) {

                    String EXT = cmdline.getOptionValue("num-ext");
                    int numSpesificExtension = w.getSpesificExtension(EXT);
                    if (numSpesificExtension != -1)
                        System.out.printf("There are %d .%s file \n", numSpesificExtension, EXT);
                    else
                        System.out.printf("There is no .%s extension. \n", EXT);
                }


            } catch (ParseException exp) {

                System.err.println("Parsing failed.  Reason: " + exp.getMessage());
            }


    }


    public static Options makeOpts() {

        Options options = new Options();

        options.addOption("a", "total-num-files", false, "The total number of files .");
        options.addOption("b", "total-num-dirs", false, "Total number of directory .");
        options.addOption("c", "total-unique-exts", false, "Total number of unique file extensions .");
        options.addOption("d", "list-exts", false, "List all unique file extensions. Do not list duplicates.");
        options.addOption(Option.builder()
                .longOpt("num-ext")
                .hasArgs()
                .argName("EXT")
                .valueSeparator('=')
                .build());
        options.addOption(Option.builder("f")
                .desc("Path to the documentation folder. This is a required argument.")
                .hasArg()
                .argName("path-to-folder")
                .valueSeparator('=')
                .build());
        return options;
    }
}
