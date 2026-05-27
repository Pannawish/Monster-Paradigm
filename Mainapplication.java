// Pannawish Kriengyakul
// Papon Suramanont
// Premwiss Seenumngernmee
// Rapeepat Pokpattanakul
// Panya Mahasrisaengpetch

package Monster_Paradigm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Mainapplication extends JFrame{
    
    // Components
    private JPanel          contentpane;    // Container
    private JLabel          drawpane, iLabel;
    private JButton         playButton,     
                            nameButton,
                            creditButton,
                            okButton;
    private JTextField      nameTxt;
    
    private String          name;           // Name of monster

    private MyImageIcon     backgroundImg;
    private MySound         backgroundSong;
    
    private Mainapplication currentFrame;
    
    private int framewidth = MyConstants_title.FRAMEWIDTH;
    private int frameheight = MyConstants_title.FRAMEHEIGHT;

    public static void main(String[] args) 
    {
        new Mainapplication();
    }
    
    public Mainapplication() {
        setTitle("Monster Paradigm");
        setSize(framewidth, frameheight);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        currentFrame = this;
        
        name = "";
        
        currentFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                currentFrame.backgroundSong.stop();
                JOptionPane.showMessageDialog(currentFrame,
                        "Hope you had fun!!!", 
                        "Goodbye", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        contentpane = (JPanel)getContentPane();
        contentpane.setLayout( new BorderLayout() );
        
        AddComponents();
        
        JOptionPane.showMessageDialog(currentFrame,
                                """
                                1) Name your pet
                                2) Enter the name in textfield
                                3) Press Ok!
                                4) Press Play!
                                
                                **Don't always follow instructions! We made sure to handle eveything :)**
                                """,
                                "Instructions",JOptionPane.INFORMATION_MESSAGE);
    }

    private void AddComponents() {
        backgroundImg = new MyImageIcon(MyConstants_title.FILE_BG).resize(framewidth, frameheight);
        drawpane = new JLabel();
        drawpane.setIcon(backgroundImg);
        drawpane.setLayout(null);
        
        backgroundSong = new MySound(MyConstants_title.FILE_THEME);
        backgroundSong.playLoop();
        backgroundSong.setVolume(0.3f);

        // ---------------------------- PLAY -----------------------------------

        playButton = new JButton("PLAY");
        playButton.addActionListener( new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                if("".equals(name)) {                
                        JOptionPane.showMessageDialog(currentFrame,
                    "Please name your monster", "Error",
                    JOptionPane.ERROR_MESSAGE);
                    return;
                }
                currentFrame.setVisible(false);
                backgroundSong.stop();
                mainroom main = new mainroom(name, "Blue"); 
            }
        });
        
        // ---------------------------- NAME -----------------------------------
        
        nameTxt = new JTextField("",20);
        nameTxt.setVisible(false);
        nameTxt.setBounds(0, 0, 200, 30); 
                
        okButton = new JButton("Ok!");
        okButton.setVisible(false);
        okButton.setBounds(210, 0, 80, 30); // Set bounds for okButton
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("".equals(nameTxt.getText())){
                    JOptionPane.showMessageDialog(currentFrame,
                            "Please enter a name",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                name = nameTxt.getText().trim();
                JOptionPane.showMessageDialog(currentFrame,
                "Monster named: " + name, "Monster Name",
                JOptionPane.INFORMATION_MESSAGE);
                okButton.setVisible(false);
                nameTxt.setVisible(false);
                currentFrame.revalidate();
                currentFrame.repaint();
            }
        });
        
        nameButton = new JButton("NAME MONSTER");
        nameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okButton.setVisible(true);
                nameTxt.setVisible(true);
                currentFrame.revalidate();
                currentFrame.repaint();
                nameTxt.requestFocusInWindow();
            }
        });
        
        // ---------------------------- CREDIT ---------------------------------
        
        creditButton = new JButton("CREDIT");
        creditButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(currentFrame,
                    """
                    Pannawish Kriengyakul
                    Papon Suramanont
                    Premwiss Seenumngernmee
                    Rapeepat Pokpattanakul
                    Panya Mahasrisaengpetch""", 
                    "Credit",
                    JOptionPane.DEFAULT_OPTION);
        });
        
        // ------------------------- CONTROL PANEL -----------------------------
        JPanel control = new JPanel();
        control.setBounds(110,450,780,60);
        control.setOpaque(false);
        control.add(playButton);
        control.add(nameButton);
        control.add(creditButton);
        control.add(nameTxt);
        control.add(okButton);
        
        drawpane.add(control);
        contentpane.add(drawpane, BorderLayout.CENTER);
        
        validate();
    }
}
