package com.mhuiq.httpMocker.httpClient;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import com.mhuiq.httpMocker.listener.TextFieldFocusListener;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class HttpSimulator {

	private JFrame frame;
	private JTextField hostField;	 	// 主机输入框
	private JTextField portField; 		// 端口输入框
	private JComboBox<String> methodComboBox;	// 方法选择框
	private JTextField urlField; 		// uri输入框
	private JTextField connTmoField; 	// 连接超时时间输入框
	private JTextField respTmoField; 	// 接受超时时间输入框
	private JTextArea paraTextArea;  	// 参数输入框
	private JTabbedPane tabbedPane; 
	private JTextArea headerTextArea;	// 响应头输入框
	private JTextArea respTextArea;		// 响应数据输入框
	private JComboBox<String> charsetComboBox;
	private JComboBox<String> httpPotocomboBox;
	private JComboBox<String> ksAlgcomboBox;
	private JComboBox<String> tksAlgcomboBox; 
	private JComboBox<String> kmfAlgcomboBox;

	private final static String POST = "POST";
	private final static String GET = "GET";
	
	private FocusListener textFieldFocusListener = new TextFieldFocusListener();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("RootPane.setupButtonVisible", false);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HttpSimulator window = new HttpSimulator();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HttpSimulator() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Http请求模拟器");
		frame.setResizable(false);
		frame.setBounds(100, 100, 598, 638);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Image image = null;
		try {
			image = ImageIO.read(this.getClass().getResourceAsStream("/logo/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.setIconImage(image);
		
		JPanel panel = new JPanel();
		panel.setName("panel");
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		hostField = new JTextField(ConstDef.HOST_TIP);
		hostField.setName(ConstDef.hostField);
		hostField.setBounds(10, 7, 204, 28);
		hostField.addFocusListener(textFieldFocusListener);
		
		panel.add(hostField);
		hostField.setColumns(10);
		
		portField = new JTextField(ConstDef.PORT_TIP);
		portField.addFocusListener(textFieldFocusListener);
		portField.setName(ConstDef.portField);
		portField.setBounds(237, 7, 69, 28);
		panel.add(portField);
		portField.setColumns(10);
		
		methodComboBox = new JComboBox<String>();
		methodComboBox.setBounds(326, 9, 84, 27);
		methodComboBox.addItem("GET");
		methodComboBox.addItem("POST");
		panel.add(methodComboBox);
		
		urlField = new JTextField(ConstDef.URL_TIP);
		urlField.addFocusListener(textFieldFocusListener);
		urlField.setBounds(10, 45, 204, 28);
		urlField.setName(ConstDef.urlField);
		panel.add(urlField);
		urlField.setColumns(10);
		
		JLabel lblParameters = new JLabel("Parameters:");
		lblParameters.setBounds(10, 199, 73, 16);
		panel.add(lblParameters);
		
		JLabel lblResponses = new JLabel("Responses:");
		lblResponses.setBounds(10, 367, 73, 16);
		panel.add(lblResponses);
		
		JButton button = new JButton("发送");
		button.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		button.setForeground(Color.white);
		button.setBounds(440, 159, 77, 23);
		button.addActionListener(sendListener);
		panel.add(button);
		
		connTmoField = new JTextField(ConstDef.CONNTMO_TIP);
		connTmoField.addFocusListener(textFieldFocusListener);
		connTmoField.setName(ConstDef.connTmoField);
		connTmoField.setBounds(237, 47, 69, 28);
		panel.add(connTmoField);
		connTmoField.setColumns(10);
		
		respTmoField = new JTextField(ConstDef.RESPTMO_TIP);
		respTmoField.addFocusListener(textFieldFocusListener);
		respTmoField.setName(ConstDef.respTmoField);
		respTmoField.setBounds(326, 48, 84, 28);
		panel.add(respTmoField);
		respTmoField.setColumns(10);
		
		JScrollPane jsp = new JScrollPane();
		jsp.setBounds(10, 236, 507, 121);
		panel.add(jsp);
		
		paraTextArea = new JTextArea();
		paraTextArea.setWrapStyleWord(true);
		paraTextArea.setLineWrap(true);
		paraTextArea.setColumns(100);
		jsp.setViewportView(paraTextArea);
		
		JScrollPane headerJsp = new JScrollPane();
		headerJsp.setBounds(6, 126, 446, 121);
		
		headerTextArea = new JTextArea();
		headerJsp.setViewportView(headerTextArea);
		
		
		JScrollPane respJsp = new JScrollPane();
		respJsp.setBounds(6, 126, 446, 121);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 393, 507, 146);
		tabbedPane.add("Header", headerJsp);
		tabbedPane.add("Response", respJsp);
		
		respTextArea = new JTextArea();
		respTextArea.setWrapStyleWord(true);
		respTextArea.setLineWrap(true);
		respTextArea.setColumns(100);
		respJsp.setViewportView(respTextArea);
		
		panel.add(tabbedPane);
		
		charsetComboBox = new JComboBox<String>();
		charsetComboBox.setBounds(430, 10, 87, 25);
		charsetComboBox.addItem("UTF-8");
		charsetComboBox.addItem("GBK");
		panel.add(charsetComboBox);
		
		httpPotocomboBox = new JComboBox<String>();
		httpPotocomboBox.setBounds(430, 48, 87, 25);
		httpPotocomboBox.addItem("HTTP");
		httpPotocomboBox.addItem("HTTPS");
		panel.add(httpPotocomboBox);
		
		keyStorePathField = new JTextField();
		keyStorePathField.setColumns(10);
		keyStorePathField.setBounds(10, 83, 123, 28);
		panel.add(keyStorePathField);
		
		trustKeyStorePathField = new JTextField();
		trustKeyStorePathField.setColumns(10);
		trustKeyStorePathField.setBounds(10, 119, 123, 28);
		panel.add(trustKeyStorePathField);
		
		JButton keystoreChoserButton = new JButton("证书");
		keystoreChoserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);  
		        jfc.showDialog(new JLabel(), "客户端证书");
		        if (null != jfc.getSelectedFile()) {
		        	keyStorePathField.setText(jfc.getSelectedFile().getAbsolutePath());
		        }
			}
		});
		keystoreChoserButton.setBounds(145, 85, 69, 26);
		panel.add(keystoreChoserButton);
		
		JButton trustKeyStorebtn = new JButton("信任库");
		trustKeyStorebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);  
		        jfc.showDialog(new JLabel(), "信任库");  
		        if (null != jfc.getSelectedFile()) {
		        	trustKeyStorePathField.setText(jfc.getSelectedFile().getAbsolutePath());
		        }
			}
		});
		trustKeyStorebtn.setBounds(145, 121, 69, 26);
		panel.add(trustKeyStorebtn);
		
		keyStorePwdField = new JTextField(ConstDef.KEYSTORE_PWD_TIP);
		keyStorePwdField.addFocusListener(textFieldFocusListener);
		keyStorePwdField.setName("keyStorePwdField");
		keyStorePwdField.setColumns(10);
		keyStorePwdField.setBounds(237, 83, 69, 28);
		panel.add(keyStorePwdField);
		
		trustKeyStorePwdField = new JTextField(ConstDef.TRUSTKEYSTORE_PWD_TIP);
		trustKeyStorePwdField.addFocusListener(textFieldFocusListener);
		trustKeyStorePwdField.setName("trustKeyStorePwdField");
		trustKeyStorePwdField.setColumns(10);
		trustKeyStorePwdField.setBounds(237, 119, 69, 28);
		panel.add(trustKeyStorePwdField);
		
		ksAlgcomboBox = new JComboBox<String>();
		ksAlgcomboBox.setBounds(324, 86, 86, 25);
		ksAlgcomboBox.addItem("JKS");
		panel.add(ksAlgcomboBox);
		
		tksAlgcomboBox = new JComboBox<String>();
		tksAlgcomboBox.setBounds(324, 122, 86, 25);
		tksAlgcomboBox.addItem("JKS");
		panel.add(tksAlgcomboBox);
		
		kmfAlgcomboBox = new JComboBox<String>();
		kmfAlgcomboBox.setBounds(430, 86, 87, 25);
		kmfAlgcomboBox.addItem("SunX509");
		panel.add(kmfAlgcomboBox);
		
		tmfAlgcomboBox = new JComboBox<String>();
		tmfAlgcomboBox.setBounds(430, 122, 87, 25);
		tmfAlgcomboBox.addItem("SunX509");
		panel.add(tmfAlgcomboBox);
		
		protocomboBox = new JComboBox<String>();
		protocomboBox.setVisible(false);
		protocomboBox.setBounds(324, 159, 89, 25);
		protocomboBox.addItem("SSL");
		protocomboBox.addItem("TLS");
		panel.add(protocomboBox);
		
	}
	
	private ActionListener sendListener = new ActionListener() {
		
		public void actionPerformed(ActionEvent e) { 
			headerTextArea.setText("");
			respTextArea.setText("");
			
			try {
				HttpClientCfg clientCfg = new HttpClientCfg();
				String host = ConstDef.HOST_TIP.equals(hostField.getText().trim()) ? "" : hostField.getText().trim();
				if (host.length() < 1) {
					headerTextArea.append("主机名称不能为空\r\n");
					return;
				}
				clientCfg.setHost(host);
				String portStr = ConstDef.PORT_TIP.equals(portField.getText().trim()) ? "" : portField.getText().trim();
				int port = portStr.length() < 1 ? 80 : Integer.valueOf(portStr);
				clientCfg.setPort(port);
				clientCfg.setMethod((String) methodComboBox.getSelectedItem());
				clientCfg.setPath(ConstDef.URL_TIP.equals(urlField.getText().trim()) ? "" : urlField.getText().trim());
				String connTmoStr = ConstDef.CONNTMO_TIP.equals(connTmoField.getText().trim()) ? "" : connTmoField.getText().trim();
				String respTmoStr = ConstDef.RESPTMO_TIP.equals(respTmoField.getText().trim()) ? "" : respTmoField.getText().trim();
				int connTmo = Integer.valueOf(connTmoStr.length() < 1 ? 0 : Integer.valueOf(connTmoStr));
				int respTmo = Integer.valueOf(respTmoStr.length() < 1 ? 0 : Integer.valueOf(respTmoStr));
				clientCfg.setCharset((String)charsetComboBox.getSelectedItem());
				clientCfg.setConnTmo(connTmo);
				clientCfg.setRespTmo(respTmo);
				clientCfg.setParams(paraTextArea.getText().trim());
				clientCfg.setPotol((String) httpPotocomboBox.getSelectedItem());
				clientCfg.setKeyStorePath(keyStorePathField.getText());
				clientCfg.setTrustKeyStorePath(trustKeyStorePathField.getText());
				clientCfg.setKeyStorePwd(keyStorePwdField.getText());
				clientCfg.setTrustKeyStorePwd(trustKeyStorePwdField.getText());
				clientCfg.setKeyStoreAlg((String)ksAlgcomboBox.getSelectedItem());
				clientCfg.setTrustKeyStoreAlg((String)tksAlgcomboBox.getSelectedItem());
				clientCfg.setKmfAlg((String)kmfAlgcomboBox.getSelectedItem());
				clientCfg.setTmfAlg((String)tmfAlgcomboBox.getSelectedItem());
				clientCfg.setEncryAlg((String)protocomboBox.getSelectedItem());
				Map<String, Object> resultMap = null;
				HttpService request = null;
				switch (clientCfg.getPotol()) {
				case "HTTP":
					request = new HttpServiceNoHttps(clientCfg);
					break;
				case "HTTPS":
					request = new HttpServiceForHttps(clientCfg);
					
				}
				switch (clientCfg.getMethod()) {
				case GET:
					resultMap = request.sendGet();
					break;
				case POST:
					resultMap = request.sendPost();
					break;
				}
				if (resultMap == null) {
					headerTextArea.append("发送失败\r\n");
					return;
				}
				List<String> headerList = (List<String>) resultMap.get("HEADER");
				for (String val : headerList) {
					headerTextArea.append(val + "\r\n");
				}
				respTextArea.append(resultMap.get("BODY") + "\r\n");
			} catch (Exception e1) {
				if (e1 instanceof NumberFormatException) {
					headerTextArea.append("端口号或超时时间不能为非数字类型\r\n");
					return;
				} else {
					headerTextArea.append(e1.getMessage());
					return;
				}
				
			}
			
		}
	};
	private JTextField keyStorePathField;
	private JTextField trustKeyStorePathField;
	private JTextField keyStorePwdField;
	private JTextField trustKeyStorePwdField;
	private JComboBox<String> tmfAlgcomboBox;
	private JComboBox<String> protocomboBox;
}
