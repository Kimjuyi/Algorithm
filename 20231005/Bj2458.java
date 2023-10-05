import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 *  2023-10-05
 * 	
 * 	Bj2458 : Ű ����
 * 
 *  ������İ� �޸������̼�
 */
public class Bj2458 {

	static int N, M, adj[][];
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine().trim());
		N = Integer.parseInt(st.nextToken().trim());
		M = Integer.parseInt(st.nextToken().trim());

		adj = new int[N + 1][N + 1];

		for(int i = 0; i < N + 1; i++) {
			adj[i][0] = -1;	// �޸������̼� �̿�. �ʱⰪ -1�� ����
		}

		for(int m = 0; m < M; m++) {
			st = new StringTokenizer(br.readLine().trim());
			int r = Integer.parseInt(st.nextToken().trim());
			int c = Integer.parseInt(st.nextToken().trim());

			adj[r][c] = 1;	// ���� ��� ����
		}

		for(int i = 1; i < N + 1; i++) {
			if(adj[i][0] == -1) dfs(i);	// 0���� -1�̸� ���� Ž������ ���� ��. dfs Ž���ϱ�
		}

		for(int i = 1; i < N + 1; i++) {
			for(int j = 1; j < N + 1; j++) {
				adj[0][j] += adj[i][j];	// Ű ���� �л��� �� ����
			}
		}

		int answer = 0;
		for(int i = 0; i < N + 1; i++) {
			if(adj[i][0] + adj[0][i] == N - 1) answer++;	// Ű ���� �л� �� + Ű ū �л� �� = N-1 �̸� �� ��°���� �� �� ����
		}

		System.out.println(answer);
	}

	private static void dfs(int cur) {	// cur : ���� Ž���� ��ȣ
		for(int j = 1; j < N + 1; j++) {
			if(adj[cur][j] == 1) {	// 1�̰�
				if(adj[j][0] == -1) {	// ���� Ž������ ���� ���̸�
					dfs(j);	// Ž���ϱ�
				}

				// Ž���ߴ� ���̰ų� Ž���� ��������
				if(adj[j][0] > 0) {	// �޸������̼� Ȯ��. 0���� ũ�� Ű�� ū �л����� �ִ� ��
					for(int c = 1; c < N + 1; c++) {
						if(adj[j][c] == 1) adj[cur][c] = 1;	// ���� ���踦 ���� ����� ����
					}
				}
			}

			int cnt = 0;	// Ű ū �л��� ��
			for(int c = 1; c < N + 1; c++) {
				if(adj[cur][c] == 1) cnt++;	// Ű ū �л� �� ����
			}
			adj[cur][0] = cnt;	// 0���� ����
		}
	}

}
