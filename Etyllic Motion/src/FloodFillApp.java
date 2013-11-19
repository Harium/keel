
public class FloodFillApp {

	private static int[][] matrix;

	private static final int w = 10;
	private static final int h = 7;
	private static final int border = 1;

	private static final int MARK = 9;

	private static int component = 0;

	public static void main(String[] args) {

		matrix = new int[w][h];

		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				matrix[i][j] = 0;

			}

		}

		paintMatrix();
		filter();

		showMatrix();

	}

	private static void paintMatrix(){

		matrix [2][1] = MARK;
		matrix [3][1] = MARK;
		matrix [2][2] = MARK;
		matrix [3][2] = MARK;
		matrix [4][2] = MARK;

		matrix [7][1] = MARK;
		matrix [7][2] = MARK;
		matrix [7][3] = MARK;

		matrix [2][3] = MARK;
		matrix [2][4] = MARK;
		matrix [3][4] = MARK;
		matrix [4][4] = MARK;
		//matrix [5][4] = MARK;
		matrix [6][4] = MARK;
		matrix [7][4] = MARK;

		matrix [1][5] = MARK;
		matrix [2][5] = MARK;
	}

	private static void filter(){

		for(int j=border;j<h-border;j++){
			for(int i=border;i<w-border;i++){

				if(matrix[i][j]==MARK){

					verifyNeighboor(i, j);

				}
			}
		}

	}

	private static void verifyNeighboor(int i, int j){
		//Previous is 0
		if(matrix[i-1][j]==0){

			if(matrix[i][j-1]==0){
				component++;
				matrix[i][j] = component;
			}
			else{
				matrix[i][j] = matrix[i][j-1];
			}

			//Previous is !=0 and Upper is 0
		}else if(matrix[i][j-1]==0){
			matrix[i][j] = matrix[i-1][j];
		}else{
			//Previous and upper !=0			
			if(matrix[i-1][j]==matrix[i][j-1]){
				matrix[i][j] = matrix[i-1][j];
			}else{
				foundLowerComponent(i, j);
			}
		}

	}

	private static void foundLowerComponent(int x, int y){

		int look = Math.max(matrix[x-1][y],matrix[x][y-1]);

		int component = Math.min(matrix[x-1][y],matrix[x][y-1]);

		matrix[x][y] = component;

		boolean found = true;
		
		for(int j=y;j>=border;j--){

			for(int i=w-border;i>=border;i--){
				if(matrix[i][j]==look){
					matrix[i][j] = component;
					found = true;
				}
			}

			if(!found){
				break;
			}
			
			found = false;
		}
	}

	private static void showMatrix(){
		for(int j=0;j<h;j++){
			for(int i=0;i<w;i++){
				System.out.print(matrix[i][j]);
			}
			System.out.println("");
		}
	}

}
