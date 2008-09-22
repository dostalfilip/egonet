/***
 * Copyright (c) 2008, Endless Loop Software, Inc.
 * 
 * This file is part of EgoNet.
 * 
 * EgoNet is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * EgoNet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.endlessloopsoftware.ego.author;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.endlessloopsoftware.ego.Question;
import org.egonet.util.listbuilder.ListBuilder;
import org.egonet.util.listbuilder.Selection;

public class CategoryInputPane extends JDialog {
	private final JList parentList;

	private final GridBagLayout gridBagLayout1 = new GridBagLayout();

	// create list builder with preset values turned on.
	private final ListBuilder listBuilder = new ListBuilder();

	private final JButton jOKButton = new JButton("OK");

	private final JButton jCancelButton = new JButton("Cancel");

	private JScrollPane scrollPane;

	/**
	 * Constructor for CategoryInputPane
	 * 
	 * @param list
	 *            question list from parent frame used to determine which
	 *            question we are operating on
	 */
	public CategoryInputPane(JList list) {
		parentList = list;
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes layout and fields for the dialog
	 * 
	 * @throws Exception
	 *             No idea, sorry
	 */
	private void jbInit() throws Exception {
		JPanel panel = new JPanel();
		
		panel.setLayout(gridBagLayout1);
		this.setModal(true);
		this.setTitle("Category Options");

		panel.add(
				listBuilder,
				new GridBagConstraints(0, 0, 4, 1, 1.0, 0.9,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
		panel.add(
				jCancelButton,
				new GridBagConstraints(2, 1, 2, 1, 0.2, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
						new Insets(10, 0, 10, 10), 0, 0));
		panel.add(
				jOKButton,
				new GridBagConstraints(0, 1, 2, 1, 0.2, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
						new Insets(10, 20, 10, 0), 26, 0));

		jOKButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OKButton_actionPerformed(e);
			}
		});

		jCancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelButton_actionPerformed(e);
			}
		});

		scrollPane = new JScrollPane(panel);
		this.getContentPane().add(scrollPane);
	}

	void OKButton_actionPerformed(ActionEvent e) {
		boolean changed = false;
		boolean compatible = true;
		// boolean abort = false;

		Question q = (Question) parentList.getSelectedValue();
		if (q != null) {
			/* count choices */

			Selection[] newSelections = listBuilder.getListSelections();

			if (newSelections.length != q.selections.length) {
				if (EgoNet.study.confirmIncompatibleChange(EgoNet.frame)) {
					compatible = false;
					changed = true;

					// If the number changed we know the list has changed, so
					// just copy over
					// the reference and let the loop trim the strings
					q.selections = newSelections;
				} else {
					// Don't make this change
					EgoNet.frame.fillCurrentPanel();
					this.hide();
					return;
				}
			}

			// Trim the strings, check for changes
			for (int i = 0; i < q.selections.length; i++) {
				if (!q.selections[i]
						.equals(newSelections[i].getString().trim())) {
					q.selections[i].setString(newSelections[i].getString()
							.trim());
					changed = true;
				}
				// q.selections[i].value= newSelections[i].value;
			}

			EgoNet.study.setModified(changed);
			EgoNet.study.setCompatible(compatible);

			EgoNet.frame.fillCurrentPanel();
			this.setVisible(false);
		}

	}

	void cancelButton_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	void activate() {
		System.out.println("before activate lb.pref=" + disp());
		Question q = (Question) parentList.getSelectedValue();

		if (q != null) {
			listBuilder.setListSelections(q.selections);
		} else {
			System.err.println("Parent list had no selections");
		}

		listBuilder.setEditable(true);

		listBuilder.setElementName("Option: ");
		listBuilder.setTitle("Category Options");
		listBuilder
				.setDescription("Enter possible answers to this question below. Press Return to add the option "
						+ "to the options list. Press OK to set options or Cancel to undo changes.");
		listBuilder.setNameList(q.questionType == Question.ALTER_PROMPT);
		listBuilder.setLetUserPickValues(true);
		listBuilder.setPresetListsActive(q.answerType == Question.CATEGORICAL);
		
//		boolean preset = (q.answerType == Question.CATEGORICAL) ? true : false;
//		System.out.println("Is question categorical? " + preset);
//		
		listBuilder.setAdjacencyActive(q.questionType == Question.ALTER_PAIR_QUESTION);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// Center the window
		Dimension frameSize = this.getSize();

		this.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);
		
		jOKButton.setVisible(true);
		jCancelButton.setText("Cancel");

		System.out.println("initial lb.pref=" + disp());
		
		/* Pack since we've modified the GUI elements */
		validate();
		pack(); pack(); pack(); pack(); pack(); pack(); pack(); pack(); pack();
		
		System.out.println("after invalidate and pack lb.pref " + disp());
		
		this.setVisible(true);
		
		System.out.println("after it was visible lb.pref " + disp());
	}
	
	private String disp()
	{
		return "[pref="+listBuilder.getPreferredSize()+",min="+listBuilder.getMinimumSize()+",max="+listBuilder.getMaximumSize()+"]";
	}
}