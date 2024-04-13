package org.example.landoflustrous.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;

import java.io.IOException;
import java.util.*;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

// Imports for saving map snapshot
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.application.Platform;
import javafx.scene.image.WritableImage;
import javafx.scene.SnapshotParameters;
import org.example.landoflustrous.model.*;
import org.example.landoflustrous.service.NavigationService;


public class MapViewerScene {
    private static final int MAX_GEMS_PER_LEVEL = 5;
    //TODO: HARD CODED. Subject to change for each level (controller's job)
    private Pane root;
    private VBox base;// the base of map and status board

    private Label carbonFootprintLabel;
    private Label timeLeftLabel;
    private Label scoreLabel;
    private GameMap gameMap;
    private final int statusBoardHeight = 10;
    private static final int TILE_SIZE = 20;
    private ImageView playerSprite;
    private java.util.Queue<Gem> gemQueue;
        private ImageView currentGemSprite;
    private String levelIdentifier;

    private List<Gem> currentGemList;
    private int curLevelGemPoint = 0;
    private int curLevelCarbonPoint = 0;
    private int curLevelGemNum=0;
    private int curLevelTimeUse=0;
    int pre = -1;

    private int  cycle=0;

    // A mapping from level identifiers to file paths
    private static final Map<String, Map<String, Object>> levelPathMapping = new HashMap<>();

    //TODO: MOVE TO CONFIG AFTER MAKING SURE IT RUNS
    static {
        // Initialize the mapping of levels to files
        levelPathMapping.put("Level 1", Map.of(
                "map", "/maps/map1/map.txt", // Single string for the map file path
                "rail", List.of("/maps/map1/rail.txt"), // List for rail file paths
                "bus", List.of("/maps/map1/bus1.txt", "/maps/map1/bus2.txt"))); // List for bus file paths
//        levelPathMapping.put("Level 2", Map.of(
//                "map", "LocalJavaGame/MapEdit/level2/map.txt",
//                "rail", List.of("LocalJavaGame/MapEdit/leve2/rail.txt"),
//                "bus", List.of("MapEdit/leve2/bus.txt")));
        // Add mappings for other levels as needed


    }

    public MapViewerScene(String levelIdentifier) {
        this.levelIdentifier = levelIdentifier;

        
        Map<String, Object> paths = levelPathMapping.get(levelIdentifier);
        if (paths != null) {
            try {
                // Cast the objects back to their expected types
                String mapPath = (String) paths.get("map");
                List<String> railPaths = (List<String>) paths.get("rail");
                List<String> busPaths = (List<String>) paths.get("bus");

                // Pass the extracted paths to the GameMap constructor
                this.gameMap = new GameMap(mapPath, railPaths, busPaths);
                initializeGemSequence();//gemList本关的在该函数中存储完毕
            } catch (IOException e) {
                e.printStackTrace(); // TODO: Consider a better error handling strategy
            } catch (ClassCastException e) {
                e.printStackTrace(); // This will catch any cast errors in case of a wrong type
            }
        } else {
            throw new IllegalArgumentException("Invalid level identifier: " + levelIdentifier);
        }

    }

