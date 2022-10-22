package hw1_CourseManager;
/**
 * The <code>Course</code> class implements a object that
 * represents a course.
 * 
 * 
 * @author Zhenting Ling
 * 		CSE214.R01
 * 		e-mail: zhenting.ling@stonybrook.edu
 * 		Stony Brook ID: 114416612
 *
 */


public class Course implements Cloneable {
	
	private String name; // Holds the name of the course.
	private String department; // Holds the department 
								//code of the course
	private int code; // Course code of the course.
	private byte section; // Section number of the course.
	private String instructor; // Name of the instructor of the course.
	
	/**
	 * This is a constructor used to instantiate a <code>course</code> object. 
	 *  
	 * @param name
	 * 		The full name of the course
	 * @param department
	 * 		The department of this course
	 * @param code
	 * 		The course code of this course
	 * @param section
	 * 		The section number of this course
	 * @param instructor
	 * 		The instructor of this course
	 * 
	 * <dt>Postcondition:
	 * 		<dd>An object of course is instantiated.
	 */
	public Course(String name, String department, int code, byte section, String instructor) {
		this.name = name;
		this.department = department;
		this.code = code;
		this.section = section;
		this.instructor = instructor;
	}
	
	/**
	 *  This is a default constructor.
	 * 
	 */
	public Course() {
		
	}
	
	/**
	 * Getter for the name of the <code>Course</code> object.
	 * 
	 * @return
	 * 		returns the name of the course as a string.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter for the department of this <code>Course</code> object.
	 * 
	 * @return
	 * 		The department of this <code>Course</code> instance.
	 */
	public String getDepartment() {
		return department;
	}
	
	/**
	 * Getter for the course code of this <code>Course</code> instance.
	 * 
	 * @return
	 * 		The course code of this <code>Course</code> instance.
	 */
	public int getCode() {
		return code;
	}
	
	/**
	 * Getter for the course section of this <code>Course</code> instance.
	 * 
	 * @return
	 * 		The section number of this <code>Course</code> instance.
	 */
	public byte getSection() {
		return section;
	}
	
	/**
	 * Getter for the instructor of this <code>Course</code> instance.
	 * 
	 * @return
	 * 		The instructor name of this <code>Course</code> instance.
	 */
	public String getInstructor() {
		return instructor;
	}
	
	/**
	 * Setter for the name of this <code>Course</code> instance.
	 * 
	 * @param name
	 * 		A string that represents the name of this <code>Course</code>.
	 * 
	 * <dt>Postcondition:
	 * 		<dd>The name of this <code>Course</code> is changed to the
	 * 		input string.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Setter for the department of this <code>Course</code> instance.
	 * 
	 * @param department
	 * 		A string that represents the intended department of this
	 * 		<code>Course</code> instance.
	 * 
	 * <dt>Postcondition:
	 * 		<dd>The department of this <code>Course</code> instance is
	 * 		changed to the input string.
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	
	/**
	 * Setter for the course code of this <code>Course</code> instance.
	 * 
	 * <dt>Postcondition:
	 * 		<dd>The course code of this <code>Course</code> instance
	 * 		is changed to the input integer.
	 * 
	 * @param code
	 * 		An integer that represents the intended course code of
	 * 		this <code>Course</code> instance.
	 * @throws IllegalArgumentException
	 * 		Indicates that the input integer is not in a valid range.
	 */
	public void setCode(int code) throws IllegalArgumentException{
		if (code >= 100)
			this.code = code;
		else
			throw new IllegalArgumentException();
		
	}
	
	/**
	 * Setter for the section number of this <code>Course</code> instance.
	 * 
	 * <dt>Postcondition:
	 * 		<dd>The section number of this <code>Course</code> instance
	 * 		is changed to intended number.
	 * 
	 * @param section
	 * 		A byte that represents the intended section number of this
	 * 		<code>Course</code> instance.
	 * @throws IllegalInput
	 */
	public void setSection(byte section) throws IllegalArgumentException{
		if (section > 0)
			this.section = section;
		else
			throw new IllegalArgumentException();
	}
	
	/**
	 * Setter for the instructor of this <code>Course</code>.
	 * 
	 * <dt>Postcondition:
	 * 		<dd>The instructor of this <code>Course</code> instance
	 * 		is changed to the intended instructor
	 * 
	 * @param instructor
	 * 		A string that represents the instructor of this 
	 * 		<code>Course</code> instance.
	 */
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	
	
	/**
	 * Clone method of this <code>Course</code> instance.
	 * 
	 * @return
	 * 		Returns a planner object that is identical to this
	 * 		<code>Course</code> instance.
	 */
	public Object clone() {
		return new Course(name, department, code, section, instructor);
	}
	
	/**
	 * Equals method of this <code>Course</code> instance.
	 * 
	 * @param obj
	 * 		An object that is to compared with this
	 * 		<code>Course</code> instance.
	 * 
	 * @return
	 * 		Returns true if the inputed object is identical
	 * 		to this <code>Course</code> instance. Otherwise
	 * 		returns false.
	 * 
	 */
	public boolean equals(Object obj) {
		if(obj instanceof Course) {
			Course o = (Course)obj;
			if (name.equals(o.getName()) && department.equals(o.getDepartment())
					&& code == o.getCode() && section == o.getSection()
					&& instructor.equals(o.getInstructor()))
				return true;
		}
		return false;
	}
	
	/**
	 * ToString method of this <code>Course</code> instance.
	 * 
	 * @return
	 * 		Returns a string that represents this <code>Course</code>
	 * 		instance.
	 * 
	 */
	public String toString() {
		return String.format("%-26s%-12s%-9d%02d %s", name, department, code, section, instructor);
	}

	
}
