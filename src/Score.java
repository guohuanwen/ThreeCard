import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Score {

	// Ȩ ֵ
	private int weight = 52 * 3;
	private static List<Card> cards = new ArrayList<Card>();
	// ���е��� �� 22100��
	private static List<Card3> card3s = new ArrayList<Card3>();

	// ������
	private static int P = 156;

	private static long[] totalScore = new long[22100];

	public static void main(String[] args) {

		for (int i = 1; i <= 13; i++) {
			for (int j = 1; j <= 4; j++) {
				Card card = new Card();
				card.colorType = j;
				card.cardType = i;
				card.score = (i - 1) * 4 + j;
				cards.add(card);
			}
		}

		for (int i = 0; i < 50; i++) {
			Card card1 = cards.get(i);
			// List<Card> cards2 = new ArrayList<Card>();
			// List<Card> cards3 = new ArrayList<Card>();
			for (int j = i + 1; j < 51; j++) {
				// cards2.clear();
				// cards2.addAll(cards);
				// cards2.remove(card1);
				// Card card2 = cards2.get(j);
				Card card2 = cards.get(j);
				for (int k = j + 1; k < 52; k++) {
					// cards3.clear();
					// cards3.addAll(cards);
					// cards3.remove(card1);
					// cards3.remove(card2);
					// Card card3 = cards3.get(k);

					// Card3 card33 = new Card3(card1,card2,card3);
					// card3s.add(card33);
					Card card3 = cards.get(k);

//					System.out.println("i=" + card1.score + ";j=" + card2.score
//							+ ";k=" + card3.score);

					Card3 card33 = new Card3(card1, card2, card3);
					card3s.add(card33);
				}
			}
		}
		
		System.out.println(card3s.size());

		for (int i = 0; i < card3s.size(); i++) {
			Card3 c = card3s.get(i);
			System.out.println(i);
			c.score = clatulateScore(c);
			totalScore[i] = c.score;
		}
	
		totalScore = minToMax(totalScore);
		for (int i = 0;i<totalScore.length/100;i++ ){
			System.out.println(totalScore[i]);
		}
		System.out.println(totalScore.length);
		
		
		//��ѯ����    ��cards������ 
		Card3  card3 = new Card3(cards.get(0),cards.get(51),cards.get(9));
		float success = calculateSuccess(card3);
		System.out.println("success="+success);
	}

	private static long clatulateScore(Card3 c) {
		// ����
		if (isSameThree(c)) {
			System.out.println("����");
			return (long) ((c.card1.score + c.card2.score + c.card3.score) * Math
					.pow(P, 5));
		}
		// ͬ��˳
		else if (isSameColor(c) && isConnect(c)) {
			System.out.println("ͬ��˳");
			return (long) ((c.card1.score + c.card2.score + c.card3.score) * Math
					.pow(P, 4));
		}
		// ��
		else if (isSameColor(c)) {
			System.out.println("��");
			return (long) ((c.card1.score + c.card2.score + c.card3.score) * Math
					.pow(P, 3));
		}
		// ˳��
		else if (isConnect(c)) {
			System.out.println("˳��");
			return (long) ((c.card1.score + c.card2.score + c.card3.score) * Math
					.pow(P, 2));
		}
		// ����
		else if (isDouble(c)) {
			System.out.println("����");
			return (long) ((c.card1.score + c.card2.score + c.card3.score) * Math
					.pow(P, 1));
		}
		// ��ͨ��
		else {
			System.out.println("��ͨ��");
			return c.card1.score + c.card2.score + c.card3.score;
		}
	}
	
	//   2�ֲ���
	public static int find2(long param,long[] m,int start,int end) throws Exception{
		System.out.println("param="+param+";start="+start+";end="+end);
		if (param < m[0] || param > m[m.length-1]){
			throw  new Exception("��ǰ���������鷶Χ��");
		}
		//����������
		if (end - start == 1){
			if (param == m[start]){
				return start;
			}else {
				return end;
			}
		}
		int mediem = (start + end)/2;
		if (param > m[mediem]){
			return find2(param,m,mediem,end);
		}else {
			return find2(param,m,start,mediem);
		}
	}

	// �ж�ʤ��
	public static float calculateSuccess(Card3 card) {
		long score = clatulateScore(card);
		try {
			float count = find2(score,totalScore,0,totalScore.length-1);
			return count/22100;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	// �ж�������ͬ
	private static boolean isSameThree(Card3 c) {
		if (c.card1.cardType == c.card2.cardType
				&& c.card1.cardType == c.card3.cardType) {
			return true;
		} else {
			return false;
		}
	}

	// �ж϶���
	private static boolean isDouble(Card3 card) {
		if (isSameThree(card)) {
			return false;
		}
		long[] m = minToMax(new long[] { card.card1.cardType,
				card.card2.cardType, card.card3.cardType });
		if (m[0] == m[1]) {
			return true;
		}
		if (m[1] == m[2]) {
			return true;
		}
		return false;
	}

	// �ж��Ƿ���ͬ��
	private static boolean isSameColor(Card3 c) {
		if (c.card1.colorType == c.card2.colorType
				&& c.card1.colorType == c.card3.colorType) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean isConnect(Card3 card) {
		return isConnect(card.card1.cardType, card.card2.cardType,
				card.card3.cardType);
	}

	// �Ƿ���˳��
	private static boolean isConnect(long a, long b, long c) {
		long[] m = minToMax(new long[] { a, b, c });
		if (m[1] == m[0] + 1 && m[2] == m[1] + 1) {
			return true;
		} else {
			return false;
		}
	}

	// ��С��������
	private static long[] minToMax(long[] param) {
		for (int i = param.length - 1; i >= 0; i--) {
			for (int j = 0; j < i; j++) {
				if (param[j] > param[j + 1]) {
					long m = param[j];
					param[j] = param[j + 1];
					param[j + 1] = m;
				}
			}
		}
		return param;
	}

	public static class Card {
		// ���� �ܹ� 52 ���� ��2 Ϊ 1�� ��AΪ 52 ��
		long score;
		// �ں�÷�� 1��2��3��4
		int colorType;
		// 2 - A �� 2 ��ʼ 2-1 K-12 A-13
		int cardType;
	}

	public static class Card3 {

		Card3(Card c1, Card c2, Card c3) {
			this.card1 = c1;
			this.card2 = c2;
			this.card3 = c3;
		}

		Card card1;
		Card card2;
		Card card3;
		long score;
	}

}
