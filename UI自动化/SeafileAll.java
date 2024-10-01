import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import java.util.concurrent.TimeUnit;

public class SeafileAll {
    public static void main(String[] args) throws InterruptedException {
        /* 初始化 */

        /* 使用 Chrome 浏览器 */
        // 设置 Chrome 浏览器驱动位置环境变量 可以使用相对路径
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        // 创建Chrome浏览器驱动对象
        // WebDriver driver = new ChromeDriver();

        /* 使用 Firefox 浏览器 */
        // 设置 Firefox 浏览器驱动位置环境变量 可以使用相对路径
        System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
        // Firefox浏览器驱动日志保存到文件(可选)
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "drivers/firefox.log");
        FirefoxOptions options = new FirefoxOptions(); // 创建Firefox浏览器选项对象
        // 设置对应png格式的图片下载直接保存 不弹出对话框进行选择
        options.addPreference("browser.helperApps.neverAsk.saveToDisk", "image/png");
        // 创建Firefox浏览器驱动对象时使用参数 Firefox浏览器选项对象
        WebDriver driver = new FirefoxDriver(options);

        // 设置隐式等待时间为30秒
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // 设置窗口最大化
        driver.manage().window().maximize();
        String text; // 定义字符串变量用于存储获取的元素文字
        Actions action = new Actions(driver);

        /* 01 登录 */
        System.out.println("[01 登录]");
        driver.get("http://172.31.128.199:8000/");  // 打开首页
        driver.findElement(By.name("login")).sendKeys("wjh@51testing.com");  // 输入用户名
        driver.findElement(By.name("password")).sendKeys("123456"); // 输入密码
        driver.findElement(By.className("submit")).click(); // 点击登录按钮
        Thread.sleep(2000); // 延时2秒 等待登录完成
        driver.findElement(By.id("my-info")).click();  // 点击查看个人资料链接打开下拉菜单
        Thread.sleep(1000); // 延时1秒 等待下拉菜单打开
        // 获取用户名称输出
        text = driver.findElement(By.xpath("//*[@id=\"user-info-popup\"]/div[2]/div[1]/div")).getText();
        System.out.println("用户名称 " + text);
        driver.findElement(By.id("my-info")).click(); // 点击查看个人资料链接关闭下拉菜单

        /* 02 创建加密资料库 */
        System.out.println("[02 创建加密资料库]");
        // 点击新建资料库
        driver.findElement(By.xpath("//*[@id='my-repos-toolbar']/button/span[2]")).click();
        // 输入资料库名称
        driver.findElement(By.id("repo-name")).sendKeys("MyRepo");
        driver.findElement(By.id("encrypt-switch")).click();  // 点击加密复选框
        driver.findElement(By.id("passwd")).sendKeys("12345678");  // 输入密码
        driver.findElement(By.id("passwd-again")).sendKeys("12345678");  // 输入确认密码
        driver.findElement(By.cssSelector(".submit")).click();  // 点击提交按钮
        Thread.sleep(1000);  // 延时1秒 等待资料库创建成功
        // 定位到资料库名称元素获取名称 新创建的资料库位置在第一个
        text = driver.findElement(By.xpath
                ("//*[@id='my-repos']/div/div[2]/table/tbody/tr[1]/td[2]/span/a")).getText();
        System.out.println("加密资料库名称 " + text);  // 输出资料库名称
        // 获取资料库前图标的提示
        text = driver.findElement(By.xpath
                ("//*[@id='my-repos']/div/div[2]/table/tbody/tr[1]/td[1]/img")).getAttribute("title");
        System.out.println("资料库前图标的提示是 " + text); // 输出资料库前图标的提示
        // 点击新创建的资料库名称
        driver.findElement(By.xpath("//*[@id='my-repos']/div/div[2]/table/tbody/tr[1]/td[2]/span/a")).click();
        // 获取资料库密码输入对话框文字
        text = driver.findElement(By.xpath("//*[@id='repo-decrypt-form']/p[1]")).getText();
        System.out.println("资料库密码输入对话框文字是 " + text);  // 输出资料库密码输入对话框文字
        driver.findElement(By.xpath("//*[@id='simplemodal-container']/a")).click();  // 关闭资料库密码输入对话框

        /* 03 删除加密资料库 */
        System.out.println("[03 删除加密资料库]");
        // 鼠标移动到第一个资料库名称
        action.moveToElement(driver.findElement(By.xpath
                ("//*[@id='my-repos']/div/div[2]/table/tbody/tr[1]/td[2]/span/a"))).build().perform();
        Thread.sleep(1000);  // 延时1秒 模拟鼠标悬停 等待删除按钮可以点击
        // 点击删除按钮
        driver.findElement(By.xpath("//*[@id='my-repos']/div/div[2]/table/tbody/tr[1]/td[3]/div/a[2]")).click();
        driver.findElement(By.id("confirm-yes")).click();  // 点击确认按钮
        Thread.sleep(1000);  // 延时1秒 等待提示出现
        text = driver.findElement(By.xpath("//*[@id='main']/ul/li")).getText();  // 获取删除资料库后的提示信息
        System.out.println("提示信息 " + text);  // 输出删除资料库后的提示信息

        /* 04 创建资料库 */
        System.out.println("[04 创建资料库]");
        // 点击新建资料库
        driver.findElement(By.xpath("//*[@id='my-repos-toolbar']/button/span[2]")).click();
        // 输入资料库名称
        driver.findElement(By.id("repo-name")).sendKeys("MyRepo");
        driver.findElement(By.cssSelector(".submit")).click();  // 点击提交按钮
        Thread.sleep(1000);  // 延时1秒 等待资料库创建成功
        // 定位到资料库名称元素获取名称输出 新创建的资料库位置在第一个
        text = driver.findElement(By.xpath
                ("//*[@id='my-repos']/div/div[2]/table/tbody/tr[1]/td[2]/span/a")).getText();
        System.out.println("资料库名称 " + text); // 输出资料库名称

