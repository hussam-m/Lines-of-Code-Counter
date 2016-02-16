package locc.main;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main extends javax.swing.JFrame {

	public static String lastDirectoryAccessed = ".";
	
	public List<ExtensionWrapper> presets = new LinkedList<ExtensionWrapper>(); //name + extensions wrapper for preset list
	public String[] presetsStrings; //string in preset JList
	
	public List<ExtensionWrapper> customs = new LinkedList<ExtensionWrapper>(); //name + extensions wrapper for user defined list
	public String[] customsStrings; //string in user defined JList
	
    public Main() {
    	
    	try {
    		Image icon = ImageIO.read(getClass().getResourceAsStream("/images/logo.png"));
    		setIconImage(icon);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	initLists();
        initComponents();
    	new FileDrop( this, new FileDrop.Listener() {
    		public void filesDropped( java.io.File[] files ) {
    			startCounter(files);
			}   // end filesDropped
    	}); // end FileDrop.Listener
    	
    	addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("main gui window closing");
				saveCustoms();
			}
		});
    }
    
    private void saveCustoms() {
    	PrintWriter writer = null;
    	try {
    		File newFile = new File("data"+File.separator+"customs.txt");
    		if(!newFile.exists()) {
    			newFile.getParentFile().mkdir();
	    		newFile.createNewFile();
    		}
    		writer = new PrintWriter(new FileWriter(newFile));
    		List<ExtensionWrapper> exts = customList.getAllExtensionWrappers();
    		for(int i = 0; i < exts.size(); i++) {
    			writer.println(exts.get(i).getName()+"-"+exts.get(i).getCondensedExtensions());
    		}
    	} catch(Exception e) {
    		e.printStackTrace();
    	} finally {
    		if(writer != null)
    			try {
    				writer.close();
    			} catch(Exception e) {
    				e.printStackTrace();
    			}
    	}
    }
    
    private void startCounter(File[] files) {
		ExtensionWrapper wrapper = null;
		if(presetList.getSelectedIndex() >= 0)
			wrapper = presetList.getExtensionWrapperAt(presetList.getSelectedIndex());
		else if(customList.getSelectedIndex() >= 0)
			wrapper = customList.getExtensionWrapperAt(customList.getSelectedIndex());
		Counter.counter(this, files, jCheckBoxMenuItem1.isSelected(),jCheckBoxMenuItem2.isSelected(), wrapper, jTextArea1);
    }
    
    private void initLists() {
    	//presets
    	ExtensionWrapper java = new ExtensionWrapper("Java", new String[]{".java"});
    	ExtensionWrapper c = new ExtensionWrapper("C/C++", new String[]{".c", ".h", ".cpp", ".hpp"});
    	ExtensionWrapper android = new ExtensionWrapper("Android", new String[]{".java", ".xml"});
    	
    	presets.add(java); presets.add(c); presets.add(android);
    	
    	presetsStrings = new String[3];
    	presetsStrings[0] = java.toString(); presetsStrings[1] = c.toString(); presetsStrings[2] = android.toString();
    	
    	//customs
    	BufferedReader reader = null;
    	try {
    		File newFile = new File("data"+File.separator+"customs.txt");
    		//if the file exists, read dat from it
    		if(newFile.exists()) {
    			reader = new BufferedReader(new FileReader(newFile));
        		String line = null;
        		while((line = reader.readLine()) != null) {
        			String[] parsed = line.split("-");
        			String[] extensions = parsed[1].split(",");
        			customs.add(new ExtensionWrapper(parsed[0], extensions));
        		}
        		customsStrings = new String[customs.size()];
        		for(int i = 0; i < customsStrings.length; i++)
        			customsStrings[i] = customs.get(i).toString();
    		}
    		//otherwise try to create it and the directory
    		else {
    			newFile.getParentFile().mkdir();
	    		newFile.createNewFile();
    		}
    	} catch(FileNotFoundException e) {
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	} finally {
    		if(reader != null)
    			try {
    				reader.close();
    			} catch(Exception e) {
    				e.printStackTrace();
    			}
    	}
    	customsStrings = new String[0];
    }

    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        presetList = new NewList(presets, presetsStrings);
        MouseAdapter mouseListener1 = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	customList.clearSelection();
             }
        };
        presetList.addMouseListener(mouseListener1);
        jScrollPane2 = new javax.swing.JScrollPane();
        customList = new NewList(customs, customsStrings);
        MouseAdapter mouseListener2 = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	presetList.clearSelection();
             }
        };
        customList.addMouseListener(mouseListener2);
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LOCC v0.1");
        setPreferredSize(new java.awt.Dimension(600, 400));
        setResizable(false);

        presetList.setBorder(javax.swing.BorderFactory.createTitledBorder("Presets"));
        jScrollPane1.setViewportView(presetList);

        customList.setBorder(javax.swing.BorderFactory.createTitledBorder("Custom"));
        jScrollPane2.setViewportView(customList);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Drag files or folders to count the lines of code.\nOnly files with the selected extensions will be counted."+
        				   "\nUse the preset extensions or create your own grouping.");
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder("Breakdown"));
        jScrollPane3.setViewportView(jTextArea1);

        //////////
        //File menu
        //////////
        jMenu1.setText("File");
        jMenuItem3.setText("Open");
        jMenuItem3.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		if(customList.getSelectedIndex() < 0 && presetList.getSelectedIndex() < 0) {
        			JOptionPane.showMessageDialog(Main.this, "Please select an extension filter", "Error", JOptionPane.ERROR_MESSAGE);
    				return;
    			}
        		JFileChooser chooser = new JFileChooser();
        		chooser.setCurrentDirectory(new File(lastDirectoryAccessed));
        		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        		chooser.setDialogTitle("Select file(s)/folder(s)");
        		chooser.setMultiSelectionEnabled(true);
        		chooser.setAcceptAllFileFilterUsed(false);
        		
        		ExtensionWrapper wrapper = null;
        		if(customList.getSelectedIndex() >= 0)
        			wrapper = customList.getExtensionWrapperAt(customList.getSelectedIndex());
        		else
        			wrapper = presetList.getExtensionWrapperAt(presetList.getSelectedIndex());
        		FileNameExtensionFilter filter = new FileNameExtensionFilter(wrapper.getName() + " (" + wrapper.getToolTip() + ")", wrapper.getExtensionFilterStrings());
        		chooser.setFileFilter(filter);
        		
    		    if (chooser.showOpenDialog(Main.this) == JFileChooser.APPROVE_OPTION) {
    		    	lastDirectoryAccessed = chooser.getCurrentDirectory().getAbsolutePath();
    		    	startCounter(chooser.getSelectedFiles());
    		    }
        	}
        });
        jMenu1.add(jMenuItem3);
        jMenuBar1.add(jMenu1);

        //////////
        //Edit menu
        //////////
        jMenu2.setText("Edit");
        jMenuItem1.setText("Add custom extension");
        jMenuItem1.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		JTextField name = new JTextField();
        		JTextField extensions = new JTextField();
        		Object[] message = {
        		    "Name (Ex: Android):", name,
        		    "Extensions (Ex: .java, .xml):", extensions
        		};

        		int option = JOptionPane.showConfirmDialog(null, message, "Add custom extensions", JOptionPane.OK_CANCEL_OPTION);
        		if (option == JOptionPane.OK_OPTION) {
        		    if (name.getText().trim().equals("") || extensions.getText().trim().equals("")) {
        		        JOptionPane.showMessageDialog(Main.this, "The Name or Extensions field cannot be blank", "Error", JOptionPane.ERROR_MESSAGE);
        		    } else {
        		    	String[] ext = extensions.getText().split(",");
        		    	for(int i = 0; i < ext.length; i++)
        		    		ext[i] = ext[i].trim();
        		        ExtensionWrapper item = new ExtensionWrapper(name.getText(), ext);
        		        customList.addExtension(item);
        		    }
        		}
        	}
        });
        jMenuItem6.setText("Delete custom extension");
        jMenuItem6.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		int index = customList.getSelectedIndex();
        		if(index < 0)
        			JOptionPane.showMessageDialog(Main.this, "No custom extension selected", "Error", JOptionPane.ERROR_MESSAGE);
        		else
        			customList.deleteExtension(index);
        	}
        });
        jMenu2.add(jMenuItem1);
        jMenu2.add(jMenuItem6);

        jMenuBar1.add(jMenu2);

        //////////
        //Options menu
        //////////
        jMenu3.setText("Options");
        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("Count whitespace");
        jMenu3.add(jCheckBoxMenuItem1);
        jCheckBoxMenuItem2.setSelected(false);
        jCheckBoxMenuItem2.setText("Count comment");
        jMenu3.add(jCheckBoxMenuItem2);
        jMenuBar1.add(jMenu3);
        
        //////////
        //Help menu
        //////////
        jMenu4.setText("Help");
        jMenuItem2.setText("How to use");
        jMenuItem2.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		System.out.println("how to");
        	}
        });
        jMenuItem4.setText("About LOCC");
        jMenuItem4.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		System.out.println("about");
        	}
        });
        jMenu4.add(jMenuItem2);
        jMenu4.add(jMenuItem4);
        jMenuBar1.add(jMenu4);
        
        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );

        pack();
    }                      

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private NewList presetList;
    private NewList customList;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;                 
}
