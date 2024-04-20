package org.example.landoflustrous.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.landoflustrous.controller.GameStartController;
import org.example.landoflustrous.model.*;
import org.example.landoflustrous.service.NavigationService;
import org.example.landoflustrous.controller.GameController;


import java.io.IOException;
import java.util.*;


public class MapViewerScene {
    private static final int MAX_GEMS_PER_LEVEL = 5;
    private Pane root;
    private VBox base;// the base of map and status board
    private GameMap gameMap;
    private static final int TILE_SIZE = 30;
    private ImageView playerSprite;
    private String levelIdentifier;
    private List<Gem> currentGemList;
    private int curLevelGemPoint = 0;
    private int curLevelCarbonPoint = 0;
    private int curLevelGemNum = 0;
    private int curLevelTimeUse = 0;
    int pre = -1;
    private int cycle = 0;
    String mapPath;
    List<String> busPath;
    List<String> railPath;
    private GameController controller;


    // 定义一个私有的、静态的、不可变的HashMap，用于存储“level”与另一个“Map”之间的映射关系。这个“Map”则包含键值对，其中键为字符串类型，值为Object类型。
    private static final Map<String, Map<String, Object>> levelPathMapping = new HashMap<>();

//类创建时首先加载地图
    static {
// 将“Level 1”与另一个Map进行映射，这个Map包含关于“Level 1”的详细信息。
        levelPathMapping.put("Level 1", Map.of(

                "map", "/maps/map1/level1/map.txt",

                "rail", List.of("/maps/map1/level1/rail.txt"),

                "bus", List.of("/maps/map1/level1/bus1.txt", "/maps/map1/level1/bus2.txt")));
    levelPathMapping.put("Level 2", Map.of(

            "map", "/maps/map1/level2/map.txt",

            "rail", List.of("/maps/map1/level2/rail1.txt","/maps/map1/level2/rail2.txt"),

            "bus", List.of("/maps/map1/level2/bus1.txt", "/maps/map1/level2/bus2.txt")));

    }



    //根据传入的关卡标识符，从预先定义的路径映射中获取相关文件的路径，并用这些路径创建一个 GameMap 对象，然后初始化宝石序列
    public MapViewerScene(String levelIdentifier) {
        this.levelIdentifier = levelIdentifier;
//        setupLevelBackround();

        Map<String, Object> paths = levelPathMapping.get(levelIdentifier);

        if (paths != null) {
            try {
                String mapPath = (String) paths.get("map");

                List<String> railPaths = (List<String>) paths.get("rail");

                List<String> busPaths = (List<String>) paths.get("bus");


                this.gameMap = new GameMap(mapPath, railPaths, busPaths);
// 调用initializeGemSequence方法，初始化本关的宝石序列，并在该函数中存储完毕。
                initializeGemSequence();
            } catch (IOException e) {
// 如果在文件操作中出现异常，打印堆栈跟踪信息。
// TODO: 这里应该考虑更好的错误处理策略，例如提供用户友好的错误提示或记录日志。
                e.printStackTrace();
            } catch (ClassCastException e) {
// 如果在类型转换过程中出现错误，捕获ClassCastException并打印堆栈跟踪信息。
// 这将捕获任何由于类型错误导致的转换异常。
                e.printStackTrace();
            }
        } else {
// 如果根据levelIdentifier没有找到对应的路径映射，则抛出IllegalArgumentException异常。
            throw new IllegalArgumentException("Invalid level identifier: " + levelIdentifier);
        }
    }


    private void drawMap(Pane root) {
        int tempLevel = Integer.parseInt(levelIdentifier.replaceAll("[^0-9]",""));
        String pathToMap = "/images/map_level"+tempLevel+".png";
        Image mapImage = new Image(getClass().getResourceAsStream(pathToMap));
        ImageView mapView = new ImageView(mapImage);
        mapView.setFitWidth(gameMap.getWidth() * TILE_SIZE);
        mapView.setFitHeight(gameMap.getHeight() * TILE_SIZE);
        root.getChildren().add(mapView);
    }


