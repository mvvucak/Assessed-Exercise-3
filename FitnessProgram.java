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
	
	static final int MAXIMUM_CLASSES = 7;
	static final int EARLIEST_START_TIME = 9;
	FitnessClass[] classList;
	private int currentClasses;
	
	public FitnessProgram()
	{
		classList = new FitnessClass[MAXIMUM_CLASSES];
		for(int i=0; i<MAXIMUM_CLASSES;i++)
		{
			classList[i]=null;
		}
		currentClasses=0;
	}
	
	public void insertClass(FitnessClass newClass)
	{
		int startTime = newClass.getStartTime();
		classList[(startTime - EARLIEST_START_TIME)] = newClass;
		currentClasses++;
		//Needs code to check if time slot is already taken, possibly throw an exception if so.
	}
	
	public void deleteClass(int index)
	{
		classList[index] = null;
		currentClasses--;
	}
	
	public void deleteClass(String id)
	{
		int index = this.searchById(id);
		classList[index] = null;
		currentClasses--;
	}
	
	public int searchById(String id)
	{
			for(int i=0; i<MAXIMUM_CLASSES; i++)
			{
				try {
					if(classList[i].getID().equals(id))
					{
						return i;
					}
				}
				catch(NullPointerException e)
				{
					
				}
			}
		return -1;
	}
	
	public FitnessClass searchByTime(int startTime)
	{
		return classList[startTime-EARLIEST_START_TIME];
	}
	
	
	public void populateAttendance(String line)
	{
		//Split line read from AttendanceIn. Result is array of length 6 due to presence of ID.
		String [] attendanceElements = line.split(" ");
		
		System.err.println("Length is" + attendanceElements.length);
		
		String id = attendanceElements[0];
		int index = searchById(id);
		System.err.println("index is " + index);
		System.err.println(id);
		int [] attendance = new int [FitnessClass.ATTENDANCE_WEEKS];
		
		for (int i=1; i<attendanceElements.length; i++)
		{
			attendance[i-1] = Integer.parseInt(attendanceElements[i]);
			System.err.println(attendance[i-1]);
		}
		try
		{
			System.err.println("Okay");
			classList[index].setAttendance(attendance);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			
		}
		
	}
	
	public int getCurrentClasses()
	{
		return currentClasses;
	}
	
	public FitnessClass getListedClass(int x)
	{
		FitnessClass query = classList[x];
		return query;
	}
	
	public FitnessClass[] getClassList()
	{
		return classList;
	}
	
	public FitnessClass[] getClassListByAttendance()
	{
		FitnessClass[] temp = new FitnessClass[currentClasses];
		int tempIndex=0;
		for(int j=0; j<MAXIMUM_CLASSES; j++)
		{
			if(classList[j] != null)
			{
					System.err.println(classList[j].getID());
					temp[tempIndex]=classList[j];
					System.err.println("ID is" + temp[tempIndex].getID());
					tempIndex++;
					
			}
		}
		
		Arrays.sort(temp);
		return temp;
	}
	
	public int findEarliestSlot()
	{
		int time=-1;
		for(int i=0; i<MAXIMUM_CLASSES; i++)
		{
			if(classList[i]==null)
			{
				time=i+EARLIEST_START_TIME;
				return time;
			}
		}
		return time;
	}
	
	public String getAllClassNames()
	{
		String names="";
		
		for(int i=0; i<MAXIMUM_CLASSES; i++)
		{
			if(classList[i]!=null)
			{
				names = String.format("%s%-12.20s|", names, classList[i].getName());
			}
			else
			{
				names = String.format("%s%-12.20s|", names, "Available");
			}
		}
		names=String.format("%s %n", names);
		
		return names;
	}
	
	public String getAllTutors()
	{
		String tutors="";
		
		for(int i=0; i<MAXIMUM_CLASSES; i++)
		{
			if(classList[i]!=null)
			{
				tutors = String.format("%s%-12.20s|", tutors, classList[i].getTutor());
			}
			else
			{
				tutors = String.format("%s%-12.20s|", tutors, " ");
			}
		}
		tutors=String.format("%s %n", tutors);
		
		return tutors;
	}
	
	
	
	public static void main(String[] args)
	{
		FitnessProgram test = new FitnessProgram();
		
		try
		{
			FileReader r = new FileReader("ClassesIn.txt");
			Scanner s = new Scanner(r);
			while(s.hasNextLine())
			{
				String line = s.nextLine();
				test.insertClass(new FitnessClass(line));
			}
			s.close();
			
			
		}
		catch(FileNotFoundException e)
		{
			System.err.println("File Not Found");
		}
		 try
		    {
		    	FileReader r = new FileReader("AttendancesIn.txt");
				Scanner s = new Scanner(r);
				while(s.hasNextLine())
				{
					String line = s.nextLine();
					test.populateAttendance(line);
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
		 
		 System.out.println(test.getAllClassNames());
		 System.out.println(test.getAllTutors());
		
		
		FitnessClass[] hm = test.getClassListByAttendance();
		
		
		
		
		for(int i=0; i< hm.length; i++)
	    {
			if(hm[i] != null)
			{
				String formatResult = hm[i].getReportFormat();
				System.out.println(formatResult);
			}
		}
	}
}
