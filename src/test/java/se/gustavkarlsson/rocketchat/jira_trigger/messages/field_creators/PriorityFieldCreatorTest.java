package se.gustavkarlsson.rocketchat.jira_trigger.messages.field_creators;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Priority;
import org.junit.Before;
import org.junit.Test;
import se.gustavkarlsson.rocketchat.models.to_rocket_chat.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PriorityFieldCreatorTest {

	private Issue mockIssue;
	private Priority mockPrio;
	private PriorityFieldCreator extractor;

	@Before
	public void setUp() throws Exception {
		mockIssue = mock(Issue.class);
		mockPrio = mock(Priority.class);
		extractor = new PriorityFieldCreator();
	}

	@Test(expected = NullPointerException.class)
	public void createNullThrowsNPE() throws Exception {
		extractor.create(null);
	}

	@Test
	public void createReturnsCorrectField() throws Exception {
		String prio = "Blocker";
		when(mockIssue.getPriority()).thenReturn(mockPrio);
		when(mockPrio.getName()).thenReturn(prio);

		Field field = extractor.create(mockIssue);

		assertThat(field.getValue()).isEqualTo(prio);
	}

	@Test
	public void createWithNullPrioritySetsNonEmptyValue() throws Exception {
		when(mockIssue.getPriority()).thenReturn(null);

		Field field = extractor.create(mockIssue);

		assertThat(field.getValue()).isNotNull();
	}
}
