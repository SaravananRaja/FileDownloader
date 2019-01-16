package com.download.spring.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.download.spring.constant.FileDownloaderConstants;
import com.download.spring.model.FileDownloaderRequest;
import com.download.spring.validation.FileDownloaderValidation;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author Saravanan
 *
 */
@Controller
public class FileDownloadController {

	public static final String HOSTNAME = "localhost";

	/*public java.sql.Connection connectOracleDB() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			java.sql.Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");

			return con;
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}*/
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		System.out.println("Home Page Requested, locale = " + locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);
		return "home";
	}

	public String send(String pRequest) throws Exception {
		String response = FileDownloaderConstants.EMPTY_STRING;
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		try {
			factory.setHost(HOSTNAME);
			channel.basicPublish("AgodaFileDownloaderExchange", FileDownloaderConstants.EMPTY_STRING, null,
					pRequest.toString().getBytes("UTF-8"));
			response = FileDownloaderConstants.SUCCESS;
		} catch (Exception e) {
			System.out.println("Exception Occured " + e.getMessage());
			response = FileDownloaderConstants.FAILURE;
		} finally {
			try {
				channel.close();
				connection.close();
			} catch (Exception e) {
				System.out.println("Exception Occured " + e.getMessage());
			}

		}
		return response;
	}

	public String sendQueue(FileDownloaderRequest pFileDownloaderRequest) {
		String response = FileDownloaderConstants.EMPTY_STRING;
		String sourceLocation = pFileDownloaderRequest.getSource();
		String sourceArray[] = sourceLocation.split(":");
		pFileDownloaderRequest.setProtocol(sourceArray[0]);
		Gson gson = new Gson();
		String sendRequest = gson.toJson(pFileDownloaderRequest);
		try {
			response = this.send(sendRequest);
		} catch (Exception exception) {
			response = FileDownloaderConstants.FAILURE;
		}
		return response;
	}

	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public String processDownload(@Validated FileDownloaderRequest pFileDownloaderRequest, Model model) {
		model.addAttribute("userName", pFileDownloaderRequest.getUserName());
		model.addAttribute("userPassword", pFileDownloaderRequest.getUserPassword());
		model.addAttribute("source", pFileDownloaderRequest.getSource());
		model.addAttribute("destination", pFileDownloaderRequest.getDestination());
		FileDownloaderValidation aFileDownloaderValidation = new FileDownloaderValidation();

		if (aFileDownloaderValidation.validateUserName(pFileDownloaderRequest.getUserName())
				&& aFileDownloaderValidation.validateUserPassword(pFileDownloaderRequest.getUserPassword())
				&& aFileDownloaderValidation.validateSourceLocation(pFileDownloaderRequest.getSource())
				&& aFileDownloaderValidation.validateDestinationLocation(pFileDownloaderRequest.getDestination())) {

			String response = this.sendQueue(pFileDownloaderRequest);
			if (FileDownloaderConstants.SUCCESS.equalsIgnoreCase(response)) {
				return "Success";
			} else {
				return "failure";
			}

		} else {
			return "failure";
		}

	}

	@RequestMapping(value = "/show")
	public String showHistory(@Validated FileDownloaderRequest pFileDownloaderRequest, Model model) {
		model.addAttribute("userName", pFileDownloaderRequest.getUserName());
		model.addAttribute("userPassword", pFileDownloaderRequest.getUserPassword());

		FileDownloaderValidation aFileDownloaderValidation = new FileDownloaderValidation();

		/*java.sql.Connection con = this.connectOracleDB();
		try {
			PreparedStatement ps = con
					.prepareStatement("select  * from download");
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				System.out.println(rs.getInt(1));
				 
					}
			
			*/
			
			
		/*}
		catch (Exception e) {
			System.out.println("SQL Exception Occured During Retrieving Data by Manufacturer and by SeatCount");

		} finally {
			try {
				con.close();
			} catch (Exception exception) {
				System.out.println("SQL Connection could not be closed because " + exception.getMessage());
			}
		}	
		*/
		if (aFileDownloaderValidation.validateUserName(pFileDownloaderRequest.getUserName())
				&& aFileDownloaderValidation.validateUserPassword(pFileDownloaderRequest.getUserPassword())) {
			return "DisplayAdmin";

		} else {
			return "failure";
		}

	}

}
