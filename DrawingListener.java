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
 * 1.����DrawingListener�¼������࣬����ʵ��MouseListener����¼��ӿڣ���д�ӿ��еĳ��󷽷���
 * 
 * �����϶�Ч�� ��ʾ->ÿ���϶�����Ҫ���Ƶ�������,�ڵڶ����϶�ʱ����֮ǰ�Ľ�ͼ
 * 
 * 
 */
public class DrawingListener extends MouseAdapter implements ActionListener {

	private int x1, y1, x2, y2;// �����ĸ��������洢���º��ͷŵ�����ֵ
	public int x_max, y_max, x_min, y_min, w, h, m, n;
	private Graphics2D g;// �������ʶ����������ȫ�����ǻ������ģ�Ҫ�û��ʣ�ֻҪ���������Ϳ��Ի�ȡ���ʣ�
	private JPanel panel;// �������������
	private Color color = Color.black;// ������ɫ����
	private int lineWidth = 1;// ������ϸ����
	private String type = "ֱ��";// ����ͼ������
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
	 * @param e�����д洢���¼�Դ�������Ϣ�Ͷ�����Ϣ
	 */
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		if (e.getSource() instanceof JComboBox) {
			JComboBox<String> comboBox = (JComboBox) e.getSource();
			str = comboBox.getSelectedItem().toString();
		}
		isStr(str, e);// ����strֵȷ���¼�

