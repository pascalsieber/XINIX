package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;

public interface WorkshopService extends WorkflowElementService
{

	public List< WorkshopImpl > findAllWorkshopsSimple();

	public WorkshopImpl findWorkshopByID( String id );

	public void reset( String workshopID );

	public String generateFromTemplate( WorkshopImpl obj );

}
