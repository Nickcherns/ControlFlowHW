import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AsciiChars {

	public static void main(String[] args) {
		// initialize scanner
		Scanner input = new Scanner(System.in);

		// methods to print 0-9 and alphabet (upper case & lower case)
		printNumbers();
		System.out.println("-------------------");
		printLowerCase();
		System.out.println("-------------------");
		printUpperCase();
		System.out.println("-------------------");

		// ask user for name input
		String enterName = "Please enter your name: ";
		String name = stringCheck(input, enterName);
		System.out.println("Hello " + name);

		// main survey loop
		mainLoop: while (true) {

			// ask user if they wish to continue
			System.out.println("Do you wish to continue?..");
			Boolean cont = yesCheck(input);

			// lottery question game loop start
			lotteryLoop: while (cont.equals(true)) {

				// ask user for the name of favorite pet
				String pet = stringCheck(input, "What is the name of your favorite pet?\n");

				// ask user for the age of favorite pet
				int petAge = intCheck(input, "What is the age of your favorite pet?\n");

				// ask user for their lucky number
				int luckyNum = intCheck(input, "What is your lucky number?\n");

				// ask user if they have a favorite quarter back
				System.out.println("Do you have a favorite Quarterback?");
				Boolean favQb = yesCheck(input);

				// if user says yes, then ask them for the QB jersey number
				// if not, skip
				int jerseyNum = 0;
				if (favQb.equals(true)) {
					jerseyNum = intCheck(input, "What is their jersey Number?\n");
				}

				// ask user for the two-digit year of their car
				int carYear = intCheck(input, "What is the two-digit model year of your car?\n");
				// carYear check loop - make sure input is appropriate to a car model year
				while (true) {
					if (carYear > 0 && carYear < 100) {
						break;
					}
					System.out.println("Please enter a two digit numbers..");
					carYear = intCheck(input, "");
				}

				// ask user for first name of favorite actor/actress
				String favActor = stringCheck(input, "What is the first name of your favorite actor or actress\n");

				// ask user to enter a random number between 1 and 50
				int userNum = intCheck(input, "Enter a random number between 1 and 50\n");

				// randoLoop checks to see if the number input is between 1 and 50, it loops
				// until an appropriate number is input
				randoLoop: while (userNum < 1 || userNum > 50) {
					System.out.println("The number you entered is not between 1 and 50");
					System.out.println("Please try again...");
					userNum = Integer.parseInt(input.nextLine());
					if (userNum >= 1 || userNum <= 50) {
						break randoLoop;
					}
				}

				// store 3 random numbers
				Random randoNum = new Random();
				int randomNum1 = randoNum.nextInt(51);
				int randomNum2 = randoNum.nextInt(51);
				int randomNum3 = randoNum.nextInt(51);

				// create array for random values, add the values, then randomly select a value
				int[] lotteryList = new int[3];
				lotteryList[0] = randomNum1;
				lotteryList[1] = randomNum2;
				lotteryList[2] = randomNum3;
				// stores random number between 1 and 3 to randomly select a randomNum
				int m = randoNum.nextInt(3);
				int magicBall = lotteryList[m];

				// magicBall number formula
				// -- checks if a jersey number was declared and use that if so, if not uses
				// lucky number
				if (jerseyNum != 0) {
					magicBall *= jerseyNum;
				} else {
					magicBall *= luckyNum;
				}
				magicBall = clampToRange(magicBall, 1, 75);

				// lottery Number #1 = user's car model year + user's lucky number
				// lottery number #2 = user's random number - one of the random generated
				// numbers
				// lottery number #3 = the number 42
				// lottery number #4 = user's pet age + their car model year
				// lottery number #5 = user's QB jersey number + pet age + lucky number
				// if there is no QB jersey number then user lucky number twice
				//
				// new array to store lottery numbers
				int[] lotteryNumbers = new int[5];
				lotteryNumbers[0] = carYear + luckyNum;
				lotteryNumbers[1] = userNum - lotteryList[m];
				lotteryNumbers[2] = 42;
				lotteryNumbers[3] = petAge + carYear;
				if (jerseyNum != 0) {
					lotteryNumbers[4] = jerseyNum + petAge + luckyNum;
				} else {
					lotteryNumbers[4] = petAge + (luckyNum * 2);
				}

				// runs array through dupCheck to check for duplicates
				// and clampToRange to keep values between 1 & 65
				for (int i = 0; i < lotteryNumbers.length; i++) {
					lotteryNumbers = dupCheck(i, lotteryNumbers);
					lotteryNumbers[i] = clampToRange(lotteryNumbers[i], 1, 65);
				}

				// sort array of numbers (ascending)
				Arrays.sort(lotteryNumbers);

				// print lottery numbers and magic ball values
				System.out.println("--------------------------------------------");
				System.out.printf("Lottery Numbers: %d, %d, %d, %d, %d   Magic ball: %d", lotteryNumbers[0],
						lotteryNumbers[1], lotteryNumbers[2], lotteryNumbers[3], lotteryNumbers[4], magicBall);
				System.out.println();
				System.out.println("--------------------------------------------");

				// ask user if they would like to continue, if yes then restart lotteryLoop, if
				// no then break loop

				System.out.println("Would you like to answer more questions and print another set of lottery numbers?");
				Boolean continueRequest = yesCheck(input);
				if (continueRequest.equals(true)) {
					continue lotteryLoop;
				} else {
					break mainLoop;
				}
			}
			break mainLoop;
		}
		System.out.println("Thank you and have a great day!");
	}

	// method to take array[i] and check if it has a duplicate, if so then it will
	// be altered
	// and returns the array
	static int[] dupCheck(int placeholder, int[] array) {
		for (int i = placeholder + 1; i < array.length; i++) {
			if (array[i] == array[placeholder]) {
				array[i] = (array[i] + 50) / 2;
			}
		}
		return array;
	}

	// method used to alter values within given range
	static int clampToRange(int value, int lowerValue, int upperValue) {
		// we can say clampToRange(300, 1, 75) for example
		int rangeWidth = upperValue - lowerValue + 1;

		// if the value is larger than 75, then subtract 75 until magicBall is lower
		// than 75
		while (value > upperValue) {
			value -= rangeWidth;
		}
		// if value is less than 75 then add 75 until magicBall is above 1
		while (value < lowerValue) {
			value += rangeWidth;
		}
		return value;
	}

	// a 'template' class for asking the user questions with a 'String check' loop
	static String stringCheck(Scanner input, String sentence) {

		String userInput = null;
		System.out.print(sentence);
		userInput = input.nextLine().toString();
		while (true) {
			if (Pattern.matches(".*[A-Za-z]+.*", userInput)) {
				break;
			}

			System.out.println("You have entered an incorrect value, please an appropriate entry");
			userInput = input.nextLine();
		}

		return userInput;
	}

	// a 'template' class for asking the user questions with an 'int check' loop
	static int intCheck(Scanner input, String sentence) {
		String userInput;
		int userNum;
		System.out.print(sentence);
		userInput = input.nextLine();
		while (true) {
			if (userInput.matches("\\d+")) {
				userNum = Integer.parseInt(userInput);
				return userNum;
			}
			System.out.println("You have entered an incorrect value, please an appropriate entry");
			userInput = input.nextLine();
		}
	}

	// method to ask user for yes or no then returns boolean 
	// [yes = true / no = false]
	static Boolean yesCheck(Scanner input) {
		System.out.println("Please enter 'yes' or 'no'...");
		String userInput = input.nextLine().toLowerCase();
		while (true) {
			if (userInput.charAt(0) == 'y') {
				return true;
			} else if (userInput.charAt(0) == 'n') {
				return false;
			} else {
			}
			System.out.println("You did not enter an appropriate entry...");
			System.out.println("Please enter 'yes' or 'no'");
			userInput = input.nextLine().toLowerCase();
		}
	}

	// print numbers 0-9 using char values
	public static void printNumbers() {
		// TODO: print valid numeric input
		// "0123456789"

		for (char value = 48; value < '0' + 10; value++) {
			System.out.print(value);
		}
		System.out.println();
	}

	// print alphabet in lower case using char values
	public static void printLowerCase() {
		// TODO: print valid lower case alphabetic input
		// "a,b,c,d,e,f,......"

		for (char value = 97; value < 123; value++) {
			System.out.print(value);
		}
		System.out.println();
	}

	// print alphabet in upper case using char values
	public static void printUpperCase() {
		// TODO: print valid upper case alphabetic input
		// "A,B,C,D,E,F,....."

		for (char value = 65; value < 90; value++) {
			System.out.print(value);
		}
		System.out.println();
	}

}
