 1.join the two data sources crime.csv and comunity.csv.
 
 	Command: hadoop jar crimes.jar edu.bridgeport.cs651.crimes.CrimesAnalysis 0 project community result
 2. create a tmp folder, cope the result file into tmp folder.
 	Command: hadoop fs -mkdir tmp
 			 hadoop fs -cp result/part-r-00000  tmp
 	
 3. analysis the year distribution of crimes.
 	Command: hadoop jar crimes.jar edu.bridgeport.cs651.crimes.CrimesAnalysis 1 tmp year
 
 4. analysis the time slot distribution of crimes.
  	Command: hadoop jar crimes.jar edu.bridgeport.cs651.crimes.CrimesAnalysis 2 tmp timeslot
 
 5. analysis the crime types distribution of crimes.
 	Command: hadoop jar crimes.jar edu.bridgeport.cs651.crimes.CrimesAnalysis 3 tmp type
 
 6. analysis the income distribution of crimes.
 	Command: hadoop jar crimes.jar edu.bridgeport.cs651.crimes.CrimesAnalysis 4 tmp income
 	
 7. analysis the relationship between the unemployed rate and  the number of crimes.
 	Command: hadoop jar crimes.jar edu.bridgeport.cs651.crimes.CrimesAnalysis 5 tmp unemployed
 
 8. analysis the relationship between diploma and the number of crimes.
 	Command: hadoop jar crimes.jar edu.bridgeport.cs651.crimes.CrimesAnalysis 6 tmp diploma