package org.example.landoflustrous.view;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import org.example.landoflustrous.controller.GameOverController;
import org.example.landoflustrous.controller.ScoreBoardController;
import org.example.landoflustrous.model.*;
import org.example.landoflustrous.service.NavigationService;

import java.io.IOException;
import java.util.*;

public class MapViewerScene {
    private GameMap gameMap;
    private VBox root;
    private Pane upPart;
    private Stage stage;
    private Scene scene;
    private static final int TILE_SIZE = 20;
    private PlayerCharacter player;
    private ImageView playerImage;
    private Gem gem;
    private ImageView gemImageView;
    private List<Route> routeList;
    private HBox statusBoard;
    private Label nameLabel; // 显示名字
    private Label carbonLabel; // 显示碳足迹
    private Label scoreLabel; // 显示分数
    private Label gemCountLabel; // 显示宝石数量
    private Label timeRemainingLabel;
    private Timeline gameTimer;
    private int gameTimeRemaining = 60;  // 总游戏时间60秒
    private boolean continueGeneratingGems = true;
    NavigationService navigationService;
    String levelIdentifier;

    private static final Map<String, Map<String, Object>> levelPathMapping = new HashMap<>();

    static {
        levelPathMapping.put("Level 1", Map.of(
                "map", "/maps/map1/level1/map.txt",
                "rail", List.of("/maps/map1/level1/rail.txt"),
                "bus", List.of("/maps/map1/level1/bus1.txt", "/maps/map1/level1/bus2.txt")));
        levelPathMapping.put("Level 2", Map.of(
                "map", "/maps/map1/level2/map.txt",
                "rail", List.of("/maps/map1/level2/rail1.txt", "/maps/map1/level2/rail2.txt"),
                "bus", List.of("/maps/map1/level2/bus1.txt", "/maps/map1/level2/bus2.txt")));
    }

    public MapViewerScene(Stage stage, String levelIdentifier, String playerName) throws IOException {
        this.stage = stage;
        this.levelIdentifier = levelIdentifier;

        Map<String, Object> paths = levelPathMapping.get(levelIdentifier);
        String mapPath = (String) paths.get("map");
        List<String> railPaths = (List<String>) paths.get("rail");
        List<String> busPaths = (List<String>) paths.get("bus");


        this.gameMap = new GameMap(mapPath, railPaths, busPaths);
        int gameWidth = gameMap.getWidth();
        int gameHeight = gameMap.getHeight();

        initGameTimer(levelIdentifier);

        //创建VBOX
        root = new VBox();
        upPart = new Pane();

        //根据关卡id绘制地图于Pane
        drawMap(upPart, levelIdentifier);

        //创建玩家于Pane
        addPlayerCharacter(upPart, playerName);
        //随机生成一颗宝石
        createRandomGem(upPart, gameWidth, gameHeight);


        //把pane加入vbox上
        root.getChildren().addAll(upPart);

        //创建玩家状态栏于vbox
        createStatusBoard(root, player);

        //画地图网格方便观察
        drawTileBorders(upPart, gameMap.getWidth(), gameMap.getHeight());

        // 引入CSS样式
        root.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        this.scene = new Scene(root);

    }

