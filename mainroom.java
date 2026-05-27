// Pannawish Kriengyakul
// Papon Suramanont
// Premwiss Seenumngernmee
// Rapeepat Pokpattanakul
// Panya Mahasrisaengpetch

package Monster_Paradigm;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import javax.swing.*;


public class mainroom extends JFrame{
    // Components
    private JPanel          contentpane;    // Container
    private JLabel          drawpane,
                            nameLabel;
    private JComboBox       skinCombo;          // skin
    private JList           foodList;       // food items
    private JToggleButton   []tb;           // amount
    private JButton         feedButton,     // feed monster
                            gameButton;
    private JProgressBar    xpProgressBar;  
    private JTextField      xpText;         // Display current xp
    private int             xp,
                            multiplier;       // corresponds to food amount
    private String          skin;
    private String          Monstername;

    private MyImageIcon    backgroundImg;
    private MySound        backgroundSong;   
 
    private JLabel           spongeLabel;
    private MonsterLabelmain monsterLabel;   
    private mainroom         currentFrame;
    
    private final int framewidth = MyConstants_main.FRAMEWIDTH;
    private final int frameheight = MyConstants_main.FRAMEHEIGHT;
    
    public mainroom(String name, String sk) {
        setTitle("Monster Paradigm");
        setSize(framewidth, frameheight);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        skin =sk;
        Monstername = name;
        currentFrame = this;
        currentFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // do nothing on normal closing operation
        currentFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int result = 
                        JOptionPane.showConfirmDialog(currentFrame, 
                        "Are you sure you want to exit? You will lose all progress!", 
                        "Warning",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.NO_OPTION) {
                    return; // Stay on same page
                }
                else{
                currentFrame.setVisible(false);
                backgroundSong.stop();
                
                Mainapplication main = new Mainapplication();
                }
            }        
        });
        
        contentpane = (JPanel)getContentPane();
        contentpane.setLayout( new BorderLayout() );
        
        AddComponents();
        
        JOptionPane.showMessageDialog(currentFrame,
                                """
                                - Click to pet and get XP!
                                - Use sponge to brush monster
                                - Feed: select food & amount -> feed button
                                - Skin: Change color of monster
                                - Each action gives out different XP points
                                - Level up your monster to change its form (3 forms)
                                - Game: Go to game room
                                
                                **Don't always follow instructions! We made sure to handle eveything :)**
                                """,
                                "Instructions",JOptionPane.INFORMATION_MESSAGE);
    
    }

    private void AddComponents() {
        backgroundImg = new MyImageIcon(MyConstants_main.FILE_BG).resize(framewidth, frameheight);
        drawpane = new JLabel();
        drawpane.setIcon(backgroundImg);
        drawpane.setLayout(null);
        
        backgroundSong = new MySound(MyConstants_main.FILE_THEME);
        backgroundSong.playLoop();
        backgroundSong.setVolume(0.1f);     // 10% Volume
        
        spongeLabel = new itemLabel(MyConstants_main.FILE_SPONGE, MyConstants_main.FILE_BUBBLE,
                0,0, MyConstants_main.ITEMWIDTH, MyConstants_main.ITEMHEIGHT, currentFrame);
        drawpane.add(spongeLabel);
        
        nameLabel = new JLabel(Monstername);
        nameLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 42));
        nameLabel.setVisible(true);
        nameLabel.setBounds(150, 40, 350, 150); // Adjusted bounds for visibility
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        drawpane.add(nameLabel);
        
        // ---------------------------- FEEDING --------------------------------
        // Radio buttons to select food amount (1-5)
        tb = new JToggleButton[5];
        tb[0] = new JRadioButton("1");  tb[0].setName("1");
        tb[1] = new JRadioButton("2");  tb[1].setName("2");
        tb[2] = new JRadioButton("3");  tb[2].setName("3");
        tb[3] = new JRadioButton("4");  tb[3].setName("4");
        tb[4] = new JRadioButton("5");  tb[4].setName("5");
        //tb[0].setSelected(true);
        
        ButtonGroup rgroup = new ButtonGroup();
        for (int i = 0; i < 5; i++) {
            rgroup.add(tb[i]); // Only 1 can be selected at once
        }
        
        for (int i = 0; i < 5; i++) {
            final int index = i;
            tb[i].addItemListener((ItemEvent e) -> {setMod(index + 1);}); 
        }
        
        // Food item names
	 String[] items = {
            "Pizza", "Cake", "Sushi", "Ice-Cream", "Burgers", "Pasta",
            "Tacos", "Steak", "Salad", "Cookies"};
        
        // JList to select food item
        foodList = new JList(items);
	foodList.setVisibleRowCount(3);
        JScrollPane scroll = new JScrollPane(foodList);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(130, 70));
        foodList.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        
        feedButton = new JButton("Feed");
        feedButton.addActionListener((ActionEvent e) -> {
            try {
                boolean radioButtonSelected = false;
                for (JToggleButton radio : tb) {
                    if (radio.isSelected()) {
                        radioButtonSelected = true;
                        break;
                    }
                }
                if (!radioButtonSelected) {throw new IllegalArgumentException();}
                String food = currentFrame.foodList.getSelectedValue().toString();
                switch (food) {
                    case "Pizza"    -> updateXp(50 * multiplier);
                    case "Cake"     -> updateXp(40 * multiplier);
                    case "Sushi"    -> updateXp(12 * multiplier);
                    case "Ice-Cream"-> updateXp(19 * multiplier);
                    case "Burgers"  -> updateXp(25 * multiplier);
                    case "Pasta"    -> updateXp(20 * multiplier);
                    case "Tacos"    -> updateXp(22 * multiplier);
                    case "Steak"    -> updateXp(45 * multiplier);
                    case "Salad"    -> updateXp(12 * multiplier);
                    case "Cookies"  -> updateXp(18 * multiplier);
            }
            monsterLabel.eatSound();
            System.out.println("Monster has been fed " + " " + food);
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(currentFrame,
                        "Select an item before feeding", 
                        "Tip", 
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(currentFrame,
                    "Select the amount before feeding",
                    "Tip",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // ---------------------------- SKINS ----------------------------------
        String[] skins = { "Red", "Blue", "Green"};
        skinCombo = new JComboBox(skins);
        int index = 1; //Default
        switch(skin) {
            case "Red" -> {index=0;}
            case "Blue" -> {index=1;} 
            case "Green" -> {index=2;}
        }
	skinCombo.setSelectedIndex( index);
        skinCombo.addItemListener((ItemEvent e) -> {
        currentFrame.monsterLabel.setSkin(getSkin());
        });
        
        // ------------------------------ XP -----------------------------------
        xpText = new JTextField( "0", 5);
	xpText.setEditable(false);
        
        xpProgressBar = new JProgressBar(0, 1000);
        xpProgressBar.setStringPainted(true);
        xpProgressBar.setValue(0);
        xpProgressBar.setString("Level 1");
        xpProgressBar.setPreferredSize(new Dimension(180, 20));
        //------------------------------ GAME ----------------------------------
        gameButton = new JButton("Game");
        //quit to main room
        gameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFrame.setVisible(false);
                currentFrame.backgroundSong.stop();
                MyImageIcon[] hold = monsterLabel.getImg();
                GameRoom game = new GameRoom(hold[0],hold[1], Monstername, getSkin());
//                game.setImg(monsterLabel.getImg()[0], monsterLabel.getImg()[1]);
                game.setXP(xp);
            }
        });
        // ------------------------- CONTROL PANEL -----------------------------
        JPanel control = new JPanel(new GridLayout(2, 1, 0, 6));

        JPanel feedingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        feedingPanel.add(new JLabel("Food"));
        feedingPanel.add(scroll);        // Add list of food items
        feedingPanel.add(new JLabel("Amount"));
        for (int i = 0; i < 5; i++) {
            feedingPanel.add(tb[i]);         // Add radio buttons to control panel
        }
        feedingPanel.add(feedButton);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        statusPanel.add(new JLabel("Skin"));
        statusPanel.add(skinCombo);     // Add skin combo box
        statusPanel.add(new JLabel("XP points"));
        statusPanel.add(xpText);        // Add XP display
        statusPanel.add(xpProgressBar);
        statusPanel.add(gameButton);

        control.add(feedingPanel);
        control.add(statusPanel);

        
        contentpane.add(control, BorderLayout.SOUTH);
        contentpane.add(drawpane, BorderLayout.CENTER);
        
        // Adding monster
        monsterLabel = new MonsterLabelmain(currentFrame);
        drawpane.add(monsterLabel);
        
        validate();
    }
    
    public void updateXp(int point) {
        int prevLvl = currentFrame.xp / 1000;
        
        currentFrame.xp += point;
        xpText.setText(Integer.toString(currentFrame.xp));
                
        int newLvl = currentFrame.xp / 1000;
        int progressInCurrentLevel = currentFrame.xp % 1000;

        xpProgressBar.setValue(progressInCurrentLevel);
        xpProgressBar.setString("Level " + newLvl);
        
        // Check if Lvlup occurs
        if (newLvl > prevLvl) {
            monsterLabel.lvlupSound();
            monsterLabel.setSkin(getSkin());
            System.out.println("Level up to " + newLvl);
        }
    }
    
    // GETTER SETTER
    protected int getXp() {return xp;}
    protected String getSkin() {return skinCombo.getSelectedItem().toString();}
    private void setMod(int n) {multiplier = n;}
    public MonsterLabelmain getMonsterLabel() {return monsterLabel;}
    MyImageIcon getImgL() {return monsterLabel.getImage();}
    MyImageIcon getImgR() {return monsterLabel.getImage();}
}

