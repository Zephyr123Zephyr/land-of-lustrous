package org.example.landoflustrous.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.InputStream;

public class LevelSelectionScene {

    public Scene createLevelSelectionScene(Stage stage) {
        // Create the grid pane for the images
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

//        // Define each ImageView and Button
//        ImageView imageView1 = createImageView("/gui/img/image1.jpg");
//        Button button1 = createImageButton(imageView1, () -> openMapPage(stage, "Map 1"));
//
//        ImageView imageView2 = createImageView("/gui/img/image1.jpg");
//        Button button2 = createImageButton(imageView2, () -> openMapPage(stage, "Map 2"));
//
//        ImageView imageView3 = createImageView("/gui/img/image1.jpg");
//        Button button3 = createImageButton(imageView3, () -> openMapPage(stage, "Map 3"));
//
//        ImageView imageView4 = createImageView("/gui/img/image1.jpg");
//        Button button4 = createImageButton(imageView4, () -> openMapPage(stage, "Map 4"));
        // Modified Implementation to interact with updated openMapPage method. Difference: parameter takes 'Level 1' instead of 'Map 1'
        ImageView imageView1 = createImageView("/images/image1.jpg");
        Button button1 = createImageButton(imageView1, () -> openMapPage(stage, "Level 1"));

        ImageView imageView2 = createImageView("/images/image2.jpg");
        Button button2 = createImageButton(imageView2, () -> openMapPage(stage, "Level 2"));

        ImageView imageView3 = createImageView("/images/image3.jpg");
        Button button3 = createImageButton(imageView3, () -> openMapPage(stage, "Level 3"));

        ImageView imageView4 = createImageView("/images/image4.jpg");
        Button button4 = createImageButton(imageView4, () -> openMapPage(stage, "Level 4"));
        // Add buttons to the grid instead of ImageViews
        grid.add(button1, 0, 0);
        grid.add(button2, 1, 0);
        grid.add(button3, 0, 1);
        grid.add(button4, 1, 1);

        // Create the "Return" button
        Button returnBtn = new Button("Return");
        // Sets the scene to the main scene when "Return" is clicked. This action should be defined in Main.
        returnBtn.setOnAction(e -> stage.setScene(new GameStartScene().createStartScene(stage))); // Adjust according to Main class

        // Create the root stack pane and add the grid and the button
        StackPane rootStack = new StackPane();
        rootStack.getChildren().addAll(grid, returnBtn);
        StackPane.setAlignment(returnBtn, Pos.BOTTOM_CENTER);
        StackPane.setMargin(returnBtn, new Insets(0, 0, 20, 0));

        // Create the scene with the stack pane as the root
        return new Scene(rootStack, 640, 480);
    }

