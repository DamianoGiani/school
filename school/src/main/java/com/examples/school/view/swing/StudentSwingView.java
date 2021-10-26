package com.examples.school.view.swing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.examples.school.controller.SchoolController;
import com.examples.school.model.Student;
import com.examples.school.view.StudentView;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
public class StudentSwingView extends JFrame implements StudentView{

	private JPanel contentPane;
	private JTextField txtIdtextbox;
	private JTextField textField;
	private JButton btnNewButton;
	private JList list;
	private JButton btnDeleteSelected;
	private JLabel lblErrormessagelabel;
	private final Action action = new SwingAction();
	private JLabel lblName;
	private JList<Student> listStudents;
	private DefaultListModel<Student> listStudentsModel;
	private SchoolController schoolController;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentSwingView frame = new StudentSwingView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	DefaultListModel <Student> getListStudentsModel(){
		return listStudentsModel;
	}
	
	public void setSchoolController(SchoolController schoolController) {
		this.schoolController=schoolController;
	}
	/**
	 * Create the frame.
	 */
	public StudentSwingView() {
		listStudentsModel=new DefaultListModel<>();
		listStudents=new JList<>(listStudentsModel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		txtIdtextbox = new JTextField();
		
		txtIdtextbox.addKeyListener(boh);
		txtIdtextbox.setName("idTextBox");
		GridBagConstraints gbc_txtIdtextbox = new GridBagConstraints();
		gbc_txtIdtextbox.gridwidth = 5;
		gbc_txtIdtextbox.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIdtextbox.insets = new Insets(0, 0, 5, 0);
		gbc_txtIdtextbox.gridx = 0;
		gbc_txtIdtextbox.gridy = 0;
		contentPane.add(txtIdtextbox, gbc_txtIdtextbox);
		txtIdtextbox.setColumns(10);
		
		JLabel lblId = new JLabel("id");
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.gridwidth = 2;
		gbc_lblId.gridx = 1;
		gbc_lblId.gridy = 1;
		contentPane.add(lblId, gbc_lblId);
		
		textField = new JTextField();
		textField.setName("nameTextBox");
		textField.addKeyListener(boh);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 2;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("Add");
		btnNewButton.setEnabled(false);
		btnNewButton.addActionListener(
				e->new Thread(()->
					schoolController.newStudent(new Student(txtIdtextbox.getText(),textField.getText()))
				
						).start()
				);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		lblName = new JLabel("name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 3;
		contentPane.add(lblName, gbc_lblName);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 5;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		lblErrormessagelabel = new JLabel(" ");
		lblErrormessagelabel.setName("errorMessageLabel");
		GridBagConstraints gbc_lblErrormessagelabel = new GridBagConstraints();
		gbc_lblErrormessagelabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrormessagelabel.gridx = 3;
		gbc_lblErrormessagelabel.gridy = 6;
		contentPane.add(lblErrormessagelabel, gbc_lblErrormessagelabel);
		
		//list = new JList();
		listStudentsModel = new DefaultListModel<>();
		listStudents = new JList<>(listStudentsModel);
		listStudents.addListSelectionListener(
				e -> btnDeleteSelected.setEnabled(listStudents.getSelectedIndex() != -1));
		//listStudents.addListSelectionListener(new ListSelectionListener() {
			//@Override
			//public void valueChanged(ListSelectionEvent e) {
				//btnDeleteSelected.setEnabled(listStudents.getSelectedIndex()!=-1);
			//}
	//	});
		listStudents.setName("studentList");
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 0, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 8;
		contentPane.add(listStudents, gbc_list);
		
		btnDeleteSelected = new JButton("Delete Selected");
		btnDeleteSelected.setEnabled(false);
		btnDeleteSelected.addActionListener(
				e->schoolController.deleteStudent(listStudents.getSelectedValue()));
		GridBagConstraints gbc_btnDeleteSelected = new GridBagConstraints();
		gbc_btnDeleteSelected.insets = new Insets(0, 0, 0, 5);
		gbc_btnDeleteSelected.gridx = 3;
		gbc_btnDeleteSelected.gridy = 8;
		contentPane.add(btnDeleteSelected, gbc_btnDeleteSelected);
	}

	@Override
	public void showAllStudents(List<Student> students) {
		students.stream().forEach(listStudentsModel::addElement);
		
	}

	@Override
	public void showError(String message, Student student) {
		SwingUtilities.invokeLater(()->{
		lblErrormessagelabel.setText(message+": "+ student);
		
	});}

	@Override
	public void studentAdded(Student student) {
		SwingUtilities.invokeLater(()->{
		listStudentsModel.addElement(student);
		resetErrorLabel();
		
	});
		}

	@Override
	public void studentRemoved(Student student) {
		listStudentsModel.removeElement(student);
		resetErrorLabel();
		
	}
	private void resetErrorLabel() {
		lblErrormessagelabel.setText(" ");
	}

	KeyAdapter boh=new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			btnNewButton.setEnabled(
					!txtIdtextbox.getText().trim().isEmpty() &&
					!textField.getText().trim().isEmpty());
		}
	};

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
