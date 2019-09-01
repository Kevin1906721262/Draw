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
		// �����������
		JFrame frame = new JFrame("Kevin֮���⻭��");
		frame.setSize(750, 500);
		frame.setLocationRelativeTo(null);// ʹ����λ����Ļ����
		frame.setDefaultCloseOperation(3);// ����رշ�ʽ
		frame.setLayout(new BorderLayout());// ���ô���Ĳ��ַ�ʽΪ��ʽ����

		// ��ӹ��� ���
		JPanel panel_op = new JPanel();
		JPanel panel_south = new JPanel();
		JLabel label = new JLabel("ȡɫ��");
		panel_op.setPreferredSize(new Dimension(90, 0));
		panel_op.setLayout(new GridLayout(15, 1));

		// ���ѡ�ť(��)
		JComboBox<String> faceCombo = new JComboBox<>();
		faceCombo.setEditable(true);// ʹ֮�ɱ༭
		faceCombo.setEnabled(true);

		String[] Array = { "ֱ��", "����", "����", "���Բ", "������", "����������", "����������",
				"�ݹ�������", "��̺" };
		for (int i = 0; i < Array.length; i++) {
			faceCombo.addItem(Array[i]);
		}
		panel_op.add(faceCombo);

		// ���߰�ť

		// JButton button1[] = new JButton[Array.length];
		JButton[] button1 = { new JButton("��Ƥ��"), new JButton("����"),
				new JButton("����ͼƬ"), new JButton("1"), new JButton("3"),
				new JButton("5") };
		JButton[] button2 = { new JButton(), new JButton(), new JButton(),
				new JButton(), new JButton() };// ��ɫ��ť
		// �������飬�����洢��ť��Ҫ��ʾ����ɫ��Ϣ
		Color[] color_Array = { Color.black, Color.red, Color.GREEN, Color.BLUE };
		for (int i = 0; i < button1.length; i++) {// �ظ�������������Ҫ�õ�����
			button1[i].setBackground(Color.cyan);
			// button1[i].setContentAreaFilled(false); // ���ð�ť͸����(������)�� ֻ����ϴ˾�
			panel_op.add(button1[i]);
		}

		panel_south.add(label);
		for (int i = 0; i < color_Array.length; i++) {
			button2[i].setBackground(color_Array[i]);// ���ð�ť��Ҫ��ʾ�ı�����ɫ
			button2[i].setPreferredSize(new Dimension(15, 15));
			panel_south.add(button2[i]);
		}
		frame.add(panel_op, BorderLayout.WEST);
		frame.add(panel_south, BorderLayout.SOUTH);
		// panel_op.add("SOUTH", panel_south);//frame������

		frame.add(this);
		panel_op.setBackground(new Color(220, 240, 230));
		panel_south.setBackground(new Color(218, 224, 241));
		panel_south.setPreferredSize(new Dimension(0, 20));
		this.setBackground(Color.white);
		frame.setVisible(true);
		dl = new DrawingListener(this, faceCombo);

		for (int i = 0; i < button1.length; i++) {
			// ���¼�Դ������Ӷ�������������ָ���¼����������Ϊdl.
			button1[i].addActionListener(dl);
		}
		for (int i = 0; i < button2.length; i++) {
			// ���¼�Դ������Ӷ�������������ָ���¼����������Ϊdl.
			button2[i].addActionListener(dl);
		}
		// System.out.println(Math.cos(90));
		faceCombo.addActionListener(dl);
		this.addMouseListener(dl);
		// ���¼�Դ�����������ƶ���������������ָ���¼����������Ϊdl��
		this.addMouseMotionListener(dl);

	}

	// a����=��x-y*sin72,y*(1-sin28)��
	// c����=��x,0��
	// e����=��x+y*sin72,y*(1-sin28) )
	// g����=��x+y*sin36,y*(1+sin54)��%
	// i����=��x-y*sin36,y*(1+sin54)��
	// j����=��x-(1-sin28)*tan28,y(1-sin28) )
	// b����=��x+(1-sin28)*tan28,y(1-sin28) )
	// ͬ�����d��f��h����
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