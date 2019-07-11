package com.cognizant.cognizantits.engine.cli;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CIFWebservice {

	public static void main(String[] args)  {
		
		String ceritificateAccept="overridelink";
		String CorpCustAddBtn="CorpCustAdd";
		String XmlHttpRouteBtn="//td[text()='XML/HTTP Route']/input";
		String submitBtn="//input[@type='submit']";
		String webServiceMessageBox="//textarea[@name='ipXml']";
		String reqMessage = null;
		String submitbtnForwebServie="//input[@name='submitBut']";
		
		
		System.setProperty("webdriver.chrome.driver", "H://cognizant-intelligent-test-scripter-1.2//lib//Drivers//chromedriver.exe");

		WebDriver driver = new ChromeDriver();

		driver.get("https://tst-wbcore-web.ic.ing.net:8443/fiuicif/services/index_CIF.html");
		//driver.get("https://web-wbcore-tst.ic.ing.net:8443/fiuicif/services/index_CIF.html");
		driver.manage().window().maximize();
		//clickByID(driver, ceritificateAccept);
		clickByLinkText(driver, CorpCustAddBtn);
		clickByXpath(driver, XmlHttpRouteBtn);
		clickByXpath(driver, submitBtn);

		for (String handle : driver.getWindowHandles()) {
			driver.switchTo().window(handle);
			}
	try {
			reqMessage=messagePost();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendRequestMessage(driver, webServiceMessageBox,reqMessage);
		clickByXpath(driver, submitbtnForwebServie);
		
		
		driver.findElement(By.xpath("/html/body/form/table[1]/tbody/tr[2]/td[4]/textarea[@name='opXml']")).click();
		
		Robot robot;	
		
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			  robot.keyPress(KeyEvent.VK_A);
			  robot.keyRelease(KeyEvent.VK_A);
			  robot.keyRelease(KeyEvent.VK_CONTROL);			  
			  robot.keyPress(KeyEvent.VK_CONTROL);
			  robot.keyPress(KeyEvent.VK_C);
			  robot.keyRelease(KeyEvent.VK_C);
			  robot.keyRelease(KeyEvent.VK_CONTROL); 

				
					Thread.sleep(5000);
				
				  java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
			        java.awt.datatransfer.Transferable clipData = clipboard.getContents(clipboard);
				  
			   String   s = (String)(clipData.getTransferData(java.awt.datatransfer.DataFlavor.stringFlavor));
		      
			      if(s.contains("<SuccessOrFailure>Success</SuccessOrFailure>"))
			      {
			    	  System.out.println("CIF is genrated successfully");
			      }else
			      {
			    	  System.out.println("CIF is not genrated successfully");
			      }
				
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedFlavorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			  
			
		 catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	}
	
	
	public static void sendRequestMessage(WebDriver driver, String webServiceMessageBox,String message) {
		for (String handle : driver.getWindowHandles()) {

			driver.switchTo().window(handle);}

   WebElement webl = driver.findElement(By.xpath("/html/body/form/table[1]/tbody/tr[2]/td[2]/textarea"));
		
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].value='"+message+"';", webl);
		
		
		
	}
	
	
	
	public static String messagePost() throws FileNotFoundException {

        File gridtextfile = new File("H://GRID.txt");
        String gridtext = "";
        Scanner gridtextsc = new Scanner(gridtextfile);
        while (gridtextsc.hasNextLine()) {
               gridtext += gridtextsc.nextLine();
        }

      gridtext = gridtext.replace("$ORG_ID$", generateRandomNumber(9, "", ""));       
      //gridtext = gridtext.replace("$ORG_ID$",""); 
    
    String  s=generateRandomNumber(4, "INGBANK", "");
    System.out.println("CIF Number :: "+s+" is in processing, Please wait...");
        gridtext = gridtext.replace("$CIF_NAME$", generateRandomNumber(4, "INGBANK", ""));
        gridtext = gridtext.replace("$PRA$", generateRandomNumber(4, "ING", ""));
        
        gridtext = gridtext.replace("$CIF_NAME$", s);
        gridtext = gridtext.replace("$PRA$",s);
        
        gridtext = gridtext.replace("$TRN$", generateRandomNumber(5, "IE", ""));
        gridtext = gridtext.replace("$BRN$", generateRandomNumber(5, "IE", ""));
        

        return gridtext;

       }
	
	
	public static String generateRandomNumber(int length, String prefix,
			String suffix) {
		StringBuilder randomNumberStringBuilder = new StringBuilder("");
		for (int i = 0; i < length; i++)
		{
			randomNumberStringBuilder.append(Long.toString(
					(long) (Math.random() * 1000)).substring(0, 1));
		}
		return prefix + randomNumberStringBuilder + suffix;
	}
	

	public static  void clickByID(WebDriver webdriver,String webElement)
	{
		webdriver.findElement(By.id(webElement)).click();
	}

	public static  void clickByLinkText(WebDriver webdriver,String webElement)
	{
		webdriver.findElement(By.partialLinkText(webElement)).click();
	}

	public static  void clickByXpath(WebDriver webdriver,String webElement)
	{
		webdriver.findElement(By.xpath(webElement)).click();
	}

	
	
	
}
