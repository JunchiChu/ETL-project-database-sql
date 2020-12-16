package edu.brown.cs.cs127.etl.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EtlQuery
{
	private Connection conn;

	public EtlQuery(String pathToDatabase) throws Exception
	{
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:" + pathToDatabase);

		Statement stat = conn.createStatement();
		stat.executeUpdate("PRAGMA foreign_keys = ON;");
	}

	

	public ResultSet queryA(String[] args) throws SQLException
	{
		PreparedStatement stat = conn.prepareStatement(
			"SELECT COUNT(airport_id) FROM airports"
		);
		// stat.setString(1, args[0]);
		// stat.setInt(2, Integer.parseInt(args[1]));
		return stat.executeQuery();
	}
	public ResultSet queryB(String[] args) throws SQLException
	{
		PreparedStatement stat = conn.prepareStatement(
			"SELECT COUNT(airline_id) FROM airlines"
		);
		// stat.setString(1, args[0]);
		// stat.setInt(2, Integer.parseInt(args[1]));
		return stat.executeQuery();
	}

	public ResultSet queryC(String[] args) throws SQLException
	{
		PreparedStatement stat = conn.prepareStatement(
			"SELECT COUNT(flight_id) FROM flights"
		);
		// stat.setString(1, args[0]);
		// stat.setInt(2, Integer.parseInt(args[1]));
		return stat.executeQuery();
	}
	public ResultSet query0(String[] args) throws SQLException
	{
		PreparedStatement stat = conn.prepareStatement(
			"SELECT airport_name, city, state FROM airports WHERE state = 'Alaska' ORDER BY city DESC LIMIT 3"
		);
		// stat.setString(1, args[0]);
		// stat.setInt(2, Integer.parseInt(args[1]));
		return stat.executeQuery();
	}


	public ResultSet query1(String[] args) throws SQLException
	{
		
		
		PreparedStatement stat = conn.prepareStatement(
			"SELECT a.airline_name, b.flight_num FROM airlines a, flights b WHERE a.airline_id = b.airline_id AND a.airline_name = ? ORDER BY flight_num ASC LIMIT 1;"
		);
		
		stat.setString(1, args[0]);
		//stat.setInt(2, Integer.parseInt(args[1]));
		return stat.executeQuery();
	}
	public ResultSet query2(String[] args) throws SQLException
	{
		
	
		PreparedStatement stat = conn.prepareStatement(
			"SELECT COUNT(cancelled) FROM airlines a, flights b WHERE a.airline_id = b.airline_id AND b.cancelled = 1 AND a.airline_name = ?;"
		);
		
		stat.setString(1, args[0]);
		//stat.setInt(2, Integer.parseInt(args[1]));
		return stat.executeQuery();
	}
	public ResultSet query3(String[] args) throws SQLException
	{
		
	
		PreparedStatement stat = conn.prepareStatement(
			"WITH max AS (SELECT f.airline_id, COUNT(f.flight_id) from flights f GROUP BY airline_id HAVING COUNT (f.flight_num) >=10000 ORDER BY COUNT(f.flight_id)) SELECT airport_name, COUNT(origin_airport_id) AS co FROM flights JOIN airports ON flights.origin_airport_id = airports.airport_id WHERE flights.airline_id IN (SELECT airline_id FROM max) GROUP BY origin_airport_id ORDER BY co DESC LIMIT 5;"
		);
		
		//stat.setString(1, args[0]);
		//stat.setInt(2, Integer.parseInt(args[1]));
		return stat.executeQuery();
	}
	//"WITH max AS (SELECT f.airline_id, COUNT(f.flight_id) from flights f GROUP BY airline_id HAVING COUNT (f.flight_num) >=10000 ORDER BY COUNT(f.flight_id)) SELECT airport_name, COUNT(origin_airport_id) AS co FROM flights JOIN airports ON flights.origin_airport_id = airports.airport_id WHERE flights.airline_id IN (SELECT airline_id FROM max) GROUP BY origin_airport_id ORDER BY co DESC LIMIT 5;"
	public ResultSet query4(String[] args) throws SQLException
	{
	
	
		PreparedStatement stat = conn.prepareStatement(
			" SELECT 'Air Traffic Delay',COUNT(flight_id) FROM flights WHERE air_traffic_delay > 0 UNION  SELECT 'Carrier Delay',COUNT(flight_id) FROM flights WHERE carrier_delay > 0 UNION  SELECT 'Weather Delay',COUNT(flight_id) FROM flights WHERE weather_delay > 0 UNION  SELECT 'Security Delay',COUNT(flight_id) FROM flights WHERE security_delay > 0 ORDER BY COUNT(flight_id) DESC;"
		);
		
		//System.out.println(args[0]);
		return stat.executeQuery();
	}
	public ResultSet query5(String[] args) throws SQLException
	{
	
	
		PreparedStatement stat = conn.prepareStatement(
			"SELECT airline_name,COUNT(flight_id) FROM airlines LEFT JOIN flights ON airlines.airline_id = flights.airline_id AND flight_id IN (SELECT flight_id FROM flights WHERE strftime('%Y-%m-%d',departure_dt) = ?) GROUP BY airline_name ORDER BY COUNT(flight_id) DESC ;"
		);
		String s = args[2]+"-0"+args[0]+'-'+args[1];
		//System.out.println(s);
		stat.setString(1, s);
		return stat.executeQuery();
		//SELECT airport_name,COUNT(flight_id) FROM airports, flights ON airports.airport_id = flights.dest_airport_id AND airport_name = 'LaGuardia' AND flight_id IN (SELECT flight_id FROM flights WHERE strftime('%Y-%m-%d',departure_dt) = '2012-01-31') ;

	}
	public ResultSet query6(String[] args) throws SQLException
	{
	
	
		PreparedStatement stat = conn.prepareStatement(
			" SELECT * FROM (SELECT airport_name,COUNT(flight_id) FROM airports, flights ON airports.airport_id = flights.origin_airport_id AND airport_name = ? AND flight_id IN (SELECT flight_id FROM flights WHERE strftime('%Y-%m-%d',departure_dt) = ?)) JOIN (SELECT COUNT(flight_id) FROM airports, flights ON airports.airport_id = flights.dest_airport_id AND airport_name = ? AND flight_id IN (SELECT flight_id FROM flights WHERE strftime('%Y-%m-%d',arrival_dt) = ?)) ;"
		);
		String s = args[2]+"-0"+args[0]+'-'+args[1];
		//System.out.println(s);
		stat.setString(1, args[3]);
		stat.setString(2, s);
		stat.setString(3, args[3]);
		stat.setString(4, s);
		return stat.executeQuery();
		//SELECT airport_name,COUNT(flight_id) FROM airports, flights ON airports.airport_id = flights.dest_airport_id AND airport_name = 'LaGuardia' AND flight_id IN (SELECT flight_id FROM flights WHERE strftime('%Y-%m-%d',departure_dt) = '2012-01-31') ;

	}
	public ResultSet query7(String[] args) throws SQLException
	{
	
	
		PreparedStatement stat = conn.prepareStatement(
		  //"SELECT COUNt(airline_id) FROM airlines;"
			"WITH max AS (SELECT flight_id FROM airlines,flights WHERE airlines.airline_id = flights.airline_id AND flight_num=? AND strftime('%Y-%m-%d',departure_dt) >= ? AND strftime('%Y-%m-%d',departure_dt) <= ? AND airline_name = ?) SELECT * FROM (SELECT COUNT(flight_id) FROM flights WHERE flight_id IN (SELECT flight_id FROM max)) JOIN (SELECT COUNT(flight_id) FROM flights WHERE flight_id IN (SELECT flight_id FROM max) AND cancelled = 1) JOIN (  SELECT COUNT(flight_id) FROM flights WHERE flight_id IN (SELECT flight_id FROM max) AND cancelled = 0 AND depart_diff<=0  ) JOIN ( SELECT COUNT(flight_id) FROM flights WHERE flight_id IN (SELECT flight_id FROM max) AND cancelled = 0 AND depart_diff>0 ) JOIN(  SELECT COUNT(flight_id) FROM flights WHERE flight_id IN (SELECT flight_id FROM max) AND cancelled = 0 AND arrival_diff<=0 ) JOIN (  SELECT COUNT(flight_id) FROM flights WHERE flight_id IN (SELECT flight_id FROM max) AND cancelled = 0 AND arrival_diff>0);;"
		);
		String airl = args[0];

		String flinum = args[1];
		String d1 = args[2];
		String d2 = args[3];
		//String s = args[0]+"*"+args[1]+"*"+args[2]+"*"+args[3]+"*"+args[4]+"*";
		//System.out.println(airl+"**"+flinum+"**");
		String str = d2;
        String[] arrOfStr = str.split("/", 3); 
		
		String[] x={"Volvo", "BMW", "Ford"};
		int i =0;
        for (String a : arrOfStr) {
			x[i]=a;
			i++;
		}
		//System.out.println(x[0]+x[1]+x[2]);
		String dd2 = x[2]+"-"+x[0]+"-"+x[1];
		String strr = d1;
        String[] arrOfStrr = strr.split("/", 3); 
		
		String[] xx={"Volvo", "BMW", "Ford"};
		i =0;
        for (String a : arrOfStrr) {
			xx[i]=a;
			i++;
		}
		String dd1 = xx[2]+"-"+xx[0]+"-"+xx[1];
     
// } 
		stat.setInt(1, Integer.parseInt(flinum));
		stat.setString(2, dd1);
		stat.setString(3, dd2);
		stat.setString(4, airl);
		return stat.executeQuery();
		//SELECT airport_name,COUNT(flight_id) FROM airports, flights ON airports.airport_id = flights.dest_airport_id AND airport_name = 'LaGuardia' AND flight_id IN (SELECT flight_id FROM flights WHERE strftime('%Y-%m-%d',departure_dt) = '2012-01-31') ;

	}
	public ResultSet query8(String[] args) throws SQLException
	{
		
		
		PreparedStatement stat = conn.prepareStatement(
			//"SELECT COUNT(flight_id) FROM flights"
			"WITH totaldur AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff FROM flights) , accdate AS (SELECT flight_id,strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') AS n1,  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') AS n2 FROM flights), origin AS (SELECT airport_id,airport_code FROM airports WHERE city= ? AND state = ?), dest AS (SELECT airport_id,airport_code FROM airports WHERE city = ? AND state = ?) SELECT airline_code,flight_num,origin.airport_code,  strftime('%H:%M', departure_dt, depart_diff || ' minutes')             , dest.airport_code,                  strftime('%H:%M', arrival_dt, arrival_diff || ' minutes'),                             (strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff)/60                                FROM airlines,flights,origin,dest WHERE flights.airline_id = airlines.airline_id AND origin.airport_id = flights.origin_airport_id AND dest.airport_id = flights.dest_airport_id AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )=?  AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )=?  ORDER BY   (strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff)/60   ASC ,airline_code ASC, flight_num DESC;"
		);
		String dep_city = args[0];
		String dep_sta = args[1];
		String arr_city = args[2];
		String arr_dat = args[3];
		//System.out.println(args[0]+"**"+args[1]+"**"+args[2]+"**"+args[3]);
		String str = args[4];
		String[] arrOfStr = str.split("/", 3); 
		
		String[] x={"Volvo", "BMW", "Ford"};
		int i =0;
        for (String a : arrOfStr) {
			x[i]=a;
			i++;
		}
		//System.out.println(x[0]+x[1]+x[2]);
		String dd2 = x[2]+"-"+x[0]+"-"+x[1];
		//System.out.println(dd2);
		stat.setString(1, dep_city);
		stat.setString(2, dep_sta);
		stat.setString(3, arr_city);
		stat.setString(4, arr_dat);
		stat.setString(5, dd2);
		stat.setString(6, dd2);

		
		//stat.setString(1, args[0]);
		//stat.setInt(2, Integer.parseInt(args[1]));
		return stat.executeQuery();
	}
	public ResultSet query9(String[] args) throws SQLException
	{
		PreparedStatement stat = conn.prepareStatement(
			//"SELECT airport_name, city, state FROM airports WHERE state = 'Alaska' ORDER BY city DESC LIMIT 3"
			"SELECT a1,b1,c1,d1,fir,e1,a2,b2,c2,d2,sec,e2,(strftime('%s',e2) - strftime('%s',d1))/60 FROM (                WITH origin AS (SELECT airport_id,airport_code FROM airports WHERE city= ? AND state = ?), dest AS (SELECT airport_id,airport_code FROM airports WHERE city != ? ),                  totaldur AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS t1  FROM flights) SELECT DISTINCT airline_code AS a1,flight_num AS b1,origin.airport_code AS c1,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes') AS d1,                  dest.airport_code AS fir,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') AS e1 ,  t1                     FROM airlines,flights,origin,dest,totaldur WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldur.flight_id AND origin.airport_id = flights.origin_airport_id AND dest.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )=? AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )=?      ) JOIN (  WITH origin2 AS (SELECT airport_id,airport_code FROM airports WHERE city != ? AND state != ?), dest2 AS (SELECT airport_id,airport_code FROM airports WHERE city = ? AND state = ?),                  totaldur2 AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS t2  FROM flights) SELECT DISTINCT airline_code AS a2 ,flight_num AS b2 ,origin2.airport_code AS c2,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes') AS d2,                  dest2.airport_code AS sec,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') AS e2 ,  t2                     FROM airlines,flights,origin2,dest2,totaldur2 WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldur2.flight_id AND origin2.airport_id = flights.origin_airport_id AND dest2.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )=? AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )=?  ) WHERE fir = c2 AND d2>e1  ORDER BY (strftime('%s',e2) - strftime('%s',d1))/60, a1,a2,d1;"
		);
		//System.out.println(args[0]+"***"+args[1]+"***"+args[2]+"***"+args[3]+"***"+args[4]+"***");
		//stat.setInt(2, Integer.parseInt(args[1]));
		
		String str = args[4];
		String[] arrOfStr = str.split("/", 3); 
		
		String[] x={"Volvo", "BMW", "Ford"};
		int i =0;
        for (String a : arrOfStr) {
			x[i]=a;
			i++;
		}
		//System.out.println(x[0]+x[1]+x[2]);
		String dd2 = x[2]+"-"+x[0]+"-"+x[1];
		stat.setString(1, args[0]);
		stat.setString(2, args[1]);
		stat.setString(3, args[0]);
		
		stat.setString(4, dd2);
		stat.setString(5, dd2);
		stat.setString(6, args[2]);
		stat.setString(7, args[3]);
		stat.setString(8, args[2]);
		stat.setString(9, args[3]);
		stat.setString(10, dd2);
		stat.setString(11, dd2);
		return stat.executeQuery();
	}

	

	public ResultSet query10(String[] args) throws SQLException
	{
		PreparedStatement stat = conn.prepareStatement(
			"SELECT a1,b1,c1,d1,fir,e1, am,bm,cm,dm,middl,em,      a2,b2,c2,d2,sec,e2,(strftime('%s',e2) - strftime('%s',d1))/60 FROM (                WITH origin AS (SELECT airport_id,airport_code FROM airports WHERE city= ? AND state = ?), dest AS (SELECT airport_id,airport_code FROM airports WHERE city != ? ),                  totaldur AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS t1  FROM flights) SELECT DISTINCT airline_code AS a1,flight_num AS b1,origin.airport_code AS c1,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes') AS d1,                  dest.airport_code AS fir,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') AS e1 ,  t1                     FROM airlines,flights,origin,dest,totaldur WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldur.flight_id AND origin.airport_id = flights.origin_airport_id AND dest.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )=? AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )=?      ) JOIN(    WITH origin2 AS (SELECT airport_id,airport_code FROM airports WHERE city != ? ), dest2 AS (SELECT airport_id,airport_code FROM airports WHERE city = ? AND state = ?),                  totaldur2 AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS t2  FROM flights) SELECT DISTINCT airline_code AS a2 ,flight_num AS b2 ,origin2.airport_code AS c2,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes') AS d2,                  dest2.airport_code AS sec,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') AS e2 ,  t2                     FROM airlines,flights,origin2,dest2,totaldur2 WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldur2.flight_id AND origin2.airport_id = flights.origin_airport_id AND dest2.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )=? AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )=?   ) JOIN (      WITH originmid AS (SELECT airport_id,airport_code FROM airports ), destmid AS (SELECT airport_id,airport_code FROM airports ),                  totaldurmid AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS tmid  FROM flights) SELECT DISTINCT airline_code AS am ,flight_num AS bm ,originmid.airport_code AS cm,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes') AS dm,                  destmid.airport_code AS middl,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') AS em ,  tmid                     FROM airlines,flights,originmid,destmid,totaldurmid WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldurmid.flight_id AND originmid.airport_id = flights.origin_airport_id AND destmid.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )=? AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )=?  )WHERE fir = cm AND middl=c2 AND dm>e1 AND d2>em ORDER BY (strftime('%s',e2) - strftime('%s',d1))/60,a1,am,a2,d1;"
		);
		// stat.setString(1, args[0]);
		// stat.setInt(2, Integer.parseInt(args[1]));
		String str = args[4];
		String[] arrOfStr = str.split("/", 3); 
		
		String[] x={"Volvo", "BMW", "Ford"};
		int i =0;
        for (String a : arrOfStr) {
			x[i]=a;
			i++;
		}
		//System.out.println(x[0]+x[1]+x[2]);
		String dd2 = x[2]+"-"+x[0]+"-"+x[1];
		stat.setString(1, args[0]);
		stat.setString(2, args[1]);
		stat.setString(3, args[0]);
		
		stat.setString(4, dd2);
		stat.setString(5, dd2);
		
		stat.setString(6, args[2]);
		stat.setString(7,args[2]);
		stat.setString(8,args[3]);
		stat.setString(9, dd2);
		stat.setString(10, dd2);
		stat.setString(11, dd2);
		stat.setString(12, dd2);


		return stat.executeQuery();
	}

}
	//WITH totaldur AS (SELECT strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff FROM flights);
    //SELECT departure_dt, depart_diff, strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') FROM flights;
