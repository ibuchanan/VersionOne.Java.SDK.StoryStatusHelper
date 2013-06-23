package com.versionone.example.storystatushelper;

import com.versionone.apiclient.Query;

public interface StoryStatusRepository {

	boolean isDirty();

	Query buildQueryForAllStoryStatuses();

}
