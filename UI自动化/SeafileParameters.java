import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SeafileParameters {
    //定义变量
    WebDriver driver;
    String url = "http://172.31.128.199:8000";

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
        //使用火狐浏览器下载时，浏览器需要询问下载路径，则需要对浏览器选项进行设置
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream,image/png,text/html");
        driver = new FirefoxDriver(options);
    }

    @AfterMethod
    public void tearDown() {
        //关闭窗口
        driver.close();
    }
    @DataProvider
    public  Object[][] testData(){
        return new Object[][] {
                {"wjh@51testing.com","123456"},
                {"wjh1@51testing.com","123456"},
                {"admin@51testing.com","seafile123456"}
        };
    }
    @Test(dataProvider ="testData")
    public void testLogin(String email,String passwd) throws InterruptedException {
        driver.get(url);
        //定位登录邮箱、密码、登录按钮元素，并进行登录操作
        driver.findElement(By.name("login")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(passwd);
        driver.findElement(By.cssSelector(".submit")).click();
        //登录后隐式等待2000ms，以便找到后续元素
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
        Thread.sleep(2000);
        //验证登录是否成功，则需要定位登录成功后的标志，再进行断言
        //点击登录后的头像
        driver.findElement(By.xpath("//*[@id=\"my-info\"]/img")).click();
        //获取用户信息，并将相关用户信息赋值给text变量
        String text= driver.findElement(By.xpath("//*[@id=\"space-traffic\"]/div/p")).getText();
        //断言用户信息
        assertTrue(text.startsWith("已用空间"));
        //延迟2s
        Thread.sleep(2000);
       /* [点击右上角用户图标]这一步骤在登录后退出用例中需要去掉，原因是在登录后断言时已经点击头像获取用户信息，后续在需要退出时，就无需再次操作
        driver.findElement(By.cssSelector(".avatar")).click();
        Thread.sleep(3000);*/
        //点击退出按钮
        driver.findElement(By.xpath("//*[@id=\"logout\"]")).click();
        Thread.sleep(2000);
        //断言
        String txt = driver.findElement(By.xpath("//*[@id='main']/div/p")).getText();
        assertEquals(txt, "感谢参与！重新登录");
        //点击重新登录超链接
        driver.findElement(By.linkText("重新登录")).click();
    }
}
