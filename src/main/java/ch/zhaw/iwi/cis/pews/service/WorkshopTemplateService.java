package ch.zhaw.iwi.cis.pews.service;

import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;

import java.util.List;

public interface WorkshopTemplateService extends WorkshopObjectService
{

	WorkshopTemplate findWorkshopTemplateByID( String id );

	List<WorkshopTemplate> findAllWorkshopTemplates();

	void updateOrderOfExerciseTemplates( WorkshopTemplate wrapper );

	public String persistWorkshopTemplate( WorkshopTemplate workshopTemplate );

}
