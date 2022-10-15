package florida.AD.AEO1;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;

public class AE01Vista {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AE01Vista window = new AE01Vista();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AE01Vista() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 559, 511);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 34, 430, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(10, 77, 267, 253);
		frame.getContentPane().add(textPane);
		
		//Este botón lo que hace es buscar en el directorio los archivos de tipo texto e imprimir la información de este
		JButton btnNewButton = new JButton("Buscar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    JFileChooser fileChooser = new JFileChooser();
			    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			    
			    fileChooser.setCurrentDirectory(new File (textField.getText()));
			    FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("Ficheros de texto","txt"); 
			    fileChooser.setFileFilter(imgFilter);
			    
			    int result = fileChooser.showOpenDialog(null);

			    if (result == JFileChooser.APPROVE_OPTION) {
			    	
			    	try {
			    		File fileName = fileChooser.getSelectedFile();
				        BasicFileAttributes attributes = Files.readAttributes(fileName.toPath(), BasicFileAttributes.class);
				        String fileInfo = "";
				        String filePath = fileName.toPath().toString();
				        if(fileName.isFile()) 
						fileInfo+="Tipo: Fichero\n";
						else 
							fileInfo+="Tipo: Directorio\n";
				        fileInfo+="Nombre:" + fileName.getName() + '\n';
				        fileInfo+="Extension:" + filePath.substring(filePath.lastIndexOf(".")) + '\n';
				        fileInfo+="Fecha de creacion: " + attributes.creationTime() + '\n';
				        fileInfo+="Permisos:\n ";
				        fileInfo+= "\tLectura: " + fileName.canRead() + '\n';
				        fileInfo+= "\tEscritura: " + fileName.canWrite() + '\n';
				        fileInfo+= "\tEjecucion: " + fileName.canExecute() + '\n';
						if(fileName.isFile())
							fileInfo+="Tamanyo " + fileName.length() + '\n';
						else {
//							System.out.println("Elementos " + fichero.list().length);
							fileInfo+="Espacio libre: " + fileName.getFreeSpace() + '\n';
//							System.out.println("Espacio ocupado: " + fichero.getTotalSpace() - fichero.getFreeSpace());
							fileInfo+="Espacio tolat: " + fileName.getTotalSpace() + '\n';
						}
						textField.setText(filePath);
						textPane.setText(fileInfo);
			    	}catch(Exception e1) {
			    		e1.printStackTrace();
			    	}
			        
			    }
			}
		});
		btnNewButton.setBounds(444, 33, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		
		//Este boton lo que hace es crear un nuevo fichero en un directorio
		JButton btnNewButton_1 = new JButton("Crear");
		btnNewButton_1.setBounds(344, 76, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearFichero();
			}
		});
		
		
		//Este boton lo que hace es renombrar un fichero ya existente en un directorio
		JButton btnNewButton_2 = new JButton("Renombrar");
		btnNewButton_2.setBounds(344, 108, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
			    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			    
			    fileChooser.setCurrentDirectory(new File (textField.getText()));
			    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    fileChooser.setAcceptAllFileFilterUsed(false);
			    
			    int result = fileChooser.showOpenDialog(null);

			    if (result == JFileChooser.APPROVE_OPTION) {
			    	File actualDirectory = fileChooser.getSelectedFile();
			    	renombrar(actualDirectory.getPath());
			    }

			}
		});
		
		//Este boton lo que hace es eliminar un fichero existente en un directorio
		JButton btnNewButton_3 = new JButton("Eliminar");
		btnNewButton_3.setBounds(344, 176, 89, 23);
		frame.getContentPane().add(btnNewButton_3);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
			    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			    
			    fileChooser.setCurrentDirectory(new File (textField.getText()));
			    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    fileChooser.setAcceptAllFileFilterUsed(false);
			    
			    int result = fileChooser.showOpenDialog(null);

			    if (result == JFileChooser.APPROVE_OPTION) {
			    	File actualDirectory = fileChooser.getSelectedFile();
			    	eliminar(actualDirectory.getPath());
			    }
			}
		});
		//Este boton lo que hace es copiar un fichero existente y le añade _copia al nuevo
		JButton btnNewButton_4 = new JButton("Copiar");
		btnNewButton_4.setBounds(344, 142, 89, 23);
		frame.getContentPane().add(btnNewButton_4);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
			    
			    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			    
			    fileChooser.setCurrentDirectory(new File (textField.getText()));
			    FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("Ficheros de texto","txt"); 
			    fileChooser.setFileFilter(imgFilter);
			    
			    int result = fileChooser.showOpenDialog(null);

			    if (result == JFileChooser.APPROVE_OPTION) {
			    	try {
						Files.copy(new File(fileChooser.getSelectedFile().toString()).toPath(), new File(fileChooser.getSelectedFile().toString()+"_copia").toPath(),StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    }
				
			}
		});
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBounds(10, 77, 523, 253);
		frame.getContentPane().add(editorPane);
		
		JButton btnNewButton_6 = new JButton("Buscar");
		btnNewButton_6.setBounds(444, 33, 89, 23);
		frame.getContentPane().add(btnNewButton_6);
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				    
					if (textField.getText() != "" && textField.getText()!= null) {
						System.out.println("gnjosdbguos");
						highlight(editorPane, textField.getText());
					}
				}
			});
		
		
		
		JButton btnNewButton_7 = new JButton("Guardar");
		btnNewButton_7.setBounds(31, 351, 89, 23);
		frame.getContentPane().add(btnNewButton_7);
		
		
		
		
		
		
		JButton btnNewButton_5 = new JButton("Mostrar contenido");
		btnNewButton_5.setBounds(303, 210, 179, 23);
		frame.getContentPane().add(btnNewButton_5);
		
		//Este boton lo que hace es cancelar una accion de las anteriores
		JButton btnNewButton_8 = new JButton("Cancelar");
		btnNewButton_8.setBounds(31, 400, 89, 23);
		frame.getContentPane().add(btnNewButton_8);
		btnNewButton_8.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			    
				textPane.setVisible(true);
		    	btnNewButton.setVisible(true);
		    	btnNewButton_1.setVisible(true);
		    	btnNewButton_2.setVisible(true);
		    	btnNewButton_3.setVisible(true);
		    	btnNewButton_4.setVisible(true);
		    	btnNewButton_5.setVisible(true);
		    	btnNewButton_6.setVisible(false);
		    	btnNewButton_7.setVisible(false);
		    	btnNewButton_8.setVisible(false);
		    	editorPane.setVisible(false);
			}
		});
		
		btnNewButton_6.setVisible(false);
		btnNewButton_7.setVisible(false);
		btnNewButton_8.setVisible(false);
		editorPane.setVisible(false);
		
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
			    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			    
			    fileChooser.setCurrentDirectory(new File (textField.getText()));
			    FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("Ficheros de texto","txt"); 
			    fileChooser.setFileFilter(imgFilter);
			    
			    int result = fileChooser.showOpenDialog(null);
			    
			    if (result == JFileChooser.APPROVE_OPTION) {
			    	textPane.setVisible(false);
	    	    	btnNewButton.setVisible(false);
	    	    	btnNewButton_1.setVisible(false);
	    	    	btnNewButton_2.setVisible(false);
	    	    	btnNewButton_3.setVisible(false);
	    	    	btnNewButton_4.setVisible(false);
	    	    	btnNewButton_5.setVisible(false);
	    	    	btnNewButton_6.setVisible(true);
	    	    	btnNewButton_7.setVisible(true);
	    	    	btnNewButton_8.setVisible(true);
	    	    	editorPane.setVisible(true);
			    	try (BufferedReader br = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
			    	    String line;
			    	    StringBuffer buffer = new StringBuffer();
			    	    while ((line = br.readLine()) != null) {
			    	    	buffer.append(line);
			    	    	buffer.append("\n");
			    	    }
			    	    editorPane.setText(buffer.toString());
			    	} catch (Exception e1){
			    	e1.printStackTrace();
			    	}
			    }
			}
		});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	//Funcion para crear fichero
	public static void crearFichero() {
		Scanner teclado = new Scanner(System.in);
		System.out.println("Nombre del fichero para crear: ");
		String nombreFichero = teclado.nextLine();
		File nuevoFichero = new File(nombreFichero);
		if (!nuevoFichero.exists()) {
			try {
				nuevoFichero.createNewFile();
				System.out.println("Fichero creado con exito");
			} catch (IOException e) {
				System.err.println("Erro al crearlo");
				e.printStackTrace();
			}
		}else
			System.out.println("Ya existe");
	}
	
	//Funcion para renombrar el fichero
	public void renombrar(String strDirectorio) {
		mostrarContenido(strDirectorio);
		File directorio = new File(strDirectorio);
		String[] contenido = directorio.list();
		Scanner teclado = new Scanner(System.in);
		System.out.println("Numero del fichero para renombrar: ");
		String numero = teclado.nextLine();
		File itemRenombrar = new File(contenido[Integer.parseInt(numero)-1]);
		System.out.println("Introducir nuevo nombre");
		String nuevoNombre = teclado.nextLine();
		File nuevoFichero = new File(nuevoNombre);
		boolean exito = itemRenombrar.renameTo(nuevoFichero);
		if (exito) {
			 JOptionPane.showInternalMessageDialog(null,
                    "Fichero renombrado correctamente", "Atencion", JOptionPane.INFORMATION_MESSAGE);
			}
		else {
			JOptionPane.showInternalMessageDialog(null,
                    "Error al renombrar fichero", "Atencion", JOptionPane.INFORMATION_MESSAGE);
		}
		mostrarContenido(strDirectorio);
		
	}
	
	//Funcion para mostrar el contenido de un directorio
	public static void mostrarContenido(String strDirectorio) {
		File directorio = new File(strDirectorio);
		String[] contenido = directorio.list();
		System.out.println("\nContenido del directorio: \n");
		int contador = 1;
		for(String elemento : contenido) {
			System.out.println(contador++ + ". " + elemento);
		}
	}
	//Funcion para eliminar un fichero
	public static void eliminar(String strDirectorio) {
		mostrarContenido(strDirectorio);
		File directorio = new File(strDirectorio);
		String[] contenido = directorio.list();
		Scanner teclado = new Scanner(System.in);
		System.out.println("Numero del fichero para eliminar: ");
		int numero = teclado.nextInt();
		File itemEliminar = new File(contenido[numero-1]);
		boolean exito = itemEliminar.delete();
		if (exito) {
			 JOptionPane.showInternalMessageDialog(null,
                   "Fichero eliminado correctamente", "Atencion", JOptionPane.INFORMATION_MESSAGE);
			}
		else {
			JOptionPane.showInternalMessageDialog(null,
                   "Error al eliminar fichero", "Atencion", JOptionPane.INFORMATION_MESSAGE);
		}
		mostrarContenido(strDirectorio);
	}
	
	//Funcion para enmarcar las palabras que decidas
    public void highlight(JTextComponent textComp, String pattern) {
        
        removeHighlights(textComp);

        try {
            Highlighter hilite = textComp.getHighlighter();
            Document doc = textComp.getDocument();
            String text = doc.getText(0, doc.getLength());

            int pos = 0;
            
            while ((pos = text.indexOf(pattern, pos)) >= 0) {
                
                hilite.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
                pos += pattern.length();
            }

        } catch (BadLocationException e) {
        }
    }

    
    public void removeHighlights(JTextComponent textComp) {
        Highlighter hilite = textComp.getHighlighter();
        Highlighter.Highlight[] hilites = hilite.getHighlights();

        for (int i = 0; i < hilites.length; i++) {
            if (hilites[i].getPainter() instanceof MyHighlightPainter) {
                hilite.removeHighlight(hilites[i]);
            }
        }
    }
    
    Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.red);

    
    class MyHighlightPainter
            extends DefaultHighlighter.DefaultHighlightPainter {

        public MyHighlightPainter(Color color) {
            super(color);
        }
    }
}

