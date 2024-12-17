import java.util.Scanner;
import edu.cmu.ri.createlab.terk.robot.finch.Finch;
public class Task6 {	
	public static void main(String[] args) {
		boolean start = promptReady();
		if(start == true) {
			main();
		}
	}
	public static void main() {
		String hexadecimal = promptInput();
		hexadecimal = checkSize(hexadecimal);
		hexadecimal = checkBoundary(hexadecimal);
		String binary = generateBinary(hexadecimal);
		int denary = binaryToDenary(binary);
		int octal = denaryToOctal(denary);
		int speed = calculateSpeed(octal);
		int red = generateRed(denary);
		int green = generateGreen(denary);
		int blue = generateBlue(red, green);
		System.out.println(hexadecimal + ", " + octal + ", " + denary + ", " + binary + ", " + "speed = " + speed + ", " + "LED colour (red " + red + ", " + "green " + green + ", " + "blue " + blue  + ").");
		moveFinch(red, green, blue, speed, binary);
		boolean restart = promptReady();
		if(restart == true) {
			main();
		}
	}
	public static boolean promptReady() {
		System.out.println("Enter 'start' to begin or 'exit' to quit: ");
		Scanner userReady = new Scanner(System.in);
		String choice = userReady.next();
		choice = choice.toUpperCase();
		boolean start = false;
		if(choice.equals("START")) {
			start = true;
		}
		else if(choice.equals("EXIT")) {
			start = false;
		}
		else {
			System.out.println("Input can only be 'start' or 'exit'");
			start = promptReady();
		}
		return start;
	}	
	public static String promptInput() {
		Scanner userInput = new Scanner(System.in);
		System.out.println("Enter a hexadecimal number: ");
		String hexadecimal = userInput.next();
		return hexadecimal.toUpperCase();
	}
	public static String checkSize(String hexadecimal) {
		while(hexadecimal.length() != 2) {
			System.out.println("Input is not of size 2");
			hexadecimal = promptInput();
		}
		return hexadecimal;
	}
	public static String checkBoundary(String hexadecimal) {
		for(int i = 0; i < 2; i++) {
			char hex = hexadecimal.charAt(i);
			if(!Character.isDigit(hex) && !(hex >= 'A' && hex <= 'F')) {
				System.out.println("Input is outside of hexadecimal boundaries");
				hexadecimal = checkBoundary(promptInput());
			}
		}
		return hexadecimal;	
	}
	public static String generateBinary(String hexadecimal) {
		String binary = "";
		binary = binary + intToBinary(hexValue(hexadecimal.charAt(0)));
		binary = binary + intToBinary(hexValue(hexadecimal.charAt(1)));
		return binary;
	}
	public static int hexValue(char hexSegment) {
		int conversion = 0;
		switch(hexSegment) {
			case 'A':
				conversion = 10;
			break;
			case 'B':
				conversion = 11;
			break;
			case 'C':
				conversion = 12;
			break;
			case 'D':
				conversion = 13;
			break;
			case 'E':
				conversion = 14;
			break;
			case 'F':
				conversion = 15;
			break;
			default:
				conversion = hexSegment - 48;
			break;
		}
		return conversion;
	}
	public static int powerOf(int power) {
		int answer = 2;
		if(power == 0) {
			answer = 1;
		}
		else {
			for(int i = power; i > 1; i--) {
				answer = answer * 2;
			}
		}		
		return answer;
	}
	public static String intToBinary(int hexValue) {
		String conversion = "";
		for(int i = 3; i >= 0; i--) {
			if(hexValue >= powerOf(i)) {
				conversion = conversion + "1";
				hexValue = hexValue - powerOf(i);
			}
			else {
				conversion = conversion + "0";
			}
		}
		return conversion;
	}
	public static int binaryToDenary(String binary) {
		int answer = 0;
		for(int i = (binary.length() - 1); i >= 0; i--) {
			if((binary.charAt(i)-48) == 1) {
				answer = answer + powerOf((binary.length() - 1) - i);
			}
		}
		return answer;
	}
	public static int denaryToOctal(int denary) {
		int answer = 0;
		int digits = denary % 8;
		int factor = denary / 8;
		int tens = (factor % 8) * 10;
		int hundreds = (factor / 8) * 100;		
		answer = answer + digits + tens + hundreds;
		return answer;
	}
	public static int calculateSpeed(int octal) {
		int speed = octal;
		if(speed < 60) {
			speed = speed + 30;
		}
		if(speed > 255) {
			speed = 255;
		}
		return speed;
	}
	public static int generateRed(int denary) {
		int colour = denary;
		return colour;
	}
	public static int generateGreen(int denary) {
		int colour = (denary % 80) + 60;
		return colour;
	}
	public static int generateBlue(int red, int green) {
		int colour = (red + green) / 2;
		return colour;
	}
	public static void moveFinch(int red, int green, int blue, int speed, String binary) {
		int forward = speed;
		int backward = -speed;
		Finch dance = new Finch();
		dance.setLED(red, green, blue);
		for(int i = (binary.length() - 1); i >= 0; i--) {
			if((binary.charAt(i) - 48) == 1) {
				dance.setWheelVelocities(forward, forward, 1000);
			}
			else {
				dance.setWheelVelocities(backward, backward, 1000);
			}
		}
		dance.quit();
	}
}