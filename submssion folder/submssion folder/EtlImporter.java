package edu.brown.cs.cs127.etl.importer;


import au.com.bytecode.opencsv.CSVReader; //for the CSVReader objects;
import java.util.HashMap;
import java.io.FileReader; 
import java.sql.*; //(for jdbc)
import java.io.*; 
import java.util.ArrayList; 
import java.util.Objects; //(for various useful Java functions)
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList; //If you are planning to use ArrayLists

public class EtlImporter
{
	/**
	 * You are only provided with a main method, but you may create as many
	 * new methods, other classes, etc as you want: just be sure that your
	 * application is runnable using the correct shell scripts.
	 */
	public static void main(String[] args) throws Exception
	{
		if (args.length != 4)
		{
			System.err.println("This application requires exactly four parameters: " +
					"the path to the airports CSV, the path to the airlines CSV, " +
					"the path to the flights CSV, and the full path where you would " +
					"like the new SQLite database to be written to.");
			System.exit(1);
		}

		String AIRPORTS_FILE = args[0];
		String AIRLINES_FILE = args[1];
		String FLIGHTS_FILE = args[2];
		String DB_FILE = args[3];
		System.out.println("apple");
		FileReader filereader = new FileReader(AIRLINES_FILE); 
		CSVReader reader = new CSVReader(new FileReader(AIRLINES_FILE));
	





		String a = "2013-01-04";
		String b = "2013-01-03";
		if (a.compareTo(b)<=0){
			System.out.println("sp sdas das");
		}
		String c = "10:50";
		String d = "10:36";
		if (c.compareTo(d)<=0){
			System.out.println("pppps");
		}

		// a sample date to convert
		String sampleDateString = "4:45 PM";
		String  s2 = "10:45";
		String sampleDateString3 = "10:45 AM";
       

		String sampleDateString4 = "06/23/1996";


		// String s5 = "10:34";
		// String s6 = "22:15";
		// String sampleDateString7 = "06/23/1996";
		// if (s5.compareTo(s6)<0){
		// 	System.out.println("sad");
		// }else{
		// 	System.out.println("led");
		// }

		// DateFormat timePM = new SimpleDateFormat("HH:mm");
		// DateFormat timePM1 = new SimpleDateFormat("h:mm a");
		// timePM1.setLenient(false);


		// try{
		// 	Date sampleDate1 = timePM1.parse(s2);
		// 	String ss = timePM.format(sampleDate1);
		// 	System.out.println(ss);
		// 	s2 = ss;
		// }
		// catch (ParseException p){
		// 	System.out.println("am exc");
		// 	System.out.println(s2);


		// }
		 
		//  // choose a standard date format that will be used throughout the database
		// DateFormat standardFormat = new SimpleDateFormat("yyyy-MM-dd");
		 
		//  // one possible date format to be converted
		// DateFormat sampleDateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		// DateFormat sampleDateFormat2 = new SimpleDateFormat("MM-dd-yyyy");
		// DateFormat sampleDateFormat3 = new SimpleDateFormat("yyyy/MM/dd");


		// //DateFormat timePM = new SimpleDateFormat("HH:mm");

		// //DateFormat timePM1 = new SimpleDateFormat("h:mm a");


		// //DateFormat sampleDateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		//   // you need to set this flag to false for every possible DateFormat that you will be checking against.
		// sampleDateFormat1.setLenient(false);
		// sampleDateFormat2.setLenient(false);
		// sampleDateFormat3.setLenient(false);
		// //timePM.setLenient(false);
		
		 
		// try {

		// 	Date sampleDate1 = sampleDateFormat1.parse(sampleDateString4);
			 
		//  	// standardize the sample date from "MM-dd-yyyy" to "yyyy-MM-dd"
		// 	//String sampleDateStringNormalized = timePM.format(sampleDate);
			 
		// 	String sn =standardFormat.format(sampleDate1);
		 
		//  	// this will print "1992-01-13"
		// 	//System.out.println(sampleDateStringNormalized);
		// 	System.out.println(sn);
		// 	sampleDateString4 = sn;

	    // }
		//   catch (ParseException e) {

		// 	  try {
		// 		Date sampleDate2 = sampleDateFormat2.parse(sampleDateString4);
		// 		String sn =standardFormat.format(sampleDate2);
		// 		System.out.println(sn);
		// 	    sampleDateString4 = sn;

		// 	  }
		// 	  catch (ParseException f) {
		// 		  try {
		// 			Date sampleDate3 = sampleDateFormat3.parse(sampleDateString4);
		// 			String sn =standardFormat.format(sampleDate3);
		// 			System.out.println(sn);
		// 			sampleDateString4 = sn;

		// 		  }
		// 		  catch (ParseException h){
		// 			System.out.println("keep it real");
		// 			System.out.println(sampleDateString4);


		// 		  }

		// 	  }
			
			

			
			
			


			

		 	// the sample format doesn't match the sample date string.
		 	// now you should try parsing with a different sample format
			 
			
		  
		System.out.println("banana");

		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);
		Statement stat = conn.createStatement();
		stat.executeUpdate("PRAGMA foreign_keys = ON;");
		stat.executeUpdate("PRAGMA synchronous = OFF;");
		stat.executeUpdate("PRAGMA journal_mode = MEMORY;");
		stat.executeUpdate("DROP TABLE IF EXISTS airlines;");
		stat.executeUpdate("DROP TABLE IF EXISTS tairports;");
		stat.executeUpdate("DROP TABLE IF EXISTS airports;");
		stat.executeUpdate("DROP TABLE IF EXISTS tflights;");
		stat.executeUpdate("DROP TABLE IF EXISTS csh;");
		stat.executeUpdate("DROP TABLE IF EXISTS dcsh;");
		stat.executeUpdate("DROP TABLE IF EXISTS datetime;");
		stat.executeUpdate("DROP TABLE IF EXISTS helpair;");
		stat.executeUpdate("DROP TABLE IF EXISTS flights;");
		stat.executeUpdate("DROP TABLE IF EXISTS kflights;");



