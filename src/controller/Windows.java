package controller;

/**
 * Created by alex on 2/22/15.
 */
public enum Windows
{
    /**
     * Simple configuration for the controllers and connected GUIs.
     */
    BASE("window/base.fxml", "Boat Registry", 800, 650),
    MEMBER_NEW("dialog/member/new.fxml", "New Member", 300, 200),
    MEMBER_EDIT("dialog/member/edit.fxml", "Edit Member", 300, 200),
    BOAT_NEW("dialog/boat/new.fxml", "New Boat", 300, 400),
    BOAT_EDIT("dialog/boat/edit.fxml", "Edit Boat", 300, 400);

    private int width;
    private int height;
    private String layout;
    private String title;

    private Windows(String layout, String title, int width, int height)
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
