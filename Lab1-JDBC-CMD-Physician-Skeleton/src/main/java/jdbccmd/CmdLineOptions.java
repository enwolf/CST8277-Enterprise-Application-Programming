/*********************************************************************************************************
 * File:  CmdLineOptions.java Course Materials CST8277
 * 
 * @author Robin Phillis
 * @author (original) Mike Norman
 * @version 1.0
 * @since 2024-09-14
 * 
 * @description This class is used to define and manage command-line options for the application. 
 *              It uses the Picocli library to specify and handle command-line arguments. 
 *              The class includes fields for various command-line options such as database URL, 
 *              username, password, and the number of random physicians to generate.
 * 
 * @see picocli.CommandLine.Option
 */
package jdbccmd;

import picocli.CommandLine.Option;

/**
 * A class that holds annotated member fields representing command-line arguments.
 * <p>
 * This class is used by Picocli to parse command-line options. It includes options for 
 * database connection details and the number of random physician records to generate.
 * </p>
 */
public class CmdLineOptions {

    /** Short option for help */
    protected static final String DASH = "-";
    /** Long option for help */
    protected static final String DASHDASH = DASH + DASH;
    /** Short option flag for help */
    protected static final String HELP_SHORTOPT = DASH + "h";
    /** Long option flag for help */
    protected static final String HELP_LONGOPT = DASHDASH + "help";
    /** Help option description */
    protected static final String HELP_USAGE = "print this message";

    /** Short option flag for username */
    protected static final String USER_SHORTOPT = DASH + "u";
    /** Long option flag for username */
    protected static final String USER_LONGOPT = DASHDASH + "username";
    /** Username option description */
    protected static final String USER_USAGE = "The DB username (default: ${DEFAULT-VALUE})";
    /** Default username */
    protected static final String USER_DEFAULT = "sa";

    /** Short option flag for password */
    protected static final String PASSW_SHORTOPT = DASH + "p";
    /** Long option flag for password */
    protected static final String PASSW_LONGOPT = DASHDASH + "password";
    /** Password option description */
    protected static final String PASSW_USAGE = "The DB password (default: ${DEFAULT-VALUE})";
    /** Default password */
    protected static final String PASSW_DEFAULT = "password";

    /** Option flag for JDBC URL */
    protected static final String JDBCURL_OPT = DASH + "url";
    /** JDBC URL option description */
    protected static final String JDBCURL_USAGE = "The DB URL";

    /** Short option flag for generate count */
    protected static final String GENCOUNT_SHORTOPT = DASH + "g";
    /** Long option flag for generate count */
    protected static final String GENCOUNT_LONGOPT = DASHDASH + "generate-count";
    /** Generate count option description */
    protected static final String GENCOUNT_USAGE = "number of random physicians to be generated (default: ${DEFAULT-VALUE})";

    /** Flag indicating whether help was requested */
    @Option(names = {HELP_SHORTOPT, HELP_LONGOPT}, usageHelp = true, description = HELP_USAGE)
    public boolean helpRequested = false;

    /** The JDBC URL for the database */
    @Option(names = {JDBCURL_OPT}, required = true, description = JDBCURL_USAGE)
    public String jdbcUrl;

    /** The username for the database connection */
    @Option(names = {USER_SHORTOPT, USER_LONGOPT}, required = false, description = USER_USAGE)
    public String username = USER_DEFAULT;

    /** The password for the database connection */
    @Option(names = {PASSW_SHORTOPT, PASSW_LONGOPT}, description = PASSW_USAGE)
    public String password = PASSW_DEFAULT;

    /** The number of random physicians to generate */
    @Option(names = {GENCOUNT_SHORTOPT, GENCOUNT_LONGOPT}, description = GENCOUNT_USAGE)
    public int count = 10;

}
