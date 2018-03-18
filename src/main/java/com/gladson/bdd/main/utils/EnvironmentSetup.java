package com.gladson.bdd.main.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.util.Properties;

import com.gladson.bdd.main.controllers.InitMethod;

/**
 * @Author Gladson Antony
 * @Date 28-Jan-2017
 */
public class EnvironmentSetup
{
	
	public static void environmentSetup() throws Exception
	{
		try 
		{
			Properties properties = new Properties();
			properties.setProperty("Author", "Gladson Antony");
			properties.setProperty("Browser", InitMethod.Browser);
			properties.setProperty("OS", InitMethod.OSName);
			properties.setProperty("OS Architecture", InitMethod.OSArchitecture);
			properties.setProperty("OS Bit", InitMethod.OSBit);
			properties.setProperty("Java Version", Runtime.class.getPackage().getImplementationVersion());
			properties.setProperty("Host Name", InetAddress.getLocalHost().getHostName());
			properties.setProperty("Host IP Address", InetAddress.getLocalHost().getHostAddress());

			File file = new File("./src/main/resources/environment.properties");
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.store(fileOut, "Envronement Setup");
			fileOut.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
	}
}
