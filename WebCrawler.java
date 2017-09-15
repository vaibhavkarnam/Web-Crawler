/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author vaibhav
 */
public class WebCrawler {

	static List<String> anchors = new LinkedList<String>();
	static List<String> anchors2 = new LinkedList<String>();
	static int counter = 0;
	static int linkCount = 0;
	private static String currDir = System.getProperty("user.dir");
	private static String appDir = currDir + File.separator + "WebCrawler";
	private static final String TASK1_LINK_FILE_NAME = "task1_links.txt";
	private static final String DFS_LINK_FILE_NAME = "dfs_links.txt";
	private static final String BFS_LINK_FILE_NAME = "bfs_links.txt";
	private static final String SEED_HTML = "seed.txt";

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
        try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter task number(1 or 2)");
			int task = Integer.parseInt(br.readLine());
			WebCrawler crawler = new WebCrawler();
			if (task == 1) {
				crawler.task1();
			} else if (task == 2) {
				crawler.task2();
			} /*else if (task == 3) {
				crawler.task3();
			}*/ else {
				System.out.println("ERROR: Incorrect option.\nExiting");
				System.exit(0);
			}

		} catch (NumberFormatException nfe) {
			System.out.println("ERROR: Incorrect input\nExiting");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to execute Task1
	 * BFS crawling of all URLs
	 */
	public void task1() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter web link to crawl");
			String link = br.readLine();
			// String link = "https://en.wikipedia.org/wiki/Sustainable_energy";

			// Create the application directory
			new File("WebCrawler").mkdirs();
			File file = new File(appDir + File.separator + TASK1_LINK_FILE_NAME);
			if (!file.exists()) {
				file.createNewFile();
			}
			
			// Write the seed URL to file and add to list
			BufferedWriter bw1 = new BufferedWriter(new FileWriter(appDir + File.separator + TASK1_LINK_FILE_NAME));
			bw1.write(link);
			bw1.newLine();
			bw1.close();
			anchors.add(link);

			crawl_task1(link, anchors);
			int count = 0;
			for (String anchor : anchors) {
				if (count < 5) {
					crawl_task1(anchor, anchors2);
					count++;
				}
			}
			anchors.addAll(anchors2);
			anchors2.removeAll(anchors2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to execute Task 2
	 * Web crawling with keyword matching
	 * Option 1 : BFS crawling
	 * Option 2 : DFS crawling
	 */
	public void task2() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("\n\nEnter keyword to be matched: ");
			String anchorName = br.readLine();

			System.out.println("\n\nOptions:");
			System.out.println("1. Breadth first crawling");
			System.out.println("2. Depth first crawling");
			System.out.println("Select option:");
			int option = Integer.parseInt(br.readLine());

			System.out.println("Enter web link to crawl");
			String link = br.readLine();
			// String link =
			// "https://en.wikipedia.org/wiki/Sustainable_energy";

			// Breadth First Search
			if (option == 1) {
				// Create application directory
				new File(appDir).mkdirs();
				File file = new File(appDir + File.separator + BFS_LINK_FILE_NAME);
				if (!file.exists()) {
					file.createNewFile();
				}
				
				// Insert Seed URL to file and add to list
				BufferedWriter bw1 = new BufferedWriter(new FileWriter(appDir + File.separator + BFS_LINK_FILE_NAME));
				bw1.write(link);
				bw1.newLine();
				bw1.close();
				anchors.add(link);
				try {
					crawl_task2(link, anchors, anchorName);

					int count = 0;
					for (String anchor : anchors) {
						if (count < 5) {
							crawl_task2(anchor, anchors2, anchorName);
							count++;
						}
					}
					anchors2.addAll(anchors);
					anchors.removeAll(anchors);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// Depth first search
			else if (option == 2) {
				// Create application directory
				new File(appDir).mkdirs();
				File file = new File(appDir + File.separator + DFS_LINK_FILE_NAME);
				if (!file.exists()) {
					file.createNewFile();
				}
				
				// Insert Seed URL to file and add to List
				BufferedWriter bw1 = new BufferedWriter(new FileWriter(appDir + File.separator + DFS_LINK_FILE_NAME));
				bw1.write(link);
				bw1.newLine();
				bw1.close();
				anchors.add(link);
				crawl_task2_dfs(link/* , anchors */, anchorName);
			}

			// Error handling
			else {
				System.out.println("ERROR: Incorrect option.\nExiting");
				System.exit(0);
			}
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR: Incorrect input\nExiting");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void task3() {

	}

	/**
	 * Task 1 implementation
	 * @param url
	 * @param anchors
	 */
	public static void crawl_task1(String url, List<String> anchors) {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		File linkFile = new File(appDir + File.separator + TASK1_LINK_FILE_NAME);
		try {
			org.jsoup.nodes.Document doc = Jsoup.connect(url).timeout(10000).validateTLSCertificates(false).get();

			// Write HTML to a file
			Connection.Response html = Jsoup.connect(url).timeout(10000).validateTLSCertificates(false).execute();
			File mainhtml = new File(appDir + File.separator + SEED_HTML);
			if (!mainhtml.exists()) {
				mainhtml.createNewFile();
			}
			BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(mainhtml));
			htmlWriter.write(html.body());
			htmlWriter.close();

			// Get HTML content
			Element elem = doc.getElementById("content");
			org.jsoup.select.Elements links = elem.getElementsByTag("a");
			for (Element link : links) {
				String linkHref = link.attr("href");
				String absUrl = link.absUrl("href");
				if (!anchors.contains(absUrl)) {

					// Filtering administrative links and links pointing to the
					// same page
					if (!linkHref.contains(":") && !linkHref.contains("#")) {
						if (linkHref.contains("/wiki/")) {
							if (linkCount < 1000) {
								// Add URL to file and insert to list
								linkCount++;
								anchors.add(absUrl);
								BufferedWriter bw = new BufferedWriter(new FileWriter(linkFile, true));
								bw.write(absUrl);
								bw.newLine();
								bw.close();
							} else {
								System.out.println("Crawled 1000 URLs");
								System.exit(0);
							}
						}
					}
				}
			}
		} catch (IOException ex) {
			Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Task2 : BFS crawling implementation
	 * @param url
	 * @param localAnchors
	 * @param anchorNameText
	 */
	public static void crawl_task2(String url, List<String> localAnchors, String anchorNameText) {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		File linkFile = new File(appDir + File.separator + BFS_LINK_FILE_NAME);
		try {
			org.jsoup.nodes.Document doc = Jsoup.connect(url).get();

			// Write HTML to a file
			Connection.Response html = Jsoup.connect(url).execute();
			File mainhtml = new File(appDir + File.separator + SEED_HTML);
			if (!mainhtml.exists()) {
				mainhtml.createNewFile();
			}
			BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(mainhtml));
			htmlWriter.write(html.body());
			htmlWriter.close();

			// Get HTML content
			Element elem = doc.getElementById("content");
			org.jsoup.select.Elements links = elem.getElementsByTag("a");

			for (Element link : links) {
				String anchorName = link.text();
				String linkHref = link.attr("href");
				String absUrl = link.absUrl("href");
				if (!localAnchors.contains(absUrl) && !anchors2.contains(absUrl) && !anchors.contains(absUrl)) {

					// Filtering administrative links and links pointing to the
					// same page
					if (!linkHref.contains(":") && !linkHref.contains("#")) {
						if (linkHref.contains("/wiki/")) {
							if (anchorName.toLowerCase().contains(anchorNameText.toLowerCase())) {
								if (linkCount < 1000) {
									
									// Add URL to file and insert to list
									localAnchors.add(absUrl);
									BufferedWriter bw = new BufferedWriter(new FileWriter(linkFile, true));
									bw.write(absUrl);
									bw.newLine();
									bw.close();
								} else {
									System.out.println("Crawled 1000 URLs");
									System.exit(0);
								}
							}
						}
					}
				}
			}
		} catch (IOException ex) {
			Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Task 2 : DFS crawling implementation
	 * @param url
	 * @param anchorNameText
	 */
	public static void crawl_task2_dfs(String url, String anchorNameText) {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		File linkFile = new File(appDir + File.separator + DFS_LINK_FILE_NAME);
		try {
			org.jsoup.nodes.Document doc = Jsoup.connect(url).get();

			// Write HTML to a file
			Connection.Response html = Jsoup.connect(url).execute();
			File mainhtml = new File(appDir + File.separator + SEED_HTML);
			if (!mainhtml.exists()) {
				mainhtml.createNewFile();
			}
			BufferedWriter htmlWriter = new BufferedWriter(new FileWriter(mainhtml));
			htmlWriter.write(html.body());
			htmlWriter.close();

			// Get HTML content
			Element elem = doc.getElementById("content");
			org.jsoup.select.Elements links = elem.getElementsByTag("a");

			for (Element link : links) {
				String anchorName = link.text();
				String linkHref = link.attr("href");
				String absUrl = link.absUrl("href");
				if (!anchors.contains(absUrl)) {
					if (anchorName.toLowerCase().contains(anchorNameText.toLowerCase())) {

						// Filtering administrative links and links pointing to
						// the same page
						if (!linkHref.contains(":") && !linkHref.contains("#")) {
							if (linkHref.contains("/wiki/")) {
								if (linkCount < 1000) {
									if (counter < 4) {
										// Add URL to file and insert to list
										BufferedWriter bw = new BufferedWriter(new FileWriter(linkFile, true));
										bw.write(absUrl);
										bw.newLine();
										bw.close();
										anchors.add(absUrl);
										counter++;
										crawl_task2_dfs(absUrl, anchorNameText);
									}
									linkCount++;
									if (!anchors.contains(absUrl)) {
										anchors.add(absUrl);
										BufferedWriter bw = new BufferedWriter(new FileWriter(linkFile, true));
										bw.write(absUrl);
										bw.newLine();
										bw.close();
									}
								} else {
									System.out.println("Crawled 1000 URLs");
									System.exit(0);
								}
							}
						}
					}
				}
			}
		} catch (IOException ex) {
			Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
