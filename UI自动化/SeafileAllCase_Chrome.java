import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class SeafileAllCase_Chrome {


    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //打开seafile网页
        driver.get("http://172.31.128.199:8000");
        //窗口最大化
        driver.manage().window().maximize();
        /* 01 登录*/
        //定位登录邮箱、密码、登录按钮元素，并进行登录操作
        driver.findElement(By.name("login")).sendKeys("wjh@51testing.com");
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.cssSelector(".submit")).click();
        //登录后隐式等待2000ms，以便找到后续元素
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);

       /* 02 新建加密资料库*/
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

        /* 03 删除资料库*/
        //定位删除悬浮按钮，需要创建动作类对象
        Actions actions=new Actions(driver);
        //定位删除悬浮按钮，并将鼠标移动到对应的删除悬浮元素上
        WebElement element=driver.findElement(By.xpath("//*[@id=\"my-repos\"]/div/div[2]/table/tbody/tr/td[2]"));
        actions.moveToElement(element).build().perform();
        Thread.sleep(2000);
        //点击删除按钮
        driver.findElement(By.xpath("//table/tbody/tr[1]/td[3]/div/a[2]")).click();
        Thread.sleep(2000);
        //确定删除资料库
        driver.findElement(By.cssSelector("#confirm-yes")).click();
        Thread.sleep(2000);

       /* 04 新建普通资料库*/
        //定位新建资料库按钮
        driver.findElement(By.xpath("//*[@id=\"my-repos-toolbar\"]/button/span[2]")).click();
        //定位新建资料库名称输入框
        driver.findElement(By.id("repo-name")).sendKeys("ptzlk");
        //定位提交按钮
        driver.findElement(By.cssSelector(".submit")).click();
        Thread.sleep(2000);

        /* 05 上传文件*/
        //选择当前显示第一行的资料库
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

        /* 06 重命名文件*/
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

        /* 07 下载文件*/
        //点击勾选文件前的复选框
        driver.findElement(By.xpath("//table/tbody/tr/td[1]/input")).click();
        //点击下载按钮
        driver.findElement(By.id("download-dirents")).click();
        Thread.sleep(2000);

        /* 08 删除文件*/
        //点击删除按钮
        driver.findElement(By.xpath("//*[@id=\"del-dirents\"]")).click();
        Thread.sleep(2000);
        //点击删除提示框中的确定按钮
        driver.findElement(By.xpath("//*[@id=\"confirm-yes\"]")).click();
        Thread.sleep(2000);

        /* 09 在资料库中，返回资料库列表，再删除资料库*/
        //返回资料库
        driver.findElement(By.xpath("//div[2]/div[2]/div[2]/div/div[1]/div[1]/a")).click();
        Thread.sleep(2000);
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

        /* 10 退出*/
        //点击右上角用户图标
        driver.findElement(By.cssSelector(".avatar")).click();
        Thread.sleep(2000);
        //点击退出按钮
        driver.findElement(By.id("logout")).click();
        Thread.sleep(2000);
        //点击重新登录超链接
        driver.findElement(By.linkText("重新登录")).click();
        //关闭窗口
        driver.close();
    }
}
