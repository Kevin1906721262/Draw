package drawer2_1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Drawing2_1 extends JPanel {
	public BufferedImage img;
	public Graphics2D g;
	private JFrame frame;
	private JButton[] buttons;
	private JComboBox<String> faceCombo;
	public DrawingListener dl = new DrawingListener(this);

	public static void main(String[] args) {
		Drawing2_1 draw = new Drawing2_1();
		draw.init();
	}

	public void init() {
		frame = new JFrame("Kevin之创意画板");
		frame.setSize(750, 500);
		frame.setLocationRelativeTo(null);// 使窗体位于屏幕中央
		frame.setDefaultCloseOperation(3);// 窗体关闭方式
		frame.setLayout(new BorderLayout());// 设置窗体的布局方式为边界布局

		initLeftPanel();// 初始化左边面板区
		initSouthPanel();// 初始化下面取色区

		this.setBackground(Color.white);
		frame.add(this);
		frame.setVisible(true);

		this.addMouseListener(dl);
		// 给事件源对象添加鼠标移动动作监听方法，指定事件处理类对象为dl。
		this.addMouseMotionListener(dl);
	}

	public void initSouthPanel() {
		JPanel panel_south = new JPanel();
		JLabel label = new JLabel("取色：");
		JButton[] button = { new JButton(), new JButton(), new JButton(),
				new JButton() };// 颜色按钮
		// 定义数组，用来存储按钮上要显示的颜色信息
		Color[] color_Array = { Color.black, Color.red, Color.GREEN, Color.BLUE };

		panel_south.add(label);
		for (int i = 0; i < color_Array.length; i++) {
			button[i].setBackground(color_Array[i]);// 设置按钮上要显示的背景颜色
			button[i].setPreferredSize(new Dimension(15, 15));
			panel_south.add(button[i]);
			// 给事件源对象添加动作监听方法，指定事件处理类对象为dl.
			button[i].addActionListener(dl);
		}
		frame.add(panel_south, BorderLayout.SOUTH);
		panel_south.setBackground(new Color(218, 224, 241));
		panel_south.setPreferredSize(new Dimension(0, 20));
	}

	public void initLeftPanel() {
		JPanel panel_left = new JPanel();
		panel_left.setPreferredSize(new Dimension(90, 0));
		panel_left.setLayout(new GridLayout(15, 1));

		faceCombo = new JComboBox<>();
		faceCombo.setEditable(true);// 使之可编辑
		faceCombo.setEnabled(true);

		String[] Array = { "直线", "曲线", "矩形", "填充圆", "长方体", "等腰三角形", "中心三角形",
				"递归三角形", "地毯" };
		for (int i = 0; i < Array.length; i++) {
			faceCombo.addItem(Array[i]);
		}
		panel_left.add(faceCombo);
		faceCombo.addActionListener(dl);

		// 工具按钮
		buttons = new JButton[] { new JButton("橡皮擦"), new JButton("清屏"),
				new JButton("导入图片"), new JButton("1"), new JButton("3"),
				new JButton("5") };
		for (int i = 0; i < buttons.length; i++) {// 重复的批量操作需要用到数组
			buttons[i].setBackground(Color.cyan);
			// button1[i].setContentAreaFilled(false); // 设置按钮透明度(方法二)， 只须加上此句
			panel_left.add(buttons[i]);
			// 给事件源对象添加动作监听方法，指定事件处理类对象为dl.
			buttons[i].addActionListener(dl);
		}
		panel_left.setBackground(new Color(220, 240, 230));
		frame.add(panel_left, BorderLayout.WEST);
	}

	/**
	 * 重写JFrame的重绘方法
	 */
	public void paint(Graphics g) {
		super.paint(g);// 调用父类绘制组件的方法
		img = dl.getImage();
		if (img != null) {
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}
}