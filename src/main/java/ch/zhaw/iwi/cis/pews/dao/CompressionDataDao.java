package ch.zhaw.iwi.cis.pews.dao;

import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressionExerciseDataElement;

public interface CompressionDataDao extends ExerciseDataDao
{
	public CompressionExerciseDataElement findBySolutionAndDescription( String solution, String description );
}