    public void addPlayerCharacter(Pane root, PlayerCharacter player) {
        try {
            // 尝试加载玩家图像，如果图像加载失败，则抛出异常。
            Image image = new Image(getClass().getResourceAsStream("/images/player.png"));
            if (image.isError()) {
                throw new RuntimeException("Error loading player image.");
            }

            // 创建玩家精灵的 ImageView，并设置图像。
            playerSprite = new ImageView(image);
            playerSprite.setFitWidth(TILE_SIZE); // 设置图像视图的宽度以适应地图格子的尺寸。
            playerSprite.setFitHeight(TILE_SIZE);

            // 根据玩家的网格坐标定位精灵。
            playerSprite.setX(player.getX() * TILE_SIZE);
            playerSprite.setY(player.getY() * TILE_SIZE);

            // 将玩家精灵添加到根面板中。
            root.getChildren().add(playerSprite);

        } catch (Exception e) {
            // 在控制台打印异常堆栈，方便调试。
            e.printStackTrace();
            // 向外部抛出运行时异常，指示添加玩家角色失败。
            throw new RuntimeException("Failed to add player character.", e);
        }
    }


    // 定义 createMapScene 方法，用于创建游戏的地图场景。这个方法接受一个舞台 (Stage) 对象和两个计算器类的实例，分别用于计算分数和生命时长。
    public void createMapScene(Stage stage, ScoreCalculator scoreCalculator, TimeLifeCalculator timeLifeCalculator) {
        controller = new GameController(stage, this);


        base = new VBox();
        root = new Pane();
        drawMap(root);
        PlayerCharacter testPlayer;

// 初始化索引变量来标记最后一次收集的宝石位置。
        int lastCollectedIndex = -1;

// 遍历宝石列表，检查每个宝石是否已经被收集。
        for (int i = cycle - 1; i >= 0; i--) {
            if (currentGemList.get(i).isCollected()) {
                lastCollectedIndex = i;
            }
        }

        // 根据宝石的收集情况，初始化测试玩家的位置和状态。
        // 检查上一次收集的宝石索引，如果为-1，则表示没有收集到任何宝石。
        if (lastCollectedIndex == -1) {
            // 如果没有收集到宝石，则初始化测试玩家在地图的默认位置（例如，坐标(2, 2)）上，并且设置其初始状态（如生命值为100）。
            // PlayerCharacter构造函数的参数分别为：玩家名称、玩家初始X坐标、玩家初始Y坐标、玩家初始生命值、其他可能的参数（此处为null）、其他可能的参数（此处为null）、玩家等级。
            testPlayer = new PlayerCharacter("TestPlayer", 0, 0, 100, null, null, 1);
        } else {
            // 如果收集到了宝石（lastCollectedIndex不是-1），则根据最后收集的宝石的位置来初始化测试玩家的位置。
            // 获取最后收集的宝石的X坐标和Y坐标。
            // currentGemList是一个存储宝石信息的列表，lastCollectedIndex是最后一次收集的宝石在列表中的索引。
            // getX()和getY()方法用于获取宝石的坐标。
            // 然后使用这些坐标作为测试玩家的初始位置，并设置其初始状态（如生命值为100）。
            testPlayer = new PlayerCharacter("TestPlayer", currentGemList.get(lastCollectedIndex).getX(), currentGemList.get(lastCollectedIndex).getY(), 100, null, null, 1);
        }


// 将测试玩家添加到场景中。
        addPlayerCharacter(root, testPlayer);

        // 创建状态显示板，并将其添加到 VBox 布局中。
        Pane statusBoard = createStatusBoard(scoreCalculator, timeLifeCalculator);
        base.getChildren().add(root);
        base.getChildren().add(statusBoard);

        // 设置玩家的精灵为最前显示，并设置其不透明度和可见性。
        playerSprite.toFront();
        playerSprite.setOpacity(1.0);
        playerSprite.setVisible(true);

// 实例化导航服务，并计算从当前玩家位置到目标宝石的路线。
        NavigationService navigationService = new NavigationService(gameMap);


// 创建一个从测试玩家当前位置到指定宝石位置的导航路线列表。

// 获取测试玩家的当前坐标
        Coordinated playerCoordinate = new Coordinated(testPlayer.getX(), testPlayer.getY());

// 获取目标宝石的坐标
// 在 Java 中，List 接口提供了 get(int index) 方法，这使得我们可以通过索引来访问列表中的元素。
        Coordinated gemCoordinate = new Coordinated(currentGemList.get(cycle).getX(), currentGemList.get(cycle).getY());

// 使用导航服务计算从玩家到宝石的路线
        List<Route> routeList = navigationService.navigate(playerCoordinate, gemCoordinate);


// 创建选项面板，用于显示路线选择和宝石的相关信息。
        int i = cycle;
        Gem currentGem = currentGemList.get(i);
        Image image = new Image(getClass().getResourceAsStream("/images/" + currentGem.getType() + ".png"));
        ImageView imageView_gem = new ImageView(image);
        imageView_gem.setX(currentGem.getX() * TILE_SIZE);
        imageView_gem.setY(currentGem.getY() * TILE_SIZE);
        imageView_gem.resize(30, 30);

        //给宝石设置定时消失效果
        bindTimer(imageView_gem, currentGem.getLiveTime());

        root.getChildren().add(imageView_gem);

        Pane optionBoard = new OptionBoard().createOptionBoard(routeList, currentGem);

        Label labelForClick = new Label("UnClick");
        labelForClick.setVisible(false);
        root.getChildren().add(labelForClick);

// 根据是否到达最后一个宝石，配置选项板的行为。
        if (i == currentGemList.size() - 1) {
            String s = labelForClick.textProperty().getValue();
            {
//                添加一个监听器到optionboard的可见属性，当optionboard的可见属性发生变化时，执行监听器中的代码块
//                最后一个宝石A页面，用户没点击，optionboard消失，跳出返回home按钮
                optionBoard.visibleProperty().addListener((observable, oldValue, newValue) -> {
// 添加返回主页按钮。
                    Button buttonTestToHome = new Button("Home");
                    buttonTestToHome.setOnAction(event -> {
                        stage.setScene(new GameStartScene().createStartScene(stage, scoreCalculator, timeLifeCalculator));
                    });
                    root.getChildren().add(buttonTestToHome);

// 创建等级结果卡片，并将其添加到场景中。
                    Pane level = createLevlResultCardWithLabels();

                    level.setLayoutX(100);
                    level.setLayoutY(400);

                    root.getChildren().add(level);

// 根据分数决定是否显示“进入下一关”或“游戏结束”按钮。
                    Button buttonToNextLevel = new Button("To Next Level");
                    buttonToNextLevel.setOnAction(e -> controller.goToScoreBoard());

                    Button buttonTestToOver = new Button("分数不足 结算");
                    root.getChildren().add(buttonTestToOver);
                    buttonTestToOver.setVisible(false);
//objectscore = level number * 10
//                    buttonTestToOver.setOnAction(e -> controller.goToGameOver());


                    int objectScoreLvel = (levelIdentifier.charAt(levelIdentifier.length() - 1) - '0') * 10;
                    //                        buttonToNextLevel.setVisible(true);
                    if (curLevelGemPoint >= objectScoreLvel) {
                        buttonToNextLevel.setVisible(true);
                        buttonToNextLevel.setOnAction(e -> controller.goToScoreBoard());   } else {
                    }

                    root.getChildren().add(buttonToNextLevel);

                });
            }
        }
        else {
            optionBoard.visibleProperty().addListener((observable, oldValue, newValue) -> {
// 当选项板状态改变时，如果未点击则进入下一次循环。
                String s = labelForClick.textProperty().getValue();
                if (labelForClick.textProperty().getValue().equals("UnClick")) {
                    cycle++;
                    createMapScene(stage, scoreCalculator, timeLifeCalculator);
                }

            });
        }

        optionBoard.setLayoutX(200);
        optionBoard.setLayoutY(100);

// 绑定按钮以处理路线选择。
        for (int j = 0; j < routeList.size(); j++) {
            Route currentRoute = routeList.get(j);
            List<Tile> tileList = routeList.get(j).totalTileList();
            double carbonPoint = routeList.get(j).getTotalCarbon();
            int totalCost = routeList.get(j).getTotalCost();
            ((Button) optionBoard.lookup("#Route" + (j + 1))).setOnAction(event -> {
// 设置点击行为和更新当前状态。
                labelForClick.setText("click");
                optionBoard.setVisible(false);
                Gem temp = currentGemList.get(cycle);
                temp.collected = true;
                currentGemList.set(cycle, temp);
//建立一个新optionboard接收选择后结果
                OptionBoard curOptionBoard = new OptionBoard(temp.getScore(), currentRoute.getTotalCarbon(), true);

                curLevelCarbonPoint += curOptionBoard.getCarbonPoint();
                curLevelGemPoint += curOptionBoard.getGemPoint();
                curLevelTimeUse += currentRoute.getTotalCost();
                curLevelGemNum++;
                if (timeLifeCalculator.getCurLifeRemain() - totalCost < 0) {
                    pre = totalCost;
//                    controller.goToGameOver();
                } else {
                    scoreCalculator.addPoints(curOptionBoard);
                }
                timeLifeCalculator.setCurLifeRemain(timeLifeCalculator.getCurLifeRemain() - totalCost);

                Path path = new Path(TrafficType.BIKE, tileList);
                createMapScene_AfterChooseOption(stage, currentRoute, scoreCalculator, temp, timeLifeCalculator, curOptionBoard);
            });
        }

        root.getChildren().add(optionBoard);

// 设置场景，并显示舞台。
// stage.setScene(new Scene(base, gameMap.getWidth() * TILE_SIZE, gameMap.getHeight() * TILE_SIZE + statusBoardHeight));

        stage.setScene(new Scene(base, 1500, 900));
        stage.show();
    }


