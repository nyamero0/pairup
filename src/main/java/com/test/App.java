package com.test;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.HashMap;
/**
 * Hello world!
 *
 */
public class App extends JFrame
{
    public static int
        WIDTH = 1280,
        HEIGHT = 760;

    private JPanel GameUI;
    private GridBagConstraints RootGBC;
    private GridBagLayout GameUILayout;
    private GridBagConstraints GameUIGBC;
    private boolean isInitialized = false;
    private HashMap<String, JPanel> GamePages = new HashMap<String,JPanel>();
    static class GameTopics {
        private static HashMap<String, String[]> gameTopics = new HashMap<String, String[]>();

        {
            gameTopics.put("Programming", new String[]{

            });
        }
    }
    public App init(){
        if(isInitialized) return this;
        
        isInitialized = true;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Test");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final ImageIcon bg = new ImageIcon(new ImageIcon(Paths.get("").toAbsolutePath().normalize().toString() + "/src/main/java/com/test/linus.jpg").getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH));
        setContentPane(new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(bg.getImage(), 0, 0, null);
            }
        });

        RootGBC = new GridBagConstraints();
        GameUILayout = new GridBagLayout();
        GameUIGBC = new GridBagConstraints();

        GameUIGBC.anchor = GridBagConstraints.CENTER;
        setLayout(GameUILayout);

        
        GameUI = new JPanel();
        GameUI.setOpaque(false);
        
        final GridBagLayout GameMenuLayout = new GridBagLayout();
        final GridBagConstraints GameMenuGBC = new GridBagConstraints();


        final JPanel GameMenu = new JPanel();
        final JPanel GameDifficulty = new JPanel();
        final JPanel GameMode = new JPanel();
        final JPanel GameTopics = new JPanel();
        final JScrollPane GameTopics2 = new JScrollPane(GameTopics);
        final JPanel MainGame = new JPanel();
        
        final JButton playBtn = new JButton("Play");
        final JButton settingsBtn = new JButton("Settings");
        final JButton exitBtn = new JButton("Exit");

        GameMenu.setLayout(GameMenuLayout);
        GameMenuGBC.anchor = GridBagConstraints.CENTER;
        GameMenuGBC.gridwidth = GridBagConstraints.REMAINDER;
        GameMenuGBC.fill = GridBagConstraints.BOTH;

        final JLabel titleLabel = new JLabel("PairUP!");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 68));
        titleLabel.setForeground(Color.BLACK);
        
        GameMenuGBC.ipadx = 5;
        GameMenuGBC.ipady = 18;
        GameMenuGBC.insets = new Insets(0,0,24,0);

        GameMenu.add(titleLabel, GameMenuGBC);
        GameMenu.setOpaque(false);
        GameMenu.setBackground(new Color(0,0,0,0.0f));
        GameMenu.setBorder(BorderFactory.createEmptyBorder());
        int padx = 56;
        GameMenuGBC.insets.left = padx;
        GameMenuGBC.insets.right = padx;
        GameMenuGBC.insets.bottom = 12;

        final Font btnFont = new Font("Arial", Font.PLAIN, 25);
        for(JButton btn : new JButton[]{playBtn,settingsBtn,exitBtn}){
            btn.setFont(btnFont);
            btn.setFocusPainted(false);
            btn.setForeground(Color.WHITE);
            btn.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0.4f), 1, true));
        }

        playBtn.setBackground(new Color(77, 196, 61));
        GameMenu.add(playBtn, GameMenuGBC);
        settingsBtn.setBackground(new Color(130, 103, 163));
        GameMenu.add(settingsBtn, GameMenuGBC);
        GameMenuGBC.insets.bottom = 0;
        exitBtn.setBackground(new Color(201, 69, 62));
        GameMenu.add(exitBtn, GameMenuGBC);
        final App self = this;

        playBtn.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    self.GameUI.remove(GameMenu);
                    self.GameUI.add(self.GamePages.get("GameDifficulty"), GameUIGBC);
                    self.revalidate();
                    self.repaint();
                }

            }
        );
        settingsBtn.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){

                }

            }
        );
        exitBtn.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    int res = JOptionPane.showConfirmDialog(null, "Exit the Game?", "", JOptionPane.YES_NO_OPTION);
                    if(res == 0)
                        System.exit(0);
                }

            }
        );

        GamePages.put("GameMenu", GameMenu);

        final GridBagLayout GameDifficultyLayout = new GridBagLayout();
        final GridBagConstraints GameDifficultyGBC = new GridBagConstraints();

        final JLabel difficultyTitle = new JLabel("Difficulty");
        final JButton easyBtn = new JButton("Easy");
        final JButton medBtn = new JButton("Medium");
        final JButton hardBtn = new JButton("Hard");
        final JButton backBtn = new JButton("Back");

        difficultyTitle.setFont(new Font("Arial", Font.PLAIN, 64));
        difficultyTitle.setHorizontalAlignment(JLabel.CENTER);
        difficultyTitle.setForeground(Color.BLACK);

        GameDifficulty.setLayout(GameDifficultyLayout);
        GameDifficulty.setOpaque(false);

        GameDifficultyGBC.anchor = GridBagConstraints.CENTER;
        GameDifficultyGBC.fill = GridBagConstraints.BOTH;
        GameDifficultyGBC.gridwidth = GridBagConstraints.REMAINDER;
        GameDifficultyGBC.ipady = 12;
        GameDifficultyGBC.insets = new Insets(0,0,18,0);
        GameDifficulty.add(difficultyTitle, GameDifficultyGBC);

        GameDifficultyGBC.ipadx = 2;
        GameDifficultyGBC.ipady = 13;
        GameDifficultyGBC.insets = new Insets(0, 52, 12, 52);


        for(JButton btn:new JButton[]{easyBtn, medBtn, hardBtn, backBtn}){
            btn.setFont(btnFont);            
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
        }

        easyBtn.setBackground(new Color(127, 227, 118));
        medBtn.setBackground(new Color(130, 103, 163));
        hardBtn.setBackground(new Color(57, 63, 135));
        backBtn.setBackground(new Color(204, 84, 80));

        GameDifficultyGBC.fill = GridBagConstraints.BOTH;
        GameDifficulty.add(easyBtn, GameDifficultyGBC);
        GameDifficulty.add(medBtn, GameDifficultyGBC);
        GameDifficultyGBC.insets.bottom = 18;
        GameDifficulty.add(hardBtn, GameDifficultyGBC);
        GameDifficultyGBC.ipadx = 0;
        GameDifficultyGBC.insets.left = padx;
        GameDifficultyGBC.insets.right = padx;
        GameDifficulty.add(backBtn, GameDifficultyGBC);

        easyBtn.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    self.GameUI.remove(GameDifficulty);
                    self.GameUI.add(self.GamePages.get("GameMode"), GameUIGBC);
                    self.revalidate();
                    self.repaint();
                }
            }
        );
        medBtn.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    self.GameUI.remove(GameDifficulty);
                    self.GameUI.add(self.GamePages.get("GameMode"), GameUIGBC);
                    self.revalidate();
                    self.repaint();
                }
            }
        );
        hardBtn.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    self.GameUI.remove(GameDifficulty);
                    self.GameUI.add(self.GamePages.get("GameMode"), GameUIGBC);
                    self.revalidate();
                    self.repaint();
                }
            }
        );
        backBtn.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    self.GameUI.remove(GameDifficulty);
                    self.GameUI.add(self.GamePages.get("GameMenu"), GameUIGBC);
                    self.revalidate();
                    self.repaint();
                }
            }
        );


        GamePages.put("GameDifficulty", GameDifficulty);

        GridBagLayout GameModeLayout = new GridBagLayout();
        GridBagConstraints GameModeGBC = new GridBagConstraints(); 
        GameMode.setLayout(GameModeLayout);
        final JLabel gameModeTitle = new JLabel("Mode Selection");
        final JButton selectBtn = new JButton("Select Topic");
        final JButton randomBtn = new JButton("Random Topic");
        final JButton gameModeBackBtn = new JButton("Back");


        GameMode.add(gameModeTitle,GameDifficultyGBC);
        GameMode.add(selectBtn,GameDifficultyGBC);
        GameMode.add(randomBtn,GameDifficultyGBC);
        GameMode.add(gameModeBackBtn,GameDifficultyGBC);


        selectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                RootGBC.anchor = GridBagConstraints.NORTH;
                RootGBC.weighty = 1;
                self.GameUI.remove(GameMode);
                self.GameUI.add(GameTopics2);
                GameUILayout.setConstraints(GameUI, RootGBC);
                self.GameUI.revalidate();
                self.GameUI.repaint();
                self.revalidate();
                self.repaint();
            }
        });
        randomBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                self.GameUI.remove(GameMode);
                self.GameUI.add(self.GamePages.get("MainGame"), GameUIGBC);
                self.revalidate();
                self.repaint();
            }
        });
        gameModeBackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                self.GameUI.remove(GameMode);
                self.GameUI.add(self.GamePages.get("GameDifficulty"), GameUIGBC);
                self.revalidate();
                self.repaint();
            }
        });

        GamePages.put("GameMode", GameMode);

        GridBagLayout GameTopicsLayout = new GridBagLayout();
        GridBagConstraints GameTopicsGBC = new GridBagConstraints();
        ScrollPaneLayout GameTopics2Layout = new ScrollPaneLayout();

        final JPanel topicsTitlePanel = new JPanel();
        final BorderLayout topicsTitlePanelLayout = new BorderLayout();
        final JLabel topicsTitle = new JLabel("Choose Topic");
        
        topicsTitlePanel.setLayout(topicsTitlePanelLayout);
        topicsTitlePanel.setOpaque(false);
        topicsTitlePanel.add(topicsTitle, BorderLayout.CENTER);
        topicsTitle.setFont(new Font("Arial", Font.PLAIN, 24));


        GameTopics.setLayout(GameTopicsLayout);
        GameTopics.setOpaque(false);
        GameTopics2.setLayout(GameTopics2Layout);
        GameTopics2.setOpaque(false);
        
        

        GameTopicsGBC.anchor = GridBagConstraints.PAGE_START;
        GameTopicsGBC.fill = GridBagConstraints.BOTH;
        GameTopicsGBC.gridwidth = GridBagConstraints.REMAINDER;


        GameTopics.add(topicsTitlePanel, GameTopicsGBC);
        
        GamePages.put("GameTopics", GameTopics);
        GamePages.put("MainGame", MainGame);
        

        return this;
    }
    public void run(){
        if(!isInitialized) throw new Error("App not initialized.");
        JPanel curr = GamePages.get("GameMenu");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        

        GameUI.add(curr, GameUIGBC);
        add(GameUI, RootGBC);
        setBounds(
            (screenSize.width - WIDTH) / 2,
            (screenSize.height - HEIGHT) / 2,
            WIDTH,
            HEIGHT
        );
        setVisible(true);
    }
    
    public static void main( String[] args )
    {
        SwingUtilities.invokeLater(
            new Runnable(){
                @Override
                public void run(){
                    new App().init().run();
                }
            }
        );
    }
}