// SELECT strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes'),  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') FROM flights;
// WITH totaldur AS (SELECT strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff FROM flights) 
//WITH accdate AS (SELECT strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes'),  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') FROM flights)
// SELECT airline_code,flight_num,origin_airport_code, dest_airport_code FROM airlines,airports,flights WHERE flights.airline_id = airlines.airline_id AND airports.airport_id = flights.airport_id AND city = 'Los Angeles' AND state = 'California';
//  WITH totaldur AS (SELECT strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff FROM flights) , accdate AS (SELECT flight_id,strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes'),  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') FROM flights), origin AS (SELECT airport_id,airport_code FROM airports WHERE city= 'Los Angeles' AND state = 'California'), dest AS (SELECT airport_id,airport_code FROM airports WHERE city = 'New York' AND state = 'New York') SELECT COUNT(flight_id) FROM flights;
//WITH totaldur AS (SELECT strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff FROM flights) , accdate AS (SELECT flight_id,strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes'),  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') FROM flights), origin AS (SELECT airport_id,airport_code FROM airports WHERE city= 'Los Angeles' AND state = 'California'), dest AS (SELECT airport_id,airport_code FROM airports WHERE city = 'New York' AND state = 'New York') SELECT DISTINCT airline_code,origin.airport_code,dest.airport_code FROM airlines,flights,origin,dest;
// WITH totaldur AS (SELECT strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff FROM flights) , accdate AS (SELECT flight_id,strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes'),  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') FROM flights), origin AS (SELECT airport_id,airport_code FROM airports WHERE city= 'Los Angeles' AND state = 'California'), dest AS (SELECT airport_id,airport_code FROM airports WHERE city = 'New York' AND state = 'New York') SELECT DISTINCT airline_code,origin.airport_code,dest.airport_code FROM airlines,flights,origin,dest WHERE flights.airline_id = airlines.airline_id AND origin.airport_id = flights.origin_airport_id AND dest.airport_id = flights.dest_airport_id;
//  WITH totaldur AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff FROM flights) , accdate AS (SELECT flight_id,strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') AS n1,  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') AS n2 FROM flights), origin AS (SELECT airport_id,airport_code FROM airports WHERE city= 'Los Angeles' AND state = 'California'), dest AS (SELECT airport_id,airport_code FROM airports WHERE city = 'New York' AND state = 'New York') SELECT airline_code,flight_num,origin.airport_code,dest.airport_code,  strftime('%H:%M', departure_dt, depart_diff || ' minutes')             ,                   strftime('%H:%M', arrival_dt, arrival_diff || ' minutes'),                             (strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff)/60                                FROM airlines,flights,origin,dest WHERE flights.airline_id = airlines.airline_id AND origin.airport_id = flights.origin_airport_id AND dest.airport_id = flights.dest_airport_id AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20'  AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20'  ORDER BY   (strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff)/60   ASC    ;






