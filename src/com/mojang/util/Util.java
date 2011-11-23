package com.mojang.util;

import java.io.File;


public class Util {

	private static EnumOS getOs()
	{
		String s = System.getProperty("os.name").toLowerCase();
		if (s.contains("win"))
		{
			return EnumOS.windows;
		}
		if (s.contains("mac"))
		{
			return EnumOS.macos;
		}
		if (s.contains("solaris"))
		{
			return EnumOS.solaris;
		}
		if (s.contains("sunos"))
		{
			return EnumOS.solaris;
		}
		if (s.contains("linux"))
		{
			return EnumOS.linux;
		}
		if (s.contains("unix"))
		{
			return EnumOS.linux;
		} else
		{
			return EnumOS.unknown;
		}
	}

	public static File getAppDir(String s)
	{
		String s1 = System.getProperty("user.home", ".");
		File file;
		switch (OsMap.os[getOs().ordinal()]) {
		case 1:
		case 2:
			file = new File(s1, (new StringBuilder()).append('.').append(s).append('/').toString());
			break;

		case 3:
			String s2 = System.getenv("APPDATA");
			if (s2 != null)
			{
				file = new File(s2, (new StringBuilder()).append(".").append(s).append('/').toString());
			} else
			{
				file = new File(s1, (new StringBuilder()).append('.').append(s).append('/').toString());
			}
			break;

		case 4:
			file = new File(s1, (new StringBuilder()).append("Library/Application Support/").append(s).toString());
			break;

		default:
			file = new File(s1, (new StringBuilder()).append(s).append('/').toString());
			break;
		}
		if (!file.exists() && !file.mkdirs())
		{
			throw new RuntimeException((new StringBuilder()).append("The working directory could not be created: ").append(file).toString());
		} else
		{
			return file;
		}
	}

}