    private void initGameTimer(String levelIdentifier) {
        gameTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            gameTimeRemaining--;
            timeRemainingLabel.setText("Time Remaining: " + gameTimeRemaining + "s"); // Update the timer display

            if (gameTimeRemaining == 0 & player.getGemNumber() >= 5) {
                switchToScoreBoard(levelIdentifier);
            }
            if (gameTimeRemaining == 0 & player.getGemNumber() < 5) {
                handleGameOver(levelIdentifier);
            }

            if (player.getCarbonHP() <= 0) {
                gameTimer.stop();
                handleGameOver(levelIdentifier);
            }
        }));
        gameTimer.setCycleCount(Timeline.INDEFINITE);
        gameTimer.play();
    }

    private void switchToScoreBoard(String levelIdentifier) {
        // Parameters could include current game stats like carbon footprint, gem score, etc.
        int currentCarbon = player.getCarbonHP();
        int gemScore = player.getGemScore();
        int gemCount = player.getGemNumber();

        ScoreBoardController scoreBoardController = new ScoreBoardController(stage);
        ScoreBoardScene scoreBoardScene = new ScoreBoardScene(player.getName(), stage, currentCarbon, gemCount, gemScore, scoreBoardController);
        stage.setScene(scoreBoardScene.getScene());
        stage.show();
    }


    private void handleGameOver(String levelIdentifier) {
        int currentCarbon = player.getCarbonHP();
        int gemScore = player.getGemScore();
        int gemCount = player.getGemNumber();

        GameOverController gameOverController = new GameOverController(stage);
        GameOverScene gameOverScene = new GameOverScene(player.getName(), stage, currentCarbon, gemCount, gemScore, gameOverController);
        stage.setScene(gameOverScene.getScene());
        stage.show();


    }

    public Scene getScene() {
        return this.scene;
    }


    private void drawMap(Pane root, String levelIdentifier) {
        int tempLevel = Integer.parseInt(levelIdentifier.replaceAll("[^0-9]", ""));
        String pathToMap = "/images/map_level" + tempLevel + ".png";
        Image mapImage = new Image(getClass().getResourceAsStream(pathToMap));
        ImageView mapView = new ImageView(mapImage);
        mapView.setFitWidth(1000);
        mapView.setFitHeight(600);
        root.getChildren().add(mapView);
    }


    public void addPlayerCharacter(Pane root, String playerName) {

        Image image = new Image(getClass().getResourceAsStream("/images/player.png"));
        if (image.isError()) {
            throw new RuntimeException("Error loading player image.");
        }

        this.player = new PlayerCharacter(0, 0, playerName);

        // 创建玩家的 ImageView，并设置图像。
        this.playerImage = new ImageView(image);
        playerImage.setFitWidth(20); // 设置图像视图的宽度以适应地图格子的尺寸。
        playerImage.setFitHeight(25);

        // 将玩家图像添加到根面板中。
        root.getChildren().add(playerImage);
    }


    private void createStatusBoard(VBox root, PlayerCharacter player) {
        this.statusBoard = new HBox(40); // 调整间距以更好地显示所有信息
        statusBoard.setStyle("-fx-background-color: #FFFFFF;");
        statusBoard.setPrefHeight(50); // 设置HBox的预设高度为50像素

        nameLabel = new Label("Name: " + player.getName());
        carbonLabel = new Label("Carbon Footprint: " + player.getCarbonHP());
        scoreLabel = new Label("Gem Score: " + player.getScore());
        gemCountLabel = new Label("Gems Collected: " + player.getGemNumber());
        timeRemainingLabel = new Label("Time Remaining: " + gameTimeRemaining + "s"); // 显示剩余时间

        nameLabel.getStyleClass().add("label_map");
        carbonLabel.getStyleClass().add("label_map");
        scoreLabel.getStyleClass().add("label_map");
        gemCountLabel.getStyleClass().add("label_map");
        timeRemainingLabel.getStyleClass().add("label_map");

        statusBoard.getChildren().addAll(nameLabel, carbonLabel, scoreLabel, gemCountLabel, timeRemainingLabel);
        root.getChildren().add(statusBoard);
    }

    private void createRandomGem(Pane root, int gameWidth, int gameHeight) {
        if (gameTimeRemaining > 0) {
            Random random = new Random();
            int gem_x = random.nextInt(gameWidth);
            int gem_y = random.nextInt(gameHeight);

            this.gem = new Gem(gem_x, gem_y);
            String gemType = gem.getType();
            int liveTime = gem.getLiveTime();

            Image gemImage = new Image(getClass().getResourceAsStream("/images/" + gemType + ".gif"));
            this.gemImageView = new ImageView(gemImage);
            gemImageView.setX(gem_x * TILE_SIZE);
            gemImageView.setY(gem_y * TILE_SIZE);
            gemImageView.setFitWidth(30);
            gemImageView.setFitHeight(30);

            // 创建宝石消失的计时器
            Timeline disappearTimeline = new Timeline(new KeyFrame(Duration.seconds(liveTime), e -> {
                upPart.getChildren().remove(gemImageView);
                scheduleNextGem(root, gameWidth, gameHeight);
            }));

            // 宝石点击事件
            gemImageView.setOnMouseClicked(event -> {
                continueGeneratingGems = false;
                handleGemClick(event, root, gemType, liveTime);
                disappearTimeline.stop();
                scheduleNextGem(root, gameWidth, gameHeight);
            });

            root.getChildren().add(gemImageView);
            disappearTimeline.play();
        }

        //获得路线选项
        this.navigationService = new NavigationService(gameMap);
        this.routeList = navigationService.navigate(player, gem);
        System.out.println(routeList);


    }


    private void scheduleNextGem(Pane root, int gameWidth, int gameHeight) {
        if (continueGeneratingGems) {
            Random random = new Random();
            int delay = 1 + random.nextInt(3);  // 生成2-4秒的随机延迟
            new Timeline(new KeyFrame(Duration.seconds(delay), e -> createRandomGem(root, gameWidth, gameHeight))).play();
        }
    }

    // 宝石点击事件的方法
    private void handleGemClick(MouseEvent event, Pane root, String gemType, int liveTime) {
        //创建路线选项卡
        VBox optionBoard = createOptionBoard(routeList, gem);
        optionBoard.layoutXProperty().bind(upPart.widthProperty().subtract(optionBoard.widthProperty()).divide(2));
        optionBoard.layoutYProperty().bind(upPart.heightProperty().subtract(optionBoard.heightProperty()).divide(2));

        upPart.getChildren().add(optionBoard);
    }


    //绘制每个tile的图层
    private void drawTileBorders(Pane root, int gameWidth, int gameHeight) {
        for (int x = 0; x < gameWidth; x++) {
            for (int y = 0; y < gameHeight; y++) {
                Rectangle border = new Rectangle(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                border.setFill(null); // 不填充颜色，只显示边框
                border.setStroke(Color.web("#00ffff", 0.1)); // 设置边框颜色为白色，半透明
                border.setStrokeWidth(1); // 设置边框宽度
                root.getChildren().add(border);
            }
        }
    }

    private VBox createOptionBoard(List<Route> routeList, Gem gem) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("CHOOSE A ROUTE");
        titleLabel.getStyleClass().add("route-title");
        root.getChildren().add(titleLabel);

        for (int i = 0; i < routeList.size(); i++) {
            Route route = routeList.get(i);
            String routeDetails = "Route " + (i + 1) + ": " + route.getTrafficType() +
                    " Time Cost: " + route.getTotalCost() + " Carbon: " + route.getTotalCarbon();

            Button routeButton = new Button(routeDetails);
            routeButton.getStyleClass().add("route-button");
            routeButton.setId("Route" + (i + 1)); // 设置按钮ID

            int finalI = i;
            routeButton.setOnAction(event -> {
                System.out.println("Route " + (finalI + 1) + " selected");
                movePlayerToGem(route);  // 绑定动画触发方法
                upPart.getChildren().removeAll(root);

            });
            root.getChildren().add(routeButton);
        }

        return root;
    }


    private void movePlayerToGem(Route route) {
        List<Path> paths = route.getPathList();
        Queue<Pair<Path, PathTransition>> transitions = new LinkedList<>();

        Tile lastTile = null;

        for (Path path : paths) {
            javafx.scene.shape.Path segmentPath = new javafx.scene.shape.Path();
            List<Tile> tiles = path.getTileList();
            if (tiles.isEmpty()) continue;

            Tile startTile = tiles.get(0);
            lastTile = tiles.get(tiles.size() - 1);
            segmentPath.getElements().add(new MoveTo(startTile.getX() * TILE_SIZE, startTile.getY() * TILE_SIZE));

            for (int i = 1; i < tiles.size(); i++) {
                Tile tile = tiles.get(i);
                segmentPath.getElements().add(new LineTo(tile.getX() * TILE_SIZE, tile.getY() * TILE_SIZE));
            }

            PathTransition transition = new PathTransition();
            transition.setPath(segmentPath);
            transition.setNode(playerImage);
            transition.setInterpolator(Interpolator.LINEAR);
            transition.setDuration(Duration.seconds(10 * calculateSpeedFactor(path.getTrafficType())));

            transitions.add(new Pair<>(path, transition));  // Store Path with its transition
        }

        // Play all transitions in sequence
        playTransitionsSequentially(transitions, lastTile);
    }

    private void playTransitionsSequentially(Queue<Pair<Path, PathTransition>> transitions, Tile lastTile) {
        if (transitions.isEmpty()) {
            return;
        }

        Pair<Path, PathTransition> currentPair = transitions.poll();
        PathTransition currentTransition = currentPair.getValue();

        currentTransition.setOnFinished(event -> {
            System.out.println("Segment completed.");
            playTransitionsSequentially(transitions, lastTile);
        });

        if (transitions.isEmpty()) {
            currentTransition.setOnFinished(event -> {
                System.out.println("Animation completed.");
                updatePlayerSpriteImage(TrafficType.WALK); // Reset to default walking image
                collectGem(gem, lastTile);
                createRandomGem(upPart, gameMap.getWidth(), gameMap.getHeight());
            });
        }

        updatePlayerSpriteImage(currentPair.getKey().getTrafficType());  // Update image based on current path's traffic type
        System.out.println("Starting animation...");
        currentTransition.play();
    }

    private double calculateSpeedFactor(TrafficType trafficType) {
        // 根据交通类型返回不同的速度因子。
        switch (trafficType) {
            case WALK:
                return 0.5;
            case BIKE:
                return 0.45;
            case BUS:
                return 0.4;
            case CAR:
                return 0.35;
            case TRAIN:
                return 0.3;
            default:
                return 0.5;
        }
    }


    private void collectGem(Gem gem, Tile lastTile) {
        // 更新玩家位置
        player.setX(lastTile.getX());
        player.setY(lastTile.getY());
        // 将玩家图像移动到新位置
        playerImage.setX(lastTile.getX() * TILE_SIZE);
        playerImage.setY(lastTile.getY() * TILE_SIZE);

        // 从界面上移除宝石的视图
        upPart.getChildren().remove(gemImageView);

        // 增加玩家的宝石数量和分数
        player.addGemNumber(1);
        player.addGemScore(gem.getScore());

        updateStatusBoard();

        if (continueGeneratingGems) {
            scheduleNextGem(root, gameMap.getWidth(), gameMap.getHeight()); // 继续生成新的宝石
        }
    }


    private void updateStatusBoard() {
        nameLabel.setText("Name: " + player.getName());
        carbonLabel.setText("Carbon Footprint: " + player.getCarbonHP());
        scoreLabel.setText("Gem Score: " + player.getGemScore());
        gemCountLabel.setText("Gems Collected: " + player.getGemNumber());
    }


    // 更新玩家角色的图像以反映当前的移动方式，例如自行车或公交车。
    private void updatePlayerSpriteImage(TrafficType trafficType) {
        String imagePath;
        double width = 20;  // 默认宽度
        double height = 25;  // 默认高度

        switch (trafficType) {
            case BIKE:
                imagePath = "/images/bike.png";
                width = 35;   // 更宽的图像
                height = 30;  // 更高的图像
                break;
            case BUS:
                imagePath = "/images/bus.png";
                width = 35;
                height = 30;
                break;
            case TRAIN:
                imagePath = "/images/rail.png";
                width = 35;
                height = 30;
                break;
            case CAR:
                imagePath = "/images/texi.png";
                width = 35;
                height = 28;
                break;
            case WALK:
                imagePath = "/images/side.png";
                // 使用默认尺寸
                break;
            default:
                imagePath = "/images/player.png";
                // 使用默认尺寸
                break;
        }

        Image newImage = new Image(getClass().getResourceAsStream(imagePath));
        playerImage.setImage(newImage);
        playerImage.setFitWidth(width);
        playerImage.setFitHeight(height);


    }

}