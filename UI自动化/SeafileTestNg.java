import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class SeafileTestNg {

    //定义变量
    WebDriver driver;
    String url = "http://172.31.128.199:8000";
    String email = "wjh@51testing.com";
    String passwd = "123456";

    /* 多个测试用例中的前置条件中均需要先登录，故将登录方法，封装为私有方法，供其他用例调用 */
    private void login(String email, String passwd) {
        driver.get(url);
        //定位登录邮箱、密码、登录按钮元素，并进行登录操作
        driver.findElement(By.name("login")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys(passwd);
        driver.findElement(By.cssSelector(".submit")).click();
        //登录后隐式等待2000ms，以便找到后续元素
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
    }


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

    @Test /* 01 登录*/
    public void test01Login() throws InterruptedException {
        //已封装登录方法，则可直接调用登录方法
        login(email, passwd);
        Thread.sleep(2000);
        //验证登录是否成功，则需要定位登录成功后的标志，再进行断言
        //点击登录后的头像
        driver.findElement(By.id("my-info")).click();
        //获取用户名，并将用户名赋值给name变量
        String name = driver.findElement(By.xpath("//*[@id=\"user-info-popup\"]/div[2]/div[1]/div")).getText();
        //断言用户名
        assertEquals("wjh", name);

    }

    @Test/* 02 新建加密资料库*/
    public void test02NewRepo() throws InterruptedException {
        //先登录，调用login方法；
        login(email, passwd);
        //定位新建资料库元素
        driver.findElement(By.xpath("//div[1]/div[1]/button/span[2]")).click();
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
        //定位资料库名称，并输入资料库名称
        driver.findElement(By.name("repo_name")).sendKeys("xjjmzlk");
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
        //定位加密资料库复选框
        driver.findElement(By.id("encrypt-switch")).click();
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
        //定位密码输入框
        driver.findElement(By.id("passwd")).sendKeys("12345678");
        driver.findElement(By.id("passwd-again")).sendKeys("12345678");
        //定位新建资料库中的提交按钮
        driver.findElement(By.cssSelector(".submit")).click();
        //强制等待2s，以便提交后窗口关闭，页面刷新
        Thread.sleep(2000);

        //获取资料库名称，并将获取到的资料库名称赋值给zlkmc变量
        String zlkmc = driver.findElement(By.xpath("//*[@id=\"my-repos\"]/div/div[2]/table/tbody/tr/td[2]/span/a")).getText();
        //断言资料库名称
        assertEquals("xjjmzlk", zlkmc);
    }
    @Test  /* 03 删除资料库*/
    public void test03DeleteRepo() throws InterruptedException {
        //先登录，调用login方法；
        login(email, passwd);

        //定位删除悬浮按钮，需要创建动作类对象
        Actions actions=new Actions(driver);
        //定位删除悬浮按钮，并将鼠标移动到对应的删除悬浮元素上
        WebElement element=driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]"));
        actions.moveToElement(element).build().perform();
        Thread.sleep(2000);
        //点击删除按钮
        driver.findElement(By.xpath("//table/tbody/tr[1]/td[3]/div/a[2]")).click();
        Thread.sleep(2000);
        //确定删除资料库
        driver.findElement(By.cssSelector("#confirm-yes")).click();
        Thread.sleep(2000);

        //获取删除成功提示语，并赋值给tips变量
        String tips =driver.findElement(By.xpath("//*[@id=\"main\"]/ul/li")).getText();
        //断言删除成功提示语
        assertEquals("删除成功。",tips);
        assertTrue(tips.startsWith("删除成功"));// 断言提示以删除成功开始

    }

    @Test/* 04 新建普通资料库*/
    public void test04NewPTRepon() throws InterruptedException {
        //先登录，调用login方法；
        login(email, passwd);
        //定位新建资料库按钮
        driver.findElement(By.xpath("//*[@id=\"my-repos-toolbar\"]/button/span[2]")).click();
        //定位新建资料库名称输入框
        driver.findElement(By.id("repo-name")).sendKeys("ptzlk");
        //定位提交按钮
        driver.findElement(By.cssSelector(".submit")).click();
        Thread.sleep(2000);
        //获取资料库名称，并将获取到的资料库名称赋值给zlkmc变量
        String zlkmc1 = driver.findElement(By.xpath("//*[@id=\"my-repos\"]/div/div[2]/table/tbody/tr/td[2]/span/a")).getText();
        //断言资料库名称
        assertEquals("ptzlk", zlkmc1);

    }

    @Test/* 05 上传文件*/
    public void test05UploadFile() throws InterruptedException {

        //先登录，调用login方法；
        login(email, passwd);

        //选择当前显示第一行的资料库，即进入资料库
        driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]/span/a")).click();
        //上传按钮为input标签，定位上传按钮
        WebElement input=driver.findElement(By.cssSelector("#advanced-upload-file-input"));
        Thread.sleep(2000);
        //输入文件路径,上传文件
         // input.sendKeys("D:\\SeleniumProject\\uploadfile\\1.png");
        //上传文件路径和文件名 其中 System.getProperty("user.dir") 获取项目根目录
        String uploadfile=System.getProperty("user.dir")+"\\files\\seafile-logo.png";
        input.sendKeys(uploadfile);
        Thread.sleep(5000);
        //获取上传文件状态，并将获取到的文件上传成功的状态赋值给result变量
        String result = driver.findElement(By.xpath("//*[@id=\"upload-file-dialog\"]/div[2]/table/tbody/tr/td[3]/span")).getText();
        //断言已上传状态
        assertEquals("已上传", result);

    }

    @Test/* 06 重命名文件*/
    public void test06Rename() throws InterruptedException {
        //先登录，调用login方法；
        login(email, passwd);
        //选择当前显示第一行的资料库，即进入资料库
         driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]/span/a")).click();
        //选择当前第一个文件
        driver.findElement(By.xpath("//table/tbody/tr/td[4]")).click();
        Thread.sleep(2000);
        //点击下拉选择按钮
        driver.findElement(By.xpath("//table/tbody/tr/td[5]/div/div[2]/a")).click();
        Thread.sleep(2000);
        //点击重命名下拉选择项
        driver.findElement(By.xpath("//*[@id=\"dir-view\"]/div/div[2]/div/table/tbody/tr/td[5]/div/div[2]/div/ul[1]/li[1]/a")).click();
        Thread.sleep(2000);
        //点击重命名文件输入框，并输入新的文件名
        driver.findElement(By.name("newname")).clear();
        driver.findElement(By.name("newname")).sendKeys("abc.png");
        //点击对勾提交按钮
        driver.findElement(By.xpath("//*[@id=\"rename-form\"]/button[1]")).click();
        Thread.sleep(2000);
        //获取重命名后的名称，并赋值给fileNewName
        String fileNewName = driver.findElement(By.xpath("//*[@id=\"dir-view\"]/div/div[2]/div/table/tbody/tr/td[4]/span[1]/a")).getText();
        assertEquals(fileNewName, "abc.png");
    }

    @Test/* 07 下载文件*/
    public void test07Download() throws InterruptedException {
        //先登录，调用login方法；
        login(email, passwd);
        //选择当前显示第一行的资料库，即进入资料库
        driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]/span/a")).click();
        //点击勾选文件前的复选框
        driver.findElement(By.xpath("//table/tbody/tr/td[1]/input")).click();
        //点击下载按钮
        driver.findElement(By.id("download-dirents")).click();
        Thread.sleep(2000);

    }

    @Test  /* 08 删除文件*/
    public void test08DeleteFile() throws InterruptedException {
        //先登录，调用login方法；
        login(email, passwd);
        //选择当前显示第一行的资料库，即进入资料库
        driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]/span/a")).click();
        //点击勾选文件前的复选框
        driver.findElement(By.xpath("//table/tbody/tr/td[1]/input")).click();
        //点击删除按钮
        driver.findElement(By.xpath("//*[@id=\"del-dirents\"]")).click();
        Thread.sleep(2000);
        //点击删除提示框中的确定按钮
        driver.findElement(By.xpath("//*[@id=\"confirm-yes\"]")).click();
        Thread.sleep(2000);
        //获取删除成功提示语，并赋值给tips变量
        String tips =   driver.findElement(By.xpath("//*[@id=\"main\"]/ul/li")).getText();
        //断言删除成功提示语
        assertTrue(tips.startsWith("成功删除"));// 断言提示以成功删除开始

    }

    @Test/* 09 在资料库中，返回资料库列表，再删除资料库*/
    public void test09DeleteRepo() throws InterruptedException {
        //先登录，调用login方法；
        login(email, passwd);
        //选择当前显示第一行的资料库，即进入资料库
        driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]/span/a")).click();
        //返回资料库
        driver.findElement(By.xpath("//div[2]/div[2]/div[2]/div/div[1]/div[1]/a")).click();
        Thread.sleep(2000);
        Actions actions=new Actions(driver);
        //移动鼠标到我的资料库，原因是要改变鼠标焦点，否则会导致定位不到删除按钮
        actions.moveToElement( driver.findElement(By.linkText("我的资料库"))).build().perform();
        Thread.sleep(2000);
        //选中第一条资料库
        driver.findElement(By.xpath("//div[2]/div[2]/div/div[2]/table/tbody/tr[1]/td[2]")).click();
        Thread.sleep(2000);
        //将鼠标移动到删除按钮上
        WebElement element1=driver.findElement(By.xpath("//*[@id=\"my-repos\"]/div/div[2]/table/tbody/tr[1]/td[2]"));
        actions.moveToElement(element1).build().perform();
        Thread.sleep(2000);
        //点击删除按钮
        driver.findElement(By.xpath("//*[@id='my-repos']/div/div[2]/table/tbody/tr[1]/td[3]/div/a[2]")).click();
        Thread.sleep(2000);
        //确定删除资料库
        driver.findElement(By.cssSelector("#confirm-yes")).click();
        Thread.sleep(3000);
        //获取删除成功提示语，并赋值给tips变量
        String tips =driver.findElement(By.xpath("//*[@id=\"main\"]/ul/li")).getText();
        //断言删除成功提示语
        assertEquals("删除成功。",tips);
        assertTrue(tips.startsWith("删除成功"));// 断言提示以删除成功开始

    }

    @Test/* 10 退出*/
    public void test10logout() throws InterruptedException {
        //先登录，调用login方法；
        login(email, passwd);
        Thread.sleep(2000);
        //点击右上角用户图标
        driver.findElement(By.cssSelector(".avatar")).click();
        Thread.sleep(2000);
        //点击退出按钮
        driver.findElement(By.id("logout")).click();
        Thread.sleep(2000);
        //断言
        String txt = driver.findElement(By.xpath("//*[@id='main']/div/p")).getText();
        assertEquals(txt, "感谢参与！重新登录");
        //点击重新登录超链接
        driver.findElement(By.linkText("重新登录")).click();
    }
}
