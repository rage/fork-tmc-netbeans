package fi.helsinki.cs.tmc.ui;


import fi.helsinki.cs.tmc.data.Course;
import fi.helsinki.cs.tmc.data.CourseCollection;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 * The settings panel.
 * 
 * This is missing the "OK" and "Cancel" buttons because it is placed in
 * a dialog that provides these.
 */
/*package*/ class PreferencesPanel extends JPanel implements PreferencesUI {
    
    /*package*/ PreferencesPanel() {
        initComponents();
    }
    
    @Override
    public String getUsername() {
        return usernameTextField.getText();
    }
    
    @Override
    public void setUsername(String username) {
        usernameTextField.setText(username);
    }
    
    @Override
    public String getServerBaseUrl() {
        return serverAddressTextField.getText();
    }
    
    @Override
    public void setServerBaseUrl(String baseUrl) {
        serverAddressTextField.setText(baseUrl);
    }
    
    @Override
    public String getProjectDir() {
        return projectFolderTextField.getText();
    }
    
    @Override
    public void setProjectDir(String projectDir) {
        projectFolderTextField.setText(projectDir);
    }
    
    @Override
    public void setAvailableCourses(CourseCollection courses) {
        coursesComboBox.removeAllItems();
        for (Course course : courses) {
            coursesComboBox.addItem(course);
        }
        coursesComboBox.setSelectedIndex(-1);
    }
    
    @Override
    public void setSelectedCourse(Course course) {
        coursesComboBox.setSelectedItem(course);
    }
    
    @Override
    public Course getSelectedCourse() {
        return (Course)coursesComboBox.getSelectedItem();
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usernameLabel = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        serverAddressLabel = new javax.swing.JLabel();
        serverAddressTextField = new javax.swing.JTextField();
        defaultProjectFolderLabel = new javax.swing.JLabel();
        projectFolderTextField = new javax.swing.JTextField();
        folderChooserBtn = new javax.swing.JButton();
        refreshCoursesBtn = new javax.swing.JButton();
        coursesLabel = new javax.swing.JLabel();
        coursesComboBox = new javax.swing.JComboBox();

        usernameLabel.setText(org.openide.util.NbBundle.getMessage(PreferencesPanel.class, "PreferencesPanel.usernameLabel.text")); // NOI18N

        usernameTextField.setText(org.openide.util.NbBundle.getMessage(PreferencesPanel.class, "PreferencesPanel.usernameTextField.text")); // NOI18N
        usernameTextField.setMinimumSize(new java.awt.Dimension(150, 27));
        usernameTextField.setPreferredSize(new java.awt.Dimension(150, 27));

        serverAddressLabel.setText(org.openide.util.NbBundle.getMessage(PreferencesPanel.class, "PreferencesPanel.serverAddressLabel.text")); // NOI18N

        serverAddressTextField.setText(org.openide.util.NbBundle.getMessage(PreferencesPanel.class, "PreferencesPanel.serverAddressTextField.text")); // NOI18N
        serverAddressTextField.setMinimumSize(new java.awt.Dimension(250, 27));
        serverAddressTextField.setPreferredSize(new java.awt.Dimension(250, 27));

        defaultProjectFolderLabel.setText(org.openide.util.NbBundle.getMessage(PreferencesPanel.class, "PreferencesPanel.defaultProjectFolderLabel.text")); // NOI18N

        projectFolderTextField.setEditable(false);
        projectFolderTextField.setText(org.openide.util.NbBundle.getMessage(PreferencesPanel.class, "PreferencesPanel.projectFolderTextField.text")); // NOI18N
        projectFolderTextField.setEnabled(false);
        projectFolderTextField.setPreferredSize(new java.awt.Dimension(250, 27));

        folderChooserBtn.setText(org.openide.util.NbBundle.getMessage(PreferencesPanel.class, "PreferencesPanel.folderChooserBtn.text")); // NOI18N
        folderChooserBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                folderChooserBtnActionPerformed(evt);
            }
        });

        refreshCoursesBtn.setText(org.openide.util.NbBundle.getMessage(PreferencesPanel.class, "PreferencesPanel.refreshCoursesBtn.text")); // NOI18N
        refreshCoursesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshCoursesBtnActionPerformed(evt);
            }
        });

        coursesLabel.setText(org.openide.util.NbBundle.getMessage(PreferencesPanel.class, "PreferencesPanel.coursesLabel.text")); // NOI18N

        coursesComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usernameLabel)
                    .addComponent(serverAddressLabel)
                    .addComponent(defaultProjectFolderLabel)
                    .addComponent(coursesLabel))
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(serverAddressTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(projectFolderTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                            .addComponent(coursesComboBox, 0, 361, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(refreshCoursesBtn)
                            .addComponent(folderChooserBtn))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serverAddressLabel)
                    .addComponent(serverAddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(defaultProjectFolderLabel)
                    .addComponent(projectFolderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(folderChooserBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refreshCoursesBtn)
                    .addComponent(coursesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(coursesLabel))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * The method is used to select the default save folder for downloaded exercises.
     * It is called when the user presses the "Browse" button on the preferences window.
     * @param evt 
     */
    private void folderChooserBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_folderChooserBtnActionPerformed

        JFileChooser folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int choice = folderChooser.showOpenDialog(this);
        if (choice == JFileChooser.CANCEL_OPTION) {
            return;
        }
        File projectDefaultFolder = folderChooser.getSelectedFile();

        projectFolderTextField.setText(projectDefaultFolder.getAbsolutePath());
    }//GEN-LAST:event_folderChooserBtnActionPerformed

    
    /**
     * Method reads course objects to coursesComboBox e.g in a situation where user changes hostAddress or 
     * writes it for the first time.
     * @param evt 
     */
    private void refreshCoursesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshCoursesBtnActionPerformed
        
    }//GEN-LAST:event_refreshCoursesBtnActionPerformed

    /**
     * This method is called whenever the user chooses something from the
     * combobox. It takes the chosen course and sets it as the selected course.
     * @param evt 
     */
    /**
     * The method sends the selected course from coursesComboBox to the settings-object for saving.
     * @param evt 
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox coursesComboBox;
    private javax.swing.JLabel coursesLabel;
    private javax.swing.JLabel defaultProjectFolderLabel;
    private javax.swing.JButton folderChooserBtn;
    private javax.swing.JTextField projectFolderTextField;
    private javax.swing.JButton refreshCoursesBtn;
    private javax.swing.JLabel serverAddressLabel;
    private javax.swing.JTextField serverAddressTextField;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables

    
}
