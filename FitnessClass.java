/** Defines an object representing a single fitness class
 */
public class FitnessClass implements Comparable<FitnessClass> {
    
	/** Class details */
	private String ID, name, tutor;
	/** Class start time */
	private int startTime;
	/** Array to hold attendance data */
	private int [] attendance;
	/** Number of weeks over which attendance is measured. */
	static final int ATTENDANCE_WEEKS = 5;
	
	/** 
	 * Default constructor for FitnessClass
	 */
	public FitnessClass()
	{
		ID = "Unknown";
		name = "Unknown";
		tutor = "Unassigned";
		startTime = 9;
		attendance = new int[ATTENDANCE_WEEKS];
		
		for(int i=0;  i<ATTENDANCE_WEEKS; i++)
		{
			attendance[i] = 0;
		}
	}
	/** 
	 * FitnessClass constructor using a single String containing all details.
	 */
	public FitnessClass(String details)
	{
		//Split string according to pre-determined format and isntantiate variables.
		ID = details.split(" ")[0];
		name = details.split(" ")[1];
		tutor = details.split(" ")[2];
		startTime = Integer.parseInt(details.split(" ")[3]);
		
	}
	
	/** 
	 * FitnessClass constructor which does not instantiate attendance array.
	 */
	public FitnessClass(String id, String n, String t, int st)
	{
		ID = id;
		name = n;
		tutor = t;
		startTime = st;
	}
	
	/** 
	 * FitnessClass constructor which also instantiates the attendance array with only 0 values.
	 */
	public FitnessClass(String id, String n, String t, int st, boolean noAttendance)
	{
		ID = id;
		name = n;
		tutor = t;
		startTime = st;
		attendance = new int[ATTENDANCE_WEEKS];
		for(int i=0; i<ATTENDANCE_WEEKS; i++)
		{
			attendance[i] = 0;
		}
	}
	
	
	/** 
	 * Calculates average attendance of class using attendance array
	 * @return the average attendance
	 */
	public double getAverageAttendance()
	{
		double avg;
		int total = 0;
		//Iterate through attendance array and add attendance to running total.
		for(int i=0; i<ATTENDANCE_WEEKS; i++)
		{
			total = total + attendance[i];
		}
		//Calculate average.
		avg = total*1.0/ATTENDANCE_WEEKS;
		return avg;
	}
	
	/** 
	 * Summarizes all Fitness Class variables in a single String for addition to the attendance report.
	 * @return all class details in attendance report format
	 */
	public String getReportFormat()
	{
		String fine = String.format(" %-5s %-15s %-15s %4d %4d %4d %4d %4d %16.2f", ID, name, tutor, attendance[0], attendance[1], attendance[2], attendance [3], attendance[4], getAverageAttendance());
		return fine;
	}
	
	/** 
	 * Compares two FitnessClass objects by their average attendance and gives natural order for sort operations.
	 * @return position of this FitnessClass object relative to another (less, equal or greater)
	 */
    public int compareTo(FitnessClass other) {
    	//Get average attendances for both objects.
      double otherAverage = other.getAverageAttendance();
      double thisAverage = this.getAverageAttendance();
      
      //Greater average attendance means coming first in order (leading to descending order by average attendance)
      if(thisAverage>otherAverage)
      {
    	  return -1;
      }
      else if(thisAverage<otherAverage)
      {
    	  return 1;
      }
      else
      {
    	  return 0;  
      }
	 
    }

    /** 
	 * @return the class ID
	 */
	public String getID() {
		return ID;
	}


	public void setID(String iD) {
		ID = iD;
	}

	/** 
	 * @return the class name
	 */
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	/** 
	 * @return the name of the class tutor
	 */
	public String getTutor() {
		return tutor;
	}


	public void setTutor(String tutor) {
		this.tutor = tutor;
	}

	/** 
	 * @return the class start time (9 to 15)
	 */
	public int getStartTime() {
		return startTime;
	}


	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	/** 
	 * @return the class attendance data
	 */
	public int[] getAttendance() {
		return attendance;
	}


	public void setAttendance(int[] attendance) {
		this.attendance = attendance;
	}
}
