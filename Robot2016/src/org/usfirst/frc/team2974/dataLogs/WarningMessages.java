package org.usfirst.frc.team2974.dataLogs;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WarningMessages {
	private static File loginFile;
	private static Path write;
	private static File dir;

	// private static long previousSize = 0;

	public static void addError(final String warning) {
		message(true, warning);
	}

	public static void addError(final String warning, final Object obj) {
		message(true, warning, obj);
	}

	public static void addWarning(final String warning) {
		message(false, warning);
	}

	public static void addWarning(final String warning, final Object obj) {
		message(false, warning, obj);
	}

	private static void deleteAll() {
		if (getDir() != null)
			deleteDir(getDir());

		else
			System.out.println(
					"Directory has not been initiated yet please insert the WarningMessages.initiateLoggerFile() method before this one");
	}

	private static void deleteDir(final File file) {
		File[] contents = file.listFiles();
		if (contents != null)
			for (File f : contents) {
				deleteDir(f);

				if (f.exists())
					System.out.println(f.getName() + " at " + f.getAbsolutePath() + " has not been deleted");

				else
					System.out.println(f.getName() + " at " + f.getAbsolutePath() + " has been deleted");
			}

		if (file.delete() && file.exists())
			System.out.println(file.getName() + " at " + file.getAbsolutePath() + " has not been deleted");

		else
			System.out.println(file.getName() + " at " + file.getAbsolutePath() + " has been deleted");
	}

	private static String getDate() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy_MM_dd"));
	}

	public static String getDateMessageStyle() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy/MM/dd"));
	}

	private static File getDir() {
		return dir;
	}

	private static File getLoginFile() {
		return loginFile;
	}

	private static Path getPath() {
		return write;
	}

	private static int getSize() {
		int size = 0;

		if (getDir() != null) {
			File[] contents = getDir().listFiles();

			if (contents != null)
				for (File f : contents)
					size += f.length();
		}

		return size;
	}

	private static String getSystemTime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
	}

	public static void initiateLoggerFile() {
		dir = new File("Logs");

		if (!dir.exists())
			if (dir.mkdir())
				System.out.println("Managed to add folder " + dir.getName() + " to " + dir.getAbsolutePath());
			else
				System.out.println("Did not manage to add directory.");
		else {
			System.out.println("Directory " + dir.getName() + " already exists");

			long dirSize = getSize();

			System.out.println("Directory size is currently " + dirSize + " bytes == " + dirSize / 1000 + " megabytes");

			if (dirSize / 1000 >= 50) {
				System.out.println("Director is " + dirSize / 1000
						+ " megabytes. The folder is too large, the folder will be purged.");
				deleteAll();
				initiateLoggerFile();
				message(true, "DIRECTORY CONGLOMERATION TOO BIG, ALL FILES WERE DESTROYED", null);
			}
		}

		loginFile = new File("Logs\\Logger".concat(getDate()).concat(".txt"));

		if (!loginFile.exists())
			try {
				if (loginFile.createNewFile())
					System.out.println("Managed to create " + loginFile.getName());
			} catch (IOException e) {
				System.out.println("Did not manage to create to create " + loginFile.getName());
			}
		else
			System.out.println(loginFile.getName() + " already exists");

		write = Paths.get("./Logs/".concat(getLoginFile().getName()));
	}

	private static void message(final boolean error, final String warning) {
		message(error, warning, null);
	}

	private static void message(final boolean error, String warning, final Object obj) {

		warning = String.format("%-11s%19s %s | %s\n", error ? "[ERROR]" : "[WARNING]", getSystemTime(),
				obj == null ? "" : obj.getClass().getName(), warning);

		try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(getPath(), CREATE, APPEND))) {
			out.write(warning.getBytes(), 0, warning.getBytes().length);
		} catch (Exception e) {
			System.out.println("Did not manage to write to " + getLoginFile().getName()
					+ " reinitializing will delete current file and create a new one, all current logs will be deleted.");
			reinitializeSequence(error, warning, obj);
		}
	}

	public static void printWarningsBetweenTime(String start) {
		File dir = new File("Logs");

		if (dir.exists()) {
			File file = new File(dir.getName().concat("\\Logger".concat(getDate()).concat(".txt")));

			if (file.exists()) {
				try (Scanner scanner = new Scanner(file)) {
					if (file.exists()) {
						boolean hasSeenStart = false;

						while (scanner.hasNext()) {
							String message = scanner.nextLine();
							if (message.contains(start))
								hasSeenStart = true;

							if (hasSeenStart)
								System.out.println(message);
						}
					}
				} catch (FileNotFoundException e) {
					System.out.println("Cannot read from file");
				}
			}
		}
	}

	public static void printWarningsBetweenTime(String start, String end) {
		File dir = new File("Logs");

		if (dir.exists()) {
			File file = new File(dir.getName().concat("\\Logger".concat(getDate()).concat(".txt")));

			if (file.exists()) {
				try (Scanner scanner = new Scanner(file)) {
					if (file.exists()) {
						boolean hasSeenStart = false;

						while (scanner.hasNext()) {
							String message = scanner.nextLine();
							if (message.contains(start))
								hasSeenStart = true;

							if (hasSeenStart)
								System.out.println(message);

							if (message.contains(end))
								hasSeenStart = false;
						}
					}
				} catch (FileNotFoundException e) {
					System.out.println("Cannot read from file");
				}
			}
		}
	}

	public static void printWarningsFromToday() {
		File dir = new File("Logs");

		if (dir.exists()) {
			File file = new File(dir.getName().concat("\\Logger".concat(getDate()).concat(".txt")));

			// ArrayList<String> warnings = new ArrayList<>();

			if (file.exists()) {
				try (Scanner scanner = new Scanner(file)) {
					if (file.exists())
						while (scanner.hasNext()) {
							String message = scanner.nextLine();
							if (message.contains(getDateMessageStyle())) {
								System.out.println(message);
								// warnings.add(message);
							}
						}

					/*
					 * make it so that the above array list prints out all the
					 * data in SmartDashboard and not using System.out.println()
					 * long size = getSize();
					 *
					 * if(previousSize != size) SmartDashboard.initTable(new
					 * Table()); previousSize = size;
					 */
				} catch (FileNotFoundException e) {
					System.out.println("Cannot read from file");
				}
			}
		}
	}

	private static void reinitializeSequence(final boolean error, final String message, final Object obj) {
		deleteDir(getLoginFile());
		initiateLoggerFile();
		message(error, message, obj);
	}
}