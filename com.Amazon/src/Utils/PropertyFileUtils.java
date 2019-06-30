package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileUtils {

	public static String getValueForKey(String key) throws FileNotFoundException, IOException
	{
		Properties confgProperty=new Properties();
		confgProperty.load(new FileInputStream(new File("./PropertyFile/Environment.properties")));
		return confgProperty.getProperty(key);
	}
}
