package constants;

public class Constant {
public static final String username ="Your@Email.com";
public static final String password ="YourPassword";
public static final String NICKNAME ="YourInstagramNickName";
public static final String HREF_XPATH = String.format("//a[contains(@href,\"%s\")]",NICKNAME);
public static final String FOLLOWERS_XPATH = String.format("//a[contains(@href,\"%s/followers\")]",NICKNAME);
public static final String FOLLOWING_XPATH = String.format("//a[contains(@href,\"%s/following\")]",NICKNAME);
public static final int FOLLOWER_COUNT =1000;
public static final int FOLLOWING_COUNT =1000;
}
