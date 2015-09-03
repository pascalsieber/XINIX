package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;


public interface ExerciseTemplateService extends WorkshopObjectService
{

	ExerciseTemplate findExerciseTemplateByID( String id );

	List< ExerciseTemplate > findAllExerciseTemplates();

	void removeExerciseTemplate( ExerciseTemplate obj );
}