        /* 05 资料库上传文件 */
        System.out.println("[05 资料库上传文件]");
        // 点击资料库名称
        driver.findElement(By.xpath("//*[@id='my-repos']/div/div[2]/table/tbody/tr[1]/td[2]/span/a")).click();
        // 上传文件路径和文件名 其中 System.getProperty("user.dir") 获取项目根目录
        String uploadFile = System.getProperty("user.dir") + "\\files\\seafile-logo.png";
        driver.findElement(By.id("advanced-upload-file-input")).sendKeys(uploadFile);  // 上传文件
        Thread.sleep(2000);  // 延时2秒 等待上传完成
        text = driver.findElement(By.cssSelector(".name")).getText();  // 获取上传状态的文件名
        System.out.println("上传状态的文件名 " + text);  // 输出上传状态的文件名
        text = driver.findElement(By.cssSelector(".tip")).getText();  // 获取上传状态的结果
        System.out.println("上传状态的结果 " + text);  // 输出上传状态的结果
        driver.findElement(By.cssSelector(".sf-close")).click();  // 点击上传状态关闭按钮

        /* 06 资料库重命名文件 */
        System.out.println("[06 资料库重命名文件]");
        // 鼠标移动到文件名
        action.moveToElement(driver.findElement(By.xpath
                ("//*[@id='dir-view']/div/div[2]/div/table/tbody/tr/td[4]/span[1]/a"))).build().perform();
        Thread.sleep(1000);  // 延时1秒 模拟鼠标悬停 等待更多操作按钮可以点击
        // 点击更多操作
        driver.findElement(By.xpath("//*[@id='dir-view']/div/div[2]/div/table/tbody/tr/td[5]/div/div[2]/a")).click();
        driver.findElement(By.linkText("重命名")).click();  // 点击重命名链接
        // 修改文件名方式1：定位元素清除原文件名 再定位元素输入新文件名
        // driver.findElement(By.name("newname")).clear();  // 清除原文件名
        // driver.findElement(By.name("newname")).sendKeys("logo.png");  // 输入新文件名
        // 修改文件名方式2：定位元素保存为WebElement类的引用变量 使用变量清除原文件名 再输入新文件名
        WebElement element = driver.findElement(By.name("newname"));
        element.clear();  // 清除原文件名
        element.sendKeys("logo.png");  // 输入新文件名
        driver.findElement(By.xpath("//*[@id='rename-form']/button[1]")).click();  // 点击提交按钮
        Thread.sleep(1000);  // 延时1秒 等待文件列表刷新完成
        // 获取新文件名
        text = driver.findElement(By.xpath
                ("//*[@id='dir-view']/div/div[2]/div/table/tbody/tr/td[4]/span[1]/a")).getText();
        System.out.println("新文件名 " + text);  // 输出新文件名

        /* 07 资料库下载文件 */
        System.out.println("[07 资料库下载文件]");
        // 点击第一个文件前的复选框
        driver.findElement(By.xpath("//*[@id='dir-view']/div/div[2]/div/table/tbody/tr[1]/td[1]/input")).click();
        driver.findElement(By.id("download-dirents")).click();  // 点击下载按钮
        Thread.sleep(1000); // 延时1秒 等待文件下载完成

        /* 08 资料库删除文件 */
        System.out.println("[08 资料库删除文件]");
        driver.findElement(By.id("del-dirents")).click();  // 点击删除按钮
        driver.findElement(By.id("confirm-yes")).click();  // 点击确认按钮
        Thread.sleep(1000);  // 延时1秒 等待提示出现
        text = driver.findElement(By.xpath("//*[@id='main']/ul/li")).getText();  // 获取删除文件后的提示信息
        System.out.println("提示信息 " + text);  // 输出删除文件后的提示信息

        /* 09 删除资料库 */
        System.out.println("[09 删除资料库]");
        driver.navigate().back();  // 退回上一页资料库列表
        // 鼠标移动到第一个资料库名称
        action.moveToElement(driver.findElement(By.xpath
                ("//*[@id='my-repos']/div/div[2]/table/tbody/tr[1]/td[2]/span/a"))).build().perform();
        Thread.sleep(2000);  // 延时1秒 模拟鼠标悬停 等待删除按钮可以点击
        // 点击第一个资料库的删除按钮
        driver.findElement(By.xpath("//*[@id='my-repos']/div/div[2]/table/tbody/tr[1]/td[3]/div/a[2]")).click();
        driver.findElement(By.id("confirm-yes")).click();  // 点击确认按钮
        Thread.sleep(1000);  // 延时1秒 等待提示出现
        text = driver.findElement(By.xpath("//*[@id='main']/ul/li")).getText();  // 获取删除资料库后的提示信息
        System.out.println("提示信息 " + text);  // 输出删除资料库后的提示信息

        /* 10 退出登录 */
        driver.findElement(By.id("my-info")).click();  // 点击查看个人资料链接打开下拉菜单
        Thread.sleep(1000); // 延时1秒 等待下拉菜单打开
        driver.findElement(By.id("logout")).click(); // 点击退出链接
        text = driver.findElement(By.xpath("//*[@id='main']/div/p")).getText();  // 获取退出登录后的信息
        System.out.println("提示信息 " + text);  // 输出退出登录后的信息
        /* 关闭浏览器 退出浏览器驱动 */
        driver.quit();
    }
}
