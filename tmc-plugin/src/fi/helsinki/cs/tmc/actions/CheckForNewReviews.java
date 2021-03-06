package fi.helsinki.cs.tmc.actions;

import static java.util.logging.Level.INFO;

import fi.helsinki.cs.tmc.core.domain.Course;
import fi.helsinki.cs.tmc.core.domain.Review;
import fi.helsinki.cs.tmc.model.CourseDb;
import fi.helsinki.cs.tmc.model.ReviewDb;
import fi.helsinki.cs.tmc.model.TmcCoreSingleton;
import fi.helsinki.cs.tmc.ui.ConvenientDialogDisplayer;
import fi.helsinki.cs.tmc.utilities.BgTask;
import fi.helsinki.cs.tmc.utilities.BgTaskListener;
import fi.helsinki.cs.tmc.utilities.CancellableCallable;

import com.google.common.util.concurrent.ListenableFuture;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

@ActionID(category = "TMC", id = "fi.helsinki.cs.tmc.actions.CheckForNewReviews")
@ActionRegistration(displayName = "#CTL_CheckForNewReviews")
@ActionReferences({@ActionReference(path = "Menu/TM&C", position = -40)})
@NbBundle.Messages("CTL_CheckForNewReviews=Check for new code &reviews")
public class CheckForNewReviews implements ActionListener, Runnable {

    private static final Logger log = Logger.getLogger(CheckForNewReviews.class.getName());

    private static CheckForNewReviews instance;

    public static void startTimer() {
        if (instance == null) {
            instance = new CheckForNewReviews(true, false, false);
            int interval = 20 * 60 * 1000; // 20 minutes
            javax.swing.Timer timer = new javax.swing.Timer(interval, instance);
            timer.setRepeats(true);
            timer.start();
            SwingUtilities.invokeLater(instance);
        } else {
            log.warning("CheckForNewReviews.startTimer() called twice");
        }
    }

    private CourseDb courseDb;
    private ReviewDb reviewDb;
    private ConvenientDialogDisplayer dialogs;
    private boolean beQuiet;
    private boolean resetNotifications;
    private boolean notifyAboutNoNewReviews;

    CheckForNewReviews() {
        this(false, true, true);
    }

    CheckForNewReviews(
            boolean beQuiet, boolean resetNotifications, boolean notifyAboutNoNewReviews) {
        this.courseDb = CourseDb.getInstance();
        this.reviewDb = ReviewDb.getInstance();
        this.dialogs = ConvenientDialogDisplayer.getDefault();
        this.beQuiet = beQuiet;
        this.resetNotifications = resetNotifications;
        this.notifyAboutNoNewReviews = notifyAboutNoNewReviews;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        run();
    }

    @Override
    public void run() {
        if (resetNotifications) {
            reviewDb.forgetReviewsNotifiedAbout();
        }

        Course course = courseDb.getCurrentCourse();
        if (course == null) {
            if (!beQuiet) {
                dialogs.displayError("Please select a course in TMC->Settings");
            }
            return;
        }
        if (course.getReviewsUrl() == null) {
            return;
        }
        BgTaskListener bgTaskListener =
                new BgTaskListener<List<Review>>() {
                    @Override
                    public void bgTaskReady(List<Review> result) {
                        boolean newReviews = reviewDb.setReviews(result);
                        if (!newReviews && notifyAboutNoNewReviews) {
                            dialogs.displayMessage("You have no unread code reviews.");
                        }
                    }

                    @Override
                    public void bgTaskFailed(final Throwable ex) {
                        final String msg = "Failed to check for code reviews";
                        log.log(INFO, msg, ex);
                        if (!beQuiet) {
                            dialogs.displayError(msg, ex);
                        }
                    }

                    @Override
                    public void bgTaskCancelled() {}
                };
        BgTask.start(
                "Checking for code reviews",
                new CancellableCallable<List<Review>>() {
                    private ListenableFuture<List<Review>> lf;

                    @Override
                    public List<Review> call() throws Exception {
                        lf =
                                TmcCoreSingleton.getInstance()
                                        .getNewReviews(courseDb.getCurrentCourse());
                        return lf.get();
                    }

                    @Override
                    public boolean cancel() {
                        return lf.cancel(true);
                    }
                },
                bgTaskListener);
    }
}
