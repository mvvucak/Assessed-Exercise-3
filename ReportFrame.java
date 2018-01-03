import java.awt.*;
import javax.swing.*;

/**
 * Class to define window in which attendance report is displayed.
 */
public class ReportFrame extends JFrame {
	
	FitnessProgram program;
	
	JTextArea display;
	
	public ReportFrame(FitnessProgram p)
	{
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Attendance Report");
		setSize(700, 300);
		
		program = p;
		
		display = new JTextArea();
		display.setFont(new Font("Monospaced", Font.PLAIN, 14));
		display.setEditable(false);
		add(display, BorderLayout.CENTER);
		display.setText(writeReport());
		
		setVisible(true);
		
		
		
		
		
	}
	
	private String writeReport()
	{
		String report = String.format(" %-5s %-15s %-15s %16s %30s %n %n", "Id", "Class", "Tutor" , "Attendances" , "Average Attendance");
		FitnessClass[] allClasses = program.getClassListByAttendance();
		
		for(int i = 0; i<allClasses.length; i++)
		{
			report = String.format("%s%s %n", report, allClasses[i].getReportFormat());
		}
		
		return report;
		
	}
        // your code here
}
