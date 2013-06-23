package com.versionone.example.storystatushelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.versionone.DB.DateTime;
import com.versionone.apiclient.APIException;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.ConnectionException;
import com.versionone.apiclient.EnvironmentContext;
import com.versionone.apiclient.FilterTerm;
import com.versionone.apiclient.IAssetType;
import com.versionone.apiclient.IAttributeDefinition;
import com.versionone.apiclient.MetaException;
import com.versionone.apiclient.OidException;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.QueryResult;

public class StoryStatusRepositoryApiClient implements StoryStatusRepository {
	
	private EnvironmentContext cx;
	private DateTime mostRecentChangeDateTime;
	private IAssetType storyStatusType;
	private IAttributeDefinition nameAttribute;
	private IAttributeDefinition changeAttribute;
	private Query queryForAllStoryStatuses;
	private Map<String, String> allStoryStatuses;
	private static final DateFormat V1STYLE = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

	public StoryStatusRepositoryApiClient(EnvironmentContext cx) {
		this.cx = cx;
		mostRecentChangeDateTime = null;
		storyStatusType = cx.getMetaModel().getAssetType("StoryStatus");
		nameAttribute = storyStatusType.getAttributeDefinition("Name");
		changeAttribute = storyStatusType.getAttributeDefinition("ChangeDateUTC");
		queryForAllStoryStatuses = buildQueryForAllStoryStatuses();
	}

	public boolean isDirty() throws StoryStatusRepositoryException {
		if (null==mostRecentChangeDateTime) {
			return true;
		}
        return areThereNewStoryStatusesAfter(mostRecentChangeDateTime);
    }

	public boolean areThereNewStoryStatusesAfter(DateTime thisDate) throws StoryStatusRepositoryException {
		Query query = new Query(storyStatusType);
        query.getSelection().add(changeAttribute);
        FilterTerm term = new FilterTerm(changeAttribute);
        term.greater(V1STYLE.format(thisDate.getValue()));
        query.setFilter(term);
        QueryResult result;
		try {
			result = cx.getServices().retrieve(query);
		} catch (ConnectionException e) {
			throw new StoryStatusRepositoryException(e);
		} catch (APIException e) {
			throw new StoryStatusRepositoryException(e);
		} catch (OidException e) {
			throw new StoryStatusRepositoryException(e);
		}
		return result.getTotalAvaliable() > 0;
	}

	public Query buildQueryForAllStoryStatuses() {
		Query query = new Query(storyStatusType);
		query.getSelection().add(nameAttribute);
		query.getSelection().add(changeAttribute);
        return query;
	}

	public void reload() throws StoryStatusRepositoryException {
		allStoryStatuses = new HashMap<String, String>();
		QueryResult result;
		try {
			result = cx.getServices().retrieve(queryForAllStoryStatuses);
		} catch (ConnectionException e) {
			throw new StoryStatusRepositoryException(e);
		} catch (APIException e) {
			throw new StoryStatusRepositoryException(e);
		} catch (OidException e) {
			throw new StoryStatusRepositoryException(e);
		}
		for (Asset asset : result.getAssets()) {
			DateTime changeDateTime = null;
			try {
				allStoryStatuses.put((String)asset.getAttribute(nameAttribute).getValue(), asset.getOid().getToken());
	            // Remember the most recent change to VersionOne for checking dirty state
				String myDateTime = asset.getAttribute(changeAttribute).getValue().toString();
	            changeDateTime = new DateTime(asset.getAttribute(changeAttribute).getValue());
			} catch (APIException e) {
				throw new StoryStatusRepositoryException(e);
			} catch (MetaException e) {
				throw new StoryStatusRepositoryException(e);
			}
            if ((null==mostRecentChangeDateTime) || (changeDateTime.compareTo(mostRecentChangeDateTime) > 0))
            {
                mostRecentChangeDateTime = changeDateTime;
            }
		}
	}

}
