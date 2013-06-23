package com.versionone.example.storystatushelper;

import com.versionone.DB.DateTime;
import com.versionone.apiclient.EnvironmentContext;

public class StoryStatusRepositoryApiClient implements StoryStatusRepository {
	
	private DateTime mostRecentChangeDateTime;

	public StoryStatusRepositoryApiClient(EnvironmentContext cx) {
		mostRecentChangeDateTime = null;
	}

	public boolean isDirty() {
		if (null==mostRecentChangeDateTime) {
			return true;
		}
		return false;
	}

}
