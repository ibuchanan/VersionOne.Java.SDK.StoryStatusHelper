package com.versionone.example.storystatushelper;

import com.versionone.DB.DateTime;
import com.versionone.apiclient.EnvironmentContext;
import com.versionone.apiclient.IAssetType;
import com.versionone.apiclient.Query;

public class StoryStatusRepositoryApiClient implements StoryStatusRepository {
	
	private DateTime mostRecentChangeDateTime;
	private IAssetType storyStatusType;

	public StoryStatusRepositoryApiClient(EnvironmentContext cx) {
		mostRecentChangeDateTime = null;
		storyStatusType = cx.getMetaModel().getAssetType("StoryStatus");
	}

	public boolean isDirty() {
		if (null==mostRecentChangeDateTime) {
			return true;
		}
		return false;
	}

	public Query buildQueryForAllStoryStatuses() {
		Query query = new Query(storyStatusType);
        return query;
	}

}
