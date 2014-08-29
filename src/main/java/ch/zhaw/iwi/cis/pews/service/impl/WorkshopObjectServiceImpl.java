package ch.zhaw.iwi.cis.pews.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.model.Client;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;
import ch.zhaw.sml.iwi.cis.exwrapper.java.io.CloseableWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.io.IOExceptionWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.io.ObjectInputStreamWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.io.ObjectOutputStreamWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.io.OutputStreamWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.ClassWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.reflect.MethodWrapper;

public abstract class WorkshopObjectServiceImpl extends ServiceImpl implements WorkshopObjectService
{
	@SuppressWarnings( "unchecked" )
	@Override
	public < T extends WorkshopObject > String persist( T object )
	{
		Object newObject = setClientInObjectGraph( object );
		return getWorkshopObjectDao().persist( (T)newObject );
	}

	@Override
	public < T extends WorkshopObject > void remove( T object )
	{
		getWorkshopObjectDao().remove( object );
	}

	@Override
	public < T extends WorkshopObject > T findByID( String id )
	{
		return getWorkshopObjectDao().findById( id );
	}

	@Override
	public < T extends WorkshopObject > List< T > findAll()
	{
		return getWorkshopObjectDao().findByAll( UserContext.getCurrentUser().getClient().getID() );
	}

	protected abstract WorkshopObjectDao getWorkshopObjectDao();
	
	private Object setClientInObjectGraph( Object object )
	{
		byte[] byteArray = serialize( object );
		Object newObject = deserialize( byteArray );
		
		return newObject;
	}
	
	private byte[] serialize( Object object )
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = ObjectOutputStreamWrapper.__new( baos );
		ObjectOutputStreamWrapper.writeObject( oos, object );
		OutputStreamWrapper.flush( oos );
		OutputStreamWrapper.flush( baos );
		byte[] byteArray = baos.toByteArray();
		CloseableWrapper.close( oos );
		CloseableWrapper.close( baos );
		
		return byteArray;
	}
	
	private Object deserialize( byte[] byteArray )
	{
		ByteArrayInputStream bais = new ByteArrayInputStream( byteArray );
		ObjectInputStream ois = ClientReplacementInputStream.create( bais );
		Object object = ObjectInputStreamWrapper.readObject( ois );
		CloseableWrapper.close( ois );
		CloseableWrapper.close( bais );
		
		return object;
	}
	
	private static class ClientReplacementInputStream extends ObjectInputStream
	{
		public static ClientReplacementInputStream create( InputStream in )
		{
			try
			{
				return new ClientReplacementInputStream( in );
			}
			catch ( IOException e )
			{
				throw new IOExceptionWrapper( e );
			}
		}
		
		public ClientReplacementInputStream( InputStream in ) throws IOException
		{
			super( in );
			enableResolveObject( true );
		}

		@Override
		protected Object resolveObject( Object obj ) throws IOException
		{
			if ( obj instanceof WorkshopObject )
			{
				Method method = ClassWrapper.getMethod( WorkshopObject.class, "setClient", Client.class );
				MethodWrapper.invoke( method, obj, UserContext.getCurrentUser().getClient() );
			}
			
			return obj;
		}
	}
}