    private ImageView createImageView(String imagePath) {
        InputStream is = getClass().getResourceAsStream(imagePath);
        if (is == null) {
            throw new RuntimeException("Cannot find resource " + imagePath);
        }
        ImageView imageView = new ImageView(new Image(is));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private Button createImageButton(ImageView imageView, Runnable action) {
        Button button = new Button();
        button.setGraphic(imageView);
        button.setOnAction(e -> action.run());
        return button;
    }

    //    private void openMapPage(Stage stage, String mapName) {
//        Label mapLabel = new Label(mapName); // For demonstration
//        VBox mapPageLayout = new VBox(mapLabel);
//        mapPageLayout.setAlignment(Pos.CENTER);
//        Scene mapPageScene = new Scene(mapPageLayout, 300, 200);
//        stage.setScene(mapPageScene);
//    }
    // Modified implementation to link with MapViewerScene.java.
//    private void openMapPage(Stage stage, String levelIdentifier) {
//        MapViewerScene mapViewer = new MapViewerScene(levelIdentifier); // Use the level identifier directly
//        Scene mapScene = mapViewer.createMapScene();
//        stage.setScene(mapScene);
//    }
    private void openMapPage(Stage stage, String levelIdentifier) {
        MapViewerScene mapViewer = new MapViewerScene(levelIdentifier); // Use the level identifier directly
//        Scene mapScene = mapViewer.createMapScene();
        mapViewer.createMapScene(stage);

    }

}



//public class LevelSelection extends Application {
//
//        private Scene levelSelectionScene; // Store the level selection scene
//
//        public static void main(String[] args) {
//            Application.launch(args);
//        }
//
//        @Override
//        public void start(Stage primaryStage) {
//            primaryStage.setTitle("Level Selection");
//            levelSelectionScene = createLevelSelectionScene(primaryStage);
//            primaryStage.setScene(levelSelectionScene);
//            primaryStage.show();
//        }
//
//        private Scene createLevelSelectionScene(Stage stage) {
//            // Create the grid pane for the images
//            GridPane grid = new GridPane();
//            grid.setAlignment(Pos.CENTER);
//            grid.setHgap(10);
//            grid.setVgap(10);
//            grid.setPadding(new Insets(25, 25, 25, 25));
//
//            // ORIGINAL IMPLEMENTATION
//            // Define each ImageView and Button
//            // ImageView imageView1 = createImageView("/image1.jpg");
//            // Button button1 = createImageButton(imageView1, () -> openMapPage(stage, "Map 1"));
//
//            // ImageView imageView2 = createImageView("/image2.jpg");
//            // Button button2 = createImageButton(imageView2, () -> openMapPage(stage, "Map 2"));
//
//            // ImageView imageView3 = createImageView("/image3.jpg");
//            // Button button3 = createImageButton(imageView3, () -> openMapPage(stage, "Map 3"));
//
//            // ImageView imageView4 = createImageView("/image4.jpg");
//            // Button button4 = createImageButton(imageView4, () -> openMapPage(stage, "Map 4"));
//
//
//            // Modified Implementation to interact with updated openMapPage method. Difference: parameter takes 'Level 1' instead of 'Map 1'
//            ImageView imageView1 = createImageView("/gui/img/image1.jpg");
//            Button button1 = createImageButton(imageView1, () -> openMapPage(stage, "Level 1"));
//
//            ImageView imageView2 = createImageView("/gui/img/image2.jpg");
//            Button button2 = createImageButton(imageView2, () -> openMapPage(stage, "Level 2"));
//
//            ImageView imageView3 = createImageView("/gui/img/image3.jpg");
//            Button button3 = createImageButton(imageView3, () -> openMapPage(stage, "Level 3"));
//
//            ImageView imageView4 = createImageView("gui/img/image4.jpg");
//            Button button4 = createImageButton(imageView4, () -> openMapPage(stage, "Level 4"));
//
//            // Add buttons to the grid instead of ImageViews
//            grid.add(button1, 0, 0);
//            grid.add(button2, 3, 0);
//            grid.add(button3, 0, 3);
//            grid.add(button4, 3, 3);
//
//            // Create the "Return" button
//            Button returnBtn = new Button("Return");
//            // Sets the scene to the play scene when "Return" is clicked.
//            returnBtn.setOnAction(e -> stage.setScene(createPlayScene(stage)));
//
//            // Create the root stack pane and add the grid and the button
//            StackPane rootStack = new StackPane();
//            rootStack.getChildren().addAll(grid, returnBtn);
//            StackPane.setAlignment(returnBtn, Pos.BOTTOM_CENTER);
//            StackPane.setMargin(returnBtn, new Insets(0, 0, 20, 0));
//
//            // Create the scene with the stack pane as the root
//            return new Scene(rootStack, 640, 480);
//        }
//
//        private ImageView createImageView(String imagePath) {
//            InputStream is = getClass().getResourceAsStream(imagePath);
//            if (is == null) {
//                throw new RuntimeException("Cannot find resource " + imagePath);
//            }
//            ImageView imageView = new ImageView(new Image(is));
//            imageView.setFitHeight(100);
//            imageView.setFitWidth(100);
//            imageView.setPreserveRatio(true);
//            return imageView;
//        }
//
//        private Button createImageButton(ImageView imageView, Runnable action) {
//            Button button = new Button();
//            button.setGraphic(imageView);
//            button.setOnAction(e -> action.run());
//            return button;
//        }
//
//        // ORIGINAL IMPLEMENTATION
//        // private void openMapPage(Stage stage, String mapName) {
//        //     Label mapLabel = new Label(mapName); // For demonstration
//        //     VBox mapPageLayout = new VBox(mapLabel);
//        //     mapPageLayout.setAlignment(Pos.CENTER);
//        //     Scene mapPageScene = new Scene(mapPageLayout, 300, 200);
//        //     stage.setScene(mapPageScene);
//
//
//        // Modified implementation to link with MapViewerScene.java.
//        private void openMapPage(Stage stage, String levelIdentifier) {
//            MapViewerScene mapViewer = new MapViewerScene (levelIdentifier); // Use the level identifier directly
//            Scene mapScene = mapViewer.createMapScene();
//            stage.setScene(mapScene);
//        }
//
//        private Scene createReturnScene(Stage stage) {
//            // Create a "Play" button that when clicked will set the scene back to levelSelectionScene
//            Button playButton = new Button("Play");
//            playButton.setOnAction(e -> stage.setScene(levelSelectionScene));
//
//            // Layout for the "Play" button
//            VBox playLayout = new VBox(10, playButton);
//            playLayout.setAlignment(Pos.CENTER);
//
//            // Create and return the scene
//            return new Scene(playLayout, 300, 200);
//        }
//
//
//        //Create the scene that connect to "return" button.
//        // Temporarily call it "PlayScene", it should be the "OpenScene" for the real game.
//        private Scene createPlayScene(Stage stage) {
//            Button playButton = new Button("Play");
//            // When "Play" is clicked, go back to the level selection scene
//            playButton.setOnAction(e -> stage.setScene(levelSelectionScene));
//
//            StackPane playRoot = new StackPane(playButton);
//            playRoot.setAlignment(Pos.CENTER);
//
//            return new Scene(playRoot, 200, 100);
//        }
//}
//
//public class LevelSelection {
//
//    public Scene createLevelSelectionScene(Stage stage) {
//        // Create the grid pane for the images
//        GridPane grid = new GridPane();
//        grid.setAlignment(Pos.CENTER);
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(25, 25, 25, 25));
//
//        // Define each ImageView and Button
//        ImageView imageView1 = createImageView("/image1.jpg");
//        Button button1 = createImageButton(imageView1, () -> openMapPage(stage, "Map 1"));
//
//        ImageView imageView2 = createImageView("/image2.jpg");
//        Button button2 = createImageButton(imageView2, () -> openMapPage(stage, "Map 2"));
//
//        ImageView imageView3 = createImageView("/image3.jpg");
//        Button button3 = createImageButton(imageView3, () -> openMapPage(stage, "Map 3"));
//
//        ImageView imageView4 = createImageView("/image4.jpg");
//        Button button4 = createImageButton(imageView4, () -> openMapPage(stage, "Map 4"));
//
//        // Add buttons to the grid instead of ImageViews
//        grid.add(button1, 0, 0);
//        grid.add(button2, 1, 0);
//        grid.add(button3, 0, 1);
//        grid.add(button4, 1, 1);
//
//        // Create the "Return" button
//        Button returnBtn = new Button("Return");
//        // Sets the scene to the main scene when "Return" is clicked. This action should be defined in Main.
//        returnBtn.setOnAction(e -> stage.setScene(Main.homePage)); // Adjust according to Main class
//
//        // Create the root stack pane and add the grid and the button
//        StackPane rootStack = new StackPane();
//        rootStack.getChildren().addAll(grid, returnBtn);
//        StackPane.setAlignment(returnBtn, Pos.BOTTOM_CENTER);
//        StackPane.setMargin(returnBtn, new Insets(0, 0, 20, 0));
//
//        // Create the scene with the stack pane as the root
//        return new Scene(rootStack, 640, 480);
//    }
//
//    private ImageView createImageView(String imagePath) {
//        InputStream is = getClass().getResourceAsStream(imagePath);
//        if (is == null) {
//            throw new RuntimeException("Cannot find resource " + imagePath);
//        }
//        ImageView imageView = new ImageView(new Image(is));
//        imageView.setFitHeight(100);
//        imageView.setFitWidth(100);
//        imageView.setPreserveRatio(true);
//        return imageView;
//    }

