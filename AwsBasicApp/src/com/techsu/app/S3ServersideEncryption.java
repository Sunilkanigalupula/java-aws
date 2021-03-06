/**
 * 
 */
package com.techsu.app;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.model.ProgressEvent;
import com.amazonaws.services.s3.model.ProgressListener;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

/**
 * @author techsu
 *
 */
public class S3ServersideEncryption {

	
	/**
	 * @param args
	 */

	private static AWSCredentials credentials;
	private static TransferManager transfermanager;
	private static String bucketName;
	
	private JProgressBar progressBar;
	private JFrame frame;
	private Upload upload;
	private JButton button;
	
	public static void main(String[] args) throws IOException {
		
		 credentials=new PropertiesCredentials(S3ServersideEncryption.class.getResourceAsStream("AwsCredentials.properties"));
		 
		 transfermanager=new TransferManager(credentials);
		 
		 bucketName="servrsideEncryption"+credentials.getAWSAccessKeyId().toLowerCase();
		 new S3ServersideEncryption();
		 			
	}
	public S3ServersideEncryption(){
		
		frame=new JFrame("AWS s3 file upload");
		button=new JButton("choose file...");
		
		button.addActionListener(new ButtonListener());
		
		progressBar=new JProgressBar(0, 100);
		progressBar.setStringPainted(true);
		
		frame.setContentPane(createContentpane());
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	private Container createContentpane() {
		JPanel panel=new JPanel();
		panel.add(button);
		
		JPanel borderPanel=new JPanel();
		borderPanel.setLayout(new BorderLayout());
		borderPanel.add(panel,BorderLayout.CENTER);
		borderPanel.add((Component) BorderFactory.createEmptyBorder(50, 50, 50, 50));
		
		return borderPanel;
	}
	

	public class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fileChooser=new JFileChooser();
			int showOpenDialog=fileChooser.showOpenDialog(fileChooser);
			
			if(showOpenDialog !=JFileChooser.APPROVE_OPTION)
					return;
				
			createAmazonS3Bucket();
			
			ProgressListener progressListener=new ProgressListener() {
				
				@Override
				public void progressChanged(ProgressEvent progressEvent) {
					if(upload)
					
				}
			};
	
		}
	
		private void createAmazonS3Bucket() {
			// TODO Auto-generated method stub
			
		}
	
	}

}