class MonsterLabelmain extends JLabel implements MouseListener{
    private mainroom        parentFrame;
    
    private MyImageIcon     MonsterImg, MonsterImgL;
    private MySound         eatSound,
                            petSound,
                            lvlupSound;
    
    private final int       width = MyConstants_main.MONSTERWIDTH; 
    private final int       height = MyConstants_main.MONSTERHEIGHT;
    private int             curX = 140;     // Monster x-cor
    private int             curY = 160;     // Monster y-cor
    

    MonsterLabelmain(mainroom currentFrame) {
        parentFrame = currentFrame;
        
        setSkin(parentFrame.getSkin());
        eatSound = new MySound(MyConstants_main.FILE_FEED);
        petSound = new MySound(MyConstants_main.FILE_PET);
        petSound.setVolume(1f);
        lvlupSound = new MySound(MyConstants_main.FILE_LVLUP);
                
        setBounds(curX, curY, width, height);
        addMouseListener(this);
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor when hovering over
    }

    protected void setSkin(String color) {
        int form;

        if(parentFrame.getXp() > 4999 && parentFrame.getXp() < 10000) {form = 2;} // 5 up -> Form 2
        else if (parentFrame.getXp() > 9999) {form = 3;} // 10 up -> Form 3
        else {form = 1;} // form 1
            
        String selectedItem = parentFrame.getSkin();    
        try {
            Field image = MyConstants_main.class.getField("FILE_MONSTER_" + selectedItem.toUpperCase() + form);
            MonsterImg = new MyImageIcon(image.get(null).toString());
            
            Field imageL = MyConstants_main.class.getField("FILE_MONSTER_" + selectedItem.toUpperCase() + form + "L");
            MonsterImgL = new MyImageIcon(imageL.get(null).toString());
        } catch (Exception ex) {}

        setIcon(MonsterImg.resize(width, height));
        repaint();
        System.out.println("Skin Changed");
    }
    