    // 定义一个私有方法 createStatusBoard，用于创建游戏的状态显示板。该方法接收两个参数：scoreCalculator 和 timeLifeCalculator，分别用于计分和计算剩余时间。
    private Pane createStatusBoard(ScoreCalculator scoreCalculator, TimeLifeCalculator timeLifeCalculator) {
        HBox statusBoard = new HBox(30);

//        VBox.setMargin(statusBoard, new Insets(100, 0, 0, 0));

        statusBoard.setStyle("-fx-background-color: #FFFFFF;");

        // 创建并初始化碳足迹标签，显示当前的总碳点数。如果碳点数未初始化，则显示 "N/A"。
        String s = scoreCalculator.getTotalCarbonPoint() == -1 ? "N/A" : ("" + scoreCalculator.getTotalCarbonPoint());
        Label carbonFootprintLabel = new Label("Carbon Footprint: " + s);

        // 创建并初始化剩余时间标签，显示当前的生命剩余时间。
        Label timeLeftLabel = new Label("Time Left: " + timeLifeCalculator.getCurLifeRemain());

        // 创建并初始化宝石分数标签，显示当前的总宝石点数。如果宝石点数未初始化，则显示 "N/A"。
        s = scoreCalculator.getTotalGemPoint() == -1 ? "N/A" : ("" + scoreCalculator.getTotalGemPoint());
        Label scoreLabel = new Label("Gem Score: " + s);

        // 将所有标签添加到状态板中。
        statusBoard.getChildren().addAll(carbonFootprintLabel, timeLeftLabel, scoreLabel);

        // 返回包含所有状态信息的 HBox 容器。

        return statusBoard;
    }

