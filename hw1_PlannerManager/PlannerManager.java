package hw1_CourseManager;

import java.util.Scanner;

/**
 * Class <code>PlannerManager</code> is a class that implements
 * and utilizes both <code>Planner</code> object and <code>Course</code>
 * object. <code>PlannerManager</code> makes the user able to add, remove,
 * back up, revert to back up, print courses in a planner, filter and print
 * courses by departments.
 * 
 * 
 * @author Zhenting Ling
 * 		CSE214.R01
 * 		e-mail: zhenting.ling@stonybrook.edu
 * 		Stony Brook ID: 114416612
 *
 */

public class PlannerManager{
	
	/**
	 * The main method of <code>PlannerManager</code> object.
	 * 
	 * @param ars
	 * 		implementation of the user interface.
	 */
	public static void main(String[] ars) {

		Planner planner = new Planner();
		Planner backUp = new Planner();
		
		String choice = "";
		
		/**
		 * The main menu that the user will choose from.
		 */
		String menu = "(A) Add Course\r\n"
				+ "(G) Get Course\r\n"
				+ "(R) Remove Course\r\n"
				+ "(P) Print Courses in Planner\r\n"
				+ "(F) Filter by Department Code\r\n"
				+ "(L) Look For Course\r\n"
				+ "(S) Size\r\n"
				+ "(B) Backup\r\n"
				+ "(PB) Print Courses in Backup\r\n"
				+ "(RB) Revert to Backup\r\n"
				+ "(Q) Quit";
		
		System.out.println(menu);
		
		Scanner input = new Scanner(System.in);
		
		/**
		 * A do-while loop that enables the user to enter 
		 * their choice from the menu selection and asks them
		 * to enter their choice again if the input is illegal.
		 */
		do {
			System.out.print("\nEnter a selection from the menu: ");
			choice = input.nextLine().toUpperCase();
			
			/**
			 * Switch case to determine what action from the main
			 * menu the user wants to choose.
			 */
			switch (choice) {
			
				/**
				 * Add a course to the planner at a specific
				 * position. If the position is occupied, 
				 * the added course will be inserted to the
				 * intended position and the original course
				 * (including the following ones) will be shifted
				 * toward right one position.
				 * 
				 */
				case "A": // Add Course
					Course newCourse = new Course();
					try {
						System.out.print("\nEnter course name: ");
						String name = input.nextLine();
						newCourse.setName(name);
						
						System.out.print("Enter department: ");
						String department = input.nextLine().toUpperCase();
						newCourse.setDepartment(department);
						
						System.out.print("Enter course code: ");
						int code = Integer.parseInt(input.nextLine());
						newCourse.setCode(code);
						
						System.out.print("Enter section: ");
						byte section = Byte.parseByte(input.nextLine());
						newCourse.setSection(section);
						
						System.out.print("Enter instructor: ");
						String instructor = input.nextLine();
						newCourse.setInstructor(instructor);
						
						System.out.print("Enter position: ");
						int position = Integer.parseInt(input.nextLine());
						
						planner.addCourse(newCourse, position);
						
					} catch (java.lang.NumberFormatException e) {
						System.out.println("\nPlease enter a number.");
						continue;
					} catch (IllegalArgumentException e) {
						System.out.println("\nNumber was not in range, "
								+ "plaese enter a valid number.");
						continue;
					} catch (FullPlannerException e) {
						System.out.println("\nPlanner is full, failed to add course.");
					}
					
					break;
				
					
				/**
				 * Displays information of a Course at the given position.
				 */
				case "G":
					try {
						System.out.print("\nEnter position: ");
						int position = Integer.parseInt(input.nextLine());
						
						String header = "\nNo. Course Name               " // Header
								+ "Department Code Section Instructor";
						header += "\n--------------------------------------" // The dotted line
								+ "-----------------------------------------";
						System.out.println(header);
						
						System.out.println(String.format("%3d %s", position, 
								planner.getCourse(position).toString()));
						
					} catch (NumberFormatException e) {
						System.out.print("\nPlease enter a number.");
						continue;
					} catch (IllegalArgumentException e) {
						System.out.println("\nNumber was not in range, "
								+ "plaese enter a valid number.");
						continue;
					}
					break;
				
				/**
				 * Removes the Course at the given position.
				 */
				case "R": // Remove course
					System.out.println("\nEnter position: ");
					try {
						int position = Integer.parseInt(input.nextLine());
						String resMsg = String.format("\n%-4s%d.%02d "
								+ "successfully removed from planner."
								, planner.getCourse(position).getDepartment()
								, planner.getCourse(position).getCode()
								, planner.getCourse(position).getSection());
						
						planner.removeCourse(position);
						System.out.println(resMsg);
						
					} catch (NumberFormatException e) {
						System.out.print("\nPlease enter a number.");
						continue;
					} catch (IllegalArgumentException e) {
						System.out.println("\nNumber was not in range, "
								+ "plaese enter a valid number.");
						continue;
					}
					break;
				
				/**
				 * Displays all courses in the list.
				 */
				case "P": // Print courses in planner.
					planner.printAllCourses();
					break;
				
				/**
				 * Displays courses that match the given 
				 * department code.
				 */
				case "F": // Filter
					System.out.print("\nEnter department code: ");
					String department = input.nextLine().toUpperCase();
					try {
						Planner.filter(planner, department);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
						System.out.println("Unexpected error, "
								+ "please contact the developer.");
						continue;
					}
					break;
					
				/**
				 * Determines whether the course with 
				 * the given attributes is in the list.
				 */
				case "L": // Look for course
					Course c1 = new Course();
					try {
						System.out.print("\nEnter course name: ");
						String name = input.nextLine();
						c1.setName(name);
						
						System.out.print("Enter department: ");
						String dpt = input.nextLine().toUpperCase();
						c1.setDepartment(dpt);
						
						System.out.print("Enter course code: ");
						int code = Integer.parseInt(input.nextLine());
						c1.setCode(code);
						
						System.out.print("Enter section: ");
						byte section = Byte.parseByte(input.nextLine());
						c1.setSection(section);
						
						System.out.print("Enter instructor: ");
						String instructor = input.nextLine();
						c1.setInstructor(instructor);
						
						int position = planner.indexOfCourse(c1);
						
						if (position > 0) {
							System.out.println(String.format("\n%s %d.%02d is found "
									+ "in the planner at position %d.",
									planner.getCourse(position).getName(), 
									planner.getCourse(position).getCode(),
									planner.getCourse(position).getSection(), position));
						} else {
							System.out.print("\nThe course is not found in the planner.\n");
						}
						
					} catch (java.lang.NumberFormatException e) {
						System.out.println("\nPlease enter a number.");
						continue;
					} catch (IllegalArgumentException e) {
						System.out.println("\nNumber was not in range, "
								+ "plaese enter a valid number.");
						continue;
					}
					break;
					
				/**
				 * Determines the number of courses in the Planner.
				 */
				case "S": // Size
					System.out.print(String.format("The size of "
							+ "this planner is %d.", planner.size()));
					break;
				
				/**
				 * Creates a copy of the given Planner. 
				 * Changes to the copy will not affect the original 
				 * and vice versa.
				 */
				case "B":
					backUp = (Planner)planner.clone();
					
					System.out.println("\nCreated a backup of the"
							+ " current planner.");
					break;
					
				/**
				 * Displays all the courses from the backed-up list.
				 */
				case "PB": // Print back up planner.
					System.out.println("Back up planner: ");	
					backUp.printAllCourses();
					break;
					
				/**
				 * Reverts the current Planner to the backed up copy.
				 */
				case "RB":
					planner = (Planner) backUp.clone();
					System.out.println("\nPlanner successfully "
							+ "reverted to the backup copy.");
					break;
				
				/**
				 * Terminates the program.
				 */
				case "Q": break;
				
				default:
					System.out.println("\nPlease enter a valid"
							+ " selection.");
					continue;
			}
			
		} while (!choice.equals("Q"));
		input.close();
		System.out.println("\nProgram terminated successfully.");
	}
	
}
