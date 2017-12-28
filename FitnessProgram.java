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

	
	
	
	public static void main(String[] args)
	{
		FitnessProgram test = new FitnessProgram();
		
		FitnessClass old = new FitnessClass("old Pilates Johnny 14");
		FitnessClass jen = new FitnessClass("jen Pilates Johnny 15");
		FitnessClass gor = new FitnessClass("gor Pilates Johnny 9");
		FitnessClass dul = new FitnessClass("dul Pilates Johnny 10");
		FitnessClass sal = new FitnessClass("sal Pilates Johnny 11");
		
		int[] l = {0,0,0,0,0};
		old.setAttendance(l);
		jen.setAttendance(l);
		gor.setAttendance(l);
		dul.setAttendance(l);
		sal.setAttendance(l);
		
		
		test.insertClass(old);
		test.insertClass(jen);
		test.insertClass(gor);
		test.insertClass(dul);
		test.insertClass(sal);
		
		test.deleteClass(test.searchById("gor"));
		test.populateAttendance("dul 13 13 12 10 8");
		
		for(int i=0; i< MAXIMUM_CLASSES; i++)
		{
			if(test.getListedClass(i) != null)
			{
				String formatResult = test.getListedClass(i).getReportFormat();
				System.out.println(formatResult);
			}
		}
		
    	
	}
}
