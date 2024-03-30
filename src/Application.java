package mateusz.projekt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;

public class Application extends URLWebsite {

    public Application(String url) {
        super(url);
    }

    private final static int defaultWidth = 1200;
    private final static int defaultHeight = 700;

    static ArrayList<GameInfo> ArrGames = new ArrayList<GameInfo>();
    static ArrayList<String> ArrTemp = new ArrayList<String>();
    static ArrayList<GameInfo> ArrGamesFiltered = new ArrayList<GameInfo>();

    private static final Logger log = LogManager.getLogger(Application.class);

    public ArrayList<GameInfo> addNewGame() {

    	Document website = null;
        try {
            website = Jsoup.connect(URL)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
                    .header("Accept", "text/html")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .get();
        } catch (Exception e) {
            log.error("Error while connecting to website: " + e);
            return ArrGames;
        }

        Elements gamesElements = website.select("div[width=1,1,0.5,0.5,0.3333333333333333]");
        Elements li = website.select("li.sc-vb9gxz-2");
        log.info("li.size() = " + li.size());

        int liCouterLoop = 0;
        for (Element el : gamesElements) {

            log.info("Adding new game to list");
            //log.info("liCouterLoop = " + liCouterLoop);    // spam w logach
            GameInfo game = new GameInfo();

            game.setImgURL(el.select("img").tagName("img").attr("src").replace("product-mini", "product-medium"));

            game.setName(el.select("div.sc-3g60u5-0:has(>a>h3)").text());
            game.setPrice(el.select("div.sc-1yu46qn-15:has(>div>div)").text());

            for (int i = liCouterLoop; i < (liCouterLoop + 4); i++) {
                // if li element is not empty, check if it is pegi, mode or type
                if (!li.get(i).text().isEmpty()) {

                    // if li element contains "Platforma", it is platform
                    if (li.get(i).text().contains("Platforma:")) {
                        game.setPlatform(li.get(i).text());
                    }
                    // if li element contains "Dystrybucja", it is dystrybution
                    else if (li.get(i).text().contains("Dystrybucja")) {
                        game.setDistribution(li.get(i).text());
                    }
                    // if li element contains "Wersja", it is language_version
                    else if (li.get(i).text().contains("Wersja:")) {
                        game.setLanguage_version(li.get(i).text());
                    }
                    // if li element contains "PEGI", it is pegi
                    else if (li.get(i).text().contains("PEGI")) {
                        game.setPegi(li.get(i).text());
                    }
                    // if li element contains "Tryb", it is mode
                    else if (li.get(i).text().contains("Tryb")) {
                        game.setMode(li.get(i).text());
                    }
                    // if li element contains "Premiera", it is premier
                    else if (li.get(i).text().contains("Premiera:")) {
                        game.setPremier(li.get(i).text());
                    }
                } else if (li.get(i).text().isEmpty()) {
                    log.error("li.get(i).text() is empty");
                }
            }

            liCouterLoop += 4;
            ArrGames.add(game);
        }
        log.info("all games: " + ArrGames.size() + "");
        return ArrGames;
    }

    public static void generateURL(String page) {
        URLWebsite.URL = "https://www.x-kom.pl/g-7/c/1686-gry-na-pc.html?page=" + page;
    }

    public static void showGames() {

        for (GameInfo game : ArrGames) {

            // if the attribute is not null or empty, print it
            if (game.getImgURL() != null && !game.getImgURL().isEmpty()) {
                log.info(game.getImgURL());
            }
            if (game.getName() != null && !game.getName().isEmpty()) {
                log.info(game.getName());
            }
            if (game.getPrice() != null && !game.getPrice().isEmpty()) {
                log.info(game.getPrice());
            }
            if (game.getPlatform() != null && !game.getPlatform().isEmpty()) {
                log.info(game.getPlatform());
            }
            if (game.getDistribution() != null && !game.getDistribution().isEmpty()) {
                log.info(game.getDistribution());
            }
            if (game.getLanguage_version() != null && !game.getLanguage_version().isEmpty()) {
                log.info(game.getLanguage_version());
            }
            if (game.getPegi() != null && !game.getPegi().isEmpty()) {
                log.info(game.getPegi());
            }
            if (game.getMode() != null && !game.getMode().isEmpty()) {
                log.info(game.getMode());
            }
            if (game.getPremier() != null && !game.getPremier().isEmpty()) {
                log.info(game.getPremier());
            }
            log.info("");
        }
    }

