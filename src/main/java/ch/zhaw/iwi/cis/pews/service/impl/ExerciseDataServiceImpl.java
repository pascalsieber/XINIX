package ch.zhaw.iwi.cis.pews.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.collection.internal.PersistentBag;
import org.hibernate.collection.internal.PersistentIdentifierBag;
import org.hibernate.collection.internal.PersistentList;
import org.hibernate.collection.internal.PersistentMap;
import org.hibernate.collection.internal.PersistentSet;
import org.hibernate.collection.internal.PersistentSortedMap;
import org.hibernate.collection.internal.PersistentSortedSet;
import org.hibernate.collection.spi.PersistentCollection;

import ch.zhaw.iwi.cis.pews.PewsConfig;
import ch.zhaw.iwi.cis.pews.dao.ExerciseDao;
import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.data.impl.EvaluationDataDao;
import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseDataDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.impl.WorkshopDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.CleanExerciseDataOutputStream;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.OwnableObject;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.data.WorkflowElementDataImpl;
import ch.zhaw.iwi.cis.pews.model.data.export.ExerciseDataViewObject;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.model.xinix.XinixImage;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.CompressionExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.EvaluationExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.EvaluationResultExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.P2POneExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.P2PTwoExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.PinkLabsExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.PosterExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.SimplePrototypingExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.XinixExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.XinixImageDataService;
import ch.zhaw.iwi.cis.pews.service.exercise.data.impl.You2MeExerciseDataService;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.EvaluationExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2PTwoData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.PinkLabsExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.SimplePrototypingData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.XinixData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.You2MeExerciseData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.CompressionExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.EvaluationExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.EvaluationResultExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.P2POneExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.P2PTwoExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.PinkLabsExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.PosterExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.SimplyPrototypingExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.XinixExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.You2MeExercise;
import ch.zhaw.sml.iwi.cis.exwrapper.java.io.CloseableWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.io.IOExceptionWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.io.ObjectInputStreamWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.io.ObjectOutputStreamWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.io.OutputStreamWrapper;
import ch.zhaw.sml.iwi.cis.exwrapper.java.lang.ClassWrapper;