    //    public Scene createMapScene() {
//        Pane root = new Pane();
//        drawMap(root);
//        // TODO: this is for testing purpose. Move player instantiation to controller later
//        // Instantiate a player character for testing
//        PlayerCharacter testPlayer = new PlayerCharacter("TestPlayer", 2, 2, 100, null, null, 1); // Adjust the parameters as needed
//
//        // Add the player character to the scene
//        addPlayerCharacter(root, testPlayer);
//        playerSprite.toFront();
//        playerSprite.setOpacity(1.0);
//    playerSprite.setVisible(true);
//        return new Scene(root, gameMap.getWidth() * TILE_SIZE, gameMap.getHeight() * TILE_SIZE);
//    }
    public void createMapScene(Stage stage, ScoreCalculator scoreCalculator, TimeLifeCalculator timeLifeCalculator) {
        base = new javafx.scene.layout.VBox();
        root = new Pane();

        drawMap(root);
        // TODO: this is for testing purpose. Move player instantiation to controller later
        // Instantiate a player character for testing
        PlayerCharacter testPlayer;

        int lastCollectedIndex = -1;
        for(int i=0;i<currentGemList.size();i++){
            if(currentGemList.get(i).isCollected()){
                lastCollectedIndex=i;
                break;
            }
        }
        if(lastCollectedIndex==-1){
            testPlayer = new PlayerCharacter("TestPlayer", 2, 2, 100, null, null, 1); // Adjust the parameters as needed
        }else{
            testPlayer = new PlayerCharacter("TestPlayer", currentGemList.get(lastCollectedIndex).getX(), currentGemList.get(lastCollectedIndex).getY(), 100, null, null, 1); // Adjust the parameters as needed
        }
        // Add the player character to the scene
        addPlayerCharacter(root, testPlayer);
        //底部显示栏
        Pane statusBoard = createStatusBoard(scoreCalculator,timeLifeCalculator);
        base.getChildren().add(root); // Add the map pane to the base VBox
        base.getChildren().add(statusBoard); // Add the status board pane to the base VBox


        playerSprite.toFront();
        playerSprite.setOpacity(1.0);
        playerSprite.setVisible(true);

        //获取当前起点 以及 目标钻石的路线集合
        NavigationService navigationService = new NavigationService(gameMap);
        List<Route> routeList = navigationService.navigate(new Coordinated(testPlayer.getX(),testPlayer.getY()),new Coordinated(currentGemList.get(cycle).getX(),currentGemList.get(cycle).getY()));




        //optionboard code
            int i=cycle;
            Gem currentGem = currentGemList.get(i);
            Image image = new Image(getClass().getResourceAsStream("/images/" + currentGem.getType() + ".png"));
            ImageView imageView = new ImageView(image);
            imageView.setX(currentGem.getX() * TILE_SIZE);
            imageView.setY(currentGem.getY() * TILE_SIZE);
            imageView.resize(10, 10);
            bindTimer(imageView, currentGem.getLiveTime());
            root.getChildren().add(imageView);
            Pane optionBoard = new OptionBoard().createOptionBoard(routeList, currentGem);//需要输入Path 或者该章地图的宝石等等参数以确定具体内容
            Label labelForClick = new Label("UnClick");
            labelForClick.setVisible(false);
            root.getChildren().add(labelForClick);
            if(i==currentGemList.size()-1){
                String s = labelForClick.textProperty().getValue();
//                if(labelForClick.textProperty().getValue().equals("UnClick"))
                {
                    optionBoard.visibleProperty().addListener((observable, oldValue, newValue) -> {
                        // 在这里执行属性变化时的操作
                        Button buttonTestToHome = new Button("Home");
                        buttonTestToHome.setOnAction(event -> {
                            stage.setScene(new GameStartScene().createStartScene(stage, scoreCalculator, timeLifeCalculator));
                        });
                        root.getChildren().add(buttonTestToHome);

                        Pane level = createLevlResultCardWithLabels();
                        level.setLayoutX(100);
                        level.setLayoutY(400);

                        root.getChildren().add(level);

                        //返回下一关按钮如果分数满足的话 直接下一关
                        Button buttonToNextLevel = new Button("To Next Level");
                        buttonToNextLevel.setOnAction(event -> {
//                        String s = "Level "+(levelIdentifier.charAt(levelIdentifier.length()-1)-'0'+1);
                            String s1 = "Level 1";
                            new LevelSelectionScene().openMapPage(stage, s1,scoreCalculator,timeLifeCalculator);
                        });
                        buttonToNextLevel.setVisible(false);

                        //返回下一关按钮如果分数不满足的话 游戏失败 结算
                        Button buttonTestToOver = new Button("分数不足 结算");
                        buttonTestToOver.setOnAction(event -> {
                            stage.setScene(new GameOverScene(stage,scoreCalculator,timeLifeCalculator).getScene());
                        });
                        root.getChildren().add(buttonTestToOver);
                        buttonTestToOver.setVisible(false);
                        int objectScoreLvel = (levelIdentifier.charAt(levelIdentifier.length()-1)-'0')*10;
                        if(curLevelGemPoint>=objectScoreLvel){buttonToNextLevel.setVisible(true);}else{buttonTestToOver.setVisible(true);}

                        root.getChildren().add(buttonToNextLevel);

                    });
                }

            }else{
                optionBoard.visibleProperty().addListener((observable, oldValue, newValue) -> {
                    // 在这里执行属性变化时的操作
                   String s = labelForClick.textProperty().getValue();
                    if(labelForClick.textProperty().getValue().equals("UnClick")){
                       cycle++;
                       createMapScene(stage, scoreCalculator, timeLifeCalculator);
                   }

                });
            }

            optionBoard.setLayoutX(200);
            optionBoard.setLayoutY(100);



            //绑定按钮
            for(int j=0;j<routeList.size();j++){
                Route currentRoute = routeList.get(j);
                List<Tile> tileList = routeList.get(j).totalTileList();
                double carbonPoint = routeList.get(j).getTotalCarbon();
                int totalCost = routeList.get(j).getTotalCost();
                ((Button) optionBoard.lookup("#Route"+(j+1))).setOnAction(event -> {

                    labelForClick.setText("click");
                    optionBoard.setVisible(false);
                    Gem temp = currentGemList.get(cycle);
                    temp.collected=true;
                    currentGemList.set(cycle,temp);

                    OptionBoard curOptionBoard = new OptionBoard(temp.getScore(), currentRoute.getTotalCarbon(), true);

                    curLevelCarbonPoint+=curOptionBoard.getCarbonPoint();
                    curLevelGemPoint+=curOptionBoard.getGemPoint();
                    curLevelTimeUse+=currentRoute.getTotalCost();
                    curLevelGemNum++;
                    if(timeLifeCalculator.getCurLifeRemain()-totalCost<0){pre=totalCost;}else{scoreCalculator.addPoints(curOptionBoard);}
                    timeLifeCalculator.setCurLifeRemain(timeLifeCalculator.getCurLifeRemain()-totalCost);

                    Path path = new Path(TrafficType.BIKE, tileList);//自己生成的
                    ////需要生成当前地图的Route实例-保存PathList
                    //然后绑定表单控件的id来选择
                    createMapScene_AfterChooseOption(stage, currentRoute, scoreCalculator,temp,timeLifeCalculator,curOptionBoard);
                });
            }

//
            root.getChildren().add(optionBoard);
        // Line to save map background dynamically generated
        //    Platform.runLater(() -> savePaneAsImage(root, "src/gui/img/map_image.png"));
        stage.setScene(new Scene(base, gameMap.getWidth() * TILE_SIZE, gameMap.getHeight() * TILE_SIZE + statusBoardHeight));
        stage.show();
        // Line to save map background dynamically generated
        //    Platform.runLater(() -> savePaneAsImage(root, "src/gui/img/map_image.png"));

    }

