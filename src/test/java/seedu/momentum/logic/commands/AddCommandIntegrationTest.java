package seedu.momentum.logic.commands;

import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.Project;
import seedu.momentum.testutil.ProjectBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalProjectBook(), new UserPrefs());
    }

    @Test
    public void execute_newProject_success() {
        Project validProject = new ProjectBuilder().withName("TEST").build();

        Model expectedModel = new ModelManager(model.getProjectBook(), new UserPrefs());
        expectedModel.addProject(validProject);

        assertCommandSuccess(new AddCommand(validProject), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validProject), expectedModel);
    }

    @Test
    public void execute_duplicateProject_throwsCommandException() {
        Project projectInList = model.getProjectBook().getProjectList().get(0);
        assertCommandFailure(new AddCommand(projectInList), model, AddCommand.MESSAGE_DUPLICATE_PROJECT);
    }

}