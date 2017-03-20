package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface WorkshopService extends WorkflowElementService
{

	public List< WorkshopImpl > findAllWorkshopsSimple();

	public WorkshopImpl findWorkshopByID( String id );

	public void reset( String workshopID );

	public String generateFromTemplate( WorkshopImpl obj );

	public void updateOrderOfExercises( WorkshopImpl wrapper );

	public void updateBasicInformation( WorkshopImpl workshop );

}
