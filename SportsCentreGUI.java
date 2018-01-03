import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

/**
 * Defines a GUI that displays details of a FitnessProgram object
 * and contains buttons enabling access to the required functionality.
 */
public class SportsCentreGUI extends JFrame implements ActionListener {
	
	/** GUI JButtons */
	private JButton closeButton, attendanceButton;
	private JButton addButton, deleteButton;

	/** GUI JTextFields */
	private JTextField idIn, classIn, tutorIn;

	/** Display of class timetable */
	private JTextArea display;

	/** Display of attendance information */
	private ReportFrame report;

	/** Names of input text files */
	private final String classesInFile = "ClassesIn.txt";
	private final String classesOutFile = "ClassesOut.txt";
	private final String attendancesFile = "AttendancesIn.txt";
	
	/** List to contain and manage classes **/
	private FitnessProgram program;
	
	/**
	 * Constructor for AssEx3GUI class
	 */
	public SportsCentreGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Boyd-Orr Sports Centre");
		setSize(700, 300);
		display = new JTextArea();
		display.setFont(new Font("Monospaced", Font.PLAIN, 14));
		display.setEditable(false);
		add(display, BorderLayout.CENTER);
		layoutTop();
		layoutBottom();
		program = new FitnessProgram();
		initLadiesDay();
		updateDisplay();
		setVisible(true);
	}

	/**
	 * Creates the FitnessProgram list ordered by start time
	 * using data from the file ClassesIn.txt
	 */
	public void initLadiesDay() {
		try
		{
			FileReader r = new FileReader(classesInFile);
			Scanner s = new Scanner(r);
			while(s.hasNextLine())
			{
				String line = s.nextLine();
				program.insertClass(new FitnessClass(line));
			}
			
			s.close();
			r.close();
		}
		catch(FileNotFoundException e)
		{
			System.err.println("File Not Found");
		}
		catch(IOException e)
		{
			
		}
	    
	    
	}

	/**
	 * Initialises the attendances using data
	 * from the file AttendancesIn.txt
	 */
	public void initAttendances() {
	    try
	    {
	    	FileReader r = new FileReader(attendancesFile);
			Scanner s = new Scanner(r);
			while(s.hasNextLine())
			{
				String line = s.nextLine();
				program.populateAttendance(line);
			}
			s.close();
			r.close();
	    }
	    catch(FileNotFoundException e)
	    {
	    	System.err.println("File Not Found");
	    }
	    catch(IOException e)
	    {
	    	
	    }
	}

	/**
	 * Instantiates timetable display and adds it to GUI
	 */
	public void updateDisplay() {
		//Can make this more fancy, perhaps using for loop and earliest start time and maximum classes constants)
		String timeSlots = String.format("%-12s|%-12s|%-12s|%-12s|%-12s|%-12s|%-12s|%n", "9-10", "10-11","11-12","12-13","13-14","14-15", "15-16");
		String classNames = program.getAllClassNames();
		String tutors = program.getAllTutors();
	    
		display.setText(timeSlots);
		display.append(classNames);
		display.append(tutors);
		
		
	}

	/**
	 * adds buttons to top of GUI
	 */
	public void layoutTop() {
		JPanel top = new JPanel();
		closeButton = new JButton("Save and Exit");
		closeButton.addActionListener(this);
		top.add(closeButton);
		attendanceButton = new JButton("View Attendances");
		attendanceButton.addActionListener(this);
		top.add(attendanceButton);
		add(top, BorderLayout.NORTH);
	}

	/**
	 * adds labels, text fields and buttons to bottom of GUI
	 */
	public void layoutBottom() {
		// instantiate panel for bottom of display
		JPanel bottom = new JPanel(new GridLayout(3, 3));

		// add upper label, text field and button
		JLabel idLabel = new JLabel("Enter Class Id");
		bottom.add(idLabel);
		idIn = new JTextField();
		bottom.add(idIn);
		JPanel panel1 = new JPanel();
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		panel1.add(addButton);
		bottom.add(panel1);

		// add middle label, text field and button
		JLabel nmeLabel = new JLabel("Enter Class Name");
		bottom.add(nmeLabel);
		classIn = new JTextField();
		bottom.add(classIn);
		JPanel panel2 = new JPanel();
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		panel2.add(deleteButton);
		bottom.add(panel2);

		// add lower label text field and button
		JLabel tutLabel = new JLabel("Enter Tutor Name");
		bottom.add(tutLabel);
		tutorIn = new JTextField();
		bottom.add(tutorIn);

		add(bottom, BorderLayout.SOUTH);
	}

	/**
	 * Processes adding a class
	 */
	public void processAdding(String id, String name, String tutor) 
	{
	    int time = program.findEarliestSlot();
	    if(time!=-1)//Not sure if the if statement is needed, already taken care of by validation.
	    {
	    	program.insertClass(new FitnessClass(id, name, tutor, time, true));
	    }   
	}

	/**
	 * Processes deleting a class
	 */
	public void processDeletion(String id) 
	{
	    int index = program.searchById(id);
	    program.deleteClass(index);
	}

	/**
	 * Instantiates a new window and displays the attendance report
	 */
	public void displayReport() 
	{
	    // your code here
	}

	/**
	 * Writes lines to file representing class name, 
	 * tutor and start time and then exits from the program
	 */
	public void processSaveAndClose() 
	{
		try
		{
			FileWriter w = new FileWriter(classesOutFile);
			for(int i = 0; i<program.MAXIMUM_CLASSES; i++)
			{
				FitnessClass current = program.getListedClass(i);
				if(current != null)
				{
					String id = current.getID();
					String name = current.getName();
					String tutor = current.getTutor();
					int time = current.getStartTime();
					String outputLine = String.format("%s %s %s %d %n", id, name, tutor, time);
					w.write(outputLine);
				}
				
			}
			w.close();
			
		}
	    catch(IOException e)
		{
	    	
		}
	    
	}

	/**
	 * Process button clicks.
	 * @param ae the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {
	    if(ae.getSource() == addButton)
	    {
	    	String id = idIn.getText();
	    	String name = classIn.getText();
	    	String tutor = tutorIn.getText();
	    	if(validateAddition(id, name, tutor))
	    	{
	    			processAdding(id, name, tutor);
	    			updateDisplay();
	    			resetTextFields();   			
	    	}
	    }
	    else if(ae.getSource() == deleteButton)
	    {
	    	String id = idIn.getText();
	    	if(validateDeletion(id))
	    	{
	    		processDeletion(id);
	    		updateDisplay();
    			resetTextFields();
	    	}
	    }
	    else if(ae.getSource() == closeButton)
	    {
	    	processSaveAndClose();
	    	this.dispose();
	    }
	}
	
	
	
	private boolean validateDeletion(String id)
	{
		if(!validateInput(id, "ID"))
		{
			return false;
		}
		else if(program.searchById(id) == -1)
		{
			JOptionPane.showMessageDialog(null, "The ID you entered does not match any class. Plase enter a valid ID.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else
		{
			return true;
		}
	}
	
	private boolean validateAddition(String id, String name, String tutor)
	{
		if(program.isTimetableFull())
		{
			JOptionPane.showMessageDialog(null, "The timetable is full. Please delete a class before adding a new one.", "Timetable Full", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(!validateInput(id, "ID"))
		{
			return false;
		}
		else if(program.searchById(id) != -1)
		{
			JOptionPane.showMessageDialog(null, "The ID already exists. Please enter a different class ID.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(!validateInput(name, "class name"))
		{
			return false;
		}
		else if(!validateInput(tutor, "tutor name"))
		{
			return false;
		}
		else
		{
			return true;
		}
		
	}
	
	private boolean validateInput(String input, String type)
	{
		if(input.isEmpty() || input.trim().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "The "+ type +" cannot be empty.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(input.split(" ").length>1)
		{
			JOptionPane.showMessageDialog(null, "The "+ type +" must be one word long (no spaces).", "Invalid Input", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else
		{
			return true;
		}		
	}

	private void resetTextFields()
	{
		idIn.setText("");
		classIn.setText("");
		tutorIn.setText("");
	}
	
	public static void main(String[] args)
	{
		SportsCentreGUI test = new SportsCentreGUI();
	}
}