		stat.executeUpdate("CREATE TABLE airlines (airline_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, airline_code CHAR(20), airline_name CHAR(20)); ");
		stat.executeUpdate("CREATE TABLE tairports (airport_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, airport_code CHAR(20), airport_name CHAR(20), city CHAR(20), state CHAR(20)); ");
		stat.executeUpdate("CREATE TABLE tflights (flight_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, flight_num INTEGER NOT NULL, origin_airport_id INETEGER, dest_airport_id INTEGER, departure_dt CHAR(20) NOT NULL, depart_diff CHAR(20) NOT NULL, arrival_dt CHAR(20) NOT NULL, arrival_diff CHAR(20) NOT NULL, cancelled INTEGER NOT NULL, carrier_delay INTEGER NOT NULL, weather_delay INTEGER NOT NULL, air_traffic_delay INTEGER NOT NULL, security_delay INTEGER NOT NULL,FOREIGN KEY(origin_airport_id) REFERENCES tairports(airport_id),FOREIGN KEY(dest_airport_id) REFERENCES tairports(airport_id),  CONSTRAINT weather_delay CHECK (weather_delay <999999) ,    CONSTRAINT air_traffic_delay CHECK (air_traffic_delay <999999), CONSTRAINT security_delay CHECK (security_delay <999999), CONSTRAINT carrier_delay CHECK (carrier_delay <999999)  ); "); //,  CONSTRAINT weather_delay CHECK (weather_delay>0)
		stat.executeUpdate("CREATE TABLE csh (airport_code, city CHAR(20), state CHAR(20)); ");
		//stat.executeUpdate("CREATE TABLE datetime (date CHAR(20), time CHAR(20)); ");
		stat.executeUpdate("CREATE TABLE helpair (flight_id INTEGER PRIMARY KEY AUTOINCREMENT, airline_code CHAR(20), ori_p_code CHAR(20), des_p_code CHAR(20)); ");