///SELECT * FROM  (WITH origin AS (SELECT airport_id,airport_code FROM airports WHERE city= 'Los Angeles' AND state = 'California'), dest AS (SELECT airport_id,airport_code FROM airports WHERE city != 'Los Angeles' AND state != 'California') SELECT airline_code,flight_num,origin.airport_code,dest.airport_code AS fir      FROM airlines,flights,origin,dest WHERE flights.airline_id = airlines.airline_id AND origin.airport_id = flights.origin_airport_id  AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20'  AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20'  )       JOIN      (    WITH origin AS (SELECT airport_id,airport_code FROM airports WHERE city!= 'New York' AND state = 'New York'), dest AS (SELECT airport_id,airport_code FROM airports WHERE city = 'New York' AND state != 'New York') SELECT airline_code,flight_num,origin.airport_code,dest.airport_code    AS sec            FROM airlines,flights,origin,dest WHERE flights.airline_id = airlines.airline_id AND origin.airport_id = flights.origin_airport_id  AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20'  AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20');


//SELECT COUNT(*) FROM  (WITH origin AS (SELECT airport_id,airport_code FROM airports WHERE 
/*city= 'Los Angeles' AND state = 'California'), dest AS (SELECT airport_id,airport_code 
FROM airports WHERE city != 'Los Angeles' AND state != 'California') SELECT airline_code
,flight_num,origin.airport_code,dest.airport_code AS fir      
FROM airlines,flights,origin,dest WHERE flights.airline_id = airlines.airline_id AND origin.airport_id = flights.origin_airport_id
  AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20'  AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20'  )       JOIN      (    WITH origin AS (SELECT airport_id,airport_code FROM airports WHERE city!= 'New York' AND state != 'New York'), dest AS (SELECT airport_id,airport_code FROM airports WHERE city = 'New York' AND state == 'New York') SELECT airline_code,flight_num,origin.airport_code,dest.airport_code    AS sec            FROM airlines,flights,origin,dest WHERE flights.airline_id = airlines.airline_id AND origin.airport_id = flights.origin_airport_id  AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20'  AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20') ON fir=sec ;  
}
// WITH origin AS (SELECT airport_id,airport_code FROM airports WHERE city= 'Los Angeles' AND state = 'California'),
 dest AS (SELECT airport_id,airport_code FROM airports WHERE city != 'Los Angeles' AND state != 'California')
  SELECT DISTINCT airline_code,flight_num,origin.airport_code,dest.airport_code AS fir     
   FROM airlines,flights,origin,dest WHERE flights.airline_id = airlines.airline_id AND origin.airport_id = flights.origin_airport_id
    AND dest.airport_id != flights.dest_airport_id 

 WITH origin AS (SELECT airport_id,airport_code FROM airports WHERE city= 'Los Angeles' AND state = 'California'), dest AS (SELECT airport_id,airport_code FROM airports WHERE city != 'Los Angeles' AND state != 'California') SELECT DISTINCT airline_code,flight_num,origin.airport_code,dest.airport_code AS fir      FROM airlines,flights,origin,dest WHERE flights.airline_id = airlines.airline_id AND origin.airport_id = flights.origin_airport_id AND dest.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20' AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20'  ;

WITH origin2 AS (SELECT airport_id,airport_code FROM airports WHERE city!= 'New York' AND state != 'New York'), dest2 AS (SELECT airport_id,airport_code FROM airports WHERE city = 'New York' AND state = 'New York') SELECT DISTINCT airline_code,flight_num,origin2.airport_code,dest2.airport_code AS sec      FROM airlines,flights,origin2,dest2 WHERE flights.airline_id = airlines.airline_id AND origin2.airport_id = flights.origin_airport_id AND dest2.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20' AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20';

SELECT * FROM (     WITH origin AS (SELECT airport_id,airport_code FROM airports WHERE city= 'Los Angeles' AND state = 'California'), dest AS (SELECT airport_id,airport_code FROM airports WHERE city != 'Los Angeles' AND state != 'California') SELECT DISTINCT airline_code,flight_num,origin.airport_code,dest.airport_code AS fir      FROM airlines,flights,origin,dest WHERE flights.airline_id = airlines.airline_id AND origin.airport_id = flights.origin_airport_id AND dest.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20' AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20'          ) JOIN (WITH origin2 AS (SELECT airport_id,airport_code FROM airports WHERE city!= 'New York' AND state != 'New York'), dest2 AS (SELECT airport_id,airport_code FROM airports WHERE city = 'New York' AND state = 'New York') SELECT DISTINCT airline_code,flight_num,origin2.airport_code,dest2.airport_code AS sec      FROM airlines,flights,origin2,dest2 WHERE flights.airline_id = airlines.airline_id AND origin2.airport_id = flights.origin_airport_id AND dest2.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20' AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20') WHERE fir = sec;
*/
//WITH origin AS (SELECT airport_id,airport_code FROM airports WHERE city= 'Los Angeles' AND state = 'California'), dest AS (SELECT airport_id,airport_code FROM airports WHERE city != 'Los Angeles' AND state != 'California'),                  totaldur AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS t1  FROM flights) SELECT DISTINCT airline_code,flight_num,origin.airport_code,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes'),                  dest.airport_code AS fir,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') ,  t1                     FROM airlines,flights,origin,dest,totaldur WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldur.flight_id AND origin.airport_id = flights.origin_airport_id AND dest.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20' AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20'
//WITH origin2 AS (SELECT airport_id,airport_code FROM airports WHERE city != 'New York' AND state != 'New York'), dest2 AS (SELECT airport_id,airport_code FROM airports WHERE city = 'New York' AND state = 'New York'),                  totaldur2 AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS t2  FROM flights) SELECT DISTINCT airline_code,flight_num,origin2.airport_code,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes'),                  dest2.airport_code AS sec,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') ,  t2                     FROM airlines,flights,origin2,dest2,totaldur2 WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldur2.flight_id AND origin2.airport_id = flights.origin_airport_id AND dest2.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20' AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20';
// WITH origin2 AS (SELECT airport_id,airport_code FROM airports WHERE city != 'New York' AND state != 'New York'), dest2 AS (SELECT airport_id,airport_code FROM airports WHERE city = 'New York' AND state = 'New York'),                  totaldur2 AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS t2  FROM flights) SELECT DISTINCT airline_code AS a2 ,flight_num AS b2 ,origin2.airport_code AS c2,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes') AS d2,                  dest2.airport_code AS sec,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') AS e2 ,  t2                     FROM airlines,flights,origin2,dest2,totaldur2 WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldur2.flight_id AND origin2.airport_id = flights.origin_airport_id AND dest2.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20' AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20';
//WITH origin AS (SELECT airport_id,airport_code FROM airports WHERE city= 'Los Angeles' AND state = 'California'), dest AS (SELECT airport_id,airport_code FROM airports WHERE city != 'Los Angeles' AND state != 'California'),                  totaldur AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS t1  FROM flights) SELECT DISTINCT airline_code AS a1,flight_num AS b1,origin.airport_code AS c1,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes') AS d1,                  dest.airport_code AS fir,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') AS e1 ,  t1                     FROM airlines,flights,origin,dest,totaldur WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldur.flight_id AND origin.airport_id = flights.origin_airport_id AND dest.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20' AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20'
//SELECT a1,b1,c1,d1,fir,e1,a2,b2,c2,d2,sec,e2,(strftime('%s',e2) - strftime('%s',d1))/60 FROM (                WITH origin AS (SELECT airport_id,airport_code FROM airports WHERE city= 'Los Angeles' AND state = 'California'), dest AS (SELECT airport_id,airport_code FROM airports WHERE city != 'Los Angeles' ),                  totaldur AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS t1  FROM flights) SELECT DISTINCT airline_code AS a1,flight_num AS b1,origin.airport_code AS c1,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes') AS d1,                  dest.airport_code AS fir,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') AS e1 ,  t1                     FROM airlines,flights,origin,dest,totaldur WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldur.flight_id AND origin.airport_id = flights.origin_airport_id AND dest.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20' AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20'      ) JOIN (    WITH origin2 AS (SELECT airport_id,airport_code FROM airports WHERE city != 'New York' AND state != 'New York'), dest2 AS (SELECT airport_id,airport_code FROM airports WHERE city = 'New York' AND state = 'New York'),                  totaldur2 AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS t2  FROM flights) SELECT DISTINCT airline_code AS a2 ,flight_num AS b2 ,origin2.airport_code AS c2,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes') AS d2,                  dest2.airport_code AS sec,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') AS e2 ,  t2                     FROM airlines,flights,origin2,dest2,totaldur2 WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldur2.flight_id AND origin2.airport_id = flights.origin_airport_id AND dest2.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20' AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20'   ) WHERE fir = c2 AND d2>e1  ORDER BY (t1+t2)/60 ;
//SELECT a1,b1,c1,d1,fir,e1,a2,b2,c2,d2,sec,e2,(strftime('%s',e2) - strftime('%s',d1))/60 FROM (     
	         //  WITH origin AS (SELECT airport_id,airport_code FROM airports WHERE city= 'Los Angeles' AND state = 'California'), dest AS (SELECT airport_id,airport_code FROM airports WHERE city != 'Los Angeles' AND state != 'California'),                  totaldur AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS t1  FROM flights) SELECT DISTINCT airline_code AS a1,flight_num AS b1,origin.airport_code AS c1,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes') AS d1,                  dest.airport_code AS fir,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') AS e1 ,  t1                     FROM airlines,flights,origin,dest,totaldur WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldur.flight_id AND origin.airport_id = flights.origin_airport_id AND dest.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20' AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20'      ) JOIN (  
				//     WITH origin2 AS (SELECT airport_id,airport_code FROM airports WHERE city != 'New York' AND state != 'New York'), dest2 AS (SELECT airport_id,airport_code FROM airports WHERE city = 'New York' AND state = 'New York'),                  totaldur2 AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS t2  FROM flights) SELECT DISTINCT airline_code AS a2 ,flight_num AS b2 ,origin2.airport_code AS c2,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes') AS d2,                  dest2.airport_code AS sec,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') AS e2 ,  t2                     FROM airlines,flights,origin2,dest2,totaldur2 WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldur2.flight_id AND origin2.airport_id = flights.origin_airport_id AND dest2.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20' AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20'   ) WHERE fir = c2 AND d2>e1  ORDER BY (strftime('%s',e2) - strftime('%s',d1))/60, a1,a2,d1;
