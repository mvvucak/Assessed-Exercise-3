import java.awt.*;
import javax.swing.*;

/**
 * Class to define window in which attendance report is displayed.
 */
public class ReportFrame extends JFrame {
	
	/** List of classes to acquire attendance info from. */
	FitnessProgram program;
	
	/** Display of attendance info. */
	JTextArea display;
	
	/**
	 * Constructor for ReportFrame
	 * @param p the list of classes with attendance information.
	 */
	public ReportFrame(FitnessProgram p)
	{
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Attendance Report");
		setSize(700, 300);
		
		program = p;
		
		//Instantiate text area which will display report.
		display = new JTextArea();
		display.setFont(new Font("Monospaced", Font.PLAIN, 14));
		display.setEditable(false);
		add(display, BorderLayout.CENTER);
		//Create attendance report text and show it in the text area.
		display.setText(writeReport());
		
		setVisible(true);
		
	}
	
	/**
	 * Creates formatted attendance report using data from the class list.
	 * @return the attendance report for the provided list of classes.
	 */
	private String writeReport()
	{
		//Create attendance report header containing all column titles.
		String report = String.format(" %-5s %-15s %-15s %16s %30s %n %n", "Id", "Class", "Tutor" , "Attendances" , "Average Attendance");
		//Get list of all non null classes sorted by attendance.
		FitnessClass[] allClasses = program.getClassListByAttendance();
		//Iterate through list of non null classes
		for(int i = 0; i<allClasses.length; i++)
		{
			//Append attendance information to report for each class.
			report = String.format("%s%s %n", report, allClasses[i].getReportFormat());
		}
		//Append overall average attendance.
		String overallAverage = String.format("%65s %7.2f", "Overall Average:", program.getOverallAverageAttendance());
		report = String.format("%s %n %n %s", report, overallAverage);
		
		return report;
		
	}
}
