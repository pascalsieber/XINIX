package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;


public interface ExerciseDefinitionService extends WorkshopObjectService
{

	ExerciseDefinitionImpl findExerciseDefinitionByID( String id );

	List< ExerciseDefinitionImpl > findAllExerciseDefinitions();
}