    public static String[] createListPegi() {

        for (GameInfo games : ArrGames) {
            if (games.getPegi() != null) {
                ArrTemp.add(games.getPegi());
            }
        }

        Set<String> hs = new HashSet<>();
        hs.addAll(ArrTemp);
        ArrTemp.clear();
        ArrTemp.addAll(hs);
        int SizeArrTemp = ArrTemp.size();

        String[] pegi = new String[SizeArrTemp + 1];
        pegi[0] = "Wszystkie";
        for (int i = 1; i <= SizeArrTemp; i++) {
            pegi[i] = ArrTemp.get(i - 1);
        }
        ArrTemp.clear();
        log.info("listPegi created");
        return pegi;
    }

    public static String[] createListDystrybution() {

        for (GameInfo games : ArrGames) {
            if (games.getDistribution() != null) {
                ArrTemp.add(games.getDistribution());
            }
        }

        Set<String> hs = new HashSet<>();
        hs.addAll(ArrTemp);
        ArrTemp.clear();
        ArrTemp.addAll(hs);
        int SizeArrTemp = ArrTemp.size();

        String[] dystrybution = new String[SizeArrTemp + 1];
        dystrybution[0] = "Wszystkie";
        for (int i = 1; i <= SizeArrTemp; i++) {
            dystrybution[i] = ArrTemp.get(i - 1);
        }
        ArrTemp.clear();
        log.info("listDystrybution created");
        return dystrybution;
    }

    public static String[] createListPrice() {

        for (GameInfo games : ArrGames) {
            if (games.getPrice() != null) {
                ArrTemp.add(games.getPrice());
            }
        }

        Set<String> hs = new HashSet<>();
        hs.addAll(ArrTemp);
        ArrTemp.clear();
        ArrTemp.addAll(hs);
        int SizeArrTemp = ArrTemp.size();

        String[] price = new String[SizeArrTemp + 1];
        price[0] = "Wszystkie";
        for (int i = 1; i <= SizeArrTemp; i++) {
            price[i] = ArrTemp.get(i - 1);
        }
        ArrTemp.clear();
        log.info("listPrice created\n");
        return price;
    }