		ScreenCap();// ���ʱ����
	}

	public void isStr(String str, ActionEvent e) {
		if (str.equals("")) {// ��ʾ���������ɫ��ť
			JButton button = (JButton) e.getSource();// ��ȡ�¼�Դ����
			color = button.getBackground();// ��ȡ��ť�ϵı�����ɫ
			System.out.println(color);
		} else if (str.equals("1") || str.equals("3") || str.equals("5")) {
			lineWidth = Integer.parseInt(str);// ���ַ���ת��Ϊ����
		} else {
			type = str;// ��ȡͼ��
		}
		if (str.equals("����")) {
			g.setColor(panel.getBackground());
			g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
		}
		if (str.equals("����ͼƬ")) {
			JFileChooser chooser = new JFileChooser(); // ����ѡ���ļ�����
			chooser.setDialogTitle("��ѡ���ļ�");// ���ñ���
			chooser.setMultiSelectionEnabled(true); // ����ֻ��ѡ���ļ�
			FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg",
					"jpg");// �����ѡ���ļ�����
			chooser.setFileFilter(filter); // ���ÿ�ѡ���ļ�����
			chooser.showOpenDialog(null); // ��ѡ���ļ��Ի���,null������Ϊ�㵱ǰ�Ĵ���JFrame��Frame
			File file = chooser.getSelectedFile(); // fileΪ�û�ѡ���ͼƬ�ļ�
			ImageIcon icon = new ImageIcon(file.getPath() + "");
			g.drawImage(icon.getImage(), 0, 0, null);
		}
	}

	public void mousePressed(MouseEvent e) {
		// ��ȡ��������ֵ
		x1 = e.getX();
		y1 = e.getY();
		// ��frame�����ϻ�ȡ���ʶ��� ����Ҫ�ڴ���ɼ�֮����ܻ�ȡ���ʶ���
		if (g == null) {
			g = (Graphics2D) panel.getGraphics();
		}
		// ���û��ʵ���ɫ
		g.setColor(color);
		g.setStroke(new BasicStroke(lineWidth));// ���û��ʵĴ�ϸ
		ScreenCap();
	}

	public void mouseReleased(MouseEvent e) {
		// ��ȡ�ͷ�����ֵ
		x2 = e.getX();
		y2 = e.getY();

		x_max = Math.max(x1, x2);
		y_max = Math.max(y1, y2);
		x_min = Math.min(x1, x2);
		y_min = Math.min(y1, y2);
		w = Math.abs(x2 - x1);
		h = Math.abs(y2 - y1);

		ScreenCap();// ��ȡ��Ļ������
	}

	// �ݹ黭������
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

	// ��������
	private void Recter() {
		GeneralPath gp = new GeneralPath();

		g.drawRect(x_min, y_min, w, h);// ������
		// ������
		gp.append(new Line2D.Double(x_min, y_min, x_min + (int) (h / 2 / 1.4),
				y_min// б
						- (int) (h / 2 / 1.4)), true);
		gp.lineTo(x_min + (int) (h / 2 / 1.4) + w, y_min - (int) (h / 2 / 1.4));// ��
		gp.lineTo(x_min + w, y_min);// б
		gp.lineTo(x_min, y_min);// б
		g.setColor(new Color(rand.nextInt(128), rand.nextInt(128), rand
				.nextInt(128)));
		g.fill(gp);

		GeneralPath gp2 = new GeneralPath();
		gp2.append(new Line2D.Double(x_min + (int) (h / 2 / 1.4) + w, y_min
				- (int) (h / 2 / 1.4), x_min + (int) (h / 2 / 1.4) + w, y_min
				- (int) (h / 2 / 1.4) + h), true);// ��
		gp2.lineTo(x_max, y_max);// б
		gp2.lineTo(x_min + w, y_min);// ��
		gp2.lineTo(x_min + (int) (h / 2 / 1.4) + w, y_min - (int) (h / 2 / 1.4));// б
		// gp2.lineTo(x2, y2);
		g.setColor(new Color(127 + rand.nextInt(128), 127 + rand.nextInt(128),
				127 + rand.nextInt(128)));
		g.fill(gp2);

	}

	public void dr(double x, double y, double c, double n) {
		if (n < 25) {
		} else {
			dr1(x, y, c, n);// ���ȷֵ㻭����������
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
			// ��������
			dui_angle2(x / 2, x1, y1, (x2 + x1) / 2, (y2 + y1) / 2,
					(x1 + x3) / 2, (y1 + y3) / 2);
			// g.setColor(Color.blue);//��Ϊ��ͬ�ı����ò�ͬ��ɫ
			angle(x1, y1, x2, y2, x3, y3);
			// �м�������
			dui_angle2(x / 4, (x1 + x2) / 2, (y1 + y2) / 2, (x3 + x2) / 2,
					(y3 + y2) / 2, (x1 + x3) / 2, (y1 + y3) / 2);
			// g.setColor(Color.black);
			angle(x1, y1, x2, y2, x3, y3);

			// ��������
			dui_angle2(x / 2, x2, y2, (x2 + x1) / 2, (y2 + y1) / 2,
					(x2 + x3) / 2, (y2 + y3) / 2);
			// g.setColor(Color.red);
			angle(x1, y1, x2, y2, x3, y3);

			// ��������
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
	 * �������¼�Դ�Ϸ�������϶�����ʱִ�д˷���
	 * 
	 * e�����д洢���¼�Դ�������Ϣ�Ͷ�����Ϣ
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
		if (type.equals("����")) {
			g.drawLine(x1, y1, x2, y2);
			x1 = x2;
			y1 = y2;
		} else if (type.equals("��Ƥ��")) {
			g.setColor(panel.getBackground());
			g.fillOval(x1, y1, 20, 20);
			x1 = x2;
			y1 = y2;
		} else {
			dragDraw();
		}
	}

	// ��קͼ��Ļ���
	public void dragDraw() {
		g.drawImage(img, 0, 0, null);// δ�ͷ�ʱ��imgͼ��û�иı䣬�϶�ʱ�����켣
		if (type.equals("ֱ��")) {
			g.drawLine(x1, y1, x2, y2);
		} else if (type.equalsIgnoreCase("����������")) {
			triangle();
		} else if (type.equalsIgnoreCase("������")) {
			Recter();
		} else if (type.equalsIgnoreCase("����������")) {
			if (x2 > x1)
				dui_angle(2 * w, x1, y1, x2, y2, x_max - 2 * w, y2);
			else
				dui_angle(2 * w, x1, y1, x2, y2, x_max - 2 * w, y1);
		} else if (type.equalsIgnoreCase("��̺")) {
			g.setColor(Color.cyan);
			g.fillRect(x_min, y_min, w, w);
			dr1(x_min, y_min, w, h);
		} else if (type.equalsIgnoreCase("�ݹ�������")) {
			if (x2 > x1)
				dui_angle2(2 * w, x1, y1, x2, y2, x_max - 2 * w, y2);
			else
				dui_angle2(2 * w, x1, y1, x2, y2, x_max - 2 * w, y1);
		} else if (type.equals("����")) {
			g.drawRect(x_min, y_min, w, h);
		} else if (type.equals("���Բ")) {
			g.fillOval(x_min, y_min, w, h);
		}
	}

	// �ػ��������ȡ�������
	private void ScreenCap() {
		m = panel.getLocationOnScreen().x;
		n = panel.getLocationOnScreen().y;
		rect = new Rectangle(m, n, panel.getWidth(), panel.getHeight());
		img = robot.createScreenCapture(rect);
	}
}
