package org.spring.ourchat;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;

@Controller
public class ClientTor {
	private static String s = System.getenv("OPENSHIFT_DATA_DIR");

	@RequestMapping(value = "/tor", method = RequestMethod.GET)
	public @ResponseBody String upload() {
		// First, instantiate the Client object.
		Client client;
		try {
			client = new Client(
			// This is the interface the client will listen on (you might need
			// something
			// else than localhost here).
					InetAddress.getLocalHost(),

					// Load the torrent from the torrent file and use the given
					// output directory. Partials downloads are automatically
					// recovered.
					SharedTorrent.fromFile(new File(s + File.separator
							+ "k.torrent"), new File(s)));

			// You can optionally set download/upload rate limits
			// in kB/second. Setting a limit to 0.0 disables rate
			// limits.
			client.setMaxDownloadRate(0);
			client.setMaxUploadRate(0);

			// At this point, can you either call download() to download the
			// torrent
			// and
			// stop immediately after...
			client.download();

			// Or call client.share(...) with a seed time in seconds:
			// client.share(3600);
			// Which would seed the torrent for an hour after the download is
			// complete.

			// Downloading and seeding is done in background threads.
			// To wait for this process to finish, call:
			client.waitForCompletion();

			// At any time you can call client.stop() to interrupt the download.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "upload";
	}
}
