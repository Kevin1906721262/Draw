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
		frame = new JFrame("Kevin֮���⻭��");
		frame.setSize(750, 500);
		frame.setLocationRelativeTo(null);// ʹ����λ����Ļ����
		frame.setDefaultCloseOperation(3);// ����رշ�ʽ
		frame.setLayout(new BorderLayout());// ���ô���Ĳ��ַ�ʽΪ�߽粼��

		initLeftPanel();// ��ʼ����������
		initSouthPanel();// ��ʼ������ȡɫ��

		this.setBackground(Color.white);
		frame.add(this);
		frame.setVisible(true);

		this.addMouseListener(dl);
		// ���¼�Դ�����������ƶ���������������ָ���¼����������Ϊdl��
		this.addMouseMotionListener(dl);
	}

	public void initSouthPanel() {
		JPanel panel_south = new JPanel();
		JLabel label = new JLabel("ȡɫ��");
		JButton[] button = { new JButton(), new JButton(), new JButton(),
				new JButton() };// ��ɫ��ť
		// �������飬�����洢��ť��Ҫ��ʾ����ɫ��Ϣ
		Color[] color_Array = { Color.black, Color.red, Color.GREEN, Color.BLUE };

		panel_south.add(label);
		for (int i = 0; i < color_Array.length; i++) {
			button[i].setBackground(color_Array[i]);// ���ð�ť��Ҫ��ʾ�ı�����ɫ
			button[i].setPreferredSize(new Dimension(15, 15));
			panel_south.add(button[i]);
			// ���¼�Դ������Ӷ�������������ָ���¼����������Ϊdl.
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
		faceCombo.setEditable(true);// ʹ֮�ɱ༭
		faceCombo.setEnabled(true);

		String[] Array = { "ֱ��", "����", "����", "���Բ", "������", "����������", "����������",
				"�ݹ�������", "��̺" };
		for (int i = 0; i < Array.length; i++) {
			faceCombo.addItem(Array[i]);
		}
		panel_left.add(faceCombo);
		faceCombo.addActionListener(dl);

		// ���߰�ť
		buttons = new JButton[] { new JButton("��Ƥ��"), new JButton("����"),
				new JButton("����ͼƬ"), new JButton("1"), new JButton("3"),
				new JButton("5") };
		for (int i = 0; i < buttons.length; i++) {// �ظ�������������Ҫ�õ�����
			buttons[i].setBackground(Color.cyan);
			// button1[i].setContentAreaFilled(false); // ���ð�ť͸����(������)�� ֻ����ϴ˾�
			panel_left.add(buttons[i]);
			// ���¼�Դ������Ӷ�������������ָ���¼����������Ϊdl.
			buttons[i].addActionListener(dl);
		}
		panel_left.setBackground(new Color(220, 240, 230));
		frame.add(panel_left, BorderLayout.WEST);
	}

	/**
	 * ��дJFrame���ػ淽��
	 */
	public void paint(Graphics g) {
		super.paint(g);// ���ø����������ķ���
		img = dl.getImage();
		if (img != null) {
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}
}