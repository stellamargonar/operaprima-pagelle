package it.margonar.operaprima.pagelle.gui;

import it.margonar.operaprima.pagelle.model.Docente;
import it.margonar.operaprima.pagelle.model.Orchestra;
import it.margonar.operaprima.pagelle.model.PagellaOrchestra;
import it.margonar.operaprima.pagelle.model.Studente;
import it.margonar.operaprima.pagelle.repository.CorsiDAO;
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
public class FramePagOrchestra extends JFrame {

	private JTextField anno;
	private JTextField assenze;
	private JTextArea repertorio;
	private JTextArea note;
	private ArrayList<JComboBox<Integer>> competenzeBox;
	private List<String> competenze;

	private JComboBox<String> sedeBox;
	private JComboBox<Docente> docenteBox;
	private JComboBox<Orchestra> orchestraBox;

	private JButton submit;

	private Studente studente;
	private PagellaOrchestra po;

	@Autowired
	CorsiDAO corsiDAO;
	@Autowired
	DocenteDAO docenteDAO;
	@Autowired
	PagellaDAO pagellaDAO;

	public FramePagOrchestra() {}

	FramePagOrchestra(Studente stu) {
		studente = stu;
	}

	public void draw() {
		po = pagellaDAO.findByStudente(studente, PagellaOrchestra.class);
		if (po == null) {
			po = new PagellaOrchestra();
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

		pan.add(getLabel("Orchestra"));
		pan.add(getOrchestraBox());

		pan.add(getLabel("Docente"));
		pan.add(getDocenteBox());

		pan.add(getLabel("Sede"));
		pan.add(getSedeBox());

		pan.add(getLabel("Anno"));
		pan.add(getAnnoText());

		pan.add(getLabel("Assenze"));
		pan.add(getAssenzeText());

		pan.add(getLabel("Repertorio"));
		pan.add(getTestiText());

		pan.add(getLabel("Note"));
		pan.add(getNoteText());

		// Lay out the panel.
		SpringUtilities.makeCompactGrid(pan, 6, 2, 6, 6, 6, 6);
		return pan;
	}

	private void addCompetenze(JPanel pan) {
		competenzeBox = new ArrayList<JComboBox<Integer>>();

		for (int i = 0; i < 7; i++) {
			pan.add(getLabel(competenze.get(i)));

			JPanel container = new JPanel();
			Integer[] values = { 1, 2, 3, 4, 5 };
			JComboBox<Integer> combo = new JComboBox<Integer>(values);
			combo.setSelectedItem(1);
			System.out.println("competenza " + i + ", value " + 1);
			competenzeBox.add(combo);

			container.add(combo);
			pan.add(container);
		}
	}

	private Component getDocenteBox() {
		Vector<Docente> lista = new Vector<Docente>(docenteDAO.findAll());
		docenteBox = new JComboBox<Docente>(lista);
		if (po.getDocente() != null) {
			boolean cond = true;
			Enumeration<Docente> e = lista.elements();
			while (e.hasMoreElements() && cond) {
				Docente docente = e.nextElement();
				if (docente.equals(po.getDocente())) {
					docenteBox.setSelectedItem(docente);
					cond = false;
				}
			}
		}
		return docenteBox;
	}

	private Component getOrchestraBox() {
		Vector<Orchestra> lista = new Vector<Orchestra>(
				corsiDAO.findAll(Orchestra.class));
		orchestraBox = new JComboBox<Orchestra>(lista);
		if (po.getOrchestra() != null) {
			boolean cond = true;
			Enumeration<Orchestra> e = lista.elements();
			while (e.hasMoreElements() && cond) {
				Orchestra orchestra = e.nextElement();
				if (orchestra.equals(po.getOrchestra())) {
					orchestraBox.setSelectedItem(orchestra);
					cond = false;
				}
			}
		}
		return orchestraBox;
	}

	private Component getSedeBox() {
		String[] sedi = { "Ala", "Avio", "Brentonico", "Mori", "Pannone" };
		sedeBox = new JComboBox<String>(sedi);

		if (po.getSede() != null) {
			for (int i = 0; i < sedi.length; i++) {
				if (sedi[i].equals(po.getSede())) {
					sedeBox.setSelectedItem(sedi[i]);
				}
			}
		}

		return sedeBox;
	}

	private Component getAnnoText() {
		anno = new JTextField(30);
		if (po != null) {
			anno.setText(Integer.toString(po.getAnno()));
		}
		return anno;
	}

	private Component getAssenzeText() {
		assenze = new JTextField(30);
		if (po != null) {
			assenze.setText(Integer.toString(po.getAssenze()));
		}
		return assenze;
	}

	private Component getTestiText() {
		repertorio = new JTextArea(4, 50);
		repertorio.setLineWrap(true);
		if (po != null) {
			repertorio.setText(po.getRepertorio());
		}
		return new JScrollPane(repertorio,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}

	private Component getNoteText() {
		note = new JTextArea(4, 50);
		if (po != null) {
			note.setText(po.getNote());
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

				boolean cond = po == null;
				if (cond) {
					// nuova pagella
					po = new PagellaOrchestra();
				}

				po.setStudente(studente);
				po.setDocente((Docente) docenteBox.getSelectedItem());
				po.setOrchestra((Orchestra) orchestraBox.getSelectedItem());
				po.setSede((String) sedeBox.getSelectedItem());
				po.setAnno(Integer.parseInt(anno.getText()));
				po.setRepertorio(repertorio.getText());
				po.setNote(note.getText());
				po.setAssenze(Integer.parseInt(assenze.getText()));

				po = pagellaDAO.save(po);
				closeWindow();
				return;

			}

			private boolean validate() {
				try {
					int i = Integer.parseInt(anno.getText());
					i = Integer.parseInt(assenze.getText());
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
				if (po.getStudente() != null) {
					pagellaDAO.delete(po);
				}
				closeWindow();
			}
		});
		pan.add(delete);
		return pan;
	}

	private void setGui() {
		setVisible(true);
		setSize(500, 400);
		setTitle("Pagella Orchestra: " + studente.getCognome() + " "
				+ studente.getNome());
	}

	private void closeWindow() {
		dispose();
	}

	@Value("#{\"${orchestra.c}\".split(',')}")
	public void setCompetenze(List<String> competenze) {
		this.competenze = competenze;
	}

	public void setStudente(Studente studente) {
		this.studente = studente;
	}
}
