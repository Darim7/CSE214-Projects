package hw3;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * An object class <code>PythonTracer</code> which reads Python scripts and
 * calculate its complexity.
 * 
 * @author Zhenting Ling
 * 		CSE214.R01
 * 		e-mail: zhenting.ling@stonybrook.edu
 * 		Stony Brook ID: 114416612
 *
 */
public class PythonTracer {

	public static final int SPACE_COUNT = 4;

	/**
	 * Prompts the user for the name of a file containing a single Python function,
	 * determines its order of complexity, and prints the result to the console.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		String inputStr;
		do {
			System.out.print("Please enter a file name (or 'quit' to quit):");
			inputStr = input.nextLine();
			if (!inputStr.equalsIgnoreCase("quit")) {
				String fileName = inputStr;
				try {
					System.out.println();
					Complexity res = traceFile(fileName);
					int index = fileName.indexOf(".py");
					String displayName = "";
					for (int i = 0; i < index; i++) {
						displayName += fileName.charAt(i);
					}
					System.out.println(new String(String.format
							("Overall complexity of %s: %s\n", displayName, res)));
				} catch (FileNotFoundException e) {
					System.out.println("File is not found.\n");
				} catch (IllegalArgumentException e) {
					System.out.println("\nPlease enter a valid input.\n");
				}
			}
		} while (!inputStr.equalsIgnoreCase("quit"));
		System.out.println("\nProgram terminating successfully...");
		input.close();
	}

	/**
	 * Opens the indicated file and traces through the code of the Python function
	 * contained within the file, returning the Big-Oh order of complexity of the
	 * function. During operation, the stack trace should be printed to the console
	 * as code blocks are pushed to/popped from the stack. (Used algorithm provided
	 * in the hw documentation).
	 * 
	 * Preconditions: filename is not null and the file it names contains a single
	 * Python function with valid syntax (Reminder: you do NOT have to check for
	 * invalid syntax).
	 * 
	 * @param fileName
	 * 
	 * @return A <code>Complexity</code> object representing the total order of
	 *         complexity of the Python code contained within the file.
	 * @throws FileNotFoundException
	 */
	public static Complexity traceFile(String fileName) throws FileNotFoundException {
		
		BlockStack codeBlockStack = new BlockStack();
		ArrayList<Integer> blockAddress = new ArrayList<Integer>();
		File f = new File(fileName);
		Scanner input = new Scanner(f);

		while (input.hasNextLine()) {
			String line = input.nextLine();

			if (!line.contains("#") && !line.isEmpty() && !line.equals("\n")) {
				int indents = countSpaces(line) / SPACE_COUNT;
				while (indents < codeBlockStack.size()) {
					if (indents == 0) {
						input.close();
						return endOfFile(codeBlockStack);
					} else {
						leaveBlock(codeBlockStack);
					}
				}

				CodeBlock.Keyword key = findKeyword(line);

				if (key != null) {
					Complexity blockComplx = new Complexity();
					CodeBlock newCodeBlock = new CodeBlock(blockComplx);
					String blockName = "block ";
					
					if (blockAddress.size()-1 <= indents) {
						for (int i = blockAddress.size(); i <= indents + 1; i++) {
							blockAddress.add(0);
						}
					}
					blockAddress.set(indents, blockAddress.get(indents)+1);
					blockAddress.set(indents+1, 0);
					for (int i = 0; i <= indents; i++) {
						if (indents == 0) {
							blockName += blockAddress.get(i);
						} else if (i < indents) {
							blockName += blockAddress.get(i) + ".";
						} else {
							blockName += blockAddress.get(i);
						}
					}
					newCodeBlock.setName(blockName);
					String msg = "    Entering " + blockName;
					
					switch (key) {
					case FOR:
						System.out.println(msg + " 'for': ");
						if (line.contains("log_N:")) {
							blockComplx.setLogPower(1);
							codeBlockStack.push(newCodeBlock);
						} else if (line.contains("N:")){
							blockComplx.setNPower(1);
							codeBlockStack.push(newCodeBlock);
						}
						break;
					case WHILE:
						String str = line.trim();
						String[] strArr = str.split(" ");
						newCodeBlock.setLoopVariable(strArr[1]);
						System.out.println(msg + " 'while': ");
						codeBlockStack.push(newCodeBlock);
						break;
					case IF:
						System.out.println(msg + " 'if': ");
						codeBlockStack.push(newCodeBlock);
						break;
					case ELIF:
						System.out.println(msg + " 'elif': ");
						codeBlockStack.push(newCodeBlock);
						break;
					case ELSE:
						System.out.println(msg + " 'else': ");
						codeBlockStack.push(newCodeBlock);
						break;
					case DEF:
						System.out.println(msg + " 'def': ");
						codeBlockStack.push(newCodeBlock);
						break;
					default: // Not required. But in case needed.
						break;
					}
					System.out.println(newCodeBlock + "\n");
				} else if (!codeBlockStack.isEmpty() && codeBlockStack.peek().getLoopVariable() != null && 
						line.trim().startsWith(codeBlockStack.peek().getLoopVariable())){
					System.out.println("    Found update statement, updating " + codeBlockStack.peek().getName() + ":");
					if (line.contains(" /= 2")) {
						codeBlockStack.peek().getBlockComplexity().setLogPower(1);
					} else if (line.contains(" -= 1")) {
						codeBlockStack.peek().getBlockComplexity().setNPower(1);
					}
					System.out.println(codeBlockStack.peek() + "\n");
				}
			}

		}
		while (codeBlockStack.size() > 1) {
			leaveBlock(codeBlockStack);
		}
		
		input.close();
		return endOfFile(codeBlockStack);

	}

