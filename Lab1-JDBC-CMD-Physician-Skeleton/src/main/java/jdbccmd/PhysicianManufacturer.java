/*********************************************************************************************************
 * File:  PhysicianManufacturer.java Course Materials CST 8277
 * 
 * @author Robin Phillis
 * @version 1.0
 * @since 2024-09-14
 * 
 * @description This class extends `StringTypeManufacturerImpl` from the PODAM library to provide 
 *              custom random data generation for fields in the `Physician` class. It uses predefined 
 *              pools of data (e.g., last names, first names, specialties) and generates random email 
 *              addresses and phone numbers. It initializes these data pools from text files and uses a 
 *              `SecureRandom` instance for generating random values.
 * 
 * @see uk.co.jemos.podam.typeManufacturers.StringTypeManufacturerImpl
 * @see uk.co.jemos.podam.api.AttributeMetadata
 * @see uk.co.jemos.podam.api.DataProviderStrategy
 * @see java.security.SecureRandom
 * @see java.util.List
 * @see java.util.Map
 * @see java.util.Scanner
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
 * Custom implementation of `StringTypeManufacturerImpl` to generate random data for fields in the 
 * `Physician` class. This implementation is specifically used to provide random values for attributes
 * such as last names, first names, email addresses, phone numbers, and specialties.
 * The class uses predefined pools of data loaded from text files and a `SecureRandom` instance for 
 * generating random values. The email addresses and phone numbers are generated according to specific 
 * formats, while other attributes are selected from the loaded data pools.
 * 
 * @see uk.co.jemos.podam.typeManufacturers.StringTypeManufacturerImpl
 * @see uk.co.jemos.podam.api.AttributeMetadata
 * @see uk.co.jemos.podam.api.DataProviderStrategy
 * @see java.security.SecureRandom
 * @see java.util.List
 * @see java.util.Map
 * @see java.util.Scanner
 */
public class PhysicianManufacturer extends StringTypeManufacturerImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	/** Field name for last names in the `Physician` class */
	private static final String LASTNAME_FIELD = "lastName";
	/** Field name for first names in the `Physician` class */
	private static final String FIRSTNAME_FIELD = "firstName";
	/** Field name for email addresses in the `Physician` class */
	private static final String EMAIL_FIELD = "email";
	/** Field name for phone numbers in the `Physician` class */
	private static final String PHONENUMBER_FIELD = "phoneNumber";
	/** Field name for specialties in the `Physician` class */
	private static final String SPECIALTY_FIELD = "specialty";

	/** File containing pool of last names */
	protected static final String POOL_OF_LASTNAMES = "lastnamePool.txt";
	/** File containing pool of first names */
	protected static final String POOL_OF_FIRSTNAMES = "firstnamePool.txt";
	/** File containing pool of specialties */
	protected static final String POOL_OF_SPECIALTIES = "specialtyPool.txt";
	
	/** Characters used for generating random strings */
	protected static final String ALPHA_LETTERS = "abcdefghijklmnopqrstuvwxyz";
	protected static final String DIGITS = "1234567890";
	
	/** Secure random number generator for better randomness */
	protected static SecureRandom rnd = new SecureRandom();
	
	/** List of last names loaded from file */
	protected static List<String> poolOfLastnames = new ArrayList<>();
	/** List of first names loaded from file */
	protected static List<String> poolOfFirstnames = new ArrayList<>();
	/** List of specialties loaded from file */
	protected static List<String> poolOfSpecialties = new ArrayList<>();

	/** Static block to initialize data pools from files */
	static {
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
			logger.error("Error loading data pools: {}", e.getLocalizedMessage());
		}
	}

	/**
	 * Generates a random value for a given attribute based on its name.
	 * <p>
	 * Depending on the attribute name, this method generates random data for fields such as email, 
	 * last name, first name, phone number, or specialty. If the attribute name does not match any 
	 * predefined field, it falls back to the default implementation in `StringTypeManufacturerImpl`.
	 * </p>
	 * 
	 * @param strategy The data provider strategy used to generate the data.
	 * @param attributeMetadata Metadata about the attribute for which data is being generated.
	 * @param genericTypesArgumentsMap A map of generic type arguments.
	 * @return A randomly generated string suitable for the given attribute.
	 */
	@Override
	public String getType(DataProviderStrategy strategy, AttributeMetadata attributeMetadata,
			Map<String, Type> genericTypesArgumentsMap) 
	{
		
		String stringType = "";
		
		if (EMAIL_FIELD.equals(attributeMetadata.getAttributeName())) 
		{
			// Generate a random email address
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
