package org.usfirst.frc.team2974.dataLogs;

/**
 * This class used is a utility class that allows you to log information such
 * as: errors, warnings and actions the code will give
 */
public class Message {

	/**
	 * this method allows you to log a specific action to the Action Logs.txt
	 * file in the Logs directory on the desktop
	 *
	 * @param message
	 *            that string message that will be recorded in a given file
	 */
	public static void addAction(final String message) {
		addAction(message, null);
	}

	/**
	 * this method allows you to log a specific action to the Action Logs.txt
	 * file and from which class the method was called (this information is
	 * usually entered using "this" as the parameter for the object variable) to
	 * the Logs directory on the desktop
	 *
	 * @param message
	 *            that string message that will be recorded in a given file
	 * @param object
	 *            gives more information to the user by telling him/her where
	 *            the method was executed
	 */
	public static void addAction(final String message, final Object object) {
		addMessage("[ACTION]", message, object, "Action Logs");
	}

	/**
	 * this method allows you to log a specific error to the Error Logs.txt file
	 * in the Logs directory on the desktop
	 *
	 * @param message
	 *            that string message that will be recorded in a given file
	 */
	public static void addError(final String message) {
		addError(message, null);
	}

	/**
	 * this method allows you to log a specific error to the Error Logs.txt file
	 * and from which class the method was called (this information is usually
	 * entered using "this" as the parameter for the object variable) to the
	 * Logs directory on the desktop
	 *
	 * @param message
	 *            that string message that will be recorded in a given file
	 * @param object
	 *            gives more information to the user by telling him/her where
	 *            the method was executed
	 */
	public static void addError(final String message, final Object object) {
		addMessage("[ERROR]", message, object, "Error Logs");
	}

	/**
	 * This method creates file with the given file name and writes the
	 * Information in a specific format with the time and the class information
	 * if given.
	 *
	 * @param what
	 *            tells the user what kind of log the message is Ex: [ERROR],
	 *            [WARNING] or [ACTION]
	 * @param message
	 *            that string message that will be recorded in a given file
	 * @param object
	 *            gives more information to the user by telling him/her where
	 *            the method was executed
	 * @param fileName
	 *            decides what the file that will be created will be named
	 */
	private static void addMessage(final String what, String message, final Object object, final String fileName) {
		final java.io.File file = FileHelper
                .create(FileHelper.formatFilePath(System.getProperty("user.home").concat("\\Desktop\\Logs\\Log")
                        .concat(getSystemTime("yyyy_MM_dd")).concat("\\".concat(fileName)).concat(".txt")));

		if (/*
			 * is removed for now but if helpful uncomment this piece
			 * !FileHelper.contains(message, file) &&
			 */!message.trim().isEmpty()) {
			message = String.format("%-9s %19s %s | %s\n", what, getSystemTime("yyyy/MM/dd HH:mm:mm"),
					object == null ? "" : object.getClass().getName(), message);

			FileHelper.writeToFile(message, file);

			System.out.println(message.replaceAll("\n", ""));
		}
	}

	/**
	 * this method allows you to log a specific warning to the Warning Logs.txt
	 * file in the Logs directory on the desktop
	 *
	 * @param message
	 *            that string message that will be recorded in a given file
	 */
	public static void addWarning(final String message) {
		addWarning(message, null);
	}

	/**
	 * this method allows you to log a specific warning to the Warning Logs.txt
	 * file and from which class the method was called (this information is
	 * usually entered using "this" as the parameter for the object variable) to
	 * the Logs directory on the desktop
	 *
	 * @param message
	 *            that string message that will be recorded in a given file
	 * @param object
	 *            gives more information to the user by telling him/her where
	 *            the method was executed
	 */
	public static void addWarning(final String message, final Object object) {
		addMessage("[WARNING]", message, object, "Warning Logs");
	}

	/**
	 * <p>
	 * This method return the system time in a specific format such as:
	 * yyyy/MM/dd HH:mm:ss or yyyy_MM_dd.
	 * </p>
	 * <p>
	 * <br>G = Era designator 
	 * <br>y = Year
	 * <br>Y = Week year
	 * <br>M = Month in year
	 * <br>w = Week in year
	 * <br>W = Week in month
	 * <br>D = Day in year
	 * <br>d = Day in month
	 * <br>F = Day of week in month
	 * <br>E = Day name in week
	 * <br>u = Day number of week (1 = Monday, ..., 7 = Sunday)
	 * <br>a = Am/pm marker
	 * <br>H = Hour in day (0-23)
	 * <br>k = Hour in day (1-24)
	 * <br>h = Hour in am/pm (1-12)
	 * <br>m = Minute in hour
	 * <br>s = Secnd in minute
	 * <br>S = Milisecond
	 * <br>z = Time zone Ex: Pacific Standard Time; PST; GMT-08:00 
	 * <br>Z = Time zone Ex: -0800 
	 * <br>X = Time zone Ex: -08; -0800; -08:00 
	 * </p>
	 * 
	 * @param pattern
	 *            is the parameter that will format the time to be shown a
	 *            specific way
	 * @return the time given in a certain format
	 */
	private static String getSystemTime(final String pattern) {
		return new java.text.SimpleDateFormat(pattern).format(new java.util.Date(System.currentTimeMillis()));
	}
}