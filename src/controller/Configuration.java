/**
 *
 * @filename Configurator
 *
 * @date 2015-02-22
 *
 * Configurator enum for setting template path, windows sizes etc.
 */

package controller;

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
    ABOUT("widget/about/about.fxml", "About", 400, 250),
    DIALOG("dialog/message.fxml", "Alert", 340, 140);

    private final String layout;
    private final String title;
    private final int width;
    private final int height;

    Configuration(String layout, String title, int width, int height)
    {
        this.layout = layout;
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public String getLayout() { return layout; }
    public String getTitle() { return title; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
