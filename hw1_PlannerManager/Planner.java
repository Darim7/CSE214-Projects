package hw1_CourseManager;
/**
 * The <code>Planner</code> class implements an abstract data 
 * type for a list of courses supporting some common 
 * operations on such lists.
 * 
 * 
 * @author Zhenting Ling
 * 		CSE214.R01
 * 		e-mail: zhenting.ling@stonybrook.edu
 *		Stony Brook ID: 114416612
 */
public class Planner implements Cloneable{
	
	// The maximum amount of courses in a planner.
	public final static int MAX_COURSES = 50;
	
	private Course[] courses; // An array that holds the courses.
	private int size; // Number of courses curently in the planner.
	
	/**
	 * Returns an instance of <code>Planner</code>.
	 * <dt>Postcondition:
	 * 		<dd>This <code>Planner</code>
	 * 		 has been initialized to an empty list of Courses.
	 */
	public Planner() {
		courses = new Course[MAX_COURSES];
		size = 0;
	}
	
	/**
	 * Determines the number of courses currently in the list.
	 * 
	 * <dt>Precondition: <dd>This Planner has been instantiated.
	 * 
	 * @return
	 * 		The number of Courses in this Planner.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Add an course into the planner.
	 * 
	 * <dt>Preconditions: 
	 *		<dd>This <code>Course</code> object has been instantiated and 
	 * 		1 ¡Ü position ¡Ü items_currently_in_list + 1. 
	 * 		The number of <code>Course</code> objects 
	 * 		in this Planner is less than <code>MAX_COURSES</code>.
	 * 
	 * <dt>Postconditions:
	 * 		<dd>The new <code>Course</code> is now listed in the 
	 * 		correct preference on the list.
	 * 		 All <code>Courses</code> that were originally greater 
	 * 		than or equal to position are 
	 * 		moved back one position. (e.g. If there are 5 Courses in 
	 * 		a <code>Planner</code>, 
	 * 		positioned 1-5, and you insert a <code>Course</code> in 
	 * 		position 4, the new <code>Course</code> would 
	 * 		be placed in position 4, the Course that was in 
	 * 		position 4 will be moved to 
	 * 		position 5, and the Course that was in position 5 
	 * 		will be moved to position 6.)
	 * 
	 * @param newCourse
	 * 		The new course to add to the list
	 * @param position
	 * 		The position (preference) of this course on the list
	 * @throws FullPlannerException
	 * 		Indicates that there is no more room in the Planner 
	 * 		to record an additional Course.
	 * @throws IllegalArgumentException
	 * 		Indicates that position is not within the valid range.
	 */
	public void addCourse(Course newCourse, int position) 
			throws FullPlannerException, IllegalArgumentException {
		if (size < MAX_COURSES && position > 0 && position <= size+1) {
			int index = position-1;
			if (courses[index] == null) {
				courses[index] = newCourse;
			} else {
				Course[] newCourses = new Course[MAX_COURSES];
				for (int i = 0, j = 0; i < size+1; i++) {
					if (i != index) {
						newCourses[i] = courses[j];
						j++;
					} else {
						newCourses[i] = newCourse;
					}
				}
				courses = newCourses;
			}
			size++;
			System.out.println(String.format(
					"\n%-4s%d.%02d successfully added to planner."
					, newCourse.getDepartment(), newCourse.getCode(),
					newCourse.getSection()));
		}
		else
			if (size >= MAX_COURSES)
				throw new FullPlannerException();
			else if (position <= 0 || position > size + 1) {
				throw new IllegalArgumentException();
			}
	}
	
	/**
	 * Add a course to the end of the planner.
	 */
	public void addCourse(Course newCourse) 
			throws FullPlannerException, IllegalArgumentException {
		addCourse(newCourse, ++size);
	}
	
	/**
	 * Removes a course from the planner in the given position.
	 * 
	 * <dt>Precondition:
	 * 		<dd>This <code>Planner</code> has been instantiated and 
	 * 		1 ¡Ü position ¡Ü items_currently_in_list.
	 * 
	 * <dt>Postcondition:
	 * 		<dd>The Course at the desired position has been removed. 
	 * 		All <code>Courses</code> that were originally greater than or equal to 
	 * 		position are moved backward one position. (e.g. If there 
	 * 		are 5 Courses in a Planner, positioned 1-5, and you remove 
	 * 		the <code>Course</code> in position 4, the item that was in 
	 * 		position 5 will be moved to position 4.)
	 * 
	 * @param position
	 * 		The position in the Planner where the Course will be removed from.
	 * @throws IllegalArgumentException
	 * 		Indicates that position is not within the valid range.
	 */
	public void removeCourse(int position) throws IllegalArgumentException {
		if (position > 0 && position <= size) {
			int index = position - 1;
			courses[index] = null;
			if (position != size) {
				for (int i = index; i < size; i++) {
					courses[i] = courses[i+1];
				}
			}
			size--;
		} else
			throw new IllegalArgumentException();
	}
	
