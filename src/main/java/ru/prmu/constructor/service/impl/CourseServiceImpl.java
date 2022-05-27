package ru.prmu.constructor.service.impl;

import static java.util.stream.Collectors.groupingBy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ContentDisposition;
import org.springframework.stereotype.Service;
import ru.prmu.constructor.entity.Course;
import ru.prmu.constructor.entity.Module;
import ru.prmu.constructor.entity.Topic;
import ru.prmu.constructor.entity.TopicFile;
import ru.prmu.constructor.repository.CourseRepository;
import ru.prmu.constructor.service.CourseService;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    @Value("${selenium.driver.path}")
    private String seleniumDriverPath;
    @Value("${moodle.password}")
    private String moodlePassword;
    @Value("${moodle.login}")
    private String moodleLogin;
    @Value("${moodle.path.backup}")
    private String moodleBackupPath;
    @Value("${moodle.path.tmp}")
    private String moodleTmpPath;
    @Value("${moodle.url}")
    private String moodleUrl;


    @Override
    public void downloadBackupZip(Long id, HttpServletResponse response)
        throws IOException, InterruptedException {
        Course course = findById(id);
        List<Topic> topics = course.getModules().stream().map(Module::getTopics).flatMap(
                Collection::stream)
            .collect(Collectors.toList());
        List<TopicFile> topicFiles = topics.stream().map(Topic::getFiles)
            .flatMap(Collection::stream).collect(
                Collectors.toList());
        Map<Long, List<TopicFile>> map = topicFiles.stream()
            .collect(groupingBy(e -> e.getTopic().getId()));
        WebDriver driver = getWebDriver();
        login(driver);
        navigateToCourses(driver);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='http://127.0.0.1/course/edit.php?category=0']")));
        driver.findElement(By.xpath("//a[@href='http://127.0.0.1/course/edit.php?category=0']")).click();
        createCourse(course, topics, map, driver);
        makeBackupFile(driver);
        navigateToCourses(driver);
        driver.findElement(By.xpath("//a[@href='http://127.0.0.1/course/management.php']")).click();
        List<WebElement> courses = driver.findElements(
            By.cssSelector("a[href*='http://127.0.0.1/course/delete.php?id=']"));
        courses.get(0).click();
        driver.findElement(By.xpath("//*[text()='Удалить']")).click();
        driver.close();
        downloadBackupFile(response, course);
    }

    private void makeBackupFile(WebDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(By.id("action-menu-toggle-2")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[text()='Резервное копирование']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("id_oneclickbackup")).click();
        driver.findElement(By.xpath("//*[text()='Продолжить']")).click();
        List<WebElement> t = driver.findElements(By.cssSelector("a[href*='forcedownload=1']"));
        t.get(0).click();
    }

    private void downloadBackupFile(HttpServletResponse response, Course course) throws IOException {
        File dir = new File(moodleBackupPath);
        File[] files = dir.listFiles();
        if (files.length == 0) {
            return;
        }
        File file = files[0];
        InputStream is = new FileInputStream(file);
        response.setStatus(HttpServletResponse.SC_OK);
        String name = file.getName().replaceAll(course.getName().toLowerCase() + "-", "");
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
            .filename(name)
            .build();
        response.addHeader("Content-Disposition",
            contentDisposition.toString());
        IOUtils.copy(is, response.getOutputStream());
        is.close();
        Files.deleteIfExists(file.toPath());
    }

    private void createCourse(Course course, List<Topic> topics, Map<Long, List<TopicFile>> map,
        WebDriver driver) throws IOException, InterruptedException {
        driver.findElement(By.id("id_fullname")).sendKeys(course.getName());
        driver.findElement(By.id("id_shortname")).sendKeys(course.getName());

        fillStartDate(course, driver);

        fillEndDate(course, driver);

        driver.findElement(By.id("id_idnumber")).sendKeys(course.getCode());

        driver.findElement(By.id("id_summary_editoreditable")).sendKeys(course.getDescription());

        driver.findElement(By.xpath("//*[text()='Формат курса']")).click();

        WebElement numSections = driver.findElement(By.id("id_numsections"));
        Select selectNumSections = new Select(numSections);
        selectNumSections.selectByVisibleText(Integer.toString(topics.size()));

        driver.findElement(By.id("id_saveanddisplay")).click();
        driver.findElement(By.xpath("//a[@data-key='coursehome']")).click();
        if (!topics.isEmpty()) {
            addTopic(topics, map, driver);
        }
    }

    private void addTopic(List<Topic> topics, Map<Long, List<TopicFile>> map, WebDriver driver)
        throws IOException, InterruptedException {
        driver.findElement(By.xpath("//*[text()='Режим редактирования']")).click();
        for (Topic topic : topics) {
            int i = topics.indexOf(topic) + 1;
            WebElement section = driver.findElement(By.id("section-" + i));
            WebElement element = section.findElement(By.cssSelector("*[title='Редактировать название темы']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            Thread.sleep(1000);
            element.click();
            Thread.sleep(1000);
            WebElement input = section.findElement(
                By.tagName("input"));
            Thread.sleep(1000);
            input.sendKeys(topic.getName());
            input.sendKeys(Keys.ENTER);
            List<TopicFile> topicToFile = map.get(topic.getId());
            if (topicToFile != null) {
                for (TopicFile topicFile : topicToFile) {
                    Thread.sleep(1000);
                    List<WebElement> webElements = driver.findElements(
                        By.xpath("//*[text()='Добавить элемент или ресурс']"));
                    webElements.get(i).click();
                    Thread.sleep(1000);
                    webElements.get(i).findElement(By.xpath("//*[text()='Файл']")).click();
                    Path path = Paths.get(moodleTmpPath + topicFile.getName());
                    Files.write(path, Base64.getDecoder().decode(topicFile.getContent()));
                    Thread.sleep(1000);
                    driver.findElement(By.className("dndupload-arrow")).click();
                    WebElement uploadFile = driver.findElement(
                        By.cssSelector("*[type='file']"));
                    uploadFile.sendKeys(path.toString());
                    driver.findElement(By.xpath("//*[text()='Загрузить этот файл']")).click();
                    Thread.sleep(1000);
                    driver.findElement(By.id("id_name")).sendKeys(topicFile.getName());
                    driver.findElement(By.id("id_submitbutton2")).click();
                    path.toFile().delete();
                }
            }
        }
    }

    private void fillStartDate(Course course, WebDriver driver) {
        DateObject startDate = getDate(course.getStartDate());

        WebElement selectElement = driver.findElement(By.id("id_startdate_day"));
        Select selectObject = new Select(selectElement);
        selectObject.selectByValue(startDate.getDate());

        WebElement selectMonth = driver.findElement(By.id("id_startdate_month"));
        Select selectMonthObject = new Select(selectMonth);
        selectMonthObject.selectByVisibleText(startDate.getMonth());

        WebElement selectYear = driver.findElement(By.id("id_startdate_year"));
        Select selectYearObject = new Select(selectYear);
        selectYearObject.selectByVisibleText(startDate.getYear());
    }

    private void fillEndDate(Course course, WebDriver driver) {
        DateObject endDate = getDate(course.getEndDate());

        WebElement selectEndElement = driver.findElement(By.id("id_enddate_day"));
        Select selectEndDateObject = new Select(selectEndElement);
        selectEndDateObject.selectByValue(endDate.getDate());

        WebElement selectEndMonth = driver.findElement(By.id("id_enddate_month"));
        Select selectEndMonthObject = new Select(selectEndMonth);
        selectEndMonthObject.selectByVisibleText(endDate.getMonth());

        WebElement selectEndYear = driver.findElement(By.id("id_enddate_year"));
        Select selectEndYearObject = new Select(selectEndYear);
        selectEndYearObject.selectByVisibleText(endDate.getYear());
    }

    private void navigateToCourses(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        String administration = "//a[@href='http://127.0.0.1/admin/search.php']";
        String courses = "//a[@href='#linkcourses']";
        driver.findElement(By.xpath(administration)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(courses)));
        driver.findElement(By.xpath(courses)).click();
    }

    private void login(WebDriver driver) {
        driver.get(moodleUrl);
        driver.findElement(By.xpath("//a[@href='http://127.0.0.1/login/index.php']")).click();
        driver.findElement(By.id("username")).sendKeys(moodleLogin);
        driver.findElement(By.id("password")).sendKeys(moodlePassword);
        driver.findElement(By.id("loginbtn")).click();
    }

    private WebDriver getWebDriver() {
        System.setProperty("webdriver.gecko.driver", seleniumDriverPath);
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("browser.download.folderList", 2);
        options.addPreference("browser.download.dir", moodleBackupPath);
        options.addPreference("browser.download.useDownloadDir", true);
        return new FirefoxDriver(options);
    }

    @Override
    public Page<Course> findAll(Integer page, Integer size) {
        return courseRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.getById(id);
    }

    @Override
    public Page<Course> findBySubjectId(Long subjectId, Integer page, Integer size) {
        return courseRepository.findBySubjectId(subjectId, PageRequest.of(page, size));
    }

    private DateObject getDate(LocalDate localDate) {
        String dateString = localDate.format(formatter);
        String month = localDate.getMonth()
            .getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru"));
        String[] dateArray = dateString.split("-");
        String date = dateArray[0];
        if (date.startsWith("0")) {
            date = date.replace("0", "");
        }
        return new DateObject(date, month, dateArray[2]);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private class DateObject {

        private String date;
        private String month;
        private String year;

    }
}
