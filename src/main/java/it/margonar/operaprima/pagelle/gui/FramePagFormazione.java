package it.margonar.operaprima.pagelle.gui;

import it.margonar.operaprima.pagelle.model.Docente;
import it.margonar.operaprima.pagelle.model.PagellaFormazione;
import it.margonar.operaprima.pagelle.model.Studente;
import it.margonar.operaprima.pagelle.repository.DocenteDAO;
import it.margonar.operaprima.pagelle.repository.PagellaDAO;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@org.springframework.stereotype.Component
public class FramePagFormazione extends JFrame {

	private JTextField anno;
	private JTextField assenze;
	private JTextArea testi;
	private JTextArea note;
	private ArrayList<JComboBox<Integer>> competenzeBox;
	private List<String> competenze;

	private JComboBox<String> sedeBox;
	private JComboBox<Docente> docenteBox;

	private JButton submit;

	private Studente studente;
	private PagellaFormazione pf;

	@Autowired
	PagellaDAO pagellaDAO;
	@Autowired
	DocenteDAO docenteDAO;

	public FramePagFormazione() {}

	FramePagFormazione(Studente stu) {
		studente = stu;
	}

	public void draw() {
		pf = pagellaDAO.findByStudente(studente, PagellaFormazione.class);
		if (pf == null) {
			pf = new PagellaFormazione();
		}

		JPanel pan = new JPanel();
		pan.setLayout(new BorderLayout());

		pan.add(getInputPane(), BorderLayout.CENTER);

		pan.add(getButtonPane(), BorderLayout.SOUTH);

		setContentPane(pan);
		setGui();

	}

	private Component getLabel(String string) {
		JLabel label = new JLabel(string);
		return label;
	}

	private Component getInputPane() {
		JPanel pan = new JPanel(new SpringLayout());

		pan.add(getLabel("Docente"));
		pan.add(getDocenteBox());

		pan.add(getLabel("Sede"));
		pan.add(getSedeBox());

		pan.add(getLabel("Corso"));
		pan.add(getAnnoText());

		pan.add(getLabel("Assenze"));
		pan.add(getAssenzeText());

		pan.add(getLabel("Testi"));
		pan.add(getTestiText());

		addCompetenze(pan);

		pan.add(getLabel("Note"));
		pan.add(getNoteText());

		// Lay out the panel.
		SpringUtilities.makeCompactGrid(pan, 12, 2, 6, 6, 6, 6);
		return pan;
	}

	private void addCompetenze(JPanel pan) {
		competenzeBox = new ArrayList<JComboBox<Integer>>();

		for (int i = 0; i < 7; i++) {
			pan.add(getLabel(competenze.get(i)));

			JPanel container = new JPanel();
			Integer[] values = { 0, 1, 2, 3, 4, 5 };
			JComboBox<Integer> combo = new JComboBox<Integer>(values);
			combo.setSelectedItem(pf.getCompetenza(i));
			System.out.println("competenza " + i + ", value " + 1);
			competenzeBox.add(combo);

			container.add(combo);
			pan.add(container);
		}
	}

	private Component getDocenteBox() {
		Vector<Docente> lista = new Vector<Docente>(docenteDAO.findAll());
		docenteBox = new JComboBox<Docente>(lista);
		if (pf.getDocente() != null) {
			boolean cond = true;
			Enumeration<Docente> e = lista.elements();
			while (e.hasMoreElements() && cond) {
				Docente docente = e.nextElement();
				if (docente.equals(pf.getDocente())) {
					docenteBox.setSelectedItem(docente);
					cond = false;
				}
			}
		}
		return docenteBox;
	}

	private Component getSedeBox() {
		String[] sedi = { "Ala", "Avio", "Brentonico", "Mori", "Pannone" };
		sedeBox = new JComboBox<String>(sedi);

		if (pf.getSede() != null) {
			for (int i = 0; i < sedi.length; i++) {
				if (sedi[i].equals(pf.getSede())) {
					sedeBox.setSelectedItem(sedi[i]);
				}
			}
		}

		return sedeBox;
	}

	private Component getAnnoText() {
		anno = new JTextField(30);
		if (pf != null) {
			anno.setText(pf.getCorso());
		}
		return anno;
	}

	private Component getAssenzeText() {
		assenze = new JTextField(30);
		if (pf != null) {
			assenze.setText(Integer.toString(pf.getAssenze()));
		}
		return assenze;
	}

	private Component getTestiText() {
		testi = new JTextArea(4, 50);
		if (pf != null) {
			testi.setText(pf.getTesti());
		}
		testi.setLineWrap(true);
		return new JScrollPane(testi, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}

	private Component getNoteText() {
		note = new JTextArea(4, 50);
		if (pf != null) {
			note.setText(pf.getNote());
		}
		note.setFont(Font.getFont(Font.SANS_SERIF));
		note.setLineWrap(true);
		return new JScrollPane(note, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}

	private Component getButtonPane() {
		JPanel pan = new JPanel();

		submit = new JButton("Salva Pagella");
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (!validate()) {
					JOptionPane.showMessageDialog(null,
							"Anno = " + anno.getText()
									+ " non e' un numero intero");
					return;
				}

				boolean cond = pf == null;
				if (cond) {
					pf = new PagellaFormazione();
				}

				pf.setStudente(studente);
				pf.setDocente((Docente) docenteBox.getSelectedItem());
				pf.setSede((String) sedeBox.getSelectedItem());
				pf.setCorso(anno.getText());
				pf.setTesti(testi.getText());
				pf.setNote(note.getText());
				pf.setAssenze(Integer.parseInt(assenze.getText()));

				List<Integer> values = new ArrayList<Integer>();
				for (int i = 0; i < competenze.size(); i++) {
					values.add((Integer) competenzeBox.get(i).getSelectedItem());
				}
				pf.setCompetenze(values);

				pf = pagellaDAO.save(pf);
				closeWindow();
				return;

			}

			private boolean validate() {
				try {
					int i = Integer.parseInt(assenze.getText());
				} catch (NumberFormatException ex) {
					return false;
				}
				return true;

			}

		});
		pan.add(submit);

		JButton delete = new JButton("Elimina Pagella");
		delete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (pf.getStudente() != null) {
					pagellaDAO.delete(pf);
				}
				closeWindow();
			}
		});
		pan.add(delete);
		return pan;
	}

	private void setGui() {
		setVisible(true);
		setSize(400, 700);
		setTitle("Pagella Formazione: " + studente.getCognome() + " "
				+ studente.getNome());
	}

	private void closeWindow() {
		dispose();
	}

	@Value("#{\"${formazione.c}\".split(',')}")
	public void setCompetenze(List<String> competenze) {
		this.competenze = competenze;
	}

	public void setStudente(Studente studente) {
		this.studente = studente;
	}
}
