package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;


public interface ExerciseDefinitionService extends WorkshopObjectService
{

	ExerciseTemplate findExerciseDefinitionByID( String id );

	List< ExerciseTemplate > findAllExerciseDefinitions();

	void removeExerciseDefinition( ExerciseTemplate obj );
}
