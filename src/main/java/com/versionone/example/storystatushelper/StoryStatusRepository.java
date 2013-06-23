package com.versionone.example.storystatushelper;

import com.versionone.DB.DateTime;
import com.versionone.apiclient.Query;

public interface StoryStatusRepository {

	boolean isDirty() throws StoryStatusRepositoryException;

	void reload() throws StoryStatusRepositoryException;

	Query buildQueryForAllStoryStatuses();
	
	boolean areThereNewStoryStatusesAfter(DateTime thisDate) throws StoryStatusRepositoryException;

}
