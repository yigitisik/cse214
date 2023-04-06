/**
 * Slide class is designed to hold place of a slide and its attributes such as title, bullets, and duration.
 * Mustafa Yigit Isik
 * 113080465
 * CSE214_hw2
 * Rec 02 Jamieson Barkume - Steven Secreti
 */
import java.util.Arrays;
public class Slide {
    public static int MAX_BULLETS = 5;
    private String title;
    private String[] bullets;
    private double duration;
    /**
     * empty constructor
     * no @param taken in
     */
    public Slide() {
        this.title = null;
        this.bullets = new String[MAX_BULLETS];
        this.duration = 0;
    }

    /**
     * @return String variable title
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets title to new param:
     * @param newTitle is used for setting
     */
    public void setTitle(String newTitle) {
        if (newTitle == null) {
            throw new IllegalArgumentException("Can't have null for title.");
        }
        this.title = newTitle;
    }

    /**
     * gets the requested bullet
     * @param i used for indexing
     * @return the bullet that is requested
     */
    public String getBullet(int i) {
        if (i < 1 || i > MAX_BULLETS) {
            throw new IllegalArgumentException("Can't have '< 1' or '> MAX_BULLETS'");
        }
        return this.bullets[i - 1];
    }

    /**
     * sets the requested bullet
     * @param i used for indexing
     * @param newBullet used for changing the bullet
     */
    public void setBullet(int i, String newBullet) {
        if (i < 1 || i > MAX_BULLETS) {
            throw new IllegalArgumentException("Can't have '< 1' or '> MAX_BULLETS'");
        }

        if (i > 1 && newBullet != null) {
            if (bullets[i - 2] == null)
                setBullet(i - 1, newBullet);
            else {
                bullets[i - 1] = newBullet;
            }
        } else if (newBullet == null) {
            if (i == 1) {
                bullets[i - 1] = null;
                while (i < 5) {
                    bullets[i - 1] = bullets[i];
                    i++;
                }
            } else {
                bullets[i - 1] = null;
                setBullet(i - 1, bullets[i - 2]);
            }
        } else {
            bullets[i - 1] = newBullet;
        }
    }

    /**
     * gets the duration
     * @return duration
     */
    public double getDuration() {
        return duration;
    }

    /**
     * sets the duration
     * @param newDuration used for changing the duration
     */
    public void setDuration(double newDuration) {
        if (newDuration > 0) {
            this.duration = newDuration;
        } else {
            throw new IllegalArgumentException("Invalid Duration");
        }
    }

    /**
     * gets the number of bullets
     * @return total number of bullets that are not null ~ empty
     */
    public int getNumBullets() {
        int bulletCounter = 0;
        for (String bullet : bullets) {
            if (bullet != null && !bullet.equals("")) {
                bulletCounter++;
            }
        }
        return bulletCounter;
    }

    /**
     * overridden equals method
     * @param o is for what you compare
     * @return True/False depending on equality check
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Slide)) {
            return false;
        }
        Slide slide = (Slide) o;
        return Double.compare(slide.duration, this.duration) == 0 &&
                this.title.equals(slide.title) &&
                Arrays.equals(this.bullets, slide.bullets);
    }

    /**
     * overridden toString method
     * @return info of slide in a formatted form
     */
    @Override
    public String toString() {
        String s = "==============================================" +
                "\nTitle: " + this.title +
                "\n==============================================";
        for (int i = 1; i < 6; i++) {
            if (bullets[i - 1] != null) {
                s += "\n" + i + ". " + bullets[i - 1];
            }
        }
        s += "\n==============================================\n";
        return s;
    }
}