    private Pane createStatusBoard(ScoreCalculator scoreCalculator, TimeLifeCalculator timeLifeCalculator) {
        // Create a horizontal box to hold the status labels
        HBox statusBoard = new HBox(10);
        statusBoard.setPadding(new Insets(10));
        statusBoard.setStyle("-fx-background-color: #336699;"); // Set a background color

        // Create labels for the player status
        String s = scoreCalculator.getTotalCarbonPoint()==-1?"N/A":(""+scoreCalculator.getTotalCarbonPoint());
        Label carbonFootprintLabel = new Label("Carbon Footprint: " + s);

        Label timeLeftLabel = new Label("Time Left: " + timeLifeCalculator.getCurLifeRemain());
        s = scoreCalculator.getTotalGemPoint()==-1?"N/A":(""+scoreCalculator.getTotalGemPoint());
        Label scoreLabel = new Label("Gem Score: " + s);
        // Add labels to the status board
        statusBoard.getChildren().addAll(carbonFootprintLabel, timeLeftLabel, scoreLabel);

        // Return the status board pane
        return statusBoard;

    }

    public void createMapScene_AfterChooseOption(Stage stage, Route route, ScoreCalculator scoreCalculator, Gem gem, TimeLifeCalculator timeLifeCalculator, OptionBoard curOptionBoard) {
        base = new javafx.scene.layout.VBox();
        Pane root = new Pane();
        drawMap(root);
        // TODO: this is for testing purpose. Move player instantiation to controller later
        // Instantiate a player character for testing
        PlayerCharacter testPlayer;

        int lastCollectedIndex = -1;
        for(int i=0;i<currentGemList.size();i++){
            if(currentGemList.get(i).isCollected()){
                lastCollectedIndex=i;
                break;
            }
        }
        if(lastCollectedIndex==-1){
            testPlayer = new PlayerCharacter("TestPlayer", 2, 2, 100, null, null, 1); // Adjust the parameters as needed
        }else{
            testPlayer = new PlayerCharacter("TestPlayer", currentGemList.get(lastCollectedIndex).getX(), currentGemList.get(lastCollectedIndex).getY(), 100, null, null, 1); // Adjust the parameters as needed
        }

        // Add the player character to the scene
        addPlayerCharacter(root, testPlayer);
        playerSprite.toFront();
        playerSprite.setOpacity(1.0);
        playerSprite.setVisible(true);
        //底部显示栏

         // Add the map pane to the base VBox

        //Add 本关的宝石在scene中 此时不会消失
        ImageView imageView;
        Image image = new Image(getClass().getResourceAsStream("/images/" + gem.getType() + ".png"));
        imageView = new ImageView(image);
        imageView.setX(gem.getX() * TILE_SIZE);
        imageView.setY(gem.getY() * TILE_SIZE);
        imageView.resize(10, 10);
        root.getChildren().add(imageView);
        //如果选项时间太长，需要提示用户该选择无法支持 游戏失败
        if(timeLifeCalculator.getCurLifeRemain()<0){
            timeLifeCalculator.setCurLifeRemain(timeLifeCalculator.getCurLifeRemain()+pre);
            Pane statusBoard = createStatusBoard(scoreCalculator,timeLifeCalculator);
            Label infoLabel = new Label("剩余时间无法支持本次路线选择无效 游戏结束 请点击结算按钮");
            infoLabel.setLayoutX(100);
            infoLabel.setLayoutY(350);
            root.getChildren().add(infoLabel);

            curLevelCarbonPoint-=curOptionBoard.getCarbonPoint();
            curLevelGemPoint-=curOptionBoard.getGemPoint();
            curLevelGemNum--;
            curLevelTimeUse-=pre;
            Pane level = createLevlResultCardWithLabels();
            level.setLayoutX(100);
            level.setLayoutY(400);

            root.getChildren().add(level);


            Button buttonTestToOver = new Button("结算按钮");
            buttonTestToOver.setOnAction(event -> {
                stage.setScene(new GameOverScene(stage,scoreCalculator,timeLifeCalculator).getScene());
            });
            root.getChildren().add(buttonTestToOver);
            base.getChildren().add(root);
            base.getChildren().add(statusBoard); // Add the status board pane to the base VBox


            stage.setScene(new Scene(base, gameMap.getWidth() * TILE_SIZE, gameMap.getHeight() * TILE_SIZE+statusBoardHeight));

            stage.show();
            return;
        }
        Pane statusBoard = createStatusBoard(scoreCalculator,timeLifeCalculator);
        base.getChildren().add(root);
        base.getChildren().add(statusBoard); // Add the status board pane to the base VBox

        //返回Level按钮测试累加分数
        Button buttonTestToHome = new Button("Home");
        buttonTestToHome.setOnAction(event -> {
            stage.setScene(new GameStartScene().createStartScene(stage, scoreCalculator, timeLifeCalculator));
        });
        buttonTestToHome.setVisible(false);
        root.getChildren().add(buttonTestToHome);

        //返回下一关按钮如果分数满足的话 直接下一关
        Button buttonToNextLevel = new Button("To Next Level");
        buttonToNextLevel.setOnAction(event -> {
//            String s = "Level "+(levelIdentifier.charAt(levelIdentifier.length()-1)-'0'+1);
            String s = "Level 1";
            new LevelSelectionScene().openMapPage(stage, s,scoreCalculator,timeLifeCalculator);
        });
        buttonToNextLevel.setVisible(false);
        root.getChildren().add(buttonToNextLevel);



        //返回下一关按钮如果分数不满足的话 游戏失败 结算
        Button buttonTestToOver = new Button("分数不足 结算");
        buttonTestToOver.setOnAction(event -> {
            stage.setScene(new GameOverScene(stage,scoreCalculator,timeLifeCalculator).getScene());
        });
        root.getChildren().add(buttonTestToOver);
        buttonTestToOver.setVisible(false);

        //返回下一关按钮如果分数满足的话 继续玩
        Button buttonKeepLevel = new Button("Keep Playing this Level");
        buttonKeepLevel.setLayoutY(100);
        buttonKeepLevel.setVisible(false);
        buttonKeepLevel.setOnAction(event -> {
            cycle++;
            createMapScene(stage,scoreCalculator, timeLifeCalculator);
        });
        root.getChildren().add(buttonKeepLevel);

        //小人走路
        final int[] pathIndex = {0};  // Index for paths in the route
        final int[] tileIndex = {0};  // Index for tiles within the current path
        final int[] changeCount = {0};
        final Timeline[] timeline = new Timeline[1]; // 声明为数组以在匿名类中引用
        timeline[0] = new Timeline(
                new KeyFrame(Duration.seconds(0.1), event -> {
                    if (pathIndex[0] < route.getPathList().size()) {
                        Path currentPath = route.getPathList().get(pathIndex[0]);
                        if (tileIndex[0] < currentPath.getTileList().size()) {
                            Tile currentTile = currentPath.getTileList().get(tileIndex[0]);
                            double newX = currentTile.x * TILE_SIZE;
                            double newY = currentTile.y * TILE_SIZE;
                            updatePlayerSpriteImage(currentPath.getTrafficType()); // Update image based on TrafficType
                            playerSprite.setX(newX);
                            playerSprite.setY(newY);
                            tileIndex[0]++;
                        } else {
                            tileIndex[0] = 0;  // Reset tile index for next path
                            pathIndex[0]++;  // Move to next path in the route
                        }
                    } else {

                        timeline[0].stop();
                        imageView.setVisible(false);
                        int objectScoreLvel = (levelIdentifier.charAt(levelIdentifier.length()-1)-'0')*10;
                        if(cycle==currentGemList.size()-1){
//                            System.out.println("1");
                            Pane level = createLevlResultCardWithLabels();
                            level.setLayoutX(100);
                            level.setLayoutY(400);

                            root.getChildren().add(level);


                            if(curLevelGemPoint>=objectScoreLvel){buttonToNextLevel.setVisible(true);}else{buttonTestToOver.setVisible(true);}


                        }else if(curLevelGemPoint>=objectScoreLvel){
                            buttonToNextLevel.setVisible(true);
                            buttonKeepLevel.setVisible(true);
                        }
                        else{
                            cycle++;
                            createMapScene(stage,scoreCalculator, timeLifeCalculator);

                        }



                    }
                })
        );




        stage.setScene(new Scene(base, gameMap.getWidth() * TILE_SIZE, gameMap.getHeight() * TILE_SIZE));
        timeline[0].setCycleCount(Timeline.INDEFINITE); // 设置为无限循环
        timeline[0].play(); // 启动时间轴
        stage.show();

    }
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
                imagePath = "/images/player.png"; // Default case for walking or unknown
                break;
        }
        playerSprite.setImage(new Image(getClass().getResourceAsStream(imagePath)));
    }

    private void drawMap(Pane root) {
        // Load the map image
        Image mapImage = new Image(getClass().getResourceAsStream("/images/map_image.png"));
        ImageView mapView = new ImageView(mapImage);

        // Assuming the image size matches the size of the game map in terms of tiles
        mapView.setFitWidth(gameMap.getWidth() * TILE_SIZE);
        mapView.setFitHeight(gameMap.getHeight() * TILE_SIZE);

        // Add the ImageView to the root pane
        root.getChildren().add(mapView);

        // draw transparant rectangles 透明格子
    for (int y = 0; y < gameMap.getHeight(); y++) {
        for (int x = 0; x < gameMap.getWidth(); x++) {
            Rectangle rect = new Rectangle(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            rect.setFill(Color.TRANSPARENT); // Set the fill to transparent
            root.getChildren().add(rect);
        }

    }

//    private Color getTileColor(Tile tile) {
//        // Replace with your actual tile property checks and color assignments
//        if (tile.isRail) {
//            return Color.YELLOW;
//        } else if (tile.isRoad) {
//            return Color.GRAY;
//        } else if (tile.isForbidden) {
//            return Color.LIGHTBLUE;
//        } else {
//            return Color.GREEN;
//        }
    }

    public void addPlayerCharacter(Pane root, PlayerCharacter player) {
//        Image image = new Image("/gui/img/playercharacter.png");
//        playerSprite = new ImageView(image);
//        updatePlayerPosition(player.getX(), player.getY());
//        root.getChildren().add(playerSprite);
        try {
            Image image = new Image(getClass().getResourceAsStream("/images/player.png"));
            if (image.isError()) {
                throw new RuntimeException("Error loading player image.");
            }

            playerSprite = new ImageView(image);
            playerSprite.setFitWidth(TILE_SIZE);  // Set the image view size to fit the tile size
            playerSprite.setFitHeight(TILE_SIZE);
            playerSprite.setX(player.getX() * TILE_SIZE);  // Position the sprite based on the player's grid coordinates
            playerSprite.setY(player.getY() * TILE_SIZE);
            root.getChildren().add(playerSprite);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add player character.", e);
        }
    }
    //更新小人贴图 update playerSprite according to transportation path
    private void updatePlayerSpriteImage(String imagePath) {
        Image newImage = new Image(getClass().getResourceAsStream(imagePath));
        playerSprite.setImage(newImage);
    }

    public void updatePlayerPosition(int x, int y) {
        if (playerSprite != null) {
            playerSprite.setX(x * TILE_SIZE);
            playerSprite.setY(y * TILE_SIZE);
        }
    }

    // dynamically generate texture for map's tiles and paint it
    private ImagePattern getTilePattern(Tile tile) {
        Image image = null;
        if (tile.isRail) {
            image = new Image("/images/rail.png"); // Update the path to your rail image
        } else if (tile.isRoad) {
            image = new Image("/images/road.jpg"); // Update the path to your road image
        } else if (tile.isForbidden) {
            image = new Image("/images/water.png"); // Update the path to your forbidden image
        } else {
            // Default tile image, for example, grass
            image = new Image("/images/grass.jpg"); // Update the path to your grass image
        }
        return new ImagePattern(image);
    }

    //Saves textured map
    private void savePaneAsImage(Pane pane, String filename) {
        // Take a snapshot of the pane
        WritableImage writableImage = pane.snapshot(new SnapshotParameters(), null);

        // Convert to BufferedImage
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);

        // Write the image to a file
        File outFile = new File(filename);
        try {
            ImageIO.write(bufferedImage, "png", outFile);
            System.out.println("Map saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save map.");
        }
    }
    private  List<Gem> generateGems(int count) {
        List<Gem> gems = new ArrayList<Gem>();
        for (int i = 0; i < count; i++) {
            Gem newGem = null;
            do {
                newGem = new Gem();
                newGem.collected=false;// Gem constructor assigns type, x, and y randomly
            } while (gameMap.getTile(newGem.getX(), newGem.getY()).isForbidden || gameMap.getTile(newGem.getX(), newGem.getY()).isRail);
            gems.add(newGem);
        }
//        System.out.println("Generated Gems:");
//        for (Gem gem : gems) {
//            System.out.println("Gem ID: " + gem.getGemID() +
//                    ", Type: " + gem.getType() +
//                    ", X: " + gem.getX() +
//                    ", Y: " + gem.getY() +
//                    ", Live Time: " + gem.getLiveTime());
//        }
        return gems;
    }

    public  void initializeGemSequence() {
        int num = (levelIdentifier.toCharArray()[levelIdentifier.length() - 1] - '0') + 1;
        List<Gem> gems = generateGems(num); // Generate 5 gems
        currentGemList = new LinkedList<>(gems);
//        displayNextGem(levelIdentifier,gemQueue);
                // Start the timer for the gem's display
//                startGemTimer(gem);
//                System.out.println(i);
    }