		PreparedStatement prep = conn.prepareStatement("INSERT INTO airlines (airline_code, airline_name) VALUES (?, ?)");
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null){
			prep.setString(1, nextLine[0]);
			prep.setString(2, nextLine[1]);
			prep.addBatch();
		}
		
		conn.setAutoCommit(false);
        prep.executeBatch(); //actually executes the batch of sql commands
		conn.setAutoCommit(true); 
		
		FileReader filereader2 = new FileReader(AIRPORTS_FILE); 
		CSVReader reader2 = new CSVReader(new FileReader(AIRPORTS_FILE));

		prep = conn.prepareStatement("INSERT INTO tairports (airport_code, airport_name) VALUES (?, ?)");
		String[] nextLine2;
		while ((nextLine2 = reader2.readNext()) != null){

			prep.setString(1, nextLine2[0]);
			prep.setString(2, nextLine2[1]);
			prep.addBatch();
		}

		
        conn.setAutoCommit(false);
        prep.executeBatch(); //actually executes the batch of sql commands
		conn.setAutoCommit(true); 
		
		
		

		FileReader filereader5 = new FileReader(FLIGHTS_FILE); 
		CSVReader reader5 = new CSVReader(new FileReader(FLIGHTS_FILE));
		prep = conn.prepareStatement("INSERT INTO helpair (airline_code, ori_p_code, des_p_code) VALUES (?, ?, ?)");
		String[] nextLine5;
		while ((nextLine5 = reader5.readNext()) != null){
			prep.setString(1, nextLine5[0]);
			prep.setString(2, nextLine5[2]);
			prep.setString(3, nextLine5[5]);
			prep.addBatch();
		}
        
		conn.setAutoCommit(false);
        prep.executeBatch(); //actually executes the batch of sql commands
		conn.setAutoCommit(true); 
		






		FileReader filereader4 = new FileReader(FLIGHTS_FILE); 
		CSVReader reader4 = new CSVReader(new FileReader(FLIGHTS_FILE));
		prep = conn.prepareStatement("INSERT INTO csh (airport_code, city, state) VALUES (?, ?, ?)");
		String[] nextLine4;
		while ((nextLine4 = reader4.readNext()) != null){
			prep.setString(1, nextLine4[2]);
			prep.setString(2, nextLine4[3]);
			prep.setString(3, nextLine4[4]);


			prep.setString(1, nextLine4[5]);
			prep.setString(2, nextLine4[6]);
			prep.setString(3, nextLine4[7]);
			
			prep.addBatch();
		}
        
		conn.setAutoCommit(false);
        prep.executeBatch(); //actually executes the batch of sql commands
		conn.setAutoCommit(true); 
		


		
		

		//PreparedStatement selectAirport = conn.prepareStatement("SELECT airline_id FROM airlines WHERE airline_code = ?");
		//selectAirport.setString(2, "JetClub AG"); 
		//ResultSet airport_id = selectAirport.executeQuery();
		//System.out.println(airport_id.getInt(1));


        FileReader filereader3 = new FileReader(FLIGHTS_FILE); 
		CSVReader reader3 = new CSVReader(new FileReader(FLIGHTS_FILE));
		int d_tokon = 0;
		
		prep = conn.prepareStatement("INSERT INTO tflights (flight_num, departure_dt, depart_diff, arrival_dt, arrival_diff, cancelled, carrier_delay, weather_delay, air_traffic_delay, security_delay) VALUES (?,?,?,?,?,?,?,?,?,?)");
		String[] nextLine3;
		while ((nextLine3 = reader3.readNext()) != null){

			// PreparedStatement selectAirport = conn.prepareStatement("SELECT airport_id FROM airports WHERE airport_code = ?");
			// selectAirport.setString(1, ?); 
			// ResultSet airport_id = selectAirport.executeQuery();
			// airport_id.getInt(1);


		   String depart_date = nextLine3[8];
		   String arri_date = nextLine3[11];
		   DateFormat standardFormat = new SimpleDateFormat("yyyy-MM-dd");
		 
			// one possible date format to be converted
		   DateFormat sampleDateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		   DateFormat sampleDateFormat2 = new SimpleDateFormat("MM-dd-yyyy");
		   DateFormat sampleDateFormat3 = new SimpleDateFormat("yyyy/MM/dd");
 
		   sampleDateFormat1.setLenient(false);
		   sampleDateFormat2.setLenient(false);
		   sampleDateFormat3.setLenient(false);

		   try {
			   Date sampleDate1 = sampleDateFormat1.parse(depart_date);
			   String sn =standardFormat.format(sampleDate1);
			   depart_date = sn;
   
		   }
			catch (ParseException e) {
				 try {
				   Date sampleDate2 = sampleDateFormat2.parse(depart_date);
				   String sn =standardFormat.format(sampleDate2);
				   depart_date = sn;
				 }
				 catch (ParseException f) {
					 try {
					   Date sampleDate3 = sampleDateFormat3.parse(depart_date);
					   String sn =standardFormat.format(sampleDate3);
					   depart_date = sn;
   
					 }
					 catch (ParseException h){
					 }
   
				 }
				}

			try {
					Date sampleDate11 = sampleDateFormat1.parse(arri_date);
					String sn =standardFormat.format(sampleDate11);
					arri_date = sn;
		
				}
				  catch (ParseException q) {
					  try {
						Date sampleDate22 = sampleDateFormat2.parse(arri_date);
						String sn =standardFormat.format(sampleDate22);
						arri_date = sn;
					  }
					  catch (ParseException w) {
						  try {
							Date sampleDate33 = sampleDateFormat3.parse(arri_date);
							String sn =standardFormat.format(sampleDate33);
							arri_date = sn;
		
						  }
						  catch (ParseException s){
						  }
		
					  }
					}
			   




            if (depart_date.compareTo(arri_date)>0){
					d_tokon++;
			}







			String depart_time = nextLine3[9];
			String arri_time = nextLine3[12];

			DateFormat timePM = new SimpleDateFormat("HH:mm");
		    DateFormat timePM1 = new SimpleDateFormat("h:mm a");
		    timePM1.setLenient(false);


		    try{
			       Date sd1 = timePM1.parse(depart_time);
			       String ss = timePM.format(sd1);
			       depart_time = ss;
				}
		         catch (ParseException p){
				 }

			try{
					Date sd2 = timePM1.parse(arri_time);
					String sss = timePM.format(sd2);
					arri_time = sss;
				 }
				  catch (ParseException p){
				  }



			if (depart_date.compareTo(arri_date)==0 && depart_time.compareTo(arri_time)>0){
					d_tokon++;
			} 
			prep.setInt(1, Integer.parseInt(nextLine3[1])); //flight number 
			prep.setString(2, depart_date+" "+depart_time); //depart date

			prep.setString(3, nextLine3[10]); //depart diff 
			prep.setString(4, arri_date+" "+arri_time); //arr date
			
			prep.setString(5, nextLine3[13]); //arr diff 
			prep.setInt(6, Integer.parseInt(nextLine3[14]));//14
			prep.setInt(7, Integer.parseInt(nextLine3[15]));//15
			prep.setInt(8, Integer.parseInt(nextLine3[16]));//16
			prep.setInt(9, Integer.parseInt(nextLine3[17]));//17
			prep.setInt(10, Integer.parseInt(nextLine3[18]));//18
	        prep.addBatch();
		
            
		}
		System.out.println("token");
		System.out.println(d_tokon);
		conn.setAutoCommit(false);
		prep.executeBatch();
		conn.setAutoCommit(true); 



		stat.executeUpdate("CREATE TABLE dcsh AS SELECT DISTINCT * FROM csh");
		stat.executeUpdate("CREATE TABLE airports AS SELECT airport_id, tairports.airport_code, airport_name, dcsh.city, dcsh.state FROM tairports LEFT JOIN dcsh ON dcsh.airport_code = tairports.airport_code;");
	   
		
		stat.executeUpdate("DROP TABLE IF EXISTS f_lineid;");
		stat.executeUpdate("DROP TABLE IF EXISTS f_ori_port;");
		stat.executeUpdate("DROP TABLE IF EXISTS r_ori_port;");
		stat.executeUpdate("DROP TABLE IF EXISTS f_des_port;");
		stat.executeUpdate("DROP TABLE IF EXISTS r_des_port;");
		stat.executeUpdate("DROP TABLE IF EXISTS f_com;");
		stat.executeUpdate("DROP TABLE IF EXISTS f_line_port;");
		stat.executeUpdate("DROP TABLE IF EXISTS dflights;");
		
		
		stat.executeUpdate("CREATE TABLE f_lineid AS SELECT flight_id, airline_id FROM helpair INNER JOIN airlines ON airlines.airline_code = helpair.airline_code;");
		
		stat.executeUpdate("CREATE TABLE f_ori_port AS SELECT flight_id, airport_id FROM helpair INNER JOIN airports ON airports.airport_code = helpair.ori_p_code;");
		
		stat.executeUpdate("CREATE TABLE r_ori_port (flight_id INTEGER, origin_airport_id INTEGER);");

		stat.executeUpdate("INSERT INTO r_ori_port SELECT * FROM f_ori_port;");


        
		stat.executeUpdate("CREATE TABLE f_des_port AS SELECT flight_id, airport_id FROM helpair INNER JOIN airports ON airports.airport_code = helpair.des_p_code;");
		
        stat.executeUpdate("CREATE TABLE r_des_port (flight_id INTEGER, dest_airport_id INTEGER);");

		stat.executeUpdate("INSERT INTO r_des_port SELECT * FROM f_des_port;");

		stat.executeUpdate("CREATE TABLE f_com AS SELECT r_ori_port.flight_id, origin_airport_id, dest_airport_id FROM r_ori_port INNER JOIN r_des_port ON r_ori_port.flight_id = r_des_port.flight_id;");
		
		stat.executeUpdate("CREATE TABLE f_line_port AS SELECT f_com.flight_id, airline_id, origin_airport_id, dest_airport_id FROM f_lineid INNER JOIN f_com ON f_com.flight_id = f_lineid.flight_id; ");
		
		stat.executeUpdate("CREATE TABLE dflights AS SELECT tflights.flight_id, airline_id, flight_num, f_line_port.origin_airport_id, f_line_port.dest_airport_id, departure_dt, depart_diff, arrival_dt, arrival_diff, cancelled, carrier_delay, weather_delay, air_traffic_delay, security_delay FROM f_line_port INNER JOIN tflights ON tflights.flight_id = f_line_port.flight_id; ");
		stat.executeUpdate("CREATE TABLE kflights AS SELECT * FROM dflights WHERE security_delay >= 0 AND air_traffic_delay >= 0 AND weather_delay >= 0 AND carrier_delay >= 0 ;");
		
		
		stat.executeUpdate("CREATE TABLE flights AS SELECT * FROM kflights WHERE strftime('%s',arrival_dt) + 60*arrival_diff > strftime('%s',departure_dt) + 60*depart_diff;");
		stat.executeUpdate("DROP TABLE IF EXISTS dflights;");
		stat.executeUpdate("DROP TABLE IF EXISTS kflights;");
		stat.executeUpdate("DROP TABLE IF EXISTS tflights;");
		stat.executeUpdate("DROP TABLE IF EXISTS f_lineid;");
		stat.executeUpdate("DROP TABLE IF EXISTS f_ori_port;");
		stat.executeUpdate("DROP TABLE IF EXISTS r_ori_port;");
		stat.executeUpdate("DROP TABLE IF EXISTS f_des_port;");
		stat.executeUpdate("DROP TABLE IF EXISTS r_des_port;");
		stat.executeUpdate("DROP TABLE IF EXISTS f_com;");	
		stat.executeUpdate("DROP TABLE IF EXISTS f_line_port;");
		stat.executeUpdate("DROP TABLE IF EXISTS tairports;");
		stat.executeUpdate("DROP TABLE IF EXISTS helpair;");
		stat.executeUpdate("DROP TABLE IF EXISTS csh;");
		stat.executeUpdate("DROP TABLE IF EXISTS dcsh;");

		





		//String[] nextLine;
		// int i =4;
		// while (i !=0)
		// {
		// prep.setString(1, "ssssss");
		// prep.setInt(2, 454545);
		// prep.addBatch();
		// i--;}
		/*
		 * READING DATA FROM CSV FILES
		 * Source: http://opencsv.sourceforge.net/#how-to-read
		 * 
		 * If you want to use an Iterator style pattern, you might do something like this: 
		 * 
		CSVReader reader = new CSVReader(new FileReader("yourfile.csv"));
		 *	String [] nextLine;
		 *	while ((nextLine = reader.readNext()) != null) {
		 *		// nextLine[] is an array of values from the line
		 *		System.out.println(nextLine[0] + nextLine[1] + "etc...");
		 * 	}
		 */

		/*
		 * Below are some snippets of JDBC code that may prove useful
		 * 
		 * For more sample JDBC code, check out 
		 * http://web.archive.org/web/20100814175321/http://www.zentus.com/sqlitejdbc/
		 * 
		 * ---
		 * 
		 *	// INITIALIZE THE CONNECTION
		 *	Class.forName("org.sqlite.JDBC");
		 *	Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);
		 *
		 * ---
		 *
		 *	// ENABLE FOREIGN KEY CONSTRAINT CHECKING
		 *	Statement stat = conn.createStatement();
		 *	stat.executeUpdate("PRAGMA foreign_keys = ON;");
		 *
		 *	// Speed up INSERTs
		 *	stat.executeUpdate("PRAGMA synchronous = OFF;");
		 *	stat.executeUpdate("PRAGMA journal_mode = MEMORY;");
		 *
		 * ---
		 * 
		 *	// You can execute DELETE statements before importing if you want to be
		 *	// able to overwrite an existing database.
		 *	stat.executeUpdate("DROP TABLE IF EXISTS table;");
		 *
		 * ---
		 *
		 *	// To create a table, you can execute the following command.
		 *	stat.executeUpdate("CREATE TABLE airports (airport_id INTEGER PRIMARY KEY AUTOINCREMENT, airport_code CHAR(3)); ");
		 *
		 * ---
		 * 
		 * 	// Normally the database throws an exception when constraints are enforced
		 *	// and an INSERT statement that violates a constraint is executed. This is true
		 *	// even when doing a batch insert (multiple rows in one statement), causing all
		 *	// rows in the statement to not be inserted into the database.
		 *
		 *	// As a result, if you want the efficiency gains of using batch inserts, you need to be smart:
		 *	// You need to make sure your application enforces foreign key constraints before the insert ever happens.
		   while loop (

		   insert 
		   )

		 * 	PreparedStatement prep = conn.prepareStatement("INSERT OR IGNORE INTO table (col1, col2) VALUES (?, ?)");
		 *  String[] nextLine;
		 *  for ((nextLine = reader.readNext()) != null)
		 *  {
		 *  	prep.setString(1, nextLine[0]);
		 *  	prep.setInt(2, nextLine[1]);
		 *  	prep.addBatch();
		 *  }
		 *  
		 *  // We temporarily disable auto-commit, allowing the batch to be sent
		 *  // as one single transaction. Then we re-enable it, executing the batch.
		 *  conn.setAutoCommit(false);
		 *  prep.executeBatch();
		 *  conn.setAutoCommit(true);
		 * 	
		 */



		/*
		 * Date/Time Normalization Example
		 *
		 * import java.util.Date;
		 * import java.text.DateFormat;
		 * import java.text.ParseException;
		 * import java.text.SimpleDateFormat;
		 *
		 * // a sample date to convert
		 * String sampleDateString = "01-13-1992";
		 *
		 * // choose a standard date format that will be used throughout the database
		 * DateFormat standardFormat = new SimpleDateFormat("yyyy-MM-dd");
		 *
		 * // one possible date format to be converted
		 * DateFormat sampleDateFormat1 = new SimpleDateFormat("MM-dd-yyyy");
		 * // you need to set this flag to false for every possible DateFormat that you will be checking against.
		 * sampleDateFormat1.setLenient(false);
		 *
		 *
		 * try {
		 * 	// if the parse method doesn't throw an exception, then the format matches the date string.
		 *	// in our example, sampleDateFormat1 will match sampleDateString
		 *	Date sampleDate = sampleFormat1.parse(sampleDateString);
		 *	
		 *	// standardize the sample date from "MM-dd-yyyy" to "yyyy-MM-dd"
		 *	String sampleDateStringNormalized = standardFormat.format(sampleDate);
		 *
		 *	// this will print "1992-01-13"
		 *	System.out.println(sampleDateStringNormalized);
		 * }
		 * catch (ParseException e) {
		 *	// the sample format doesn't match the sample date string.
		 *	// now you should try parsing with a different sample format
		 *	return;
		 * }
		 */	

	}
}
