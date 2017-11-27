package util;

public abstract class Logger {
    abstract public void log(String msg);

    private static Logger logger;
    public static void out(String msg){
        if (logger == null) logger = new ConsoleLogger();

        logger.log(msg);
    }
    public static void setLogger(Logger loggerInstance){
        logger = loggerInstance;
    }
}
