package utilidades;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogErrores {

	private static LogErrores	instance;

	private File				file;
	private BufferedWriter		writer;

	private LogErrores() {
		try {
			this.file = new File(
					"data/errores/" + new SimpleDateFormat("dd-MM-yyyy")
							.format(Calendar.getInstance().getTime()) + ".txt");
			if (!this.file.exists()) {
				this.file.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeLog(final String classname, final String method,
			final String details) {
		try {
			this.writer = new BufferedWriter(new FileWriter(file, true));
			this.writer.write(new SimpleDateFormat("[HH:mm:ss]")
					.format(Calendar.getInstance().getTime())
					+ " -->\tClase:\t\t" + classname + "\n\t\t\t\tFunción:\t"
					+ method + "\n\t\t\t\tDetalles:\t" + details + "\n\n");
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static LogErrores getInstance() {
		if (instance == null) {
			instance = new LogErrores();
		}
		return instance;
	}
}