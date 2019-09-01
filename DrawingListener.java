package drawer2_1;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 1.定义DrawingListener事件处理类，该类实现MouseListener鼠标事件接口，重写接口中的抽象方法。
 * 
 * 矩形拖动效果 显示->每次拖动都需要绘制到窗体上,在第二次拖动时绘制之前的截图
 * 
 * 
 */
public class DrawingListener extends MouseAdapter implements ActionListener {

	private int x1, y1, x2, y2;// 声明四个变量，存储按下和释放的坐标值
	public int x_max, y_max, x_min, y_min, w, h, m, n;
	private Graphics2D g;// 声明画笔对象名（组件全部都是画出来的，要拿画笔，只要有组件对象就可以获取画笔）
	private JPanel panel;// 声明窗体对象名
	private Color color = Color.black;// 声明颜色属性
	private int lineWidth = 1;// 声明粗细属性
	private String type = "直线";// 声明图形属性
	public Random rand;
	public BufferedImage img;
	public Robot robot;
	public Rectangle rect;

	public DrawingListener(JPanel panel) {
		this.panel = panel;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		rand = new Random();
	}

	/**
	 * @param e对象中存储了事件源对象的信息和动作信息
	 */
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		if (e.getSource() instanceof JComboBox) {
			JComboBox<String> comboBox = (JComboBox) e.getSource();
			str = comboBox.getSelectedItem().toString();
		}
		isStr(str, e);// 根据str值确定事件

