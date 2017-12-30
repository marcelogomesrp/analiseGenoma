package org.analisegenoma.selenium.pages;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class EntrarPage {
    private WebDriver driver;

    public EntrarPage(WebDriver driver) {       
        this.driver = driver;
    }

    public void visita() {
        driver.get("http://localhost:8080/analiseGenoma");
    }

    public EntrarPage novo() {
        // clica no link de novo usuario
        driver.findElement(By.linkText("Register")).click();
        return new EntrarPage(driver);
    }
//
//    public boolean existeNaListagem(String nome, String email) {
//        // verifica se ambos existem na listagem
//        return driver.getPageSource().contains(nome) && 
//                driver.getPageSource().contains(email);
//    }
    public void cadastra(String email, String senha) {
        WebElement textbox = fluentWait(By.id("formulario:nome"));
        WebElement nameTbx = driver.findElement(By.name("formulario:nome"));
        WebElement emailTbx = driver.findElement(By.name("formulario:email"));
        WebElement senhaTbx = driver.findElement(By.name("formulario:senha"));
        WebElement confSenhaTbx = driver.findElement(By.name("formulario:confirmaSenha"));        
        WebElement addBtn = driver.findElement(By.name("formulario:add"));

        nameTbx.sendKeys("nome");        
        emailTbx.sendKeys(email);
        senhaTbx.sendKeys(senha);
        confSenhaTbx.sendKeys(senha);

        //txtNome.submit();
        //addBtn.submit();
        addBtn.click();

    }
    
    public WebElement fluentWait(final By locator) {
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
            .withTimeout(30, TimeUnit.SECONDS)
            .pollingEvery(5, TimeUnit.SECONDS)
            .ignoring(NoSuchElementException.class);

    WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
        public WebElement apply(WebDriver driver) {
            return driver.findElement(locator);
        }
    });

    return  foo;
};

}
