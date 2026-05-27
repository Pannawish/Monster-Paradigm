// Pannawish Kriengyakul
// Papon Suramanont
// Premwiss Seenumngernmee
// Rapeepat Pokpattanakul
// Panya Mahasrisaengpetch

package Monster_Paradigm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class GameRoom extends JFrame 
{
    private JPanel               contentpane;
    private JLabel               drawpane;
    private JComboBox            difficulty; 
    private JButton              startButton, quitButton; 
    private JTextField           scoreText;
    private MyImageIcon          backgroundImg,Limg, Rimg;    
    private MySound              themeSound, winSound, lostSound;
    
    private String               Monstername;
    private MonsterLabel         monsterLabel;
    private GameRoom      currentFrame;

    private int framewidth  = MyConstants_gameroom.FRAMEWIDTH;
    private int frameheight = MyConstants_gameroom.FRAMEHEIGHT;
    
    private int score=0;
    boolean startItemGenerator=false;
    public Timer timer;
    public String level;
    public int xp = 0;
    public int delayTime = 400;
    boolean shouldStop = false;
    int total_score = 0;
    int clickButton = 0;
    int counter = 0;
    int itemSpeed = 50;
    String skin = "";
    

    public GameRoom(MyImageIcon l, MyImageIcon r, String n, String sk)
    {
        setTitle("Worm Game");
	setSize(framewidth, frameheight); 
        setLocationRelativeTo(null);
	setVisible(true);
	setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        currentFrame = this;
        
        skin = sk;
        
        Monstername = n;
        
        Limg = l; Rimg = r;
        
        contentpane = (JPanel)getContentPane();
	contentpane.setLayout( new BorderLayout() );     
        AddComponents();

        contentpane.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int keyCode = e.getKeyCode();
                    if (keyCode == KeyEvent.VK_LEFT) {
                        monsterLabel.turnLeft();
                        monsterLabel.updateLocation();
                    } else if (keyCode == KeyEvent.VK_RIGHT) {
                        monsterLabel.turnRight();
                        monsterLabel.updateLocation();
                    }
                }
            });
        currentFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(currentFrame,
                        "Hope you had fun!!!", 
                        "Goodbye", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        JOptionPane.showMessageDialog(currentFrame,
                                """
                                - Rules: Meat +1 , Sword -1
                                - Start: begins game
                                - Control: Left & Right arrow key
                                - Quit: Calculate score and update level
                                       (Force quit if point < 0)
                                - Difficulty: Speed and amount of items increase with each level
                                
                                **Don't always follow instructions! We made sure to handle eveything :)**
                                """,
                                "Instructions",JOptionPane.INFORMATION_MESSAGE);
    }
    public void AddComponents()
    {
        backgroundImg  = new MyImageIcon(MyConstants_gameroom.FILE_BG).resize(framewidth, frameheight);
	drawpane = new JLabel();
	drawpane.setIcon(backgroundImg);
        drawpane.setLayout(null);

	themeSound = new MySound(MyConstants_gameroom.FILE_THEME); 
        themeSound.playLoop(); themeSound.setVolume(0.4f);
        
        winSound = new MySound(MyConstants_gameroom.FILE_WIN);
        lostSound = new MySound(MyConstants_gameroom.FILE_LOST); 
        
        monsterLabel = new MonsterLabel(currentFrame);
        drawpane.add(monsterLabel);
        
        startButton = new JButton("START");
        //start to drop the item
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!startItemGenerator){
                    ////
                    timer = new Timer(delayTime, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        startItemGeneration();
                    }
                    });
                    timer.start();
                    ////
                    startItemGenerator=true;
                    clickButton = 1;
                }
                contentpane.requestFocusInWindow();
            }
        });
        
        quitButton = new JButton("QUIT");
        //quit to main room
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer != null && timer.isRunning()) {
                    timer.stop();
                    startItemGenerator=false;
                }
                shouldStop = true;
                total_score = score / 10;
                xp += total_score * 1000;
                themeSound.stop();
                winSound.playOnce(); themeSound.setVolume(0.4f);
                JOptionPane.showMessageDialog(null, 
                        "Total score = " + score + 
                        "\nLevel Up: " + total_score +
                        "\n" + Monstername + " has " + xp + "XP", 
                        "Score", 
                        JOptionPane.INFORMATION_MESSAGE);
                winSound.stop();
                currentFrame.setVisible(false);
                mainroom main = new mainroom(Monstername, skin);
                main.updateXp(xp);
                }
        });
        
        String[] speed = { "EASY", "NORMAL", "HARD"};
        difficulty = new JComboBox(speed);
	difficulty.setSelectedIndex(1);
        difficulty.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (timer != null && timer.isRunning()) {
                    timer.stop();
                    startItemGenerator=false;
                }
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (e.getItem()=="EASY"){
                        level="EASY"; delayTime=800; itemSpeed = 100;
                    }
                    else if(e.getItem()=="NORMAL"){
                        level="NORMAL"; delayTime=400; itemSpeed = 50;
                    }
                    else if(e.getItem()=="HARD"){
                        level="HARD"; delayTime=200; itemSpeed = 35;
                    }
                }
                if (!startItemGenerator&&clickButton==1){ 
                    timer = new Timer(delayTime, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        startItemGeneration();
                    }
                    });
                    timer.start();
                }
                contentpane.requestFocusInWindow();
            }
        });
        
        scoreText = new JTextField(Integer.toString(score), 5);		
	scoreText.setEditable(false);
        JLabel titleLabel = new JLabel("SCORE:");
        JPanel control  = new JPanel();
        control.add(startButton);
        control.add(quitButton);
        control.add(difficulty);
        control.add(titleLabel);
        control.add(scoreText);
        contentpane.add(control,BorderLayout.SOUTH);
        contentpane.add(drawpane, BorderLayout.CENTER); 
        validate();
    }//end addComponent
    
    public synchronized void LevelCalculate(int type) {
        //calculate xp
        if(type==0){
            score+=1;
        }
        else{
            score-=1;
        }
        if (score<0&&counter==0){
            if (timer != null && timer.isRunning()) {
                    timer.stop();
                    startItemGenerator=false;
                }
            shouldStop = true;
            themeSound.stop();
            lostSound.playOnce(); themeSound.setVolume(0.4f);
            JOptionPane.showMessageDialog(currentFrame,
                        "YOU LOST", 
                        "YOU LOST", 
                        JOptionPane.INFORMATION_MESSAGE);
            lostSound.stop();
            currentFrame.setVisible(false);
            mainroom main = new mainroom(Monstername, skin);
            main.updateXp(xp);
            counter++;
        }
        scoreText.setText(Integer.toString(score));
    }