//
//        return gems;

    private void bindTimer(ImageView imageView, int durationSeconds) {
        Duration duration = Duration.seconds(durationSeconds);
        Timeline timeline = new Timeline(new KeyFrame(duration, event -> {
            // 当时间到达指定值时执行的操作
            imageView.setVisible(false); // 或者从父节点中移除 imageView
        }));
        timeline.play(); // 启动计时器
    }

    private void displayNextGem(String levelIdentifier, Queue<Gem> gemQueue) {




            if (currentGemSprite != null) {
                root.getChildren().remove(currentGemSprite); // Remove the previous gem sprite
            }

            Gem gem = this.gemQueue.poll(); // Retrieve and remove the next gem
            ImageView imageView;

            if (gem != null) {
                Image image = new Image(getClass().getResourceAsStream("/images/"+gem.getType()+".png"));
                imageView = new ImageView(image);
                imageView.setX(gem.getX() * TILE_SIZE);
                imageView.setY(gem.getY() * TILE_SIZE);
                imageView.resize(10,10);
                bindTimer(imageView,gem.getLiveTime());
                currentGemSprite = new ImageView(image);
                currentGemSprite.setX(gem.getX() * TILE_SIZE);
                currentGemSprite.setY(gem.getY() * TILE_SIZE);
                currentGemSprite.resize(10,10);
                root.getChildren().add(imageView);

                // Start the timer for the gem's display
//                startGemTimer(gem);
//                System.out.println(i);
            }
        }

    private Pane createStatusBoard(PlayerCharacter player) {
        // Create a horizontal box to hold the status labels
        HBox statusBoard = new HBox(10);
        statusBoard.setPadding(new Insets(10));
        statusBoard.setStyle("-fx-background-color: #336699;"); // Set a background color

        // Create labels for the player status
        carbonFootprintLabel = new Label("Carbon Footprint: " + player.getCarbonFootprint());
        timeLeftLabel = new Label("Time Left: " + player.getRemainingFictionalTime());
        scoreLabel = new Label("Score: " + player.getScore());

        // Add labels to the status board
        statusBoard.getChildren().addAll(carbonFootprintLabel, timeLeftLabel, scoreLabel);

        // Return the status board pane
        return statusBoard;
    }

    // Methods to update status board
    public void updateStatusBoard(PlayerCharacter player) {
        // Assuming you have getter methods in PlayerCharacter
        carbonFootprintLabel.setText("Carbon Footprint: " + player.getCarbonFootprint());
        timeLeftLabel.setText("Time Left: " + player.getRemainingFictionalTime());
        scoreLabel.setText("Score: " + player.getScore());
    }

    //CONTROLLER Call this method whenever you update the player character's attributes
    public void playerAttributesChanged(PlayerCharacter player) {
        // Run the update in the UI thread
        Platform.runLater(() -> updateStatusBoard(player));
    }




    private void startGemTimer(Gem gem) {
        new Thread(() -> {
            try {
                Thread.sleep(gem.getLiveTime() * 1000); // Convert seconds to milliseconds
                Platform.runLater(() -> {
                    if (!gem.isCollected()) { // Check if not collected
                        displayNextGem(levelIdentifier, gemQueue); // Display next gem
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public Pane createLevlResultCardWithLabels() {
        // 创建一个 Pane 作为卡片背景
        Pane cardPane = new Pane();
        cardPane.setPrefSize(300, 200); // 设置卡片大小
        cardPane.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;"); // 设置样式

        // 创建三个 Label
        Label labelCarbonPoint = new Label("Carbon FootPrint Gained in this Level: "+curLevelCarbonPoint);
        labelCarbonPoint.setLayoutX(10);
        labelCarbonPoint.setLayoutY(20);

        Label labelGemPoint = new Label("Gem Point Gained in this Level:: "+curLevelGemPoint);
        labelGemPoint.setLayoutX(10);
        labelGemPoint.setLayoutY(50);

        Label labelGemNum = new Label("Num of Gem Collected in this Level: "+curLevelGemNum);
        labelGemNum.setLayoutX(10);
        labelGemNum.setLayoutY(80);

        Label labelTime = new Label("Time cost in this Level: "+curLevelTimeUse);
        labelTime.setLayoutX(10);
        labelTime.setLayoutY(110);

        // 将 Label 添加到 Pane 中
        cardPane.getChildren().addAll(labelCarbonPoint, labelGemPoint, labelGemNum,labelTime);

        return cardPane;
    }


//    }
}