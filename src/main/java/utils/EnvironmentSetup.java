/**
 * 
 */
package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.util.Properties;

import controllers.BaseMethod;
import controllers.WebDriverFactory;

/**
 * @Author Gladson Antony
 * @Date 28-Jan-2017
 */
public class EnvironmentSetup extends BaseMethod
{
	static WebDriverFactory webDriverFactory = new WebDriverFactory();
	
	public static void environmentSetup() throws Exception
	{
		try 
		{
			Properties properties = new Properties();
			properties.setProperty("Author", "Gladson Antony");
			properties.setProperty("Browser", webDriverFactory.Browser);
			properties.setProperty("OS", webDriverFactory.OSName);
			properties.setProperty("OS Architecture", webDriverFactory.OSArchitecture);
			properties.setProperty("OS Bit", webDriverFactory.OSBit);
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
