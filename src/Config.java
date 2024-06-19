public class Config {
    private String imagePath;
    private int imageSize;
    private boolean borderless;
    private boolean alwaysOnTop;
    private boolean clickThrough;
    
    public Config() {
        this.imagePath = "";
        this.imageSize = 100;
        this.borderless = true;
        this.alwaysOnTop = true;
        this.clickThrough = false;
    }

    public Config(String imagePath, int imageSize, boolean borderless, boolean alwaysOnTop, boolean clickThrough) {
        this.imagePath = imagePath;
        this.imageSize = imageSize;
        this.borderless = borderless;
        this.alwaysOnTop = alwaysOnTop;
        this.clickThrough = clickThrough;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getImageSize() {
        return this.imageSize;
    }

    public void setImageSize(int imageSize) {
        this.imageSize = imageSize;
    }

    public boolean isBorderless() {
        return this.borderless;
    }

    public void setBorderless(boolean borderless) {
        this.borderless = borderless;
    }

    public boolean isAlwaysOnTop() {
        return this.alwaysOnTop;
    }

    public void setAlwaysOnTop(boolean alwaysOnTop) {
        this.alwaysOnTop = alwaysOnTop;
    }

    public boolean isClickThrough() {
        return this.clickThrough;
    }

    public void setClickThrough(boolean clickThrough) {
        this.clickThrough = clickThrough;
    }

    public String toString() {
        return String.format("%s|%s|%s|%s|%s", imagePath, imageSize, borderless, alwaysOnTop, clickThrough);
    }
}


