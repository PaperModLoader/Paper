package xyz.papermodloader.paper.util;

import org.apache.logging.log4j.Logger;

import java.io.PrintStream;

public class LoggerPrintStream extends PrintStream {
    private Logger logger;

    public LoggerPrintStream(Logger logger, PrintStream stream) {
        super(stream);
        this.logger = logger;
    }

    public void println(boolean x) {
        this.logger.info(x);
    }

    @Override
    public void println(char x) {
        this.logger.info(x);
    }

    @Override
    public void println(int x) {
        this.logger.info(x);
    }

    @Override
    public void println(long x) {
        this.logger.info(x);
    }

    @Override
    public void println(float x) {
        this.logger.info(x);
    }

    @Override
    public void println(double x) {
        this.logger.info(x);
    }

    @Override
    public void println(char x[]) {
        this.logger.info(x);
    }

    @Override
    public void println(String x) {
        this.logger.info(x);
    }

    @Override
    public void println(Object x) {
        this.logger.info(x);
    }
}