    protected static ArrayList<GameInfo> filterGames(String selectedPegi, String selectedDystrybution, String selectedPrice) {

        // function to filter games by selected filters
        ArrGamesFiltered.clear();
        log.info("[ CLEAR ] Clearing ArrGamesFiltered");
        log.info("Size of ArrGamesFiltered: " + ArrGamesFiltered.size());
        log.info("Size of ArrGames: " + ArrGames.size());
        log.info("Filtering games\n");
        log.info("[ INFO ] .filterGames()");
        log.info("selectedPegi: " + selectedPegi);
        log.info("selectedDystrybution: " + selectedDystrybution);
        log.info("selectedPrice: " + selectedPrice + "\n");

        for (int i = 0; i < ArrGames.size(); i++) {

            if (ArrGames.get(i).getPegi() == null || ArrGames.get(i).getPegi().isEmpty()) {
                ArrGames.get(i).setPegi("Brak PEGI");
            }
            if (ArrGames.get(i).getDistribution() == null || ArrGames.get(i).getDistribution().isEmpty()) {
                ArrGames.get(i).setDistribution("Brak danych o dystrybucji");
            }
            if (ArrGames.get(i).getPrice() == null || ArrGames.get(i).getPrice().isEmpty()) {
                ArrGames.get(i).setPrice("Brak");
            }

            if (selectedPegi.equals("Wszystkie") && selectedDystrybution.equals("Wszystkie") && selectedPrice.equals("Wszystkie")) {
                ArrGamesFiltered.add(ArrGames.get(i));
            } else if (selectedPegi.equals("Wszystkie") && selectedDystrybution.equals("Wszystkie") && !selectedPrice.equals("Wszystkie")) {
                if (ArrGames.get(i).getPrice().equals(selectedPrice)) {
                    ArrGamesFiltered.add(ArrGames.get(i));
                }
            } else if (selectedPegi.equals("Wszystkie") && !selectedDystrybution.equals("Wszystkie") && selectedPrice.equals("Wszystkie")) {
                if (ArrGames.get(i).getDistribution().equals(selectedDystrybution)) {
                    ArrGamesFiltered.add(ArrGames.get(i));
                }
            } else if (selectedPegi.equals("Wszystkie") && !selectedDystrybution.equals("Wszystkie") && !selectedPrice.equals("Wszystkie")) {
                if (ArrGames.get(i).getDistribution().equals(selectedDystrybution) && ArrGames.get(i).getPrice().equals(selectedPrice)) {
                    ArrGamesFiltered.add(ArrGames.get(i));
                }
            } else if (!selectedPegi.equals("Wszystkie") && selectedDystrybution.equals("Wszystkie") && selectedPrice.equals("Wszystkie")) {
                if (ArrGames.get(i).getPegi().equals(selectedPegi)) {
                    ArrGamesFiltered.add(ArrGames.get(i));
                }
            } else if (!selectedPegi.equals("Wszystkie") && selectedDystrybution.equals("Wszystkie") && !selectedPrice.equals("Wszystkie")) {
                if (ArrGames.get(i).getPegi().equals(selectedPegi) && ArrGames.get(i).getPrice().equals(selectedPrice)) {
                    ArrGamesFiltered.add(ArrGames.get(i));
                }
            } else if (!selectedPegi.equals("Wszystkie") && !selectedDystrybution.equals("Wszystkie") && selectedPrice.equals("Wszystkie")) {
                if (ArrGames.get(i).getPegi().equals(selectedPegi) && ArrGames.get(i).getDistribution().equals(selectedDystrybution)) {
                    ArrGamesFiltered.add(ArrGames.get(i));
                }
            } else if (!selectedPegi.equals("Wszystkie") && !selectedDystrybution.equals("Wszystkie") && !selectedPrice.equals("Wszystkie")) {
                if (ArrGames.get(i).getPegi().equals(selectedPegi) && ArrGames.get(i).getDistribution().equals(selectedDystrybution) && ArrGames.get(i).getPrice().equals(selectedPrice)) {
                    ArrGamesFiltered.add(ArrGames.get(i));
                }
            }
        }
        return ArrGamesFiltered;
    }

