package controller;

/**
 * Created by alex on 2/22/15.
 */
public enum Configuration
{
    /**
     * Simple configuration for the controllers and connected GUIs.
     */
    BASE("window/base.fxml", "Boat Registry", 800, 650),
    MEMBER_NEW("widget/member/new.fxml", "New Member", 300, 200),
    MEMBER_EDIT("widget/member/edit.fxml", "Edit Member", 300, 200),
    BOAT_NEW("widget/boat/new.fxml", "New Boat", 300, 400),
    BOAT_EDIT("widget/boat/edit.fxml", "Edit Boat", 300, 400),
    DIALOG("dialog/message.fxml", "Alert", 340, 140);


    private int width;
    private int height;
    private String layout;
    private String title;

    private Configuration(String layout, String title, int width, int height)
    {
        this.width = width;
        this.height = height;
        this.layout = layout;
        this.title = title;
    }

    public String getLayout() { return layout; }
    public String getTitle() { return title; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
