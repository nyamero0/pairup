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
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
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
    
    private String difficulty;
    private String topic;
    private JButton lastPicked;
    private Timer countdownTimer;
    private Timer startTimer;
    private long gameTime;
    private int gameAccuracy;
    private int gameCountdown = 5;
    

    private static class GameInfo {
        public static HashMap<String, HashMap<String, HashMap<String, Object>>> GameStat = new HashMap<String, HashMap<String, HashMap<String,Object>>>();
        public static HashMap<String, String[]> gameTopics = new HashMap<String, String[]>();
        public static HashMap<String, int[]> gameDifficulty =new HashMap<String, int[]>();
        public static String[] gameDifficulties = {"Easy", "Medium", "Hard"};
        public static HashMap<String, int[]> gameSizes = new HashMap<String, int[]>();

        static {
            gameTopics.put("Programming Languages", new String[]{
                "C", "Clang.png",
                "C#", "Csharp.png",
                "HTML", "html.png",
                "Java", "Java.png",
                "Javascript", "javascript.png",
                "PHP", "php.png",
                "Python", "python.jpg",
                "Ruby", "ruby.png",
                "C++", "C++.png",
                "Kotlin", "kotlin.jpg",
                "Typescript", "typescript.png"
            });
            gameTopics.put("Country Flags", new String[]{
                "Monaco", "monaco.jpg",
                "Italy", "italy.jpg",
                "Japan", "japan.jpg",
                "Mexico", "mexico.jpg",
                "Thailand", "thailand.jpg",
                "Canada", "canada.jpg",
                "India", "india.jpg",
                "Philippines", "philippines.jpg",
                "Vietnam", "vietnam.jpg",
                "Cambodia", "cambodia.jpg"
            });
            gameDifficulty.put("Easy", new int[]{4,3});
            gameDifficulty.put("Medium", new int[]{4,4});
            gameDifficulty.put("Hard", new int[]{5,4});
            gameSizes.put("Easy", new int[]{180, 200});
            gameSizes.put("Medium", new int[]{140, 150});
            gameSizes.put("Hard", new int[]{140, 155});
            
            for(String difficultyName : gameDifficulties){
                GameStat.put(difficultyName, new HashMap<String, HashMap<String,Object>>());
                for(String key : gameTopics.keySet()){
                    final HashMap<String, HashMap<String,Object>> topicInfo = GameStat.get(difficultyName);
                    final HashMap<String,Object> topicProps = new HashMap<String, Object>();
                    topicProps.put("accuracy", 0.0f);
                    topicProps.put("time", 0);
                    topicInfo.put(key, topicProps);
                }
            }
            
        }
    }
    
    public App init(){
        if(isInitialized) return this;
        
        isInitialized = true;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("PairUP");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final ImageIcon bg = new ImageIcon(new ImageIcon(Paths.get("").toAbsolutePath().normalize().toString() + "/src/main/java/com/test/linus.jpg").getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH));
        final ImageIcon mainBg = new ImageIcon(new ImageIcon(Paths.get("").toAbsolutePath().normalize().toString() + "/src/main/java/com/test/background.jpg").getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_AREA_AVERAGING));
        
        setContentPane(new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(mainBg.getImage(), 0, 0, null);
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
        final JPanel MainGame = new JPanel();
        
        final JButton playBtn = new JButton("Play");
        final JButton settingsBtn = new JButton("Settings");
        final JButton exitBtn = new JButton("Exit");

        GameMenu.setLayout(GameMenuLayout);
        GameMenuGBC.anchor = GridBagConstraints.CENTER;
        GameMenuGBC.gridwidth = GridBagConstraints.REMAINDER;
        GameMenuGBC.fill = GridBagConstraints.BOTH;

        final JLabel titleLabel = new JLabel("PairUP!");
        titleLabel.setFont(new Font("Helvetica", Font.PLAIN, 68));
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
                    difficulty = "Easy";
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
                    difficulty = "Medium";
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
                    difficulty = "Hard";
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
        
        gameModeTitle.setFont(new Font("Arial", Font.PLAIN, 42));
        gameModeTitle.setHorizontalAlignment(JLabel.CENTER);

        GameMode.setOpaque(false);

        GameMode.add(gameModeTitle,GameDifficultyGBC);

        GameDifficultyGBC.insets = new Insets(7, 96, 7, 96);
        GameDifficultyGBC.ipady = 15;
        GameMode.add(selectBtn,GameDifficultyGBC);
        GameMode.add(randomBtn,GameDifficultyGBC);
        GameDifficultyGBC.insets.left = 132;
        GameDifficultyGBC.insets.right = 132;
        GameMode.add(gameModeBackBtn,GameDifficultyGBC);

        for(JButton btn : new JButton[]{selectBtn, randomBtn, gameModeBackBtn}){
            btn.setFont(btnFont);
            btn.setFocusPainted(false);
        }


        selectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                RootGBC.anchor = GridBagConstraints.NORTH;
                RootGBC.weighty = 1;
                self.GameUI.remove(GameMode);
                self.GameUI.add(GameTopics);
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
                RootGBC.anchor = GridBagConstraints.NORTH;
                RootGBC.weighty = 0;
                self.GameUI.add(GameTopics);
                GameUILayout.setConstraints(GameUI, RootGBC);
                self.GameUI.revalidate();
                self.GameUI.repaint();
                self.revalidate();
                self.repaint();
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

        final JPanel topicsTitlePanel = new JPanel();
        final GridBagLayout topicsTitlePanelLayout = new GridBagLayout();
        final GridBagConstraints topicsTitlePanelGBC = new GridBagConstraints();
        final GridBagLayout topicsBodyLayout = new GridBagLayout();
        final GridBagConstraints topicsBodyGBC = new GridBagConstraints();
        final JPanel topicsBodyPanel = new JPanel();
        final JLabel topicsTitle = new JLabel("PairUP");
        ArrayList<JPanel> topics = new ArrayList<JPanel>();
        


        topicsTitlePanel.setLayout(topicsTitlePanelLayout);
        topicsTitlePanel.setOpaque(false);
        topicsTitlePanel.add(topicsTitle, topicsTitlePanelGBC);
        topicsTitle.setFont(new Font("Arial", Font.PLAIN, 44));
        topicsBodyPanel.setLayout(topicsBodyLayout);
        topicsBodyPanel.setOpaque(false);

        GameTopics.setLayout(GameTopicsLayout);
        GameTopics.setOpaque(false);
        

        GameTopicsGBC.anchor = GridBagConstraints.PAGE_START;
        GameTopicsGBC.fill = GridBagConstraints.BOTH;
        GameTopicsGBC.gridwidth = GridBagConstraints.REMAINDER;
        GameTopicsGBC.insets = new Insets(24, 0, 36, 0);
        

        GameTopics.add(topicsTitlePanel, GameTopicsGBC);
        GameTopicsGBC.insets = new Insets(0,0,0,0);

        topicsBodyGBC.anchor = GridBagConstraints.NORTH;
        topicsBodyGBC.insets = new Insets(7,10,7,10);
        final Set<String> gameTopics = GameInfo.gameTopics.keySet();
        System.out.println(gameTopics.size());
        int i = 0;
        
        for(Object key : gameTopics){
            if(((i+1) % 4) == 0)
                topicsBodyGBC.gridwidth = GridBagConstraints.REMAINDER;
            else
                topicsBodyGBC.gridwidth = 1;
            final int j = i;
            final JPanel sampleTopic = new JPanel();
            final int
                scWidth = screenSize.width,
                scHeight = screenSize.height;
            final String finalKey = (String) key;
            sampleTopic.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub
                    super.mouseClicked(e);
                    self.GameUI.remove(GameTopics);
                    topic = (String) finalKey;
                    
                    // RootGBC.weighty = 0;
                    RootGBC.anchor = GridBagConstraints.CENTER;
                    
                    
                    int[] gridSize = GameInfo.gameDifficulty.get(difficulty);
                    GridBagLayout newLayout = new GridBagLayout();
                    GridBagConstraints gbc = new GridBagConstraints();
                    final JPanel game = new JPanel();
                    gbc.anchor = GridBagConstraints.CENTER;
                    game.setOpaque(false);
                    game.setLayout(newLayout);
                    final JPanel titlePanel = new JPanel();
                    final JPanel rightPanel = new JPanel();
                    final JButton pauseBtn = new JButton("Pause");
                    titlePanel.setOpaque(false);
                    titlePanel.setLayout(new BorderLayout());
                    titlePanel.add(new JLabel("test"), BorderLayout.SOUTH);
                    
                    rightPanel.setOpaque(false);
                    rightPanel.setLayout(new BorderLayout());
                    
                    rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
                    rightPanel.add(pauseBtn);

                    final JPanel pausePanel = new JPanel();
                    final JPanel pausePanelBtns = new JPanel();

                    final GridBagLayout pausePanelBtnsLayout = new GridBagLayout();
                    final GridBagConstraints pausePanelBtnsGBC = new GridBagConstraints();
                    pausePanelBtns.setLayout(pausePanelBtnsLayout);
                    final JButton playBtn = new JButton("Play");
                    final JButton restartBtn = new JButton("Restart");
                    final JButton gameExitBtn = new JButton("Menu");

                    for(JButton btn: new JButton[]{playBtn,restartBtn,gameExitBtn}){
                        pausePanelBtns.add(btn);
                    }
                    gameExitBtn.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            MainGame.removeAll();
                            self.GameUI.remove(MainGame);
                            self.GameUI.add(GameMenu, GameUIGBC);
                            
                            RootGBC.anchor = GridBagConstraints.CENTER;
                            self.GameUILayout.setConstraints(GameMenu, RootGBC);
                            self.revalidate();
                            self.repaint();
                        }
                    });

                    pausePanel.setOpaque(false);
                    GridBagLayout pausePanelLayout = new GridBagLayout();
                    GridBagConstraints pausePanelGBC = new GridBagConstraints();
                    pausePanel.setLayout(pausePanelLayout);
                    pausePanelGBC.anchor = GridBagConstraints.CENTER;
                    final JLabel pauseHeading = new JLabel("Game Paused");
                    pauseHeading.setFont(new Font("Arial", Font.PLAIN, 42));

                    pausePanelGBC.gridwidth = GridBagConstraints.REMAINDER;

                    pausePanel.add(pauseHeading,pausePanelGBC);
                    pausePanel.add(pausePanelBtns, pausePanelGBC);
                    pausePanel.setPreferredSize(new Dimension(900, 700));
                    pauseBtn.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e){
                            
                            if(pauseBtn.getText().equals("Pause")){
                                MainGame.remove(game);
                                MainGame.add(pausePanel, BorderLayout.CENTER);
                                pauseBtn.setText("Play");
                            }
                            else{
                                MainGame.remove(pausePanel);
                                MainGame.add(game, BorderLayout.CENTER);
                                pauseBtn.setText("Pause");
                            }
                            MainGame.revalidate();
                            MainGame.repaint();
                        }
                    });

                    final HashSet<JButton> validated = new HashSet<JButton>();
                    MainGame.setLayout(new BorderLayout());
                    MainGame.add(titlePanel,BorderLayout.WEST);
                    gbc.fill = GridBagConstraints.NONE;
                    gbc.gridwidth = GridBagConstraints.REMAINDER;
                    gbc.insets = new Insets(5, 5, 5, 5);
                    MainGame.setPreferredSize(new Dimension(800, 700));
                    final int[] cardSize = GameInfo.gameSizes.get(difficulty);
                    final ImageIcon cardBack = new ImageIcon(new ImageIcon(Paths.get("").toAbsolutePath().normalize().toString() + "/src/main/java/com/test/card_back.png").getImage().getScaledInstance(cardSize[0], cardSize[1], Image.SCALE_SMOOTH));
                    ArrayList<JButton> deck = new ArrayList<JButton>();
                    final String[] topicDeck = GameInfo.gameTopics.get(topic);
                    
                    for(int i = 0, max = (gridSize[0] * gridSize[1]); i< max;i+= 2){
                        final JButton gameCard = new JButton(cardBack);
                        final JButton gameCard2 = new JButton(cardBack);
                        gameCard.setPreferredSize(new Dimension(cardSize[0],cardSize[1]));
                        ;
                        gameCard2.setPreferredSize(new Dimension(cardSize[0],cardSize[1]));
                        ;
                        final String cardDef = topicDeck[i];
                        final ImageIcon cardPic = new ImageIcon(new ImageIcon(Paths.get("").toAbsolutePath().normalize().toString() + "/src/main/java/com/test/asset/" + topic + "/" + topicDeck[i+1] ).getImage().getScaledInstance(cardSize[0], cardSize[1], Image.SCALE_SMOOTH));
                        gameCard.addActionListener(
                            new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    if(validated.contains(gameCard))
                                        return;
                                    if(lastPicked == null){
                                        lastPicked = gameCard;
                                        gameCard.setIcon(null);
                                        gameCard.setText(cardDef);
                                        return;
                                    }
                                    if(lastPicked == gameCard){
                                        return;
                                    }
                                    if(lastPicked == gameCard2){
                                        gameCard.setIcon(null);
                                        gameCard.setText(cardDef);
                                        gameCard.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1, true));
                                        gameCard2.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1, true));
                                        validated.add(gameCard);
                                        validated.add(gameCard2);
                                        lastPicked = null;
                                        if(validated.size() == topicDeck.length){

                                        }
                                        return;
                                    }
                                    lastPicked.removeAll();
                                    lastPicked.setText(null);
                                    lastPicked.setIcon(cardBack);
                                    lastPicked = null;
                                };
                            }
                        );
                        gameCard2.addActionListener(
                            new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    if(validated.contains(gameCard2))
                                        return;
                                    if(lastPicked == null){
                                        lastPicked = gameCard2;
                                        gameCard2.setText(null);
                                        gameCard2.setIcon(cardPic);
                                        return;
                                    }
                                    if(lastPicked == gameCard2){
                                        return;
                                    }
                                    if(lastPicked == gameCard){
                                        gameCard2.setText(null);
                                        gameCard2.setIcon(cardPic);
                                        gameCard.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1, true));
                                        gameCard2.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1, true));
                                        validated.add(gameCard);
                                        validated.add(gameCard2);
                                        lastPicked = null;
                                        if(validated.size() == topicDeck.length){
                                            
                                        }
                                        return;
                                    }
                                    lastPicked.removeAll();
                                    lastPicked.setText(null);
                                    lastPicked.setIcon(cardBack);

                                    lastPicked = null;
                                };
                            }
                        );
                        deck.add(gameCard);
                        deck.add(gameCard2);
                    }
                    Collections.shuffle(deck);
                    for(int i =0, len = deck.size();i < len;i++){
                        if((i+1) % gridSize[0] == 0)
                            gbc.gridwidth = GridBagConstraints.REMAINDER;
                        else
                            gbc.gridwidth = 1;
                        game.add(deck.get(i), gbc);
                    }
                    MainGame.add(game, BorderLayout.CENTER);
                    MainGame.add(rightPanel, BorderLayout.EAST);
                    MainGame.setPreferredSize(new Dimension(900, 700));
                    self.GameUI.add(MainGame, GameUIGBC);
                    self.GameUILayout.setConstraints(GameUI, RootGBC);
                    
                    startTimer = new Timer(1000, new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            gameTime++;
                        }
                    });
                    countdownTimer = new Timer(1000, new ActionListener(){
                        
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(--gameCountdown == 0){
                                startTimer.start();
                                countdownTimer.stop();
                                gameCountdown = 5;
                            }
                        }
                    });
                    self.revalidate();
                    self.repaint();
                    countdownTimer.start();
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub
                    super.mouseEntered(e);
                    sampleTopic.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1, true));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub
                    super.mouseExited(e);
                    sampleTopic.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                }
            });
            sampleTopic.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            sampleTopic.setPreferredSize(new Dimension(230, 290));
            sampleTopic.setLayout(new BorderLayout(2,0));
            final JPanel cardTitlePanel = new JPanel();
            final GridBagLayout cardTitleLayout = new GridBagLayout();
            final GridBagConstraints cardTitleGBC = new GridBagConstraints();
            
            
            cardTitlePanel.setLayout(cardTitleLayout);

            cardTitleGBC.anchor = GridBagConstraints.WEST;
            cardTitleGBC.fill = GridBagConstraints.NONE;
            cardTitleGBC.ipady = 12;
            
            final String cardTitle = (String) key;
            final JLabel cardTitleLabel = new JLabel(cardTitle);
            cardTitleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            cardTitlePanel.add(cardTitleLabel, cardTitleGBC);
            sampleTopic.add(cardTitlePanel, BorderLayout.NORTH);
            sampleTopic.add(new JLabel(bg), BorderLayout.CENTER);
            final JPanel cardInfo = new JPanel();
            final GridBagLayout cardInfoLayout = new GridBagLayout();
            final GridBagConstraints cardInfoConstraints = new GridBagConstraints();
            cardInfoConstraints.fill = GridBagConstraints.HORIZONTAL;
            cardInfoConstraints.anchor = GridBagConstraints.CENTER;
            cardInfoConstraints.ipady = 2;
            cardInfoConstraints.ipadx = 12;
            cardInfo.setLayout(cardInfoLayout);
            final Font infoFont = new Font("Arial", Font.PLAIN, 16);
            final JLabel accuracyJLabel = new JLabel("0% Accuracy");
            accuracyJLabel.setFont(infoFont);
            cardInfo.setPreferredSize(new Dimension(230, 36));
            cardInfo.add(accuracyJLabel, cardInfoConstraints);
            cardInfoConstraints.gridwidth = GridBagConstraints.REMAINDER;
            cardInfoConstraints.ipady = 3;
            cardInfoConstraints.ipadx = 6;
            final JLabel timeJLabel = new JLabel("Best Time 0m0s");
            timeJLabel.setFont(infoFont);
            cardInfo.add(timeJLabel, cardInfoConstraints);
            cardInfoConstraints.gridwidth = 1;
            
            sampleTopic.add(cardInfo, BorderLayout.SOUTH);
            
            topicsBodyPanel.add(sampleTopic, topicsBodyGBC);
            topics.add(sampleTopic);
            i++;
            System.out.println(i);
        }
        GameTopicsGBC.fill = GridBagConstraints.BOTH;
        GameTopicsGBC.gridwidth = GridBagConstraints.RELATIVE;
        
        final JScrollPane topicsBodyScrollPane = new JScrollPane(topicsBodyPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        final JViewport topicsBodyVP = topicsBodyScrollPane.getViewport();
        topicsBodyPanel.setOpaque(false);
        topicsBodyScrollPane.setOpaque(false);
        topicsBodyVP.setOpaque(false);
        topicsBodyScrollPane.setBorder(BorderFactory.createEmptyBorder());
        

        topicsBodyScrollPane.setPreferredSize(new Dimension((int)(0.75f * screenSize.width), (int)(0.7f * screenSize.height)));
        GameTopicsGBC.ipady = 20;
        GameTopics.add(topicsBodyScrollPane, GameTopicsGBC);
        GamePages.put("GameTopics", GameTopics);

        MainGame.setOpaque(false);

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