	/**
	 * Helper method that counts the spaces in the front of string.
	 * 
	 * @param str string of interest
	 * @return number of spaces in the string
	 */
	public static int countSpaces(String str) {
		int res = 0;
		String[] strArr = str.split("");

		for (String c : strArr) {
			if (c.equals("\t"))
				res += 4;
			else if (c.equals(" "))
				res++;
			else
				break;
		}

		return res;
	}

	/**
	 * Helper method that makes finds and returns the Python script keyword in a
	 * string.
	 * 
	 * @param line a string of a line of Python script
	 * @return the keyword found in this line of Python script.
	 */
	public static CodeBlock.Keyword findKeyword(String line) {
		CodeBlock.Keyword key;
		if (line.matches("[\\s]*for [\\w]+ in [\\w]+:")) {
			key = CodeBlock.Keyword.FOR;
		} else if (line.matches("[\\s]*while [(]?[\\w]+ " + "[><[==]]+ [\\d]+[)]?:")) {
			key = CodeBlock.Keyword.WHILE;
		} else if (line.matches("[\\s]*if [(]?[\\S[\\s]]*[)]?:")) {
			key = CodeBlock.Keyword.IF;
		} else if (line.matches("[\\s]*elif [(]?[\\S[\\s]]*[)]?:")) {
			key = CodeBlock.Keyword.ELIF;
		} else if (line.matches("[\\s]*else:")) {
			key = CodeBlock.Keyword.ELSE;
		} else if (line.trim().startsWith("def ")) {
			key = CodeBlock.Keyword.DEF;
		} else {
			key = null;
		}
		return key;
	}
	
	/**
	 * Helper method of <code>traceFile</code>, takes a
	 * <code>codeBlockStack</code> object and performs a leaving
	 * block operation.
	 * 
	 * @param codeBlockStack
	 * 	the codeBlockStacks that needs to perform a leaving operation.
	 */
	public static void leaveBlock(BlockStack codeBlockStack) {
		CodeBlock oldTop = codeBlockStack.pop();
		oldTop.updateBlockComplexity();
		Complexity oldComplexity = oldTop.getBlockComplexity();
		System.out.print("    Leaving " + oldTop.getName());
		Complexity topHighestSubComplexity = codeBlockStack.peek().getHighestSubComplexity();
		if (topHighestSubComplexity.compareTo(oldComplexity) < 0) {
			System.out.println(", updating " + codeBlockStack.peek().getName() + ":");
			codeBlockStack.peek().setHighestSubComplexity(oldComplexity);
		} else {
			System.out.println(", nothing to update.");
		}
		System.out.println(codeBlockStack.peek() + "\n");
	}
	
	
	public static Complexity endOfFile(BlockStack codeBlockStack) {
		Complexity res;
		System.out.println("    Leaving " + codeBlockStack.peek().getName() + ".\n");
		codeBlockStack.peek().updateBlockComplexity();
		res = codeBlockStack.pop().getBlockComplexity();
		return res;
	}
}
