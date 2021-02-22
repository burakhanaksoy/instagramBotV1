package instaBot;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.*;

import static constants.Constant.*;

public class Bot {

        WebDriver driver = ChromeInitiator.initiateChrome();
        JavascriptExecutor js = (JavascriptExecutor) driver; // for scrolling action

    @Test
    public void instaBotStart() throws InterruptedException {

        driver.get("https://www.instagram.com/");

        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[contains(text(),\"Phone number\")]//following::input")).sendKeys(username);
        driver.findElement(By.xpath("//span[contains(text(),\"Password\")]//following::input")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"loginForm\"]/div/div[3]/button/div")).click();
        Thread.sleep(3000);
        String notNowText = driver.findElement(By.xpath("//*[contains(text(),'Not Now')]")).getText();
        if(notNowText != null){
            driver.findElement(By.xpath("//*[contains(text(),'Not Now')]")).click();
        }
        driver.findElement(By.xpath("/html/body/div[4]/div/div/div/div[3]/button[2]")).click();

        driver.findElement(By.xpath(HREF_XPATH)).click();

        //Here starts comparison of followers and unfollowers
        // Create a new arraylist for followers
        List<String> followerList = new ArrayList<>();
        List<String> followingList = new ArrayList<>();
        // click on followers and use javascript for scroll action

        driver.findElement(By.xpath(FOLLOWERS_XPATH)).click();
        Thread.sleep(2000);

        WebElement scrollFollowers = driver.findElement(By.className("isgrP"));

        for(int i =0; i<FOLLOWER_COUNT ; i++) {

            js.executeScript("arguments[0].scrollBy(0,arguments[0].scrollHeight*(0.39))", scrollFollowers);
            String followerXPath = String.format("//div[contains(@class,\"isgrP\")]//following::span[contains(@class,\"Jv7Aj\")][%d]",i);
            Thread.sleep(300);
            String follower = driver.findElement(By.xpath(followerXPath)).getText();
            System.out.println(follower);
            followerList.add(follower);
        }
        //Close follower tab
        driver.findElement(By.xpath("//*[@class=\"WaOAr\"]//button")).click();

        //Open following tab
        driver.findElement(By.xpath(FOLLOWING_XPATH)).click();
        WebElement scrollFollowing = driver.findElement(By.className("isgrP"));
        Thread.sleep(2000);
        for(int i =1; i<FOLLOWING_COUNT; i++){
            js.executeScript("arguments[0].scrollBy(0,arguments[0].scrollHeight*(0.39))", scrollFollowing);
            String followingXPath = String.format("//div[contains(@class,\"isgrP\")]//following::span[contains(@class,\"Jv7Aj\")][%d]",i);
            Thread.sleep(300);
            String following = driver.findElement(By.xpath(followingXPath)).getText();
            System.out.println(following);
            followingList.add(following);
        }

        //Create sets for removing common elements
        Set<String> followers = new HashSet<>(followerList);
        Set<String> following = new HashSet<>(followingList);


        String[] arrayFollowers = followerList.toArray(new String[0]);
        String[] arrayFollowing = followingList.toArray(new String[0]);

        System.out.println("Followers: "+ Arrays.toString(arrayFollowers) );
        System.out.println("Following: "+ Arrays.toString(arrayFollowing));

        following.removeAll(followers);

        Object[] unfollowers = following.toArray();
        System.out.println("\nUnfollows you: "+Arrays.toString(unfollowers));



    }

    @AfterClass
    public void tearDownSuite(){
        driver.close();
    }

}
