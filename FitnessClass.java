/** Defines an object representing a single fitness class
 */
public class FitnessClass implements Comparable<FitnessClass> {
    // your code here
	private String ID, name, tutor;
	private int startTime;
	private int [] attendance;
	static final int ATTENDANCE_WEEKS = 5;
	
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
	
	public FitnessClass(String id, String n, String t, int st)
	{
		ID = id;
		name = n;
		tutor = t;
		startTime = st;
	}
	
	public FitnessClass(String details)
	{
		ID = details.split(" ")[0];
		name = details.split(" ")[1];
		tutor = details.split(" ")[2];
		startTime = Integer.parseInt(details.split(" ")[3]);
		
	}
	
	public double getAverageAttendance()
	{
		double avg;
		int total = 0;
		
		for(int i=0; i<ATTENDANCE_WEEKS; i++)
		{
			total = total + attendance[i];
		}
		
		avg = total*1.0/ATTENDANCE_WEEKS;
		return avg;
	}
	
	public String getReportFormat()
	{
		String fine = String.format("%5s %10s %10s %4d %4d %4d %4d %4d %8f", ID, name, tutor, attendance[0], attendance[1], attendance[2], attendance [3], attendance[4], getAverageAttendance());
		return fine;
	}
	
	
	
    public int compareTo(FitnessClass other) {
      double otherAverage = other.getAverageAttendance();
      double thisAverage = this.getAverageAttendance();
      
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


	public String getID() {
		return ID;
	}


	public void setID(String iD) {
		ID = iD;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getTutor() {
		return tutor;
	}


	public void setTutor(String tutor) {
		this.tutor = tutor;
	}


	public int getStartTime() {
		return startTime;
	}


	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}


	public int[] getAttendance() {
		return attendance;
	}


	public void setAttendance(int[] attendance) {
		this.attendance = attendance;
	}
    
    public static void main(String[] args)
    {
    	int[] l = {3,5,6,7,10};
    	FitnessClass old = new FitnessClass("adf Pilates Johnny 14");
    	old.setAttendance(l);
    	String formatResult = old.getReportFormat();
    	System.out.println(formatResult);
    }
	
}
