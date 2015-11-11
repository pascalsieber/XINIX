package ch.zhaw.iwi.cis.pews.dao;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;

public interface ExerciseTemplateDao extends WorkshopObjectDao
{

	ExerciseTemplate findExerciseTemplateByID( String id );

}