    protected MyImageIcon[] getImg() {
        MyImageIcon[] res = {MonsterImg, MonsterImgL};
        return res;
    }

    protected void eatSound() { eatSound.playOnce(); }
    protected void petSound() { petSound.playOnce(); }
    protected void lvlupSound() { lvlupSound.playOnce(); }


    @Override
    public void mouseClicked(MouseEvent e) {
        parentFrame.updateXp(150);
        petSound();
        System.out.println("You petted the monster");
    }

    public void mousePressed(MouseEvent e)  {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e)  {}
    public void mouseExited(MouseEvent e)   {}

    MyImageIcon getImage() {
        return MonsterImg;
    }
}

class itemLabel extends JLabel implements MouseMotionListener{
    private MyImageIcon icon;
    protected int       curX, curY, width, height;
    private MySound     soundfx;
    protected mainroom  parentFrame;
    
    public itemLabel(String file, String sfile, int x, int y, int w, int h, mainroom pf) {
        width = w;  height = h;
        curX = x;   curY = y;
        
        icon = new MyImageIcon(file).resize(width, height);
        setIcon(icon);

        soundfx = new MySound(sfile);
        setHorizontalAlignment(JLabel.CENTER);
        
        setBounds(curX, curY, width, height);
        
        parentFrame = pf;
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor when hovering over
        addMouseMotionListener(this);
    }
        
    @Override
    public void mouseMoved(MouseEvent e) {}
    
    @Override
    public void mouseDragged(MouseEvent e) {
            curX = curX + e.getX() - height/2;
            curY = curY + e.getY() - width/2;

            if(curX < 0) {
                curX = 0;
            } else if(curX + width > parentFrame.getWidth()) {
                curX = parentFrame.getWidth() - width;
            }
            
            if(curY < 0) {
                curY = 0;
            } else if(curY + height > parentFrame.getHeight()) {
                curY = parentFrame.getHeight() - height;
            }
            
            MonsterLabelmain mons = parentFrame.getMonsterLabel();
            Rectangle monsBound = mons.getBounds();
            Rectangle thisBound = this.getBounds();                        
            if (thisBound.intersects(monsBound)) {
                // swap pic for happy looking monster    
                soundfx.playOnce();
                parentFrame.updateXp(1);
                System.out.println("Monster brushed");
            }
            
            setLocation(curX,curY);
    }    
}

