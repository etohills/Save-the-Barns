import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SaveTheBarns {
	//declare variables
	static double mentotalcont = 0.0, femaletotalcont = 0.0, demtotalcont = 0.0, repubtotalcont = 0.0, indtotalcont = 0.0, mendemtotalcont = 0.0, femdemtotalcont = 0.0, menrepubtotalcont = 0.0, femrepubtotalcont = 0.0, menindtotalcont = 0.0, femindtotalcont = 0.0, overalltotalcont = 0.0;
	static int menrecordcount = 0, femalerecordcount = 0, demrecordcount = 0, repubrecordcount = 0, indrecordcount = 0, mendemrecordcount = 0, femdemrecordcount = 0, menrepubrecordcount = 0, femrepubrecordcount = 0, menindrecordcount = 0, femindrecordcount = 0, overallrecordcount = 0;
	static String oErrMsg;
	static String iString;	 		//generic input string
	static String iContributors;	// full name of contributors (last name and First name )
	static String iAddress;			//address
	static String  iCity;			//city string 
	static String iState;			//State string 
	static Scanner ContributionScanner;	//input device to read from .dat file
	static PrintWriter pw;			//used to write data to the .prt file	
	static PrintWriter errpw;		// to print to the error .prt file
	static boolean eof = false;		//used to control user loop
	static String iZip;				//zip string 
	static int CZip;
	static String iParty;			// D, I AND OR R
	static char CiParty;
	static String iGender;			//f or m
	static char CiGender;
	static String iContribution; 	//Amnt people will contribute to the cause
	static double DiContribution;
	private static List<String> errors;
	private static String record;	//string used to hold entire record being read
	static String input = "";
public static void main(String[] args) {
		System.out.print("Would you like to process the report only for contributions over $500?");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try { 
			input = br.readLine();
		} catch (IOException e) {
			
		}
		//call init()		
		init();
		
		//heading for valid data in Console
		//heading();
		//loop until no more records
		while(!eof) {
			if(validation()) {
			} else {
			Noerror() ;
			}
			input();									
		    }
		    printReport();
		
		errpw.close();
	}		

	public static void init() {
		try {
			ContributionScanner = new Scanner(new File("contributors.dat"));
			ContributionScanner.useDelimiter(System.getProperty("line.separator"));
		} catch (FileNotFoundException e1) {
			System.out.println("Couldn't find file, fix code!!");
			System.exit(1);
		}
				
		//initialize the PrintWriter object
		try {
			errpw = new PrintWriter(new File ("error.prt"));
		} catch (FileNotFoundException e) {
			System.out.println("Output file error");
		}
		input();
		
	}

	public static void input() {
		//as long as there are more records to be read
		if (ContributionScanner.hasNext()) { //hasNext means if the .dat file has anything in it, it will keep going 
			record = ContributionScanner.next();
			iContributors = record.substring(0,24); iContributors = iContributors.trim();
			iAddress = record.substring(24,49); iAddress = iAddress.trim();
			iCity = record.substring(49,64); iCity = iCity.trim();
			iState = record.substring(64,66); iState = iState.trim(); 
			iZip = record.substring(66,71); iZip = iZip.trim();
			iParty = record.substring(71);	
			CiParty = iParty.charAt(0);
			/*
			 * If part of party add + 1 to record count and add the records contribution to total contribution
			 */
			iGender = record.substring(72);
			CiGender = iGender.charAt(0);
			iContribution=record.substring(73,80);
			try {
			DiContribution=Double.parseDouble(iContribution);
			} catch (NumberFormatException e) {
			}
			calcs();
		}
		else {
			eof=true;	//no more records so set eof to true
		}
	}
	private static void calcs () {
		if(input.toUpperCase().equals("Y")) {
			//For parties Posibilites > $ 500
		if(DiContribution > 500) {
		if (CiParty == 'D' & DiContribution > 500) {
			demrecordcount++;
			demtotalcont += DiContribution;
		} else if (CiParty == 'R' & DiContribution > 500) {
			repubrecordcount++;
			repubtotalcont += DiContribution;
		} else if (CiParty == 'I' & DiContribution > 500) {
			indrecordcount++;
			indtotalcont += DiContribution;
		}
		// For Male Posibilities > $ 500
		if (CiGender == 'M' & DiContribution > 500) {
		if (CiParty == 'D' & DiContribution > 500) {
			mendemrecordcount++;
			mendemtotalcont += DiContribution;
		} else if(CiParty == 'R' & DiContribution > 500) {
			menrepubrecordcount++;
			menrepubtotalcont += DiContribution;
		} else if(CiParty == 'I' & DiContribution > 500) {
			menindrecordcount++;
			menindtotalcont += DiContribution;
		}
			menrecordcount++;
			mentotalcont += DiContribution;
			//for Female Posibilities >$500
		} else if (CiGender == 'F' & DiContribution > 500) {
		if (CiParty == 'D' & DiContribution > 500) {
			femdemrecordcount++;
			femdemtotalcont += DiContribution;
		} else if(CiParty == 'R' & DiContribution > 500) {
			femrepubrecordcount++;
			femrepubtotalcont += DiContribution;
		} else if(CiParty == 'I' & DiContribution > 500) {
			femindrecordcount++;
			femindtotalcont += DiContribution;
		}
			femalerecordcount++;
			femaletotalcont += DiContribution;
		}
		overallrecordcount++;
		overalltotalcont += DiContribution;
		}
		
		//If input is N then it will process everything in the file 
		} else if (input.toUpperCase().equals("N")) {
			if (CiParty == 'D') {
				demrecordcount++;
				demtotalcont += DiContribution;
				
			} else if (CiParty == 'R') {
				repubrecordcount++;
				repubtotalcont += DiContribution;
				
			} else if (CiParty == 'I') {
				indrecordcount++;
				indtotalcont += DiContribution;
			}
			if (CiGender == 'M') {
			if (CiParty == 'D') {
				mendemrecordcount++;
				mendemtotalcont += DiContribution;
				
			} else if(CiParty == 'R') {
				menrepubrecordcount++;
				menrepubtotalcont += DiContribution;
				
			} else if(CiParty == 'I') {
				menindrecordcount++;
				menindtotalcont += DiContribution;
			}
				menrecordcount++;
				mentotalcont += DiContribution;
			} else if (CiGender == 'F') {
			if (CiParty == 'D') {
				femdemrecordcount++;
				femdemtotalcont += DiContribution;
				
			} else if(CiParty == 'R') {
				femrepubrecordcount++;
				femrepubtotalcont += DiContribution;
				
			} else if(CiParty == 'I') {
				femindrecordcount++;
				femindtotalcont += DiContribution;
				
			}
				femalerecordcount++;
				femaletotalcont += DiContribution;
			}
			overallrecordcount++;
			overalltotalcont += DiContribution;
		} else {
		}
	}
	
	private static boolean validation() {
		//validation for empty values 
		boolean valid = true;
		if(iContributors.trim().isEmpty()) {
			oErrMsg = "Contributor name is empty";
			return false;
		} else if (iAddress.trim().isEmpty()) {
			oErrMsg = "Address is empty";
			return false;
		} else if (iCity.trim().isEmpty()) {
			oErrMsg = "City is empty";
			return false;
		} else if (iState.trim().isEmpty()) {
			oErrMsg = "State is empty";
			return false;
			
			
			
		} 
		
		
		//else if (!iZip.matches("\\d{5}")) {
		//	oErrMsg = "Zip needs at least 5 numbers";
			
		//	return false;
			
		
		//}
		else if (CiParty == ' ') {
			oErrMsg = "Party is empty";
			return false;
		} else if (CiGender == ' ') {
			oErrMsg = "Gender is empty";
			return false;
		}
		
		if (iContributors.length() > 25) {
			oErrMsg = "Contributor name has exceeded 25 spaces.";
			return false;
		}	else if (iAddress.length() > 25) {
			oErrMsg = "Address has exceeded 25 spaces.";
			return false;
		}	else if (iCity.length() > 15) {
			oErrMsg = "City has exceeded 15 spaces.";
			return false;
		}	else if (iState.length() < 2 || iState.length() > 2 ) {
			oErrMsg = "state has to be at least 2 spaces; no more , no less.";
			return false;
		}	
			else if (iZip.length() < 5 || iZip.length() > 5 )  {
			oErrMsg = "zip has to be at least 5 numbers.";
			return false;
		}
		
		
		
		//validation for wrong D, R, I values 
		if (CiParty != 'D' && CiParty != 'R' && CiParty != 'I') {
			oErrMsg = "Party isn't a valid character";
			return false;
		}
		//validation for wrong F, M values 
		if (CiGender != 'M' && CiGender != 'F') {
			oErrMsg = "Gender isn't a valid character";
			return false;
		}
		//validation for values to 0.1  cent 9999.99$ 
		if(DiContribution < .01 || DiContribution > 9999.99) {
			oErrMsg = "Contribution must be between .01 to 9999.99";
			return false;
		}
		//validation for Contributions Amount 
		try {
			Double.parseDouble(iContribution);
			} catch (NumberFormatException e) {
				oErrMsg = "Contribution includes non-numeric characters";
				return false;
			}
		
		return valid;
	}
	private static void Noerror()  {
		errpw.format("%-66s%6s%-60s%n", record, " ", oErrMsg);
	}
	public static void printReport() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy ");
		Date date = new Date();		
		DecimalFormat df = new DecimalFormat(".00");
		System.out.println("Date: " + dateFormat.format(date));
		System.out.println("");
		if(input.toUpperCase().equals("Y")) {
			System.out.println("-------------------------------------CONTRIBUTION REPORT------------------------------------");
			System.out.println("**Over 500 SUMMARY REPORT:**        ---Save--The--Barn---");}
		else {
			System.out.println("-------------------------------------CONTRIBUTION REPORT------------------------------------");
			System.out.println("SUMMARY REPORT for ALL data:        ---Save--The--Barn---");
			}
		System.out.println("");  
		
		
		System.out.format( "%-30s%5s%25s%5s%25s%n", "Mens Record Count:  ", " " , " Total Contribution: " ,     "    "    ,  "      Average Contribution: " );
		System.out.format( "%-30s%5s%25s%5s%25s%n",  menrecordcount    ,"  ",   "$" + df.format(mentotalcont) , " ",  "$" +        df.format(mentotalcont/menrecordcount)); 
		System.out.println("");
		
		System.out.format( "%-30s%5s%25s%5s%25s%n", "Womens Record Count: ", " " , " Total Contribution: ", "      " ,  "     Average Contribution: " );
		System.out.format( "%-30s%5s%25s%5s%25s%n",  femalerecordcount , "  " , "$"+  df.format(femaletotalcont), " ",   "$"+      df.format(femaletotalcont/femalerecordcount));
		System.out.println("");
		
		System.out.format( "%-30s%5s%25s%8s%25s%n", "Democratic Record Count: " , " " , " Total Contribution: ", " " , " Average Contribution: " );
		System.out.format( "%-30s%5s%25s%5s%25s%n",  demrecordcount    , "  " , "$"+  df.format(demtotalcont), " ",       "$"+     df.format(demtotalcont/demrecordcount));
		System.out.println("");  
		
		System.out.format( "%-30s%5s%25s%8s%25s%n", "Republicans Record Count: " , " " , " Total Contribution: ", " " , " Average Contribution: " );
		System.out.format( "%-30s%5s%25s%5s%25s%n",  repubrecordcount    , "  " , "$"+  df.format(repubtotalcont), " ",       "$"+ df.format(repubtotalcont/repubrecordcount));
		System.out.println("");  
		
		System.out.format( "%-30s%5s%25s%5s%25s%n", "Independent Record Count: " , " " , " Total Contribution: " ,     "    "    ,  "      Average Contribution: " );
		System.out.format( "%-30s%5s%25s%5s%25s%n",  indrecordcount    ,"  ",   "$" + df.format(indtotalcont) , " ",  "$" +        df.format(indtotalcont/indrecordcount));
		System.out.println("");
		
		
		System.out.format( "%-30s%5s%25s%5s%25s%n"," Democratic Men Record Count: " , " " , " Total Contribution: " ,     "    "    ,  "      Average Contribution: " );
		System.out.format( "%-30s%5s%25s%5s%25s%n",  mendemrecordcount    ,"  ",   "$" + df.format(mendemtotalcont) , " ",  "$" +       df.format(mendemtotalcont/mendemrecordcount));
		System.out.println("");
		
		System.out.format( "%-30s%4s%25s%5s%25s%n", "Democratic Women Record Count: " , " " , " Total Contribution: " ,     "    "    ,  "      Average Contribution: " );
		System.out.format( "%-30s%5s%25s%5s%25s%n",  femdemrecordcount    ,"  ",   "$" + df.format(femdemtotalcont) , " ",  "$" +       df.format(femdemtotalcont/femdemrecordcount));
		System.out.println(""); 
		
		System.out.format( "%-30s%5s%25s%5s%25s%n","Republican Men Record Count: " , " " , " Total Contribution: " ,     "    "    ,  "      Average Contribution: " );
		System.out.format( "%-30s%5s%25s%5s%25s%n",  menrepubrecordcount    ,"  ",   "$" + df.format(menrepubtotalcont) , " ",  "$" +       df.format(menrepubtotalcont/menrepubrecordcount));
		System.out.println(""); 
		
		System.out.format( "%-30s%4s%25s%5s%25s%n","Republican Women Record Count: " , " " , " Total Contribution: " ,     "    "    ,  "      Average Contribution: " );
		System.out.format( "%-30s%5s%25s%5s%25s%n",  femrepubrecordcount    ,"  ",   "$" + df.format(femrepubtotalcont) , " ",  "$" +       df.format(femrepubtotalcont/femrepubrecordcount));
		System.out.println(""); 
		
		
		System.out.format( "%-30s%5s%25s%5s%25s%n","Independent Men Record Count: ", " " , " Total Contribution: " ,     "    "    ,  "      Average Contribution: " );
		System.out.format( "%-30s%5s%25s%5s%25s%n",  menindrecordcount    ,"  ",   "$" + df.format(menindtotalcont)  , " ",  "$" +       df.format(menindtotalcont/menindrecordcount));
		System.out.println(""); 
		
		System.out.format( "%-30s%3s%25s%5s%25s%n","Independent Women Record Count: ", " " , " Total Contribution: " ,     "    "    ,  "      Average Contribution: " );
		System.out.format( "%-30s%5s%25s%5s%25s%n",  femindrecordcount    ,"  ",   "$" + df.format(femindtotalcont)  , " ",  "$" +      df.format(femindtotalcont/femindrecordcount));
		System.out.println(""); 
		
		System.out.format( "%-30s%5s%25s%5s%25s%n","Overall Record Count: ", " " , " Total Contribution: " ,     "    "    ,  "      Average Contribution: " );
		System.out.format( "%-30s%5s%25s%5s%25s%n",  overallrecordcount    ,"  ",   "$" + df.format(overalltotalcont)  , " ",  "$" +     df.format(overalltotalcont/overallrecordcount));
		System.out.println(""); 
		
		
	}
	public static void saveErrors() {
		if (errors.size() > 0) {
			PrintWriter writer;
			try {
				writer = new PrintWriter("error.prt", "UTF-8");
				for (String err : errors) {
					writer.println(err);
				}
				writer.close();
			} catch (Exception e) {
				System.out.println("Could not save the error file!");
			}
		}
	}
	
}



