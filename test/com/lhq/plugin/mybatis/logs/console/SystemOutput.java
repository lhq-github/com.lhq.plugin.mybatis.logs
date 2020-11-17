package com.lhq.plugin.mybatis.logs.console;

public class SystemOutput implements Output {

    private SystemOutput() {
    }

    public static Output output = new SystemOutput();

    @Override
    public void print(String message) {
        System.out.print(message);
    }

    @Override
    public void println(String message) {
        System.out.println(message);
    }

}
