package org.example;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        var options = new Options();
        options.addOption("h", "help", false, "Print this message");
        options.addOption(Option.builder("in")
                .argName("jawiki.xml.bz2")
                .hasArg()
                .desc("The path to the jawiki.xml.bz2 file")
                .build());
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("in")) {
                var path = cmd.getOptionValue("in");
                if (path != null) {
                    var bz2File = new File(path);
                    new Parser(bz2File).run();
                    return;
                }
            }
            printHelp(options);
        } catch (ParseException e) {
            printHelp(options);
        }
    }

    private static void printHelp(Options options) {
        var helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("jawiki-importer", options);
    }
}