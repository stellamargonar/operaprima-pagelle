package it.margonar.operaprima.pagelle.gui;

import it.margonar.operaprima.pagelle.model.Docente;
import it.margonar.operaprima.pagelle.model.PagellaStrumento;
import it.margonar.operaprima.pagelle.model.Strumento;
import it.margonar.operaprima.pagelle.model.Studente;
import it.margonar.operaprima.pagelle.repository.CorsiDAO;
import it.margonar.operaprima.pagelle.repository.DocenteDAO;
import it.margonar.operaprima.pagelle.repository.PagellaDAO;

import java.awt.BorderLayout;
import java.awt.Component;
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
public class FramePagStrumento extends JFrame {

	private JTextField anno;
	private JTextField assenze;
	private JTextArea testi;
	private JTextArea repertorio;
	private JTextArea note;
	private ArrayList<JComboBox<Integer>> competenzeBox;
	private List<String> competenze;

	private JComboBox<String> sedeBox;
	private JComboBox<Strumento> strumentoBox;
	private JComboBox<Docente> docenteBox;

	private JButton submit;

	private Studente studente;
	private PagellaStrumento ps;

	@Autowired
	DocenteDAO docenteDAO;
	@Autowired
	CorsiDAO corsiDAO;
	@Autowired
	PagellaDAO pagellaDAO;

	public FramePagStrumento() {}

	FramePagStrumento(Studente stu) {
		studente = stu;
	}

	public void draw() {
		ps = pagellaDAO.findByStudente(studente, PagellaStrumento.class);
		if (ps == null) {
			ps = new PagellaStrumento();
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

		pan.add(getLabel("Strumento"));
		pan.add(getStrumentoBox());

		pan.add(getLabel("Docente"));
		pan.add(getDocenteBox());

		pan.add(getLabel("Sede"));
		pan.add(getSedeBox());

		pan.add(getLabel("Anno"));
		pan.add(getAnnoText());

		pan.add(getLabel("Assenze"));
		pan.add(getAssenzeText());

		pan.add(getLabel("Testi"));
		pan.add(getTestiText());

		pan.add(getLabel("Repertorio"));
		pan.add(getRepertorioText());

		addCompetenze(pan);

		pan.add(getLabel("Note"));
		pan.add(getNoteText());

		// Lay out the panel.
		SpringUtilities.makeCompactGrid(pan, 15, 2, 6, 6, 6, 6);
		return pan;
	}

	private void addCompetenze(JPanel pan) {
		competenzeBox = new ArrayList<JComboBox<Integer>>();

		for (int i = 0; i < competenze.size(); i++) {
			pan.add(getLabel(competenze.get(i)));

			JPanel container = new JPanel();
			Integer[] values = { 0, 1, 2, 3, 4, 5 };
			JComboBox<Integer> combo = new JComboBox<Integer>(values);
			combo.setSelectedItem(ps.getCompetenza(i));
			System.out.println("competenza " + i + ", value "
					+ ps.getCompetenza(i));
			competenzeBox.add(combo);

			container.add(combo);
			pan.add(container);
		}
	}

	private Component getStrumentoBox() {
		Vector<Strumento> lista = new Vector<Strumento>(
				corsiDAO.findAll(Strumento.class));
		strumentoBox = new JComboBox<Strumento>(lista);
		if (ps.getStrumento() != null) {
			boolean cond = true;
			Enumeration<Strumento> e = lista.elements();
			while (e.hasMoreElements() && cond) {
				Strumento strumento = e.nextElement();
				if (strumento.equals(ps.getStrumento())) {
					strumentoBox.setSelectedItem(strumento);
					cond = false;
				}
			}
		}
		return strumentoBox;
	}

	private Component getDocenteBox() {
		Vector<Docente> lista = new Vector<Docente>(docenteDAO.findAll());
		docenteBox = new JComboBox<Docente>(lista);
		if (ps.getDocente() != null) {
			boolean cond = true;
			Enumeration<Docente> e = lista.elements();
			while (e.hasMoreElements() && cond) {
				Docente docente = e.nextElement();
				if (docente.equals(ps.getDocente())) {
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

		if (ps.getSede() != null) {
			for (int i = 0; i < sedi.length; i++) {
				if (sedi[i].equals(ps.getSede())) {
					sedeBox.setSelectedItem(sedi[i]);
				}
			}
		}

		return sedeBox;
	}

	private Component getAnnoText() {
		anno = new JTextField(30);
		if (ps != null) {
			anno.setText(Integer.toString(ps.getAnno()));
		}
		return anno;
	}

	private Component getAssenzeText() {
		assenze = new JTextField(30);
		if (ps != null) {
			assenze.setText(Integer.toString(ps.getAssenze()));
		}
		return assenze;
	}

	private Component getTestiText() {
		testi = new JTextArea(4, 50);
		testi.setLineWrap(true);
		if (ps != null) {
			testi.setText(ps.getTesti());
		}
		return new JScrollPane(testi, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}

	private Component getRepertorioText() {
		repertorio = new JTextArea(4, 50);
		repertorio.setLineWrap(true);
		if (ps != null) {
			repertorio.setText(ps.getRepertorio());
		}
		return new JScrollPane(repertorio,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}

	private Component getNoteText() {
		note = new JTextArea(4, 50);
		note.setLineWrap(true);
		if (ps != null) {
			note.setText(ps.getNote());
		}
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

				boolean cond = ps == null;
				if (cond) {
					// nuova pagella
					ps = new PagellaStrumento();
				}
				System.out.println("studente: " + studente);
				ps.setStudente(studente);
				ps.setDocente((Docente) docenteBox.getSelectedItem());
				ps.setStrumento((Strumento) strumentoBox.getSelectedItem());
				ps.setSede((String) sedeBox.getSelectedItem());
				ps.setAnno(Integer.parseInt(anno.getText()));
				ps.setTesti(testi.getText());
				ps.setRepertorio(repertorio.getText());
				ps.setNote(note.getText());
				ps.setAssenze(Integer.parseInt(assenze.getText()));

				List<Integer> values = new ArrayList<Integer>();
				for (int i = 0; i < competenze.size(); i++) {
					values.add((Integer) competenzeBox.get(i).getSelectedItem());
				}
				System.out.println(values);
				ps.setCompetenze(values);

				ps = pagellaDAO.save(ps);
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
				if (ps.getStudente() != null) {
					pagellaDAO.delete(ps);
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
		setTitle("Pagella Strumento: " + studente.getCognome() + " "
				+ studente.getNome());
	}

	private void closeWindow() {
		dispose();
	}

	@Value("#{\"${strumento.c}\".split(',')}")
	public void setCompetenze(List<String> competenze) {
		this.competenze = competenze;
	}

	public void setStudente(Studente studente) {
		this.studente = studente;
	}

}