	/**
	 * Returns a <code>Course</code> at a given position.
	 * 
	 * @param position
	 * 		Position of the Course to retrieve.
	 * @return Course
	 * 		The <code>Course</code> at the specified 
	 * 		position in this <code>Planner</code> object.
	 * @throws IllegalArgumentException
	 * 		Indicates that position is not within the valid range.
	 */
	public Course getCourse(int position) throws IllegalArgumentException {
		if (position > 0 && position <= size) {
			return courses[position-1];
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Prints all Courses that are within the specified department.
	 * 
	 * <dt>Preconditions:
	 * 		<dd>This <code>Planner</code> object has been instantiated.
	 * 
	 * <dt>Postconditions:
	 * 		Displays a neatly formatted table of each course 
	 * 		filtered from the Planner. 
	 * 		Keep the preference numbers the same.
	 * 
	 * @param planner
	 * 		The list of <code>courses</code> to search in
	 * @param department
	 * 		The 3 letter department code for a <code>Course</code>.
	 * @throws IllegalArgumentException
	 * 		Indicates that the counter of the loop went out of range.
	 */
	public static void filter (Planner planner, String department) throws IllegalArgumentException {
		String res = "No. Course Name               " // Header
				+ "Department Code Section Instructor";
		res += "\n--------------------------------------" // The dotted line
				+ "-----------------------------------------";
		System.out.println(res);
		
		/**
		 *  Variable i represents position.
		 */
		for (int i = 1; i <= planner.size() ; i++) {
			if (planner.getCourse(i).getDepartment().equals(department)) {
				System.out.println(String.format("%3d %s", i, planner.getCourse(i).toString()));
			}
		}
	}
	
	/**
	 * Checks whether a certain Course is already in the list.
	 * 
	 * <dt>Precondition:
	 * 		<dd>This Planner and Course has both been instantiated.
	 * 
	 * @param course
	 * 		the <code>Course</code> we are looking for.
	 * @return boolean
	 * 		True if the Planner contains this Course, false otherwise.
	 */
	public boolean exists(Course course) {
		for (int i = 0; i < size; i++) {
			if (courses[i].equals(course)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the position of the targeted course.
	 * @param course
	 * @return
	 * 		Returns the positon of the targeted course.
	 * 		if the course does not exist in the planner,
	 * 		returns -1.
	 */
	public int indexOfCourse(Course course) {
		for (int i = 0; i < size; i++) {
			if (courses[i].equals(course)) {
				return i+1;
			}
		}
		return -1;
	}
	
	/**
	 * Creates a copy of this Planner. Subsequent changes 
	 * to the copy will not affect the original and vice versa.
	 * 
	 * <dt>Preconditions:
	 * 		<dd>This Planner object has been instantiated.
	 * 
	 * @return
	 * 		A copy (backup) of this Planner object.
	 */
	public Object clone() {
		Planner res = new Planner();
		for (int i = 0; i < size; i++) {
			//try {
				res.courses[i] = courses[i];
			//} catch (IllegalArgumentException e) {
			//} catch (FullPlannerException e) {
			//	System.out.println("Planner is full.");
			//}
		}
		res.size = this.size;
		return res;
	}
	
	/**
	 * Prints a neatly formatted table of each item in the list 
	 * with its position number as shown in the sample output.
	 * 
	 * <dt>Preconditions:
	 *  	<dd>This Planner has been instantiated.
	 * <dt>Postconditions:
	 * 		<dd>Displays a neatly formatted table of each course 
	 * 		from the Planner.
	 */
	public void printAllCourses() {
		System.out.println(toString());
	}
	
	/**
	 * Determines whether a <code>Planner</code> object is equals
	 * to this <code>Planner</code> instance.
	 * 
	 * @param
	 * 		Any Object
	 * 
	 * @return
	 * 		Returns true if the entered object has the same
	 * 		datafield value as this <code>Course</code> instance.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Planner) {
			Planner o = (Planner)obj;
			for (int i = 0; i < size; i++)
				if (!courses[i].equals(o.courses[i]))
					return false;
			return true;
		} else
			return false;
	}
	
	/**
	 * Gets the String representation of this Planner object, 
	 * which is a neatly formatted table of each Course in the 
	 * Planner on its own line with its position number as 
	 * shown in the sample output.
	 * 
	 * @return
	 * 		The String representation of this Planner object.
	 */
	public String toString() {
		String res = "No. Course Name               " // Header
				+ "Department Code Section Instructor";
		res += "\n--------------------------------------" // The dotted line
				+ "-----------------------------------------";
		
		for (int i = 0; i < size; i++) {
			//System.out.println(i);
			//System.out.println(courses[i]);
			//System.out.println(String.format("\n%3d %s", i+1, courses[i].toString()));
			res += String.format("\n%3d %s", i+1, courses[i].toString());
		}
		
		return res;
	}
	
}
