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
	public DrawingListener dl;
	public Graphics2D g;

	public static void main(String[] args) {
		Drawing2_1 draw = new Drawing2_1();
		draw.initUI();
	}

	public void initUI() {
		// 窗体基本设置
		JFrame frame = new JFrame("Kevin之创意画板");
		frame.setSize(750, 500);
		frame.setLocationRelativeTo(null);// 使窗体位于屏幕中央
		frame.setDefaultCloseOperation(3);// 窗体关闭方式
		frame.setLayout(new BorderLayout());// 设置窗体的布局方式为流式布局

		// 添加工具 面板
		JPanel panel_op = new JPanel();
		JPanel panel_south = new JPanel();
		JLabel label = new JLabel("取色：");
		panel_op.setPreferredSize(new Dimension(90, 0));
		panel_op.setLayout(new GridLayout(15, 1));

		// 添加选项按钮(盒)
		JComboBox<String> faceCombo = new JComboBox<>();
		faceCombo.setEditable(true);// 使之可编辑
		faceCombo.setEnabled(true);

		String[] Array = { "直线", "曲线", "矩形", "填充圆", "长方体", "等腰三角形", "中心三角形",
				"递归三角形", "地毯" };
		for (int i = 0; i < Array.length; i++) {
			faceCombo.addItem(Array[i]);
		}
		panel_op.add(faceCombo);

		// 工具按钮

		// JButton button1[] = new JButton[Array.length];
		JButton[] button1 = { new JButton("橡皮擦"), new JButton("清屏"),
				new JButton("导入图片"), new JButton("1"), new JButton("3"),
				new JButton("5") };
		JButton[] button2 = { new JButton(), new JButton(), new JButton(),
				new JButton(), new JButton() };// 颜色按钮
		// 定义数组，用来存储按钮上要显示的颜色信息
		Color[] color_Array = { Color.black, Color.red, Color.GREEN, Color.BLUE };
		for (int i = 0; i < button1.length; i++) {// 重复的批量操作需要用到数组
			button1[i].setBackground(Color.cyan);
			// button1[i].setContentAreaFilled(false); // 设置按钮透明度(方法二)， 只须加上此句
			panel_op.add(button1[i]);
		}

		panel_south.add(label);
		for (int i = 0; i < color_Array.length; i++) {
			button2[i].setBackground(color_Array[i]);// 设置按钮上要显示的背景颜色
			button2[i].setPreferredSize(new Dimension(15, 15));
			panel_south.add(button2[i]);
		}
		frame.add(panel_op, BorderLayout.WEST);
		frame.add(panel_south, BorderLayout.SOUTH);
		// panel_op.add("SOUTH", panel_south);//frame不能用

		frame.add(this);
		panel_op.setBackground(new Color(220, 240, 230));
		panel_south.setBackground(new Color(218, 224, 241));
		panel_south.setPreferredSize(new Dimension(0, 20));
		this.setBackground(Color.white);
		frame.setVisible(true);
		dl = new DrawingListener(this, faceCombo);

		for (int i = 0; i < button1.length; i++) {
			// 给事件源对象添加动作监听方法，指定事件处理类对象为dl.
			button1[i].addActionListener(dl);
		}
		for (int i = 0; i < button2.length; i++) {
			// 给事件源对象添加动作监听方法，指定事件处理类对象为dl.
			button2[i].addActionListener(dl);
		}
		// System.out.println(Math.cos(90));
		faceCombo.addActionListener(dl);
		this.addMouseListener(dl);
		// 给事件源对象添加鼠标移动动作监听方法，指定事件处理类对象为dl。
		this.addMouseMotionListener(dl);

	}

	// a坐标=（x-y*sin72,y*(1-sin28)）
	// c坐标=（x,0）
	// e坐标=（x+y*sin72,y*(1-sin28) )
	// g坐标=（x+y*sin36,y*(1+sin54)）%
	// i坐标=（x-y*sin36,y*(1+sin54)）
	// j坐标=（x-(1-sin28)*tan28,y(1-sin28) )
	// b坐标=（x+(1-sin28)*tan28,y(1-sin28) )
	// 同理求出d、f、h坐标
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