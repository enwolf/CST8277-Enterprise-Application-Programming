/*********************************************************************************************************
 * File:  PhysicianManufacturer.java Course Materials CST 8277
 * 
 * @author Robin Phillis
 */
package jdbccmd;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.jemos.podam.api.AttributeMetadata;
import uk.co.jemos.podam.api.DataProviderStrategy;
import uk.co.jemos.podam.typeManufacturers.StringTypeManufacturerImpl;

/**
 * This class used the PODAM (POJO Data Mocker) library.  The idea is to provide the needed data and this class randomize
 * data for the given object.
 * 
 * @author Robin Phillis
 */
public class PhysicianManufacturer extends StringTypeManufacturerImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	//Name of the fields in the Physician class
	private static final String LASTNAME_FIELD = "lastName";
	private static final String FIRSTNAME_FIELD = "firstName";
	private static final String EMAIL_FIELD = "email";
	private static final String PHONENUMBER_FIELD = "phoneNumber";
	private static final String SPECIALTY_FIELD = "specialty";

	
	//Name of the files with data in them
	protected static final String POOL_OF_LASTNAMES = "lastnamePool.txt";
	protected static final String POOL_OF_FIRSTNAMES = "firstnamePool.txt";
	protected static final String POOL_OF_SPECIALTIES = "specialtyPool.txt";
	
	//List of useful digits and letters
	protected static final String ALPHA_LETTERS = "abcdefghijklmnopqrstuvwxyz";
	protected static final String DIGITS = "1234567890";
	
	//Random generator which is less predictable than Random class
	//https://stackoverflow.com/a/11052736/764951
	protected static SecureRandom rnd = new SecureRandom();
	
	//List of loaded data pool
	protected static List<String> poolOfLastnames = new ArrayList<>();
	protected static List<String> poolOfFirstnames = new ArrayList<>();
	protected static List<String> poolOfSpecialties = new ArrayList<>();

	//Load the pool of data into the list
	static 
	{
		
		ClassLoader theClassLoader = MethodHandles.lookup().lookupClass().getClassLoader();
		
		try (
			
			InputStream firstnamePoolInputStream = theClassLoader.getResourceAsStream(POOL_OF_FIRSTNAMES);
			InputStream lastnamePoolInputStream = theClassLoader.getResourceAsStream(POOL_OF_LASTNAMES);
			InputStream specialtyPoolInputStream = theClassLoader.getResourceAsStream(POOL_OF_SPECIALTIES);
			
			Scanner lastnamePoolScanner = new Scanner(lastnamePoolInputStream);
			Scanner firstnamePoolScanner = new Scanner(firstnamePoolInputStream);
			Scanner specialtyPoolScanner = new Scanner(specialtyPoolInputStream);
			
		    ) 
		{
			while (lastnamePoolScanner.hasNext()) 
			{
				poolOfLastnames.add(lastnamePoolScanner.nextLine());
			}

			while (firstnamePoolScanner.hasNext()) 
			{
				poolOfFirstnames.add(firstnamePoolScanner.nextLine());
			}
			
			while (specialtyPoolScanner.hasNext()) 
			{
				poolOfSpecialties.add(specialtyPoolScanner.nextLine());
			}
	
		} 
		catch (IOException e) 
		{
			logger.error("something went wrong building pools:  {}", e.getLocalizedMessage());
		}

	}

	//Depending on the attribute name, create random data for that field and return it.
	@Override
	public String getType(DataProviderStrategy strategy, AttributeMetadata attributeMetadata,
			Map< String, Type> genericTypesArgumentsMap) 
	{
		
		String stringType = "";
		
		if (EMAIL_FIELD.equals(attributeMetadata.getAttributeName())) 
		{
			//Create random email
			StringBuilder sb = new StringBuilder();
			
			while (sb.length() < 3) 
			{
				int index = (int) (rnd.nextFloat() * ALPHA_LETTERS.length());
				sb.append(ALPHA_LETTERS.charAt(index));
			}
			
			while (sb.length() < 8) 
			{
				int index = (int) (rnd.nextFloat() * DIGITS.length());
				sb.append(DIGITS.charAt(index));
			}
			
			sb.append("@algonquinlive.com");
			stringType = sb.toString();
			
		} 
		
		else if (LASTNAME_FIELD.equals(attributeMetadata.getAttributeName())) 
		{
			stringType = poolOfLastnames.get(rnd.nextInt(poolOfLastnames.size()));
		} 
		
		else if (FIRSTNAME_FIELD.equals(attributeMetadata.getAttributeName())) 
		{
			stringType = poolOfFirstnames.get(rnd.nextInt(poolOfFirstnames.size()));			
		} 
		
		else if (PHONENUMBER_FIELD.equals(attributeMetadata.getAttributeName())) 
		{
			int npa = rnd.nextInt(643) + 100;
			int extension = rnd.nextInt(9000) + 1000;
			stringType = String.format("613%03d%04d", npa, extension);
		} 
		
		else if (SPECIALTY_FIELD.equals(attributeMetadata.getAttributeName())) 
		{
			stringType = poolOfSpecialties.get(rnd.nextInt(poolOfSpecialties.size()));			
		} 
	
		else 
		{			
			stringType = super.getType(strategy, attributeMetadata, genericTypesArgumentsMap);			
		}
		
		return stringType;
		
	}

}