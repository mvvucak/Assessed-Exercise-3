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
		//Create text area for timetable display
		display = new JTextArea();
		display.setFont(new Font("Monospaced", Font.PLAIN, 14));
		display.setEditable(false);
		add(display, BorderLayout.CENTER);
		
		layoutTop();
		layoutBottom();
		//Create Fitness Program isntance using default constructor.
		program = new FitnessProgram();
		//Populate Fitness Program
		initLadiesDay();
		initAttendances();
		//Add timetable to text area.
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
			//Iterate through classes input file.
			while(s.hasNextLine())
			{
				//Instantiate fitness class for each line and add to program.
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
			//Iterate through attendance input file.
			while(s.hasNextLine())
			{
				//Input attendance for each class in file.
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
		//Create header for timetable display, containing all timeslots
		String timeSlots = String.format("%-12s|%-12s|%-12s|%-12s|%-12s|%-12s|%-12s|%n", "9-10", "10-11","11-12","12-13","13-14","14-15", "15-16");
		//Create second line of timetable display, containing all class names.
		String classNames = program.getAllClassNames();
		//Create final line of timetable display, containing all tutor names.
		String tutors = program.getAllTutors();
	    
		//Add timetable to text area.
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
	    report = new ReportFrame(program);
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
			//Iterate through class list
			for(int i = 0; i < FitnessProgram.MAXIMUM_CLASSES; i++)
			{
				//If class is not a null value, write its details to the file.
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
			this.dispose();
			
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
		//If adding a class
	    if(ae.getSource() == addButton)
	    {
	    	//Grab all input and validate, then process addition.
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
	    //If deleting a class
	    else if(ae.getSource() == deleteButton)
	    {
	    	//Grab ID input, validate and process deletion.
	    	String id = idIn.getText();
	    	if(validateDeletion(id))
	    	{
	    		processDeletion(id);
	    		updateDisplay();
    			resetTextFields();
	    	}
	    }
	    //If displaying attendance report.
	    else if(ae.getSource() == attendanceButton)
	    {
	    	displayReport();
	    }
	    //If closing the program.
	    else if(ae.getSource() == closeButton)
	    {
	    	processSaveAndClose();
	    	
	    }
	}
	
	/** 
	 * Checks to see if ID input by user is valid (not empty, only 1 word, matches existing class)
	 * Invalid ID results in error dialog being output.
	 * @param id, the String ID of an existing class input by the user.
	 * @return Whether the ID is valid. False if not. 
	 */
	private boolean validateDeletion(String id)
	{
		if(!validateInput(id, "ID"))
		{
			return false;
		}
		//Check if ID actually exists. If not, output error dialog.
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
	
	/** 
	 * Checks to see if ID, class name and tutor name input by user are valid (not empty, only 1 word).
	 * Also checks if there is an available time slot and if class with specified ID already exists.
	 * Error dialogs if inputs are invalid or no timeslots available.
	 * @param id the id of a new class
	 * @param name the name of a new class
	 * @param tutor the name of the tutor teaching the new class
	 * @return Whether the inputs are valid, non duplicate and there is a timeslot available.
	 */
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
		//Check if ID is already taken. If yes, output error dialog.
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
		//Return true if all conditions are met.
		else
		{
			return true;
		}
		
	}
	
	/** 
	 * Checks whether provided input is empty or more than one word long.
	 * @param input the specific String to be validated
	 * @param the type of input, for accurate error dialogs (ID, class name or tutor name).
	 * @return Whether the given input is not empty and only one word.
	 */
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
	
	/** 
	 * Wipes all text fields so new information can be entered.
	 */
	private void resetTextFields()
	{
		idIn.setText("");
		classIn.setText("");
		tutorIn.setText("");
	}
	
	
}
