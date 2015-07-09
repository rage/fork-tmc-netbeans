package fi.helsinki.cs.tmc.ui;

import fi.helsinki.cs.tmc.actions.DownloadExercisesAction;
import fi.helsinki.cs.tmc.actions.ServerErrorHelper;
import fi.helsinki.cs.tmc.actions.UnlockExercisesAction;
import fi.helsinki.cs.tmc.actions.UpdateExercisesAction;
import hy.tmc.core.domain.Exercise;
import hy.tmc.core.exceptions.TmcCoreException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import org.openide.util.Exceptions;
import org.openide.windows.WindowManager;

public class DownloadOrUpdateExercisesDialog extends JDialog {

    public static void display(final List<Exercise> unlockable, final List<Exercise> downloadable, final List<Exercise> updateable) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DownloadOrUpdateExercisesDialog dialog = new DownloadOrUpdateExercisesDialog(unlockable, downloadable, updateable);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
    }

    boolean haveUnlockables;

    private List<JCheckBox> unlockableCheckboxes;
    private HashMap<JCheckBox, Exercise> checkBoxToExercise;

    private boolean selectAllButtonIsDeselecting;

    private DownloadOrUpdateExercisesDialog(List<Exercise> unlockable, List<Exercise> downloadable, List<Exercise> updateable) {
        super(WindowManager.getDefault().getMainWindow(), true);
        initComponents();

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        haveUnlockables = !unlockable.isEmpty();
        if (!haveUnlockables) {
            remove(unlockCheckbox);
        }
        if (downloadable.isEmpty() && unlockable.isEmpty()) {
            remove(downloadableLabel);
            remove(downloadableScrollPane);
            setTitle("Update exercises");
            downloadButton.setText("Update");
        }
        if (updateable.isEmpty()) {
            remove(updateableLabel);
            remove(updateableScrollPane);
            setTitle("Download exercises");
            downloadButton.setText("Download");
        }

        checkBoxToExercise = new HashMap<JCheckBox, Exercise>();
        for (Exercise ex : downloadable) {
            String text = ex.getName();
            if (ex.isCompleted()) {
                text += " (completed)";
            }
            JCheckBox cb = new JCheckBox(text, true);
            checkBoxToExercise.put(cb, ex);
            ((CheckBoxList) downloadableList).addCheckbox(cb);
        }

        unlockableCheckboxes = new ArrayList<JCheckBox>();
        for (Exercise ex : unlockable) {
            String desc;
            if (ex.getDeadlineDescription() != null) {
                desc = "unlockable; deadline: " + ex.getDeadlineDescription();
            } else {
                desc = "unlockable";
            }
            JCheckBox cb = new JCheckBox(ex.getName() + " (" + desc + ")", true);
            unlockableCheckboxes.add(cb);
            checkBoxToExercise.put(cb, ex);
            ((CheckBoxList) downloadableList).addCheckbox(cb);
        }

        for (Exercise ex : updateable) {
            String text = ex.getName();
            if (ex.isCompleted()) {
                text += " (completed)";
            }
            JCheckBox cb = new JCheckBox(text, true);
            checkBoxToExercise.put(cb, ex);
            ((CheckBoxList) updateableList).addCheckbox(cb);
        }

        ((CheckBoxList) downloadableList).addItemListener(updateSelectAllButtonStateListener);
        ((CheckBoxList) updateableList).addItemListener(updateSelectAllButtonStateListener);
        updateSelectAllButtonState();

        pack();
    }

    private ItemListener updateSelectAllButtonStateListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            updateSelectAllButtonState();
        }
    };

    private boolean isUnlockable(int index) {
        return unlockableCheckboxes.contains(((CheckBoxList) downloadableList).getElement(index));
    }

    private void updateSelectAllButtonState() {
        if (((CheckBoxList) downloadableList).isAnySelected() || ((CheckBoxList) updateableList).isAnySelected()) {
            selectAllButtonIsDeselecting = true;
            selectAllButton.setText("Unselect all");
        } else {
            selectAllButtonIsDeselecting = false;
            selectAllButton.setText("Select all");
        }
    }

    private void doDownloadAndUpdate(List<Exercise> toDownload, List<Exercise> toUpdate) {
        try {
            new DownloadExercisesAction(toDownload).run();
            new UpdateExercisesAction(toUpdate).run();
        } catch (TmcCoreException ex) {
            Exceptions.printStackTrace(ex);
            ConvenientDialogDisplayer.getDefault()
                .displayError("Failed to download exercises.\n" + ServerErrorHelper.getServerExceptionMsg(ex));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        downloadableScrollPane = new javax.swing.JScrollPane();
        downloadableList = new CheckBoxList();
        downloadableLabel = new javax.swing.JLabel();
        updateableLabel = new javax.swing.JLabel();
        updateableScrollPane = new javax.swing.JScrollPane();
        updateableList = new CheckBoxList();
        downloadButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        selectAllButton = new javax.swing.JButton();
        unlockCheckbox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(DownloadOrUpdateExercisesDialog.class, "DownloadOrUpdateExercisesDialog.title")); // NOI18N

        downloadableScrollPane.setViewportView(downloadableList);

        downloadableLabel.setText(org.openide.util.NbBundle.getMessage(DownloadOrUpdateExercisesDialog.class, "DownloadOrUpdateExercisesDialog.downloadableLabel.text")); // NOI18N

        updateableLabel.setText(org.openide.util.NbBundle.getMessage(DownloadOrUpdateExercisesDialog.class, "DownloadOrUpdateExercisesDialog.updateableLabel.text")); // NOI18N

        updateableScrollPane.setViewportView(updateableList);

        downloadButton.setText(org.openide.util.NbBundle.getMessage(DownloadOrUpdateExercisesDialog.class, "DownloadOrUpdateExercisesDialog.downloadButton.text")); // NOI18N
        downloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadButtonActionPerformed(evt);
            }
        });

        closeButton.setText(org.openide.util.NbBundle.getMessage(DownloadOrUpdateExercisesDialog.class, "DownloadOrUpdateExercisesDialog.closeButton.text")); // NOI18N
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        selectAllButton.setText(org.openide.util.NbBundle.getMessage(DownloadOrUpdateExercisesDialog.class, "DownloadOrUpdateExercisesDialog.selectAllButton.text")); // NOI18N
        selectAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllButtonActionPerformed(evt);
            }
        });

        unlockCheckbox.setSelected(true);
        unlockCheckbox.setText(org.openide.util.NbBundle.getMessage(DownloadOrUpdateExercisesDialog.class, "DownloadOrUpdateExercisesDialog.unlockCheckbox.text")); // NOI18N
        unlockCheckbox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                unlockCheckboxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(updateableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                    .addComponent(downloadableScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                    .addComponent(selectAllButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(downloadButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(unlockCheckbox)
                            .addComponent(updateableLabel)
                            .addComponent(downloadableLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(downloadableLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(unlockCheckbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(downloadableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .addGap(15, 15, 15)
                .addComponent(updateableLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updateableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectAllButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton)
                    .addComponent(downloadButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void downloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadButtonActionPerformed
        final List<Exercise> toDownload = new ArrayList<Exercise>();
        for (JCheckBox cb : (CheckBoxList) downloadableList) {
            if (cb.isSelected()) {
                toDownload.add(checkBoxToExercise.get(cb));
            }
        }

        final List<Exercise> toUpdate = new ArrayList<Exercise>();
        for (JCheckBox cb : (CheckBoxList) updateableList) {
            if (cb.isSelected()) {
                toUpdate.add(checkBoxToExercise.get(cb));
            }
        }

        if (haveUnlockables && unlockCheckbox.isSelected()) {
            UnlockExercisesAction unlockAction = new UnlockExercisesAction();
            unlockAction.setSuccessListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    doDownloadAndUpdate(toDownload, toUpdate);
                }
            });
            unlockAction.run();
        } else {
            doDownloadAndUpdate(toDownload, toUpdate);
        }

        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_downloadButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void selectAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllButtonActionPerformed
        boolean select = !selectAllButtonIsDeselecting;
        for (int i = 0; i < ((CheckBoxList) downloadableList).getElementCount(); ++i) {
            if (select && isUnlockable(i) && !unlockCheckbox.isSelected()) {
                // Don't check grayed out unlockables
                continue;
            }
            ((CheckBoxList) downloadableList).setSelected(i, select);
        }
        for (int i = 0; i < ((CheckBoxList) updateableList).getElementCount(); ++i) {
            ((CheckBoxList) updateableList).setSelected(i, select);
        }
        updateSelectAllButtonState();
    }//GEN-LAST:event_selectAllButtonActionPerformed

    private void unlockCheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_unlockCheckboxItemStateChanged
        boolean active = (evt.getStateChange() == ItemEvent.SELECTED);
        for (JCheckBox cb : unlockableCheckboxes) {
            cb.setEnabled(active);
            cb.setSelected(active);
        }
        updateSelectAllButtonState();
    }//GEN-LAST:event_unlockCheckboxItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JButton downloadButton;
    private javax.swing.JLabel downloadableLabel;
    private javax.swing.JList downloadableList;
    private javax.swing.JScrollPane downloadableScrollPane;
    private javax.swing.JButton selectAllButton;
    private javax.swing.JCheckBox unlockCheckbox;
    private javax.swing.JLabel updateableLabel;
    private javax.swing.JList updateableList;
    private javax.swing.JScrollPane updateableScrollPane;
    // End of variables declaration//GEN-END:variables
}
