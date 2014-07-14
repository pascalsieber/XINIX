package ch.zhaw.sml.iwi.cis.exwrapper.java.util.zip;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import ch.zhaw.sml.iwi.cis.exwrapper.java.io.IOExceptionWrapper;

public class ZipInputStreamWrapper
{
	public static ZipEntry getNextEntry( ZipInputStream zipInputStream )
	{
		try
		{
			return zipInputStream.getNextEntry();
		}
		catch ( IOException e )
		{
			throw new IOExceptionWrapper( e );
		}
	}
}
