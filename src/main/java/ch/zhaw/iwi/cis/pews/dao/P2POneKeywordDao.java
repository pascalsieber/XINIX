package ch.zhaw.iwi.cis.pews.dao;

import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneKeyword;

public interface P2POneKeywordDao extends ExerciseDataDao
{
	public P2POneKeyword findByKeywordString (String keywordString);
}
