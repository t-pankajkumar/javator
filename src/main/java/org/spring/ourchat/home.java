package org.spring.ourchat;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Handles requests for the application home page.
 */
@Controller
public class home {

	private static final Logger logger = LoggerFactory
			.getLogger(home.class);
	private static String s = "C:\\Users\\PankajKumar\\Downloads\\Video";

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String upload(Model model,@ModelAttribute("AttributeName") final String value) {
		final File folder = new File(s);
		model.addAttribute("msg", value);
		model.addAttribute("file", listFilesForFolder(folder));
		return "upload";
	}

	public HashMap<String, Long> listFilesForFolder(final File folder) {
		HashMap<String, Long> li = new HashMap<String, Long>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {

				listFilesForFolder(fileEntry);
			} else {
				long fileSizeInBytes = fileEntry.length();
				// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
				long fileSizeInKB = fileSizeInBytes / 1024;
				// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
				long fileSizeInMB = fileSizeInKB / 1024;
				li.put(fileEntry.getName(), fileSizeInMB);
			}
		}
		return li;
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String uploadFileHandler(final RedirectAttributes redirectAttrs,
			@RequestParam("file") MultipartFile file, Model model) {
		String message = "";
		String name = file.getOriginalFilename();
		if (!file.isEmpty()) {

			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(s);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);

				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				// logger.info("Server File Location="+
				// serverFile.getAbsolutePath());

				message =  "You successfully uploaded file="
						+ name;
			} catch (Exception e) {
				message =  "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			message =  "You failed to upload " + name + " because the file was empty.";
		}
		redirectAttrs.addFlashAttribute("AttributeName", message);
		return "redirect:/";
	}

	/**
	 * Upload multiple file using Spring Controller
	 */
	@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
	public String uploadMultipleFileHandler(
			final RedirectAttributes redirectAttrs,
			@RequestParam("name") String[] names,
			@RequestParam("file") MultipartFile[] files, Model model) {

		if (files.length != names.length)
			return "Mandatory information missing";

		String message = "";
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String name = names[i];
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(s);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				// logger.info("Server File Location=" +
				// serverFile.getAbsolutePath());

				message = message + "You successfully uploaded file=" + name
						+ "<br />";
			} catch (Exception e) {
				message = "You failed to upload " + name + " => "
						+ e.getMessage();
			}
		}
		redirectAttrs.addFlashAttribute("AttributeName", message);
		return "redirect:/";
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void doDownload(@RequestParam("fname") String fname,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		final int BUFFER_SIZE = 4096;
		// get absolute path of the application
		ServletContext context = request.getSession().getServletContext();

		// construct the complete absolute path of the file
		String fullPath = s + "/" + fname;
		File downloadFile = new File(fullPath);
		FileInputStream inputStream = new FileInputStream(downloadFile);

		// get MIME type of the file
		String mimeType = context.getMimeType(fullPath);
		if (mimeType == null) {
			// set to binary type if MIME mapping not found
			mimeType = "application/octet-stream";
		}

		// set content attributes for the response
		response.setContentType(mimeType);
		response.setContentLength((int) downloadFile.length());

		// set headers for the response
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				downloadFile.getName());
		response.setHeader(headerKey, headerValue);

		// get output stream of the response
		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;

		// write bytes read from the input stream into the output stream
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}

		inputStream.close();
		outStream.close();

	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	/*
	 * @RequestMapping(value = "/", method = RequestMethod.GET) public String
	 * home(Locale locale, Model model) { //
	 * logger.info("Welcome home! The client locale is {}.", locale);
	 * 
	 * Date date = new Date(); DateFormat dateFormat =
	 * DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
	 * 
	 * String formattedDate = dateFormat.format(date);
	 * 
	 * model.addAttribute("serverTime", formattedDate);
	 * 
	 * return "home"; }
	 * 
	 * 
	 * @RequestMapping(value = "/ss", method = RequestMethod.POST) public
	 * 
	 * @ResponseBody String uploadFileHandler(
	 * 
	 * @RequestParam("file") MultipartFile file) { String name =
	 * file.getOriginalFilename(); System.out.println(name); if
	 * (!file.isEmpty()) { try { byte[] bytes = file.getBytes();
	 * 
	 * // Creating the directory to store file String rootPath =
	 * System.getProperty("catalina.home"); //File dir = new File(rootPath +
	 * File.separator + "tmpFiles"); //if (!dir.exists()) //dir.mkdirs();
	 * 
	 * // Create the file on server File serverFile = new
	 * File(rootPath+"/tmpFiles"+File.separator+name); BufferedOutputStream
	 * stream = new BufferedOutputStream( new FileOutputStream(serverFile));
	 * stream.write(bytes); stream.close();
	 * 
	 * //logger.info("Server File Location=" + serverFile.getAbsolutePath());
	 * 
	 * return "You successfully uploaded file=" + name; } catch (Exception e) {
	 * return "You failed to upload " + name + " => " + e.getMessage(); } } else
	 * { return "You failed to upload " + name + " because the file was empty.";
	 * } }
	 */

}
