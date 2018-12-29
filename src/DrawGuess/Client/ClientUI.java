package DrawGuess.Client;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class ClientUI extends JFrame {
    public JButton sendButton;
    public JLabel content;
    public JPanel drawPanel;
    public JPanel colorPanel;
    public JPanel drawLeftPanel;
    public JPanel centerPanel;
    public JTextField jTextField;
    public JTextArea jTextArea;
    public Graphics2D g;
    public Color color;
    public ClientController controller;
    public int x1, y1;
    public BasicStroke stroke;
    public JComboBox<Integer> box;
    public JTextField IPAddress;
    public JTextField username;
    public boolean connect;
    public JButton readyButton;
    public JButton clearButton;
    public JPanel loginPanel; // 登录面板
    public JPanel waitPanel;
    private int width;
    //鼠标监听器
    MouseAdapter mouseAdapter = new MouseAdapter() {

        public void mousePressed(MouseEvent e) {
            x1 = e.getX();
            y1 = e.getY();
        }

        public void mouseEntered(MouseEvent e) {
            if (color == null) {
                color = Color.black;
            }

            // System.out.println(i);
            g.setColor(color);

        }

        public void mouseDragged(MouseEvent e) {
            width = (Integer) box.getSelectedItem();
            int x2 = e.getX();
            int y2 = e.getY();
            controller.DrawAndSend(x1,x2,y1,y2,color.getRGB(),width);
//            System.out.println(x1 + " x1");
             x1 = x2;
             y1 = y2;

            // !调用controller的发送函数传递信息 (x1, y1, x2, y2,g.getColor().getRGB(),width);

            // try {
            //     controller.sendMsg1(socket.getOutputStream(), x1, y1, x2, y2,g.getColor().getRGB(),width);
            //     x1 = x2;
            //     y1 = y2;
            // } catch (IOException e1) {
            // }
        }
    };

    //聊天信息发送监听器
    ActionListener 聊天信息发送监听器 = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            //获取发送框的内容
            String str = jTextField.getText();
            if (str == null || str.equals("")) {
                // 弹窗破坏游戏体验！
                // JOptionPane.showMessageDialog(null, "发送内容不能为空！");
            } else {
//                String sended;
//                do{
//                    sended = controller.SendMsg(str);
//                }while(sended == "");
                //类似以上操作，因为controller.SendMsg(str)会返回一个String
                jTextField.setText( controller.SendMsg(str));


                // jTextField.setText() = （!调用controller的发送函数传递信息(str), 失败返回原字符串，成功返回空串）

                // try {
                //     control.dos.writeUTF(str);
                //     jtf.setText("");
                // } catch (IOException e1) {
                //     e1.printStackTrace();
                // }
            }

        }
    };
    
    // 登录按钮监听器
    ActionListener loginButtonListener = new ActionListener() {
    	
        public void actionPerformed(ActionEvent e) {
            //获取发送框的内容
            String IPAddressText = IPAddress.getText();
            String usernameText = username.getText();
            if (!IPAddressText.matches("\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}")
                    || usernameText == null || usernameText.equals("")) {
                JOptionPane.showMessageDialog(null, "参数错误！");
            } else {
            	//addDrawPanel();
            	PopDialog("正在连接当中。。。请稍后","你画我猜");
                // 控制器建立连接
                boolean isConnected = false;
                
                for( int i = 0; i < 5; i++ ){
                    isConnected = controller.connect(IPAddressText, usernameText);
                    if( isConnected ) break;
                }
                if(isConnected == true) {
                	System.out.println("connected");
                	LoginToWait();
                	controller.start();
//                	PopDialog("水果","提示");
//                	controller.start();
//                	if(controller.GetDGControl().equals("draw")){
//                	    System.out.println("UI:draw!");
//                        TransformToDrawGame();
//                    }
//                	if(controller.GetDGControl().equals("guess")){
//                	    System.out.println("UI:guess!");
//                	    TransformToGuessGame();
//                        content.setText("猜的提示信息: "+"水果");
//                    }
                }
                // try {
                //     socket = new Socket(str1, 9090);
                // } catch (Exception e1) {
                //     e1.printStackTrace();
                // }
                // connect = true;
                // try {
                //     control.dos.writeUTF(str2);
                //     jtf.setText("");
                // } catch (IOException e1) {
                //     e1.printStackTrace();
                // */
            }
            
        }
    };
    ActionListener exitButtonListener = e -> System.exit(0);

    ActionListener 输入框发送监听 = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            sendButton.doClick();
        }
    };

    ActionListener readyButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.ChangeReady();
        }
    };

    ActionListener clearButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.SendAndClear();
        }
    };

    //初始化界面的时候开始创建客户端对象
    public ClientUI() {
        connect = false;
        initFrame();
    }

    public void initFrame() {
        int frameWeight = 960;
        int frameHeight = 540;

        this.setTitle("你画我猜");
        this.setSize(frameWeight, frameHeight);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        // 总的面板
        // JPanel panel = new JPanel();
        // 等待面板
        JLabel welcome = new JLabel("欢迎来到第十一小组的你画我猜游戏");
        JLabel 分割线 = new JLabel("-----------loginPanel-----------");
        JLabel IPAddressLabel = new JLabel("IP地址:");
        JLabel usernameLabel = new JLabel("用户名:");
        //label.setLayout(null);


        IPAddress = new JTextField(20);
        username = new JTextField(20);

        // ==== 初始化登陆面板
        loginPanel = new JPanel();
       
        loginPanel.setBackground(Color.decode("0xd08cd9")); // 登录框背景颜色
        // loginPanel.setLayout(new BorderLayout());
        // label.setPreferredSize(new Dimension(0,50));
        loginPanel.setLayout(null);

        loginPanel.add(分割线);
        分割线.setLocation(50, 50);
        分割线.setSize(300, 20);

        loginPanel.add(welcome);
        welcome.setLocation(45, 30);
        welcome.setSize(300, 20);

        loginPanel.add(IPAddressLabel);
        IPAddressLabel.setLocation(90 - 50 + 20, 95);
        IPAddressLabel.setSize(120, 20);

        loginPanel.add(IPAddress);
        IPAddress.setLocation(90 + 20, 95);
        IPAddress.setSize(120, 20);

        loginPanel.add(usernameLabel);
        usernameLabel.setLocation(90 - 50 + 20, 145);
        usernameLabel.setSize(120, 20);

        loginPanel.add(username);
        username.setLocation(90 + 20, 145);
        username.setSize(120, 20);

        // ==== 初始化登陆面板

        loginPanel.setPreferredSize(new Dimension(0, 150)); // 宽度随窗口，高度150

        JButton loginButton = new JButton();
        JButton exitButton = new JButton();
        //loginButton = new JButton();
        loginButton.setText("登录");
        exitButton.setText("退出");

        loginButton.addActionListener(loginButtonListener);
        exitButton.addActionListener(exitButtonListener);

        loginPanel.add(loginButton);
        loginPanel.add(exitButton);

        loginButton.setLocation(90, 175);
        loginButton.setSize(80, 30);

        exitButton.setLocation(90 + 20 + 80, 175);
        exitButton.setSize(80, 30);

        //	this.add(waitPanel);
        // 画的面板
		//addDrawPanel();
//		addGuessPanel();
        this.setLayout(new BorderLayout());
        //waitPanel.setLayout(null);
        this.add(loginPanel, BorderLayout.CENTER);
        //loginPanel.setLocation(60, 120);
        //loginPanel.setSize(150,100);
        this.setVisible(true);
        // 创建客户端控制器对象
        /* if (connect) {
            controller = new ClientCtroller(socket, this);
            control.dealwith();
        }
        */
        controller = new ClientController(this);
        //if(controller.connect(IPAddress.getText(),username.getText()) == true){System.out.println("connected!");controller.start();}
        //else JOptionPane.showMessageDialog(null, "连接失败！");
    }

    // remove login panel and transform
    public void WaitToGuessGame() {
    	this.remove(waitPanel);
    	this.addGuessPanel();
    }
    public void WaitToDrawGame(String str) {
    	this.remove(waitPanel);
    	this.addDrawPanel();
        content.setText(str);
    }
    public void GameToWait(){
        this.remove(drawPanel);
        addWaitPanel();
    }
    private void LoginToWait(){
        this.remove(loginPanel);
        addWaitPanel();
    }
    // 创建等待面板
    public void addWaitPanel(){
        this.remove(loginPanel);
        jTextArea = new JTextArea();
        jTextArea.setLineWrap(true);
        waitPanel = new JPanel();
        waitPanel.setLayout(new BorderLayout());
        waitPanel.add(jTextArea,BorderLayout.CENTER);
        readyButton = new JButton("准备");
        readyButton.setFocusPainted(false);
        readyButton.setFont(new Font("方正大黑_GBK", Font.PLAIN, 20));
        readyButton.addActionListener(readyButtonListener);
        waitPanel.add(readyButton,BorderLayout.SOUTH);
        this.add(waitPanel);
        this.setVisible(true);
    }

    // 创建画布面板
    public void addDrawPanel() {
        drawPanel = new JPanel();
        drawPanel.setLayout(new BorderLayout());
        // 画面板的左右子面板
        drawLeftPanel = new JPanel();
        drawLeftPanel.setLayout(new BorderLayout());
        //JPanel
        //左边板的中间面板
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        //左面板下的颜色面板
        colorPanel = new JPanel();

        //给颜色面板设置空布局
        colorPanel.setLayout(null);
        colorPanel.setBackground(Color.gray);
        colorPanel.setPreferredSize(new Dimension(0, 60));

        //颜色面板的颜色按钮
        Color[] colors = {Color.red, Color.black, Color.orange, Color.green,
                Color.pink, Color.blue, Color.cyan, Color.magenta, Color.YELLOW, Color.WHITE};

        //颜色按钮添加
        ActionListener buttonListener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                JButton button = (JButton) e.getSource();
                color = button.getBackground();
            }
        };

        for (int i = 0; i < colors.length; i++) {
            JButton button = new JButton();
            button.setBackground(colors[i]);
            button.addActionListener(buttonListener);
            button.setBounds(40 + i * 30, 15, 30, 30);
            colorPanel.add(button);
        }


        //添加画笔粗细  <-- 能否像颜色那样直接显示
        box = new JComboBox<Integer>();
        box.setBounds(380, 15, 80, 30);
        for (int i = 0; i < 10; i++) {
            @SuppressWarnings("deprecation")
            Integer intdata = new Integer(i + 1);
            box.addItem(intdata);
        }
        colorPanel.add(box);


        // 右面板
        JPanel drawRightPanel = new JPanel();

        drawRightPanel.setLayout(new BorderLayout());
        drawRightPanel.setPreferredSize(new Dimension(200, 0));

        // 右面板的的下面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(0, 50));
        jTextArea = new JTextArea();
        jTextArea.setLineWrap(true);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jTextField = new JTextField(11);
        jTextField.addActionListener(输入框发送监听);
        content = new JLabel();
        sendButton = new JButton();
        sendButton.setText("发送");
        sendButton.addActionListener(聊天信息发送监听器);
        buttonPanel.add(jTextField);
        buttonPanel.add(sendButton);

        drawRightPanel.add(jScrollPane);
        drawRightPanel.add(buttonPanel, BorderLayout.SOUTH);
        clearButton = new JButton("清空");
        clearButton.setFocusPainted(false);
        clearButton.setFont(new Font("方正大黑_GBK", Font.PLAIN, 20));
        clearButton.addActionListener(clearButtonListener);
        colorPanel.add(clearButton);
        clearButton.setLocation(540, 10);
        clearButton.setSize(120, 40);


        content.setPreferredSize(new Dimension(0, 20));
        drawLeftPanel.add(content, BorderLayout.NORTH);
        drawLeftPanel.add(centerPanel, BorderLayout.CENTER);
        drawLeftPanel.add(colorPanel, BorderLayout.SOUTH);
        drawPanel.add(drawLeftPanel);
        drawPanel.add(drawRightPanel, BorderLayout.EAST);
        this.add(drawPanel);
        centerPanel.addMouseListener(mouseAdapter);
        centerPanel.addMouseMotionListener(mouseAdapter);
        this.setVisible(true);
        g = (Graphics2D) centerPanel.getGraphics();
        sendButton.setEnabled(false);
    }
    private void PopDialog(String str,String str2) {
    	JOptionPane.showMessageDialog(null, str,str2,JOptionPane.PLAIN_MESSAGE);
    }
    //添加猜面板的函数
    private void addGuessPanel() {
        addDrawPanel();
        // drawPanel.remove(colorPanel);
        sendButton.setEnabled(true);
    }
    public void Draw(int x1,int x2,int y1,int y2){
        stroke = new BasicStroke(width);
        g.setStroke(stroke);
        g.drawLine(x1,y1,x2,y2);
    }
    public void SetDraw(int color,int width){
        this.width = width;
        this.color = new Color(color);
        g.setColor(this.color);
        g.setStroke(new BasicStroke(width));
    }
    public void Clear(){
        g.setColor(Color.WHITE);
        g.fillRect(centerPanel.getX(),centerPanel.getY(),centerPanel.getWidth(),centerPanel.getHeight());
        g.setColor(color);
    }
}
