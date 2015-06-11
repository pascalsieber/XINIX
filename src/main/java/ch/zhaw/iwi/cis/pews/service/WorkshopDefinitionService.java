package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;

public interface WorkshopDefinitionService extends WorkshopObjectService
{

	WorkshopDefinitionImpl findWorkshopDefinitionByID( String id );

	List< WorkshopDefinitionImpl > findAllWorkshopDefinitions();

}