    // 定义 createMapScene_AfterChooseOption 方法，该方法在用户选择完路径选项后调用，用于更新地图场景和游戏状态。
    public void createMapScene_AfterChooseOption(Stage stage, Route route, ScoreCalculator scoreCalculator, Gem gem, TimeLifeCalculator timeLifeCalculator, OptionBoard curOptionBoard) {
        controller = new GameController(stage, this);

        base = new javafx.scene.layout.VBox();
// 创建根面板，用于添加游戏元素。
        Pane root = new Pane();
// 绘制地图,接收了
        drawMap(root);
// 实例化测试用的玩家角色，注意未来需要移到控制器中。
        PlayerCharacter testPlayer;

// 寻找最后一个被收集的宝石的索引。
        int lastCollectedIndex = -1;
        for (int i = cycle-1; i >= 0; i--) {
            if (currentGemList.get(i).isCollected()) {
                lastCollectedIndex = i;

            }
        }
// 根据是否有宝石被收集来初始化玩家角色的位置。
        if (lastCollectedIndex == -1) {
            testPlayer = new PlayerCharacter("TestPlayer", 2, 2, 100, null, null, 1);
        } else {
            testPlayer = new PlayerCharacter("TestPlayer", currentGemList.get(lastCollectedIndex).getX(), currentGemList.get(lastCollectedIndex).getY(), 100, null, null, 1);
        }
// 将玩家添加到场景中。
        addPlayerCharacter(root, testPlayer);
        playerSprite.toFront();
        playerSprite.setOpacity(1.0);
        playerSprite.setVisible(true);

// 在场景中添加本关的宝石图像。
        ImageView imageView;
        Image image = new Image(getClass().getResourceAsStream("/images/" + gem.getType() + ".png"));
        imageView = new ImageView(image);
        imageView.setX(gem.getX() * TILE_SIZE);
        imageView.setY(gem.getY() * TILE_SIZE);
        imageView.resize(10, 10);
        root.getChildren().add(imageView);

// 如果剩余时间小于0，说明时间不足，触发游戏结束逻辑处理
        if (timeLifeCalculator.getCurLifeRemain() < 0) {
//            controller.goToGameOver();

// 尝试为剩余时间增加pre值（这里可能是个逻辑错误，因为时间应该不能是负数，这行代码可能是尝试修复负时间的情况）
// 但这样做并不符合常规的游戏逻辑，通常应该是结束游戏而不是增加时间
//            剩余时间小于0，时间不足，选项为无效选项, 加回之前扣除的时间
            timeLifeCalculator.setCurLifeRemain(timeLifeCalculator.getCurLifeRemain() + pre);

// 创建一个状态面板，展示分数和时间等游戏状态信息
            Pane statusBoard = createStatusBoard(scoreCalculator, timeLifeCalculator);
//            controller.goToGameOver();


// 创建一个标签，用于显示游戏结束信息
            Label infoLabel = new Label("剩余时间无法支持本次路线选择无效 游戏结束 请点击结算按钮");

// 设置标签的位置
            infoLabel.setLayoutX(100);
            infoLabel.setLayoutY(350);

// 将标签添加到root节点中
            root.getChildren().add(infoLabel);

// 更新当前关卡中的碳点数、宝石点数、宝石数量和时间消耗
            curLevelCarbonPoint -= curOptionBoard.getCarbonPoint();
            curLevelGemPoint -= curOptionBoard.getGemPoint();
            curLevelGemNum--;
            curLevelTimeUse -= pre;

// 创建一个关卡结果卡片，用于展示关卡结束后的相关信息
            Pane level = createLevlResultCardWithLabels();
            controller.goToScoreBoard();

// 设置关卡结果卡片的位置
            level.setLayoutX(100);
            level.setLayoutY(400);

// 将关卡结果卡片添加到root节点中
            root.getChildren().add(level);

// 创建一个按钮，用于结算游戏
            Button buttonTestToOver = new Button("结算按钮");// ScoreBoard

// 设置按钮的点击事件处理
            buttonTestToOver.setOnAction(event -> {
// 原本这里可能是跳转到游戏结束场景，但现在被注释掉了

                stage.setScene(new GameOverScene(stage, scoreCalculator, timeLifeCalculator).getScene());

// 现在点击按钮后直接跳转到分数板场景
// stage.setScene(new ScoreBoardScene().getScene());
            });

// 将结算按钮添加到root节点中
            root.getChildren().add(buttonTestToOver);

// 将root节点和其他相关组件添加到base节点中
            base.getChildren().add(root);
            base.getChildren().add(statusBoard);

// 创建一个新的场景，将base节点设置为场景的根节点，并设置场景的尺寸
            stage.setScene(new Scene(base, 1500, 900));

// 显示舞台（即显示整个游戏界面）
            stage.show();

// 返回某种值或继续执行后续逻辑（这里返回类型未明确，可能是void或其他类型）
            return;
        }


// 添加状态板至主容器。
        Pane statusBoard = createStatusBoard(scoreCalculator, timeLifeCalculator);
//        controller.goToGameOver();

        base.getChildren().add(root);
        base.getChildren().add(statusBoard);

// 设置各种按钮并根据游戏状态决定其可见性。
        Button buttonTestToHome = new Button("Home");
        buttonTestToHome.setOnAction(event -> {
            stage.setScene(new GameStartScene().createStartScene(stage, scoreCalculator, timeLifeCalculator));
        });
        buttonTestToHome.setVisible(false);
        root.getChildren().add(buttonTestToHome);

        Button buttonToNextLevel = new Button("To Next Level");
        buttonToNextLevel.setOnAction(event -> {
            controller.goToScoreBoard();
            int tempLevel = Integer.parseInt(levelIdentifier.replaceAll("[^0-9]",""));
            int nextLevel = tempLevel + 1;
            String nextLevelIdentifier = "Level "+ nextLevel; // level
            try {
                new LevelSelectionScene().openMapPage(stage, nextLevelIdentifier, scoreCalculator, timeLifeCalculator);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
//        Scene scoreBoardScene = new ScoreBoardScene().getScene();



        buttonToNextLevel.setVisible(false);
//        buttonToNextLevel.setOnAction(e ->
////                controller.goToGameOver()
//        );
        root.getChildren().add(buttonToNextLevel);

        Button buttonTestToOver = new Button("分数不足 结算");//GameOver
        buttonTestToOver.setOnAction(event -> {
//            controller.goToGameOver();
            stage.setScene(new GameOverScene(stage, scoreCalculator, timeLifeCalculator).getScene());
        });
        buttonTestToOver.setVisible(false);
        root.getChildren().add(buttonTestToOver);

        Button buttonKeepLevel = new Button("Keep Playing this Level");
        buttonKeepLevel.setLayoutY(100);
        buttonKeepLevel.setVisible(false);
        buttonKeepLevel.setOnAction(event -> {
            cycle++;
            createMapScene(stage, scoreCalculator, timeLifeCalculator);
        });
        root.getChildren().add(buttonKeepLevel);

// 处理玩家角色按照选定路线移动的动画逻辑。
        final int[] pathIndex = {0};
        final int[] tileIndex = {0};
        final Timeline[] timeline = new Timeline[1];
        timeline[0] = new Timeline(
                new KeyFrame(Duration.seconds(0.1), event -> {
                    if (pathIndex[0] < route.getPathList().size()) {
                        Path currentPath = route.getPathList().get(pathIndex[0]);
                        if (tileIndex[0] < currentPath.getTileList().size()) {
                            Tile currentTile = currentPath.getTileList().get(tileIndex[0]);
                            double newX = currentTile.x * TILE_SIZE;
                            double newY = currentTile.y * TILE_SIZE;
                            updatePlayerSpriteImage(currentPath.getTrafficType());
                            playerSprite.setX(newX);
                            playerSprite.setY(newY);
                            tileIndex[0]++;
                        } else {
                            tileIndex[0] = 0;
                            pathIndex[0]++;
                        }
                    } else {
                        timeline[0].stop();
                        imageView.setVisible(false);
                        int objectScoreLvel = (levelIdentifier.charAt(levelIdentifier.length() - 1) - '0') * 10;
                        if (cycle == currentGemList.size() - 1) {
                            Pane level = createLevlResultCardWithLabels();
                            controller.goToScoreBoard();


                            level.setLayoutX(100);
                            level.setLayoutY(400);

                            root.getChildren().add(level);

                            if (curLevelGemPoint >= objectScoreLvel) {
                                buttonToNextLevel.setVisible(true);
                                buttonToNextLevel.setOnAction(e -> controller.goToScoreBoard());
                            } else {
                                buttonTestToOver.setVisible(true);

//                                buttonTestToOver.setOnAction(e -> controller.goToGameOver());

                            }
                        } else if (curLevelGemPoint >= objectScoreLvel) {
                            buttonToNextLevel.setVisible(true);
                            buttonToNextLevel.setOnAction(e -> controller.goToScoreBoard());
                            buttonKeepLevel.setVisible(true);
                        } else {
                            cycle++;
                            createMapScene(stage, scoreCalculator, timeLifeCalculator);
                        }
                    }
                })
        );

        stage.setScene(new Scene(base, 1300, 700));
        timeline[0].setCycleCount(Timeline.INDEFINITE);
        timeline[0].play();
        stage.show();
    }

    // 更新玩家角色的图像以反映当前的移动方式，例如自行车或公交车。
    private void updatePlayerSpriteImage(TrafficType trafficType) {
        String imagePath;
        switch (trafficType) {
            case BIKE:
                imagePath = "/images/bike.png";
                break;
            case BUS:
                imagePath = "/images/bus.png";
                break;
            case TRAIN:
                imagePath = "/images/rail.png";
                break;
            case CAR:
                imagePath = "/images/texi.png";
                break;
            case WALK:
                imagePath = "/images/side.png";
                break;
            default:
                imagePath = "/images/player.png"; // 默认情况下使用行走图像
                break;
        }
        playerSprite.setImage(new Image(getClass().getResourceAsStream(imagePath)));
    }


    // 定义 generateGems 方法，用于随机生成一定数量的宝石。
    private List<Gem> generateGems(int count) {
        List<Gem> gems = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Gem newGem;
            do {
// 创建宝石实例，并确保其位置不在禁止区域或轨道上。
                newGem = new Gem(); // 宝石的类型、x 坐标和 y 坐标随机生成。
                newGem.collected = false;
            } while (gameMap.getTile(newGem.getX(), newGem.getY()).isForbidden || gameMap.getTile(newGem.getX(), newGem.getY()).isRail);
            gems.add(newGem);
        }
        return gems;
    }


    // 初始化宝石序列，用于根据当前关卡生成宝石。
    public void initializeGemSequence() {
// 根据当前关卡标识符的最后一个字符确定生成宝石的数量。
        int num = (levelIdentifier.toCharArray()[levelIdentifier.length() - 1] - '0') + 1;
        List<Gem> gems = generateGems(num);
        currentGemList = new LinkedList<>(gems);
    }

    // 定义 bindTimer 方法，用于为图像视图设置定时隐藏功能。
    private void bindTimer(ImageView imageView, int durationSeconds) {
        // 设置定时器的时长。
        Duration duration = Duration.seconds(durationSeconds);
        Timeline timeline = new Timeline(new KeyFrame(duration, event -> {
            // 时间到达后执行的操作，如隐藏图像视图。
            imageView.setVisible(false);
        }));
        timeline.play(); // 启动定时器。
    }


    // 定义 createLevlResultCardWithLabels 方法，用于创建显示关卡结果的卡片。
    public Pane createLevlResultCardWithLabels() {
// 创建一个 Pane 作为显示卡片的容器，并设置其样式和大小。
        Pane cardPane = new Pane();
        cardPane.setPrefSize(300, 200);
        cardPane.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");

// 创建并配置显示碳足迹、宝石分数、收集宝石数和消耗时间的标签。
        Label labelCarbonPoint = new Label("Carbon FootPrint Gained in this Level: " + curLevelCarbonPoint);
        labelCarbonPoint.setLayoutX(10);
        labelCarbonPoint.setLayoutY(20);

        Label labelGemPoint = new Label("Gem Point Gained in this Level:: " + curLevelGemPoint);
        labelGemPoint.setLayoutX(10);
        labelGemPoint.setLayoutY(50);

        Label labelGemNum = new Label("Num of Gem Collected in this Level: " + curLevelGemNum);
        labelGemNum.setLayoutX(10);
        labelGemNum.setLayoutY(80);

        Label labelTime = new Label("Time cost in this Level: " + curLevelTimeUse);
        labelTime.setLayoutX(10);
        labelTime.setLayoutY(110);

// 将标签添加到卡片容器中。
        cardPane.getChildren().addAll(labelCarbonPoint, labelGemPoint, labelGemNum, labelTime);

// 返回配置好的卡片面板。
        return cardPane;
    }

    // 访问器方法
    public int getCurLevelGemPoint() {
        return curLevelGemPoint;
    }

    public int getCurLevelCarbonPoint() {
        return curLevelCarbonPoint;
    }

    public int getCurLevelGemNum() {
        return curLevelGemNum;
    }

    public int getCurLevelTimeUse() {
        return curLevelTimeUse;
    }

    // 假设的方法来检测关卡是否完成和玩家是否赢得了关卡
    public boolean isLevelComplete() {
        System.out.println("check level complete");
        return cycle >= currentGemList.size() - 1;
    }
    public boolean playerHasWon() {
        System.out.println("check player won");

        // 首先检查时间和碳点数是否耗尽
        if (getCurLevelCarbonPoint() >= 300 || getCurLevelTimeUse() >= 1000) {
            return false;  // 如果任一条件满足，玩家输了游戏
        }

        // 然后检查玩家是否达到了赢得游戏所需的宝石点数
        int objectScoreLevel = (levelIdentifier.charAt(levelIdentifier.length() - 1) - '0') * 10;
        return curLevelGemPoint >= objectScoreLevel;
    }





}