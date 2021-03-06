package fi.helsinki.cs.tmc.runners;

import fi.helsinki.cs.tmc.data.ResultCollector;
import fi.helsinki.cs.tmc.model.ProjectMediator;
import fi.helsinki.cs.tmc.model.TmcProjectInfo;
import fi.helsinki.cs.tmc.model.TmcCoreSingleton;
import fi.helsinki.cs.tmc.ui.ConvenientDialogDisplayer;
import fi.helsinki.cs.tmc.core.exceptions.TmcCoreException;
import fi.helsinki.cs.tmc.utilities.BgTask;
import fi.helsinki.cs.tmc.utilities.BgTaskListener;

import com.google.common.util.concurrent.ListenableFuture;
import fi.helsinki.cs.tmc.langs.abstraction.ValidationResult;
import fi.helsinki.cs.tmc.stylerunner.validation.CheckstyleResult;

import org.netbeans.api.project.Project;

import org.openide.util.Exceptions;
import java.util.concurrent.ExecutionException;

public final class CheckstyleRunHandler implements Runnable {

    private Project project;
    private final ConvenientDialogDisplayer dialogDisplayer =
            ConvenientDialogDisplayer.getDefault();
    private ValidationResult validationResult = new CheckstyleResult();

    public void performAction(final ResultCollector resultCollector, final Project project) {
        this.project = project;

        BgTask.start(
                "Running validations",
                this,
                new BgTaskListener<Object>() {

                    @Override
                    public void bgTaskFailed(final Throwable exception) {
                        dialogDisplayer.displayError("Failed to validate the code.");
                    }

                    @Override
                    public void bgTaskCancelled() {}

                    @Override
                    public void bgTaskReady(final Object nothing) {
                        resultCollector.setValidationResult((fi.helsinki.cs.tmc.langs.abstraction.ValidationResult) validationResult);
                    }
                });
    }

    @Override
    public void run() {
        try {
            final TmcProjectInfo projectInfo = ProjectMediator.getInstance().wrapProject(project);
            ProjectMediator.getInstance().saveAllFiles();
            ListenableFuture<ValidationResult> result;
            result =
                    TmcCoreSingleton.getInstance().runCheckstyle(projectInfo.getProjectDirAsPath());
            validationResult = result.get();
        } catch (InterruptedException ex) {
            Exceptions.printStackTrace(ex);
        } catch (ExecutionException ex) {
            Exceptions.printStackTrace(ex);
        } catch (TmcCoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
