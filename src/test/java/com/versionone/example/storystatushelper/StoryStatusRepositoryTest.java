package com.versionone.example.storystatushelper;

import static org.junit.Assert.*;

import java.util.Map.Entry;

import com.versionone.apiclient.APIException;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.Attribute;
import com.versionone.apiclient.ConnectionException;
import com.versionone.apiclient.EnvironmentContext;
import com.versionone.apiclient.IAssetType;
import com.versionone.apiclient.IAttributeDefinition;
import com.versionone.apiclient.OidException;
import com.versionone.apiclient.Query;
import com.versionone.apiclient.QueryResult;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class StoryStatusRepositoryTest {

	@Test
	public void new_story_status_repository_is_dirty() {
		// Given a connection to a VersionOne instance defined in the APIConfiguration.properties
		EnvironmentContext cx = null;
		try {
			cx = new EnvironmentContext();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// When I create a new repository with that connection
		StoryStatusRepository repository = new StoryStatusRepositoryApiClient(cx);
		// Then it is initially dirty
		boolean dirty = false;
		try {
			dirty = repository.isDirty();
		} catch (StoryStatusRepositoryException e) {
			fail(e.getMessage());
		}
		assertTrue(dirty);
	}

	@Test
	public void query_for_story_statuses_is_scoped_to_StoryStatus() {
		// Given a connection to a VersionOne instance defined in the APIConfiguration.properties
		EnvironmentContext cx = null;
		try {
			cx = new EnvironmentContext();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// And a new repository with that connection
		StoryStatusRepository repository = new StoryStatusRepositoryApiClient(cx);
		// When I build the query for story statuses
		Query query = repository.buildQueryForAllStoryStatuses();
		// Then the asset type is StoryStatus
		assertEquals("StoryStatus", query.getAssetType().getToken());
	}

	@Test
	public void query_for_request_categories_selects_name() {
		// Given a connection to a VersionOne instance defined in the APIConfiguration.properties
		EnvironmentContext cx = null;
		try {
			cx = new EnvironmentContext();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// And a new repository with that connection
		StoryStatusRepository repository = new StoryStatusRepositoryApiClient(cx);
		// And a reference to the story status asset type
		IAssetType assetType = cx.getMetaModel().getAssetType("StoryStatus");
		// And a reference to the name attribute
		IAttributeDefinition nameAttribute = assetType.getAttributeDefinition("Name");
		// When I build the query for request categories
		Query query = repository.buildQueryForAllStoryStatuses();
		// Then the query selects the name attribute
		assertTrue(query.getSelection().contains(nameAttribute));
	}

	@Test
	public void query_for_request_categories_selects_change_date() {
		// Given a connection to a VersionOne instance defined in the APIConfiguration.properties
		EnvironmentContext cx = null;
		try {
			cx = new EnvironmentContext();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// And a new repository with that connection
		StoryStatusRepository repository = new StoryStatusRepositoryApiClient(cx);
		// And a reference to the story status asset type
		IAssetType assetType = cx.getMetaModel().getAssetType("StoryStatus");
		// And a reference to the name attribute
		IAttributeDefinition nameAttribute = assetType.getAttributeDefinition("ChangeDateUTC");
		// When I build the query for request categories
		Query query = repository.buildQueryForAllStoryStatuses();
		// Then the query selects the name attribute
		assertTrue(query.getSelection().contains(nameAttribute));
	}

	@Test
	public void reload_is_clean() {
		// Given a connection to a VersionOne instance defined in the APIConfiguration.properties
		EnvironmentContext cx = null;
		try {
			cx = new EnvironmentContext();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// And a new repository with that connection
		StoryStatusRepository repository = new StoryStatusRepositoryApiClient(cx);
		
		QueryResult result = null;
		try {
			result = cx.getServices().retrieve(repository.buildQueryForAllStoryStatuses());
		} catch (ConnectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (APIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (OidException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (Asset asset : result.getAssets()) {
			for (Entry<String, Attribute> attribute : asset.getAttributes().entrySet()) {
				try {
					String k = attribute.getKey();
					Object v = attribute.getValue().getValue();
				} catch (APIException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		
		// When I reload the repository
		try {
			repository.reload();
		} catch (StoryStatusRepositoryException e) {
			fail(e.getMessage());
		}
		// Then the repository is not dirty
		boolean dirty = false;
		try {
			dirty = repository.isDirty();
		} catch (StoryStatusRepositoryException e) {
			fail(e.getMessage());
		}
		assertFalse(dirty);
	}


}
