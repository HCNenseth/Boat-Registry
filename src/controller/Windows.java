package controller;

/**
 * Created by alex on 2/22/15.
 */
public enum Windows
{
    BASE("../layout/base.fxml", "Boat Registry", 800, 650),
    MEMBER_NEW("../layout/member_new.fxml", "New Member", 300, 200),
    MEMBER_EDIT("../layout/member_edit.fxml", "Edit Member", 300, 200),
    BOAT_NEW("../layout/boat_new.fxml", "New Boat", 300, 400),
    BOAT_EDIT("../layout/boat_edit.fxml", "Edit Boat", 300, 400);

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
