package org.kohsuke.github;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

// TODO: Auto-generated Javadoc

/**
 * The Class GHTeamUpdateBuilderTest.
 *
 * @author Rory Kelly
 */
public class GHTeamUpdateBuilderTest extends AbstractGitHubWireMockTest {

    private static final String TEAM_TO_UPDATE_SLUG = "dummy-team-to-update";

    private static final String TEAM_TO_UPDATE_NEW_NAME = "dummy-team-updated";
    private static final String TEAM_TO_UPDATE_NEW_DESCRIPTION = "This is an updated description!";
    private static final GHTeam.Privacy TEAM_TO_UPDATE_NEW_PRIVACY = GHTeam.Privacy.SECRET;
    private static final GHTeam.NotificationSetting TEAM_TO_UPDATE_NEW_NOTIFICATIONS = GHTeam.NotificationSetting.NOTIFICATIONS_DISABLED;
    @Deprecated
    private static final GHOrganization.Permission TEAM_TO_UPDATE_NEW_PERMISSIONS = GHOrganization.Permission.PUSH;

    // private static final String CURRENT_PARENT_TEAM_SLUG = "dummy-current-parent-team";
    private static final String NEW_PARENT_TEAM_SLUG = "dummy-new-parent-team";

    /**
     * Create default GHTeamBuilderTest instance
     */
    public GHTeamUpdateBuilderTest() {
    }

    // update name, description, privacy, notifications, permission, no change to parent team
    // update parent team
    // update removal of parent team

    @Test
    public void testUpdateTeamWithNewParentTeam() throws IOException {
        // Get the parent team
        GHOrganization org = gitHub.getOrganization(GITHUB_API_TEST_ORG);
        GHTeam teamToUpdate = org.getTeamBySlug(TEAM_TO_UPDATE_SLUG);
        GHTeam newParentTeam = org.getTeamBySlug(NEW_PARENT_TEAM_SLUG);

        GHTeam updatedTeam = getCommonBuilder(teamToUpdate)
                .parentTeamId(newParentTeam.getId())
                .update();

        assertUpdatedTeam(updatedTeam);
        // assertThat(updatedTeam.getParentTeam().getId(), equalTo(newParentTeam.getId()));
    }

    @Test
    public void testUpdateTeamWithNoChangeToParentTeam() throws IOException {
        // Get the parent team
        GHOrganization org = gitHub.getOrganization(GITHUB_API_TEST_ORG);
        GHTeam teamToUpdate = org.getTeamBySlug(TEAM_TO_UPDATE_SLUG);
        // GHTeam existingParentTeam = org.getTeamBySlug(CURRENT_PARENT_TEAM_SLUG);

        GHTeam updatedTeam = getCommonBuilder(teamToUpdate).update();

        assertUpdatedTeam(updatedTeam);
        // assertThat(teamToUpdate.getParentTeam().getId(), equalTo(existingParentTeam.getId()));
    }

    @Test
    public void testUpdateTeamWithRemovedParentTeam() throws IOException {
        GHOrganization org = gitHub.getOrganization(GITHUB_API_TEST_ORG);
        GHTeam teamToUpdate = org.getTeamBySlug(TEAM_TO_UPDATE_SLUG);

        GHTeam updatedTeam = getCommonBuilder(teamToUpdate)
                .parentTeamId(null)
                .update();

        assertUpdatedTeam(updatedTeam);
        // assertThat(teamToUpdate.getParentTeam(), equalTo(null));
    }

    private GHTeamUpdateBuilder getCommonBuilder(GHTeam teamToUpdate) {
        return teamToUpdate.updateTeam()
                .name(TEAM_TO_UPDATE_NEW_NAME)
                .description(TEAM_TO_UPDATE_NEW_DESCRIPTION)
                .privacy(TEAM_TO_UPDATE_NEW_PRIVACY)
                .notifications(TEAM_TO_UPDATE_NEW_NOTIFICATIONS)
                .permission(TEAM_TO_UPDATE_NEW_PERMISSIONS);
    }

    private void assertUpdatedTeam(GHTeam updatedTeam) {
        assertThat(updatedTeam.getName(), equalTo(TEAM_TO_UPDATE_NEW_NAME));
        assertThat(updatedTeam.getDescription(), equalTo(TEAM_TO_UPDATE_NEW_DESCRIPTION));
        assertThat(updatedTeam.getPrivacy(), equalTo(TEAM_TO_UPDATE_NEW_PRIVACY));
        // assertThat(updatedTeam.getNotificationSetting(), equalTo(TEAM_TO_UPDATE_NEW_NOTIFICATIONS));
        assertThat(updatedTeam.getPermission(), equalTo(TEAM_TO_UPDATE_NEW_PERMISSIONS));
    }
}