// TODO: find a way to merge cleanseData and cleanObjectGraph

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class ExerciseDataServiceImpl extends WorkshopObjectServiceImpl implements ExerciseDataService
{
	private ExerciseDataDao exerciseDataDao;
	private ExerciseDao exerciseDao;
	private ExerciseDataDao evaluationDataDao;
	private WorkshopDao workshopDao;

	public ExerciseDataServiceImpl()
	{
		exerciseDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDataDaoImpl.class.getSimpleName() );
		exerciseDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDaoImpl.class.getSimpleName() );
		evaluationDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( EvaluationDataDao.class.getSimpleName() );
		workshopDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkshopDaoImpl.class.getSimpleName() );
	}

	// TODO move this from manual entries to an automatic solution (e.g. with an annotation)
	private static final Map< String, Class< ? extends ExerciseDataServiceImpl > > EXERCISEDATACLASSSPECIFICSERVICES = new HashMap< String, Class< ? extends ExerciseDataServiceImpl > >();
	static
	{
		EXERCISEDATACLASSSPECIFICSERVICES.put( PinkLabsExerciseData.class.getSimpleName(), PinkLabsExerciseDataService.class );
		EXERCISEDATACLASSSPECIFICSERVICES.put( You2MeExerciseData.class.getSimpleName(), You2MeExerciseDataService.class );
		EXERCISEDATACLASSSPECIFICSERVICES.put( P2POneData.class.getSimpleName(), P2POneExerciseDataService.class );
		EXERCISEDATACLASSSPECIFICSERVICES.put( P2PTwoData.class.getSimpleName(), P2PTwoExerciseDataService.class );
		EXERCISEDATACLASSSPECIFICSERVICES.put( SimplePrototypingData.class.getSimpleName(), SimplePrototypingExerciseDataService.class );
		EXERCISEDATACLASSSPECIFICSERVICES.put( XinixData.class.getSimpleName(), XinixExerciseDataService.class );
		EXERCISEDATACLASSSPECIFICSERVICES.put( CompressionExerciseData.class.getSimpleName(), CompressionExerciseDataService.class );
		EXERCISEDATACLASSSPECIFICSERVICES.put( XinixImage.class.getSimpleName(), XinixImageDataService.class );
		EXERCISEDATACLASSSPECIFICSERVICES.put( EvaluationExerciseData.class.getSimpleName(), EvaluationExerciseDataService.class );
	}

	private Class< ? > getExerciseDataClassSpecificService( String exerciseDataClassName )
	{
		return EXERCISEDATACLASSSPECIFICSERVICES.get( exerciseDataClassName );
	}

	private static final Map< String, Class< ? extends ExerciseDataServiceImpl > > EXERCISECLASSSPECIFICSERVICES = new HashMap< String, Class< ? extends ExerciseDataServiceImpl >>();
	static
	{
		EXERCISECLASSSPECIFICSERVICES.put( PinkLabsExercise.class.getSimpleName(), PinkLabsExerciseDataService.class );
		EXERCISECLASSSPECIFICSERVICES.put( You2MeExercise.class.getSimpleName(), You2MeExerciseDataService.class );
		EXERCISECLASSSPECIFICSERVICES.put( P2POneExercise.class.getSimpleName(), P2POneExerciseDataService.class );
		EXERCISECLASSSPECIFICSERVICES.put( P2PTwoExercise.class.getSimpleName(), P2PTwoExerciseDataService.class );
		EXERCISECLASSSPECIFICSERVICES.put( SimplyPrototypingExercise.class.getSimpleName(), SimplePrototypingExerciseDataService.class );
		EXERCISECLASSSPECIFICSERVICES.put( XinixExercise.class.getSimpleName(), XinixExerciseDataService.class );
		EXERCISECLASSSPECIFICSERVICES.put( CompressionExercise.class.getSimpleName(), CompressionExerciseDataService.class );
		EXERCISECLASSSPECIFICSERVICES.put( EvaluationExercise.class.getSimpleName(), EvaluationExerciseDataService.class );
		EXERCISECLASSSPECIFICSERVICES.put( PosterExercise.class.getSimpleName(), PosterExerciseDataService.class );
		EXERCISECLASSSPECIFICSERVICES.put( EvaluationResultExercise.class.getSimpleName(), EvaluationResultExerciseDataService.class );
	}

	private Class< ? > getExerciseClassSpecificService( String exerciseClassName )
	{
		return EXERCISECLASSSPECIFICSERVICES.get( exerciseClassName );
	}

	// overriding persist in order to handle special behavior for EvaluationExerciseData
	// this needs to be delegated to evaluationDataDao
	// TODO: this is a quick fix! check if there is a more elegant way to do this
	@Override
	public < T extends WorkshopObject > String persist( T object )
	{
		if ( object instanceof EvaluationExerciseData )
		{
			return evaluationDataDao.persist( object );
		}
		else
		{
			return super.persist( object );

		}
	}

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return exerciseDataDao;
	}

	@Override
	public ExerciseDataImpl findExerciseDataByID( String id )
	{
		ExerciseDataService service = ZhawEngine.getManagedObjectRegistry().getManagedObject( getExerciseDataClassSpecificService( findByID( id ).getClass().getSimpleName() ).getSimpleName() );
		return (ExerciseDataImpl)cleanObjectGraph( service.findExerciseDataByID( id ) );
	}

	public ExerciseDataImpl genericFindDataByID( String id )
	{
		return (ExerciseDataImpl)cleanObjectGraph( exerciseDataDao.findDataByID( id ) );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findAllExerciseData()
	{
		return (List< ExerciseDataImpl >)cleanObjectGraph( findAll() );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID )
	{
		ExerciseImpl ex = exerciseDao.findById( exerciseID );
		Class< ? > serviceClass = getExerciseClassSpecificService( ex.getClass().getSimpleName() );
		ExerciseDataService service = ZhawEngine.getManagedObjectRegistry().getManagedObject( serviceClass.getSimpleName() );
		return (List< ExerciseDataImpl >)cleanseData( service.findByExerciseID( exerciseID ) );
	}

	@SuppressWarnings( "unchecked" )
	public List< ExerciseDataImpl > genericFindByExerciseID( String exerciseID )
	{
		return (List< ExerciseDataImpl >)cleanseData( exerciseDataDao.findByExerciseID( exerciseID ) );
	}

	@Override
	public void removeExerciseDataByID( String id )
	{
		remove( findByID( id ) );
	}

	@Override
	public String exportByExerciseID( String exerciseID )
	{
		ExerciseImpl ex = exerciseDao.findById( exerciseID );
		Class< ? > serviceClass = getExerciseClassSpecificService( ex.getClass().getSimpleName() );
		ExerciseDataService service = ZhawEngine.getManagedObjectRegistry().getManagedObject( serviceClass.getSimpleName() );

		List< ExerciseDataViewObject > data = service.getExportableDataByExerciseID( ex );
		Map< ExerciseImpl, List< ExerciseDataViewObject > > exportableDataMap = new HashMap< ExerciseImpl, List< ExerciseDataViewObject > >();
		exportableDataMap.put( ex, data );
		return exportDataToFile( exportableDataMap );
	}

	@Override
	public < T extends ExerciseDataViewObject > List< T > getExportableDataByExerciseID( ExerciseImpl exercise )
	{
		throw new UnsupportedOperationException( "no suitable Service class found for handling" );
	}

	@Override
	public String exportByWorkshopID( String workshopID )
	{
		WorkshopImpl workshop = workshopDao.findWorkshopByID( workshopID );
		Map< ExerciseImpl, List< ExerciseDataViewObject > > exportableDataMap = new HashMap< ExerciseImpl, List< ExerciseDataViewObject > >();

		for ( ExerciseImpl exercise : workshop.getExercises() )
		{
			ExerciseImpl ex = exerciseDao.findById( exercise.getID() );
			Class< ? > serviceClass = getExerciseClassSpecificService( ex.getClass().getSimpleName() );
			ExerciseDataService service = ZhawEngine.getManagedObjectRegistry().getManagedObject( serviceClass.getSimpleName() );

			List< ExerciseDataViewObject > data = service.getExportableDataByExerciseID( ex );
			exportableDataMap.put( ex, data );
		}

		return exportDataToFile( exportableDataMap );
	}

	@SuppressWarnings( "resource" )
	private String exportDataToFile( Map< ExerciseImpl, List< ExerciseDataViewObject >> exportableDataMap )
	{
		String exportDirLocation = PewsConfig.getWebDir() + File.separator + "export";

		// init export directory if not available
		File dir = new File( exportDirLocation );
		if ( !dir.exists() )
			dir.mkdirs();

		String filePath = dir.getAbsolutePath() + File.separator + "export.xlsx";
		// TODO: make this configurable
		String downloadPath = "http://localhost:" + ZhawEngine.APPLICATION_PORT + "/export/export.xlsx";
		Workbook workbook = new XSSFWorkbook();

		// make sheet for every entry in exportableDataMap
		for ( Entry< ExerciseImpl, List< ExerciseDataViewObject >> entry : exportableDataMap.entrySet() )
		{
			// generate sheet with headers
			int rowIndex = 0;
			Sheet sheet = workbook.createSheet( entry.getKey().getID() );
			sheet.createRow( rowIndex++ ).createCell( 0 ).setCellValue( entry.getKey().getName() );
			sheet.createRow( rowIndex++ ).createCell( 0 ).setCellValue( entry.getKey().getQuestion() );
			sheet.createRow( rowIndex++ ); // empty row

			// generate column headers if data not empty
			if ( entry.getValue().isEmpty() )
			{
				sheet.createRow( rowIndex++ ).createCell( 0 ).setCellValue( "no data found" );
			}
			else
			{
				// get fields of data objects
				List< Field > fields = getAllFields( new ArrayList<Field>(), entry.getValue().get( 0 ).getClass() );
				
				// headers
				int colIndex = 0;
				Row row = sheet.createRow( rowIndex++ );

				for ( Field field : fields )
				{
					field.setAccessible( true );
					row.createCell( colIndex++ ).setCellValue( field.getName() );
				}

				// write row for every data element
				for ( ExerciseDataViewObject dataObj : entry.getValue() )
				{
					int dataColIndex = 0;
					Row dataRow = sheet.createRow( rowIndex++ );

					// write cell for every attribute / field value
					for ( Field field : fields )
					{
						field.setAccessible( true );
						try
						{
							dataRow.createCell( dataColIndex++ ).setCellValue( field.get( dataObj ).toString() );
						}
						catch ( IllegalArgumentException | IllegalAccessException e )
						{
							throw new RuntimeException( e.getStackTrace().toString() );
						}
					}
				}
			}
		}

		// write workbook as xlsx file
		try
		{
			FileOutputStream fos = new FileOutputStream( filePath );
			workbook.write( fos );
			fos.close();

			return downloadPath;
		}
		catch ( IOException e )
		{
			throw new RuntimeException( e.getStackTrace().toString() );
		}
	}

	/**
	 * helper method to get all fields (including declared fields of super classes) of an object recursively
	 */
	private List< Field > getAllFields( List< Field > fields, Class< ? > type )
	{
		Field[] declaredFields = type.getDeclaredFields();
		for ( int i = 0; i < declaredFields.length; i++ )
		{
			fields.add( declaredFields[i] );
		}
		
		if ( type.getSuperclass() != null )
		{
			fields = getAllFields( fields, type.getSuperclass() );
		}
		
		return fields;
	}

	@SuppressWarnings( { "resource" } )
	private Object cleanseData( List< ExerciseDataImpl > data )
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream ooStream = new CleanExerciseDataOutputStream( baos );
			ooStream.writeObject( data );

			byte[] bytes = baos.toByteArray();

			ByteArrayInputStream bais = new ByteArrayInputStream( bytes );
			ObjectInputStream oiStream = new ObjectInputStream( bais );
			return oiStream.readObject();
		}
		catch ( Exception e )
		{
			throw new RuntimeException( e );
		}
	}

	private Object cleanObjectGraph( Object object )
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
		ObjectInputStream ois = DataCleaningInputStream.create( bais );
		Object object = ObjectInputStreamWrapper.readObject( ois );
		CloseableWrapper.close( ois );
		CloseableWrapper.close( bais );

		return object;
	}

	private static class DataCleaningInputStream extends ObjectInputStream
	{
		private PersistenceUnitUtil puu;
		private static final Map< Class< ? >, Class< ? > > PERSISTENT_TO_TRANSIENT_COLLECTION_MAP = new HashMap< Class< ? >, Class< ? > >();

		static
		{
			PERSISTENT_TO_TRANSIENT_COLLECTION_MAP.put( PersistentList.class, ArrayList.class );
			PERSISTENT_TO_TRANSIENT_COLLECTION_MAP.put( PersistentSet.class, HashSet.class );
			PERSISTENT_TO_TRANSIENT_COLLECTION_MAP.put( PersistentSortedSet.class, TreeSet.class );
			PERSISTENT_TO_TRANSIENT_COLLECTION_MAP.put( PersistentMap.class, HashMap.class );
			PERSISTENT_TO_TRANSIENT_COLLECTION_MAP.put( PersistentSortedMap.class, TreeMap.class );
			PERSISTENT_TO_TRANSIENT_COLLECTION_MAP.put( PersistentBag.class, ArrayList.class );
			PERSISTENT_TO_TRANSIENT_COLLECTION_MAP.put( PersistentIdentifierBag.class, ArrayList.class );
		}

		public static DataCleaningInputStream create( InputStream in )
		{
			try
			{
				return new DataCleaningInputStream( in );
			}
			catch ( IOException e )
			{
				throw new IOExceptionWrapper( e );
			}
		}

		public DataCleaningInputStream( InputStream in ) throws IOException
		{
			super( in );
			enableResolveObject( true );
			EntityManagerFactory emf = ZhawEngine.getManagedObjectRegistry().getManagedObject( "pewsFactory" );
			puu = emf.getPersistenceUnitUtil();
		}

		@Override
		protected Object resolveObject( Object obj ) throws IOException
		{
			if ( obj instanceof ExerciseDataImpl )
			{
				// simplify owner -> ExerciseDataImpl is subclass of OwnableObject
				if ( null != ( (OwnableObject)obj ).getOwner() )
				{
					( (OwnableObject)obj ).getOwner().setCredential( null );
					( (OwnableObject)obj ).getOwner().setParticipation( null );
					( (OwnableObject)obj ).getOwner().setRole( null );
					( (OwnableObject)obj ).getOwner().setSessionAcceptances( null );
					( (OwnableObject)obj ).getOwner().setSessionExecutions( null );
					( (OwnableObject)obj ).getOwner().setSessionInvitations( null );

					if ( ( (OwnableObject)obj ).getOwner() instanceof UserImpl )
					{
						( (UserImpl)( (OwnableObject)obj ).getOwner() ).setGroups( null );
					}
				}

				// simplify workflowElement (i.e. exercise) by excluding template and workshop
				if ( null != ( (WorkflowElementDataImpl)obj ).getWorkflowElement() )
				{
					( (ExerciseDataImpl)obj ).getWorkflowElement().setDerivedFrom( null );
					( (ExerciseImpl)( (ExerciseDataImpl)obj ).getWorkflowElement() ).setWorkshop( null );
				}
			}

			if ( isTerminalCollection( obj ) )
				return replacePersistentCollection( (PersistentCollection)obj );
			else
				return obj;
		}

		private boolean isTerminalCollection( Object sourceObject )
		{
			boolean isTerminalCollection;

			if ( sourceObject instanceof PersistentCollection && !puu.isLoaded( sourceObject ) )
				isTerminalCollection = true;
			else
				isTerminalCollection = false;

			return isTerminalCollection;
		}

		private Collection< ? > replacePersistentCollection( PersistentCollection pCollection )
		{
			Collection< ? > collection = null;

			Class< ? > transientCollectionClass = PERSISTENT_TO_TRANSIENT_COLLECTION_MAP.get( pCollection.getClass() );

			if ( transientCollectionClass == null )
				throw new IllegalStateException( "Unexpected type for pCollection: " + pCollection.getClass().getName() );

			collection = (Collection< ? >)ClassWrapper.newInstance( transientCollectionClass );

			return collection;
		}

	}

}
