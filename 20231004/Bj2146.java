import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;
/*
 *  2023-10-04
 *  Bj2146 : �ٸ� �����
 *  
 *  �� ����
 *  - 2�� �ݺ��� �̿�
 *  - map�� 1�̰� island�� 0�̸� ���ο� ��
 *  - �ش� ��ġ�� �������� 4�� Ž�� bfs ������ -> ����Ǿ� �ִ� ��ġ�� ���� idx�� ����
 *  - idx ����
 *  
 *  �ִ� �Ÿ� ã��
 *  - 2�� �ݺ��� �̿�
 *  - island�� 0�� �ƴϸ� ��
 *  - �ش� ��ġ�� �������� 4�� Ž��, Ž�� �ø��� �湮 �迭 ���� ����
 *    0�̸� -> �ٴ��̹Ƿ� cnt ����, Ž���ؼ� �ٸ� ���� ã�ƾߵǴϱ� ť�� �ֱ�
 *    �ٸ� ���� ������ -> cnt�� min ���ؼ� �ּҰ� ����
 *    ���� ���� ������ -> Ž���� �ʿ� x, ť�� �ȳ���
 *  - ����ġ�� : current.cnt�� min ������ ũ�� �� �̻� Ž���� �ʿ� x -> continue;
 *  
 */
public class Bj2146 {

	static int N;
	static int[][] map;
	static int[][] island;	// ���ο� �� �迭
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	static int min;	// �ٸ��� �ִ� �Ÿ�
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());	// N �Է� �ޱ�
		
		map = new int[N][N];	// map ����
		for(int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());	// map �Է� �ޱ�
			}
		}
		
		island = new int[N][N];	// ���ο� �� �迭 ����
		min = Integer.MAX_VALUE;	// �ּҰ�
		
		findIsland();	// �� ��ȣ �ű��
		bfs();	// �ִ� �Ÿ� ã��
		System.out.println(min);
	}
	
	private static void findIsland() {
		Queue<Data> queue = new ArrayDeque<>();
		
		int idx = 1;	// �� ��ȣ
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(map[i][j] == 1 && island[i][j] == 0) {	// 1(��)�̰� ���ο� ������ ������� �ʾ�����
					queue.offer(new Data (i, j));	// ť�� �ֱ�
					island[i][j] = idx;	// ���ο� ������ ����
					
					while(!queue.isEmpty()) {
						Data current = queue.poll();
						
						for(int k = 0; k < 4; k++) {	// 4�� Ž��
							int nr = current.i + dr[k];
							int nc = current.j + dc[k];
							
							if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;	// ���� ������ continue
							
							if(island[nr][nc] != 0) continue;	// ���ο� ������ ����Ǿ� ������ continue
							
							if(map[nr][nc] == 1) {	// 1(��)�̸�
								queue.offer(new Data (nr, nc));	// ť�� �ֱ�
								island[nr][nc] = idx;	// ���ο� ������ ����
							}
						}
					}
					// �ش� ��ġ�� ������ �� ���� �Ϸ�
					
					idx++;	// idx ���� -> ���� �� ��ȣ	 
				}
			}
		}
		
	}
	
	private static void bfs() {
		Queue<Data> queue = new ArrayDeque<>();
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(island[i][j] != 0) {	// �� ��ȣ�� 0�� �ƴϸ� ��
					boolean[][] visited = new boolean[N][N];	// �ش� ��ġ �������� �湮 üũ
					queue.offer(new Data(i, j, 0));	// ť�� �ֱ�
					visited[i][j] = true;	// �湮 üũ

					int idx = island[i][j];	// �ش� ��ġ�� �� ��ȣ ���� -> 4�� Ž�� �� ���� ������ Ȯ�ο�
					while(!queue.isEmpty()) {
						Data current = queue.poll();
						
						if(current.cnt > min) continue;	// ����ġ�� : ������ �ٸ� �Ÿ� cnt�� min���� ũ�� �� �̻� Ž���� �ʿ� x
						
						for(int k = 0; k < 4; k++) {	// 4�� Ž��
							int nr = current.i + dr[k];
							int nc = current.j + dc[k];
							
							if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;	// ���� ����� continue
							
							if(visited[nr][nc]) continue;	// �湮������ continue
							
							if(island[nr][nc] == 0) {	// 0�̸� �ٴ�
								queue.offer(new Data(nr, nc, current.cnt + 1));	// cnt �����ϰ� ť�� �ֱ�
								visited[nr][nc] = true;	// �湮 üũ
								continue;	// ���� Ž��
							}
							
							if(island[nr][nc] != idx) min = Math.min(min, current.cnt);	// 0�� �ƴϰ� ���� ���� �ƴϸ� �ּҰ� ���� 
							
						}
					}
				}
			}
		}
		
		
	}
	
	static class Data{
		int i, j, cnt;

		public Data(int i, int j) {
			super();
			this.i = i;
			this.j = j;
		}

		public Data(int i, int j, int cnt) {
			super();
			this.i = i;
			this.j = j;
			this.cnt = cnt;
		}

		@Override
		public String toString() {
			return "Data [i=" + i + ", j=" + j + "]";
		}
		
	}

}