    public void createAndShowGUI() {

        log.info("Creating GUI");

        Application.generateURL("1");
        URLWebsite website_x_kom = new Application(getURL());
        log.info("connecting to: " + getURL());
        log.info("getting games from website");
        ArrGames = website_x_kom.addNewGame();

//        Application.generateURL("2");
//        website_x_kom = new Application(getURL());
//        log.info("connecting to: " + getURL());
//        log.info("getting games from website");
//        ArrGames = website_x_kom.addNewGame();

        Application.generateURL("3");
        website_x_kom = new Application(getURL());
        log.info("connecting to: " + getURL());
        log.info("getting games from website");
        ArrGames = website_x_kom.addNewGame();

//        Application.generateURL("4");
//        website_x_kom = new Application(getURL());
//        log.info("connecting to: " + getURL());
//        log.info("getting games from website");
//        ArrGames = website_x_kom.addNewGame();

        log.info("creating lists for filters");
        final JComboBox<String> jListPegi = new JComboBox<String>(Application.createListPegi());
        final JComboBox<String> jListDistribution = new JComboBox<String>(Application.createListDystrybution());
        final JComboBox<String> jListPrice = new JComboBox<String>(Application.createListPrice());

        jListPegi.setBackground(Color.decode("#333333"));
        jListPegi.setForeground(Color.GRAY);
        jListDistribution.setBackground(Color.decode("#333333"));
        jListDistribution.setForeground(Color.GRAY);
        jListPrice.setBackground(Color.decode("#333333"));
        jListPrice.setForeground(Color.GRAY);

        // Application.showGames();    // in logs show all games

        JFrame frame = new JFrame("x-kom Games");
        frame.setSize(defaultWidth, defaultHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.decode("#282A31"));
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font(null, Font.BOLD, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText("Witaj w aplikacji do wyszukiwania gier na platformie x-kom. Wybierz kategorię wiekową, dystrybucję oraz cenę, aby wyświetlić gry spełniające te kryteria.");
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new FlowLayout());
        panelMenu.setPreferredSize(new Dimension(400, 50));
        panelMenu.setBackground(Color.decode("#282A31"));

        JTextField textKategoriaWiekowa = new JTextField();
        textKategoriaWiekowa.setText("Kategoria wiekowa: ");
        textKategoriaWiekowa.setPreferredSize(new Dimension(150, 40));
        textKategoriaWiekowa.setBackground(Color.decode("#333333"));
        textKategoriaWiekowa.setForeground(Color.WHITE);
        textKategoriaWiekowa.setEditable(false);

        JTextField textDistribution = new JTextField();
        textDistribution.setText("Dystrybucja: ");
        textDistribution.setPreferredSize(new Dimension(150, 40));
        textDistribution.setBackground(Color.decode("#333333"));
        textDistribution.setForeground(Color.WHITE);
        textDistribution.setEditable(false);

        JTextField textPrice = new JTextField();
        textPrice.setText("Cena: ");
        textPrice.setPreferredSize(new Dimension(150, 40));
        textPrice.setBackground(Color.decode("#333333"));
        textPrice.setForeground(Color.WHITE);
        textPrice.setEditable(false);

        JScrollPane scrollPanePegi = new JScrollPane(jListPegi);
        scrollPanePegi.setPreferredSize(new Dimension(150, 40));
        scrollPanePegi.setBackground(Color.GRAY);
        scrollPanePegi.setForeground(Color.WHITE);
        scrollPanePegi.setFont(new Font(null, Font.BOLD, 14));

        JScrollPane scrollPaneDistribution = new JScrollPane(jListDistribution);
        scrollPaneDistribution.setPreferredSize(new Dimension(250, 40));
        scrollPaneDistribution.setBackground(Color.GRAY);
        scrollPaneDistribution.setForeground(Color.WHITE);
        scrollPaneDistribution.setFont(new Font(null, Font.BOLD, 14));

        JScrollPane scrollPanePrice = new JScrollPane(jListPrice);
        scrollPanePrice.setPreferredSize(new Dimension(150, 40));
        scrollPanePrice.setBackground(Color.GRAY);
        scrollPanePrice.setForeground(Color.WHITE);
        scrollPanePrice.setFont(new Font(null, Font.BOLD, 14));

        JButton button = new JButton("Filtruj");
        button.setPreferredSize(new Dimension(120, 40));
        button.setBackground(Color.decode("#3399FF"));
        button.setForeground(Color.BLACK);
        button.setFont(new Font(null, Font.BOLD, 14));

        JPanel panelText = new JPanel();
        panelText.setLayout(new BorderLayout());

        JTextPane textPane = new JTextPane();
        textPane.setLayout(new BorderLayout());
        textPane.setBackground(Color.WHITE);
        textPane.setForeground(Color.WHITE);
        textPane.setFont(new Font(null, Font.BOLD, 14));
        textPane.setPreferredSize(new Dimension(defaultWidth, defaultHeight));

        JTextArea zawartosc_info_gra = new JTextArea();
        zawartosc_info_gra.setEditable(false);
        zawartosc_info_gra.setBackground(Color.decode("#282A31"));
        zawartosc_info_gra.setForeground(Color.WHITE);
        zawartosc_info_gra.setFont(new Font(null, Font.BOLD, 14));
        zawartosc_info_gra.setLineWrap(true);
        zawartosc_info_gra.setWrapStyleWord(true);

        JScrollPane scrollPaneContentGame = new JScrollPane(zawartosc_info_gra);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.info("[ Button ] - <Filtruj> - clicked");
                zawartosc_info_gra.setText("");
                log.info("[ CLEAR ] Cleared text area");

                // PEGI
                String selectedPegi = (String) jListPegi.getSelectedItem();
                log.info("Selected PEGI: " + selectedPegi);
                // dysytrybution
                String selectedDystrybution = (String) jListDistribution.getSelectedItem();
                log.info("Selected Dystrybution: " + selectedDystrybution);
                // price
                String selectedPrice = (String) jListPrice.getSelectedItem();
                log.info("Selected Price: " + selectedPrice);

                ArrGamesFiltered = filterGames(selectedPegi, selectedDystrybution, selectedPrice);
                log.info("ArrGamesFiltered size: " + ArrGamesFiltered.size());
                // log.info("ArrGamesFiltered: " + ArrGamesFiltered);

                for (GameInfo game : ArrGamesFiltered) {

                    if (game.getImgURL() != null && !game.getImgURL().isEmpty()) {
                        zawartosc_info_gra.append(game.getImgURL());
                        zawartosc_info_gra.append("\n");
                    }
                    if (game.getName() != null && !game.getName().isEmpty()) {
                        zawartosc_info_gra.append(game.getName());
                        zawartosc_info_gra.append("\n");
                    }
                    if (game.getPrice() != null && !game.getPrice().isEmpty()) {
                        zawartosc_info_gra.append(game.getPrice());
                        zawartosc_info_gra.append("\n");
                    }
                    if (game.getPlatform() != null && !game.getPlatform().isEmpty()) {
                        zawartosc_info_gra.append(game.getPlatform());
                        zawartosc_info_gra.append("\n");
                    }
                    if (game.getDistribution() != null && !game.getDistribution().isEmpty()) {
                        zawartosc_info_gra.append(game.getDistribution());
                        zawartosc_info_gra.append("\n");
                    }
                    if (game.getLanguage_version() != null && !game.getLanguage_version().isEmpty()) {
                        zawartosc_info_gra.append(game.getLanguage_version());
                        zawartosc_info_gra.append("\n");
                    }
                    if (game.getPegi() != null && !game.getPegi().isEmpty()) {
                        zawartosc_info_gra.append(game.getPegi());
                        zawartosc_info_gra.append("\n");
                    }
                    if (game.getMode() != null && !game.getMode().isEmpty()) {
                        zawartosc_info_gra.append(game.getMode());
                        zawartosc_info_gra.append("\n");
                    }
                    if (game.getPremier() != null && !game.getPremier().isEmpty()) {
                        zawartosc_info_gra.append(game.getPremier());
                        zawartosc_info_gra.append("\n");
                    }
                    zawartosc_info_gra.append("\n");
                }
            }
        });

        panelMenu.add(textKategoriaWiekowa);
        panelMenu.add(scrollPanePegi);
        panelMenu.add(textDistribution);
        panelMenu.add(scrollPaneDistribution);
        panelMenu.add(textPrice);
        panelMenu.add(scrollPanePrice);
        panelMenu.add(button);

        panelText.add(textPane, BorderLayout.CENTER);
        textPane.add(scrollPaneContentGame, BorderLayout.CENTER);

        frame.add(textArea, BorderLayout.NORTH);
        frame.add(panelMenu, BorderLayout.CENTER);
        frame.add(panelText, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                log.info("closing application");
            }
        });

        if (ArrGames.size() == 0) {
            log.info("ArrGames.size() == 0");
            textArea.setText("Brak danych do wyświetlenia");
        } else {
            log.info("ArrGames.size() != 0");
            textArea.setText("Wczytano dane z: " + getURL());
        }
    }

    public static void main(String[] args) throws IOException {

        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        org.apache.logging.log4j.core.config.Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        loggerConfig.setLevel(Level.DEBUG);
        ctx.updateLoggers();

        log.info("Starting application");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Application(getURL()).createAndShowGUI();
                ;
            }
        });
    }
}