////

////
    public void startItemGeneration() {
        Thread itemThread = new Thread() {
            public void run()
            { 
                while(!shouldStop){
                ItemLabel_G item = new ItemLabel_G(currentFrame);
                drawpane.add(item);
                while(!item.getHitting()){
                    if (shouldStop==true) item.setHitting(true);
                    item.itemUpdateLocation();
                    if(item.getBounds().intersects(monsterLabel.getBounds())){
                        item.setHitting(true);
                        item.playHitSound();
                        item.setVisible(false);
                        drawpane.remove(item);
                        LevelCalculate(item.getType());
                    }
                    repaint();
                }
                repaint();
                }
            } // end run   
        }; // end thread creation
        itemThread.start();
    }
    
    public void setXP(int xp) {this.xp = xp;}
    public void setImg(MyImageIcon l, MyImageIcon r) {
        Limg = l;    Rimg = r;
    }
    protected MyImageIcon getRimg() {return Rimg;}
    protected MyImageIcon getLimg() {return Limg;}    
    protected int getSpeed() {
        return itemSpeed;
    }
    
}//end of MainApplication
////////////////////////////////////////////////////////////////////////////////
class MonsterLabel extends JLabel 
{
    private GameRoom  parentFrame;   
    private MyImageIcon     leftImg, 
                            rightImg;      
        
    private int width    = MyConstants_gameroom.MONSTERWIDTH;
    private int height   = MyConstants_gameroom.MONSTERHEIGHT; 
    private int curX     = 500, curY = 400; 
    private int speed    = 500;
    private boolean left = true, move = false;        
        
    public MonsterLabel(GameRoom pf)
    {
        parentFrame = pf;
        leftImg = parentFrame.getLimg().resize(width, height);
        rightImg = parentFrame.getRimg().resize(width, height);
        setIcon(leftImg);
        setBounds(curX, curY, width, height);
    }
        
    public void setSpeed(int s)     { speed = s; }
    public void turnLeft()          { setIcon(leftImg);  left = true; }
    public void turnRight()         { setIcon(rightImg); left = false; }
    public void setMove(boolean m)  { move = m; }
    public boolean isMove()         { return move; }
        
    public void updateLocation()
    {
        if (left)
        {   
            curX = curX - 50;
            if (curX < -100) { curX = parentFrame.getWidth(); } 			
        }
        else
        {
            curX = curX + 50;
            if (curX > parentFrame.getWidth()-100) { curX = 0; }			
        }
        setLocation(curX, curY);
        repaint();                       
    } 
    
    protected void setrightImg(MyImageIcon r) {
        rightImg = r.resize(width, height);
    }
    
    protected void setleftImg(MyImageIcon l) {
        leftImg = l.resize(width, height);
    }

} // end class MonsterLabel

////////////////////////////////////////////////////////////////////////////////
class ItemLabel_G extends JLabel 
{
    private GameRoom  parentFrame;   
    
    private int             type;          
    private MyImageIcon     itemImg;
    private MySound         hitSound;
    private boolean         remove=false; 
    private boolean         hitting=false;
    
    String [] imageFiles = { MyConstants_gameroom.FILE_MEAT, MyConstants_gameroom.FILE_SWORD };        
    String [] soundFiles = { MyConstants_gameroom.FILE_GOOD, MyConstants_gameroom.FILE_HURT };
    int    [] hitpoints  = { 1, -1 };    

    private int width    = MyConstants_gameroom.ITEMWIDTH;
    private int height   = MyConstants_gameroom.ITEMHEIGHT;
    private int curX, curY;
    private int speed = 400;

    public ItemLabel_G(GameRoom pf)
    {
        parentFrame = pf;
        
        curX = (int)(Math.random() * 5555) % (parentFrame.getWidth()-100);
        if (curX % 2 == 0) { type = 0; curY = 0; }
        else               { type = 1; curY = 0; }
          
        itemImg  = new MyImageIcon(imageFiles[type]).resize(width, height);
        hitSound = new MySound(soundFiles[type]);
        setIcon(itemImg);
        setBounds(curX, curY, width, height);
    }
    
    public void setSpeed(int x)        {speed =x;}
    //public int getSpeed()             {return speed;}
    public void playHitSound()         { hitSound.playOnce(); }
    public int  getHitPoints()         { return hitpoints[type]; }
    public int getType()               {return type;}
    public boolean getRemove()         {return remove;}
    public boolean getHitting()        {return hitting;}
    public void setHitting(boolean x)        { hitting=x;}
    public void itemUpdateLocation()
    {
            curY = curY + 10;
            if (curY > parentFrame.getHeight()) { 
                remove=true;
            }			
        
        setLocation(curX, curY);
        repaint();             
        try { Thread.sleep(parentFrame.getSpeed()); } 
        catch (InterruptedException e) {}            
    } 
} // end class ItemLabel


