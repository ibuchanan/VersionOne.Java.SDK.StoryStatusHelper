package com.versionone.example.storystatushelper;

import static org.junit.Assert.*;

import com.versionone.apiclient.EnvironmentContext;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class StoryStatusRepositoryTest {

	@Test
    public void new_story_status_repository_is_dirty()
    {
    	// Given a connection to a VersionOne instance defined in the app.config
    	EnvironmentContext cx = null;
    	try {
			cx = new EnvironmentContext();
		} catch (Exception e) {
			fail(e.getMessage());
		}
        // When I create a new repository with that connection
    	StoryStatusRepository repository = new StoryStatusRepositoryApiClient(cx);
        // Then it is initially dirty
        assertTrue( repository.isDirty() );
    }
}
