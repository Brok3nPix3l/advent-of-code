import Days.DailyChallenge;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Properties;

public class Driver {
    public static void main(String[] args) {
        String dayNumber;
        String fileSuffix = "";
        int part;
        Properties properties = parseArgs(args);
        if (properties.containsKey("dayNumber")) {
            dayNumber = properties.getProperty("dayNumber");
        } else {
            throw new RuntimeException("dayNumber is required");
        }
        if (Objects.equals(dayNumber, null)) {
            throw new RuntimeException("dayNumber is required");
        }
        if (properties.containsKey("fileSuffix")) {
            fileSuffix = properties.getProperty("fileSuffix");
        }
        if (properties.containsKey("part")) {
            part = Integer.parseInt(properties.getProperty("part"));
        } else {
            throw new RuntimeException("part is required");
        }

        DailyChallenge dailyChallenge;
        try {
            String className = "Days." + "Day" + dayNumber;
            Class<?> dayClass = Class.forName(className);
            Constructor<?> constructor = dayClass.getConstructor(File.class);
            String fileName = "day" + dayNumber + (fileSuffix.isEmpty() ? "" : "-" + fileSuffix) + ".txt";
            File inputFile = new File(Objects.requireNonNull(Driver.class.getClassLoader().getResource(fileName)).getFile());
            dailyChallenge = (DailyChallenge) constructor.newInstance(inputFile);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException |
                 InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        try {
            Method partMethod = dailyChallenge.getClass().getMethod("Part" + part);
            System.out.println(partMethod.invoke(dailyChallenge));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Properties parseArgs(String[] args) {
        Properties properties = new Properties();
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-") && args[i].length() > 1 && args.length >= i + 1) {
                properties.setProperty(args[i].substring(1), args[i + 1]);
            }
        }
        return properties;
    }
}