//SELECT a1,b1,c1,d1,fir,e1, am,bm,cm,dm,middl,em,      a2,b2,c2,d2,sec,e2,(strftime('%s',e2) - strftime('%s',d1))/60 FROM (                WITH origin AS (SELECT airport_id,airport_code FROM airports WHERE city= 'Los Angeles' AND state = 'California'), dest AS (SELECT airport_id,airport_code FROM airports WHERE city != 'Los Angeles' ),                  totaldur AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS t1  FROM flights) SELECT DISTINCT airline_code AS a1,flight_num AS b1,origin.airport_code AS c1,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes') AS d1,                  dest.airport_code AS fir,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') AS e1 ,  t1                     FROM airlines,flights,origin,dest,totaldur WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldur.flight_id AND origin.airport_id = flights.origin_airport_id AND dest.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20' AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20'      ) JOIN(    WITH origin2 AS (SELECT airport_id,airport_code FROM airports WHERE city != 'New York' ), dest2 AS (SELECT airport_id,airport_code FROM airports WHERE city = 'New York' AND state = 'New York'),                  totaldur2 AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS t2  FROM flights) SELECT DISTINCT airline_code AS a2 ,flight_num AS b2 ,origin2.airport_code AS c2,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes') AS d2,                  dest2.airport_code AS sec,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') AS e2 ,  t2                     FROM airlines,flights,origin2,dest2,totaldur2 WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldur2.flight_id AND origin2.airport_id = flights.origin_airport_id AND dest2.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20' AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20'   ) JOIN (      WITH originmid AS (SELECT airport_id,airport_code FROM airports WHERE airport_code  ), destmid AS (SELECT airport_id,airport_code FROM airports WHERE airport_code ),                  totaldurmid AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS tmid  FROM flights) SELECT DISTINCT airline_code AS am ,flight_num AS bm ,originmid.airport_code AS cm,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes') AS dm,                  destmid.airport_code AS middl,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') AS em ,  tmid                     FROM airlines,flights,originmid,destmid,totaldurmid WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldurmid.flight_id AND originmid.airport_id = flights.origin_airport_id AND destmid.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20' AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20'   )WHERE fir = cm AND middl=c2 AND dm>e1 AND d2>em    ORDER BY (strftime('%s',e2) - strftime('%s',d1))/60;


 //SELECT a1,b1,c1,d1,fir,e1,      a2,b2,c2,d2,sec,e2,(strftime('%s',e2) - strftime('%s',d1))/60 FROM (                WITH origin AS (SELECT airport_id,airport_code FROM airports WHERE city= 'Los Angeles' AND state = 'California'), dest AS (SELECT airport_id,airport_code FROM airports WHERE city != 'Los Angeles' ),                  totaldur AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS t1  FROM flights) SELECT DISTINCT airline_code AS a1,flight_num AS b1,origin.airport_code AS c1,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes') AS d1,                  dest.airport_code AS fir,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') AS e1 ,  t1                     FROM airlines,flights,origin,dest,totaldur WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldur.flight_id AND origin.airport_id = flights.origin_airport_id AND dest.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20' AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20'      ) JOIN(    WITH origin2 AS (SELECT airport_id,airport_code FROM airports WHERE city != 'New York' ), dest2 AS (SELECT airport_id,airport_code FROM airports WHERE city = 'New York' AND state = 'New York'),                  totaldur2 AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS t2  FROM flights) SELECT DISTINCT airline_code AS a2 ,flight_num AS b2 ,origin2.airport_code AS c2,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes') AS d2,                  dest2.airport_code AS sec,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') AS e2 ,  t2                     FROM airlines,flights,origin2,dest2,totaldur2 WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldur2.flight_id AND origin2.airport_id = flights.origin_airport_id AND dest2.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20' AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20'   ) WHERE fir = c2    ORDER BY (strftime('%s',e2) - strftime('%s',d1))/60;



 //SELECT a1,b1,c1,d1,fir,e1, am,bm,cm,dm,middl,em,      a2,b2,c2,d2,sec,e2,(strftime('%s',e2) - strftime('%s',d1))/60 FROM (                WITH origin AS (SELECT airport_id,airport_code FROM airports WHERE city= 'Los Angeles' AND state = 'California'), dest AS (SELECT airport_id,airport_code FROM airports WHERE city != 'Los Angeles' ),                  totaldur AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS t1  FROM flights) SELECT DISTINCT airline_code AS a1,flight_num AS b1,origin.airport_code AS c1,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes') AS d1,                  dest.airport_code AS fir,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') AS e1 ,  t1                     FROM airlines,flights,origin,dest,totaldur WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldur.flight_id AND origin.airport_id = flights.origin_airport_id AND dest.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20' AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20'      ) JOIN(    WITH origin2 AS (SELECT airport_id,airport_code FROM airports WHERE city != 'Providence' ), dest2 AS (SELECT airport_id,airport_code FROM airports WHERE city = 'Providence' AND state = 'Rhode Island'),                  totaldur2 AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS t2  FROM flights) SELECT DISTINCT airline_code AS a2 ,flight_num AS b2 ,origin2.airport_code AS c2,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes') AS d2,                  dest2.airport_code AS sec,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') AS e2 ,  t2                     FROM airlines,flights,origin2,dest2,totaldur2 WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldur2.flight_id AND origin2.airport_id = flights.origin_airport_id AND dest2.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20' AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20'   ) JOIN (      WITH originmid AS (SELECT airport_id,airport_code FROM airports ), destmid AS (SELECT airport_id,airport_code FROM airports ),                  totaldurmid AS (SELECT flight_id,strftime('%s',arrival_dt)-strftime('%s',departure_dt)+60*arrival_diff-60*depart_diff AS tmid  FROM flights) SELECT DISTINCT airline_code AS am ,flight_num AS bm ,originmid.airport_code AS cm,                                              strftime('%H:%M', departure_dt, depart_diff || ' minutes') AS dm,                  destmid.airport_code AS middl,            strftime('%H:%M', arrival_dt, arrival_diff || ' minutes') AS em ,  tmid                     FROM airlines,flights,originmid,destmid,totaldurmid WHERE flights.airline_id = airlines.airline_id AND flights.flight_id = totaldurmid.flight_id AND originmid.airport_id = flights.origin_airport_id AND destmid.airport_id == flights.dest_airport_id AND flights.cancelled = 0 AND strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', arrival_dt, arrival_diff || ' minutes') )='2012-01-20' AND  strftime('%Y-%m-%d',  strftime('%Y-%m-%d %H:%M', departure_dt, depart_diff || ' minutes') )='2012-01-20'   )WHERE fir = cm AND middl=c2 AND dm>e1 AND d2>em ORDER BY (strftime('%s',e2) - strftime('%s',d1))/60,a1,am,a2,d1;