import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Maintains a list of Fitness Class objects
 * The list is initialised in order of start time
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of average attendance
 */
public class FitnessProgram {
	
	/** Maximum number of classes that can take place on a day. */
	static final int MAXIMUM_CLASSES = 7;
	/** The earliest time a class can start */
	static final int EARLIEST_START_TIME = 9;
	/** Array of FitnessClasses */
	FitnessClass[] classList;
	/** Number of classes currently used */
	private int currentClasses;
	
	/**
	 * Default constructor for FitnessProgram
	 * Populates FitnessClass with null values
	 */
	public FitnessProgram()
	{
		classList = new FitnessClass[MAXIMUM_CLASSES];
		for(int i=0; i<MAXIMUM_CLASSES;i++)
		{
			classList[i]=null;
		}
		currentClasses=0;
	}
	
	/**
	 * Adds a class to the list in the appropriate spot
	 * @param newClass the class to be added
	 */
	public void insertClass(FitnessClass newClass)
	{
		//Calculate proper position in array using start time (e.g. 9am is index 0).
		int startTime = newClass.getStartTime();
		classList[(startTime - EARLIEST_START_TIME)] = newClass;
		//Increase class counter.
		currentClasses++;
	}
	
	/**
	 * Removes a class from the array
	 * @param index the position in the array where the class to be removed is.
	 */
	public void deleteClass(int index)
	{
		classList[index] = null;
		currentClasses--;
	}
	
	/**
	 * Removes a class from the array
	 * @param id the ID of the class to be removed.
	 */
	public void deleteClass(String id)
	{
		int index = this.searchById(id);
		classList[index] = null;
		currentClasses--;
	}
	/**
	 * Searches for a class in the list using a class ID.
	 * @param id the ID of a class
	 * @return the index of the class with the provided ID (-1 if not found).
	 */
	public int searchById(String id)
	{
		//Iterate through list of classes.
		for(int i=0; i<MAXIMUM_CLASSES; i++)
		{
			try 
			{
				//Return index of class with matching id
				if(classList[i].getID().equals(id))
					{
						return i;
					}
			}
			//Prevents runtime error when a null value in the list is used
			catch(NullPointerException e)
				{
					
				}
			}
		//Return -1 if no match is found.
		return -1;
	}
	
	/**
	 * Searches for a class in the list using a class start time.
	 * @param starTime the start time of a class
	 * @return the class with the provided start time.
	 */	
	public FitnessClass searchByTime(int startTime)
	{
		return classList[startTime-EARLIEST_START_TIME];
	}
	
	/**
	 * Instantiates a class's attendance array using provided data.
	 * @param line a line from the input file containing attendance data.
	 */	
	public void populateAttendance(String line)
	{
		//Split line read from input file. Result is array of length 6 due to presence of ID.
		String [] attendanceElements = line.split(" ");
		
		//Use id from input line to find index of specific class.
		String id = attendanceElements[0];
		int index = searchById(id);
	
		//Create and populate array of attendance figures.
		int [] attendance = new int [FitnessClass.ATTENDANCE_WEEKS];
		//Iterate through rest of input line and add figures to array.
		for (int i=1; i<attendanceElements.length; i++)
		{
			attendance[i-1] = Integer.parseInt(attendanceElements[i]);
		}
		try
		{
			//Pass complete array onto FitnessClass object.
			classList[index].setAttendance(attendance);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			
		}
		
	}
	
	/**
	 * Checks whether there are any free slots left in the class list.
	 * @return whether there any available slots in the class list (true if 0 left).
	 */	
	public boolean isTimetableFull()
	{
		if(currentClasses<MAXIMUM_CLASSES)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * Finds earliest available timeslot in the class list (i.e. first index with a null value).
	 * @return start time of first null value in classList (-1 if no null values).
	 */	
	public int findEarliestSlot()
	{
		int time=-1;
		//Iterate through class list.
		for(int i=0; i<MAXIMUM_CLASSES; i++)
		{
			//Return time slot with first null value.
			if(classList[i]==null)
			{
				time=i+EARLIEST_START_TIME;
				return time;
			}
		}
		//Returns -1 if no null values are found.
		return time;
	}
	
	/**
	 * @return number of non null values in class list.
	 */	
	public int getCurrentClasses()
	{
		return currentClasses;
	}
	
	/**
	 * @param x the index of a desired class in the class list
	 * @return FitnessClass object at specified index.
	 */	
	public FitnessClass getListedClass(int x)
	{
		FitnessClass query = classList[x];
		return query;
	}
	
	/**
	 * @return the list of all fitness classes, including empty slots (null values).
	 */	
	public FitnessClass[] getClassList()
	{
		return classList;
	}
	
	/**
	 * Return class list after removing null values and sorting by attendance.
	 * @return the list of all non null fitness classes, sorted by attendance in descending order.
	 */	
	public FitnessClass[] getClassListByAttendance()
	{
		//New array with only as many elements as there are non null classes.
		FitnessClass[] temp = new FitnessClass[currentClasses];
		int tempIndex=0;
		//Iterate through class list.
		for(int j=0; j<MAXIMUM_CLASSES; j++)
		{
			//Add any non null value to the temporary array
			if(classList[j] != null)
			{
					temp[tempIndex]=classList[j];
					tempIndex++;
			}
		}
		//Sort temporary array by attendance in descending order
		Arrays.sort(temp);
		return temp;
	}
	
	/**
	 * Calculates average attendance across all classes in program
	 * @return the overall average attendance.
	 */	
	public double getOverallAverageAttendance()
	{
		double avg = 0;
		//Iterate through entire class list.
		for(int i = 0; i<MAXIMUM_CLASSES; i++)
		{
			//Add average attendance of any non null class to running total.
			if(classList[i] != null)
			{
				avg = avg + classList[i].getAverageAttendance();
			}
		}
		//Calculate average.
		avg = avg/currentClasses;
		return avg;
		
	}
	
	/**
	 * Formats a string of all class names for timetable display.
	 * @return the formatted string of class names.
	 */	
	public String getAllClassNames()
	{
		String names="";
		//Iterate through class list.
		for(int i=0; i<MAXIMUM_CLASSES; i++)
		{
			//Add class name to line for non null values.
			if(classList[i]!=null)
			{
				names = String.format("%s%-12.12s|", names, classList[i].getName());
			}
			//Otherwise add "Available" to indicate free slot.
			else
			{
				names = String.format("%s%-12.12s|", names, "Available");
			}
		}
		names=String.format("%s %n", names);
		
		return names;
	}
	
	/**
	 * Formats a string of all tutor names for timetable display.
	 * @return the formatted string of tutor names.
	 */	
	public String getAllTutors()
	{
		String tutors="";
		//Iterate through class list.
		for(int i=0; i<MAXIMUM_CLASSES; i++)
		{
			//Add tutor name to line for non null values
			if(classList[i]!=null)
			{
				tutors = String.format("%s%-12.12s|", tutors, classList[i].getTutor());
			}
			//Otherwise leave the space empty.
			else
			{
				tutors = String.format("%s%-12.12s|", tutors, " ");
			}
		}
		tutors=String.format("%s %n", tutors);
		
		return tutors;
	}
	
	

}