		ScreenCap();// 点击时截屏
	}

	public void isStr(String str, ActionEvent e) {
		if (str.equals("")) {// 表示点击的是颜色按钮
			JButton button = (JButton) e.getSource();// 获取事件源对象
			color = button.getBackground();// 获取按钮上的背景颜色
			System.out.println(color);
		} else if (str.equals("1") || str.equals("3") || str.equals("5")) {
			lineWidth = Integer.parseInt(str);// 将字符串转换为整型
		} else {
			type = str;// 获取图形
		}
		if (str.equals("清屏")) {
			g.setColor(panel.getBackground());
			g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
		}
		if (str.equals("导入图片")) {
			JFileChooser chooser = new JFileChooser(); // 创建选择文件对象
			chooser.setDialogTitle("请选择文件");// 设置标题
			chooser.setMultiSelectionEnabled(true); // 设置只能选择文件
			FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg",
					"jpg");// 定义可选择文件类型
			chooser.setFileFilter(filter); // 设置可选择文件类型
			chooser.showOpenDialog(null); // 打开选择文件对话框,null可设置为你当前的窗口JFrame或Frame
			File file = chooser.getSelectedFile(); // file为用户选择的图片文件
			ImageIcon icon = new ImageIcon(file.getPath() + "");
			g.drawImage(icon.getImage(), 0, 0, null);
		}
	}

	public void mousePressed(MouseEvent e) {
		// 获取按下坐标值
		x1 = e.getX();
		y1 = e.getY();
		// 从frame窗体上获取画笔对象 必须要在窗体可见之后才能获取画笔对象。
		if (g == null) {
			g = (Graphics2D) panel.getGraphics();
		}
		// 设置画笔的颜色
		g.setColor(color);
		g.setStroke(new BasicStroke(lineWidth));// 设置画笔的粗细
		ScreenCap();
	}

	public void mouseReleased(MouseEvent e) {
		// 获取释放坐标值
		x2 = e.getX();
		y2 = e.getY();

		x_max = Math.max(x1, x2);
		y_max = Math.max(y1, y2);
		x_min = Math.min(x1, x2);
		y_min = Math.min(y1, y2);
		w = Math.abs(x2 - x1);
		h = Math.abs(y2 - y1);

		ScreenCap();// 截取屏幕并保存
	}

	// 递归画三角形
	public void dui_angle(double x, double x1, double y1, double x2, double y2,
			double x3, double y3) {
		if (x < 25) {
			angle(x1, y1, x2, y2, x3, y3);

		} else {
			dui_angle(x / 2, (x1 + x2) / 2, (y1 + y2) / 2, (x3 + x2) / 2,
					(y3 + y2) / 2, (x1 + x3) / 2, (y1 + y3) / 2);

			g.setColor(Color.orange);
			g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
			g.setColor(Color.blue);
			g.drawLine((int) x3, (int) y3, (int) x2, (int) y2);
			g.setColor(Color.black);
			g.drawLine((int) x1, (int) y1, (int) x3, (int) y3);
			// dui_angle(x/2,(x1+x2)/4,(y1+y2)/4,(x3+x2)/4,(y3+y2)/4,(x1+x3)/4,(y1+y3)/4);
		}
	}

	// 画长方体
	private void Recter() {
		GeneralPath gp = new GeneralPath();

		g.drawRect(x_min, y_min, w, h);// 画矩形
		// 画顶面
		gp.append(new Line2D.Double(x_min, y_min, x_min + (int) (h / 2 / 1.4),
				y_min// 斜
						- (int) (h / 2 / 1.4)), true);
		gp.lineTo(x_min + (int) (h / 2 / 1.4) + w, y_min - (int) (h / 2 / 1.4));// 横
		gp.lineTo(x_min + w, y_min);// 斜
		gp.lineTo(x_min, y_min);// 斜
		g.setColor(new Color(rand.nextInt(128), rand.nextInt(128), rand
				.nextInt(128)));
		g.fill(gp);

		GeneralPath gp2 = new GeneralPath();
		gp2.append(new Line2D.Double(x_min + (int) (h / 2 / 1.4) + w, y_min
				- (int) (h / 2 / 1.4), x_min + (int) (h / 2 / 1.4) + w, y_min
				- (int) (h / 2 / 1.4) + h), true);// 竖
		gp2.lineTo(x_max, y_max);// 斜
		gp2.lineTo(x_min + w, y_min);// 竖
		gp2.lineTo(x_min + (int) (h / 2 / 1.4) + w, y_min - (int) (h / 2 / 1.4));// 斜
		// gp2.lineTo(x2, y2);
		g.setColor(new Color(127 + rand.nextInt(128), 127 + rand.nextInt(128),
				127 + rand.nextInt(128)));
		g.fill(gp2);

	}

	public void dr(double x, double y, double c, double n) {
		if (n < 25) {
		} else {
			dr1(x, y, c, n);// 三等分点画中心正方形
			dr1(x + c, y, c, n);
			dr1(x + 2 * c, y, c, n);
			dr1(x, y + c, c, n);
			dr1(x, y + 2 * c, c, n);
			dr1(x + 2 * c, y + 2 * c, c, n);
			dr1(x + c, y + 2 * c, c, n);
			dr1(x + 2 * c, y + c, c, n);
		}
	}

	public void dr1(double x, double y, double c, double n) {
		if (n < 25) {
		} else {
			dr(x, y, c / 3, n / 2);

			g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand
					.nextInt(255)));

			g.fillRect((int) (x + c / 3), (int) (y + c / 3), (int) c / 3,
					(int) c / 3);
		}
	}

	public void dui_angle2(double x, double x1, double y1, double x2,
			double y2, double x3, double y3) {
		if (x < 25) {
			angle(x1, y1, x2, y2, x3, y3);
		} else {
			// 上三角形
			dui_angle2(x / 2, x1, y1, (x2 + x1) / 2, (y2 + y1) / 2,
					(x1 + x3) / 2, (y1 + y3) / 2);
			// g.setColor(Color.blue);//可为不同的边设置不同颜色
			angle(x1, y1, x2, y2, x3, y3);
			// 中间三角形
			dui_angle2(x / 4, (x1 + x2) / 2, (y1 + y2) / 2, (x3 + x2) / 2,
					(y3 + y2) / 2, (x1 + x3) / 2, (y1 + y3) / 2);
			// g.setColor(Color.black);
			angle(x1, y1, x2, y2, x3, y3);

			// 左三角形
			dui_angle2(x / 2, x2, y2, (x2 + x1) / 2, (y2 + y1) / 2,
					(x2 + x3) / 2, (y2 + y3) / 2);
			// g.setColor(Color.red);
			angle(x1, y1, x2, y2, x3, y3);

			// 右三角形
			dui_angle2(x / 2, x3, y3, (x3 + x1) / 2, (y3 + y1) / 2,
					(x2 + x3) / 2, (y2 + y3) / 2);
			// g.setColor(Color.black);
			angle(x1, y1, x2, y2, x3, y3);
		}
	}

	public void angle(double x1, double y1, double x2, double y2, double x3,
			double y3) {
		g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
		g.drawLine((int) x3, (int) y3, (int) x2, (int) y2);
		g.drawLine((int) x1, (int) y1, (int) x3, (int) y3);
	}

	private void triangle() {
		if (x2 > x1) {
			g.drawLine(x1, y1, x2, y2);
			g.drawLine(x1, y1, x2 - 2 * w, y2);
			g.drawLine(x2 - 2 * w, y2, x2, y2);

		} else {
			g.drawLine(x2, y2, x1, y1);
			g.drawLine(x2, y2, x1 - 2 * w, y1);
			g.drawLine(x1 - 2 * w, y1, x1, y1);

		}
	}

	public BufferedImage getImage() {
		return img;
	}

	/**
	 * 当你在事件源上发生鼠标拖动动作时执行此方法
	 * 
	 * e对象中存储了事件源对象的信息和动作信息
	 */
	public void mouseDragged(MouseEvent e) {
		x2 = e.getX();
		y2 = e.getY();
		x_max = Math.max(x1, x2);
		y_max = Math.max(y1, y2);
		x_min = Math.min(x1, x2);
		y_min = Math.min(y1, y2);
		w = Math.abs(x2 - x1);
		h = Math.abs(y2 - y1);
		if (type.equals("曲线")) {
			g.drawLine(x1, y1, x2, y2);
			x1 = x2;
			y1 = y2;
		} else if (type.equals("橡皮擦")) {
			g.setColor(panel.getBackground());
			g.fillOval(x1, y1, 20, 20);
			x1 = x2;
			y1 = y2;
		} else {
			dragDraw();
		}
	}

	// 拖拽图像的绘制
	public void dragDraw() {
		g.drawImage(img, 0, 0, null);// 未释放时，img图像没有改变，拖动时消除轨迹
		if (type.equals("直线")) {
			g.drawLine(x1, y1, x2, y2);
		} else if (type.equalsIgnoreCase("等腰三角形")) {
			triangle();
		} else if (type.equalsIgnoreCase("长方体")) {
			Recter();
		} else if (type.equalsIgnoreCase("中心三角形")) {
			if (x2 > x1)
				dui_angle(2 * w, x1, y1, x2, y2, x_max - 2 * w, y2);
			else
				dui_angle(2 * w, x1, y1, x2, y2, x_max - 2 * w, y1);
		} else if (type.equalsIgnoreCase("地毯")) {
			g.setColor(Color.cyan);
			g.fillRect(x_min, y_min, w, w);
			dr1(x_min, y_min, w, h);
		} else if (type.equalsIgnoreCase("递归三角形")) {
			if (x2 > x1)
				dui_angle2(2 * w, x1, y1, x2, y2, x_max - 2 * w, y2);
			else
				dui_angle2(2 * w, x1, y1, x2, y2, x_max - 2 * w, y1);
		} else if (type.equals("矩形")) {
			g.drawRect(x_min, y_min, w, h);
		} else if (type.equals("填充圆")) {
			g.fillOval(x_min, y_min, w, h);
		}
	}

	// 重绘截屏，截取绘制面板
	private void ScreenCap() {
		m = panel.getLocationOnScreen().x;
		n = panel.getLocationOnScreen().y;
		rect = new Rectangle(m, n, panel.getWidth(), panel.getHeight());
		img = robot.createScreenCapture(rect);
	}
}
