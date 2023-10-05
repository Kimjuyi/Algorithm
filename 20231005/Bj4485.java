import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
/*
 * 2023-10-05
 * 
 * Bj4485 : ��� �� ���� �ְ� ������?
 * 
 * �ּ� ��� ã��, �־����� ���� ��� -> ���ͽ�Ʈ�� �̿�
 * ��� �迭�� ��������� ��� + �̵��� map ���� ���Ͽ� �ּҰ����� ����
 * 
 */
public class Bj4485 {

	static int N, map[][], visited[][];
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int t = 0;
		while(true) {
			N = Integer.parseInt(br.readLine());
			
			if(N == 0) break;
			
			sb.append("Problem ").append(++t).append(": ");
			
			map = new int[N][N];
			StringTokenizer st = null;
			for(int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());	// map �Է� �ޱ�
				}
			}
			
			visited = new int[N][N];	// �湮 üũ �迭 <- �ּ� ��� ����
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					visited[i][j] = Integer.MAX_VALUE;	// �ʱⰪ�� max�� ����
				}
			}
			
			bfs();
			sb.append(visited[N - 1][N - 1]).append("\n");
		}
		
		System.out.println(sb);
	}
	
	private static void bfs() {
		PriorityQueue<Data> pqueue = new PriorityQueue<>();	// ���ͽ�Ʈ�� ��� ���� �켱���� ť ���
		
		pqueue.offer(new Data(0, 0, map[0][0]));	// ��� ��ġ
		visited[0][0] = map[0][0];	// ��� ��ġ ��� ����
		
		int[] dr = {-1, 1, 0, 0};
		int[] dc = {0, 0, -1, 1};
		
		while(!pqueue.isEmpty()) {
			Data current = pqueue.poll();
			
			for(int i = 0; i < 4; i++) {	// 4���� Ž��
				int nr = current.i + dr[i];
				int nc = current.j + dc[i];
				
				if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
				
				if(visited[nr][nc] > visited[current.i][current.j] + map[nr][nc]) {	// �湮�� ��ġ������ ����� ��������� ��� + �湮�� ��뺸�� ũ��
					visited[nr][nc] = visited[current.i][current.j] + map[nr][nc];	// ����
					pqueue.offer(new Data(nr, nc, visited[nr][nc]));	// ť�� �ֱ�
				}
			}
			
		}
	}

	public static class Data implements Comparable<Data>{
		int i, j, num;

		public Data(int i, int j, int num) {
			super();
			this.i = i;
			this.j = j;
			this.num = num;
		}

		@Override
		public String toString() {
			return "Data [i=" + i + ", j=" + j + ", num=" + num + "]";
		}

		@Override
		public int compareTo(Data o) {
			return this.num - o.num;
		}
		
	}
}
