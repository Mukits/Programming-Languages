package uk.ac.aston.oop.uml.diagrams;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import net.sourceforge.plantuml.BlockUml;
import net.sourceforge.plantuml.ErrorUml;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceFileReader;
import net.sourceforge.plantuml.core.Diagram;
import net.sourceforge.plantuml.error.PSystemError;

/**
 * Short program that uses PlantUML to generate PNG and StarUML-oriented XMI
 * files from all .txt files in the <code>plantuml</code> folder of this
 * project.
 */
public class DiagramGenerator {

	public static class InvalidDiagramException extends Exception {
		private static final long serialVersionUID = 1L;

		public InvalidDiagramException(String message) {
			super(message);
		}
	}

	private final File targetFolder;

	public DiagramGenerator(File targetFolder) {
		this.targetFolder = targetFolder;
	}

	public void processFolder(File sourceFolder) {
		// Clear the target folder first
		for (File f : targetFolder.listFiles()) {
			f.delete();
		}

		// Process all the .txt files in the source folder
		File[] sourceFiles = sourceFolder.listFiles((dir, name) -> name.endsWith(".txt"));
		Arrays.sort(sourceFiles, (a, b) -> a.getName().compareTo(b.getName()));
		for (File f : sourceFiles) {
			System.out.println("==> PROCESSING " + f.getName());
			try {
				processFile(f, FileFormat.XMI_STAR, FileFormat.PNG);
				System.out.println("  * OK");
			} catch (IOException | InvalidDiagramException e) {
				e.printStackTrace();
			}
		}
	}

	public void processFile(File sourceFile, FileFormat... formats) throws IOException, InvalidDiagramException {
		SourceFileReader reader = new SourceFileReader(sourceFile, targetFolder);
		validate(reader);
		for (FileFormat f : formats) {
			generate(reader, f);
		}
	}

	protected void generate(SourceFileReader reader, FileFormat fmt) throws IOException {
		reader.setFileFormatOption(new FileFormatOption(fmt));
		reader.getGeneratedImages();
	}

	protected void validate(SourceFileReader reader) throws InvalidDiagramException {
		for (BlockUml b : reader.getBlocks()) {
			Diagram diagram = b.getDiagram();
			if (diagram instanceof PSystemError) {
				StringBuilder sb = new StringBuilder();
				PSystemError pError = (PSystemError) diagram;
				for (ErrorUml err : pError.getErrorsUml()) {
					sb.append(String.format("Error at %s:%d: %s\n\n",
						err.getLineLocation(), err.getPosition(),
						err.getError()));
				}

				throw new InvalidDiagramException(sb.toString());
			}
		}
	}

	public static void main(String[] args) {
		File targetFolder = new File("diagrams").getAbsoluteFile();
		targetFolder.mkdir();

		DiagramGenerator g = new DiagramGenerator(targetFolder);
		g.processFolder(new File("plantuml"));
	}

}
